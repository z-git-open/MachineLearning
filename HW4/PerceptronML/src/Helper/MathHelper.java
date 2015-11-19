package Helper;

import java.util.Random;

public class MathHelper {
	
	public static double VectorDotProduct(Vector w, Vector v){
		if	(w == null || v == null){
			throw new IllegalArgumentException("Vector dot product: at least one of two vectors is null.");
		}
		if(w.GetVectorDimension() != v.GetVectorDimension()){
			throw new ArithmeticException("The length of two vectors are not same.");
		}
		int length = w.GetVectorDimension();
		double result = 0;
		for(int idx = 0; idx < length; idx++){
			double val_w = Double.parseDouble(w.GetFeatureValue(idx));
			double val_v = Double.parseDouble(v.GetFeatureValue(idx));
			result += val_w * val_v;
		}
		return result;
	}
	
	public static double VectorDistanceFromOrigin(Vector v){
		if(v == null || v.GetVectorDimension()<1)
			throw new IllegalArgumentException("VectorDistanceFromOrigin invalid input.");
		double dotProduct = MathHelper.VectorDotProduct(v, v);
		return Math.sqrt(dotProduct);
	}
	
	
	public static void VectorScalarMultiplicationInplace(Vector v, double scalar){
		if	(v != null){
			int length = v.GetVectorDimension();
			for(int idx = 0; idx < length; idx++){
				double value = Double.parseDouble(v.GetFeatureValue(idx));
				value = value * scalar;
				v.SetFeature(idx, Double.toString(value));
			}
		}
	}
	
	
	public static Vector VectorSum(Vector x, Vector y){
		if	(x == null || y == null){
			throw new ArithmeticException("VectorSum: at least one of vectors is null."); 
		}
		if(x.GetVectorDimension() != y.GetVectorDimension()){
			throw new ArithmeticException("VectorSum: the dimensions of these two vectors are not same.");
		}
		Vector result = x.Clone();
		int length = x.GetVectorDimension();
		for(int idx = 0; idx < length; idx++){
			double x_value = Double.parseDouble(x.GetFeatureValue(idx));
			double y_value = Double.parseDouble(y.GetFeatureValue(idx));
			double sum = x_value+y_value;
			result.SetFeature(idx, Double.toString(sum));
		}
		return result;
	}
	
	public static Vector VectorSubtraction(Vector x, Vector y){
		Vector neg_y = MathHelper.VectorScalarMultiplication(y, (double)-1);
		return MathHelper.VectorSum(x, neg_y);
	}
	
	
	public static Vector VectorScalarMultiplication(Vector v, double scalar){
		if	(v != null){
			int length = v.GetVectorDimension();
			Vector resultVector = v.Clone();
			for(int idx = 0; idx < length; idx++){
				double value = Double.parseDouble(v.GetFeatureValue(idx));
				value = value * scalar;
				resultVector.SetFeature(idx, Double.toString(value));
			}
			return resultVector;
		}
		return null;
	}
	
	public static Vector RandomizeVector(int vectorSize, double min, double max){
		if	(vectorSize < 0 || Double.compare(min, max) > 0){
			return null;
		}
		Random random = new Random(System.currentTimeMillis());
		
		Vector v = new Vector(vectorSize);
		for(int idx = 0; idx < vectorSize; idx++){
			double _randomDouble = min + (max - min) * random.nextDouble();
			v.SetFeature(idx, Double.toString(_randomDouble));
		}
		return v;
	}
	
	public static Vector FixVector(int vectorSize, double value){
		if	(vectorSize < 0){
			return null;
		}
		Vector v = new Vector(vectorSize);
		for(int idx = 0; idx < vectorSize; idx++){
			v.SetFeature(idx, Double.toString(value));
		}
		return v;
	}
	
	
	public static int RandomInt(int min, int max) {
		if (min <= max) {
			Random rand = new Random(System.currentTimeMillis());
			int randomNum = rand.nextInt((max - min) + 1) + min;
			return randomNum;
		} else {
			throw new ArithmeticException("RandomInt: min > max.");
		}

	}

	public static double RandomDouble(double min, double max) {
		if (Double.compare(min, max) <= 0) {
			Random rand = new Random(System.currentTimeMillis());
			double result = min + (max - min) * rand.nextDouble();
			return result;
		} else {
			throw new ArithmeticException("RandomDouble: min > max.");
		}
	}
	
	public static boolean IsEqual(double x, double y){
		return Double.compare(x, y) == 0;
	}
	
	public static boolean IsLess(double x, double y){
		return Double.compare(x, y) < 0;
	}
	
	public static boolean IsEqualOrLess(double x, double y){
		return Double.compare(x, y) <= 0;
	}
	
	
	public static double Logb(double a, double b) {
		return Math.log(a) / Math.log(b);
	}

	public static double Log2(double x) {
		return Logb(x, 2);
	}
	
	public static String DoubleToStringGivenLength(double number, int length){
		String num = Double.toString(number);
		if(num.length()>= length){
			return num.substring(0, length);
		}
		else
			return num;
	}
	


}
