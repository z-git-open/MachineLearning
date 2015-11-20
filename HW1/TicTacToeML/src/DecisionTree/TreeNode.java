package DecisionTree;

import java.util.ArrayList;

public class TreeNode {

	public String Value;

	public String ParentEdgeValue;

	public TreeNode Parent = null;

	public ArrayList<TreeNode> Children = new ArrayList<TreeNode>();

	public boolean isLeaf(){
		return Children.isEmpty();
	}
	
}
