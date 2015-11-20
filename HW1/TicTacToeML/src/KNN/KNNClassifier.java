package KNN;
import Helper.Vector;

import java.util.*;

public class KNNClassifier {
	private int k;
	private ArrayList<Vector> trainingExamples;

	public KNNClassifier(int k) {
		this.k = k;
		trainingExamples = new ArrayList<Vector>();
	}

	public void Train(ArrayList<Vector> trainingData) {
		if (trainingData != null) {
			trainingExamples.addAll(trainingData);
		}
	}

	
	public String PredictV2(Vector newExample) {
		if (newExample == null) {
			return null;
		}
		
		ArrayList<Vector> tmpTrainingExamples = Vector.CloneVectorSet(this.trainingExamples);
		ArrayList<Vector> nearestNeighbors = new ArrayList<Vector>();
		for(int count = 1; count <= k; count++){
			Vector theNeighbor = FindNearestNeighbor(tmpTrainingExamples, newExample);
			nearestNeighbors.add(theNeighbor);
			tmpTrainingExamples.remove(theNeighbor);
		}
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (int count = 1; count <= k; count++) {
			String label = nearestNeighbors.get(count - 1).Label.toLowerCase();
			if (map.containsKey(label)) {
				map.put(label, map.get(label) + 1);
			} else {
				map.put(label, 1);
			}
		}
		String mostCommonLabel = new String();
		int mostCommonCounter = 0;
		Iterator<Map.Entry<String, Integer>> mapItr = map.entrySet().iterator();
		while (mapItr.hasNext()) {
			Map.Entry<String, Integer> entry = mapItr.next();
			if (entry.getValue() > mostCommonCounter) {
				mostCommonCounter = entry.getValue();
				mostCommonLabel = entry.getKey();
			}
		}
		return mostCommonLabel;
	}
	
	
	public static int HammingDistance(Vector x, Vector y){
		int size = x.GetVectorDimension();
		int distance = 0;
		for(int index=0; index < size; index++){
			String xVal = x.GetFeatureValue(index);
			String yVal = y.GetFeatureValue(index);
			if	(xVal.compareToIgnoreCase(yVal)!=0){
				distance++;
			}
		}
		return distance;
	}
	
	public static Vector FindNearestNeighbor(ArrayList<Vector> neighbors, Vector testTarget){
		if(neighbors == null || neighbors.size() ==0)
			return null;
		
		int size = neighbors.size();
		int shortestDistance = HammingDistance(neighbors.get(0), testTarget);
		Vector nearestNeighbor = neighbors.get(0);
		
		for(int index = 1; index < size; index++){
			int distance = HammingDistance(neighbors.get(index), testTarget);
			if(distance <  shortestDistance){
				shortestDistance = distance;
				nearestNeighbor = neighbors.get(index);
			}
		}
		return nearestNeighbor;
	}
	
	

}
