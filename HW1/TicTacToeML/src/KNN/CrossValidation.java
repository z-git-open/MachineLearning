package KNN;

import java.util.ArrayList;
import java.util.Iterator;

import Helper.Vector;

public class CrossValidation {
	
	public static double CalculateAccuracy(int k, ArrayList<Vector> trainingData, ArrayList<Vector> evaluateData){
		KNNClassifier classifier = new KNNClassifier(k);
		classifier.Train(trainingData);
		int matchedCount = 0;
		int unmatchedCount = 0;
		for(Vector test: evaluateData)
		{
			String predictLabel = classifier.PredictV2(test);
			if	(predictLabel.compareToIgnoreCase(test.Label) == 0){
				matchedCount++;
			}
			else{
				unmatchedCount++;
			}
		}
		double result = (double)matchedCount / (double)(matchedCount + unmatchedCount);
		return result;
	}
	
	public static double CalculateAvgCrossValidationAccuracy(int k, ArrayList<ArrayList<Vector>> trainingSets){
		ArrayList<Double> accuracies = new ArrayList<Double>();
		int kfold = trainingSets.size();
		for(int index = 0; index < kfold; index++){
			ArrayList<Vector> evaluateData = trainingSets.get(index);
			ArrayList<Vector> trainingData = new ArrayList<Vector>();
			
			for(int tmpIdx = 0; tmpIdx < kfold; tmpIdx++){
				if(tmpIdx != index){
					trainingData.addAll(trainingSets.get(tmpIdx));
				}
			}
			
			double accurary = CalculateAccuracy(k, trainingData, evaluateData);
			
			accuracies.add(accurary);
		}
		
		double accuracySum = 0;
		Iterator<Double> accuraciesItr = accuracies.iterator();
		while(accuraciesItr.hasNext()){
			accuracySum += accuraciesItr.next();
		}
		return accuracySum / accuracies.size();
	}

}
