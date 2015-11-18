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
	
/*	public ArrayList<Vector> LoadDataVector(String filePath) {
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
	}*/
	
	
	
	
	public static Vector ConvertToVector(String content){
		Vector v = null;
		if (content != null && !content.isEmpty()) {
			String[] tokens = content.split(" ");
			v = new Vector(tokens.length-1);
			for(int index = 0; index <= tokens.length-2; index++){
				v.SetFeature(index, tokens[index+1].split(":")[1]);
			}
			v.Label = tokens[0];
		}
		return v;
	}
	

}
