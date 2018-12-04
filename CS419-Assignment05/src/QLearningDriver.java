import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class QLearningDriver {
	
	public ArrayList<String[]> readFile(String fname) throws IOException {
		BufferedReader rin = new BufferedReader(new FileReader(fname));
		ArrayList<String[]> world = new ArrayList<String[]>();
		String line = rin.readLine();
		while(line != null) {
			world.add(line.split(""));
			line = rin.readLine();
		}
		return world;
	}

	public static void main(String[] args) {
		try  {
			QLearningDriver driver = new QLearningDriver();
			ArrayList<String[]> world = driver.readFile("pipe_world.txt");
			//world.stream().forEach((line)->{System.out.println(Arrays.toString(line));});
			QLearning learner = new QLearning(world);
			learner.qlearn();
		}catch (IOException e) {
			System.out.println("File Problem");
		}
	};

}
