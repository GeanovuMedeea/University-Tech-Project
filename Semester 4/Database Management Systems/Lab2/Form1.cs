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
using System.Configuration;
using System.Collections.Specialized;
using System.Xml;
using System.Security.Cryptography;

namespace DBSMLab2
{
    public partial class Form1 : Form
    {
        public static string server = ConfigurationManager.AppSettings.Get("server");
        public static string database = ConfigurationManager.AppSettings.Get("database");
        public static string parentTable = ConfigurationManager.AppSettings.Get("parentTable");
        public static string childTable = ConfigurationManager.AppSettings.Get("childTable");
        public static string parentPrimaryKey = ConfigurationManager.AppSettings.Get("parentPrimaryKey");
        public static string childForeignKey = ConfigurationManager.AppSettings.Get("childForeignKey");
        // SqlConnection sqlConnection = new sqlConnection(@"Data Source=DESKTOP-VB4PGBM\SQLEXPRESS; Initial Catalog=Lab2_CS_DGV; Integrated Security=True");
        SqlConnection sqlConnection = new SqlConnection("Data Source=" + server + ";Database=" + database + ";Integrated Security=SSPI");
        DataSet dataSet = new DataSet();
        SqlDataAdapter parentDataAdapter = new SqlDataAdapter();
        SqlDataAdapter childDataAdapter = new SqlDataAdapter();
        BindingSource parentBindingSource = new BindingSource();
        BindingSource childBindingSource = new BindingSource();
        SqlCommandBuilder parentBuilder = new SqlCommandBuilder();
        SqlCommandBuilder childBuilder = new SqlCommandBuilder();
        public Form1()
        {
            InitializeComponent();
        }
        public void populate()
        {
            parentDataAdapter = new SqlDataAdapter("SELECT * FROM " + parentTable, sqlConnection);
            childDataAdapter = new SqlDataAdapter("SELECT * FROM " + childTable, sqlConnection);
            SqlCommandBuilder parentBuilder = new SqlCommandBuilder(parentDataAdapter);
            SqlCommandBuilder childBuilder = new SqlCommandBuilder(childDataAdapter);
            // dataSet.Clear();
            parentDataAdapter.Fill(dataSet, parentTable);
            childDataAdapter.Fill(dataSet, childTable);
            DataColumn parentPK = dataSet.Tables[parentTable].Columns[parentPrimaryKey];
            DataColumn childFK = dataSet.Tables[childTable].Columns[childForeignKey];
            DataRelation relation = new DataRelation("fk_parent_child", parentPK, childFK);
            dataSet.Relations.Add(relation);
            parentBindingSource.DataSource = dataSet;
            parentBindingSource.DataMember = parentTable;
            childBindingSource.DataSource = parentBindingSource;
            childBindingSource.DataMember = "fk_parent_child";
            parentDataGridView.DataSource = parentBindingSource;
            childDataGridView.DataSource = childBindingSource;
        }
        private void Form1_Load(object sender, EventArgs e)
        {
 
        }
        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                parentDataAdapter.Update(dataSet, parentTable);
                childDataAdapter.Update(dataSet, childTable);
                MessageBox.Show("Updated with succes!");
                // populate();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                sqlConnection.Close();
            }
        }

        private void Form1_Load_1(object sender, EventArgs e)
        {
            populate();
        }

        private void childDataGridView_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void buttonAdd_Click(object sender, EventArgs e)
        {
            //string ChildTableName =ConfigurationManager.AppSettings["ChildTable"];
            string ChildColumnNames =ConfigurationManager.AppSettings["ChildColumnNames"];
            //string ColumnNamesInsertParameters = ConfigurationManager.AppSettings["ColumnNamesInsertParameters"];
            List<string> ColumnNamesList = new List<string>(ConfigurationManager.AppSettings["ChildColumnNames"].Split(','));
            //SqlCommand cmd = new SqlCommand("INSERT INTO " + ChildTableName + " (" + ChildColumnNames + ") VALUES(" + ColumnNamesInsertParameters + ")", sqlConnection);
            SqlCommand cmd = new SqlCommand(ConfigurationManager.AppSettings["AddQuery"], sqlConnection);

            try
            {
                foreach (string column in ColumnNamesList)
                {
                    TextBox textBox = (TextBox)panel1.Controls[column];
                    cmd.Parameters.AddWithValue("@" + column, textBox.Text);
                }
            }
            catch ( Exception ex) {  MessageBox.Show(ex.Message); }
            sqlConnection.Open();
            try
            {
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex) { MessageBox.Show(ex.ToString()); }
            //childDataAdapter.Fill(dataSet);
            //sqlConnection.Close();
            parentDataGridView.Refresh();
            childDataGridView.Refresh();
            dataSet.Clear();
            parentDataAdapter.Fill(dataSet, parentTable);
            childDataAdapter.Fill(dataSet, childTable);
            parentDataGridView.Refresh();
            childDataGridView.Refresh();
            sqlConnection.Close();
            MessageBox.Show("Record added successfully!");
        }

        private void panel1_Paint(object sender, PaintEventArgs e)
        {

        }

        private void textboxButton_Click(object sender, EventArgs e)
        {
            panel1.Controls.Clear();
            List<string> columnNamesList = new List<string>(ConfigurationManager.AppSettings["ChildColumnNames"].Split(','));

            int yPos = 20;
            foreach (string column in columnNamesList)
            {
                Label label = new Label();
                label.Text = column;
                label.AutoSize = true;
                label.Location = new Point(10, yPos);

                TextBox textBox = new TextBox();
                textBox.Name = column;
                textBox.Location = new Point(120, yPos);

                panel1.Controls.Add(label);
                panel1.Controls.Add(textBox);

                yPos += 30;
            }
        }

        private void button1_Click_1(object sender, EventArgs e)
        {
            string ChildColumnNames = ConfigurationManager.AppSettings["ChildColumnNames"];
            List<string> ColumnNamesList = new List<string>(ConfigurationManager.AppSettings["ChildColumnNames"].Split(','));
            SqlCommand cmd = new SqlCommand(ConfigurationManager.AppSettings["UpdateQuery"], sqlConnection);
            try
            {
                foreach (string column in ColumnNamesList)
                {
                    TextBox textBox = (TextBox)panel1.Controls[column];
                    cmd.Parameters.AddWithValue("@" + column, textBox.Text);
                }
            }catch (Exception ex) { MessageBox.Show(ex.Message); }
            sqlConnection.Open();
            try
            {
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex) { MessageBox.Show(ex.ToString()); }
            parentDataGridView.Refresh();
            childDataGridView.Refresh();
            dataSet.Clear();
            parentDataAdapter.Fill(dataSet, parentTable);
            childDataAdapter.Fill(dataSet, childTable);
            parentDataGridView.Refresh();
            childDataGridView.Refresh();
            sqlConnection.Close();
            MessageBox.Show("Record updated successfully!");
        }

        private void buttonDelete_Click(object sender, EventArgs e)
        {
            string ChildColumnNames = ConfigurationManager.AppSettings["ChildColumnNames"];
            List<string> ColumnNamesList = new List<string>(ConfigurationManager.AppSettings["ChildColumnNames"].Split(','));
            SqlCommand cmd = new SqlCommand(ConfigurationManager.AppSettings["DeleteQuery"], sqlConnection);

            try
            {
                TextBox textBox = (TextBox)panel1.Controls[ColumnNamesList[0]];
                cmd.Parameters.AddWithValue("@" + ColumnNamesList[0], textBox.Text);
            }catch (Exception ex) { MessageBox.Show(ex.Message); }
            sqlConnection.Open();
            try
            {
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex) { MessageBox.Show(ex.ToString()); }
            parentDataGridView.Refresh();
            childDataGridView.Refresh();
            dataSet.Clear();
            parentDataAdapter.Fill(dataSet, parentTable);
            childDataAdapter.Fill(dataSet, childTable);
            parentDataGridView.Refresh();
            childDataGridView.Refresh();
            sqlConnection.Close();
            MessageBox.Show("Record deleted successfully!");
        }
    }
}