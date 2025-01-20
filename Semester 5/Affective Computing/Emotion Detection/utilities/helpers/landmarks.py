import cv2
import mediapipe as mp
import pandas as pd
import json
import os

# Initialize Mediapipe model
mp_holistic = mp.solutions.holistic
holistic_model = mp_holistic.Holistic(
    min_detection_confidence=0.5,
    min_tracking_confidence=0.5
)

csv_file = '../../affectnet/labels.csv'
df = pd.read_csv(csv_file)

updated_data = []

landmark_map = {
    1: "nose_tip",  # Nose tip
    133: "right_eye_inner",  # Right eye outer corner
    33: "right_eye_outer",  # Right eye inner corner
    362: "left_eye_inner",  # Left eye inner corner
    263: "left_eye_outer",  # Left eye outer corner
    61: "mouth_right_corner",  # Right mouth corner
    291: "mouth_left_corner",  # Left mouth corner
    10: "forehead_glabella",  # Forehead
    107: "right_eyebrow_inner",  # Right eyebrow inner
    70: "right_eyebrow_outer",  # Right eyebrow outer
    336: "left_eyebrow_inner",  # Left eyebrow inner
    300: "left_eyebrow_outer",  # Left eyebrow outer
    152: "chin",  # Chin
    0: "upper_lip",  # Right jawline point
    17: "lower_lip",  # Left jawline point
    280: "left_cheek",  # Right cheek point
    50: "right_cheek"  # Left cheek point
}

for index, row in df.iterrows():
    image_path = os.path.abspath("affectnet/" + row['pth'])
    print(f"Processing image: {image_path}")

    image = cv2.imread(image_path)
    if image is None:
        print(f"Error: Unable to load image {image_path}. Skipping...")
        continue

    image_rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    results = holistic_model.process(image_rgb)

    # Initialize a dictionary to store the landmark coordinates for the current image
    updated_row = row.to_dict()

    if results.face_landmarks:
        nose_tip = results.face_landmarks.landmark[1]  # Nose tip for normalization
        for index, lm in enumerate(results.face_landmarks.landmark):
            if index in landmark_map:
                normalized_x = lm.x - nose_tip.x
                normalized_y = lm.y - nose_tip.y
                updated_row[f"{landmark_map[index]}_x"] = normalized_x
                updated_row[f"{landmark_map[index]}_y"] = normalized_y
    #print(updated_row)
    updated_data.append(updated_row)

updated_df = pd.DataFrame(updated_data)

updated_df.to_csv("all_images_landmarks.csv", index=False)

print("Landmarks appended as separate columns and saved to all_images_landmarks.csv successfully!")
