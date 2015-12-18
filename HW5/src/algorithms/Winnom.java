package algorithms;

import java.util.ArrayList;

import Helper.MathHelper;
import Helper.Vector;

public class Winnom {

	protected int epoch;
	protected boolean reshuffle;
	protected Vector init_w;
	protected Vector weight; // weight vector
	//protected double learningRate;
	protected ArrayList<Vector> trainingExamples;
	public boolean verboseOn;
	protected int unmatchedPredicts;
	protected int matchedPredicts;
	protected int w_updates;
	protected int total_trainings;
	protected int theta;


	public Winnom(Vector initWeight, int epoch, boolean reshuffleData) {
		if (initWeight == null || epoch < 1) {
			throw new IllegalArgumentException("Winnom:Constructor");
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
		//this.learningRate = learningRate;
		this.theta = this.init_w.GetVectorDimension();
		this.trainingExamples = new ArrayList<Vector>();
	}
	
	
	public void Train(ArrayList<Vector> trainingData) {
		if (trainingData == null ) {
			throw new IllegalArgumentException("Winnom:Train");
		}
		if (trainingData.size() == 0)
			return;
		
		if (this.weight.GetVectorDimension() != trainingData.get(0).GetVectorDimension()) {
			throw new UnsupportedOperationException(
					"Winnom: initWeight and traindingData have different dimensions.");
		}
		
		trainingExamples.addAll(trainingData);

		for(int count =1 ; count <= this.epoch; count++){
			this.RunTraining();
		}
	}
	
	protected void RunTraining() {
		if (this.reshuffle)
			this.ReshuffleData();

		int examples_size = this.trainingExamples.size();
		for (int idx = 0; idx < examples_size; idx++) {
			Vector x = this.trainingExamples.get(idx);
			double predictValue = MathHelper.VectorDotProduct(this.weight, x) - (double) this.theta;
			double realLabel = Double.parseDouble(x.Label);
			boolean buff_w = false;
			boolean predict_error = false;
			if (MathHelper.IsEqualOrGreater(predictValue, 0) && MathHelper.IsLess(realLabel, 0)) {
				// predict label +1, real label -1
				buff_w = false;
				predict_error = true;
			} else if (MathHelper.IsEqualOrGreater(realLabel, 0) && MathHelper.IsLess(predictValue, 0)) {
				// real label +1, predict label -1
				buff_w = true;
				predict_error = true;
			}

			if (predict_error) {
				
				int x_length = x.GetVectorDimension();

				for (int x_idx = 0; x_idx < x_length; x_idx++) {
					double x_idx_value = Double.parseDouble(x.GetFeatureValue(x_idx));
					if (MathHelper.IsEqualOrGreater(x_idx_value, 0)) {
						// feature x(i) that is 1.
						double w_value_at_x_idx = Double.parseDouble(this.weight.GetFeatureValue(x_idx)); 
						if (buff_w) {
							this.weight.SetFeature(x_idx, Double.toString(w_value_at_x_idx * (double) 2));
						} else {
							this.weight.SetFeature(x_idx, Double.toString(w_value_at_x_idx / (double) 2));
						}
					}
				}
				w_updates++;
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
	
	public int GetTrainingMistakes(){
		return this.w_updates;
	}
	
	public int GetTotalTrainings(){
		return this.total_trainings;
	}
	
	public int GetPredictErrors(){
		return unmatchedPredicts;
	}
	
	public int GetTestExamplesCount(){
		return unmatchedPredicts + matchedPredicts;
	}
	
	public String Predict(Vector example) {
		if (example == null) {
			throw new IllegalArgumentException("StdPerceptron:Predict() - example is null.");
		}
		
		//double result = MathHelper.VectorDotProduct(this.weight, example);
		double predictValue = MathHelper.VectorDotProduct(this.weight, example) - (double) this.theta;
		boolean predictLabel = MathHelper.IsEqualOrGreater(predictValue, 0); //Double.compare(predictValue, 0) >= 0;
		boolean realLabel = MathHelper.IsEqualOrGreater(Double.parseDouble(example.Label), 0);  //  Double.compare(Double.parseDouble(example.Label), 0) >= 0;
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
		System.out.println("Winnom.");
		//System.out.println("Learning rate r: " + this.learningRate);
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
