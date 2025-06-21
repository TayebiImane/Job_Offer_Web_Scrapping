

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SalaryExperienceLineChart {
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
	SalaryExperienceLineChart (){
		   // Connexion à la base de données
        Connection conn =  SalaryExperienceLineChart.connect();

        // Création du dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            // Requête SQL pour récupérer les salaires en fonction du niveau d'expérience
          //  String sql = "SELECT experience_level, AVG(salary) AS avg_salary FROM job_data GROUP BY experience_level ORDER BY experience_level";
        	String sql = "SELECT contract_type, AVG(CAST(salary AS DECIMAL(10,2))) AS avg_salary " +
                    "FROM preprocessed_job_offers " +
                    "WHERE salary REGEXP '^[0-9]+(\\.[0-9]+)?$'and contract_type REGEXP '^[0-9]+(\\\\.[0-9]+)?$' " +  // Filtrer les valeurs numériques
                    "GROUP BY contract_type";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Remplir le dataset avec les données de la base
            while (rs.next()) {
                double experienceLevel = rs.getDouble("contract_type");
                double avgSalary = rs.getDouble("avg_salary");
                dataset.addValue(avgSalary, "Salaire moyen", String.valueOf(experienceLevel));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Créer le graphique linéaire
        JFreeChart lineChart = ChartFactory.createLineChart(
            "Courbe des salaires en fonction de l'expérience", // Titre
            "Niveau d'education", // Axe des X
            "Salaire moyen", // Axe des Y
            dataset // Dataset
        );

        // Afficher le graphique dans une fenêtre
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        javax.swing.JFrame frame = new javax.swing.JFrame();
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel);
        frame.pack();
        frame.setVisible(true);
	}
    public static void main(String[] args) {
     new SalaryExperienceLineChart ();
     
    }
}

