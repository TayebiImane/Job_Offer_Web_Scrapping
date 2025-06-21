

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

public class Consult2 {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    public Consult2(String username) {
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

        
        String[] ColumnNames = {
        		"site_name","city", "sector", "job_title", "contract_type", "education_level", "diploma_specialization", "experience_level", 
           "language","salary","remote_work"
        };

       
        tableModel = new DefaultTableModel(ColumnNames, 0);
        table = new JTable(tableModel); 

        try {
			loadFromDatabase(getConnection(), tableModel);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 120, 1120, 400); 
        frame.getContentPane().add(scrollPane);  

       
        JButton btnNewButton = new JButton("consult");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Consult2("nom");
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

        
        frame.setVisible(true);
    }
    
	private void loadFromDatabase(Connection conn, DefaultTableModel tableModel) throws SQLException {
        String selectSQL = "SELECT * FROM preprocessed_job_offers";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(selectSQL)) {

              
               while (rs.next()) {
            	   String site_name=rs.getString("site_name");
                   String city = rs.getString("city");
                   String sector = rs.getString("sector");
                   String jobTitle = rs.getString("job_title");
                   String contractType = rs.getString("contract_type");
                   String educationLevel = rs.getString("education_level");
                   String diplomaSpecialization = rs.getString("diploma_specialization");
                   String experienceLevel = rs.getString("experience_level");
                   String language = rs.getString("language");
                   String salary = rs.getString("salary");
                   String remoteWork = rs.getString("remote_work");
                   
                   
                   tableModel.addRow(new Object[]{
                		   site_name, city, sector, jobTitle, contractType, educationLevel,
                       diplomaSpecialization, experienceLevel,language,salary,remoteWork
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
        
    	UIManager.setLookAndFeel( new NimbusLookAndFeel() );
   
        new Consult2("name");
    }
}
