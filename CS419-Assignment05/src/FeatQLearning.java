import java.util.ArrayList;

public class FeatQLearning {
	
	private final int UP = 0;
	private final int DOWN = 1;
	private final int LEFT = 2;
	private final int RIGHT = 3;
	
	private int [] start;
	private int [][] reward;
	private int [] goal;
	
	private double w1 = 1;
	private double w2 = 1;
	
	public FeatQLearning(ArrayList<String[]> w) {
		start = new int[2];
		goal = new int[2];
		reward = new int[w.size()][];
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
		System.out.println("Start: "+ start[0]+" "+start[1]);
		System.out.println("Dim  : "+w.size()+" x "+reward[0].length);
	}
	
	public int[] takeAction(int x, int y, int action) {
		int [] state = new int[2];
		state[0] = x;
		state[1] = y;
		boolean slipped = false && Math.random() < .2;
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
	
	public void featLearn() {
		w1 = 1;
		w2 = 1;
		
		double episilon = 0.9;
		double lrate = 0.9;
		
		for(int episode = 0; episode < 25; episode++) {
			System.out.println("weights "+ w1 +" "+ w2);
			featEpisode(episilon, lrate);
			if(episode%100 == 0) {
//				TODO eval policy

			}
			if(episode%200 == 0) {
				episilon = 0.9/(((episode)/200.0) +1);
				System.out.println("Episode "+episode+" - Epi: "+episilon);
			}
			if(episode%1000 == 0) {
				lrate = 0.9/(((episode)/1000.0) +1);
				System.out.println("Episode "+episode+" - lrate: "+lrate);
			}
		}
		printFeatures();
	}
	
	public void featEpisode(double episilon, double lrate) {
		int [] state = new int[2];
		state[0] = start[0];
		state[1] = start[1];
		for(int time = 0; time < 10 ; time++) {
			System.out.println("State "+state[0]+" "+state[1] );
			int action = featBestAction(state);
			int [] sprime = takeAction(state[0], state[1], action);
			double delta = reward[sprime[0]][sprime[1]] + (0.9 * featActionValue(sprime, featBestAction(sprime))) - featActionValue(state,action);
			w1 += lrate * delta * f1goal(sprime);
			w2 += lrate * delta * f2mines(state, action);
			state = sprime;
		}
	}
	
	public int featBestAction(int [] state) {
//		int []
		double min = featActionValue(state, UP);
		int mindex = UP;
		for(int action = 1; action<4; action++) {
			double temp = featActionValue(state, action);
			if(temp < min){
				mindex = action;
				min = temp;
			}
		}
		return mindex;
	}
	
	public double featActionValue(int [] state, int action) {
		return  w1 * f1goal(takeAction(state[0], state[1], action)) + w2 * f2mines(takeAction(state[0], state[1], action), action);
	}
	
	public double f1goal(int [] state) {
		return (Math.abs(goal[0] - state[0]) + Math.abs(goal[1] - state[1])) / (double) goal[0] + goal[1];
	}
	
	public double f2mines(int[] state, int action) {
		if(atMine(state)) {
			return 1;
		}else if(action == LEFT) {
			 return 1/state[1];
		 }else if(action == RIGHT) {
			 return 1/reward.length - state[1];
		 }else {
			 return (1/state[1]) > (1/reward.length - state[1]) ? 1/reward.length - state[1] : 1/state[1];
		 }
	}
	
	private char maxActionToChar(int i) {
		switch(i) {
		case 0 : return '^';
		case 1 : return 'v';
		case 2 : return '<';
		case 3 : return '>';
		default : return 'U';
		}
	}
	
	private boolean atGoal(int[] state) {
		return state[0] == goal[0] && state[1] == goal[1]; 
	}
	
	private boolean atMine(int [] state) {
		return reward[state[0]][state[1]] == -100;
	}
	
	public void printFeatures() {
		for(int i = 0; i<reward.length; i++) {
			for(int j = 0; j<reward[0].length; j++) {
				int [] state = new int[2];
				state[0] = i;
				state[1] = j;
//				System.out.println("\nState: "+state[0]+" "+state[1]);
				if(atMine(state)) {
					System.out.print("M ");
				}else if(atGoal(state)) {
					System.out.print("G ");
				}else {
					System.out.print(maxActionToChar(featBestAction(state))+" ");
				}
			}
			System.out.println();
		}
	}
	
}
