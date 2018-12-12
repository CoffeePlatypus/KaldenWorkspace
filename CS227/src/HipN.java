import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class HipN {

	public static void main(String[] args) {
		HipN aghjava = new HipN();
		aghjava.doStuff();
	}
	
	public void doStuff() {
		Scanner scan = new Scanner(System.in);
		int d = scan.nextInt();
		int [][] board = new int[3][3];
		LinkedList<Point> p1m = new LinkedList<Point>();
		LinkedList<Point> p2m = new LinkedList<Point>();
	
		for(int count = 0; count < (d *d); count++) {
			int player = (count% 2)+1;
			int i = scan.nextInt();
			int j = scan.nextInt();
			board[i][j] = player;
			if(player == 1) {
				check();
				p1m.add(new Point(i,j));
			}else {
				check();
				p2m.add(new Point(i,j));
			}
		}
		
		for(int i = 0; i < 3; i++) {
			System.out.println(Arrays.toString(board[i]));
		}
		System.out.println(p1m);
		System.out.println(p2m);
		scan.close();
	}
	
	public void check() {
		
	}
	
	public class Point{
		int x;
		int y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public String toString() {
			return "("+x+", "+y+")";
		}
 		
	}
	
	public class Line{
		Point p1;
		Point p2;
		public Line(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
		}
		
//		public boolean isOrth(Line l2) {
//			
//		}
	}

}
