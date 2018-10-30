
public class Layer {
	private Perceptron [] perceptrons;
	private double [] output;
	private double [] errors;
	
	/* Constructs a layer of perceptrons
	 * @param inputLen - length that perceptrons should expect their inputs to be
	 * @param outputLen - length of output that the layer generates - aka number of perceptrons in layer
	 */
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
		errors = null;
	}
	
	public int getOutputLength() {
		return output.length;
	}
	
	public double[] getErrors() {
		return errors;
	}
	
	public double[] getOutput() {
		return output;
	}
	
	/* Calculates the output of a layer with a given input
	 * @param input - the input the layer was given
	 * @return the output of the layer
	 */
	public double[] calculateOutput(double [] input) {
		if(perceptrons == null) return output; // This shouldnt happen
		
		for(int i = 0; i < perceptrons.length; i++) {
			output[i] = perceptrons[i].calculateOutput(input);
		}
		return output;
	}
	
	/* Calculates the error made in the output layer
	 * @param c - what the output layer should have output
	 * @return double that is the largest error that this layer made
	 */
	public double calcuateOutputError(int [] c) {
//		System.out.print("Error : ");
		double maxError = Math.abs(c[0] - output[0]);
		for(int i = 0; i<output.length; i++) {
			errors[i] = c[i] - output[i];
//			System.out.print(errors[i]+" ");
			if(Math.abs(errors[i]) > maxError) {
				maxError = Math.abs(errors[i]);
			}
		}
//		System.out.println();
		return maxError;
	}
	
	/* Calculates the error of a hidden layer 
	 * @param plError - the error that layer below this layer had
	 */
	public void calculateHiddenError(double [] plError) {
		for(int i  = 0; i<errors.length; i++) {
			errors[i] = 0;
			for(int j = 0; j<plError.length; j++) {
				errors[i] += output[i] * plError[j];
			}
		}
	}
	
	/* Updates the weights of all the perceptrons in the layer
	 * @pre - requires that the error has already been calculated
	 */
	public void updateWeights() {
		for(int i = 0; i<perceptrons.length; i++) {
			
			perceptrons[i].updateWeights(errors[i]);
		}
	}
	
	public String toString() {
		String s = "";
		if(perceptrons == null) return s;
		for(int i = 0; i<perceptrons.length; i++) {
			s+= "\tperceptron - "+i+"\n"+perceptrons[i] +"\n\n";
		}
		return s;
	}
}
