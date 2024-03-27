namespace DBSMLab2
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
            this.buttonUpdate = new System.Windows.Forms.Button();
            this.childDataGridView = new System.Windows.Forms.DataGridView();
            this.parentDataGridView = new System.Windows.Forms.DataGridView();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.buttonAdd = new System.Windows.Forms.Button();
            this.buttonDelete = new System.Windows.Forms.Button();
            this.panel1 = new System.Windows.Forms.Panel();
            this.textboxButton = new System.Windows.Forms.Button();
            this.normalUpdateButton = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.childDataGridView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.parentDataGridView)).BeginInit();
            this.SuspendLayout();
            // 
            // buttonUpdate
            // 
            this.buttonUpdate.BackColor = System.Drawing.Color.DarkViolet;
            this.buttonUpdate.Font = new System.Drawing.Font("Arial", 10F);
            this.buttonUpdate.ForeColor = System.Drawing.SystemColors.ButtonHighlight;
            this.buttonUpdate.Location = new System.Drawing.Point(1051, 53);
            this.buttonUpdate.Name = "buttonUpdate";
            this.buttonUpdate.Size = new System.Drawing.Size(93, 55);
            this.buttonUpdate.TabIndex = 0;
            this.buttonUpdate.Text = "Fancy Update";
            this.buttonUpdate.UseVisualStyleBackColor = false;
            this.buttonUpdate.Click += new System.EventHandler(this.button1_Click);
            // 
            // childDataGridView
            // 
            this.childDataGridView.BackgroundColor = System.Drawing.Color.MistyRose;
            this.childDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.childDataGridView.Location = new System.Drawing.Point(44, 255);
            this.childDataGridView.Name = "childDataGridView";
            this.childDataGridView.RowHeadersWidth = 51;
            this.childDataGridView.RowTemplate.Height = 24;
            this.childDataGridView.Size = new System.Drawing.Size(573, 152);
            this.childDataGridView.TabIndex = 1;
            this.childDataGridView.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.childDataGridView_CellContentClick);
            // 
            // parentDataGridView
            // 
            this.parentDataGridView.BackgroundColor = System.Drawing.Color.Thistle;
            this.parentDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.parentDataGridView.Location = new System.Drawing.Point(44, 63);
            this.parentDataGridView.Name = "parentDataGridView";
            this.parentDataGridView.RowHeadersWidth = 51;
            this.parentDataGridView.RowTemplate.Height = 24;
            this.parentDataGridView.Size = new System.Drawing.Size(573, 152);
            this.parentDataGridView.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Times New Roman", 13F);
            this.label1.Location = new System.Drawing.Point(39, 32);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(122, 25);
            this.label1.TabIndex = 3;
            this.label1.Text = "Parent Table";
            this.label1.Click += new System.EventHandler(this.label1_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Times New Roman", 13F);
            this.label2.Location = new System.Drawing.Point(39, 224);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(115, 25);
            this.label2.TabIndex = 4;
            this.label2.Text = "Child Table";
            // 
            // buttonAdd
            // 
            this.buttonAdd.BackColor = System.Drawing.Color.DarkViolet;
            this.buttonAdd.Font = new System.Drawing.Font("Arial", 11F);
            this.buttonAdd.ForeColor = System.Drawing.SystemColors.ButtonHighlight;
            this.buttonAdd.Location = new System.Drawing.Point(1051, 214);
            this.buttonAdd.Name = "buttonAdd";
            this.buttonAdd.Size = new System.Drawing.Size(93, 47);
            this.buttonAdd.TabIndex = 5;
            this.buttonAdd.Text = "Add";
            this.buttonAdd.UseVisualStyleBackColor = false;
            this.buttonAdd.Click += new System.EventHandler(this.buttonAdd_Click);
            // 
            // buttonDelete
            // 
            this.buttonDelete.BackColor = System.Drawing.Color.DarkViolet;
            this.buttonDelete.Font = new System.Drawing.Font("Arial", 11F);
            this.buttonDelete.ForeColor = System.Drawing.SystemColors.ButtonHighlight;
            this.buttonDelete.Location = new System.Drawing.Point(1051, 286);
            this.buttonDelete.Name = "buttonDelete";
            this.buttonDelete.Size = new System.Drawing.Size(93, 47);
            this.buttonDelete.TabIndex = 6;
            this.buttonDelete.Text = "Delete";
            this.buttonDelete.UseVisualStyleBackColor = false;
            this.buttonDelete.Click += new System.EventHandler(this.buttonDelete_Click);
            // 
            // panel1
            // 
            this.panel1.Location = new System.Drawing.Point(661, 63);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(339, 344);
            this.panel1.TabIndex = 7;
            this.panel1.Paint += new System.Windows.Forms.PaintEventHandler(this.panel1_Paint);
            // 
            // textboxButton
            // 
            this.textboxButton.BackColor = System.Drawing.Color.DarkViolet;
            this.textboxButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F);
            this.textboxButton.ForeColor = System.Drawing.SystemColors.ButtonHighlight;
            this.textboxButton.Location = new System.Drawing.Point(1051, 360);
            this.textboxButton.Name = "textboxButton";
            this.textboxButton.Size = new System.Drawing.Size(93, 47);
            this.textboxButton.TabIndex = 8;
            this.textboxButton.Text = "Generate";
            this.textboxButton.UseVisualStyleBackColor = false;
            this.textboxButton.Click += new System.EventHandler(this.textboxButton_Click);
            // 
            // normalUpdateButton
            // 
            this.normalUpdateButton.BackColor = System.Drawing.Color.DarkViolet;
            this.normalUpdateButton.Font = new System.Drawing.Font("Arial", 11F);
            this.normalUpdateButton.ForeColor = System.Drawing.SystemColors.ButtonHighlight;
            this.normalUpdateButton.Location = new System.Drawing.Point(1051, 139);
            this.normalUpdateButton.Name = "normalUpdateButton";
            this.normalUpdateButton.Size = new System.Drawing.Size(93, 47);
            this.normalUpdateButton.TabIndex = 9;
            this.normalUpdateButton.Text = "Update";
            this.normalUpdateButton.UseVisualStyleBackColor = false;
            this.normalUpdateButton.Click += new System.EventHandler(this.button1_Click_1);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.LavenderBlush;
            this.ClientSize = new System.Drawing.Size(1251, 450);
            this.Controls.Add(this.normalUpdateButton);
            this.Controls.Add(this.textboxButton);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.buttonDelete);
            this.Controls.Add(this.buttonAdd);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.parentDataGridView);
            this.Controls.Add(this.childDataGridView);
            this.Controls.Add(this.buttonUpdate);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load_1);
            ((System.ComponentModel.ISupportInitialize)(this.childDataGridView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.parentDataGridView)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button buttonUpdate;
        private System.Windows.Forms.DataGridView childDataGridView;
        private System.Windows.Forms.DataGridView parentDataGridView;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button buttonAdd;
        private System.Windows.Forms.Button buttonDelete;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Button textboxButton;
        private System.Windows.Forms.Button normalUpdateButton;
    }
}