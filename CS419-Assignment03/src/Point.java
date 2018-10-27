import java.util.Arrays;

public class Point {
	double [] classification;
	double [] data;
	
	public Point(double [] d, double [] c) {
		data = d;
		classification = c;
	}
	
	public double [] getData() {
		return data;
	}
	
	public double [] getClassification() {
		return classification;
	}
	
	public String toString() {
		return "<"+Arrays.toString(data)+",\n"+Arrays.toString(classification);
	}
	
	public int getCassificationLength() {
		return classification.length;
	}
	
	public int getDataLength() {
		return data.length;
	}
	
//	public int getClassificationIndex() {
//		for(int i = 0; i<classification.length; i++) {
//			
//		}
//	}
}
