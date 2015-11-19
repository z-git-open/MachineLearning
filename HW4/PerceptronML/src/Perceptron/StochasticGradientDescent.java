package Perceptron;

import java.util.ArrayList;

import Helper.MathHelper;
import Helper.Vector;

public class StochasticGradientDescent {

	protected int epoch;
	protected boolean reshuffle;
	protected Vector init_w;
	protected Vector weight; // weight vector
	protected double init_learningRate;
	protected double learningRate;
	protected double relativeImportance;
	protected ArrayList<Vector> trainingExamples;
	public boolean verboseOn;
	protected int unmatchedPredicts;
	protected int matchedPredicts;
	protected int total_trainings;


	public StochasticGradientDescent(
			Vector initWeight, 
			double initLearningRate, 
			double relativeImportance, 
			int epoch, 
			boolean reshuffleData) {
		
		if (initWeight == null || epoch < 1) {
			throw new IllegalArgumentException("StdPerceptron:Constructor");
		}
		this.epoch = epoch;
		this.reshuffle = reshuffleData;
		this.unmatchedPredicts = 0;
		this.matchedPredicts = 0;
		this.total_trainings = 0;
		this.verboseOn = false;
		this.init_w = initWeight;
		this.weight = initWeight;
		this.init_learningRate = initLearningRate;
		this.relativeImportance = relativeImportance;
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
			
			// compute learning rate 
			
			this.learningRate = this.init_learningRate / (double)((double)1+this.init_learningRate*((double)(idx+1))/this.relativeImportance);
			
			Vector x = this.trainingExamples.get(idx);
			double testValue = Double.parseDouble(x.Label) * MathHelper.VectorDotProduct(this.weight, x);
			if(MathHelper.IsEqualOrLess(testValue, (double)1)){
				
				double cy = this.relativeImportance * Double.parseDouble(x.Label);
				Vector cyx = MathHelper.VectorScalarMultiplication(x, cy);
				Vector gradient = MathHelper.VectorSubtraction(this.weight, cyx);
				Vector r_gradient = MathHelper.VectorScalarMultiplication(gradient,this.learningRate);
				this.weight = MathHelper.VectorSubtraction(this.weight, r_gradient);
				
			}
			else{
				
				Vector gradient = this.weight.Clone();
				Vector r_gradient = MathHelper.VectorScalarMultiplication(gradient, this.learningRate);
				this.weight = MathHelper.VectorSubtraction(this.weight, r_gradient);
				
			}
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
		return this.unmatchedPredicts;
	}
	public double GetLearningRate(){
		return this.learningRate;
	}
	public String Predict(Vector example) {
		if (example == null) {
			throw new IllegalArgumentException("StdPerceptron:Predict() - example is null.");
		}
		double result = MathHelper.VectorDotProduct(this.weight.Clone(), example);
		boolean predictLabel = Double.compare(result, 0) >= 0;
		/*boolean realLabel = false;
		if(MathHelper.IsEqualOrLess(Double.parseDouble(example.Label), 0)){
			realLabel = false;
		}
		else{
			realLabel = true;
		}*/
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
		System.out.println("SVM.");
		System.out.println("Learning rate r: " + this.learningRate);
		System.out.println("Training Data Size: " + trainingExamples.size());
		System.out.println("Epoch: " + this.epoch);
		System.out.println("Shuffle data? " + this.reshuffle);
		System.out.println("Initial weight vector w: " + this.init_w.toStringWithoutLabel());
		System.out.println("Final weight vector w: " + this.weight.toStringWithoutLabel());
		System.out.println("Number of updates we made on initial weigth: " + this.unmatchedPredicts);
		System.out.println("Total number of trainings: " + this.total_trainings);
		System.out.println("Total Predicts we made so far: " + (this.matchedPredicts + this.unmatchedPredicts));
		System.out.println("In all predicts, matched predicts: " + this.matchedPredicts);
		System.out.println("In all predicts, unmatched predicts: " + this.unmatchedPredicts);
		System.out.println("Accuracy of predicting: " + Double.toString(this.GetAccumulativeAccuracy()));
	}

}
