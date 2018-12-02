
public class Perceptron {
	double [] weights;
	
	/* Constructs a perceptron object
	 * @param inputLen - int indicating the length of input that the perceptron will take 
	 */
	public Perceptron(int inputLen) {
		weights = new double[inputLen+1];
		setRandomWeights();
	}
	
	public Perceptron(double [] w) {
		weights = w;
	}
	
	/* Initializes the weights to a random number in [0,1]
	 */
	private void setRandomWeights() {
		for(int i = 0; i < weights.length; i++) {
			weights[i] =  Math.random();
		}
	}
	
	/* Calculates the output of a perceptron for a given input
	 * @param in - an array of doubles input to use to calculate the output of a perceptron
	 * @return double that is the output of the perceptron 
	 */
	public double calculateOutput(double [] in) {
		if(in.length + 1 != weights.length) {
			System.out.print("input and weights dim mismatch");
		}
		double out = weights[0];
		for(int i = 1; i < weights.length && i<=in.length; i++) {
			out += weights[i] * in[i-1];
		}
//		System.out.println("\n"+out);
		out = 1 / (1+Math.pow(Math.E, -out));
		return out;
	}
	
	/* Updates weights based on the error in output 
	 * @param e - double indicating the amout of error that the out put had
	 */
//	public void updateWeights(double e) {
//		error = e;
//		weights[0] += error * output*(1-output);
//		for(int i = 1; i<weights.length; i++) {
//			weights[i] +=  error * output * (1 - output)*(input[i-1]);
//		}
//	}
	
	public void deltaUpdateWeights(double delta, double [] input) {
//		System.out.println("Delta: "+delta);
		weights[0] += delta;
		for(int i = 1; i<weights.length; i++) {
			weights[i] += delta * input[i-1];
		}
	}
	
	public double findDelta(double [] deltaj, double output) {
		double del = weights[0];
		for(int i = 1; i<weights.length; i++) {
			del += weights[i] * deltaj[i-1]; 
		}
		return output * (1-output) * del;
	}
	
	public String toString() {
		String s = "\tweights: " ;
		for(int i = 0; i<weights.length; i++) {
			s+= weights[i]+" ";
		}
		return s;
	}
	
	/* Returns a string for writing the perceptron to a file
	 */
	public String write() {
		String s ="";
		for(int i = 0; i<weights.length; i++) {
			s+= weights[i] +" ";
		}
		return s;
	}
}
