import java.util.Scanner;
public class W1_AutoAnswer {

	public static void main(String[] args) {
		Scanner rin = new Scanner(System.in);
		int t = rin.nextInt();
		for(int i = 0; i < t; i++) {
			int n = rin.nextInt();
			int res = (((((n * 567) / 9 ) + 7492) * 235) / 47) - 498;
			res = (res % 100) / 10;
			res = res < 0 ? res * -1 : res;
			System.out.println(res);
		}
		rin.close();
	}
}
