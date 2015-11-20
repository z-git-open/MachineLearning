package Helper;

import java.util.ArrayList;

public class Vector {
	private ArrayList<String> features;
	public String Label;

	public Vector(int size) {
		features = new ArrayList<String>(size);
		for (int idx = 0; idx < size; idx++) {
			features.add(new String());
		}
	}

	public void SetFeature(int featureIndex, String value) {
		features.set(featureIndex, value);
	}

	public String GetFeatureValue(int index) {
		return features.get(index);
	}
	
	public int GetVectorDimension(){
		return features.size();
	}

	@Override
	public String toString() {
		String result = "";
		for (String s : features) {
			result = result + " " + s;
		}
		return "["+result + " " + Label+"]";
	}
	
	public Vector Clone()
	{
		Vector clone = new Vector(this.features.size());
		int size = this.features.size();
		for(int idx = 0; idx < size; idx++){
			clone.SetFeature(idx, new String(this.features.get(idx)));
		}
		clone.Label = new String(this.Label);
		return clone;
	}
	
	public static ArrayList<Vector> CloneVectorSet(ArrayList<Vector> set){
		if	(set == null)
			return null;
		ArrayList<Vector> cloneSet = new ArrayList<Vector>();
		for(Vector v: set){
			cloneSet.add(v.Clone());
		}
		return cloneSet;
	}
}
