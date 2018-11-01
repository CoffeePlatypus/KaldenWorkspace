import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class NeuralNetDriver {
	
	public NeuralNetDriver() {
	}
	
	public ArrayList<Point> readData(String path) throws IOException {
		BufferedReader rin = new BufferedReader(new FileReader(path));
		ArrayList<Point> dataPoints = new ArrayList<Point>();
		String line = rin.readLine();
		while(line != null) {
			if(line.charAt(0) != '#') {
				dataPoints.add(stringToPoint(line));
			}else {
//				System.out.println(line);
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
			d[i] = (x < 1? x : x/255);
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
		
		NeuralNet n = new NeuralNet( lens);
		n.backPropLearning(l);
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
		
		NeuralNet n = new NeuralNet( lens);
		n.backPropLearning(l);
		n.testData(l);
//		System.out.println(n.test(p));
	}
	
	public int[] readLengths(int inputLength, int outputLength, Scanner scan) {
		System.out.println("Enter number of hidden layers < 10: ");
		int size = scan.nextInt();
		int [] layerLengths = new int [size+2];
		layerLengths[0] = inputLength;
		for(int i = 1; i<size+1; i++) {
			System.out.printf("Enter size of layer[%d]: \n", i);
			layerLengths[i] = scan.nextInt(); 
		}
		layerLengths[size+1] = outputLength;
		return layerLengths;
	}
	
	public void write(String name, NeuralNet n) throws IOException {
		BufferedWriter wout = new BufferedWriter(new FileWriter("neuralNets/"+name));
		wout.write(n.write());
		wout.close();
	}
	
	public NeuralNet load(String fname) throws NumberFormatException, IOException {
		BufferedReader rin = new BufferedReader(new FileReader("neuralNets/"+fname));
		int numLayers = Integer.parseInt(rin.readLine());
		System.out.println("Num Layers: "+numLayers);
		NeuralNet net = new NeuralNet(numLayers);
		net.setLayer(0, new Perceptron[Integer.parseInt(rin.readLine())]);
		for(int i = 1; i < numLayers; i++) {
			int layerLength = Integer.parseInt(rin.readLine());
			Perceptron [] perceptrons = new Perceptron[layerLength];
			System.out.println(i + " : " + perceptrons.length);
			for(int j = 0; j < layerLength; j++) {
				
				String line = rin.readLine();
				System.out.println(line);
				perceptrons[j] = new Perceptron(parseWeights(line.split(" ")));
			}
			net.setLayer(i, perceptrons);
		}
		rin.close();
		return net;
	}
	
	public double[] parseWeights(String [] w) {
		double [] weights = new double[w.length];
		for(int i = 0; i<w.length; i++) {
			weights[i] = Double.parseDouble(w[i]);
		}
		return weights;
	}
	
	public NeuralNet createAndTrain(Scanner scan) throws IOException {
		ArrayList<Point> data = readData("trainSet_data/trainSet_05.dat");
		Point p = data.get(0);
		int [] lengths = readLengths(p.getDataLength(), p.getCassificationLength(), scan);
		System.out.println("Initalizing Neural Net...");
		NeuralNet net = new NeuralNet( lengths);
		System.out.println("Train Neural Net...");
		net.backPropLearning(data);
		return net;
	}
	

	public static void main(String[] args) {
		try {
			NeuralNetDriver d = new NeuralNetDriver();
			
//			d.testrun3();
			Scanner scan = new Scanner(System.in);
			NeuralNet net = d.createAndTrain(scan);
//			NeuralNet net = d.load("net1.txt");
			
			System.out.println("Test Neural Net...");
			ArrayList<Point> testData = d.readData("testSet_data/testSet_05.dat");
			net.testData(testData);
//			System.out.println("Save [Y|n]?");
//			if(scan.next().equals("Y")) {
//				d.write("net1.txt", net);
//			}
			System.out.println("Beep bloop bloop... \nGoodbye! \n: ]");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
