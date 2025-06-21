

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SalaryByContractTypeChart {
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
	SalaryByContractTypeChart()
	{
		
		 Connection conn = SalaryByContractTypeChart.connect();
	        
	        // Créer un dataset pour le pie chart
	        DefaultPieDataset dataset = new DefaultPieDataset();
	        
	        try {
	            
	        	String sql = "SELECT contract_type, AVG(CAST(salary AS DECIMAL(10,2))) AS avg_salary " +
	                    "FROM preprocessed_job_offers " +  
	                    "WHERE salary REGEXP '^[0-9]+(\\.[0-9]+)?$' " +  
	                    "GROUP BY contract_type";


	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(sql);
	            
	            // Remplir le dataset avec les résultats de la requête
	            while (rs.next()) {
	                String contractType = rs.getString("contract_type");
	                double avgSalary = rs.getDouble("avg_salary");
	                dataset.setValue(contractType, avgSalary);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        // Créer le graphique
	        JFreeChart chart = ChartFactory.createPieChart(
	            "Répartition des salaires par type de contrat", 
	            dataset, 
	            true, 
	            true, 
	            false 
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
        // Connexion à la base de données
         new SalaryByContractTypeChart();
    } 
}
