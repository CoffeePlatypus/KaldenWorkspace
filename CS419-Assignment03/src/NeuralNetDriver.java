import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
		String [] d = split[0].substring(1, split[0].length()).split(" ");
		String [] c = split[1].substring(0, split[1].length()-1).split(" ");
		return new Point(mapStringToDouble(d),mapStringToInt(c));
	}
	
	private double[] mapStringToDouble(String [] s) {
		double [] d = new double[s.length];
		for(int i = 0; i<s.length; i++) {
			double x = Double.parseDouble(s[i]);
			//TODO maybe remove this
			d[i] = (x < 1? 0 : x)/255;
		}
		return d;
	}
	
	private int[] mapStringToInt(String [] s) {
		int [] d = new int[s.length];
		for(int i = 0; i<s.length; i++) {
			d[i] = Integer.parseInt(s[i]);
		}
		return d;
	}
	
	public void testRun2to1() {
		double [] d = { 0.1, 0.2};
		int [] c = {0};
		Point p = new Point(d,c);
		int[] lens = {2,1};
		ArrayList<Point> l = new ArrayList<Point>(); 
		l.add(p);
		
		NeuralNet n = new NeuralNet( lens,l);
		n.backPropLearning();
	}
	
	public void testrun3() {
		double [] d1 = { 0.5, 0.1};
		int [] c1 = { 1, 0};		
		Point p1 = new Point(d1,c1);
		
		double [] d2 = {0.8, 0.9};
		int [] c2 = {0, 1};
		Point p2 = new Point(d2, c2);
		
		int[] lens = {2,3,2};
		ArrayList<Point> l = new ArrayList<Point>();
		l.add(p1);
		l.add(p2);
		
		NeuralNet n = new NeuralNet( lens, l);
		n.backPropLearning();
		n.testData();
//		System.out.println(n.test(p));
	}
	
	public int[] readLengths(int inputLength, int outputLength) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter number of hidden layers < 10: ");
		int size = scan.nextInt();
		int [] layerLengths = new int [size+2];
		layerLengths[0] = inputLength;
		for(int i = 1; i<size+1; i++) {
			System.out.printf("Enter size of layer[%d]: \n", i);
			layerLengths[i] = scan.nextInt(); 
		}
		layerLengths[size+1] = outputLength;
		scan.close();
		return layerLengths;
	}

	public static void main(String[] args) {
		try {
			NeuralNetDriver d = new NeuralNetDriver();
			
//			d.testrun3();
			
			ArrayList<Point> data= d.readTrainingData("trainSet_data/trainSet_05.dat");
			Point p = data.get(0);
			int [] lengths = d.readLengths(p.getDataLength(), p.getCassificationLength());
			System.out.println("Initalizing Neural Net...");
			NeuralNet net = new NeuralNet( lengths, data);
			System.out.println("Train Neural Net...");
			net.backPropLearning();
			System.out.println("Test Neural Net...");
			System.out.printf("Accuracy: %f",net.testData());
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
