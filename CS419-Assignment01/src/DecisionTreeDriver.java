import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class DecisionTreeDriver {
	private HashSet<Mushroom> trainingSet;
	private HashSet<Mushroom> testingSet;
	private int trainingSize;
	private int incrementSize;
	private boolean debug = true;
	
	public DecisionTreeDriver() {
		trainingSet = new HashSet<Mushroom>();
		testingSet = new HashSet<Mushroom>();
		trainingSize = 250; //probs change later
		incrementSize = 0;
	}
	
	/* read and error check testing size input
	 */
	public int readParams(Scanner rin) {
		System.out.println("Enter training set size in range 250 to 1000: ");
		trainingSize = rin.hasNextInt() ? rin.nextInt() : 0;
		if(trainingSize < 250 || trainingSize > 1000) {
			System.out.println("Invalid training set size");
			return -1;
		}
		
		System.out.println("Enter increment size 10, 25, or 50: ");
		incrementSize = rin.hasNextInt() ? rin.nextInt() : 0;
		if(incrementSize != 10 && incrementSize != 25 && incrementSize != 50) {
			System.out.println("Invalid increment size");
			return -1;
		}
		return 1;
	}
	
	/* Divide mushrooms into TrainingSet and TestingSet
	 */
	public void makeSets(BufferedReader br) throws IOException {
		String line = br.readLine();
		int count = 0;
		while(line != null) {
			Mushroom m = new Mushroom(line);
			if(count++ < trainingSize) {
				trainingSet.add(m);
			}else {
				testingSet.add(m);
			}
			line = br.readLine();
		}
		if(debug) {
			System.out.println("Training Set Size: "+trainingSet.size());
			System.out.println("Testing  Set Size: "+testingSet.size());
		}
	}
	
	/* build a DecisionTree with testingSubset
	 * @param numTest - number of tests to include in subset
	 */
	public DecisionTree buildTree(int numTests) {
		DecisionTree t = new DecisionTree(trainingSet);
		t.buildTree(numTests);
		return t;
	}
	
	public void test(DecisionTree t) {
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
		System.out.println("Correct Ratio - [ "+correctCount+" / "+testingSet.size()+" ] = "+ (correctCount/(double)testingSet.size())*100 +" % accuracy");
		System.out.println("\tPredicted poisonious when edible - "+falsePoison);
		System.out.println("\tPredicted edible when poisionious - "+falseEdible);
	}

	public static void main(String[] args) {
		DecisionTreeDriver d = new DecisionTreeDriver();
		Scanner rin = new Scanner(System.in);
//		if(d.readParams(rin) < 0) {
//			return;
//		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("mushroom_data.txt"));
//			BufferedReader br = new BufferedReader(new FileReader("miniShroomData.txt"));
			d.makeSets(br);
			//TODO implement build and test tree in loop for increments
			DecisionTree t = d.buildTree(250);
			d.test(t);
			
		} catch(Exception e) {
			System.out.println("Not Crashing - THIS IS A HELPFUL ERROR MESSAGE :P");
			e.printStackTrace();
		}
		
		// make tree
		rin.close();
	}

}
