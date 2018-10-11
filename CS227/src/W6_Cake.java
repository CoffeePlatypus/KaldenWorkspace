import java.util.Scanner;

public class W6_Cake {
	public static void main(String [] args) {
		Scanner rin = new Scanner(System.in);
		while(rin.hasNext()) {
			int width = rin.nextInt();
			int n = rin.nextInt();
			int area = 0;
			for(int i = 0; i<n; i++) {
				int x = rin.nextInt();
				int y = rin.nextInt();
				area += x * y;
			}
			System.out.println(area/width);
		}
		rin.close();
	}
}
