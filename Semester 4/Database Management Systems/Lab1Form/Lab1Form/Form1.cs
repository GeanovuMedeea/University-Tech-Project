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
        SqlDataAdapter daOrders;
        SqlDataAdapter daClientPublic;
        DataSet dset;
        BindingSource bsOrders;
        BindingSource bsClientPublic;
        SqlCommandBuilder cmdBuilder;
        string queryOrders;
        string queryClientsPublic;

        public Form1()
        {
            InitializeComponent();
            FillData();
        }

        void FillData()
        {
            conn = new SqlConnection(getConnectionString());
            queryOrders = "SELECT * FROM Orders";
            queryClientsPublic = "SELECT * FROM ClientPublic";

            daOrders = new SqlDataAdapter(queryOrders, conn);
            daClientPublic = new SqlDataAdapter(queryClientsPublic, conn);
            dset = new DataSet();
            daOrders.Fill(dset, "Orders");
            daClientPublic.Fill(dset, "ClientPublic");

            cmdBuilder = new SqlCommandBuilder(daOrders);
            dset.Relations.Add("ClientPublicOrders", dset.Tables["ClientPublic"].Columns["cid"], dset.Tables["Orders"].Columns["cid"]);

            this.dataGridViewClients.DataSource = dset.Tables["ClientPublic"];
            //this.dataGridViewOrders.DataSource = this.dataGridViewClients.DataSource;
            this.dataGridViewOrders.DataSource = dset.Tables["ClientPublic"].ChildRelations["ClientPublicOrders"];

            this.dataGridViewOrders.DataSource = "ClientPublicOrders";
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
