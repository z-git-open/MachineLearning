package DecisionTree;

import java.util.ArrayList;
import java.util.Iterator;

import Helper.DataHelper;
import Helper.InfoGain;
import Helper.Vector;

public class ID3DecisionTree {
	
	public TreeNode root = null;
	
	
	public void Train(ArrayList<Vector> trainingData) {

		this.root = GenerateTree(trainingData, null, new String());
	}
	
	
	private TreeNode GenerateTree(ArrayList<Vector> trainingData, TreeNode parent, String parentEdgeValue){
		if(trainingData == null || trainingData.isEmpty())
			return null;
		int myIndex = InfoGain.FindFeatureIndexWithBestGain(trainingData);
		if(myIndex < 0 )
			return null;
		TreeNode myRoot = new TreeNode();
		myRoot.Value = Integer.toString(myIndex);
		myRoot.Parent = parent;
		myRoot.ParentEdgeValue = parentEdgeValue;

		// each value corresponds to one edge to a child
		Iterator<String> featureValues = DataHelper.EnumerateFeatureValues(trainingData, myIndex).iterator();

		while (featureValues.hasNext()) {
			String featureValue = featureValues.next();
			ArrayList<Vector> subsetTrainData = DataHelper.Filter(trainingData, myIndex, featureValue);

			if (DataHelper.SameLabelForAllData(subsetTrainData)) {
				TreeNode labelNode = new TreeNode();
				labelNode.Value = subsetTrainData.get(0).Label;
				labelNode.ParentEdgeValue = featureValue;
				labelNode.Parent = myRoot;
				labelNode.Children.clear();
				myRoot.Children.add(labelNode);
			} 
			else {
				TreeNode subtreeRoot = GenerateTree(subsetTrainData, myRoot, featureValue);
				if (subtreeRoot != null) {
					myRoot.Children.add(subtreeRoot);
				}
			}
		}
		return myRoot;
	}
	
	
	public String Predict(Vector newExample){
		return Predict(this.root, newExample);
	}
	
	private static String Predict(TreeNode root, Vector newExample){
		if(newExample == null || root == null)
			return new String("Cannot Find Label.");
		if(root.isLeaf()){
			return root.Value;
		}
			
		int featureIndex = Integer.parseInt(root.Value);
		String valueOfEdgeToChild = newExample.GetFeatureValue(featureIndex);
		for(TreeNode child : root.Children){
			if(child.ParentEdgeValue.compareToIgnoreCase(valueOfEdgeToChild) == 0){
				return Predict(child, newExample);
			}
		}
		return new String();
	}
	
	


}
