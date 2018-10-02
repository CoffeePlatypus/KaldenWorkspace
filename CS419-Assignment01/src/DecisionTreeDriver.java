import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/* Driver class for DecisionTree
 * @author Julia Froegel
 */
public class DecisionTreeDriver {
	private HashSet<Mushroom> trainingSet;
	private HashSet<Mushroom> testingSet;
	private int trainingSize;
	private int incrementSize;
	boolean debug;
	boolean failureAnalysis;
	boolean verbose;
	
	/* Constructs DecisionTreeDriver 
	 */
	public DecisionTreeDriver() {
		trainingSet = new HashSet<Mushroom>();
		testingSet = new HashSet<Mushroom>();
		trainingSize = 1000; //probs change later
		incrementSize = 25;
		debug = false;
		failureAnalysis = false;
		verbose = false;
	}
	
	/* read and error check testing size input
	 * @param rin - scanner to input from user
	 * @return int for error checking
	 */
	public boolean readParams(Scanner rin) {
		System.out.print("Enter training set size in range 250 to 1000: ");
		trainingSize = rin.hasNextInt() ? rin.nextInt() : 0;
		System.out.println();
		if(trainingSize < 250 || trainingSize > 1000) {
			System.out.println(trainingSize+" - Invalid training set size");
			if(!rin.hasNextInt() && rin.hasNext()){
				System.out.println(rin.next()+" is not an even an int");
			}
			return false;
		}
		
		System.out.print("Enter increment size 10, 25, or 50: ");
		incrementSize = rin.hasNextInt() ? rin.nextInt() : 0;
		System.out.println();
		if(incrementSize != 10 && incrementSize != 25 && incrementSize != 50) {
			System.out.println(incrementSize+"- Invalid increment size");
			if(!rin.hasNextInt() && rin.hasNext()){
				System.out.println(rin.next()+" is not an even an int");
			}
			return false;
		}
		return true;
	}
	
	/* Divide mushrooms into TrainingSet and TestingSet
	 * @param br - Buffered reader to read mushrooms from file
	 */
	public void makeSets(BufferedReader br) throws IOException {
		String line = br.readLine();
		int count = 0;
		while(line != null) {
			Mushroom m = new Mushroom(line);
			if(count < trainingSize && Math.random() > .8) {
				trainingSet.add(m);
				count++;
			}else {
				testingSet.add(m);
			}
			line = br.readLine();
		}
		if(trainingSet.size() != trainingSize) {
			Iterator<Mushroom> ranfix = testingSet.iterator();
			while(trainingSet.size() < trainingSize && ranfix.hasNext()) {
				Mushroom temp = ranfix.next();
				testingSet.remove(temp);
				trainingSet.add(temp);
			}
		}
		if(verbose) {
			System.out.println("Training Set Size: "+trainingSet.size());
			System.out.println("Testing  Set Size: "+testingSet.size());
		}
	}
	
	/* build a DecisionTree with testingSubset
	 * @param numTest - number of tests to include in subset for building tree
	 * @return DecisionTree built with from testingSet subset
	 */
	public DecisionTree buildTree(int numTests) {
		DecisionTree t = new DecisionTree(trainingSet, debug);
		t.buildTree(numTests);
		return t;
	}
	
	/* Tests built DecisionTree's accuracy on training set for debugging purposes
	 * @param t - DecisionTree to test
	 */
	public void testTraining(DecisionTree t) {
		int correctCount = 0;
		int falsePoison = 0;
		int falseEdible = 0;
		Iterator<Mushroom> tester = trainingSet.iterator();
		while(tester.hasNext()) {
			Mushroom mushi = tester.next();
			boolean prediction = t.predictPoisonious(mushi);
			if(prediction == mushi.isPoisonous()) {
				correctCount++;
			}else if(prediction) {
				falsePoison++;
			}else {
				falseEdible++;
			}
		}
		System.out.println("Correct Ratio - [ "+correctCount+" / "+trainingSet.size()+" ] = "+ (correctCount/(double)trainingSet.size())*100 +" % accuracy");
		if(failureAnalysis) {
			System.out.println("\tPredicted poisonious when edible - "+falsePoison);
			System.out.println("\tPredicted edible when poisionious - "+falseEdible);
		}
	}
	
	/* Tests DecisionTree on testing set
	 * @param DecisionTree to test
	 */
	public String test(DecisionTree t) {
		int correctCount = 0;
		int falsePoison = 0;
		int falseEdible = 0;
		Iterator<Mushroom> tester = testingSet.iterator();
		while(tester.hasNext()) {
			Mushroom mushi = tester.next();
			boolean prediction = t.predictPoisonious(mushi);
			if(prediction == mushi.isPoisonous()) {
				correctCount++;
			}else if(prediction) {
				falsePoison++;
			}else {
				falseEdible++;
			}
		}
		String acuracy = ((correctCount/(double)testingSet.size())*100)+"";
		System.out.println("Correct Ratio - [ "+correctCount+" / "+testingSet.size()+" ] = "+ acuracy.substring(0,7) +" % accuracy");
		if(failureAnalysis) {
			System.out.println("\tPredicted poisonious when edible - "+falsePoison);
			System.out.println("\tPredicted edible when poisionious - "+falseEdible);
		}
		return acuracy.substring(0,7);
	}
	
	public String [] bulkTest() {
		DecisionTree t = null;
		String [] stats = new String[trainingSize/incrementSize];
		for(int i = incrementSize; i <=trainingSize; i+=incrementSize) {
			System.out.println("\nBuilding tree with training set size: "+i);
			t = buildTree(i);
			stats[(i/incrementSize)-1] = test(t);
			if(debug) System.out.println(t);
		}
		if(t != null && verbose) {
			System.out.println(t.toString());
		}
		return stats;
	}
	
	public void printStats(String [] stats) {
		System.out.println("\n-----------\nStatistics\n-----------\n");
		for(int i = 0; i<stats.length; i++) {
			int size = (i+1)*incrementSize;
			System.out.println("Training Set Size: "+( size> 99? size == 1000? size: " "+size :"  "+size)+"\t\tSuccess: "+stats[i]+"%");
		}
	}
	
	public static void main(String[] args) {
		DecisionTreeDriver d = new DecisionTreeDriver();
		String file = "";
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equals("-v")) {
				d.verbose = true;
			}else if(args[i].equals("-a")) {
				d.failureAnalysis = true;
			}else if(args[i].equals("-d")) {
				d.debug = true;
			}else if(args[i].startsWith("-f=")) {
				file = args[i].split("=")[1];
			}
		}
		
		Scanner rin = new Scanner(System.in);
		if(!d.readParams(rin)) {
			return;
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(file.equals("")? "mushroom_data.txt" : file));
			d.makeSets(br);
			String [] stats = d.bulkTest();	
			d.printStats(stats);
			
		} catch(Exception e) {
			System.out.println("Not Crashing - THIS IS A HELPFUL ERROR MESSAGE :P");
			e.printStackTrace();
		}
		rin.close();
	}
}
