import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class VisualisationTest {
	@Test
	public void GrapheTest() {
		new SalaryByContractTypeChart();
		new SalaryBySectorChart();
		new SalaryExperienceLineChart ();
	}
}
