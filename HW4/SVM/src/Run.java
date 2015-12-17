public class Run {
	
	public static void main(String[] args) {
		
		
		ExperimentWithSummary test = new ExperimentWithSummary();
		test.Run_astro_original();
		test.Run_astro_original_transformed();
		test.Run_astro_scaled();
		test.Run_astro_scaled_transformed();
		//test.Run_astro_original_transformed();
		//test.Run_astro_scaled();
		//test.Run_astro_scaled_transformed();
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
