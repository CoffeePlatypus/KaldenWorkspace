import java.util.Scanner;

public class W2_ComboLock {

	public static void main(String[] args) {
		int [] combo = new int[4];
		Scanner rin = new Scanner(System.in);
		readCombo(rin,combo);
		while(!quitCombo(combo)) {
			int total = 720;
			total += 9*((40+combo[0]) - combo[1]);
			total += 360;
			total += 9*((combo[2] + 40)- combo[1]);
			total += 9*((combo[2] + 40)- combo[3]);
			System.out.println(total);
			readCombo(rin, combo);
		}
		rin.close();
	}
	
	public static void readCombo(Scanner rin, int[] combo) {
		for(int i=0; i<4; i++) {
			combo[i] = rin.nextInt();
		}
	}
	
	public static boolean quitCombo(int [] combo) {
		return (combo[0] == 0) && (combo[1] == 0) && (combo[2] == 0) && (combo[3] == 0);
	}

}
