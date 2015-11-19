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
			//v.Label = tokens[0];
			String tmp = tokens[0];
			if(tmp.compareToIgnoreCase("0") == 0)
				v.Label = "-1";
			else
				v.Label = "1";
		}
		return v;
	}
	
	
	// eg. vector = [a, b, c]
	// method return: [aa, ab, ac, bb, bc]
	public static Vector PairVectorElements(Vector original){
		Vector vector_transformed = null;
		if(original != null && original.GetVectorDimension()!=0){
			ArrayList<String> transformedValues = new ArrayList<String>();
			int last_idx = original.GetVectorDimension() -1;
			
			for(int idx = 0; idx <= last_idx; idx++){
				double value_idx = Double.parseDouble(original.GetFeatureValue(idx));
				
				for(int inner_idx = idx; inner_idx <= last_idx; inner_idx++	){
					double value_inner_idx = Double.parseDouble(original.GetFeatureValue(inner_idx));
					double value_transformed = value_idx * value_inner_idx;
					transformedValues.add(Double.toString(value_transformed));
				}
			}
			
			int size_transformedValues = transformedValues.size();
			vector_transformed = new Vector(size_transformedValues);
				
			for(int idx = 0; idx < size_transformedValues; idx++){
				vector_transformed.SetFeature(idx, new String(transformedValues.get(idx)));
			}
			vector_transformed.Label = new String(original.Label);
		}
		return vector_transformed;
	}
	
	//eg. input = [a, b, c], [u, v, w]
	// output: [aa,ab,ac,bb,bc], [uu,uv,uw,vv,vw,ww]
	public static ArrayList<Vector> PairVectorElements(ArrayList<Vector> original){
		
		ArrayList<Vector> data_transformed = null;
		if(original != null){
			data_transformed = new ArrayList<Vector>();
			for(Vector v : original){
				data_transformed.add(DataHelper.PairVectorElements(v));
			}	
		}
		return data_transformed;
	}
	

}
