import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class KDBuilder {
	private int dimension;
	private boolean debug;
	
	public KDBuilder(int d) {
		dimension = d;
		debug = false;
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
		try {
			BufferedReader rin = new BufferedReader(new FileReader((args.length >= 1) ? args[0]:"inputData/2d_small.txt"));
			String line = rin.readLine();
			KDBuilder driver = new KDBuilder(Integer.parseInt(line));
			LinkedList<Point> data = driver.readData(rin);
			rin.close();
			if (driver.debug)System.out.println("Input data: "+data);
			
			KDTree tree = driver.createTree((args.length >= 2) ? Integer.parseInt(args[1]): 1 ,data);
			Scanner scan = new Scanner(System.in);
			
			System.out.println("Print leaves [Y|n]? : ");
			String s = scan.next();
			
			if (s.equalsIgnoreCase("y")) System.out.println(tree);
			
			System.out.println("Test data [Y|n]? : ");
			s = scan.next();
			if(s.equalsIgnoreCase("y")) {
				System.out.println("Enter file name: ");
				s = scan.next();
				System.out.println(s);
				BufferedReader testFile = new BufferedReader(new FileReader(s));
//				BufferedReader testFile = new BufferedReader(new FileReader("inputData/3dtest.txt"));
				int testDim = Integer.parseInt(testFile.readLine());
				if (testDim != driver.dimension) {
					System.out.println("Test files and tree dimesion mismatch");
					System.out.println("Beep bloop bloop... \nGoodbye! \n: ]");
					testFile.close();
					return;
				}
				LinkedList<Point> test = driver.readData(testFile);
				testFile.close();
				
				for(int i = 0; i<test.size(); i++) {
					if(driver.debug) System.out.println(i);
					tree.testPoint(test.get(i));
				}
			}
			scan.close();
			System.out.println("Beep bloop bloop... \nGoodbye! \n: ]");
			
		} catch (FileNotFoundException e) {
			System.out.println("Beep bloop bloop...\nFile Not Found!\n:' [");
		} catch (IOException e) {
			System.out.println("Beep bloop bloop...\nIO Error!\n:' [");
		}catch (NumberFormatException e) {
			System.out.println("Beep bloop bloop...\nBad Number!\n:' [");
		} catch (Exception e) {
			System.out.println("Beep bloop bloop...\nERROR!!? \n:' [");
			e.printStackTrace();
		}
	}

}
