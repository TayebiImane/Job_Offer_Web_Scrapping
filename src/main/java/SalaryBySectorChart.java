

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import weka.core.converters.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SalaryBySectorChart {
	public static Connection connect() {
        try {
            String url = "jdbc:mysql://localhost:4000/annonces_emploi";
            String user = "root";
            String password = "";
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	SalaryBySectorChart() {
		   // Connexion à la base de données
        Connection conn =  SalaryBySectorChart.connect();
        
        // Création du dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        try {
            // Requête SQL pour récupérer les salaires moyens par secteur
        	String sql = "SELECT sector, AVG(CAST(salary AS DECIMAL(10,2))) AS avg_salary " +
                    "FROM preprocessed_job_offers " +
                    "WHERE salary REGEXP '^[0-9]+(\\.[0-9]+)?$' " + 
                    "GROUP BY sector";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            // Remplir le dataset avec les résultats de la requête
            while (rs.next()) {
                String sector = rs.getString("sector");
                double avgSalary = rs.getDouble("avg_salary");
                dataset.addValue(avgSalary, "Salaires", sector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Créer le graphique
        JFreeChart chart = ChartFactory.createBarChart(
            "Salaires par secteur",
            "Secteur", 
            "Salaire moyen", 
            dataset 
        );
        
        // Afficher le graphique
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        javax.swing.JFrame frame = new javax.swing.JFrame();
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel);
        frame.pack();
        frame.setVisible(true);
	}
    public static void main(String[] args) {
     new SalaryBySectorChart();
    }
}
