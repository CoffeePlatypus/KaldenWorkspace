import java.util.Scanner;

public class W12_SwallowGround {
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		int cases = scan.nextInt();
		for(int i = 0; i<cases; i++) {
			scan.nextLine();
			int w = scan.nextInt();
			boolean flag = true;
			int diff = scan.nextInt() - scan.nextInt();
			for(int j = 1; j< w; j++) {
				if(flag && (scan.nextInt()-scan.nextInt()) != diff) {
					flag = false;
				}else {
					scan.nextLine();
				}
			}
			System.out.println(flag? "yes" : "no");
		}
		scan.close();
	}
}
