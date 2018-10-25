import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class NeuralNetDriver {
	
	public NeuralNetDriver() {
		
	}
	
	public LinkedList<Point> readTraingData(String path) throws IOException {
		BufferedReader rin = new BufferedReader(new FileReader(path));
		LinkedList<Point> dataPoints = new LinkedList<Point>();
		String line = rin.readLine();
		while(line != null) {
			dataPoints.add(stringToPoint(line));
			line = rin.readLine();
		}
		return dataPoints;
	}
	
	public Point stringToPoint(String line) {
		String [] split = line.split(") (");
		String [] d = split[0].substring(1, split[0].length()-1).split(" ");
		String [] c = split[1].substring(0, split[1].length()-2).split(" ");
		return new Point(mapStringToDouble(d),mapStringToDouble(c));
	}
	
	private double[] mapStringToDouble(String [] s) {
		double [] d = new double[s.length];
		for(int i = 0; i<s.length; i++) {
			d[i] = Double.parseDouble(s[i]);
		}
		return d;
	}
	

	public static void main(String[] args) {
		double [] d = { 0.1, 0.2};
		double [] c = {0};
		Point p = new Point(d,c);
		int[] lens = {1};
		ArrayList<Point> l = new ArrayList<Point>(); 
		l.add(p);
		
		NeuralNet n = new NeuralNet(2, lens,l);
		n.backPropLearning();
		
	}

}
