
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Visualize {
	  private JFrame frame;
	    private JLabel welcomeLabel;
	    private JTextArea textArea;
	    
	    public Visualize(String username) {
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
	        
	        JLabel lblNewLabel = new JLabel("APPLICATION JAVA-Visualize");
	        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
	        lblNewLabel.setBounds(401, 27, 480, 44);
	        panel.add(lblNewLabel);
	        
	        JButton btnNewButton = new JButton("consult");
	        btnNewButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		 
	       		 frame.dispose(); // Fermer la fenêtre de connexion
	       		 new Consult("name"); 
	        	}
	        });
	        btnNewButton.setBounds(10, 93, 89, 23);
	        frame.getContentPane().add(btnNewButton);
	        
	        JButton btnScrapp = new JButton("Scrapp");
	        btnScrapp.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		 frame.dispose(); // Fermer la fenêtre de connexion
		       		 new Scrapp();
	        	}
	        });
	        btnScrapp.setBounds(128, 93, 89, 23);
	        frame.getContentPane().add(btnScrapp);
	        
	        JButton btnNewButton_1_1 = new JButton("Model");
	        btnNewButton_1_1.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		 frame.dispose(); // Fermer la fenêtre de connexion
		       		 new Model("mod");
	        	}
	        });
	        btnNewButton_1_1.setBounds(247, 93, 89, 23);
	        frame.getContentPane().add(btnNewButton_1_1);
	        
	        JButton btnNewButton_1_2 = new JButton("Visualize");
	        btnNewButton_1_2.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		 frame.dispose(); // Fermer la fenêtre de connexion
		       		 new Visualize("vu");
	        	}
	        });
	        btnNewButton_1_2.setBounds(390, 93, 89, 23);
	        frame.getContentPane().add(btnNewButton_1_2);
	        
	        JPanel panel_1 = new JPanel();
	        panel_1.setBackground(new Color(255, 255, 255));
	        panel_1.setBounds(0, 130, 593, 428);
	        frame.getContentPane().add(panel_1);
	        panel_1.setLayout(null);
	        
	        JButton btnNewButton_1 = new JButton("Explore Data");
	        btnNewButton_1.setBounds(184, 84, 160, 41);
	        panel_1.add(btnNewButton_1);
	        
	        JButton btnNewButton_1_3 = new JButton("salary by experience level");
	        btnNewButton_1_3.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	        		 frame.dispose();
	                 new SalaryByContractTypeChart();
	        	}
	        });
	        btnNewButton_1_3.setBounds(154, 158, 243, 41);
	        panel_1.add(btnNewButton_1_3);
	        
	        JButton btnNewButton_1_4 = new JButton("Salary by experience level");
	        btnNewButton_1_4.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		frame.dispose();
	        		 new SalaryExperienceLineChart ();
	        	}
	        });
	        btnNewButton_1_4.setBounds(148, 312, 254, 41);
	        panel_1.add(btnNewButton_1_4);
	        
	        JButton btnNewButton_1_4_1 = new JButton("Salary by contra type");
	        btnNewButton_1_4_1.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		frame.dispose();
	        		 new SalaryBySectorChart();
	        	}
	        });
	        btnNewButton_1_4_1.setBounds(148, 242, 254, 41);
	        panel_1.add(btnNewButton_1_4_1);
	        
	        JPanel panel_1_1 = new JPanel();
	        panel_1_1.setBackground(Color.DARK_GRAY);
	        panel_1_1.setBounds(591, 130, 617, 428);
	        frame.getContentPane().add(panel_1_1);
	        panel_1_1.setLayout(null);
	        
	        JButton btnNewButton_1_5 = new JButton("Explore Data");
	        btnNewButton_1_5.setBounds(212, 138, 160, 41);
	        panel_1_1.add(btnNewButton_1_5);
	        
	        JButton btnNewButton_1_3_1 = new JButton("Data before pretraitement");
	        btnNewButton_1_3_1.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		frame.dispose();
	        		new Consult("conslut");
	        	}
	        });
	        btnNewButton_1_3_1.setBounds(178, 218, 243, 41);
	        panel_1_1.add(btnNewButton_1_3_1);
	        
	        JButton btnNewButton_1_4_1_1 = new JButton("Data after pretraitement");
	        btnNewButton_1_4_1_1.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		frame.dispose();
	        		 new Consult2("hello");
	        	}
	        });
	        btnNewButton_1_4_1_1.setBounds(178, 288, 254, 41);
	        panel_1_1.add(btnNewButton_1_4_1_1);
	        
	        JLabel lblNewLabel_1 = new JLabel("Welcome USER");
	        lblNewLabel_1.setForeground(Color.WHITE);
	        lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
	        lblNewLabel_1.setBounds(234, 49, 224, 14);
	        panel_1_1.add(lblNewLabel_1);
	        
	        // Ajouter un message de bienvenue
	      //  welcomeLabel = new JLabel("Bienvenue, " + username + " !", SwingConstants.CENTER);
	      //  frame.add(welcomeLabel, BorderLayout.NORTH);
	        
	        // Ajouter une zone de texte
	      //  textArea = new JTextArea("Saisissez votre message ici...");
	       // frame.add(textArea, BorderLayout.CENTER);
	        
	        // Rendre la fenêtre visible
	        frame.setVisible(true);
	    }
	    public static void main(String[] args) throws Exception {
	    	UIManager.setLookAndFeel( new NimbusLookAndFeel() );
	        new Visualize("user");
	    }
}
