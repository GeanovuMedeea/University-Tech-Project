import joblib
import numpy as np
import torch
import pandas as pd
from fontTools.misc.classifyTools import classify
from torch.utils.data import DataLoader
from au_extractor import classify_emotion
from cnn import FacialExpressionDataset, FacialExpressionCNN
import time

best_model = joblib.load('svm_emotion_model.pkl')
scaler = joblib.load('scaler.pkl')
emotion_labels = ['anger', 'disgust', 'fear', 'happy', 'sad', 'surprise', 'neutral', 'unknown']

label_csv = pd.read_csv('video_content_labels.csv')
label_dict = dict(zip(label_csv['second'], label_csv['label']))

device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

def combine_predictions(svm_probs, cnn_probs):
    combined_probs = {}
    for emotion in svm_probs:
        combined_probs[emotion] = 0.3 * svm_probs[emotion] + 0.3 * cnn_probs.get(emotion, 0)
    return combined_probs


def select_final_emotion(combined_probs):
    final_emotion = max(combined_probs, key=combined_probs.get)
    return final_emotion


def monitor_csv(csv_file):
    processed_rows = set()
    last_checked_time = time.time()
    model = FacialExpressionCNN(num_landmarks=17, num_aus=17).to(device)
    model.load_state_dict(torch.load('facial_expression_model.pth'))
    model.eval()

    while True:
        current_time = time.time()
        if current_time - last_checked_time > 5:
            try:
                df = pd.read_csv(csv_file)
                new_rows = set(df.index) - processed_rows
                if new_rows:
                    print(f"Found {len(new_rows)} new rows. Processing...")
                    process_new_data(csv_file, model, new_rows)
                    processed_rows.update(new_rows)
                else:
                    print(f"No new data in CSV at {current_time}, continuing to monitor...")
            except Exception as e:
                print(f"Error reading CSV: {e}")

            last_checked_time = current_time
        time.sleep(0.5)


from scipy.special import softmax

def process_new_data(csv_file, model, new_rows=None, batch_size=1):
    dataset = FacialExpressionDataset(csv_file)
    if new_rows:
        global_indices = list(new_rows)
        dataset.data = dataset.data.iloc[global_indices]
    else:
        global_indices = list(dataset.data.index)

    data_loader = DataLoader(dataset, batch_size=batch_size, shuffle=False)

    au_columns = [
        'AU01_r', 'AU02_r', 'AU04_r', 'AU05_r', 'AU06_r', 'AU07_r',
        'AU09_r', 'AU10_r', 'AU12_r', 'AU14_r', 'AU15_r', 'AU17_r',
        'AU20_r', 'AU23_r', 'AU25_r', 'AU26_r', 'AU45_r'
    ]

    model.eval()

    with torch.no_grad():
        for batch_idx, batch in enumerate(data_loader):
            inputs = batch['landmarks'].to(device)
            batch_indices = global_indices[batch_idx * batch_size: (batch_idx + 1) * batch_size]

            outputs = model(inputs)
            df = pd.read_csv(csv_file)

            input_array = inputs.view(inputs.size(0), -1).cpu().numpy()

            for i, global_idx in enumerate(batch_indices):
                output = outputs[i]
                au_prediction = (output > 0.5).float().cpu().numpy()
                emotion = classify_emotion(au_prediction)

                cnn_probs = {
                    "happy": 0.0,
                    "sad": 0.0,
                    "fear": 0.0,
                    "surprise": 0.0,
                    "anger": 0.0,
                    "disgust": 0.0,
                    "contempt": 0.0,
                    "neutral": 0.0
                }
                cnn_probs[emotion] = 1.0

                svm_predictions = best_model.decision_function(scaler.transform(input_array))

                svm_probs = softmax(svm_predictions, axis=1)[0]

                svm_probs_dict = {emotion_labels[i]: svm_probs[i] for i in range(len(emotion_labels))}

                combined_probs = combine_predictions(svm_probs_dict, cnn_probs)
                print(combined_probs)

                final_emotion = select_final_emotion(combined_probs)

                for j, au in enumerate(au_prediction.flatten()):
                    df.loc[global_idx, au_columns[j]] = au
                df.loc[global_idx, 'label'] = final_emotion
                print(f"Updated row {global_idx} in the CSV. Predicted Emotion: {final_emotion}")

            df.to_csv(csv_file, index=False)

if __name__ == "__main__":
    monitor_csv('landmarks_data.csv')
