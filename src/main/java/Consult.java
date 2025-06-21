

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class Consult {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    public Consult(String username) {
        // Créer la fenêtre de bienvenue
        frame = new JFrame("Bienvenue");
        frame.getContentPane().setBackground(new Color(240, 240, 240));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1222, 595);
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 128, 128));
        panel.setBounds(0, 0, 1208, 82);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("APPLICATION JAVA-CONSULT");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblNewLabel.setBounds(401, 27, 437, 44);
        panel.add(lblNewLabel);

        // Noms des colonnes
        String[] ColumnNames = {
            "title", "url", "site_name", "publication_date", "application_deadline", "company_address", "company_website", "company_name", "company_description", "job_description",
            "region", "city", "sector", "job_title", "contract_type", "education_level", "diploma_specialization", "experience_level", "profile", "personality_traits", "required_skills",
            "soft_skills", "recommended_skills", "language", "language_level", "salary", "benefits", "remote_work"
        };

        // Initialisation du modèle de table avec les colonnes
        tableModel = new DefaultTableModel(ColumnNames, 0);
        table = new JTable(tableModel);  // Déclaration correcte de la table

        try {
			loadFromDatabase(getConnection(), tableModel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Ajouter la table dans un JScrollPane pour la visibilité
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 120, 1120, 400);  // Définir une position et une taille pour le JScrollPane
        frame.getContentPane().add(scrollPane);  // Ajouter le JScrollPane dans le frame

        // Ajouter les boutons
        JButton btnNewButton = new JButton("consult");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Consult("nom");
            }
        });
        btnNewButton.setBounds(10, 93, 89, 23);
        frame.getContentPane().add(btnNewButton);

        JButton btnScrapp = new JButton("Scrapp");
        btnScrapp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Scrapp();
            }
        });
        btnScrapp.setBounds(128, 93, 89, 23);
        frame.getContentPane().add(btnScrapp);

        JButton btnNewButton_1_1 = new JButton("Model");
        btnNewButton_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Model("mod");
            }
        });
        btnNewButton_1_1.setBounds(247, 93, 89, 23);
        frame.getContentPane().add(btnNewButton_1_1);

        JButton btnNewButton_1_2 = new JButton("Visualize");
        btnNewButton_1_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Model("Visualize");
            }
        });
        btnNewButton_1_2.setBounds(390, 93, 89, 23);
        frame.getContentPane().add(btnNewButton_1_2);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }
    
	private void loadFromDatabase(Connection conn, DefaultTableModel tableModel) throws SQLException {
        String selectSQL = "SELECT * FROM job_offers";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(selectSQL)) {

               // Parcours des résultats et ajout dans le modèle de table
               while (rs.next()) {
                   // Récupérer les données de chaque colonne
                   String title = rs.getString("title");
                   String url = rs.getString("url");
                   String siteName = rs.getString("site_name");
                   String publicationDate = rs.getString("publication_date");
                   String applicationDeadline = rs.getString("application_deadline");
                   String companyAddress = rs.getString("company_address");
                   String companyWebsite = rs.getString("company_website");
                   String companyName = rs.getString("company_name");
                   String companyDescription = rs.getString("company_description");
                   String jobDescription = rs.getString("job_description");
                   String region = rs.getString("region");
                   String city = rs.getString("city");
                   String sector = rs.getString("sector");
                   String jobTitle = rs.getString("job_title");
                   String contractType = rs.getString("contract_type");
                   String educationLevel = rs.getString("education_level");
                   String diplomaSpecialization = rs.getString("diploma_specialization");
                   String experienceLevel = rs.getString("experience_level");
                   String profile = rs.getString("profile");
                   String personalityTraits = rs.getString("personality_traits");
                   String requiredSkills = rs.getString("required_skills");
                   String softSkills = rs.getString("soft_skills");
                   String recommendedSkills = rs.getString("recommended_skills");
                   String language = rs.getString("language");
                   String languageLevel = rs.getString("language_level");
                   String salary = rs.getString("salary");
                   String benefits = rs.getString("benefits");
                   String remoteWork = rs.getString("remote_work");

                   // Ajouter les données dans le modèle de table
                   tableModel.addRow(new Object[]{
                       title, url, siteName, publicationDate, applicationDeadline,
                       companyAddress, companyWebsite, companyName, companyDescription, jobDescription,
                       region, city, sector, jobTitle, contractType, educationLevel,
                       diplomaSpecialization, experienceLevel, profile, personalityTraits, requiredSkills,
                       softSkills, recommendedSkills, language, languageLevel, salary, benefits, remoteWork
                   });
             }
               
    }
}
	private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:4000/annonces_emploi";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        // Lancer l'application avec un nom d'utilisateur fictif
    	UIManager.setLookAndFeel( new NimbusLookAndFeel() );
   
        new Consult("name");
    }
}
