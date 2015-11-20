package Helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class InfoGain {
	
	public static double Entropy(ArrayList<Vector> data) {
		double pCount = 0;
		double nCount = 0;
		for (Vector v : data) {
			if (v.Label.compareToIgnoreCase("positive") == 0) {
				pCount++;
			} else {
				nCount++;
			}
		}
		double totalCount = pCount + nCount;
		double p = pCount / totalCount;
		double n = nCount / totalCount;

		double result = 0;
		if (p != 0 && n != 0) {
			result = -1 * p * Log2(p) - n * Log2(n);
		} else if (p != 0 && n == 0) {
			result = -1 * p * Log2(p);
		} else if (p == 0 && n != 0) {
			result = -1 * n * Log2(n);
		}
		return result;
	}

	public static double Entropy(ArrayList<Vector> data, int expectedIndex, String expectedValue) {
		ArrayList<Vector> filteredData = DataHelper.Filter(data, expectedIndex, expectedValue);
		return Entropy(filteredData);
	}
	
	public static double ExpectedEntropy(ArrayList<Vector> data, int expectedIndex, String expectedValue){
		double entropy = Entropy(data, expectedIndex, expectedValue);
		ArrayList<Vector> filteredData = DataHelper.Filter(data, expectedIndex, expectedValue);
		return (double)filteredData.size() / (double)data.size() * entropy;
	}
	
	public static double InformationGain(ArrayList<Vector> data, int featureIndex){
		Set<String> values = DataHelper.EnumerateFeatureValues(data, featureIndex);
		double totalExpectedEntropy = 0;
		Iterator<String> iterator = values.iterator();
		while(iterator.hasNext()){
			String featureExpectedValue = iterator.next();
			totalExpectedEntropy += ExpectedEntropy(data, featureIndex, featureExpectedValue);
		}
		double entropyOfAll = Entropy(data);
		double gain = entropyOfAll - totalExpectedEntropy;
		return gain;
	}
	
	public static int FindFeatureIndexWithBestGain(ArrayList<Vector> data){
		if(data == null || data.isEmpty())
			return -1;
		
		int vectorDimension = data.get(0).GetVectorDimension();
		
		double bestGain = InformationGain(data, 0);
		int bestGainFeatureIndex = 0;
		for (int featureIndex = 1; featureIndex < vectorDimension; featureIndex++) {
			double gain = InformationGain(data, featureIndex);
			if (Double.compare(gain, bestGain) > 0) {
				bestGain = gain;
				bestGainFeatureIndex = featureIndex;
			}
		}
		return bestGainFeatureIndex;
	}
	
	private static double Logb(double a, double b) {
		return Math.log(a) / Math.log(b);
	}

	private static double Log2(double x) {
		return Logb(x, 2);
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	

}
