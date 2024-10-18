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

namespace Lab1Form
{
    public partial class Form1 : Form
    {
        SqlConnection conn;
        SqlDataAdapter daFlowers;
        SqlDataAdapter daFlowerData;
        DataSet dset;
        BindingSource bsFlowers;
        BindingSource bsFlowerData;
        SqlCommandBuilder cmdBuilder;
        string queryFlowers;
        string queryFlowerData;

        public Form1()
        {
            InitializeComponent();
            FillData();
        }

        void FillData()
        {
            conn = new SqlConnection(getConnectionString());
            queryFlowerData = "SELECT * FROM FlowerData";
            queryFlowers = "SELECT * FROM Flowers";

            daFlowerData = new SqlDataAdapter(queryFlowerData, conn);
            daFlowers = new SqlDataAdapter(queryFlowers, conn);
            dset = new DataSet();
            daFlowerData.Fill(dset, "FlowerData");
            daFlowers.Fill(dset, "Flowers");

            cmdBuilder = new SqlCommandBuilder(daFlowerData);
            dset.Relations.Add("FlowersAndData", dset.Tables["FlowerData"].Columns["ftid"], dset.Tables["Flowers"].Columns["ftid"]);

            this.dataGridViewFlowerData.DataSource = dset.Tables["FlowerData"];
            //this.dataGridViewOrders.DataSource = this.dataGridViewClients.DataSource;
            this.dataGridViewFlowers.DataSource = dset.Tables["Flowers"].ChildRelations["FlowersAndData"];

            this.dataGridViewFlowers.DataSource = "FlowersAndData";
        }

        string getConnectionString() { return "Data Source=DESKTOP-T64O1IM\\SQLEXPRESS;Initial Catalog=FlowerShop;Integrated Security=true;"; }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }
    }
}
