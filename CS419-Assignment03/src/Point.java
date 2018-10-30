import java.util.Arrays;

public class Point {
	int [] classification;
	double [] data;
	
	public Point(double [] d, int [] c) {
		data = d;
		classification = c;
	}
	
	public double [] getData() {
		return data;
	}
	
	public int [] getClassification() {
		return classification;
	}
	
	public String toString() {
		return "<"+Arrays.toString(data)+", "+Arrays.toString(classification)+">";
	}
	
	public int getCassificationLength() {
		return classification.length;
	}
	
	public int getDataLength() {
		return data.length;
	}
	
	public int getClassificationIndex() {
		for(int i = 0; i<classification.length; i++) {
			if(classification[i] == 1) {
				return i;
			}
		}
		return -1;
	}
}
