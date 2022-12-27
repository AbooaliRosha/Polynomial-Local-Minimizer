
public class SDFixed extends SteepestDescent {
	private double alpha; // fixed step size

	// constructors
	public SDFixed() {
		// super();
		this.alpha = 0.01;
	}

	public SDFixed(double alpha) {

		this.alpha = alpha;
	}

	// getters
	public double getAlpha() {
		return this.alpha;
	}

	// setters
	public void setAlpha(double a) {
		this.alpha = a;
	}

	// other methods
	@Override
	public double lineSearch(Polynomial P, double[] x) // fixed step size
	{
		return this.alpha;
	}

	public void print() // print parameters
	{

		System.out.println("Fixed step size (alpha): " + this.alpha);
	}

}
