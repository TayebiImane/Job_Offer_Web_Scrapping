import static org.junit.jupiter.api.Assertions.*;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.junit.jupiter.api.Test;

public class AuthetificationTest {
	@Test 
	public void Authentifier() throws Exception{
		UIManager.setLookAndFeel( new NimbusLookAndFeel() );
		Authentifier mywindow=new Authentifier();
		mywindow.setUndecorated(true);
		mywindow.setVisible(true);
	}
}
