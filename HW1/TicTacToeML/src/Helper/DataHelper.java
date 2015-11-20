package Helper;

import java.util.ArrayList;
import java.util.HashSet;

public class DataHelper {
	
	public static ArrayList<Vector> Filter(ArrayList<Vector> vectors, int expectedIndex, String expectedValue){
		ArrayList<Vector> result = null;
		if	(vectors != null){
			result = new ArrayList<Vector>();
			for(Vector v: vectors){
				if	(v.GetFeatureValue(expectedIndex).compareToIgnoreCase(expectedValue) == 0){
					result.add(v.Clone());
				}
			}
		}
		return result;
	}
	
	public static HashSet<String> EnumerateFeatureValues(ArrayList<Vector> data, int featureIndex){
		HashSet<String> values = new HashSet<String>();
		for(Vector v: data){
			values.add(v.GetFeatureValue(featureIndex));
		}
		return values;
	}
	
	public static boolean SameLabelForAllData(ArrayList<Vector> data){
		if(data != null && data.size()>0){
			String label = data.get(0).Label;
			for(Vector v: data){
				if(v.Label.compareToIgnoreCase(label) != 0){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	

}
