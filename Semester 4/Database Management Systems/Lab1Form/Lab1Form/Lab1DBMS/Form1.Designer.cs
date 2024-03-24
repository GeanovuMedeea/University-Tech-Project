namespace Lab1DBMS
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.dataGridViewFlowerData = new System.Windows.Forms.DataGridView();
            this.dataGridViewFlowers = new System.Windows.Forms.DataGridView();
            this.buttonUpdate = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.addButton = new System.Windows.Forms.Button();
            this.deleteButton = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewFlowerData)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewFlowers)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridViewFlowerData
            // 
            this.dataGridViewFlowerData.BackgroundColor = System.Drawing.Color.LavenderBlush;
            this.dataGridViewFlowerData.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewFlowerData.Location = new System.Drawing.Point(47, 34);
            this.dataGridViewFlowerData.Name = "dataGridViewFlowerData";
            this.dataGridViewFlowerData.RowHeadersWidth = 51;
            this.dataGridViewFlowerData.RowTemplate.Height = 24;
            this.dataGridViewFlowerData.Size = new System.Drawing.Size(611, 264);
            this.dataGridViewFlowerData.TabIndex = 0;
            this.dataGridViewFlowerData.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellContentClick);
            // 
            // dataGridViewFlowers
            // 
            this.dataGridViewFlowers.BackgroundColor = System.Drawing.Color.LavenderBlush;
            this.dataGridViewFlowers.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewFlowers.Location = new System.Drawing.Point(47, 330);
            this.dataGridViewFlowers.Name = "dataGridViewFlowers";
            this.dataGridViewFlowers.RowHeadersWidth = 51;
            this.dataGridViewFlowers.RowTemplate.Height = 24;
            this.dataGridViewFlowers.Size = new System.Drawing.Size(611, 163);
            this.dataGridViewFlowers.TabIndex = 1;
            this.dataGridViewFlowers.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridViewFlowers_CellContentClick);
            // 
            // buttonUpdate
            // 
            this.buttonUpdate.BackColor = System.Drawing.Color.Violet;
            this.buttonUpdate.Font = new System.Drawing.Font("Arial", 9F);
            this.buttonUpdate.Location = new System.Drawing.Point(695, 203);
            this.buttonUpdate.Name = "buttonUpdate";
            this.buttonUpdate.Size = new System.Drawing.Size(75, 34);
            this.buttonUpdate.TabIndex = 2;
            this.buttonUpdate.Text = "Update";
            this.buttonUpdate.UseVisualStyleBackColor = false;
            this.buttonUpdate.Click += new System.EventHandler(this.buttonUpdate_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Times New Roman", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(47, 12);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(101, 21);
            this.label1.TabIndex = 3;
            this.label1.Text = "Flower Data";
            this.label1.Click += new System.EventHandler(this.label1_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Times New Roman", 11F);
            this.label2.Location = new System.Drawing.Point(47, 306);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(70, 21);
            this.label2.TabIndex = 4;
            this.label2.Text = "Flowers";
            this.label2.Click += new System.EventHandler(this.label2_Click);
            // 
            // addButton
            // 
            this.addButton.BackColor = System.Drawing.Color.Violet;
            this.addButton.Font = new System.Drawing.Font("Arial", 9F);
            this.addButton.Location = new System.Drawing.Point(695, 266);
            this.addButton.Name = "addButton";
            this.addButton.Size = new System.Drawing.Size(75, 32);
            this.addButton.TabIndex = 5;
            this.addButton.Text = "Add";
            this.addButton.UseVisualStyleBackColor = false;
            this.addButton.Click += new System.EventHandler(this.button1_Click);
            // 
            // deleteButton
            // 
            this.deleteButton.BackColor = System.Drawing.Color.Violet;
            this.deleteButton.Font = new System.Drawing.Font("Arial", 9F);
            this.deleteButton.Location = new System.Drawing.Point(695, 330);
            this.deleteButton.Name = "deleteButton";
            this.deleteButton.Size = new System.Drawing.Size(75, 34);
            this.deleteButton.TabIndex = 6;
            this.deleteButton.Text = "Delete";
            this.deleteButton.UseVisualStyleBackColor = false;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.MistyRose;
            this.ClientSize = new System.Drawing.Size(800, 521);
            this.Controls.Add(this.deleteButton);
            this.Controls.Add(this.addButton);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.buttonUpdate);
            this.Controls.Add(this.dataGridViewFlowers);
            this.Controls.Add(this.dataGridViewFlowerData);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewFlowerData)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewFlowers)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridViewFlowerData;
        private System.Windows.Forms.DataGridView dataGridViewFlowers;
        private System.Windows.Forms.Button buttonUpdate;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button addButton;
        private System.Windows.Forms.Button deleteButton;
    }
}

