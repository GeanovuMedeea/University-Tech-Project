using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;
using static System.Windows.Forms.VisualStyles.VisualStyleElement;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.StartPanel;

namespace Lab1DBMS
{
    public partial class Form1 : Form
    {
        SqlConnection conn;
        SqlDataAdapter daFlowerData;
        SqlDataAdapter daFlowers;
        DataSet dset;
        BindingSource bsFlowerData;
        BindingSource bsFlowers;

        SqlCommandBuilder cmdBuilder;

        string queryFlowerData;
        string queryFlowers;

        public Form1()
        {
            InitializeComponent();
            FillData();
        }

        void FillData()
        {
            conn = new SqlConnection(getConnectionString());

            queryFlowerData = "SELECT * FROM FlowerData";
            queryFlowers = "Select * FROM Flowers";

            daFlowerData = new SqlDataAdapter(queryFlowerData, conn);
            daFlowers = new SqlDataAdapter(queryFlowers, conn);
            dset = new DataSet();
            daFlowerData.Fill(dset, "FlowerData");
            daFlowers.Fill(dset, "Flowers");

            cmdBuilder = new SqlCommandBuilder(daFlowers);

            dset.Relations.Add("FlowersDataAndTheirData", dset.Tables["FlowerData"].Columns["ftid"], dset.Tables["Flowers"].Columns["ftid"]);

            this.dataGridViewFlowerData.DataSource = dset.Tables["FlowerData"];
            this.dataGridViewFlowers.DataSource = this.dataGridViewFlowerData.DataSource;
            this.dataGridViewFlowers.DataMember = "FlowersDataAndTheirData";

            /*bsFlowerData = new BindingSource();
            bsFlowerData.DataSource = dset.Tables["FlowerData"];
            bsFlowers = new BindingSource(bsFlowerData, "FlowersDataAndTheirData");

            this.dataGridViewFlowerData.DataSource = bsFlowerData;
            this.dataGridViewFlowers.DataSource = bsFlowers;*/

            cmdBuilder.GetUpdateCommand();
            //cmdBuilder.GetDeleteCommand();
            //cmdBuilder.GetInsertCommand();
        }


        string getConnectionString()
        {
            return "Data Source=DESKTOP-T64O1IM\\SQLEXPRESS;Initial Catalog=FlowerShop;Integrated Security=true;";
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            try
            {
                daFlowers.Update(dset, "Flowers");
            }
            catch (Exception) {
                string show = string.Format("Cannot insert null values!");

                MessageBox.Show(show);
            }
        }

        private void addButton_Click(object sender, EventArgs e)
        {
            try
            {
                daFlowers.Update(dset, "Flowers");
            }
            catch (Exception)
            {
                string show = string.Format("Cannot insert null values!");

                MessageBox.Show(show);
            }
        }

        private void deleteButton_Click(Object sender, EventArgs e)
        {
            try
            {
                daFlowers.Update(dset, "Flowers");
            }
            catch (Exception)
            {
                string show = string.Format("Cannot insert null values!");

                MessageBox.Show(show);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {

        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void dataGridViewFlowers_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }
    }
}
