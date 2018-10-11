import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class KDBuilder {
	private int dimension;
	
	public KDBuilder(int d) {
		dimension = d;
	}
	
	public LinkedList<Point> readData(BufferedReader rin) throws IOException{
		LinkedList<Point> data = new LinkedList<Point>();
		String line = rin.readLine();
		
		while(line != null) {
			data.push(new Point(mapStringToDouble(line.split(" "))));
			line = rin.readLine();
		}
		return data;
	}
	
	/* Maps an array of string to an array of doubles. Curse java for not having map built in for arrays
	 * @param s - String array to make doubles
	 * @returns - an array of doubles
	 */
	private double[] mapStringToDouble(String [] s) {
		double [] d = new double[s.length];
		for(int i = 0; i<s.length; i++) {
			d[i] = Double.parseDouble(s[i]);
		}
		return d;
	}
	
	public KDTree createTree(int setSize, LinkedList<Point> data) {
		KDTree tree = new KDTree(dimension, setSize);
		tree.createTree(data);
		return tree;
	}

	public static void main(String[] args) throws IOException{
		BufferedReader rin = new BufferedReader(new FileReader("inputData/2d_small.txt"));
		String line = rin.readLine();
		KDBuilder driver = new KDBuilder(Integer.parseInt(line));
		LinkedList<Point> data = driver.readData(rin);
		System.out.println(data);
		
		KDTree tree = driver.createTree(4,data);
		
		

	}

}
