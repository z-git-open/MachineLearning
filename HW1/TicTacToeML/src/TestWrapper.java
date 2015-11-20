import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import DecisionTree.ID3DecisionTree;
import Helper.Vector;
import KNN.CrossValidation;

public class TestWrapper {

	String trainFile1 = "TestData/tic-tac-toe-train-1.txt";
	String trainFile2 = "TestData/tic-tac-toe-train-2.txt";
	String trainFile3 = "TestData/tic-tac-toe-train-3.txt";
	String trainFile4 = "TestData/tic-tac-toe-train-4.txt";
	String trainFile5 = "TestData/tic-tac-toe-train-5.txt";
	String trainFile6 = "TestData/tic-tac-toe-train-6.txt";
	String testFile = "TestData/tic-tac-toe-test.txt";
	String restaurantFile = "TestData/Restaurant.txt";
	String unitTestFile = "TestData/UnitTest.txt";

	ArrayList<Vector> testData;
	ArrayList<Vector> trainingData;
	ArrayList<Vector> restaurantData;
	ArrayList<Vector> unitTestData;
	ArrayList<ArrayList<Vector>> trainingSets;

	public TestWrapper() {

		testData = LoadDataVector(testFile);

		trainingData = new ArrayList<Vector>();
		
		trainingData.addAll(LoadDataVector(trainFile1));
		trainingData.addAll(LoadDataVector(trainFile2));
		trainingData.addAll(LoadDataVector(trainFile3));
		trainingData.addAll(LoadDataVector(trainFile4));
		trainingData.addAll(LoadDataVector(trainFile5));
		trainingData.addAll(LoadDataVector(trainFile6));

		restaurantData = LoadDataVector(restaurantFile);

		unitTestData = LoadDataVector(unitTestFile);

		trainingSets = new ArrayList<ArrayList<Vector>>();
		trainingSets.add(LoadDataVector(trainFile1));
		trainingSets.add(LoadDataVector(trainFile2));
		trainingSets.add(LoadDataVector(trainFile3));
		trainingSets.add(LoadDataVector(trainFile4));
		trainingSets.add(LoadDataVector(trainFile5));
		trainingSets.add(LoadDataVector(trainFile6));
		
	}

	
	public void RunID3DecisionTreeTest() {
		System.out.println("----- Running ID3 Decision Tree -----");
		System.out.println();
		ID3DecisionTree tree = new ID3DecisionTree();

		tree.Train(trainingData);

		int matchedCount = 0;
		int unmatchedCount = 0;
		int unpredictedCount = 0;
		ArrayList<Vector> unpredictedData = new ArrayList<Vector>();

		for (Vector newExample : testData) {
			String predictedLabel = tree.Predict(newExample);
			if (predictedLabel.compareToIgnoreCase("") == 0) {
				unpredictedCount++;
				unpredictedData.add(newExample);
			} else if (predictedLabel.compareToIgnoreCase(newExample.Label) == 0) {
				matchedCount++;
			} else {
				unmatchedCount++;
			}
		}

		double accurary = (double) matchedCount / (double) (matchedCount + unmatchedCount);

		System.out.println("The accurary of this ID3 decision tree: " + accurary);
		System.out.println();
		System.out.println("The count of matched examples: " + matchedCount);
		System.out.println();
		System.out.println("The count of unmatched examples: " + unmatchedCount);
		System.out.println();
		System.out.println("The count of examples that cannot be predicted: " + unpredictedCount);
		System.out.println("examples whose labels cannot be predicted by this ID3 tree:");
		for (Vector v : unpredictedData) {
			System.out.println("Unpredicted example: " + v.toString());
		}
		System.out.println();
		System.out.println("----- END: Running ID3 Decision Tree -----");
	}
	
	
	public void RunKNN() {
		System.out.println("----- Running K-Nearest Neighbors -----");
		System.out.println();
		int bestK = 0;
		double avgCrossValidationAccuracy = -1;
		for (int k = 1; k <= 5; k++) {
			double tmpAccuracy = CrossValidation.CalculateAvgCrossValidationAccuracy(k, trainingSets);
			System.out.println("K = " + k + ", The Avg Cross-Validation Accurary = " + tmpAccuracy);

			if (Double.compare(tmpAccuracy, avgCrossValidationAccuracy) > 0) {
				avgCrossValidationAccuracy = tmpAccuracy;
				bestK = k;
			}
		}
		System.out.println();
		System.out.println("The best K is: " + bestK);
		System.out.println();
		double accuracyWithBestK = CrossValidation.CalculateAccuracy(bestK, trainingData, testData);
		System.out.println("With best K = " + bestK + " , its accuracy is " + accuracyWithBestK);
		System.out.println("----- END: Running K-Nearest Neighbors -----");
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
				Vector v = ConvertToVector(line);
				if (v != null) {
					vectors.add(v);
				}
			}
			buffer.close();
		} catch (Exception e) {
		}
		return vectors;
	}
	
	private static Vector ConvertToVector(String content) {
		Vector v = null;
		if (content != null && !content.isEmpty()) {
			String[] tokens = content.split(",");
			v = new Vector(tokens.length-1);
			for(int index = 0; index <= tokens.length-2; index++){
				v.SetFeature(index, tokens[index]);
			}
			v.Label = tokens[tokens.length-1];
		}
		return v;
	}
}
