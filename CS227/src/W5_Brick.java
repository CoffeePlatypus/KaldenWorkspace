import java.util.Scanner;

public class W5_Brick {
	public W5_Brick() {
		
	}

	public static void main(String[] args) {
		Scanner rin = new Scanner(System.in);
		int n = rin.nextInt();
		W5_Brick b = new W5_Brick();
		while(n != 0 ) {
			System.out.println(b.brick(n));
			rin.nextInt();
		}

	}
	
	public int brick(int n) {
		if(n == 1) {
			return 1;
		}else if(n == 2) {
			return 2;
		}else {
			return brick(n-1) + brick(n-2);
		}
	}

}
