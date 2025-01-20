import subprocess
import os
import pandas as pd
import json
import numpy as np
import matplotlib.pyplot as plt
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay

# Function to extract Action Units using OpenFace
def extract_action_units(image_path):
    openface_path = r"/mnt/c/Users/geano/PycharmProjects/AffCompProject/Openface/build/bin/FeatureExtraction"

    output_dir = r"/mnt/c/Users/geano/PycharmProjects/AffCompProject/openface_output/"
    os.makedirs(output_dir, exist_ok=True)

    result_file = os.path.join(output_dir, "results.csv")
    command = [
        openface_path,  # Path to FeatureExtraction executable
        "-f", image_path,  # Input image
        "-aus",  # Enable AU extraction
        "-out_dir", output_dir  # Specify the output directory
    ]

    result = subprocess.run(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    #print("STDOUT:", result.stdout.decode())
    #print("STDERR:", result.stderr.decode())

    image_name = os.path.basename(image_path).split('.')[0]
    per_image_csv = os.path.join(output_dir, f"{image_name}.csv")

    if os.path.exists(per_image_csv):
        with open(per_image_csv, "r") as file:
            lines = file.readlines()
            # Extract the Action Units from the first row (header is the first line)
            action_units = lines[1].strip().split(",")[5:]  # AU data starts from the 6th column (index 5)

        return [float(x) for x in action_units]
    else:
        print(f"Error: No AU data found for image: {image_path}")
        return []


csv_file = 'affectnet/' + "labels.csv"

df = pd.read_csv(csv_file)

au_columns = [
    "AU01_r", "AU02_r", "AU04_r", "AU05_r", "AU06_r", "AU07_r", "AU09_r", "AU10_r", "AU12_r", "AU14_r",
    "AU15_r", "AU17_r", "AU20_r", "AU23_r", "AU25_r", "AU26_r", "AU45_r",
    "AU01_c", "AU02_c", "AU04_c", "AU05_c", "AU06_c", "AU07_c", "AU09_c", "AU10_c", "AU12_c", "AU14_c",
    "AU15_c", "AU17_c", "AU20_c", "AU23_c", "AU25_c", "AU26_c", "AU28_c", "AU45_c"
]

grouped_df = df.groupby('label')  # label is the emotion type

selected_images = []

# For each category, randomly select 100 images
for category, group in grouped_df:
    selected_group = group.sample(n=100, random_state=42)
    selected_images.append(selected_group)

selected_images_df = pd.concat(selected_images)

updated_data = []
for index, row in selected_images_df.iterrows():
    image_path = 'affectnet/' + row['pth']
    image_path = os.path.abspath(image_path)
    #print(image_path)

    action_units = extract_action_units(image_path)

    # Ensure the action_units has 34 values (for AU01_r to AU45_c) !!!
    if len(action_units) == 35:
        updated_row = row.to_dict()
        for i, au in enumerate(action_units):
            updated_row[au_columns[i]] = au

        #print(updated_row)
        updated_data.append(updated_row)
    else:
        print(f"Error: Unexpected number of AUs for image {image_path}. Expected 34, got {len(action_units)}.")

updated_df = pd.DataFrame(updated_data)

updated_df.to_csv("updated_labels_random_100_images_per_category.csv", index=False)

print("Action Units added to labels.csv for random 100 images per category successfully!")
