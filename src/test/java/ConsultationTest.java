import static org.junit.jupiter.api.Assertions.*;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.junit.jupiter.api.Test;

public class ConsultationTest {
	@Test
	public void Consultation() throws Exception {
		UIManager.setLookAndFeel( new NimbusLookAndFeel() );
        new Consult("name");
	}
	
	@Test
	public void ConsultationpPretraité() throws Exception {
		UIManager.setLookAndFeel( new NimbusLookAndFeel() );
        new Consult2("name");
	}
}
