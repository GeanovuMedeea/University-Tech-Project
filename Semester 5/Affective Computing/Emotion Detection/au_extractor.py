import torch
from cnn import FacialExpressionCNN
from torch.utils.data import DataLoader
from cnn import FacialExpressionDataset
import torch.nn.functional as F
import tensorflow

def classify_emotion(au_predictions):
    #au_predictions = au_predictions.squeeze()
    #au_predictions_list = au_predictions.tolist()

    if au_predictions.sum() == 0:
        return "unknown"
    #print(f"Thresholded AU Predictions: {au_predictions}")
    #print(au_predictions.numpy())

    #print(tensor)

    # Sad
    if (au_predictions[2] == 1 and au_predictions[4] == 1 and au_predictions[5] == 1 and au_predictions[8] == 1 and au_predictions[7] == 1 and au_predictions[9]==0) or (au_predictions[2]==0 and au_predictions[4]==1 and au_predictions[5]==1 and au_predictions[7]==1 and au_predictions[8]==1 and au_predictions[9]==0):
        return "sad"

    # Fear
    if (au_predictions[5] == 1 and au_predictions[7] == 0 and au_predictions[4] == 0) or (au_predictions[7]==1 and au_predictions[9]==1 and au_predictions[4]==0):
        return "fear"

        # Surprise
    if (au_predictions[2] == 1 and (au_predictions[7] == 1 and au_predictions[9] == 1 and au_predictions[4] == 0 and au_predictions[5] == 0)) or (au_predictions[2]==1 and au_predictions[4] == 0 and au_predictions[5] == 0 and au_predictions[7] == 1 and au_predictions[8] == 0) or (au_predictions[2] == 1 and au_predictions[4] == 0):
        return "surprise"

    #anger
    if (au_predictions[2] == 1 and (au_predictions[5] == 1 or au_predictions[7] == 1 and au_predictions[9]==1)) or (au_predictions[4] == 1 and au_predictions[5] == 1 and au_predictions[7]==1 and au_predictions[8]==1 and au_predictions[9]==1):
        return "anger"

    # Disgust
    if (au_predictions[4]==1 and au_predictions[5]==1 and au_predictions[7]==0 and au_predictions[8]==0 and au_predictions[2]==0) or (au_predictions[7] == 1 and au_predictions[9] == 0 and au_predictions[6]==1 and au_predictions[4] == 1 and au_predictions[5] == 1) or (au_predictions[7]==1 and au_predictions[2]==1):
        return "disgust"

    # Happy
    if au_predictions[2]==0 and ((au_predictions[4]==1 and au_predictions[5]==1 and au_predictions[7]==1 and au_predictions[8]==0 and au_predictions[9]==0) or (au_predictions[4] == 1 and au_predictions[8] == 1 and au_predictions[5]==1 or (au_predictions[7] == 1 and au_predictions[9] == 1))):
        return "happy"

        # Contempt
    if (au_predictions[8] == 1 and au_predictions[9] == 1 or au_predictions[5] == 1 and au_predictions[7] == 1) or (au_predictions[9]==1 and au_predictions[4]==0 and au_predictions[2]==0 and au_predictions[7]==0):
        return "contempt"

    return "neutral"

# Example input: Predicted AUs
#predicted_aus = [0., 0., 1., 0., 1., 1., 0., 1., 1., 0., 0., 1., 0., 0., 0., 0., 0.]
#print(predicted_aus)
#emotion = classify_emotion(predicted_aus)
#print(f"Predicted emotion test: {emotion}")

def test_extractor_set_up():
    # Set the device to GPU if available, otherwise use CPU
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

    # Load the pre-trained model
    model = torch.load('facial_expression_model_full.pth')
    model.eval()  # Set the model to evaluation mode (no training here)
    model.to(device)  # Move the model to the device (GPU or CPU)

    # Load the dataset for inference
    #dataset = FacialExpressionDataset(csv_file='updated_labels_with_landmarks_columns.csv')
    dataset = FacialExpressionDataset(csv_file='updated_labels_with_landmarks_columns.csv')

    data_loader = DataLoader(dataset, batch_size=1, shuffle=False)

    # Inference loop (!!! no training here)
    for batch in data_loader:
        landmarks = batch['landmarks'].to(device)
        with torch.no_grad():
            raw_outputs = model(landmarks)

        outputs = raw_outputs[0]
        #au_predictions = outputs.clone().detach()
        #tensor = (au_predictions > 0.5).float()
        #tensor = tensorflow.constant(tensor).numpy()
        au_prediction = (outputs > 0.5).float().cpu().numpy().flatten()
        emotion = classify_emotion(au_prediction)

        print(f"Predicted emotion: {emotion}")
        #print(f"Predicted AUs: {outputs}")

if __name__ == "__main__":
    test_extractor_set_up()