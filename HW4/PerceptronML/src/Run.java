public class Run {
	
	public static void main(String[] args) {
		
		
		ExperimentWithSummary test = new ExperimentWithSummary();
		//test.Run_Report_Farthest_Point();
		//test.Run_SVM_Batch();
		test.Run_astro_scaled_transformed();
		
		
		System.out.println("********************************");
		System.out.println("********************************");
		System.out.println("******All Experiments Done******");
		System.out.println("********************************");
		System.out.println("********************************");
		
	}
	
	static void addMoreSpace(){
		System.out.println();
		System.out.println();
	}
	

}
