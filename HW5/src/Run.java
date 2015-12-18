public class Run {
	
	public static void main(String[] args) {
		
		
		ExperimentWithSummary test = new ExperimentWithSummary();
		
//		test.Run_StdPerceptron_Project(false, true);
//		System.out.println("********************************");
//		System.out.println("********************************");
//
//		
//		test.Run_StdPerceptron_Project(true, true);
//		System.out.println("********************************");
//		System.out.println("********************************");
//
		test.Run_MarginPerceptron_Project(false, true);
		System.out.println("********************************");
		System.out.println("********************************");
		
		test.Run_MarginPerceptron_Project(true, true);
		System.out.println("********************************");
		System.out.println("********************************");
//		
//		test.Run_Winnom(false, true);
//		System.out.println("********************************");
//		System.out.println("********************************");
//		
//		test.Run_Winnom(true, true);
//		System.out.println("********************************");
//		System.out.println("********************************");
	}
	
	static void addMoreSpace(){
		System.out.println();
		System.out.println();
	}
	

}
