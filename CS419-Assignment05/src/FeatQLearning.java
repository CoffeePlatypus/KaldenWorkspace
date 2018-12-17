import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

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
		System.out.println("#########################################################################");
		System.out.println("Feature Q Learning");
		System.out.println("Start: "+ start[0]+" "+start[1]);
		System.out.println("Dim  : "+w.size()+" x "+reward[0].length);
		System.out.println("#########################################################################");
	}
	
	public int[] takeAction(int x, int y, int action) {
		int [] state = new int[2];
		state[0] = x;
		state[1] = y;
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
	
	public int[] getPrimeState(int x, int y, int action) {
		int [] state = new int[2];
		state[0] = x;
		state[1] = y;
		switch(action) {
		case UP :
			if(state[0] > 0) {
				state[0]--;
			}
			break;
		case DOWN : 
			if(state[0] < reward.length -1) {
				state[0]++;
			}
			break;
		case LEFT : 
			if(state[1] > 0) {
				state[1]--;
			}
			break;
		case RIGHT : 
			if(state[1] < reward[0].length -1) {
				state[1]++;
			}
		}
		return state;
	}
	
	public void featLearn() {
		w1 = 0;
		w2 = 0;
		
		double episilon = 0.9;
		double lrate = 0.9;
		
		for(int episode = 0; episode < 10000; episode++) {
			featEpisode(episilon, lrate);
			if(episode%100 == 0) {
				evalPolicy();
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
		printFeatures();
//		compareFeatures();
	}
	
	public void featEpisode(double episilon, double lrate) {
		int [] state = new int[2];
		state[0] = start[0];
		state[1] = start[1];
		for(int time = 0; time <  (reward.length * reward[0].length); time++) {
			if(atMine(state) || atGoal(state)) {
				return;
			}
			int action;
			if(Math.random() <= episilon) {
				action = (int)(Math.random() * 4);
			}else {
				action = featBestAction(state);
			}
//			System.out.println("\taction: "+action);
			int [] sprime = takeAction(state[0], state[1], action);
//			System.out.println("\t\tPrime State:" +sprime[0]+" "+sprime[1]);
//			System.out.println("\t\t best action value "+featActionValue(sprime, featBestAction(sprime)));
			double delta = reward[sprime[0]][sprime[1]] + (0.9 * featActionValue(sprime[0], sprime[1], featBestAction(sprime))) - featActionValue(state[0], state[1],action);
//			
			w1 += lrate * delta * f1goal(sprime);
			w2 += lrate * delta * f2mines(state, action);
			state = sprime;
		}
	}
	
	public void evalPolicy() {
		int total = 0;
		int[] scores = new int[50];
		for(int i = 0; i<scores.length; i++) {
			int [] state = new int[2];
			state[0] = start[0];
			state[1] = start[1];
			
//			System.out.println("here: "+state[0]+" "+state[1]);
			for(int time = 0; time< (reward.length * reward[0].length); time++ ) {
//				System.out.print(state[0]+" "+state[1]+" > ");
				int action = featBestAction(state);			
//				printSAPairs(state);
				if(atMine(state)) {
//					System.out.println("hit mine");
					scores[i] -= 100;
					break;
				}else if(atGoal(state)) {
					break;
				}
				scores[i]--;
				state = takeAction(state[0], state[1], action);
			}
//			System.out.println();
			total += scores[i];
		}
//		System.out.println("Eval: "+Arrays.toString(scores));
		System.out.println("avg: "+(total/scores.length));
	}
	
	public int featBestAction(int [] state) {
//		System.out.println("\t\t\tState "+state[0]+" "+state[1]);
//		int []
		double max = featActionValue(state[0], state[1], UP);
//		System.out.println("\tval: "+min+ " action up");
		int index = UP;
		for(int action = 1; action<4; action++) {
			double temp = featActionValue(state[0], state[1], action);
//			System.out.println("\tval: "+temp+ " action "+action);
			if(temp > max){
				index = action;
				max = temp;
			}
		}
		return index;
	}
	
	public double featActionValue(int x, int y, int action) {
		double f1 = f1goal(getPrimeState(x, y, action));
		int [] s = {x,y};
		double f2 = f2mines( s, action);
//		System.out.println("\t\t\t\tAction"+action+"f1: "+f1 + " "+f2);
		return  (w1 * f1)  + (w2 * f2);
	}
	
	public double f1goal(int [] state) {
		return (Math.abs(goal[0] - state[0]) + Math.abs(goal[1] - state[1])) / ((double) goal[0] + goal[1]);
	}
	
	public double f2mines(int[] state, int action) {
		 if(action == LEFT) {
			 return 1/(state[1] + 1.0);
		 }else if(action == RIGHT) {
			 return 1/( (reward[0].length + 0.0) - state[1]);
		 }else {
			 if(state[1] > reward[0].length /2) { // if closer to left
				 return 1/(state[1] + 1.0);
			 }
			 return 1/((reward[0].length + 0.0) - state[1]);
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
	
	public void compareFeatures() {
		System.out.println(reward[0].length);
		for(int i = 0 ; i< 3; i++) {
			for(int j = 1; j<reward[0].length/2; j++) {
				int[] state = new int[2];
				state[0] = i;
				state[1] = j;
				int [] match = new int[2];
				match[0] = i;
				match[1] = (reward[0].length - 1) - j;
				System.out.println("compare "+state[0]+" [ "+state[1]+" ~~~ "+match[1]+" ]");
				System.out.println("\tups : "+ featActionValue(state[0], state[1], UP)+" ~~~ "+featActionValue(match[0], match[1],UP));
				System.out.println("\tdowns : "+ featActionValue(state[0], state[1], DOWN)+" ~~~ "+featActionValue(match[0], match[1],DOWN));
				System.out.println("\tin : "+ featActionValue(state[0], state[1], RIGHT)+" ~~~ "+featActionValue(match[0], match[1],LEFT));
				System.out.println("\tout : "+ featActionValue(state[0], match[1], LEFT)+" ~~~ "+featActionValue(match[0], match[1],RIGHT));
				System.out.println("\t\tmines out! "+ f2mines(state, LEFT)+ " ~~~ "+ f2mines(match,RIGHT));
				System.out.println("\t\tmines in! "+ f2mines(state, RIGHT)+ " ~~~ "+ f2mines(match,LEFT));
				System.out.println("\t\tmines up! "+ f2mines(state, UP)+ " ~~~ "+ f2mines(match,UP));
				System.out.println("\t\tmines down! "+ f2mines(state, DOWN)+ " ~~~ "+ f2mines(match,DOWN));
				
				System.out.println("Weights: "+w1+" "+w2);
				System.out.println("\tGoal: " +f1goal(state)+" ~~~ "+f1goal(match));
				System.out.println("\t\tGoal in: " +f1goal(takeAction(state[0], state[1], RIGHT))+" ~~~ "+f1goal(takeAction(match[0], match[1], LEFT)));
				System.out.println("\t\tGoal out: " +f1goal(takeAction(state[0], state[1], LEFT))+" ~~~ "+f1goal(takeAction(match[0], match[1], RIGHT)));
				System.out.println("\tAction: "+ featBestAction(state) +" ~~~ "+ featBestAction(match) );
			}
		}
		System.out.println("Weights: "+w1+" "+w2);
	}
	
	public void printFeatures() {
		System.out.println("#########################################################################");
		System.out.println("Feature Q Learning Policy");
		System.out.println("#########################################################################");
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
		System.out.println("#########################################################################");
	}
	
}
