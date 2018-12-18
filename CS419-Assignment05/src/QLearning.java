import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class QLearning {
	
	private final int UP = 0;
	private final int DOWN = 1;
	private final int LEFT = 2;
	private final int RIGHT = 3;
	
	private int [] start;
	private int [][] reward;
	private double [][][] sa;
	private int [] goal;

	private BufferedWriter log;
	
	public QLearning(ArrayList<String[]> w, BufferedWriter l) {
		log = l;
		start = new int[2];
		goal = new int[2];
		reward = new int[w.size()][];
		sa = new double[w.size()][w.get(0).length][4];
		for(int i = 0; i<w.size(); i++) {
			String[] line = w.get(i);
			reward[i] = new int[line.length];
			for(int j = 0; j< line.length; j++) {
				if(line[j].equals("M")) {
					reward[i][j] = -100;
				}else if(line[j].equals("G")) {
					reward[i][j] = 0;
					goal[0] = i;
					goal[1] = j;
				}else {
					reward[i][j] = -1;
					if(line[j].equals("S")) {
						start[0] = i;
						start[1] = j;
					}
				}
			}
//			System.out.println(Arrays.toString(reward[i]));
			
		}
		System.out.println("#########################################################################");
		System.out.println("Q Learning");
		System.out.println("Start: "+ start[0]+" "+start[1]);
		System.out.println("Dim  : "+w.size()+" x "+reward[0].length);
		System.out.println("#########################################################################");
	}
	
	/* MDP = < S, A, P, R, T>
	 * S : set of states
	 *	 * a state is an <i, j> int pair
	 * A : set of actions
	 * 	 * <up, down, left, right> // should it be enum?
	 * P : state transion function 
	 * 	 * P( s, a, s') : prop of ending up in s' from s with action a
	 * R : reward function
	 * 	  * reward of state <i, j> is reward[i][j] 	
	 */
	
	public int[] takeAction(int[] s, int action) {
		int [] state = new int[2];
		state[0] = s[0];
		state[1] = s[1];
		boolean slipped = true && Math.random() < .2;
		switch(action) {
		case UP :
			if(state[0] > 0) {
				state[0]--;
			}
			if(slipped) {
				if(Math.random() < .5 && state[1] > 0) {
					state[1]--;
				}else if(state[1] < reward[0].length -1){
					state[1]++;
				}
			}
			break;
		case DOWN : 
			if(state[0] < reward.length -1) {
				state[0]++;
			}
			if(slipped) {
				if(Math.random() < .5 && state[1] > 0) {
					state[1]--;
				}else if(state[1] < reward[0].length -1){
					state[1]++;
				}
			}
			break;
		case LEFT : 
			if(state[1] > 0) {
				state[1]--;
			}
			if(slipped) {
				if(Math.random() < .5 && state[0] > 0) {
					state[0]--;
				}else if(state[0] < reward.length -1){
					state[0]++;
				}
			}
			break;
		case RIGHT : 
			if(state[1] < reward[0].length -1) {
				state[1]++;
			}
			if(slipped) {
				if(Math.random() < .5 && state[0] > 0) {
					state[0]--;
				}else if(state[0] < reward.length -1){
					state[0]++;
				}
			}
		}
		return state;
	}
	
	public void qlearn() throws IOException {
		// for all states s and actions a sa = 0
		for(int i = 0; i<sa.length; i++) {
			for(int j = 0; j < sa[i].length; j++) {
				sa[i][j][UP] = 0;
				sa[i][j][DOWN] = 0;
				sa[i][j][LEFT] = 0;
				sa[i][j][RIGHT] = 0;
			}
		}
		// for 10,000 episodes
		double episilon = 0.9;
		double lrate = 0.9;
		for(int episode = 0; episode < 10000; episode++  ) {			
			runEpisode(episilon, lrate);
			if(episode%100 == 0) {
				evalQLearn();
			}
			if(episode%200 == 0) {
				episilon = 0.9/(((episode)/200.0) +1);
//				System.out.println("Episode "+episode+" - Epi: "+episilon);
			}
			if(episode%1000 == 0) {
				lrate = 0.9/(((episode)/1000.0) +1);
//				System.out.println("Episode "+episode+" - lrate: "+lrate);
			}
		}
		printSA();
	}
	
	private void runEpisode(double epi, double lrate) {
		int [] state = new int[2];
		state[0] = start[0];
		state[1] = start[1];
		// for time step t < matrix width * height
		for(int time = 0; time < (reward.length * reward[0].length); time++ ) {
//			System.out.println("State: "+state[0]+" " +state[1]);
			if(atMine(state) || atGoal(state)) {
				return;
			}
			int action;
			if(Math.random() <= epi) {
				action = (int)(Math.random() * 4);
			}else {
				action = getMaxActionIndex(state);
			}
			int [] primestate = new int[2];
			primestate = takeAction(state,action);
//			System.out.println("\taction "+maxActionToChar(action));
//			System.out.println("\tSprime: "+primestate[0]+" " +primestate[1]);
			// sa = sa +alpha(reward + gamma*max(q', s') - sa)
			sa[state[0]][state[1]][action] += lrate * (reward[primestate[0]][primestate[1]] + ((.9) * getMaxAction(primestate)) - sa[state[0]][state[1]][action]);
//			getMaxAction(state);
			state[0] = primestate[0];
			state[1] = primestate[1];
		}
	}
	
	private void evalQLearn() throws IOException {
		int[] scores = new int[50];
		int total = 0;
		for(int i = 0; i<scores.length; i++) {
			int [] state = new int[2];
			state[0] = start[0];
			state[1] = start[1];
			
//			System.out.println("here: "+state[0]+" "+state[1]);
			for(int time = 0; time< (reward.length * reward[0].length); time++ ) {
//				System.out.print(state[0]+" "+state[1]+" > ");			
//				printSAPairs(state);
				if(atMine(state)) {
//					System.out.println("hit mine");
					scores[i] -= 100;
					break;
				}else if(atGoal(state)) {
//					System.out.println("got goal!!!!!!!!!!!!!!!!!!!");
					break;
				}
				scores[i]--;
				int action = getMaxActionIndex(state);
				state = takeAction(state, action);
				
			}
			total += scores[i];
		}
//		System.out.println("Eval: "+Arrays.toString(scores));
		System.out.println("avg: "+(total/scores.length));
		log.write((total/scores.length)+", ");
	}
	
	private boolean atGoal(int[] state) {
		return state[0] == goal[0] && state[1] == goal[1]; 
	}
	
	private boolean atMine(int [] state) {
		return reward[state[0]][state[1]] == -100;
	}
	
	public int getMaxActionIndex(int [] state) {
		// up down left right
		double max = sa[state[0]][state[1]][UP];
		int index = UP;
//		System.out.println(state[0]+", "+state[1]+" : "+ Arrays.toString(sa[state[0]][state[1]]));
		for(int i = 1; i<4; i++) {
			if(sa[state[0]][state[1]][i] > max) {
				max = sa[state[0]][state[1]][i];
				index = i;
			}
		}
		
		return index;
	}
	
	public double getMaxAction(int [] state) {
		return sa[state[0]][state[1]][getMaxActionIndex(state)];
	}
	
	public double getActionSum(int [] state) {
		double sum = sa[state[0]][state[1]][UP];
		//System.out.println(state[0]+", "+state[1]+" : "+ Arrays.toString(sa[state[0]][state[1]]));
		for(int i = 1; i<4; i++) {
			sum += sa[state[0]][state[1]][i];
		}
		return sum;
	}
	
	private char maxActionToChar(int i) {
		switch(i) {
		case 0 : return 'U';
		case 1 : return 'D';
		case 2 : return 'L';
		case 3 : return 'R';
		default : return 'U';
		}
	}
	
	public void printSA() {
		System.out.println("#########################################################################");
		System.out.println("Q Learning Policy");
		System.out.println("#########################################################################");
		for(int i = 0; i<sa.length; i++) {
			for(int j = 0; j<sa[i].length; j++) {
				int [] state = new int[2];
				state[0] = i;
				state[1] = j;
				if(atMine(state)) {
					System.out.print("M ");
				}else if(atGoal(state)) {
					System.out.print("G ");
				}else {
					System.out.print(maxActionToChar(getMaxActionIndex(state))+" ");

				}
			}
			System.out.println();
		}
		System.out.println("#########################################################################\n");
	}
	

}
