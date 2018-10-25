
public class Perceptron {
	double [] weights;
	double [] input;
	double output;
	double error;
	
	public Perceptron(int inputLen) {
		weights = new double[inputLen+1];
		setRandomWeights();
		output = -1;
		error = 0;
	}
	
	private void setRandomWeights() {
		for(int i = 0; i < weights.length; i++) {
			weights[i] = 1;//(double) Math.random();
		}
	}
	
	public double getOutput(double [] in) {
		input = in;
		if(in.length + 1 != weights.length) {
			System.out.print("input and weights dim mismatch");
		}
		
		double out = weights[0];
		for(int i = 1; i < weights.length && i<=in.length; i++) {
			out += weights[i] * in[i-1];
			
		}
//		System.out.println("\n"+out);
		out = 1 / (1+Math.pow(Math.E, -out));
		
		output = out;
		return out;
	}
	
	public void updateWeights(double error) {
		weights[0] += error * output*(1-output);
		for(int i = 1; i<weights.length; i++) {
			weights[i] +=  error * output * (1 - output)*(input[i-1]);
		}
	}
	
	public String toString() {
		String s = "weights: " ;
		for(int i = 0; i<weights.length; i++) {
			s+= weights[i]+" ";
		}
		return s+"\noutput: "+output;
	}
}
