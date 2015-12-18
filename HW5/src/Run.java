public class Run {
	
	public static void main(String[] args) {
		
		
		ExperimentWithSummary test = new ExperimentWithSummary();
		
		test.Run_StdPerceptron_Project();
		System.out.println("********************************");
		System.out.println("********************************");
		//test.Run_MarginPerceptron_Project();
		test.Run_Winnom_Project();
		
		//test.Run_Report_Farthest_Point();
		//test.Run_BestPara_astro_original_tranformed();
		//test.Run_SVM_Batch();
		//test.Run_astro_scaled_transformed();
		
		
		System.out.println("********************************");
		System.out.println("********************************");
		
		
	}
	
	static void addMoreSpace(){
		System.out.println();
		System.out.println();
	}
	

}
