package Perceptron;

import java.util.ArrayList;

import Helper.MathHelper;
import Helper.Vector;

public class StdPerceptron {

	protected int epoch;
	protected boolean reshuffle;
	protected Vector init_w;
	protected Vector weight; // weight vector
	protected double learningRate;
	protected ArrayList<Vector> trainingExamples;
	public boolean verboseOn;
	protected int unmatchedPredicts;
	protected int matchedPredicts;
	protected int w_updates;
	protected int total_trainings;


	public StdPerceptron(Vector initWeight, double learningRate, int epoch, boolean reshuffleData) {
		if (initWeight == null || epoch < 1) {
			throw new IllegalArgumentException("StdPerceptron:Constructor");
		}
		this.epoch = epoch;
		this.reshuffle = reshuffleData;
		this.unmatchedPredicts = 0;
		this.matchedPredicts = 0;
		this.w_updates = 0;
		this.total_trainings = 0;
		this.verboseOn = false;
		this.init_w = initWeight;
		this.weight = initWeight;
		this.learningRate = learningRate;
		this.trainingExamples = new ArrayList<Vector>();
	}
	
	
	public void Train(ArrayList<Vector> trainingData) {
		if (trainingData == null ) {
			throw new IllegalArgumentException("StdPerceptron:Train");
		}
		if (trainingData.size() == 0)
			return;
		
		if (this.weight.GetVectorDimension() != trainingData.get(0).GetVectorDimension()) {
			throw new UnsupportedOperationException(
					"StdPerceptron: initWeight and traindingData have different dimensions.");
		}
		
		trainingExamples.addAll(trainingData);

		for(int count =1 ; count <= this.epoch; count++){
			this.RunTraining();
		}
	}
	
	protected void RunTraining() {
		if(this.reshuffle)
			this.ReshuffleData();
		
		int size = this.trainingExamples.size();
		for (int idx = 0; idx < size; idx++) {
			Vector x = this.trainingExamples.get(idx);
			double predict = MathHelper.VectorDotProduct(this.weight, x);
			double realLabel = Double.parseDouble(x.Label);
			double multiply = predict * realLabel;
			if (Double.compare(multiply, 0) <= 0) {
				// w = w + ryx
				Vector old_w = this.weight.Clone();
				Vector ryx = MathHelper.VectorScalarMultiplication(x, learningRate * realLabel);
				this.weight = MathHelper.VectorSum(this.weight, ryx);

				if (verboseOn) {
					System.out.println();
					System.out.println("Mistake found on x index " + idx);
					System.out.println("Mistake found on x : " + x.toString());
					System.out.println("old w :" + old_w.toStringWithoutLabel());
					System.out.println("new w: " + this.weight.toStringWithoutLabel());
					System.out.println();
				}

				w_updates++;
			} else {
				if (verboseOn)
					System.out.println("Correct Predict at x index " + idx);
			}
			this.total_trainings++;
		}
	}
	
	protected void ReshuffleData(){
		ArrayList<Vector> newDataset = new ArrayList<Vector>();
		int tmp_size = this.trainingExamples.size();
		while(this.trainingExamples.size() > 0){
			int size = this.trainingExamples.size();
			int randIndex = MathHelper.RandomInt(0, size-1);
			newDataset.add(this.trainingExamples.get(randIndex));
			this.trainingExamples.remove(randIndex);
		}
		if(newDataset.size() != tmp_size){
			throw new UnsupportedOperationException("ReshuffuleData: new data set has different size than the original one.");
		}
		this.trainingExamples = newDataset;
	}
	

	public Vector GetCurrentWeightVector() {
		return this.weight;
	}

	public Vector GetInitWeightVector() {
		return this.init_w;
	}
	
	public int GetMistakes(){
		return this.w_updates;
	}
	
	public String Predict(Vector example) {
		if (example == null) {
			throw new IllegalArgumentException("StdPerceptron:Predict() - example is null.");
		}
		double result = MathHelper.VectorDotProduct(this.weight, example);
		boolean predictLabel = Double.compare(result, 0) >= 0;
		boolean realLabel = Double.compare(Double.parseDouble(example.Label), 0) >= 0;
		if (predictLabel == realLabel) {
			matchedPredicts++;
			return "+1";
		} else {
			unmatchedPredicts++;
			return "-1";
		}
	}

	public void Predict(ArrayList<Vector> examples) {
		if (examples != null) {
			for (Vector e : examples) {
				this.Predict(e);
			}
		}
	}

	public double GetAccumulativeAccuracy() {
		int totalPredicts = unmatchedPredicts + matchedPredicts;
		if (totalPredicts == 0)
			return 0;
		else {
			double result = (double) matchedPredicts / (double) totalPredicts;
			return result;
		}
	}

	public void DisplayStatus() {
		System.out.println("Standard Perceptron.");
		System.out.println("Learning rate r: " + this.learningRate);
		System.out.println("Training Data Size: " + trainingExamples.size());
		System.out.println("Epoch: " + this.epoch);
		System.out.println("Shuffle data? " + this.reshuffle);
		System.out.println("Initial weight vector w: " + this.init_w.toStringWithoutLabel());
		System.out.println("Final weight vector w: " + this.weight.toStringWithoutLabel());
		System.out.println("Number of updates we made on initial weigth: " + this.w_updates);
		System.out.println("Total number of trainings: " + this.total_trainings);
		System.out.println("Total Predicts we made so far: " + (this.matchedPredicts + this.unmatchedPredicts));
		System.out.println("In all predicts, matched predicts: " + this.matchedPredicts);
		System.out.println("In all predicts, unmatched predicts: " + this.unmatchedPredicts);
		System.out.println("Accuracy of predicting: " + Double.toString(this.GetAccumulativeAccuracy()));
	}

}
