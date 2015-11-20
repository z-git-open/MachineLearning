import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import Helper.Vector;
import Helper.DataHelper;
import Helper.MathHelper;
import Helper.Table;
import Perceptron.*;

public class ExperimentWithSummary {

	String debugFile = "data0/debug.txt";
	String train010 = "data0/train0.10";
	String test010 = "data0/test0.10";
	String table1 = "data0/table1";
	String table2 = "data0/table2";
	String astro_original_train = "astroOriginal/train";
	String astro_original_test = "astroOriginal/test";
	String astro_scaled_train = "astroScaled/train";
	String astro_scaled_test = "astroScaled/test";
	double min_weight = -1;
	double max_weight = 1;
	double[] learningRates = new double[]{0.01, 0.02, 0.03, 0.04, 0.05, 0.08, 0.1, 0.25, 0.5, 0.75, 1};
	double[] init_learningRates = new double[]{0.001, 0.01, 0.1, 1.0, 5.0};
	double[] relativeImportances = new double[]{0.001, 0.01, 0.1, 1.0, 10.0, 20.0, 30.0, 50.0};
	//double[] init_learningRates = new double[]{1.0};
	//double[] relativeImportances = new double[]{20.0};
	double margin = 2;
	
	
	
	
	public void Run_Report_Farthest_Point(){
		System.out.println("----- Running Report Farthest Point  -----");
		
		ArrayList<Vector> original = LoadDataVector(this.astro_original_train);
		ArrayList<Vector> scaled = LoadDataVector(this.astro_scaled_train);
		ArrayList<Vector> original_transformed = DataHelper.PairVectorElements(original);
		ArrayList<Vector> scaled_transformed = DataHelper.PairVectorElements(scaled);
		
		
		double max_distance = -1;
		System.out.println("Dataset:  Original");
		System.out.println("Computing the distance of the farthest point.");
		for(Vector v : original){
			double distance = MathHelper.VectorDistanceFromOrigin(v);
			if(MathHelper.IsLess(max_distance, distance))
				max_distance = distance;
		}
		System.out.println("Max Distance in dataset Original: "+max_distance);
		this.addMoreSpace();
		
		
		max_distance = -1;
		System.out.println("Dataset:  Original.transformed");
		System.out.println("Computing the distance of the farthest point.");
		for(Vector v : original_transformed){
			double distance = MathHelper.VectorDistanceFromOrigin(v);
			if(MathHelper.IsLess(max_distance, distance))
				max_distance = distance;
		}
		System.out.println("Max Distance in dataset Original.transformed: "+max_distance);
		this.addMoreSpace();
		
		
		max_distance = -1;
		System.out.println("Dataset:  scaled");
		System.out.println("Computing the distance of the farthest point.");
		for(Vector v : scaled){
			double distance = MathHelper.VectorDistanceFromOrigin(v);
			if(MathHelper.IsLess(max_distance, distance))
				max_distance = distance;
		}
		System.out.println("Max Distance in dataset scaled: "+max_distance);
		this.addMoreSpace();
		
		
		max_distance = -1;
		System.out.println("Dataset:  scaled.transformed");
		System.out.println("Computing the distance of the farthest point.");
		for(Vector v : scaled_transformed){
			double distance = MathHelper.VectorDistanceFromOrigin(v);
			if(MathHelper.IsLess(max_distance, distance))
				max_distance = distance;
		}
		System.out.println("Max Distance in dataset scaled.transformed: "+max_distance);
		this.addMoreSpace();
		
		System.out.println("----- END:  Running Report Farthest Point  -----");
	}
	
	
	
	public void Run_train0(){
		Table reportTable = new Table(4);
		reportTable.SetHeader(new String[]{"Learning Rate", "Final Weight Vector", "Mistakes", "Accuracy"});
		
		ArrayList<Vector> trainingData = LoadDataVector(this.train010);
		
		ArrayList<Vector> testData = LoadDataVector(this.test010);
		
		int dimension = trainingData.get(0).GetVectorDimension();
		Vector init_w = MathHelper.RandomizeVector(dimension, 0, 0);
		int epoch = 30;
		boolean shuffle = true;
		
		double init_learningRate = 0.001;
		double relativeImportance = 20.0;
		
		StochasticGradientDescent sgd = new StochasticGradientDescent(init_w, init_learningRate, relativeImportance, epoch, shuffle);
		sgd.Train(trainingData);
		sgd.Predict(testData);
		ArrayList<String> row = new ArrayList<String>();
		row.add(Double.toString(sgd.GetLearningRate()));
		row.add(sgd.GetCurrentWeightVector().toStringWithoutLabel());
		row.add(Integer.toString(sgd.GetMistakes()));
		row.add(Double.toString(sgd.GetAccumulativeAccuracy()));
		reportTable.AddRow(row);
		reportTable.DisplayTable();
	}

	public void Run_astro_original(){
		Table reportTable = new Table(4);
		reportTable.SetHeader(new String[]{"Learning Rate", "Final Weight Vector", "Mistakes", "Accuracy"});
		
		ArrayList<Vector> trainingData = LoadDataVector(this.astro_original_train);
		
		ArrayList<Vector> testData = LoadDataVector(this.astro_original_test);
		
		int dimension = trainingData.get(0).GetVectorDimension();
		Vector init_w = MathHelper.RandomizeVector(dimension, 0, 0);
		int epoch = 30;
		boolean shuffle = true;
		
		double init_learningRate = 5.0;
		double relativeImportance = 0.1;
		
		StochasticGradientDescent sgd = new StochasticGradientDescent(init_w, init_learningRate, relativeImportance, epoch, shuffle);
		sgd.Train(trainingData);
		sgd.Predict(testData);
		ArrayList<String> row = new ArrayList<String>();
		row.add(Double.toString(sgd.GetLearningRate()));
		row.add(sgd.GetCurrentWeightVector().toStringWithoutLabel());
		row.add(Integer.toString(sgd.GetMistakes()));
		row.add(Double.toString(sgd.GetAccumulativeAccuracy()));
		reportTable.AddRow(row);
		reportTable.DisplayTable();
	}
	
	public void Run_astro_original_transformed(){
		Table reportTable = new Table(4);
		reportTable.SetHeader(new String[]{"Learning Rate", "Final Weight Vector", "Mistakes", "Accuracy"});
		
		ArrayList<Vector> trainingData = LoadDataVector(this.astro_original_train);
		ArrayList<Vector> testData = LoadDataVector(this.astro_original_test);
		ArrayList<Vector> train_transformed = DataHelper.PairVectorElements(trainingData);
		ArrayList<Vector> test_transformed = DataHelper.PairVectorElements(testData);
		
		int dimension = train_transformed.get(0).GetVectorDimension();
		Vector init_w = MathHelper.RandomizeVector(dimension, 0, 0);
		int epoch = 30;
		boolean shuffle = true;
		
		double init_learningRate = 0.001;
		double relativeImportance = 0.01;
		
		StochasticGradientDescent sgd = new StochasticGradientDescent(init_w, init_learningRate, relativeImportance, epoch, shuffle);
		sgd.Train(train_transformed);
		sgd.Predict(test_transformed);
		ArrayList<String> row = new ArrayList<String>();
		row.add(Double.toString(sgd.GetLearningRate()));
		row.add(sgd.GetCurrentWeightVector().toStringWithoutLabel());
		row.add(Integer.toString(sgd.GetMistakes()));
		row.add(Double.toString(sgd.GetAccumulativeAccuracy()));
		reportTable.AddRow(row);
		reportTable.DisplayTable();
	}
	
	public void Run_astro_scaled(){
		Table reportTable = new Table(4);
		reportTable.SetHeader(new String[]{"Learning Rate", "Final Weight Vector", "Mistakes", "Accuracy"});
		
		ArrayList<Vector> trainingData = LoadDataVector(this.astro_scaled_train);
		
		ArrayList<Vector> testData = LoadDataVector(this.astro_scaled_test);
		
		int dimension = trainingData.get(0).GetVectorDimension();
		Vector init_w = MathHelper.RandomizeVector(dimension, 0, 0);
		int epoch = 30;
		boolean shuffle = true;
		
		double init_learningRate = 0.001;
		double relativeImportance = 30.0;
		
		StochasticGradientDescent sgd = new StochasticGradientDescent(init_w, init_learningRate, relativeImportance, epoch, shuffle);
		sgd.Train(trainingData);
		sgd.Predict(testData);
		ArrayList<String> row = new ArrayList<String>();
		row.add(Double.toString(sgd.GetLearningRate()));
		row.add(sgd.GetCurrentWeightVector().toStringWithoutLabel());
		row.add(Integer.toString(sgd.GetMistakes()));
		row.add(Double.toString(sgd.GetAccumulativeAccuracy()));
		reportTable.AddRow(row);
		reportTable.DisplayTable();
	}
	
	public void Run_astro_scaled_transformed(){
		Table reportTable = new Table(4);
		reportTable.SetHeader(new String[]{"Learning Rate", "Final Weight Vector", "Mistakes", "Accuracy"});
		
		ArrayList<Vector> trainingData = LoadDataVector(this.astro_scaled_train);
		ArrayList<Vector> testData = LoadDataVector(this.astro_scaled_test);
		ArrayList<Vector> train_transformed = DataHelper.PairVectorElements(trainingData);
		ArrayList<Vector> test_transformed = DataHelper.PairVectorElements(testData);
		
		int dimension = train_transformed.get(0).GetVectorDimension();
		Vector init_w = MathHelper.RandomizeVector(dimension, 0, 0);
		int epoch = 30;
		boolean shuffle = true;
		
		double init_learningRate = 0.001;
		double relativeImportance = 50.0;
		
		StochasticGradientDescent sgd = new StochasticGradientDescent(init_w, init_learningRate, relativeImportance, epoch, shuffle);
		sgd.Train(train_transformed);
		sgd.Predict(test_transformed);
		ArrayList<String> row = new ArrayList<String>();
		row.add(Double.toString(sgd.GetLearningRate()));
		row.add(sgd.GetCurrentWeightVector().toStringWithoutLabel());
		row.add(Integer.toString(sgd.GetMistakes()));
		row.add(Double.toString(sgd.GetAccumulativeAccuracy()));
		reportTable.AddRow(row);
		reportTable.DisplayTable();
	}
	
	public static double ComputeAccuracy(
			Vector init_w,
			double init_learningRate,
			double relativeImportance,
			int epoch,
			boolean reshuffle,
			ArrayList<ArrayList<Vector>> dataParts, 
			int testDataPartIndex){
		
		int parts_size = dataParts.size();
		ArrayList<Vector> trainingData = new ArrayList<Vector>();
		for(int idx = 0; idx < parts_size; idx++){
			if(idx != testDataPartIndex)
				trainingData.addAll(dataParts.get(idx));
		}
		ArrayList<Vector> testData = dataParts.get(testDataPartIndex);
		
		StochasticGradientDescent sgd = new StochasticGradientDescent(init_w, init_learningRate, relativeImportance, epoch, reshuffle);
		sgd.Train(trainingData);
		sgd.Predict(testData);
		return sgd.GetAccumulativeAccuracy();
	}

	
	public static double ComputeAvgCrossValidationAccuracy(
			Vector init_w,
			double init_learningRate,
			double relativeImportance,
			int epoch,
			boolean reshuffle,
			int kfold,
			ArrayList<Vector> trainingData){
		
		ArrayList<Double> accuracies = new ArrayList<Double>();
		
		trainingData = DataHelper.ReshuffleData(trainingData);
		
		ArrayList<ArrayList<Vector>> dataParts = DataHelper.SplitData(trainingData, kfold);
		
		int parts_size = dataParts.size();
		
		for(int idx = 0; idx < parts_size; idx++){
			
			double accuracy = ComputeAccuracy(init_w, init_learningRate, relativeImportance, epoch, reshuffle, dataParts, idx);

			accuracies.add(accuracy);
			
		}
		
		double accuracySum = 0;
		Iterator<Double> accuraciesItr = accuracies.iterator();
		while(accuraciesItr.hasNext()){
			accuracySum += accuraciesItr.next();
		}
		return accuracySum / accuracies.size();
	}
	

	
	

	public void Run_SVM_Batch(){
		System.out.println("----- Running SVM Batch -----");
		Table reportTable = new Table(3);
		reportTable.SetHeader(new String[]{"Initial Learning Rate", "Hyper-Parameter C", "Avg Cross Validation Accuracy"});
		
		ArrayList<Vector> tmp_trainingData = LoadDataVector(this.astro_scaled_train);
		ArrayList<Vector> transformed = DataHelper.PairVectorElements(tmp_trainingData);
		
		//ArrayList<Vector> testData = LoadDataVector(this.astro_original_test);
		
		int dimension = transformed.get(0).GetVectorDimension();

		Vector init_w = MathHelper.RandomizeVector(dimension, 0, 0);
		
		int epoch = 10;
		int kfold = 10;
		boolean shuffle = true;
		
		for(int i = 0; i < this.init_learningRates.length; i++){
			for(int j = 0; j < this.relativeImportances.length; j++){
				
				double avgAccuracy = ComputeAvgCrossValidationAccuracy(init_w, init_learningRates[i], relativeImportances[j], epoch, shuffle, kfold, Vector.CloneVectorSet(transformed));
				
				ArrayList<String> row = new ArrayList<String>();
				row.add(Double.toString(init_learningRates[i]));
				row.add(Double.toString(relativeImportances[j]));
				row.add(Double.toString(avgAccuracy));
				reportTable.AddRow(row);
			}
		}
		
		reportTable.DisplayTable();
		
		System.out.println("----- END: Running SVM against original Data  -----");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void Run_Table2(){
		System.out.println("----- Running Perceptron against table 2 -----");
		
		Table reportTable = new Table(5);
		reportTable.SetHeader(new String[]{"LearningRate", "InitialWeightVector", "FinalWeightVector", "Mistakes", "Accuracy"});
		
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
			
			ArrayList<String> _row = new ArrayList<String>();
			_row.add(Double.toString(r));
			_row.add(init_w.toStringWithoutLabel());
			_row.add(perceptron.GetCurrentWeightVector().toStringWithoutLabel());
			_row.add(Integer.toString(perceptron.GetMistakes()));
			_row.add(Double.toString(perceptron.GetAccumulativeAccuracy()));
			reportTable.AddRow(_row);
		}
		
		reportTable.DisplayTable();
		
		System.out.println("----- END: Running Perceptron against table 2 -----");
	}
	
	
	
	public void Run_StdPerceptron_Data010_trainData_as_testData(){
		System.out.println("----- Running Standard Perceptron against data0.10, also use data0.10 as test data -----");
		Table reportTable = new Table(4);
		reportTable.SetHeader(new String[]{"Learning Rate", "Final Weight Vector", "Mistakes", "Accuracy"});
		
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
			
			ArrayList<String> _row = new ArrayList<String>();
			_row.add(Double.toString(r));
			_row.add(perceptron.GetCurrentWeightVector().toStringWithoutLabel());
			_row.add(Integer.toString(perceptron.GetMistakes()));
			_row.add(Double.toString(perceptron.GetAccumulativeAccuracy()));
			reportTable.AddRow(_row);
		}
		
		reportTable.DisplayTable();
		
		System.out.println("----- END: Running Standard Perceptron against data0.10, also use data0.10 as test data  -----");
	}
	
	
	public void Run_MarginPerceptron_Data010_trainData_as_testData(){

		System.out.println("----- Running Margin Perceptron against data0.10, train data also used as test data  -----");
		
		Table reportTable = new Table(4);
		reportTable.SetHeader(new String[]{"Learning Rate", "Final Weight Vector", "Mistakes", "Accuracy"});
		
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
			
			ArrayList<String> _row = new ArrayList<String>();
			_row.add(Double.toString(r));
			_row.add(perceptron.GetCurrentWeightVector().toStringWithoutLabel());
			_row.add(Integer.toString(perceptron.GetMistakes()));
			_row.add(Double.toString(perceptron.GetAccumulativeAccuracy()));
			reportTable.AddRow(_row);
		}
		
		reportTable.DisplayTable();
		
		System.out.println("----- END: Running Margin Perceptron against data0.10, train data also used as test data  -----");
	}

	
	public void Run_StdPerceptron_Data010(){
		System.out.println("----- Running Standard Perceptron against data0.10  -----");
		
		Table reportTable = new Table(4);
		reportTable.SetHeader(new String[]{"Learning Rate", "Final Weight Vector", "Mistakes", "Accuracy"});
		
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
			
			ArrayList<String> _row = new ArrayList<String>();
			_row.add(Double.toString(r));
			_row.add(perceptron.GetCurrentWeightVector().toStringWithoutLabel());
			_row.add(Integer.toString(perceptron.GetMistakes()));
			_row.add(Double.toString(perceptron.GetAccumulativeAccuracy()));
			reportTable.AddRow(_row);
		}
		reportTable.DisplayTable();
		System.out.println("----- END: Running Standard Perceptron against data0.10  -----");
	}
	
	
	public void Run_MarginPerceptron_Data010(){

		System.out.println("----- Running Margin Perceptron against data0.10  -----");
		Table reportTable = new Table(4);
		reportTable.SetHeader(new String[]{"Learning Rate", "Final Weight Vector", "Mistakes", "Accuracy"});
		
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
			
			ArrayList<String> _row = new ArrayList<String>();
			_row.add(Double.toString(r));
			_row.add(perceptron.GetCurrentWeightVector().toStringWithoutLabel());
			_row.add(Integer.toString(perceptron.GetMistakes()));
			_row.add(Double.toString(perceptron.GetAccumulativeAccuracy()));
			reportTable.AddRow(_row);
		}
		reportTable.DisplayTable();
		System.out.println("----- END: Running Margin Perceptron against data0.10  -----");
	}
	
	
	
	
	public void Run_StdPerceptron_all_data_0(){
		System.out.println("----- Running Standard Perceptron against all data in data 0 -----");
		
		Table reportTable = new Table(6);
		reportTable.SetHeader(new String[]{"Dimension", "Learning Rate", "Train Data", "Test Data", "Mistakes", "Accuracy"});
		
		int min_dimension = 10; 
		int max_dimension = 100;
		double _learningRate = 0.3;
				
		for(int dimension = min_dimension; dimension <= max_dimension; dimension += 10){
			String trainFile = "data0/train0."+dimension;
			String testFile = "data0/test0."+dimension;
			
			ArrayList<Vector> trainingData = this.LoadDataVector(trainFile);
			ArrayList<Vector> testData = this.LoadDataVector(testFile);
			
			int vector_dimension = trainingData.get(0).GetVectorDimension();
			Vector init_w = MathHelper.RandomizeVector(vector_dimension, this.min_weight, this.max_weight);
			int epoch = 10;
			boolean shuffle = true;

			StdPerceptron perceptron = new StdPerceptron(init_w, _learningRate, epoch, shuffle);

			perceptron.Train(trainingData);

			perceptron.Predict(testData);

			ArrayList<String> _row = new ArrayList<String>();
			_row.add(Integer.toString(dimension));
			_row.add(Double.toString(_learningRate));
			_row.add(trainFile);
			_row.add(testFile);
			_row.add(Integer.toString(perceptron.GetMistakes()));
			_row.add(Double.toString(perceptron.GetAccumulativeAccuracy()));
			reportTable.AddRow(_row);
		}
		reportTable.DisplayTable();
		
		System.out.println("----- END: Running Standard Perceptron against all data in data 0 -----");
	}
	
	
	public void Run_StdPerceptron_all_data_1(){
		System.out.println("----- Running Standard Perceptron against all data in data 1 -----");
		Table reportTable = new Table(6);
		reportTable.SetHeader(new String[]{"Dimension", "Learning Rate", "Train Data", "Test Data", "Mistakes", "Accuracy"});
		
		int min_dimension = 10; 
		int max_dimension = 100;
		double _learningRate = 0.02;
				
		for(int dimension = min_dimension; dimension <= max_dimension; dimension += 10){
			String trainFile = "data1/train1."+dimension;
			String testFile = "data1/test1."+dimension;
			
			ArrayList<Vector> trainingData = this.LoadDataVector(trainFile);
			ArrayList<Vector> testData = this.LoadDataVector(testFile);
			
			int vector_dimension = trainingData.get(0).GetVectorDimension();
			Vector init_w = MathHelper.RandomizeVector(vector_dimension, this.min_weight, this.max_weight);
			int epoch = 10;
			boolean shuffle = true;

			StdPerceptron perceptron = new StdPerceptron(init_w, _learningRate, epoch, shuffle);

			perceptron.Train(trainingData);

			perceptron.Predict(testData);

			ArrayList<String> _row = new ArrayList<String>();
			_row.add(Integer.toString(dimension));
			_row.add(Double.toString(_learningRate));
			_row.add(trainFile);
			_row.add(testFile);
			_row.add(Integer.toString(perceptron.GetMistakes()));
			_row.add(Double.toString(perceptron.GetAccumulativeAccuracy()));
			reportTable.AddRow(_row);
		}
		reportTable.DisplayTable();
		System.out.println("----- END: Running Standard Perceptron against all data in data 1 -----");
	}
	
	
	
	
	public void Run_MarginPerceptron_all_data_0(){
		System.out.println("----- Running Margin Perceptron against all data in data 0 -----");
		Table reportTable = new Table(6);
		reportTable.SetHeader(new String[]{"Dimension", "Learning Rate", "Train Data", "Test Data", "Mistakes", "Accuracy"});
		
		int min_dimension = 10; 
		int max_dimension = 100;
		double _learningRate = 0.02;
				
		for(int dimension = min_dimension; dimension <= max_dimension; dimension += 10){
			String trainFile = "data0/train0."+dimension;
			String testFile = "data0/test0."+dimension;
			
			ArrayList<Vector> trainingData = this.LoadDataVector(trainFile);
			ArrayList<Vector> testData = this.LoadDataVector(testFile);
			
			int vector_dimension = trainingData.get(0).GetVectorDimension();
			Vector init_w = MathHelper.RandomizeVector(vector_dimension, this.min_weight, this.max_weight);
			int epoch = 10;
			boolean shuffle = true;

			MarginPerceptron perceptron = new MarginPerceptron(init_w, _learningRate, epoch, shuffle, this.margin);

			perceptron.Train(trainingData);

			perceptron.Predict(testData);

			ArrayList<String> _row = new ArrayList<String>();
			_row.add(Integer.toString(dimension));
			_row.add(Double.toString(_learningRate));
			_row.add(trainFile);
			_row.add(testFile);
			_row.add(Integer.toString(perceptron.GetMistakes()));
			_row.add(Double.toString(perceptron.GetAccumulativeAccuracy()));
			reportTable.AddRow(_row);
		}
		reportTable.DisplayTable();
		System.out.println("----- END: Running Margin Perceptron against all data in data 0 -----");
	}
	
	
	public void Run_MarginPerceptron_all_data_1(){
		System.out.println("----- Running Margin Perceptron against all data in data 1 -----");
		Table reportTable = new Table(6);
		reportTable.SetHeader(new String[]{"Dimension", "Learning Rate", "Train Data", "Test Data", "Mistakes", "Accuracy"});
		
		int min_dimension = 10; 
		int max_dimension = 100;
		double _learningRate = 0.02;
		
		for(int dimension = min_dimension; dimension <= max_dimension; dimension += 10){
			String trainFile = "data1/train1."+dimension;
			String testFile = "data1/test1."+dimension;
			
			ArrayList<Vector> trainingData = this.LoadDataVector(trainFile);
			ArrayList<Vector> testData = this.LoadDataVector(testFile);
			
			int vector_dimension = trainingData.get(0).GetVectorDimension();
			Vector init_w = MathHelper.RandomizeVector(vector_dimension, this.min_weight, this.max_weight);
			int epoch = 10;
			boolean shuffle = true;

			MarginPerceptron perceptron = new MarginPerceptron(init_w, _learningRate, epoch, shuffle, this.margin);
			
			perceptron.Train(trainingData);

			perceptron.Predict(testData);

			ArrayList<String> _row = new ArrayList<String>();
			_row.add(Integer.toString(dimension));
			_row.add(Double.toString(_learningRate));
			_row.add(trainFile);
			_row.add(testFile);
			_row.add(Integer.toString(perceptron.GetMistakes()));
			_row.add(Double.toString(perceptron.GetAccumulativeAccuracy()));
			reportTable.AddRow(_row);
		}
		reportTable.DisplayTable();
		System.out.println("----- END: Running Margin Perceptron against all data in data 1 -----");
	}
	
	
	
	
	
	public void Run_AggressiveMarginPerceptron_all_data_0(){
		System.out.println("----- Running Aggressive Margin Perceptron against all data in data 0 -----");
		Table reportTable = new Table(6);
		reportTable.SetHeader(new String[]{"Dimension", "Learning Rate", "Train Data", "Test Data", "Mistakes", "Accuracy"});
		
		int min_dimension = 10; 
		int max_dimension = 100;
		double _learningRate = 0.02;
				
		for(int dimension = min_dimension; dimension <= max_dimension; dimension += 10){
			String trainFile = "data0/train0."+dimension;
			String testFile = "data0/test0."+dimension;
			
			ArrayList<Vector> trainingData = this.LoadDataVector(trainFile);
			ArrayList<Vector> testData = this.LoadDataVector(testFile);
			
			int vector_dimension = trainingData.get(0).GetVectorDimension();
			Vector init_w = MathHelper.RandomizeVector(vector_dimension, this.min_weight, this.max_weight);
			int epoch = 10;
			boolean shuffle = true;

			AggressiveMarginPerceptron perceptron = new AggressiveMarginPerceptron(init_w, epoch, shuffle, this.margin);

			perceptron.Train(trainingData);

			perceptron.Predict(testData);

			ArrayList<String> _row = new ArrayList<String>();
			_row.add(Integer.toString(dimension));
			_row.add(Double.toString(_learningRate));
			_row.add(trainFile);
			_row.add(testFile);
			_row.add(Integer.toString(perceptron.GetMistakes()));
			_row.add(Double.toString(perceptron.GetAccumulativeAccuracy()));
			reportTable.AddRow(_row);
		}
		reportTable.DisplayTable();
		System.out.println("----- END: Running Aggressive Margin Perceptron against all data in data 0 -----");
	}
	
	
	
	
	
	public void Run_AggressiveMarginPerceptron_all_data_1(){
		System.out.println("----- Running Aggressive Margin Perceptron against all data in data 1 -----");
		Table reportTable = new Table(6);
		reportTable.SetHeader(new String[]{"Dimension", "Learning Rate", "Train Data", "Test Data", "Mistakes", "Accuracy"});
		
		int min_dimension = 10; 
		int max_dimension = 100;
		double _learningRate = 0.02;
				
		for(int dimension = min_dimension; dimension <= max_dimension; dimension += 10){
			String trainFile = "data1/train1."+dimension;
			String testFile = "data1/test1."+dimension;
			
			ArrayList<Vector> trainingData = this.LoadDataVector(trainFile);
			ArrayList<Vector> testData = this.LoadDataVector(testFile);
			
			int vector_dimension = trainingData.get(0).GetVectorDimension();
			Vector init_w = MathHelper.RandomizeVector(vector_dimension, this.min_weight, this.max_weight);
			int epoch = 10;
			boolean shuffle = true;

			AggressiveMarginPerceptron perceptron = new AggressiveMarginPerceptron(init_w, epoch, shuffle, this.margin);
			
			perceptron.Train(trainingData);

			perceptron.Predict(testData);

			ArrayList<String> _row = new ArrayList<String>();
			_row.add(Integer.toString(dimension));
			_row.add(Double.toString(_learningRate));
			_row.add(trainFile);
			_row.add(testFile);
			_row.add(Integer.toString(perceptron.GetMistakes()));
			_row.add(Double.toString(perceptron.GetAccumulativeAccuracy()));
			reportTable.AddRow(_row);
		}
		reportTable.DisplayTable();
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
	
	
	static void addMoreSpace(){
		System.out.println();
		System.out.println();
	}
	
}
