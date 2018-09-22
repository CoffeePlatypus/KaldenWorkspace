import java.util.Scanner;

public class W2_OneTwoThree {
	
	public static void main(String [] args) {
		Scanner rin = new Scanner(System.in);
		int n = rin.nextInt();
		for(int i=0; i<n; i++) {
			String s = rin.next();
			if(s.length() == 5) {
				System.out.println(3);
			}else {
				int countOne = 0;
				char [] one = {'o','n','e'};
				for(int j=0; j<3; j++) {
					if(s.charAt(j) == one[j]) {
						countOne++;
					}
				}
				if(countOne >= 2) {
					System.out.println(1);
				}else {
					System.out.println(2);
				}
			}
		}
		rin.close();
	}
}
