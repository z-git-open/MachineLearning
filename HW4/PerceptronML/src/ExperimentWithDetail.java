import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import Helper.Vector;
import Helper.DataHelper;
import Helper.MathHelper;
import Perceptron.*;

public class ExperimentWithDetail {

	String debugFile = "data0/debug.txt";
	String train010 = "data0/train0.10";
	String test010 = "data0/test0.10";
	String table1 = "data0/table1";
	String table2 = "data0/table2";
	double min_weight = -1;
	double max_weight = 1;
	double[] learningRates = new double[]{0.01, 0.02, 0.03, 0.04, 0.05, 0.08, 0.1, 0.25, 0.5, 0.75, 1};
	double margin = 0.3;
	

	public void Run_Table2(){
		System.out.println("----- Running Perceptron against table 2 -----");
		System.out.println();
		ArrayList<Vector> trainingData = LoadDataVector(table2);
		ArrayList<Vector> testData = LoadDataVector(table2);
		
		int dimension = trainingData.get(0).GetVectorDimension();
		Vector init_w = MathHelper.RandomizeVector(dimension, this.min_weight, this.max_weight);
		int epoch = 1;
		boolean shuffle = false;
		for(double r : learningRates){
			StdPerceptron perceptron = new StdPerceptron(init_w, r, epoch, shuffle);

			perceptron.Train(trainingData);

			perceptron.Predict(testData);
			
			perceptron.DisplayStatus();
			System.out.println();
		}
		
		System.out.println("----- END: Running Perceptron against table 2 -----");
	}

	
	
	public void Run_StdPerceptron_Data010_trainData_as_testData(){
		System.out.println("----- Running Standard Perceptron against data0.10, train data also used as test data  -----");
		System.out.println();
		ArrayList<Vector> trainingData = LoadDataVector(this.train010);
		
		ArrayList<Vector> testData = LoadDataVector(this.train010);
		
		int dimension = trainingData.get(0).GetVectorDimension();
		Vector init_w = MathHelper.RandomizeVector(dimension, this.min_weight, this.max_weight);
		int epoch = 1;
		boolean shuffle = false;
		
		for(double r : learningRates){
			StdPerceptron perceptron = new StdPerceptron(init_w, r, epoch, shuffle);

			perceptron.Train(trainingData);

			perceptron.Predict(testData);
			
			perceptron.DisplayStatus();
			System.out.println();
		}
		
		System.out.println("----- END: Running Standard Perceptron against data0.10, train data also used as test data  -----");
	}
	
	
	public void Run_MarginPerceptron_Data010_trainData_as_testData(){

		System.out.println("----- Running Margin Perceptron against data0.10, train data also used as test data  -----");
		System.out.println();
		ArrayList<Vector> trainingData = LoadDataVector(this.train010);
		
		ArrayList<Vector> testData = LoadDataVector(this.train010);
		
		int dimension = trainingData.get(0).GetVectorDimension();
		Vector init_w = MathHelper.RandomizeVector(dimension, this.min_weight, this.max_weight);
		int epoch = 1;
		boolean shuffle = false;
		
		for(double r : learningRates){
			MarginPerceptron perceptron = new MarginPerceptron(init_w, r, epoch, shuffle, this.margin);

			perceptron.Train(trainingData);

			perceptron.Predict(testData);
			
			perceptron.DisplayStatus();
			System.out.println();
		}
		
		System.out.println("----- END: Running Margin Perceptron against data0.10, train data also used as test data  -----");
	}

	
	public void Run_StdPerceptron_Data010(){
		System.out.println("----- Running Standard Perceptron against data0.10  -----");
		System.out.println();
		ArrayList<Vector> trainingData = LoadDataVector(this.train010);
		
		ArrayList<Vector> testData = LoadDataVector(this.test010);
		
		int dimension = trainingData.get(0).GetVectorDimension();
		Vector init_w = MathHelper.RandomizeVector(dimension, this.min_weight, this.max_weight);
		int epoch = 1;
		boolean shuffle = false;
		
		for(double r : learningRates){
			StdPerceptron perceptron = new StdPerceptron(init_w, r, epoch, shuffle);

			perceptron.Train(trainingData);

			perceptron.Predict(testData);
			
			perceptron.DisplayStatus();
			System.out.println();
		}
		
		System.out.println("----- END: Running Standard Perceptron against data0.10  -----");
	}
	
	
	public void Run_MarginPerceptron_Data010(){

		System.out.println("----- Running Margin Perceptron against data0.10  -----");
		System.out.println();
		ArrayList<Vector> trainingData = LoadDataVector(this.train010);
		
		ArrayList<Vector> testData = LoadDataVector(this.test010);
		
		int dimension = trainingData.get(0).GetVectorDimension();
		Vector init_w = MathHelper.RandomizeVector(dimension, this.min_weight, this.max_weight);
		
		int epoch = 1;
		boolean shuffle = false;

		for(double r : learningRates){
			MarginPerceptron perceptron = new MarginPerceptron(init_w, r, epoch, shuffle, this.margin);

			perceptron.Train(trainingData);

			perceptron.Predict(testData);
			
			perceptron.DisplayStatus();
			System.out.println();
		}
		
		System.out.println("----- END: Running Margin Perceptron against data0.10  -----");
	}
	
	
	
	
	public void Run_StdPerceptron_all_data_0(){
		System.out.println("----- Running Standard Perceptron against all data in data 0 -----");
		System.out.println();

		int min_dimension = 10; 
		int max_dimension = 100;
		double _learningRate = 0.02;
				
		for(int dimension = min_dimension; dimension <= max_dimension; dimension += 10){
			String trainFile = "data0/train0."+dimension;
			String testFile = "data0/test0."+dimension;
			System.out.println(">> Experiement on : "+trainFile+"; "+testFile);
			
			ArrayList<Vector> trainingData = LoadDataVector(trainFile);
			ArrayList<Vector> testData = LoadDataVector(testFile);
			
			int vector_dimension = trainingData.get(0).GetVectorDimension();
			Vector init_w = MathHelper.RandomizeVector(vector_dimension, this.min_weight, this.max_weight);
			int epoch = 10;
			boolean shuffle = true;

			StdPerceptron perceptron = new StdPerceptron(init_w, _learningRate, epoch, shuffle);

			perceptron.Train(trainingData);

			perceptron.Predict(testData);

			perceptron.DisplayStatus();
			
			System.out.println(">>END");
			System.out.println();
		}
		
		System.out.println("----- END: Running Standard Perceptron against all data in data 0 -----");
	}
	
	
	public void Run_StdPerceptron_all_data_1(){
		System.out.println("----- Running Standard Perceptron against all data in data 1 -----");
		System.out.println();

		int min_dimension = 10; 
		int max_dimension = 100;
		double _learningRate = 0.02;
				
		for(int dimension = min_dimension; dimension <= max_dimension; dimension += 10){
			String trainFile = "data1/train1."+dimension;
			String testFile = "data1/test1."+dimension;
			System.out.println(">> Experiment on : "+trainFile+"; "+testFile);
			
			ArrayList<Vector> trainingData = LoadDataVector(trainFile);
			ArrayList<Vector> testData = LoadDataVector(testFile);
			
			int vector_dimension = trainingData.get(0).GetVectorDimension();
			Vector init_w = MathHelper.RandomizeVector(vector_dimension, this.min_weight, this.max_weight);
			int epoch = 10;
			boolean shuffle = true;

			StdPerceptron perceptron = new StdPerceptron(init_w, _learningRate, epoch, shuffle);

			perceptron.Train(trainingData);

			perceptron.Predict(testData);

			perceptron.DisplayStatus();
			
			System.out.println(">>END");
			System.out.println();
		}
		System.out.println("----- END: Running Standard Perceptron against all data in data 1 -----");
	}
	
	
	
	
	public void Run_MarginPerceptron_all_data_0(){
		System.out.println("----- Running Margin Perceptron against all data in data 0 -----");
		System.out.println();

		int min_dimension = 10; 
		int max_dimension = 100;
		double _learningRate = 0.02;
				
		for(int dimension = min_dimension; dimension <= max_dimension; dimension += 10){
			String trainFile = "data0/train0."+dimension;
			String testFile = "data0/test0."+dimension;
			System.out.println(">> Experiement on : "+trainFile+"; "+testFile);
			
			ArrayList<Vector> trainingData = LoadDataVector(trainFile);
			ArrayList<Vector> testData = LoadDataVector(testFile);
			
			int vector_dimension = trainingData.get(0).GetVectorDimension();
			Vector init_w = MathHelper.RandomizeVector(vector_dimension, this.min_weight, this.max_weight);
			int epoch = 10;
			boolean shuffle = true;

			MarginPerceptron perceptron = new MarginPerceptron(init_w, _learningRate, epoch, shuffle, this.margin);

			perceptron.Train(trainingData);

			perceptron.Predict(testData);

			perceptron.DisplayStatus();
			
			System.out.println(">>END");
			System.out.println();
		}
		System.out.println("----- END: Running Margin Perceptron against all data in data 0 -----");
	}
	
	
	public void Run_MarginPerceptron_all_data_1(){
		System.out.println("----- Running Margin Perceptron against all data in data 1 -----");
		System.out.println();

		int min_dimension = 10; 
		int max_dimension = 100;
		double _learningRate = 0.02;
		
		for(int dimension = min_dimension; dimension <= max_dimension; dimension += 10){
			String trainFile = "data1/train1."+dimension;
			String testFile = "data1/test1."+dimension;
			System.out.println(">> Experiment on : "+trainFile+"; "+testFile);
			
			ArrayList<Vector> trainingData = LoadDataVector(trainFile);
			ArrayList<Vector> testData = LoadDataVector(testFile);
			
			int vector_dimension = trainingData.get(0).GetVectorDimension();
			Vector init_w = MathHelper.RandomizeVector(vector_dimension, this.min_weight, this.max_weight);
			int epoch = 10;
			boolean shuffle = true;

			MarginPerceptron perceptron = new MarginPerceptron(init_w, _learningRate, epoch, shuffle, this.margin);
			
			perceptron.Train(trainingData);

			perceptron.Predict(testData);

			perceptron.DisplayStatus();
			
			System.out.println(">>END");
			System.out.println();
		}
		System.out.println("----- END: Running Margin Perceptron against all data in data 1 -----");
	}
	
	
	
	
	
	public void Run_AggressiveMarginPerceptron_all_data_0(){
		System.out.println("----- Running Aggressive Margin Perceptron against all data in data 0 -----");
		System.out.println();

		int min_dimension = 10; 
		int max_dimension = 100;
				
		for(int dimension = min_dimension; dimension <= max_dimension; dimension += 10){
			String trainFile = "data0/train0."+dimension;
			String testFile = "data0/test0."+dimension;
			System.out.println(">> Experiement on : "+trainFile+"; "+testFile);
			
			ArrayList<Vector> trainingData = LoadDataVector(trainFile);
			ArrayList<Vector> testData = LoadDataVector(testFile);
			
			int vector_dimension = trainingData.get(0).GetVectorDimension();
			Vector init_w = MathHelper.RandomizeVector(vector_dimension, this.min_weight, this.max_weight);
			int epoch = 10;
			boolean shuffle = true;

			AggressiveMarginPerceptron perceptron = new AggressiveMarginPerceptron(init_w, epoch, shuffle, this.margin);

			perceptron.Train(trainingData);

			perceptron.Predict(testData);

			perceptron.DisplayStatus();
			
			System.out.println(">>END");
			System.out.println();
		}
		System.out.println("----- END: Running Aggressive Margin Perceptron against all data in data 0 -----");
	}
	
	
	
	
	
	public void Run_AggressiveMarginPerceptron_all_data_1(){
		System.out.println("----- Running Aggressive Margin Perceptron against all data in data 1 -----");
		System.out.println();

		int min_dimension = 10; 
		int max_dimension = 100;
				
		for(int dimension = min_dimension; dimension <= max_dimension; dimension += 10){
			String trainFile = "data1/train1."+dimension;
			String testFile = "data1/test1."+dimension;
			System.out.println(">> Experiment on : "+trainFile+"; "+testFile);
			
			ArrayList<Vector> trainingData = LoadDataVector(trainFile);
			ArrayList<Vector> testData = LoadDataVector(testFile);
			
			int vector_dimension = trainingData.get(0).GetVectorDimension();
			Vector init_w = MathHelper.RandomizeVector(vector_dimension, this.min_weight, this.max_weight);
			int epoch = 10;
			boolean shuffle = true;

			AggressiveMarginPerceptron perceptron = new AggressiveMarginPerceptron(init_w, epoch, shuffle, this.margin);
			
			perceptron.Train(trainingData);

			perceptron.Predict(testData);

			perceptron.DisplayStatus();
			
			System.out.println(">>END");
			System.out.println();
		}
		System.out.println("----- END: Running Aggressive Margin Perceptron against all data in data 1 -----");
	}
	
	
	
	
	
	private ArrayList<Vector> LoadDataVector(String filePath) {
		ArrayList<Vector> vectors = new ArrayList<Vector>();
		BufferedReader buffer = null;
		try {
			InputStream stream = getClass().getResourceAsStream(filePath);
			InputStreamReader streamReader = new InputStreamReader(stream);
			buffer = new BufferedReader(streamReader);
			String line = null;
			while ((line = buffer.readLine()) != null) {
				Vector v = DataHelper.ConvertToVector(line);
				if (v != null) {
					vectors.add(v);
				}
			}
			buffer.close();
		} catch (Exception e) {
		}
		return vectors;
	}
	
	
}
