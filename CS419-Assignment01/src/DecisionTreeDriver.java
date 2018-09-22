import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DecisionTreeDriver {
	private Set<Mushroom> trainingSet;
	private Set<Mushroom> testingSet;
	private int trainingSize;
	private int incrementSize;
	
	public DecisionTreeDriver() {
		trainingSet = new HashSet<Mushroom>();
		testingSet = new HashSet<Mushroom>();
		trainingSize = 0;
		incrementSize = 0;
	}
	
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
		System.out.println(trainingSet.size());
		System.out.println(testingSet.size());
	}

	public static void main(String[] args) {
		DecisionTreeDriver d = new DecisionTreeDriver();
		Scanner rin = new Scanner(System.in);
		if(d.readParams(rin) < 0) {
			return;
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("mushroom_data.txt"));
			d.makeSets(br);
		} catch(IOException e) {
			System.out.println(e.getStackTrace());
		}
		
		// make tree
		rin.close();
	}

}
