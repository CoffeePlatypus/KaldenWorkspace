import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
		rin.close();
		return world;
	}

	public static void main(String[] args) {
		try  {
			QLearningDriver driver = new QLearningDriver();
//			ArrayList<String[]> world = driver.readFile("smol_pipe.txt");
			ArrayList<String[]> world = driver.readFile("pipe_world.txt");
			BufferedWriter logger = new BufferedWriter(new FileWriter("avg.txt"));
			QLearning learner = new QLearning(world, logger);
			learner.qlearn();
			logger.newLine();;
			FeatQLearning len = new FeatQLearning(world,logger);
			len.featLearn();
			logger.close();
		}catch (IOException e) {
			System.out.println("File Problem");
		}
	};

}
