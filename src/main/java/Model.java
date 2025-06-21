import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.M5P;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.Debug.Random;
import weka.core.converters.ArffLoader;
import weka.core.converters.DatabaseLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.ThresholdVisualizePanel;
import weka.gui.visualize.VisualizePanel;
import java.awt.SystemColor;

public class Model {
    private JFrame frame;
    private JTextArea txtrGhj;

    public Model(String username) {
        
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

        JLabel lblNewLabel = new JLabel("APPLICATION JAVA-MODEL");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblNewLabel.setBounds(401, 27, 467, 44);
        panel.add(lblNewLabel);

        JButton btnModel = new JButton("Model");
        btnModel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                	frame.dispose();
                    new Model("mode");
                	
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnModel.setBounds(247, 93, 89, 23);
        frame.getContentPane().add(btnModel);

        
        txtrGhj = new JTextArea();
        txtrGhj.setForeground(SystemColor.desktop);
        txtrGhj.setBackground(SystemColor.control);
        txtrGhj.setEditable(false);
        txtrGhj.setFont(new Font("Tahoma", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(txtrGhj);
        scrollPane.setBounds(10, 130, 1180, 400);
        frame.getContentPane().add(scrollPane);

       
        PrintStream printStream = new PrintStream(new TextAreaOutputStream(txtrGhj));
        
        JButton btnModel_1 = new JButton("Consult");
        btnModel_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        		new Consult("consult");
        	}
        });
        btnModel_1.setBounds(10, 93, 89, 23);
        frame.getContentPane().add(btnModel_1);
        
        JButton btnModel_1_1 = new JButton("Scrapp");
        btnModel_1_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        		new Scrapp();
        	}
        });
        btnModel_1_1.setBounds(128, 93, 89, 23);
        frame.getContentPane().add(btnModel_1_1);
        
        JButton btnModel_1_2 = new JButton("Visualize");
        btnModel_1_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        		new Visualize("hello");
        	}
        });
        btnModel_1_2.setBounds(367, 93, 89, 23);
        frame.getContentPane().add(btnModel_1_2);
        
        JButton btnClustering = new JButton("clustering");
        btnClustering.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					clustering();
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
        	}
        });
        btnClustering.setBounds(770, 94, 89, 23);
        frame.getContentPane().add(btnClustering);
        
        JButton btnModel_1_3 = new JButton("Classification");
        btnModel_1_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					classifier();
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
        	}
        });
        btnModel_1_3.setBounds(889, 93, 89, 23);
        frame.getContentPane().add(btnModel_1_3);
        
        JButton btnModel_1_4 = new JButton("regress");
        btnModel_1_4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			regresser();
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
        	}
        });
        btnModel_1_4.setBounds(996, 94, 89, 23);
        frame.getContentPane().add(btnModel_1_4);
        System.setOut(printStream);
        System.setErr(printStream);

        frame.setVisible(true);
    }

    
    
    static void classifier() throws Exception {
   	 DataSource source = new DataSource("C:\\Users\\Utilisateur\\eclipse-workspace\\MyJava_Project\\src\\main\\resources\\Data\\DonneePretraite2.arff"); 
     Instances data = source.getDataSet();
     System.out.println(data.numInstances() + " instances loaded.");
     
     
     
     String[] opts = new String[]{"-R", "1"}; 
     Remove remove = new Remove();
     remove.setOptions(opts);
     remove.setInputFormat(data);
     data = Filter.useFilter(data, remove);

     // Sélection de caractéristiques (Feature Selection)
     AttributeSelection attSelect = new AttributeSelection();
     InfoGainAttributeEval eval = new InfoGainAttributeEval();
     Ranker search = new Ranker();
     attSelect.setEvaluator(eval);
     attSelect.setSearch(search);
     attSelect.SelectAttributes(data);
     int[] indices = attSelect.selectedAttributes();
     System.out.println("Attributs sélectionnés : " + Utils.arrayToString(indices));

     // Construire un arbre de décision (J48)
     String[] options = new String[1];
     options[0] = "-U"; // Pas de pruning (élagage)
     J48 tree = new J48();
     tree.setOptions(options);
     tree.buildClassifier(data);
     System.out.println("Arbre de décision généré : \n" + tree);

     // Classifier une nouvelle instance
     double[] vals = new double[data.numAttributes()];
     // Exemple de valeurs pour la nouvelle instance
     vals[0] = 1.0; 
     vals[1] = 0.0; 
     vals[2] = 1.0; 
     vals[3] = 1.0; 
     

     Instance newJob = new DenseInstance(1.0, vals);
     newJob.setDataset(data);
     double label = tree.classifyInstance(newJob);
     System.out.println("Type de contrat prédit : " + data.classAttribute().value((int) label));

     // Visualiser l'arbre de décision
     TreeVisualizer tv = new TreeVisualizer(null, tree.graph(), new PlaceNode2());
     JFrame frame = new JFrame("Visualisation de l'arbre");
     frame.setSize(6000, 500);
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.getContentPane().add(tv);
     frame.setVisible(true);
     tv.fitToScreen();

     // Évaluation avec validation croisée
     J48 cl = new J48();
     Evaluation eval_roc = new Evaluation(data);
     eval_roc.crossValidateModel(cl, data, 10, new Random(1), new Object[]{});
     System.out.println(eval_roc.toSummaryString());

     // Afficher la matrice de confusion
     double[][] confusionMatrix = eval_roc.confusionMatrix();
     System.out.println("Matrice de confusion :");
     System.out.println(eval_roc.toMatrixString());

     // Bonus : Visualiser la courbe ROC
     ThresholdCurve tc = new ThresholdCurve();
     int classIndex = 0;
     Instances result = tc.getCurve(eval_roc.predictions(), classIndex);
     // Afficher la courbe ROC
     ThresholdVisualizePanel vmc = new ThresholdVisualizePanel();
     vmc.setROCString("(Area under ROC = " + tc.getROCArea(result) + ")");
     vmc.setName(result.relationName());
     PlotData2D tempd = new PlotData2D(result);
     tempd.setPlotName(result.relationName());
     tempd.addInstanceNumberAttribute();
     // Spécifier les points connectés
     boolean[] cp = new boolean[result.numInstances()];
     for (int n = 1; n < cp.length; n++)
         cp[n] = true;
     tempd.setConnectPoints(cp);

     // Ajouter le tracé
     vmc.addPlot(tempd);
     
     JFrame frameRoc = new JFrame("Courbe ROC");
     frameRoc.setSize(800, 500);
     frameRoc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frameRoc.getContentPane().add(vmc);
     frameRoc.setVisible(true);
    }
    
    
    static void regresser() throws Exception {

        ArffLoader loader = new ArffLoader();
        loader.setFile(new File("C:\\Users\\Utilisateur\\eclipse-workspace\\MyJava_Project\\src\\main\\resources\\Data\\DonneePretraite2.arff"));
        Instances data = loader.getDataSet();
        data.setClassIndex(data.numAttributes() - 2);

        // Remove last attribute Y2
        Remove remove = new Remove();
        remove.setOptions(new String[]{"-R", data.numAttributes() + ""});
        remove.setInputFormat(data);
        data = Filter.useFilter(data, remove);

        // Build a linear regression model
        LinearRegression model = new LinearRegression();
        model.buildClassifier(data);
        System.out.println("Linear Regression Model:");
        System.out.println(model);

        // Perform 10-fold cross-validation
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(model, data, 10, new Random(1), new String[]{});
        System.out.println("Linear Regression Evaluation:");
        System.out.println(eval.toSummaryString());

        // Build a regression tree model (M5P)
        M5P md5 = new M5P();
        md5.buildClassifier(data);
        System.out.println("M5P Regression Tree Model:");
        System.out.println(md5);

        // Perform 10-fold cross-validation for M5P
        eval.crossValidateModel(md5, data, 10, new Random(1), new String[]{});
        System.out.println("M5P Regression Tree Evaluation:");
        System.out.println(eval.toSummaryString());
   
    }
    
    
    static void clustering() throws Exception {
    	   
        // Charger les données
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Utilisateur\\eclipse-workspace\\MyJava_Project\\src\\main\\resources\\Data\\DonneePretraite2.arff"));
        Instances data = new Instances(reader);
        reader.close();

        System.out.println(data.numInstances() + " instances chargées pour le clustering.");

        // Appliquer l'algorithme de clustering K-means
        SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setNumClusters(3); // Définir le nombre de clusters souhaités
        kmeans.buildClusterer(data);

        // Afficher les détails du modèle
        System.out.println("Modèle de clustering :\n" + kmeans);

        // Évaluer le clustering
        ClusterEvaluation eval = new ClusterEvaluation();
        eval.setClusterer(kmeans);
        eval.evaluateClusterer(new Instances(data));
        System.out.println("Évaluation du clustering :\n" + eval.clusterResultsToString());

        // Appliquer l'algorithme J48 sur les données avec les clusters comme classe
        data.setClassIndex(data.numAttributes() - 1);
        J48 tree = new J48();
        tree.buildClassifier(data);
        System.out.println("Arbre de décision J48 :\n" + tree.toString());
     // Afficher les prédictions pour chaque instance
        for (int i = 0; i < data.numInstances(); i++) { 
        	double cluster = kmeans.clusterInstance(data.instance(i)); 
        	double prediction = tree.classifyInstance(data.instance(i));
        	System.out.println("Instance " + i + ": Cluster " + cluster + ", Prédiction " + prediction); 
        	}

        // Visualiser l'arbre de décision J48
        TreeVisualizer tv = new TreeVisualizer(null, tree.graph(), new PlaceNode2());
        JFrame treeFrame = new JFrame("Visualisation de l'Arbre J48");
        treeFrame.setSize(800, 600);
        treeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        treeFrame.getContentPane().add(tv);
        treeFrame.setVisible(true);
        tv.fitToScreen();

        // Visualiser les clusters sous forme de points dans un espace 2D
        VisualizePanel vp = new VisualizePanel();
        vp.setName(data.relationName()); // Nom de la relation
        vp.setInstances(data);

        JFrame frame = new JFrame("Visualisation du Clustering");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vp);
        frame.setVisible(true);
    


 

}


    public static void main(String[] args) throws Exception {
    	UIManager.setLookAndFeel( new NimbusLookAndFeel() );
        new Model("user");
    }
}


