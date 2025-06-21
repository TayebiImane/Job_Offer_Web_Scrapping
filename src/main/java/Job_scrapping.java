

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Job_scrapping {
//========================================La classe interne offre qui va correspondre qui contient tous les champ de la base de données ===============================//
	public  static class Offre {
	    String title;
	    String URL;
	    String site_name;
	    String date_publicaton;
	    String deadline_date;
	    String adress;
	    String website;
	    String Company_name;
	    String Company_description;
	    String Job_Description;
	    String region;
	    String city;
	    String sector;
	    String job_title;
	    String Contrat_type;
	    String education_level;
	    String diploma_specialization;
	    String experience_level;
	    String Profile;
	    String personality_traits ;
	    String required_skills;
	    String Soft_skills;
	    String recommended_skills;
	    String Languages;
	    String language_level;
	    String Salary;
	    String benefits;
	    String Remote_Work;
		public Offre() {
			super();
		}
		public Offre(String title, String uRL, String site_name, String date_publicaton, String deadline_date,
				String adress, String website, String company_name, String company_description, String job_Description,
				String region, String city, String sector, String job_title, String contrat_type,
				String education_level, String diploma_specialization, String experience_level, String profile,
				String personality_traits, String required_skills, String soft_skills, String recommended_skills,
				String languages, String language_level, String benefits, String remote_Work) {
			super();
			this.title = title;
			URL = uRL;
			this.site_name = site_name;
			this.date_publicaton = date_publicaton;
			this.deadline_date = deadline_date;
			this.adress = adress;
			this.website = website;
			Company_name = company_name;
			Company_description = company_description;
			Job_Description = job_Description;
			this.region = region;
			this.city = city;
			this.sector = sector;
			this.job_title = job_title;
			Contrat_type = contrat_type;
			this.education_level = education_level;
			this.diploma_specialization = diploma_specialization;
			this.experience_level = experience_level;
			Profile = profile;
			this.personality_traits = personality_traits;
			this.required_skills = required_skills;
			Soft_skills = soft_skills;
			this.recommended_skills = recommended_skills;
			Languages = languages;
			this.language_level = language_level;
			this.benefits = benefits;
			Remote_Work = remote_Work;
		}
		@Override
		public String toString() {
			return "Offre [title=" + title + ", URL=" + URL + ", site_name=" + site_name + ", date_publicaton="
					+ date_publicaton + ", deadline_date=" + deadline_date + ", adress=" + adress + ", website="
					+ website + ", Company_name=" + Company_name + ", Company_description=" + Company_description
					+ ", Job_Description=" + Job_Description + ", region=" + region + ", city=" + city + ", sector="
					+ sector + ", job_title=" + job_title + ", Contrat_type=" + Contrat_type + ", education_level="
					+ education_level + ", diploma_specialization=" + diploma_specialization + ", experience_level="
					+ experience_level + ", Profile=" + Profile + ", personality_traits=" + personality_traits
					+ ", required_skills=" + required_skills + ", Soft_skills=" + Soft_skills + ", recommended_skills="
					+ recommended_skills + ", Languages=" + Languages + ", language_level=" + language_level
					+ ", benefits=" + benefits + ", Remote_Work=" + Remote_Work +", salary"+ Salary+"]";
		}
	  }

//------------------------------La finction qui va nous permetter à se connecter à la base de donnée-----------------------------------//
	public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:4000/annonces_emploi";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }
	
//----------------------------------La fonction qui va definir les paramateres de l'offre pour qu'on puisse l'appeler dans la fonction qui assure l'insrtion dans la base de données -------------------//
	private static void setOfferParameters(PreparedStatement stmt, Offre offre) throws SQLException {
        stmt.setString(1, offre.title);
        stmt.setString(2, offre.URL);
        stmt.setString(3, offre.site_name);
        stmt.setString(4, offre.date_publicaton);
        stmt.setString(5, offre.deadline_date);
        stmt.setString(6, offre.adress);
        stmt.setString(7, offre.website);
        stmt.setString(8, offre.Company_name);
        stmt.setString(9, offre.Company_description);
        stmt.setString(10, offre.Job_Description);
        stmt.setString(11, offre.region);
        stmt.setString(12, offre.city);
        stmt.setString(13, offre.sector);
        stmt.setString(14, offre.job_title);
        stmt.setString(15, offre.Contrat_type);
        stmt.setString(16, offre.education_level);
        stmt.setString(17, offre.diploma_specialization);
        stmt.setString(18, offre.experience_level);
        stmt.setString(19, offre.Profile);
        stmt.setString(20, offre.personality_traits);
        stmt.setString(21, offre.required_skills);
        stmt.setString(22, offre.Soft_skills);
        stmt.setString(23, offre.recommended_skills);
        stmt.setString(24, offre.Languages);
        stmt.setString(25, offre.language_level);
        stmt.setString(26, offre.Salary);
        stmt.setString(27, offre.benefits);
        stmt.setString(28, offre.Remote_Work);
    }
	
//----------------------------------------------La fonction qui permet l'insertion dans la base de données -----------------------------------//
	private static void insertOfferIntoDatabase(Offre offre) {
        String insertSQL = "INSERT INTO job_offers (title, url, site_name, publication_date, application_deadline, company_address, company_website, company_name, company_description, job_description, region, city, sector, job_title, contract_type, education_level, diploma_specialization, experience_level, profile, personality_traits, required_skills, soft_skills, recommended_skills, language, language_level, salary, benefits, remote_work) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            setOfferParameters(stmt, offre);
            stmt.executeUpdate();
            System.out.println("Insertion réussie pour l'offre : " + offre);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion dans la base de données : " + e.getMessage());
        }
    }

//=============================La classe qui permet le scrapping d'anapec=======================================================//
	public static class Anapecscrapper{
	//-----------------------------puisque la syntaxe dans Anapec est très complexe, car les champs n'ont pas de classes ou de balises spécifiques, mais chaque champ est suivi de son nom (par exemple, pour 'langues', après le span 'langue', nous avons défini cette fonction)------------------------------------------------//
		private String getTextAfterSpanOrStrong(Document doc, String label) {
	        try {
	            Elements elements = doc.select("p:has(span.small_puce:contains(" + label + ")), p:has(strong:contains(" + label + "))");
	            if (!elements.isEmpty()) {
	                for (Element element : elements) {
	                    Element nextElement = element.select("span:not(.small_puce), strong").first();
	                    if (nextElement != null) {
	                        return nextElement.text().trim();
	                    }
	                }
	            }
	        } catch (Exception e) {
	            System.err.println("Erreur dans getTextAfterSpanOrStrong pour le label '" + label + "': " + e.getMessage());
	        }
	        return "Non spécifié";
	    }
		
	//---------------------------La fonction qui permet d'extraire le profile ------------------------------------------//
		private String extractProfile(Document doc) {
	        try {
	            Elements profileElements = doc.select("p:contains(Profil recherché)");
	            if (!profileElements.isEmpty()) {
	                return profileElements.get(0).text();
	            }
	        } catch (Exception e) {
	            System.err.println("Erreur lors de l'extraction du profil recherché : " + e.getMessage());
	        }
	        return "Non spécifié";
	    }
	//-------------------------La fonction qui permet d'extraire les langues et levels correpondant ------------------------//
		private void extractLanguages(Document doc, Offre offre) {
		    try {
		        Element languagesElement = doc.selectFirst("span.small_puce:contains(Langues)");
		        if (languagesElement != null) {		          
		            String languagesText = languagesElement.parent()
		                    .nextElementSibling()
		                    .html();
		            String[] lines = languagesText.split("<br>");
		            StringBuilder languagesBuilder = new StringBuilder();
		            StringBuilder levelsBuilder = new StringBuilder();
		            for (String line : lines) {
		                line = line.trim(); 
		                if (!line.isEmpty()) {
		                    if (line.contains(":")) {
		                        String[] parts = line.split(":", 2);
		                        if (parts.length == 2) {
		                            if (languagesBuilder.length() > 0) {
		                                languagesBuilder.append(", ");
		                                levelsBuilder.append(", ");
		                            }
		                            languagesBuilder.append(parts[0].trim());
		                            levelsBuilder.append(parts[1].trim());   
		                        }
		                    }
		                }
		            }		            offre.Languages = languagesBuilder.length() > 0 ? languagesBuilder.toString() : "Non spécifié";
		            offre.language_level = levelsBuilder.length() > 0 ? levelsBuilder.toString() : "Non spécifié";
		        } else {
		            
		            offre.Languages = "Non spécifié";
		            offre.language_level = "Non spécifié";
		        }

		    } catch (Exception e) {
		        System.err.println("Erreur lors de l'extraction des langues : " + e.getMessage());
		    }
		}
	//--------------------------La fonction qui permet d'extraire les données d'offres------------------------------------//
		private void populateOffreFromDocument(Document detailDoc, Offre offre, String link) {
	        offre.title = detailDoc.selectFirst("span.ref_offre2").text();
	        offre.URL = link;
	        offre.site_name = "Anapec";
	        offre.date_publicaton = detailDoc.select("strong:contains(Date :)").first().parent().text().split("Date :")[1].trim().split("Agence :")[0].trim();
	        offre.deadline_date = "Non spécifié";
	        offre.adress = getTextAfterSpanOrStrong(detailDoc, "Agence : ");
	        offre.website = "Non spécifié";
	        offre.Company_name = detailDoc.select("strong:contains(Date :)").first().parent().text().split("Date :")[1].split("Agence :")[1].trim();
	        offre.Company_description = "Non spécifié";
	        offre.Job_Description = getTextAfterSpanOrStrong(detailDoc, "Caractéristiques du poste :");
	        offre.region = "Non spécifié";
	        offre.city = getTextAfterSpanOrStrong(detailDoc, "Lieu de travail :");
	        offre.sector = getTextAfterSpanOrStrong(detailDoc, "Secteur d’activité :");
	        offre.job_title = "Non spécifié";
	        offre.Contrat_type = getTextAfterSpanOrStrong(detailDoc, "Type de contrat :");
	        offre.education_level = "Non spécifié";
	        offre.diploma_specialization = detailDoc.select("span.small_puce:contains(Formation : )").first() != null ?
	                detailDoc.select("span.small_puce:contains(Formation : )").first().parent().select("span").last().text().trim() : "Non spécifié";
	        offre.experience_level = getTextAfterSpanOrStrong(detailDoc, "Expérience professionnelle :");
	        offre.Profile = extractProfile(detailDoc);
	        offre.personality_traits = "Non spécifié";
	        offre.required_skills = getTextAfterSpanOrStrong(detailDoc, "Bureautiques : ");
	        offre.Soft_skills = "Non spécifié";
	        offre.recommended_skills = detailDoc.select("ul.liste-affiche-offre li")
	                .eachText()
	                .stream()
	                .collect(Collectors.joining(", "));
	        extractLanguages(detailDoc, offre);
	        offre.Salary = "Non spécifié";
	        offre.benefits = "Non spécifié";
	        offre.Remote_Work = "Non spécifié";
	    }
	//-----------------------------------La fonction qui utilise les fonctions précédents pour faire le scrapping -------------------------//
		 public void scraperSite1() {
		        try {
		            List<Offre> offres = new ArrayList<>();
		            String baseUrl = "https://anapec.ma/home-page-o1/chercheur-emploi/offres-demploi/?pg=";
		            int nbrPage = 10;

		            for (int page = 1; page <= nbrPage; page++) {
		                String url = baseUrl + page;
		                Document doc = Jsoup.connect(url).get();
		                Elements allPosts = doc.getElementsByClass("offres-item");

		                for (Element post : allPosts) {
		                    try {
		                        String link = post.select("a").attr("href");
		                        if (!link.startsWith("http")) {
		                            link = "https://anapec.ma" + link;
		                        }

		                        Document detailDoc = Jsoup.connect(link).get();
		                        Offre offre = new Offre();
		                        populateOffreFromDocument(detailDoc, offre, link);

		                        offres.add(offre);
		                        insertOfferIntoDatabase(offre);
		                        System.out.println("=================================================================== " );

		                    } catch (Exception e) {
		                        System.err.println("Erreur lors du traitement d'une offre : " + e.getMessage());
		                    }
		                }
		            }

		            System.out.println("Nombre total d'offres récupérées et insérées : " + offres.size());

		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }

	public Anapecscrapper() {
		super();
	}
		 
	}
	
//===================================La classe qui permet le scrapping du site emploi.ma =================================================//
	public static class emploiScrapper{
		public void scraperSite2() {
		    try {
		        int nbrPage = 5;
		        String baseUrl = "https://www.emploi.ma/recherche-jobs-maroc"; // URL de base pour la pagination
		        for (int page = 1; page <= nbrPage; page++) {
		            String url = (page == 1) ? baseUrl : baseUrl + "?page=" + page;
		            
		            Document doc = Jsoup.connect(url).get();
		            Elements allPosts = doc.getElementsByClass("card card-job featured");

		            for (Element eachPost : allPosts) {
		                try {
		                    String link = eachPost.attr("data-href");
		                    Document detailDoc = Jsoup.connect(link).get();
		                    Offre offre = new Offre();
		                    offre.URL = link;
		                    offre.site_name = "Emploi.ma";
		                    offre.title=eachPost.select("div.card-job-detail h3 a").text().isEmpty() ? "Non spécifié" : eachPost.select("div.card-job-detail h3 a").text();
		                    offre.Company_name = eachPost.select("div.card-job-detail a.card-job-company.company-name").text().isEmpty() 
		                    	    ? "Non spécifié" 
		                    	    : eachPost.select("div.card-job-detail a.card-job-company.company-name").text();

		                    offre.job_title = eachPost.select("div.card-job-detail h3 a").text().isEmpty() ? "Non spécifié" : eachPost.select("div.card-job-detail h3 a").text();
		                    offre.date_publicaton = eachPost.select("div.card-job-detail time").text().isEmpty() ? "Non spécifié" : eachPost.select("div.card-job-detail time").text();
		                    offre.Job_Description = detailDoc.select("div.job-description p").text().isEmpty() ? "Non spécifié" : detailDoc.select("div.job-description p").text();
		                    offre.Profile = detailDoc.select("div.job-qualifications ul li").text().isEmpty() ? "Non spécifié" : detailDoc.select("div.job-qualifications ul li").text();
		                    offre.sector=detailDoc.select("ul.arrow-list li:has(strong:contains(Secteur d´activité)) span").text().isEmpty() ? "Non spécifié" : detailDoc.select("ul.arrow-list li:has(strong:contains(Secteur d´activité)) span").text();
		                    offre.Contrat_type = detailDoc.select("ul.arrow-list li:has(strong:contains(Type de contrat)) span").text().isEmpty() ? "Non spécifié" : detailDoc.select("ul.arrow-list li:has(strong:contains(Type de contrat)) span").text();
		                    offre.education_level=detailDoc.select("ul.arrow-list li:has(strong:contains(Type de contrat)) span").text().isEmpty() ? "Non spécifié" : detailDoc.select("ul.arrow-list li:has(strong:contains(Type de contrat)) span").text();
		                    // Ville
		                    offre.city = detailDoc.select("ul.arrow-list li:has(strong:contains(Ville)) span").text().isEmpty() ? "Non spécifié" : detailDoc.select("ul.arrow-list li:has(strong:contains(Ville)) span").text();
		                    offre.region= detailDoc.select("ul.arrow-list li:has(strong:contains(Région)) span").text().isEmpty() ? "Non spécifié" : detailDoc.select("ul.arrow-list li:has(strong:contains(Région)) span").text();

		                    // Travail à distance
		                    offre.Remote_Work = detailDoc.select("ul.arrow-list li:has(strong:contains(Travail à distance)) span").text().isEmpty() ? "Non spécifié" : detailDoc.select("ul.arrow-list li:has(strong:contains(Travail à distance)) span").text();

		                    // Salaire proposé
		                    offre.Salary = detailDoc.select("ul.arrow-list li:has(strong:contains(Salaire proposé)) span").text().isEmpty() ? "Non spécifié" : detailDoc.select("ul.arrow-list li:has(strong:contains(Salaire proposé)) span").text();

		                    // Langues exigées (logique à adapter)
		                    Elements languages = detailDoc.select("ul.arrow-list li:has(strong:contains(Langues exigées)) span");
		                    StringBuilder languageDetails = new StringBuilder();
		                    for (Element l : languages) {
		                        String text = l.text();
		                        String[] parts = text.split(" > ");
		                        String language = parts[0].trim();
		                        String level = parts.length > 1 ? parts[1].trim() : "Non spécifié";
		                        languageDetails.append(language).append(" (").append(level).append("), ");
		                    }
		                    offre.required_skills = languageDetails.toString().isEmpty() ? "Non spécifié" : languageDetails.toString();

		                    // Insertion dans la base de données
		                    insertOfferIntoDatabase(offre);
		                    System.out.println("=================================================================== ");

		                } catch (Exception e) {
		                    System.err.println("Erreur lors du traitement d'une offre : " + e.getMessage());
		                }
		            }
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}


			public emploiScrapper() {
				super();
			}
		
		
		}
		
//===========================================La classe qui permet de scrapper rekrute ======================================================//
	public static class rekruteScrapper{
		public void scrapersite3() {
		    try {
		        int nbrPage = 17; 
		        String baseUrl = "https://www.rekrute.com/offres.html?p=";

		        for (int page = 1; page <= nbrPage; page++) {
		            String url = baseUrl + page + "&s=1&o=1&positionId%5B0%5D=13";
		            Document doc = Jsoup.connect(url).get();
		            Elements allPost=doc.select("li.post-id");
		          
		            for (Element l : allPost) {
		                Offre offre = new Offre();
		                offre.site_name="Rekrute";
		                
		                String company_name=l.select("a > img").attr("alt");
		                offre.Company_name=company_name;
		                
		                String Titre=l.select("div.section > h2").text();
		                offre.title=Titre;
		                offre.job_title=Titre;
		                
		                String Date_publicaton = l.select("em.date").text() ;
		                offre.date_publicaton=Date_publicaton;
		                offre.deadline_date=Date_publicaton;
		                
		                
		                
		                Element secteurActivite = doc.selectFirst("li:matchesOwn(Secteur d\\'activité)");
		                offre.sector = secteurActivite.text();
		                
		                Element fonction = doc.selectFirst("li:matchesOwn(Fonction)");
		                offre.diploma_specialization=fonction.text();
		                
		                Element experience = doc.selectFirst("li:matchesOwn(Expérience requise)");
		                offre.experience_level=experience.text();
		                
		                Element niveauEtude = doc.selectFirst("li:matchesOwn(Niveau d\\'étude demandé)");
		                offre.education_level=niveauEtude.text();
		                
		                Element typeContrat = doc.selectFirst("li:matchesOwn(Type de contrat proposé)");
		                if (typeContrat != null) {
		                    String typeContratTexte = typeContrat.text(); 
		                    
		                    String contrat = null;
		                    String teletravail = null;
		                    
		                    if (typeContratTexte.contains("- Télétravail :")) {
		                        String[] parts = typeContratTexte.split("- Télétravail :");
		                        contrat = parts[0].trim();
		                        teletravail = parts[1].trim();
		                        offre.Contrat_type=contrat;
			                    offre.Remote_Work=teletravail;
		                    }
		                    else {
		                    	offre.Contrat_type=typeContrat.text();
		                    }
		                }
		                
		                String link = l.select("a.titreJob").attr("href");
		                String Link="https://www.rekrute.com/"+link;
		                offre.URL=Link;
		                if(Link!=null && !Link.isEmpty()) {
		                	Document docAnnonce = Jsoup.connect(Link).get();
			                Elements infos1=docAnnonce.select("div.col-md-8.col-sm-12.col-xs-12");
			                Elements Competences =infos1.select("div.col-md-12.blc .tagSkills");
			                if (!Competences.isEmpty()) {
			                	StringBuilder skills = new StringBuilder();
			                	for (Element span : Competences) {
			                	    String skill = span.text(); 
			                	    if (skills.length() > 0) {
			                	        skills.append(", ");  
			                	    }
			                	    skills.append(skill);  
			                	}
			                	offre.required_skills=skills.toString();
			                	offre.personality_traits=skills.toString();
			                }
			                
			                
			                Elements infis2 = docAnnonce.select("div.contentbloc > div#recruiterDescription");
			                if(!infis2.isEmpty()) {
			                	offre.Company_description=infis2.text();
			                }
			                
			                			                
			                Elements infos2 = docAnnonce.select("div.contentbloc");
			                Element poste = infos2.select("div.col-md-12.blc h2:contains(Poste :) + p").first();
			                String posteDescription = poste != null ? poste.text() : null;
			                offre.Job_Description=posteDescription;

			                
			                Element profil = infos2.select("div.col-md-12.blc h2:contains(Profil recherché :) + p ").first();
			                String profilDescription = profil != null ? profil.text() : null;
			                offre.Profile=profilDescription;

		                }
		                insertOfferIntoDatabase(offre);
	                    System.out.println("=================================================================== ");

		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		}
//=============================================La classe pour scrapper Marocannonces=====================================//
	public static class marocanoncesScrapper{		
		public void Scrappersite4() {
		    try {
		    	
		        int np = 18; // Vous pouvez ajuster cette valeur selon le nombre de pages à scraper
		        for (int page = 1; page <= np; page++) {
		            String urL = "https://www.marocannonces.com/categorie/309/Emploi/Offres-emploi";
		            String Url = page == 1 ? urL + ".html" : urL + "/" + page + ".html"; // Utilisation de "page" au lieu de "np"
		            Document doc = Jsoup.connect(Url).get();
		            Elements cartes = doc.select("div.content_box li ");
		            
		            for (Element c : cartes) {
		            
		                Offre offre = new Offre();
		                offre.site_name=" Marocannonces.com";
		                
		                String l = c.select("a").attr("href");
		                String link = "https://www.marocannonces.com/" + l + "#google_vignette";
		                offre.URL=link;
		                
		                String titre = c.select("a").attr("title");
		                offre.title=titre.isEmpty()?"Non spécifié":titre;
		                
		                String niveau_etude = c.select("a div.holder div.niveauetude ").text();
		                offre.education_level=niveau_etude.isEmpty()?"Non spécifié":niveau_etude;
		                
		                String salaire = c.select("a div.holder div.salary").text();
		                offre.Salary=salaire.isEmpty()?"Non spécifié":salaire;
		                
		                String location = c.select("a div.holder span.location").text();
		                offre.city=location.isEmpty()?"Non spécifié":location;
		                
		                String date_publication = c.select("div.time em.date").text();
		                offre.date_publicaton=date_publication.isEmpty()?"Non spécifié":date_publication;
		                
		                Document detail = Jsoup.connect(link).get();
		                
		                Elements infod = detail.select("div#content > div.used-cars >div.description ");
		                
		                String poste_titre= infod.select("h1").text();
		                offre.job_title=poste_titre.isEmpty()?"Non spécifié":poste_titre;
		                
		                String job_description=infod.select("div.block").text();
		                offre.Job_Description=job_description.isEmpty()?"Non spécifié":job_description;
		                
		                String profil=infod.select("div.block ").text();
		                offre.Profile=profil.isEmpty()?"Non spécifié":profil;
		                
		                String Domaine = infod.select("ul.extraQuestionName li:contains(Domaine) a").text();
		                offre.sector=Domaine.isEmpty()?"Non spécifié":Domaine;
		                
		                String Formation = infod.select("ul.extraQuestionName li:contains(Fonction) a").text();
		                offre.diploma_specialization=Formation.isEmpty()?"Non spécifié":Formation;
		                
		                String contrat = infod.select("ul.extraQuestionName li:contains(Contrat) a").text();
		                offre.Contrat_type=contrat.isEmpty()?"Non spécifié":contrat;
		                
		                String Company_name=infod.select("ul.extraQuestionName li:contains(Entreprise) a").text();
		                offre.Company_name=Company_name.isEmpty()?"Non spécifié":Company_name;
		                
		                insertOfferIntoDatabase(offre);
		                System.out.println("=================================================================== ");
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	}

	
//=============================================La classe qui permet de scrapper le sites moncallcebter=======================================//
	public static class monCallCenter{
		public void Scrappersite5() {
		    try {
		    	int nb=20;
		    	for(int page=1 ;page<=nb;page++) {
		    		
		    		String url="https://www.moncallcenter.ma/offres-emploi/"+page+"/";
		    		Document doc=Jsoup.connect(url).get();
		    		Elements cards=doc.select("div.divoffres");
		    		
		    		for(Element c:cards) {
		    			Offre offre = new Offre();
		    			
		    			Element spanElement = c.select("div.col-md-10.col-xs-10 span").first(); // Sélectionner la balise <span> contenant la date et le lieu
		    			String date = "";
		    			String location = "";
		    			if (spanElement != null) {
		    			    Element dateElement = spanElement.select("b").first();
		    			    if (dateElement != null) {
		    			        date = dateElement.text().trim(); 
		    			    }
		    			    Element locationElement = spanElement.select("b[style]").last();  // Sélectionne le dernier <b> avec le style
		    			    if (locationElement != null) {
		    			        location = locationElement.text().trim();  
		    			    }
		    			}
		    			offre.date_publicaton=date;
		    			offre.city=location;
		    			
		    			
		    			offre.site_name="www.moncallcenter.ma";
		    			
		    			String job_title=c.select("h2 a").text();
		    			offre.title=job_title.isEmpty()?"Non spécifié":job_title;
		    			
		    			offre.job_title=job_title;
		    			
		    			String l = c.select("a.offreUrl").attr("href");
		    			String link="https://www.moncallcenter.ma"+l;
		    			offre.URL=link;
		    			
		    			
		    			Document Annonce=Jsoup.connect(link).get();
		    			
		    			Element languesElement = Annonce.select("span:contains(Langue(s) :)").first();
		    			StringBuilder langues = new StringBuilder();
		    			if (languesElement != null) {
		    			    Elements langueLinks = languesElement.select("a");
		    			    for (Element linkkk : langueLinks) {
		    			        String langue = linkkk.text().replace("#", "").trim(); 
		    			        if (!langue.isEmpty()) {
		    			            langues.append(langue).append(", ");
		    			        }
		    			    }
		    			    if (langues.length() > 0) {
		    			        langues.setLength(langues.length() - 2);  
		    			    }
		    			}
		    			offre.Languages=langues.toString();
		    			
		    			Elements infos= Annonce.select("div#centreLeftN");
		    			String entreprise=infos.select("h2 a").text();
		    			offre.Company_name=entreprise.isEmpty()?"Non spécifié":entreprise;
		    			
		    			String job_description = "";
		    			Element descriptionTitle = infos.select("h3:contains(Descriptif du poste)").first();
		    			if (descriptionTitle != null) {
		    			    Element descriptionContent = descriptionTitle.nextElementSibling();
		    			    if (descriptionContent != null && descriptionContent.tagName().equals("p")) {
		    			        job_description = descriptionContent.text().replaceAll("\n", " ").trim();  
		    			    }
		    			}
		    			offre.Job_Description=job_description;
		    			
		    			String Profile=" ";
		    			Element despro = infos.select("h3:contains(Profil Recherché)").first();
		    			if (despro != null) {
		    			    Element descriptionConten = despro.nextElementSibling();
		    			    if (descriptionConten != null && descriptionConten.tagName().equals("p")) {
		    			    	Profile = descriptionConten.text().replaceAll("\n", " ").trim();  
		    			    }
		    			}
		    			offre.Profile=Profile;
		    			
		    			
		    			String avantages=" ";
		    			Element av = infos.select("h3:contains(Avantages sociaux et autres)").first();
		    			if (av != null) {
		    			    Element descriptionConte = av.nextElementSibling();
		    			    if (descriptionConte != null && descriptionConte.tagName().equals("p")) {
		    			    	avantages = descriptionConte.text().replaceAll("\n", " ").trim();  
		    			    }
		    			}
		    			offre.benefits=avantages;
		    			
		    			String salaire=" ";
		    			Element sa = infos.select("h3:contains(Salaire Net + primes)").first();
		    			if (sa != null) {
		    			    Element descriptionCont = sa.nextElementSibling();
		    			    if (descriptionCont != null && descriptionCont.tagName().equals("p")) {
		    			    	salaire = descriptionCont.text().replaceAll("\n", " ").trim();  
		    			    }
		    			}
		    			offre.benefits=salaire;
		    			
		    			insertOfferIntoDatabase(offre);
		                System.out.println("=================================================================== ");
		    				
		    		}
		    	}
		    
		    }catch (Exception e) {
        e.printStackTrace();
    }
	}
	}

	
//=========================================La classe qui permet de scrapper le site talentspartners.com====================================================================================================================//
	public static class talentspartners{
		   public void  Scrappersites6() {
		    	try {
			    	int nb =6;
			    	for(int page=1 ; page<=nb ; page++) {
			    		
			    		String linkpage="https://talentspartners.com/jobs-modern-list/?page_job="+page;
			    		Document pagedoc=Jsoup.connect(linkpage).get();
			    		Elements cards=pagedoc.select("ul.jobs-listing.modern > li");
			    		//
			    		String annoncelinkk=cards.get(0).select("figure > a").attr("href");
			    		Document annoncedetaill=Jsoup.connect(annoncelinkk).get();
			    		Element infospublicationn = annoncedetaill.select("div.page-section").first();
		    	        Element infosjobb = annoncedetaill.select("div.page-section").get(1);
		    	        String profill = infosjobb.select("p:contains(Profil:) + ul li").stream()
		    	        	    .map(Element::text)
		    	        	    .collect(Collectors.joining("\n- ", "- ", ""));
		    	        
		    	        
			    		System.out.println("===============================");
			    		System.out.println(profill);
			    		
			    		//
			    		for(Element c:cards) {
			    			
			    			Offre offre=new Offre();
			    			
			    			offre.site_name="talentspartners.com";
			    			
			    			String datePublished = c.select("ul  li:has(span:contains(Publié :))").text().replace("Publié :", "").trim();
			    			offre.date_publicaton=datePublished;
			    			
			    	        String sector = c.select("ul > li:has(span:contains(Secteur d\\'activité))").text().replace("Secteur d'activité", "").trim();
			    	        offre.sector=sector;
			    			
			    	        String address = c.select("ul > li:has(span:contains(Adresse))").text().replace("Adresse", "").trim();
			    	        offre.city=address;
			    	        
			    	        String contrat = c.select("a.jobtype-btn").text();
			    	        offre.Contrat_type=contrat;
			    	        
			    	        String tilte=c.select("h3").text();
			    	        offre.job_title=tilte;
			    	        offre.title=tilte;
			    		
			    	        String annoncelink=c.select("figure > a").attr("href");
			    	        offre.URL=annoncelink;
			    	        
			    	        Document annoncedetail=Jsoup.connect(annoncelink).get();
			    	        Element infospublication = annoncedetail.select("div.page-section").first();
			    	        Element infosjob = annoncedetail.select("div.page-section").get(1);
			    	        
			    	        String deadline=infospublication.select("li.application-deadline-date span").text();
			    	        offre.deadline_date=deadline;
			    	        
			    	        String jobDescription = infosjob.select("h6:contains(Description du poste)").stream()
			    	        	    .findFirst()
			    	        	    .map(h6 -> h6.parent().select("ul li").stream()
			    	        	        .map(Element::text)
			    	        	        .collect(Collectors.joining("\n- ", "- ", "")))
			    	        	    .orElse("Description non trouvée");
			    	        offre.Job_Description=jobDescription;
			    	        
			    	        String profil = infosjob.select("p:contains(Profil:) + ul li").stream()
			    	        	    .map(Element::text)
			    	        	    .collect(Collectors.joining("\n- ", "- ", ""));
			    	        
			    	        offre.Profile=profil;
			    	        
			    	        insertOfferIntoDatabase(offre);
			                System.out.println("=================================================================== ");
			    		}
			    }
			    	
			    }catch (Exception e) {
			        e.printStackTrace();
			    }
		    }
	}

//============================================La classe qui pemet de scrapper marocemploi.net==================================================================//
	public static class marocemploiscrapper{
		public void scrappersite7() {
			try {
				int nb =25;
				for(int page=1 ; page<=nb ;page++) {
					String URL="https://marocemploi.net/offre/?ajax_filter=true&job_page="+page;
					Document doc = Jsoup.connect(URL).get();
					Elements cards=doc.select("li.jobsearch-column-12");
					
					
					for(Element c:cards) {
						Offre offre= new Offre();
						offre.site_name="marocemploi.net";
						
						String title=c.select("h2.jobsearch-pst-title").text();
						offre.title=title;
										
						String Company_name=c.select("li.job-company-name").text();
						offre.Company_name=Company_name;
						
						String date_publication ="";
						Elements publicationDates = c.select("li > i.jobsearch-icon.jobsearch-calendar");
			            for (Element icon : publicationDates) {
			                String publicationDate = icon.parent().text();
			                date_publication=publicationDate;
			            }
						offre.date_publicaton=date_publication;
						
						String adress="";
						Elements vil = c.select("li > i.jobsearch-icon.jobsearch-maps-and-flags");
			            for (Element icon : vil) {
			                String v = icon.parent().text();
			                adress=v;
			            }
			            offre.adress=adress;
			            offre.city=adress;
			           
			            String secteur=c.select("li:has(i.jobsearch-icon.jobsearch-filter-tool-black-shape) >a").text();
			            offre.sector=secteur;
			            
			            String contrat=c.select("a.jobsearch-option-btn").text();
			            offre.Contrat_type=contrat;
			            
			            String link=c.select("h2.jobsearch-pst-title > a").attr("href");
			            offre.URL=link;
			            
			            if(link!=null && !link.isEmpty()) {
			            	Document infosannonce=Jsoup.connect(link).get();
				            Elements infospost=infosannonce.select("div.jobsearch-row");
				            Elements infoposte=infospost.select("div.jobsearch-column-12");
				            
				            Elements dateElements = infoposte.select("li:has(i.jobsearch-icon.jobsearch-calendar)");
				            for (Element li : dateElements) {
				                String text = li.text();
				                if (text.contains("Postuler Avant :")) {
				                    String postulerAvant = text.split("Postuler Avant :")[1].trim();
				                    offre.deadline_date=postulerAvant;
				                }
				            }
				            
				            Elements infojob = infospost.select("div.jobsearch-description");
				            Element missionsElement = infojob.select("h3:contains(Vos missions) + ul").first();
				            StringBuilder missions = new StringBuilder();
				            if (missionsElement != null) {
				                for (Element li : missionsElement.select("li")) {
				                    missions.append(li.text()).append("\n");
				                }
				            offre.Job_Description=missions.toString();
				            }
				            

				            
				            Element profileElement = infojob.select("h3:contains(Profil recherché) + ul").first();
				            StringBuilder profile = new StringBuilder();
				            if (profileElement != null) {
				                for (Element li : profileElement.select("li")) {
				                    profile.append(li.text()).append("\n");
				                }
				            offre.Profile=profile.toString();
				            }
				            
				            
				            Element competencesElement =infojob.select("h4:contains(Compétences) + ul").first();
				            StringBuilder competences = new StringBuilder();
				            if (competencesElement != null) {
				                for (Element li : competencesElement.select("li")) {
				                    competences.append(li.text()).append("\n");
				                }
				            }
				            offre.required_skills=competences.toString();
				            

				           
				            Element educationElement = infojob.select("h4:contains(Niveau d’études) + p").first();
				            String education = "";
				            if (educationElement != null) {
				                education = educationElement.text();
				            }
				            offre.education_level=education.toString();
				            			            
				            insertOfferIntoDatabase(offre);
			                System.out.println("=================================================================== ");
				            
				            System.out.println("=================================");

			            }
			            else {
			            	continue;
			            }
					}}
				
			}catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	}
	

	}

