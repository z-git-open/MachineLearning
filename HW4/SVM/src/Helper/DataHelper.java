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
	
	public static Vector ConvertToVector(String content){
		Vector v = null;
		if (content != null && !content.isEmpty()) {
			String[] tokens = content.split(" ");
			v = new Vector(tokens.length-1);
			for(int index = 0; index <= tokens.length-2; index++){
				v.SetFeature(index, tokens[index+1].split(":")[1]);
			}
			//v.Label = tokens[0];
			String label_str = tokens[0];
			//v.Label = new String(label_str);
			if(label_str.compareToIgnoreCase("0") == 0 || label_str.compareToIgnoreCase("-1") == 0){
				v.Label_Int = 0;
				v.Label = "-1";
			}
			if(label_str.compareToIgnoreCase("1") == 0 || label_str.compareToIgnoreCase("+1") == 0){
				v.Label_Int = 1;
				v.Label = "1";
			}
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
	
	// split entire dataset into k parts (k-fold), each part is equal sized.
	public static ArrayList<ArrayList<Vector>> SplitData(ArrayList<Vector> data, int kfold){
		
		int dataSize = data.size();
		//int partSize = (int)Math..ceil((double)dataSize / (double)kfold);
		long partSize = Math.round((double)dataSize / (double)kfold);
		
		ArrayList<ArrayList<Vector>> dataParts = new ArrayList<ArrayList<Vector>>();
		
		int counter = 0;
		ArrayList<Vector> part = new ArrayList<Vector>();
		for(int idx = 0; idx < dataSize; idx++){
			part.add(data.get(idx).Clone());
			counter++;
			if(counter == partSize){
				dataParts.add(part);
				counter = 0;
				part = new ArrayList<Vector>();
			}
		}
		if(!part.isEmpty())
			dataParts.add(part);
		
		if(dataParts.size()>kfold){
			int dataPartsSize = dataParts.size();
			ArrayList<Vector> lastPart = dataParts.get(dataPartsSize-1);
			ArrayList<Vector> secondLastPart = dataParts.get(dataPartsSize-2);
			
			ArrayList<Vector> merge = new ArrayList<Vector>();
			merge.addAll(secondLastPart);
			merge.addAll(lastPart);
			
			dataParts.remove(dataPartsSize-1);
			dataParts.remove(dataPartsSize-2);
			
			dataParts.add(merge);
			
		}
		
		return dataParts;
	}
	
	public static ArrayList<Vector> ReshuffleData(ArrayList<Vector> data){
		ArrayList<Vector> newDataset = new ArrayList<Vector>();
		int tmp_size = data.size();
		while(data.size() > 0){
			int size = data.size();
			int randIndex = MathHelper.RandomInt(0, size-1);
			newDataset.add(data.get(randIndex));
			data.remove(randIndex);
		}
		if(newDataset.size() != tmp_size){
			throw new UnsupportedOperationException("ReshuffuleData: new data set has different size than the original one.");
		}
		return newDataset;
	}
	
	

}
