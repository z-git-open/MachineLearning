package algorithms;


import Helper.MathHelper;
import Helper.Vector;

public class MarginPerceptron extends StdPerceptron {

	protected double margin;

	public MarginPerceptron(Vector initWeight, double learningRate, int epoch, boolean reshuffleData, double margin) {
		super(initWeight, learningRate, epoch, reshuffleData);
		if (Double.compare(margin, 0) < 0) {
			throw new IllegalArgumentException("MarginPerceptron:Constructor, margin is negative, it must be posoitive.");
		}
		this.margin = margin;
	}

	@Override
	protected void RunTraining() {
		if (this.reshuffle)
			this.ReshuffleData();

		int size = this.trainingExamples.size();
		for (int idx = 0; idx < size; idx++) {
			Vector x = this.trainingExamples.get(idx);
			double predict = MathHelper.VectorDotProduct(this.weight, x);
			double realLabel = Double.parseDouble(x.Label);
			double multiply = predict * realLabel;
			if (MathHelper.IsEqualOrLess(multiply, this.margin)) {
				// w = w + ryx
				Vector old_w = this.weight.Clone();
				Vector ryx = MathHelper.VectorScalarMultiplication(x, this.learningRate * realLabel);
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

	@Override
	public void DisplayStatus() {
		System.out.println("Margin Perceptron.");
		System.out.println("Learning rate r: " + this.learningRate);
		System.out.println("Margin: " + this.margin);
		System.out.println("Training Data Size: " + trainingExamples.size());
		System.out.println("Epoch: " + this.epoch);
		System.out.println("Shuffle data? " + this.reshuffle);
		System.out.println("Initial weight vector w: " + this.init_w.toStringWithoutLabel());
		System.out.println("Final weight vector w: " + this.weight.toStringWithoutLabel());
		System.out.println("Number of updates on initial weight: " + this.w_updates);
		System.out.println("Total number of trainings: " + this.total_trainings);
		System.out.println("Total Predicts we made so far: " + (this.matchedPredicts + this.unmatchedPredicts));
		System.out.println("In all predicts, matched predicts: " + this.matchedPredicts);
		System.out.println("In all predicts, unmatched predicts: " + this.unmatchedPredicts);
		System.out.println("Accuracy of predicting: " + Double.toString(this.GetAccumulativeAccuracy()));

	}

}
