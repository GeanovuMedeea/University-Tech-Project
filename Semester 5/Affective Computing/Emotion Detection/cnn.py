import torch
import torch.optim as optim
import torch.nn as nn
import pandas as pd
from torch.utils.data import Dataset, DataLoader, random_split
import numpy as np

# Define the device (GPU if available, otherwise CPU)
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

import pandas as pd

df = pd.read_csv('updated_labels_with_landmarks_columns.csv')

landmark_columns = [
    'upper_lip_x','upper_lip_y', 'nose_tip_x', 'nose_tip_y', 'forehead_glabella_x', 'forehead_glabella_y', 'lower_lip_x',
    'lower_lip_y','right_eye_outer_x', 'right_eye_outer_y', 'right_cheek_x', 'right_cheek_y', 'mouth_right_corner_x',
    'mouth_right_corner_y', 'right_eyebrow_outer_x', 'right_eyebrow_outer_y', 'right_eyebrow_inner_x',
    'right_eyebrow_inner_y', 'right_eye_inner_x', 'right_eye_inner_y', 'chin_x', 'chin_y', 'left_eye_outer_x',
    'left_eye_outer_y', 'left_cheek_x', 'left_cheek_y', 'mouth_left_corner_x', 'mouth_left_corner_y',
    'left_eyebrow_outer_x', 'left_eyebrow_outer_y', 'left_eyebrow_inner_x', 'left_eyebrow_inner_y', 'left_eye_inner_x',
    'left_eye_inner_y'
]

# Check for NaN or Inf
nan_inf_mask = df[landmark_columns].isnull().any(axis=1) | ~df[landmark_columns].applymap(lambda x: pd.notna(x) and x != float('inf') and x != float('-inf')).all(axis=1)

#print(f"Rows with NaN or Inf in landmarks: {df[nan_inf_mask]}")

df = df.dropna(subset=landmark_columns)

df[landmark_columns] = df[landmark_columns].fillna(df[landmark_columns].mean())


class FacialExpressionDataset(Dataset):
    def __init__(self, csv_file):
        self.data = pd.read_csv(csv_file)

        # Extract columns related to landmarks
        landmark_columns = [
            'upper_lip_x', 'upper_lip_y', 'nose_tip_x', 'nose_tip_y', 'forehead_glabella_x', 'forehead_glabella_y',
            'lower_lip_x',
            'lower_lip_y', 'right_eye_outer_x', 'right_eye_outer_y', 'right_cheek_x', 'right_cheek_y',
            'mouth_right_corner_x',
            'mouth_right_corner_y', 'right_eyebrow_outer_x', 'right_eyebrow_outer_y', 'right_eyebrow_inner_x',
            'right_eyebrow_inner_y', 'right_eye_inner_x', 'right_eye_inner_y', 'chin_x', 'chin_y', 'left_eye_outer_x',
            'left_eye_outer_y', 'left_cheek_x', 'left_cheek_y', 'mouth_left_corner_x', 'mouth_left_corner_y',
            'left_eyebrow_outer_x', 'left_eyebrow_outer_y', 'left_eyebrow_inner_x', 'left_eyebrow_inner_y',
            'left_eye_inner_x','left_eye_inner_y'
        ]

        self.num_landmarks = len(landmark_columns) // 2

        # Extract landmark data
        self.landmarks_data = self.data[landmark_columns].values.astype(np.float32)

        # Handle constant columns by skipping normalization for those columns
        min_vals = self.landmarks_data.min(axis=0)
        max_vals = self.landmarks_data.max(axis=0)
        range_vals = max_vals - min_vals

        range_vals[range_vals == 0] = 1.0  # Prevent division by zero
        self.landmarks_data = (self.landmarks_data - min_vals) / range_vals

        self.landmarks_data = np.nan_to_num(self.landmarks_data, nan=0.0, posinf=0.0, neginf=0.0)

        # Re-check for NaN or Inf after handling
        if np.isnan(self.landmarks_data).any() or np.isinf(self.landmarks_data).any():
            raise ValueError("Landmarks still contain NaN or Inf after normalization")

    def __len__(self):
        return len(self.data)

    def __getitem__(self, idx):
        row = self.data.iloc[idx]

        # Extract normalized landmarks
        landmarks = self.landmarks_data[idx].reshape(-1, 2)

        label = row.iloc[2]

        # Extract and validate action units
        aus = row[4:21].values.astype(np.float32)
        aus = np.clip(aus, 0, 1)

        if np.isnan(aus).any() or np.isinf(aus).any():
            raise ValueError(f"Action units contain NaN or Inf at index {idx}")

        return {'landmarks': torch.tensor(landmarks), 'aus': torch.tensor(aus),
                'row_index': idx, 'label': label}


dataset = FacialExpressionDataset(csv_file='updated_labels_with_landmarks_columns.csv')

train_size = int(0.8 * len(dataset))
val_size = len(dataset) - train_size
train_dataset, val_dataset = random_split(dataset, [train_size, val_size])

# DataLoader for train and validation datasets
train_loader = DataLoader(train_dataset, batch_size=32, shuffle=True)
val_loader = DataLoader(val_dataset, batch_size=32, shuffle=False)

import torch
import torch.nn as nn
import torch.nn.functional as F

class FacialExpressionCNN(nn.Module):
    def __init__(self, num_landmarks=17, num_aus=17):  # 17 landmarks and 17 AUs
        super(FacialExpressionCNN, self).__init__()

        # Number of input channels is 2 (since each landmark has an x and y coordinate)
        self.num_landmarks = num_landmarks
        self.num_aus = num_aus

        # First convolutional layer
        self.conv1 = nn.Conv1d(in_channels=2, out_channels=32, kernel_size=3, stride=1, padding=1)
        self.pool1 = nn.MaxPool1d(kernel_size=2)

        # Second convolutional layer
        self.conv2 = nn.Conv1d(in_channels=32, out_channels=64, kernel_size=3, stride=1, padding=1)
        self.pool2 = nn.MaxPool1d(kernel_size=2)

        # Third convolutional layer
        self.conv3 = nn.Conv1d(in_channels=64, out_channels=128, kernel_size=3, stride=1, padding=1)
        self.pool3 = nn.MaxPool1d(kernel_size=2)

        # Fully connected layers
        self.fc1 = nn.Linear(128 * (num_landmarks // 8), 256)
        self.fc2 = nn.Linear(256, 128)
        self.fc3 = nn.Linear(128, num_aus)  # Output 17 AUs

    def forward(self, x):
        # Permute the input to match the shape for 1D convolution: (batch_size, channels, num_landmarks)
        x = x.permute(0, 2, 1)  # Change shape to [batch_size, 2 (x, y), num_landmarks]

        # Apply first convolution and pooling
        x = self.pool1(F.relu(self.conv1(x)))  # [batch_size, 32, num_landmarks / 2]

        # Apply second convolution and pooling
        x = self.pool2(F.relu(self.conv2(x)))  # [batch_size, 64, num_landmarks / 4]

        # Apply third convolution and pooling
        x = self.pool3(F.relu(self.conv3(x)))  # [batch_size, 128, num_landmarks / 8]

        # Flatten the output for the fully connected layers
        x = x.view(x.size(0), -1)  # Flatten to [batch_size, features]

        # Pass through fully connected layers
        x = F.relu(self.fc1(x))
        x = F.relu(self.fc2(x))
        x = torch.sigmoid(self.fc3(x))  # Sigmoid to ensure output is between 0 and 1 (for binary classification)

        return x


def train_model():
    # Initialize the model (CNN)
    model = FacialExpressionCNN(num_landmarks=17, num_aus=17).to(device)  # 17 landmarks, 17 AUs

    criterion = nn.BCELoss()  # Binary Cross-Entropy Loss for multi-label classification
    optimizer = optim.Adam(model.parameters(), lr=0.01)

    num_epochs = 100
    for epoch in range(num_epochs):
        model.train()
        running_loss = 0.0
        correct_predictions = 0
        total_samples = 0

        for batch in train_loader:
            inputs = batch['landmarks'].to(device)
            labels = batch['aus'].to(device)

            optimizer.zero_grad()  # Reset gradients

            # Forward pass
            outputs = model(inputs)

            # Check output range for debugging
            if torch.isnan(outputs).any() or torch.isinf(outputs).any():
                print("Warning: NaN or Inf in outputs!")

            # Compute loss
            loss = criterion(outputs, labels)

            # Calculate accuracy with 0.5 threshold for binary classification
            predicted = (outputs > 0.5).float()
            #print(predicted)
            correct_predictions += (predicted == labels).sum().item()
            total_samples += labels.numel()

            # Backward pass and optimization
            loss.backward()
            optimizer.step()

            running_loss += loss.item()

        # Accuracy and saving the model
        epoch_loss = running_loss / len(train_loader)
        accuracy = (correct_predictions / total_samples) * 100
        print(f"Epoch [{epoch + 1}/{num_epochs}], Loss: {epoch_loss:.4f}, Accuracy: {accuracy:.2f}%")

        torch.save(model.state_dict(), 'facial_expression_model.pth')
        print("Model saved successfully.")

if __name__ == "__main__":
    train_model()