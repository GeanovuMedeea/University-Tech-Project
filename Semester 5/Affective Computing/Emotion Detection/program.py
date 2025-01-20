import cv2
import time
import mediapipe as mp
import csv
import os
import requests
import numpy as np

# Initialize Mediapipe model
mp_holistic = mp.solutions.holistic
holistic_model = mp_holistic.Holistic(
    min_detection_confidence=0.5,
    min_tracking_confidence=0.5
)

mp_drawing = mp.solutions.drawing_utils

csv_file_path = "landmarks_data.csv"

csv_header = [
    'id', 'pth', 'label', 'relFCs',
    'AU01_r', 'AU02_r', 'AU04_r', 'AU05_r', 'AU06_r', 'AU07_r', 'AU09_r', 'AU10_r', 'AU12_r', 'AU14_r', 'AU15_r',
    'AU17_r', 'AU20_r', 'AU23_r', 'AU25_r', 'AU26_r', 'AU45_r',
    'AU01_c', 'AU02_c', 'AU04_c', 'AU05_c', 'AU06_c', 'AU07_c', 'AU09_c', 'AU10_c', 'AU12_c', 'AU14_c', 'AU15_c',
    'AU17_c', 'AU20_c', 'AU23_c', 'AU25_c', 'AU26_c', 'AU28_c', 'AU45_c',
    'upper_lip_x', 'upper_lip_y', 'nose_tip_x', 'nose_tip_y', 'forehead_glabella_x', 'forehead_glabella_y',
    'lower_lip_x', 'lower_lip_y', 'right_eye_outer_x', 'right_eye_outer_y', 'right_cheek_x', 'right_cheek_y',
    'mouth_right_corner_x', 'mouth_right_corner_y', 'right_eyebrow_outer_x', 'right_eyebrow_outer_y',
    'right_eyebrow_inner_x', 'right_eyebrow_inner_y', 'right_eye_inner_x', 'right_eye_inner_y', 'chin_x', 'chin_y',
    'left_eye_outer_x', 'left_eye_outer_y', 'left_cheek_x', 'left_cheek_y', 'mouth_left_corner_x',
    'mouth_left_corner_y',
    'left_eyebrow_outer_x', 'left_eyebrow_outer_y', 'left_eyebrow_inner_x', 'left_eyebrow_inner_y',
    'left_eye_inner_x', 'left_eye_inner_y'
]

# Set up the CSV
if not os.path.exists(csv_file_path) or os.path.getsize(csv_file_path) == 0:
    with open(csv_file_path, "w", newline="") as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow(csv_header)

# Function to fetch a frame from the phone camera
def fetch_phone_frame(phone_camera_url):
    response = requests.get(phone_camera_url)
    img_array = np.array(bytearray(response.content), dtype=np.uint8)
    frame = cv2.imdecode(img_array, cv2.IMREAD_COLOR)
    if frame is None:
        raise ValueError("No frame captured from phone camera.")
    return frame

# Choose the video source
print("Select video source:")
print("1. Desktop webcam")
print("2. Phone camera (IP Webcam)")
choice = input("Enter 1 or 2: ")

if choice == "1":
    capture = cv2.VideoCapture(0)
    if not capture.isOpened():
        print("Error: Could not open webcam.")
        exit()
elif choice == "2":
    phone_camera_url = "http://192.168.51.121:8080/shot.jpg"
    capture = None
else:
    print("Invalid choice.")
    exit()


# Initialize timers
start = time.time()
fps_timer = time.time()
landmark_timer = time.time()
save_interval = 3
output_folder = "recorded"
os.makedirs(output_folder, exist_ok=True)

# Named landmarks mapping for key facial features
landmark_map = {
    1: "nose_tip",
    133: "right_eye_inner",
    33: "right_eye_outer",
    362: "left_eye_inner",
    263: "left_eye_outer",
    61: "mouth_right_corner",
    291: "mouth_left_corner",
    10: "forehead_glabella",
    107: "right_eyebrow_inner",
    70: "right_eyebrow_outer",
    336: "left_eyebrow_inner",
    300: "left_eyebrow_outer",
    152: "chin",
    0: "upper_lip",
    17: "lower_lip",
    280: "left_cheek",
    50: "right_cheek"
}

current_id = 0

# Start webcam capture
# capture = cv2.VideoCapture(0)
# if not capture.isOpened():
#     print("Error: Could not open webcam.")
#     exit()

while True:
    try:
        if choice == "1":  # Desktop webcam
            ret, frame = capture.read()
            if not ret:
                print("Failed to grab frame from webcam.")
                break
        elif choice == "2":  # Phone camera
            frame = fetch_phone_frame(phone_camera_url)
    except Exception as e:
        print(f"Error capturing frame: {e}")
        break

    frame = cv2.resize(frame, (800, 600))
    image = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
    results = holistic_model.process(image)
    annotated_image = frame.copy()

    # Draw selected landmarks on the frame
    if results.face_landmarks:
        h, w, _ = frame.shape
        landmarks = results.face_landmarks.landmark

        for index, landmark in enumerate(landmarks):
            if index in landmark_map:
                cx, cy = int(landmark.x * w), int(landmark.y * h)
                cv2.circle(annotated_image, (cx, cy), 5, (255, 0, 255), -1)
                cv2.putText(annotated_image, landmark_map[index], (cx + 10, cy - 10),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 1)

    # Calculate FPS
    current_time = time.time()
    fps = 1 / (current_time - fps_timer)
    fps_timer = current_time
    cv2.putText(annotated_image, f"{int(fps)} FPS", (10, 70), cv2.FONT_HERSHEY_COMPLEX, 1, (0, 255, 0), 2)
    cv2.imshow("Facial Landmarks", annotated_image)

    # Save landmarks every 3 seconds
    if results.face_landmarks and (current_time - landmark_timer) > save_interval:
        face_landmarks = []

        # Find the nose tip for normalization
        nose_tip = results.face_landmarks.landmark[1]

        # Normalize landmarks with respect to the nose tip
        for index, lm in enumerate(results.face_landmarks.landmark):
            if index in landmark_map:
                # Normalize the x and y coordinates by subtracting the nose tip's coordinates
                normalized_x = lm.x - nose_tip.x
                normalized_y = lm.y - nose_tip.y
                face_landmarks.append({
                    "name": landmark_map[index],
                    "x": normalized_x,
                    "y": normalized_y
                })

        row = [current_id]  # id (incremented for each row)

        timestamp_str = f"{int(current_time - start):04d}"
        image_path = os.path.join(output_folder, f"frame_{timestamp_str}.jpg")
        row.append(image_path)  # pth (file path of the saved image)

        # Label (set to 'unknown' for now)
        row.append("unknown")

        row.append(0)

        # Add AU values (all set to 0 for now)
        row.extend([0] * 34)  # 34 AUs because we have both r and c

        # Add normalized landmarks data to the row
        for landmark in face_landmarks:
            row.append(landmark['x'])
            row.append(landmark['y'])

        # Save the landmarks and other data to the CSV
        with open(csv_file_path, "a", newline="") as csv_file:
            writer = csv.writer(csv_file)
            writer.writerow(row)

        # Save image
        cv2.imwrite(image_path, frame)
        print(f"Saved frame to {image_path}")
        landmark_timer = current_time

        current_id += 1

    if cv2.waitKey(1) == 13:
        print("Exiting loop...")
        break

capture.release()
cv2.destroyAllWindows()
