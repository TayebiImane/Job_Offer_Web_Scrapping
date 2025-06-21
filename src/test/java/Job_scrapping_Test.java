import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import Annonce_scraping.Job_scrapping.Anapecscrapper;
import Annonce_scraping.Job_scrapping.emploiScrapper;
import Annonce_scraping.Job_scrapping.marocanoncesScrapper;
import Annonce_scraping.Job_scrapping.marocemploiscrapper;
import Annonce_scraping.Job_scrapping.monCallCenter;
import Annonce_scraping.Job_scrapping.rekruteScrapper;
import Annonce_scraping.Job_scrapping.talentspartners;

public class Job_scrapping_Test {
	
	 @Test
	 public void ConnexionTest() throws SQLException {
		 Job_scrapping.getConnection();
	 }
	
	 @Test
	 public void AnapecTest() {
		 Anapecscrapper anapecScraper = new Anapecscrapper();
		 anapecScraper.scraperSite1();
	 }
	 
	 @Test
	 public void EmploiTest() {
		 emploiScrapper Emploiscrapper = new emploiScrapper() ;
		 Emploiscrapper.scraperSite2();
	 }
	 
	 @Test
	 public void RekruteTest() {
		 rekruteScrapper Rekrutescrapper = new rekruteScrapper() ;
		 Rekrutescrapper.scrapersite3();
	 }
	 
	 @Test
	 public void MarocannoncesTest() {
		 marocanoncesScrapper mscrapper= new marocanoncesScrapper();
		 mscrapper.Scrappersite4();
	 }
	 
	 @Test
	 public void MoncallTest() {
		 monCallCenter callcenter=new monCallCenter();
		 callcenter.Scrappersite5();
	 }
	 
	 @Test
	 public void TalentspartenersTest() { 
		 talentspartners talentsscrap=new talentspartners();
		 talentsscrap.Scrappersites6();
	 }
	 
	 @Test
	 public void MarocemploiTest() {
		 marocemploiscrapper marocemploi=new marocemploiscrapper();
		 marocemploi.scrappersite7();
	 }
}

