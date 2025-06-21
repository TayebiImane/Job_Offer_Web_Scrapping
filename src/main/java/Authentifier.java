

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JTextPane;
import javax.swing.JPasswordField;
import java.awt.Dimension;
import java.awt.Button;
import java.awt.Panel;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class Authentifier extends JFrame{

	
	private static final long serialVersionUID = 1228654078373491281L;
	private JTextField text3;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JTextField textField,textField_1;
	private JPasswordField passwordField;
	private JLabel lblNewLabel,lblNewLabel_1,lblNewLabel_2;
	
	private static final String DB_URL = "jdbc:mysql://localhost:4000/annonces_emploi";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD ="";

	int xx,xy;
	public Authentifier() {
	        super("Scrapping");
	        getContentPane().setBackground(Color.WHITE);
	        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        this.setSize(1211, 603);
	        this.setLocationRelativeTo(null);
	        getContentPane().setLayout(null);
	        
	        Panel panel = new Panel();
	        panel.setBackground(Color.DARK_GRAY);
	        panel.setBounds(0, 0, 419, 566);
	        getContentPane().add(panel);
	        panel.setLayout(null);
	        
	        JLabel lblNewLabel_4 = new JLabel("");
	        lblNewLabel_4.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mousePressed(MouseEvent e) {
	        		 xx = e.getX();
				     xy = e.getY();
	        	}
	        });
	        lblNewLabel_4.addMouseMotionListener(new MouseMotionAdapter() {
	        	@Override
	        	public void mouseDragged(MouseEvent e) {
	        		
	        		int x = e.getXOnScreen();
		            int y = e.getYOnScreen();
		            Authentifier.this.setLocation(x - xx, y - xy); 
	        	}
	        	
	        	
	        });
	        lblNewLabel_4.setBounds(6, 0, 407, 301);
	        panel.add(lblNewLabel_4);
	        lblNewLabel_4.setIcon(new ImageIcon(Authentifier.class.getResource("/Images/bg2.jpg")));

	        
	        JLabel lblNewLabel_5 = new JLabel("KeepToo");
	        lblNewLabel_5.setForeground(Color.WHITE);
	        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        lblNewLabel_5.setBounds(165, 341, 114, 29);
	        panel.add(lblNewLabel_5);
	        
	        JLabel lblNewLabel_5_1 = new JLabel("....We got you.....");
	        lblNewLabel_5_1.setForeground(Color.WHITE);
	        lblNewLabel_5_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
	        lblNewLabel_5_1.setBounds(157, 382, 131, 29);
	        panel.add(lblNewLabel_5_1);
	        
	        Button button = new Button("login");
	        button.setForeground(SystemColor.text);
	        button.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		 try {
						TournePage(e);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        	}
	        });
	        button.setBackground(new Color(241,57,83));
	        button.setBounds(607, 407, 135, 38);
	        getContentPane().add(button);
	       
	        textField = new JTextField();
	        textField.setBounds(607, 102, 334, 51);
	     
	        getContentPane().add(textField);
	        textField.setColumns(10);
	        
	        textField_1 = new JTextField();
	        textField_1.setColumns(10);
	        textField_1.setBounds(607, 211, 334, 51);
	        getContentPane().add(textField_1);
	        
	        lblNewLabel = new JLabel("NAME");
	        lblNewLabel.setBounds(607, 81, 66, 16);
	        getContentPane().add(lblNewLabel);
	        
	        lblNewLabel_1 = new JLabel("MAIL");
	        lblNewLabel_1.setBounds(607, 184, 66, 16);
	        getContentPane().add(lblNewLabel_1);
	        
	         lblNewLabel_2 = new JLabel("PASSWORD");
	        lblNewLabel_2.setBounds(607, 280, 86, 16);
	        getContentPane().add(lblNewLabel_2);
	        
	        JLabel lblNewLabel_3 = new JLabel("WELCOME PLEASE TO SingUp");
	        lblNewLabel_3.setBounds(663, 22, 334, 16);
	        getContentPane().add(lblNewLabel_3);
	        
	        JLabel lblNewLabel_6 = new JLabel("x");
	        lblNewLabel_6.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        		System.exit(0);
	        	}
	        });
	        lblNewLabel_6.setFont(new Font("SansSerif", Font.PLAIN, 18));
	        lblNewLabel_6.setForeground(new Color(241,57,83));
	        lblNewLabel_6.setBackground(Color.WHITE);
	        lblNewLabel_6.setBounds(1165, 0, 48, 40);
	        getContentPane().add(lblNewLabel_6);
	        
	        passwordField = new JPasswordField();
	        passwordField.setBounds(607, 320, 334, 51);
	        getContentPane().add(passwordField);
	        
	        Button button_1 = new Button("sign in");
	        button_1.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	        		try {
						CreerCompte(e);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        	}
	        });
	        button_1.setActionCommand("sign up");
	        button_1.setForeground(SystemColor.text);
	        button_1.setBackground(new Color(241, 57, 83));
	        button_1.setBounds(829, 407, 135, 38);
	        getContentPane().add(button_1);

	        // Utiliser un BoxLayout pour disposer les composants verticalement
	      //  JPanel contentPane = new JPanel();
	 


	        // Ajouter le panneau au centre de la fenêtre
	     //   this.getContentPane().add(contentPane, BorderLayout.CENTER);
	      //  contentPane.setLayout(new GridLayout(1, 0, 0, 0));
	    }
	private void CreerCompte(ActionEvent e) throws SQLException {
			// TODO Auto-generated method stub
		   String name = textField.getText(); 
		   String email =textField_1.getText(); 
		   char[] passArray = passwordField.getPassword(); 
		   String pass = new String(passArray);
	
		 try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			 
			 insertIntoDatabase(conn,name,email,pass);
			 
			 
			 this.dispose(); // Fermer la fenêtre de connexion
			 new Scrapp();  
		  }
		}
	private void TournePage(ActionEvent e) throws SQLException {
			// TODO Auto-generated method stub
		 try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			 DataExtract(conn);
		  }
		}
	//----------------------------------------------------------------------
	private void DataExtract(Connection conn) throws SQLException {
	    String name = textField.getText();
	    String email = textField_1.getText();
	    char[] passArray = passwordField.getPassword(); 
	    String pass = new String(passArray);
	 ;
	
	    String selectSQL = "SELECT * FROM login WHERE name = ? AND password = ? AND email= ? ";
	    try (PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
	        pstmt.setString(1, name);
	        pstmt.setString(2, pass);
	        pstmt.setString(3, email);
	
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (!rs.next()) {
	                JOptionPane.showMessageDialog(this, "Identifiants incorrects !");
	            } else {
	                this.dispose(); 
	                new Scrapp();
	            }
	        }
	    }
	
	 
	}
	
	
	
	private void insertIntoDatabase(Connection conn,String nom,String email,String pass) throws SQLException {
		// TODO Auto-generated method stub
		  
		   String insertSQL = "INSERT INTO login (name,email,password) VALUES (?,?,?)";
	        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
	            pstmt.setString(1, nom);
	            pstmt.setString(2, email);
	            pstmt.setString(3, pass);
	            pstmt.executeUpdate();
	        }
	       
	}
	
	//----------------------------------------------------------------------
		public static void main(String[] args) throws Exception {
		
			UIManager.setLookAndFeel( new NimbusLookAndFeel() );
			Authentifier mywindow=new Authentifier();
			mywindow.setUndecorated(true);
			mywindow.setVisible(true);
		}
}