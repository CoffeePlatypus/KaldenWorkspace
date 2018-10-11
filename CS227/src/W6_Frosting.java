import java.util.Scanner;

public class W6_Frosting {
	public static void main(String [] args) {
		Scanner rin = new Scanner(System.in);
		while(rin.hasNext()) {
			int n = rin.nextInt();
			int area0 = 0;
			int area1 = 0;
			int area2 = 0;
			int [] as = new int[n];
			int [] bs = new int[n];
			for(int i = 0; i<n; i++) {
				as[i] = rin.nextInt();
			}
			for(int i = 0; i<n; i++) {
				bs[i] = rin.nextInt();
			}
			
			for(int i = 0; i<n; i++) {
				for(int j = 0; j<n; j++) {
					int area = as[i] * bs[j];
					switch((i+j)%3) {
					case 0 : area0 += area; break;
					case 1 : area1 += area; break;
					case 2 : area2 += area;
					}
				}
			}
			System.out.println(area1+" "+area2+" "+area0);
		}
		rin.close();
	}
}
