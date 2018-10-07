
public class KDBuilder {
	private int dimension;
	
	public KDBuilder(int d) {
		dimension = d;
	}

	public static void main(String[] args) {
		double [] c = {2.33, 3.45};
		Point p = new Point(2,c);
		
		System.out.println(p);

	}

}
