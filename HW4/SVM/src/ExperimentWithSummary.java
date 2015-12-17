import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import Helper.Vector;
import algorithms.*;
import Helper.DataHelper;
import Helper.MathHelper;
import Helper.Table;

public class ExperimentWithSummary {

	String debugFile = "data0/debug.txt";
	String train010 = "data0/train0.10";
	String test010 = "data0/test0.10";
	String astro_original_train = "astroOriginal/train";
	String astro_original_test = "astroOriginal/test";
	String astro_scaled_train = "astroScaled/train";
	String astro_scaled_test = "astroScaled/test";
	double[] learningRates = new double[]{0.01, 0.02, 0.03, 0.04, 0.05, 0.08, 0.1, 0.25, 0.5, 0.75, 1};
	double[] init_learningRates = new double[]{0.001, 0.01, 0.1, 1.0, 5.0};
	double[] relativeImportances = new double[]{0.001, 0.01, 0.1, 1.0, 10.0, 20.0, 30.0, 50.0};
	
	
	
	
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
		addMoreSpace();
		
		
		max_distance = -1;
		System.out.println("Dataset:  Original.transformed");
		System.out.println("Computing the distance of the farthest point.");
		for(Vector v : original_transformed){
			double distance = MathHelper.VectorDistanceFromOrigin(v);
			if(MathHelper.IsLess(max_distance, distance))
				max_distance = distance;
		}
		System.out.println("Max Distance in dataset Original.transformed: "+max_distance);
		addMoreSpace();
		
		
		max_distance = -1;
		System.out.println("Dataset:  scaled");
		System.out.println("Computing the distance of the farthest point.");
		for(Vector v : scaled){
			double distance = MathHelper.VectorDistanceFromOrigin(v);
			if(MathHelper.IsLess(max_distance, distance))
				max_distance = distance;
		}
		System.out.println("Max Distance in dataset scaled: "+max_distance);
		addMoreSpace();
		
		
		max_distance = -1;
		System.out.println("Dataset:  scaled.transformed");
		System.out.println("Computing the distance of the farthest point.");
		for(Vector v : scaled_transformed){
			double distance = MathHelper.VectorDistanceFromOrigin(v);
			if(MathHelper.IsLess(max_distance, distance))
				max_distance = distance;
		}
		System.out.println("Max Distance in dataset scaled.transformed: "+max_distance);
		addMoreSpace();
		
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
		System.out.println("#### Running astro original ####");
		Table reportTable = new Table(4);
		reportTable.SetHeader(new String[]{"Learning Rate", "Final Weight Vector", "Mistakes", "Accuracy"});
		
		ArrayList<Vector> trainingData = LoadDataVector(this.astro_original_train);
		
		ArrayList<Vector> testData = LoadDataVector(this.astro_original_test);
		
		int dimension = trainingData.get(0).GetVectorDimension();
		Vector init_w = MathHelper.RandomizeVector(dimension, 0, 0);
		int epoch = 30;
		boolean shuffle = true;
		
		double init_learningRate = 5;
		double relativeImportance = 10;
		
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
		System.out.println("#### Running astro original transformed ####");
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
		
		double init_learningRate = 5;
		double relativeImportance = 0.001;
		
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
		System.out.println("#### Running astro scaled ####");
		Table reportTable = new Table(4);
		reportTable.SetHeader(new String[]{"Learning Rate", "Final Weight Vector", "Mistakes", "Accuracy"});
		
		ArrayList<Vector> trainingData = LoadDataVector(this.astro_scaled_train);
		
		ArrayList<Vector> testData = LoadDataVector(this.astro_scaled_test);
		
		int dimension = trainingData.get(0).GetVectorDimension();
		Vector init_w = MathHelper.RandomizeVector(dimension, 0, 0);
		int epoch = 30;
		boolean shuffle = true;
		
		double init_learningRate = 0.01;
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
		System.out.println("#### Running astro scaled transformed ####");
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
		
		double init_learningRate = 0.1;
		double relativeImportance = 20.0;
		
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
	

	
	

	public void Run_BestPara_astro_scaled_tranformed(){
		System.out.println("----- Running SVM Batch -----");
		Table reportTable = new Table(3);
		reportTable.SetHeader(new String[]{"Initial Learning Rate", "Hyper-Parameter C", "Avg Cross Validation Accuracy"});
		
		ArrayList<Vector> tmp_trainingData = LoadDataVector(this.astro_scaled_train);
		ArrayList<Vector> transformed = DataHelper.PairVectorElements(tmp_trainingData);
		
		
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
	
	
	public void Run_BestPara_astro_original_tranformed(){
		System.out.println("----- Running SVM Batch -----");
		Table reportTable = new Table(3);
		reportTable.SetHeader(new String[]{"Initial Learning Rate", "Hyper-Parameter C", "Avg Cross Validation Accuracy"});
		
		ArrayList<Vector> tmp_trainingData = LoadDataVector(this.astro_original_train);
		ArrayList<Vector> transformed = DataHelper.PairVectorElements(tmp_trainingData);
		
		
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
	
	public void Run_BestPara_astro_original(){
		System.out.println("----- Running SVM Batch -----");
		Table reportTable = new Table(3);
		reportTable.SetHeader(new String[]{"Initial Learning Rate", "Hyper-Parameter C", "Avg Cross Validation Accuracy"});
		
		ArrayList<Vector> trainingData = LoadDataVector(this.astro_original_train);
		
		int dimension = trainingData.get(0).GetVectorDimension();

		Vector init_w = MathHelper.RandomizeVector(dimension, 0, 0);
		
		int epoch = 10;
		int kfold = 10;
		boolean shuffle = true;
		
		for(int i = 0; i < this.init_learningRates.length; i++){
			for(int j = 0; j < this.relativeImportances.length; j++){
				
				double avgAccuracy = ComputeAvgCrossValidationAccuracy(init_w, init_learningRates[i], relativeImportances[j], epoch, shuffle, kfold, Vector.CloneVectorSet(trainingData));
				
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
	
	
	public void Run_BestPara_astro_scaled(){
		System.out.println("----- Running SVM Batch -----");
		Table reportTable = new Table(3);
		reportTable.SetHeader(new String[]{"Initial Learning Rate", "Hyper-Parameter C", "Avg Cross Validation Accuracy"});
		
		ArrayList<Vector> trainingData = LoadDataVector(this.astro_scaled_train);
		
		int dimension = trainingData.get(0).GetVectorDimension();

		Vector init_w = MathHelper.RandomizeVector(dimension, 0, 0);
		
		int epoch = 10;
		int kfold = 10;
		boolean shuffle = true;
		
		for(int i = 0; i < this.init_learningRates.length; i++){
			for(int j = 0; j < this.relativeImportances.length; j++){
				
				double avgAccuracy = ComputeAvgCrossValidationAccuracy(init_w, init_learningRates[i], relativeImportances[j], epoch, shuffle, kfold, Vector.CloneVectorSet(trainingData));
				
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
	
	
	public void Run_BestPara_data(){
		System.out.println("----- Running SVM Batch -----");
		Table reportTable = new Table(3);
		reportTable.SetHeader(new String[]{"Initial Learning Rate", "Hyper-Parameter C", "Avg Cross Validation Accuracy"});
		
		ArrayList<Vector> trainingData = LoadDataVector(this.test010);
		
		int dimension = trainingData.get(0).GetVectorDimension();

		Vector init_w = MathHelper.RandomizeVector(dimension, 0, 0);
		
		int epoch = 10;
		int kfold = 10;
		boolean shuffle = true;
		
		for(int i = 0; i < this.init_learningRates.length; i++){
			for(int j = 0; j < this.relativeImportances.length; j++){
				
				double avgAccuracy = ComputeAvgCrossValidationAccuracy(init_w, init_learningRates[i], relativeImportances[j], epoch, shuffle, kfold, Vector.CloneVectorSet(trainingData));
				
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
