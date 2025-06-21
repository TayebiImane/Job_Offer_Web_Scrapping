import java.sql.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;




public class DataPreprocessor {
    // La classe qui contient uniquement les champs nécessaires
    private static class DonneeNecessaire {
        
        String ville;
        String secteur;
        String jobTitle;
        String contratType;
        String educationLevel;
        String diplomaSpecialization;
        String experienceLevel;
        String Site_name;
        String language;
        String salaire;
        String remoteWork;

        
        @Override
		public String toString() {
			return "DonneeNecessaire [ville=" + ville + ", secteur=" + secteur + ", jobTitle="
					+ jobTitle + ", contratType=" + contratType + ", educationLevel=" + educationLevel
					+ ", diplomaSpecialization=" + diplomaSpecialization + ", experienceLevel=" + experienceLevel
					+  ", language=" + language + ", salaire=" + salaire + ", remoteWork="
					+ remoteWork + "]";
		}


		public DonneeNecessaire( String ville, String secteur, String jobTitle, String contratType,
                String educationLevel, String diplomaSpecialization, String experienceLevel, 
                String language, String salaire, String remoteWork,String site) {
            
            this.ville = ville;
            this.secteur = secteur;
            this.jobTitle = jobTitle;
            this.contratType = contratType;
            this.educationLevel = educationLevel;
            this.diplomaSpecialization = diplomaSpecialization;
            this.experienceLevel = experienceLevel;
            this.Site_name=site;
            this.language = language;
            this.salaire = salaire;
            this.remoteWork = remoteWork;
        }
        
    }
    
    //La méthode qui permet de remplacer les null par non-spécifié 
    private static String replaceNull(String value) {
        return (value != null) ? value : "Non spécifié";
    }

    // La liste qui permet de stocker les données prétraitées sur lesquelles on peut appliquer les modèles
    private static List<DonneeNecessaire> processedData = new ArrayList<>();
    
    public static void storeDataInDatabase(List<DonneeNecessaire> dataList) {
        
        String insertQuery = "INSERT INTO preprocessed_job_offers (city, sector, job_title, contract_type, education_level, "
                + "diploma_specialization, experience_level, site_name, language, salary, remote_work) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Job_scrapping.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            for (DonneeNecessaire data : dataList) {
                preparedStatement.setString(1, data.ville);                
                preparedStatement.setString(2, data.secteur);              
                preparedStatement.setString(3, data.jobTitle);             
                preparedStatement.setString(4, data.contratType);          
                preparedStatement.setString(5, data.educationLevel);       
                preparedStatement.setString(6, data.diplomaSpecialization);
                preparedStatement.setString(7, data.experienceLevel);      
                preparedStatement.setString(8, data.Site_name);             
                preparedStatement.setString(9, data.language);             
                preparedStatement.setString(10, data.salaire);             
                preparedStatement.setString(11, data.remoteWork);          

               
                preparedStatement.addBatch();
            }

            
            int[] result = preparedStatement.executeBatch();
            System.out.println("Insertion terminée : " + result.length + " enregistrements ajoutés.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'insertion des données dans la base : " + e.getMessage());
        }
    }


   
    //--------------------------------La fonction qui permet d'importer les données scrappées à partir de la base de données------------------------------------//
    public static void load_And_ProcessData() {
        try (Connection connection = Job_scrapping.getConnection()) {
            String query = "SELECT  site_name, city, sector, job_title, contract_type, education_level, diploma_specialization, "
                    + "experience_level, profile, language, salary, remote_work FROM job_offers";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    
                    String site=replaceNull(resultSet.getString("site_name"));
                    
                    String ville = replaceNull(resultSet.getString("city"));
                    //Par analyse, je remarque que certaines cellules contiennent des valeurs numériques, ce qui est une erreur. Je les ai donc remplacées par 'Non spécifié'. 
                    if (ville != null && ville.matches(".*\\d.*")) {
                        ville = ville.replaceAll("[^A-Za-zééà]", " ");

                        if (ville.trim().isEmpty()) {
                            ville = "Non spécifié";
                        }

                        // Conversion en majuscules
                        ville = ville.toLowerCase();
                    }else {
                    	 ville = ville.toLowerCase();
                    }
                    ville = ville.replaceAll("[éè]", "e");
                    //
                    if(ville.equals("marrakech gueliz-ennakhil") || ville.equals("gueliz  marrakech        ") || ville.equals("marrakech et region") || ville.equals("marrakach - menara")) {
                    	ville="marrakech";
                    }
                    
                    if(ville.equals("   bd anfa casablanca ") || ville.equals("casablanca-anfa") || ville.equals("casa-nouacer") || ville.equals("casa-sidi bernoussi") || ville.equals("casa-al fida derb sultan") || ville.equals("casa-ain chock hay hassani") || ville.equals("casa-al fida derb sultan") || ville.equals("casablanca, maroc") || ville.equals("casablanca, settat, maroc") || ville.equals("imm rafina  bourgogne  casablanca        ") || ville.equals("bourgogne  casablanca") || ville.equals("casablanca-rabat")) {
                    	ville="casablanca";
                    }
                    ville = ville.replaceAll("et region", "");
                    ville = ville.replaceAll(", maroc", "");
                    ville = ville.replaceAll("universite chouaib doukkali", "");
                    ville = ville.replaceAll("casablanca  marrakech  rabat   mekn s et f s   kenitra         ", "plusieurs villes");
                    ville = ville.replaceAll("casablanca  rabat   marrakech   f s et mekn s", "plusieurs villes");
                    ville = ville.replaceAll("casablanca  rabat", "plusieurs villes");
                    ville = ville.replaceAll("tanger-assilah", "tanger");
                    ville = ville.replaceAll("tout le maroc", "plusieurs villes");
                    ville = ville.replaceAll("casablanca  rabat   marrakech   agadir   tanger   tetouan   oujda  mekn s   f s        ", "plusieurs villes");
                    ville = ville.replaceAll("casablanca  rabat  tanger  tetouan  marrakech   agadir  beni mellal         ", "plusieurs villes");
                    ville = ville.replace("   bd anfa casablanca ", "casablanca");
                    ville = ville.replace("plusieurs villes  tanger  tetouan  marrakech   agadir  beni mellal         ", "plusieurs villes");
                    ville = ville.replace("plusieurs villes   marrakech   agadir   tanger   tetouan   oujda  mekn s   f s        ", "plusieurs villes");
                    ville = ville.replaceAll("f s s", "fes");
                    ville = ville.replaceAll("hay riad  rabat", "rabat");
                    ville = ville.replaceAll("rabat agdal", "rabat");
                    ville = ville.replaceAll("chtouka a t baha  agadir", "agadir");
                    ville = ville.replaceAll("rabat centre", "rabat");
                    ville = ville.replaceAll("bourgogne  casablanca", "casablanca");
                    ville = ville.replaceAll("riad    av  annakhil  rabat", "rabat");
                    
                    if (ville.trim().isEmpty() || ville.equals("non specifie")) {
                        ville = "casablanca";
                    }
                    ville=ville.replace(" ", "");
                    if(ville.equals("oujdaangad")) {
                    	ville="oujda";
                    }
                    if(ville.equals("lâayoune")) {
                    	ville="laayoune";
                    }
                      List<String> villesvalid = Arrays.asList("casablanca", "inezganeAitmelloul", "berkane", "meknes", "marrakech", "agadir", "eljadida", "taroudant", "tetouan", "zagora", "Tanger", "Essaouira", "kenitra", "rabat", "midelt", "tinghir", "ouarzazate", "sidikacem", "fes", "nouaceur", "mohammedia", "sale", "bouznika", "chtoukaaitBaha", "errachidia", "azilal", "beniMellal", "khouribga", "darbouazza", "laayoune", "brussels", "oujda", "alhaouz", "alhoceima", "plusieursVilles", "autreVille");
                       if (!villesvalid.contains(ville)) {
                    	    ville = "autreville";
                    	}
                    
                    
                    String secteur = replaceNull(resultSet.getString("sector"));
                    secteur = secteur.replace("Secteur d'activité : ", "");
                    secteur =secteur.toLowerCase();
                    secteur = secteur.replaceAll("[^a-zA-Z0-9éèà' ô]", " ");
                    if(secteur.equals("non spécifié")) {
                    	secteur="informatique";
                    }
                    secteur = secteur.split("\\s+")[0];
                    List<String> secteursValidés = Arrays.asList("commercial", "tourisme", "education", "informatique", "agriculture", 
                            "immobilier", "accueil", "santé", "droit", "transport", "comptabilité", 
                            "industrie", "biologie", "marketing", "restauration", "agro");


                    if (!secteursValidés.contains(secteur)) {
                    		secteur = "industrie";
                    }
                    
                    
                    
                    String jobTitle = replaceNull(resultSet.getString("job_title"));
                    jobTitle = jobTitle.replaceAll("[^a-zA-Z0-9,éèà' -]", "");
                    if(jobTitle.equals("Non spécifié")) {
                    	jobTitle="informatique";
                    }
                    //jobTitle = jobTitle.split("\\s+")[0];
                    
                    
                    String contratType = replaceNull(resultSet.getString("contract_type"));
                    contratType = contratType.replace("Type de contrat proposé : ", "");
                    if ( contratType.trim().isEmpty()) {
                    	 contratType= "Non spécifié";
                    }
                    if (contratType.equals("CDI - CDD - Intérim - Stage - Freelance - Alternance - Temps partiel - Statutaire") || 
                    	    contratType.equals("CDD - Freelance") || 
                    	    contratType.equals("CDI - CDD - Freelance")) {
                    	    contratType = "Choix Multiple";
                    	}
                    int contratTypeNumeric = 0;
                    Map<String, Integer> contratMap = new HashMap<>();
                    contratMap.put("CDI", 1); 
                    contratMap.put("CDD", 2); 
                    contratMap.put("Stage", 3);
                    contratMap.put("Freelance", 4);
                    contratMap.put("Intérim", 5);
                    contratMap.put("A discuter", 6);
                    contratMap.put("Choix Multiple", 7);
                    contratMap.put("CI", 8);
                    contratMap.put("Anapec", 0);
                    contratMap.put("Non spécifié", 0);

                    contratTypeNumeric = contratMap.getOrDefault(contratType, 0);
                    String contratTypeString = String.valueOf(contratTypeNumeric);

                    
                    String educationLevel = replaceNull(resultSet.getString("education_level"));
                    educationLevel = educationLevel.replace("Niveau d'étude demandé : ", "");
                    educationLevel = educationLevel.replace("Niveau d'études souhaité: ", "");
                    if (educationLevel.equals("CDI") || educationLevel.equals("CDD") || educationLevel.equals("CDI - CDD - Intérim - Stage - Freelance - Alternance - Temps partiel - Statutaire") || educationLevel.equals("CDD - Freelance") || educationLevel.equals("CDI - CDD - Freelance")) {
                    	educationLevel="Bac +5 ";
                    }
                    if(educationLevel.equals("Non spécifié")) {
                    	educationLevel="0 ";
                    }
                    if(educationLevel.equals("Pas important")) {
                    	educationLevel="1";
                    }
                    
                    if(educationLevel.equals("Niveau Bac")) {
                    	educationLevel="2";
                    }
                    if(educationLevel.equals("Bac+2")) {
                    	educationLevel="3";
                    }
                    if(educationLevel.equals("Bac+3")) {
                    	educationLevel="4";
                    }
                    if(educationLevel.equals("Bac+4") || educationLevel.equals("Bac +4")) {
                    	educationLevel="5";
                    }
                    if(educationLevel.equals("Bac+5") || educationLevel.equals("Bac +5 ")) {
                    	educationLevel="6";
                    }
                    if(educationLevel.equals("Bac +5 et plus")) {
                    	educationLevel="7";
                    }
                    

                    
                    String diplomaSpecialization = replaceNull(resultSet.getString("diploma_specialization"));
                    diplomaSpecialization = diplomaSpecialization.replace("Fonction : ", "");
                    diplomaSpecialization = diplomaSpecialization.replaceAll("[^a-zA-Zéèô ]", " ");
                    if(diplomaSpecialization.equals("Non spécifié")) {
                    	diplomaSpecialization="informatique";
                    }
                    diplomaSpecialization = diplomaSpecialization.split("\\s+")[0];
                    List<String> ForValidées = Arrays.asList(
                    	    "Informatique", "Assistant", "Cuisinier", "SAV", "Enseignement", 
                    	    "RH", "Commercial", "Qualité", "Ingénierie", 
                    	    "Production", "Marketing", "Comptabilité", "BTP", 
                    	    "Surveillance", "Logistique", "Achat", "Import", "Bureau", "Manager", 
                    	    "Santé", "Juridique", "Mécanicien"
                    	);
                    if (!ForValidées.contains(diplomaSpecialization)) {
                    	diplomaSpecialization = "Informatique";
                    }
                    
                    
                    String experienceLevel = replaceNull(resultSet.getString("experience_level"));
                    experienceLevel=experienceLevel.replace("Expérience requise : ", "");
                    experienceLevel=experienceLevel.replace("De ", "");
                    experienceLevel=experienceLevel.replace("à", "-");
                    experienceLevel=experienceLevel.replace("(", "");
                    experienceLevel=experienceLevel.replace(")", "");
                    experienceLevel=experienceLevel.replaceAll("[^0-9-]", "");
                    if(experienceLevel.isEmpty()) {
                    	experienceLevel="7";
                    }
                    experienceLevel = experienceLevel.replaceAll("^(\\d+)(?=-).*", "$1");
                    
                    
                    
                    
                    
                    String language = replaceNull(resultSet.getString("language"));
                    language=language.toLowerCase();
                    language=language.replaceAll(",.*", "");
                    if(language.equals("non spécifié")) {
                    	language="francais";
                    }
                    List<String> languesValidées = Arrays.asList("français", "néerlandais", "italien", "portugais", 
                            "anglais", "allemand", "arabe", "tarifit", "tamazight");


                    if (!languesValidées.contains(language)) {
                    	language = "arabe";
                    }
                    
                    
                    
                    String salaire = replaceNull(resultSet.getString("salary"));
                    salaire = salaire.replace("Salaire: ", "").trim(); 
                   if(salaire.equals("Non spécifié")) {
                	   salaire = "4000";
                   }
                 
                 if (salaire.equals("A discuter") ) {
                     salaire = "5000";  
                 } else {
                    
                     salaire = salaire.replaceAll("[^0-9-]", "");

                     if (salaire.contains("-")) {
                         String[] parts = salaire.split("-");

                         if (parts.length == 2) {
                             try {
                                 int lower = Integer.parseInt(parts[0].trim());
                                 int upper = Integer.parseInt(parts[1].trim());

                                 double average = (lower + upper) / 2.0;
                                 salaire = String.valueOf(average); 

                             } catch (NumberFormatException e) {
                                
                                 System.out.println("Erreur de conversion du salaire : " + salaire);
                                 salaire = "0";
                             }
                         } else {
                             System.out.println("Le format du salaire est incorrect : " + salaire);
                             salaire = "0";
                         }
                     } else {
                         try {
                             int singleSalary = Integer.parseInt(salaire); 
                             salaire = String.valueOf(singleSalary);  

                         } catch (NumberFormatException e) {
                             System.out.println("Erreur de conversion du salaire : " + salaire);
                             salaire = "4000";
                         }
                     }
                 }
                 
                    
                    String remoteWork = replaceNull(resultSet.getString("remote_work")).trim();
                    
                   
                    
                    int remoteTypeNumeric = 0;  
                    Map<String, Integer> remotMap = new HashMap<>();
                    remotMap.put("Non", 1); 
                    remotMap.put("Hybride", 2); 
                    remotMap.put("Oui 100%", 3);
                    remotMap.put("Non spécifié", 0);


                    remoteTypeNumeric = remotMap.getOrDefault(remoteWork, 0); 
                    String remoteTypeString = String.valueOf(remoteTypeNumeric);
                   
                    DonneeNecessaire donnee = new DonneeNecessaire(
                            ville, secteur, jobTitle, contratTypeString, educationLevel,
                            diplomaSpecialization, experienceLevel, language, salaire, remoteTypeString,site);

                    
                    processedData.add(donnee);
                    
                }
                storeDataInDatabase(processedData);
                //
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Utilisateur\\Desktop\\Semestre7\\Java\\Mini Projet\\Livrable3\\DonneePretraite2.arff"))) {

                    
                    writer.write("@relation Donnees_Pretraite\n\n");

                    writer.write("@attribute site_name {Rekrute,marocemploi.net,talentspartners.com,www.moncallcenter.ma, Marocannonces.com,Emploi.ma,Anapec}\n");
                    writer.write("@attribute ville{casablanca, inezganeaitmelloul, berkane, meknes, marrakech, agadir, eljadida, taroudant, tetouan, zagora, tanger, essaouira, kenitra, rabat, midelt, tinghir, ouarzazate, sidikacem, fes, nouaceur, mohammedia, sale, bouznika, chtoukaaitbaha, errachidia, azilal, benimellal, khouribga, darbouazza, laayoune, brussels, oujda, alhaouz, alhoceima, plusieursvilles, autreville}\n");
                    writer.write("@attribute sector {commercial,education,informatique,agriculture,immobilier,accueil,santé,droit,transport,tourisme,comptabilité,industrie,biologie,marketing,restauration,agro,}\n"); 
                    writer.write("@attribute Type_de_contrat {0,1,2,3,4,5,6,7,8}\n");
                    writer.write("@attribute Language {français,néerlandais,italien,portugais,anglais,allemand,arabe,tarifit,tamazight}\n"); 
                    writer.write("@attribute Experience_level numeric\n");
                    writer.write("@attribute Formation {Informatique,Assistant,Cuisinier,SAV,Enseignement,RH,Commercial,Qualité,Ingénierie,Production,Marketing,Comptabilité,BTP,Surveillance,Logistique, Achat,Import,Bureau,Manager,Santé,Juridique,Mécanicien}\n");
                    writer.write("@attribute Salaire numeric\n");
                    writer.write("@attribute Travail_a_distance {0,1,2,3}\n\n");

                    
                    writer.write("@data\n");

                    
                    for (DonneeNecessaire donnee : processedData) {
                        
                        int contratTypeInt = Integer.parseInt(donnee.contratType); 
                        
                        
                        int remoteWorkInt = Integer.parseInt(donnee.remoteWork);

                        writer.write(donnee.Site_name+","+ donnee.ville+ ","+ donnee.secteur + "," + contratTypeInt + ","+donnee.language +","+donnee.experienceLevel+","+donnee.diplomaSpecialization+","
                                + donnee.salaire + ","
                                + remoteWorkInt + "\n");  

                    }

                    System.out.println("Fichier ARFF exporté avec succès : " + "C:\\Users\\Utilisateur\\Desktop\\Semestre7\\Java\\Mini Projet\\Livrable3\\DonneePretraite2.arff");
                    
                    
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    System.out.println("Erreur de conversion: " + e.getMessage());
                }

                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    //--------------------------------------------------------Le main principale ---------------------------------------------------------//
    public static void main(String[] args) {
        DataPreprocessor dataPreprocessor = new DataPreprocessor();
        dataPreprocessor.load_And_ProcessData();
    }
}
