import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ModelTest {
	@Test
	public void  ClassificationTest() throws Exception {
		Model.classifier();
	}
	
	@Test
	public void  RegressionTest() throws Exception {
		Model.regresser();
	}
	
	@Test
	public void  ClusteringTest() throws Exception {
		Model.clustering();
	}
}
