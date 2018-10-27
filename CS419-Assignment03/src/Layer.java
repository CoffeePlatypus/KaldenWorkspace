
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
	
	public double[] getErrors() {
		return errors;
	}
	
	public double[] getOutput() {
		return output;
	}
	
	public double[] calculateOutput(double [] input) {
		if(perceptrons == null) return output;
		
		for(int i = 0; i < perceptrons.length; i++) {
			output[i] = perceptrons[i].calculateOutput(input);
		}
		return output;
	}
	
	public double calcuateOutputError(double [] c) {
		System.out.print("Error : ");
		double maxError = Math.abs(c[0] - output[0]);
		for(int i = 0; i<output.length; i++) {
			errors[i] = c[i] - output[i];
			System.out.print(errors[i]+" ");
			if(Math.abs(errors[i]) > maxError) {
				maxError = Math.abs(errors[i]);
			}
		}
		System.out.println();
		return maxError;
	}
	
	public double calculateHiddenError(double [] plError) {
		double maxError = 0;
		for(int j = 0; j<plError.length; j++) {
			errors[0] += output[0] * plError[j];
		}
		maxError = errors[0];
		for(int i  = 1; i<errors.length; i++) {
			errors[i] = 0;
			for(int j = 0; j<plError.length; j++) {
				errors[i] += output[i] * plError[j];
			}
			if(errors[i] > maxError) {
				maxError = errors[i];
			}
		}
		return maxError;
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
			s+= "\tperceptron - "+i+"\n"+perceptrons[i] +"\n\n";
		}
		return s;
	}
}
