import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class NeuralNetDriver {
	
	public NeuralNetDriver() {
	}
	
	public ArrayList<Point> readTrainingData(String path) throws IOException {
		BufferedReader rin = new BufferedReader(new FileReader(path));
		ArrayList<Point> dataPoints = new ArrayList<Point>();
		String line = rin.readLine();
		while(line != null) {
			if(line.charAt(0) != '#') {
				dataPoints.add(stringToPoint(line));
			}else {
				System.out.println(line);
			}
			line = rin.readLine();
		}
		rin.close();
		return dataPoints;
	}
	
	public Point stringToPoint(String line) {
		String [] split = line.split("\\) \\(");
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
	
	public void testRun2to1() {
		double [] d = { 0.1, 0.2};
		double [] c = {0};
		Point p = new Point(d,c);
		int[] lens = {1};
		ArrayList<Point> l = new ArrayList<Point>(); 
		l.add(p);
		
		NeuralNet n = new NeuralNet(2, lens,l);
		n.backPropLearning();
	}
	
	public void testrun3() {
		double [] d1 = { 0.2, 0.3};
		double [] c1 = { 1, 0};		
		Point p1 = new Point(d1,c1);
		
		double [] d2 = {0.5, 0.8};
		double [] c2 = {0, 1};
		Point p2 = new Point(d2, c2);
		
		int[] lens = {3,2};
		ArrayList<Point> l = new ArrayList<Point>();
		l.add(p1);
		l.add(p2);
		
		NeuralNet n = new NeuralNet(2, lens, l);
		n.backPropLearning();
		n.testData();
//		System.out.println(n.test(p));
	}
	
	public int[] readLengths(int outputLength) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter number of hidden layers: ");
		int size = scan.nextInt();
		int [] layerLengths = new int [size+1];
		for(int i = 0; i<size; i++) {
			System.out.printf("Enter size of layer[%d]: \n", i);
			layerLengths[i] = scan.nextInt(); 
		}
		layerLengths[size] = outputLength;
		scan.close();
		return layerLengths;
	}

	public static void main(String[] args) {
//		try {
			NeuralNetDriver d = new NeuralNetDriver();
			d.testrun3();
//			ArrayList<Point> data= d.readTrainingData("trainSet_data/trainSet_05.dat");
//			Point p = data.get(0);
//			int [] lengths = d.readLengths(p.getCassificationLength());
//			System.out.println("Initalizing Neural Net...");
//			NeuralNet net = new NeuralNet(p.getDataLength(), lengths, data);
//			System.out.println("Train Neural Net...");
//			net.backPropLearning();
//			System.out.println("Test Neural Net...");
//			System.out.printf("Accuracy: %f",net.testData());
//			
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}		
	}

}
