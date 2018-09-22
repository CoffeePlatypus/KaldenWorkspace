import java.util.Scanner;

public class W3_MachinedSurfaces {
	public static void main(String[] args) {
		Scanner rin = new Scanner(System.in);
		int n = rin.nextInt();
		while(n != 0) {
			int [] len = new int[n];
			int max = 0;
			for(int i = 0; i<n; i++) {
				String p1 = rin.next();
				if(p1.length() == 25) {
					len[i] = 25;
					max = 25;
				}else {
					String p2 = rin.next();
					len[i] = p1.length() + p2.length();
					if(len[i] > max) {
						max = len[i];
					}
				}
			}
			int voidCount = 0;
			for(int i = 0; i<n; i++) {
				voidCount += (max -len[i]);
			}
			System.out.println(voidCount);
			n = rin.nextInt();
		}
		rin.close();
	}
}
