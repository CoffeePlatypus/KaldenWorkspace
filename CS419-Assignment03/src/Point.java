
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
}
