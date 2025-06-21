

import java.awt.Color;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Scrapp {
	  private JFrame frame;
	    private JLabel welcomeLabel;
	    private JTextArea textArea;
	    private JTable table;
	    private JComboBox comboBox;
	    private Job_scrapping job=new Job_scrapping();
	    
	    public Scrapp() {
	        // Créer la fenêtre de bienvenue
	        frame = new JFrame("Bienvenue");
	        frame.getContentPane().setBackground(new Color(240, 240, 240));
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(1222, 645);
	        frame.getContentPane().setLayout(null);
	        
	        JPanel panel = new JPanel();
	        panel.setBackground(new Color(255, 128, 128));
	        panel.setBounds(0, 0, 1208, 82);
	        frame.getContentPane().add(panel);
	        panel.setLayout(null);
	        
	        JLabel lblNewLabel = new JLabel("APPLICATION JAVA-SCRAPPING");
	        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
	        lblNewLabel.setBounds(401, 27, 463, 44);
	        panel.add(lblNewLabel);
	        
	         
	        JButton btnNewButton = new JButton("consult");
	        btnNewButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		 frame.dispose(); 
	        		 new Consult("user"); 
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
	        		 new Visualize("visualize"); 
	        	}
	        });
	        btnNewButton_1_2.setBounds(390, 93, 89, 23);
	        frame.getContentPane().add(btnNewButton_1_2);
	        
	        JPanel panel_1 = new JPanel();
	        panel_1.setBackground(Color.DARK_GRAY);
	        panel_1.setBounds(-16, 127, 554, 492);
	        frame.getContentPane().add(panel_1);
	        panel_1.setLayout(null);
	        
	        JLabel lblNewLabel_3 = new JLabel("Choose url");
	        lblNewLabel_3.setForeground(new Color(255, 255, 255));
	        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
	        lblNewLabel_3.setBounds(245, 415, 107, 14);
	        panel_1.add(lblNewLabel_3);
	        
	        JLabel lblNewLabel_2 = new JLabel("New label");
	        lblNewLabel_2.setBounds(-46, 0, 667, 392);
	        lblNewLabel_2.setIcon(new ImageIcon(Scrapp.class.getResource("/Images/bg2.jpg")));
	        panel_1.add(lblNewLabel_2);
	        
	        JLabel lblNewLabel_3_1 = new JLabel("...And Scrap...");
	        lblNewLabel_3_1.setForeground(new Color(255, 255, 255));
	        lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
	        lblNewLabel_3_1.setBounds(255, 440, 154, 14);
	        panel_1.add(lblNewLabel_3_1);
	        
	        comboBox = new JComboBox();
	        comboBox.setModel(new DefaultComboBoxModel(new String[] {
	        	    "www.rekrute.com", "www.marocemploi.net", "www.emploi.ma", 
	        	    "ANAPEC.org", "talentspartners.com", "Marocannonces.com", 
	        	    "www.moncallcenter.ma"
	        	}));
	        comboBox.setBounds(741, 223, 321, 39);
	        frame.getContentPane().add(comboBox);
	        
	        JButton btnNewButton_1 = new JButton("Scrap");
	        btnNewButton_1.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		ONScrap(e);
	        	}
	        });
	        btnNewButton_1.setBackground(new Color(240, 240, 240));
	        btnNewButton_1.setBounds(830, 336, 142, 31);
	        frame.getContentPane().add(btnNewButton_1);
	        
	        JLabel lblNewLabel_1 = new JLabel("Chose an url for scrappping");
	        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
	        lblNewLabel_1.setBounds(741, 160, 257, 39);
	        frame.getContentPane().add(lblNewLabel_1);
	        
	        // Ajouter un message de bienvenue
	      //  welcomeLabel = new JLabel("Bienvenue, " + username + " !", SwingConstants.CENTER);
	      //  frame.add(welcomeLabel, BorderLayout.NORTH);
	        
	        // Ajouter une zone de texte
	      //  textArea = new JTextArea("Saisissez votre message ici...");
	       // frame.add(textArea, BorderLayout.CENTER);
	        
	        // Rendre la fenêtre visible
	        frame.setVisible(true);
	    }

		private void ONScrap(ActionEvent e) {
			// TODO Auto-generated method stub
			String selectedValue = comboBox.getSelectedItem().toString();
			System.out.println(selectedValue);
			switch (selectedValue) {
		    case "www.rekrute.com":
		    	
		    	Job_scrapping.rekruteScrapper Rekrutescrapper = new Job_scrapping.rekruteScrapper();
		    	Rekrutescrapper.scrapersite3();
		        break;
		    case "www.marocemploi.net":
		    	Job_scrapping.marocemploiscrapper marocemploi=new Job_scrapping.marocemploiscrapper();
		    	marocemploi.scrappersite7();
		        break;
		        
		    case "www.emploi.ma":
		    	Job_scrapping.emploiScrapper Emploiscrapper = new Job_scrapping.emploiScrapper() ;
		    	Emploiscrapper.scraperSite2();
		        break;
		    case "ANAPEC.org":
		    	Job_scrapping.Anapecscrapper anapecScraper = new Job_scrapping.Anapecscrapper();
		    	anapecScraper.scraperSite1();
		        break;

		    case "talentspartners.com":
		    	Job_scrapping.talentspartners talentsscrap=new Job_scrapping.talentspartners();
		    	talentsscrap.Scrappersites6();
		        break;
		    case "Marocannonces.com":
		    	Job_scrapping.marocanoncesScrapper mscrapper= new Job_scrapping.marocanoncesScrapper();
		    	mscrapper.Scrappersite4();
		    	 break;
		    case "www.moncallcenter.ma":
		    	Job_scrapping.monCallCenter callcenter=new Job_scrapping.monCallCenter();
		    	callcenter.Scrappersite5();
		    	 break;
		    default:
		        System.out.println("Autre option sélectionnée : " + selectedValue);
		}
		}
}
