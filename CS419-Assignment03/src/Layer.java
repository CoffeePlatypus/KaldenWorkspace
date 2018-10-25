
public class Layer {
	private Perceptron [] perceptrons;
	private double [] output;
	private double [] errors;
	
	public Layer(int inputLen, int outputLen) {
		perceptrons = new Perceptron[outputLen];
		for(int i = 0; i<outputLen; i++) {
			perceptrons[i] = new Perceptron(inputLen);
		}
		output = new double[outputLen];
		errors = new double[outputLen];
	}
	
	/* Constructor for first input layer?
	 */
	public Layer (double [] o) { 
		output = o;
		perceptrons = null;
	}
	
	public int getOutputLength() {
		return output.length;
	}
	
	public double[] getOutput() {
		return output;
	}
	
	public double[] getOutput(double [] input) {
		if(perceptrons == null) return output;
		
		for(int i = 0; i < perceptrons.length; i++) {
			output[i] = perceptrons[i].getOutput(input);
		}
		return output;
	}
	
	public double [] calcuateError(double [] c) {
		for(int i = 0; i<output.length; i++) {
			System.out.println(i);
			errors[i] = c[i] - output[i];
		}
		return errors;
	}
	
	public void updateWeights() {
		for(int i = 0; i<perceptrons.length; i++) {
			perceptrons[i].updateWeights(errors[i]);
		}
	}
	
	public String toString() {
		String s = "";
		if(perceptrons == null) return s;
		for(int i = 0; i<perceptrons.length; i++) {
			s+= perceptrons[i] +"\n\n";
		}
		return s;
	}
}
