package Helper;

public class DistanceHelper {
	
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

}
