import java.util.ArrayList;

public class SteepestDescent {
	private double eps; // tolerance
	private int maxIter; // maximum number of iterations
	private ArrayList<double[]> bestPoint = new ArrayList<double[]>(); // best point found for all polynomials
	private double x0; // starting point
	private double[] bestPoint_a; // best point found
	private double[] bestObjVal; // best obj fn value found
	private double[] bestGradNorm; // best gradient norm found
	private long[] compTime; // computation time needed
	private int[] nIter; // no. of iterations needed
	private boolean resultsExist; // whether or not results exist
	private double f_sum = 0;
	private double norm = 0;
	private long elapsedTime = 0;
	private long start = 0;
	private long end = 0;
	private ArrayList<Double> gNorm = new ArrayList<Double>();
	private ArrayList<Integer> n_itr = new ArrayList<Integer>();
	private ArrayList<Double> fSum = new ArrayList<Double>();
	private ArrayList<Integer> c_time = new ArrayList<Integer>();
	private ArrayList<Polynomial> copy_p = new ArrayList<Polynomial>();
	ArrayList<Integer> keep = new ArrayList<Integer>();
	Pro5_abooalir q = new Pro5_abooalir();
	Stats s = new Stats();

	// constructors
	public SteepestDescent() {
		eps = 0.001;
		maxIter = 100;
		x0 = 1.0;

	}

	public SteepestDescent(double eps, int maxIter, double x0) {
		this.eps = eps;
		this.maxIter = maxIter;
		this.x0 = x0;
	}

	// getters
	public double getEps() {
		return eps;
	}

	public int getMaxIter() {
		return maxIter;
	}

	
	public double getX0() {
		return x0;
	}

	public double[] getBestObjVal() {
		return bestObjVal;
	}

	public double[] getBestGradNorm() {
		return bestGradNorm;
	}

	public double[] getBestPoint(int i) {
		return bestPoint.get(i);
	}

	public double[] getBestPoint_a() {
		return bestPoint_a;
	}

	public int[] getNIter() {
		return nIter;
	}

	public long[] getCompTime() {
		return compTime;
	}

	public boolean hasResults() {
		return resultsExist;
	}

	public double getFSum() {
		return f_sum;

	}

	public double getNorm() {
		return norm;

	}

	public long getElapsedTime() {
		return elapsedTime;

	}

	public long getStart() {
		return start;

	}

	public long getEnd() {
		return end;

	}

	public ArrayList<Double> getGNorm() {
		return gNorm;
	}

	public ArrayList<Integer> getN_itr() {
		return n_itr;
	}

	public ArrayList<Double> get_FSum() {
		return fSum;
	}

	public ArrayList<Integer> getC_time() {
		return c_time;
	}

	public ArrayList<Polynomial> getCopy() {
		return copy_p;
	}

	// setters
	public void setEps(double a) {
		this.eps = a;
	}

	public void setMaxIter(int a) {
		this.maxIter = a;
	}

	public void setX0(double a) {
		this.x0 = a;
	}

	public void setBestObjVal(int i, double a) {
		this.bestObjVal[i] = a;
	}

	public void setBestGradNorm(int i, double a) {
		this.bestGradNorm[i] = a;
	}

	public void setBestPoint(int i, double[] a) {
		for (i = 0; i < a.length; i++) {
			this.bestPoint.set(i, a);
		}

	}

	public void setBestPoint_a(int i, double a) {
		this.bestPoint_a[i] = a;
	}

	public void setCompTime(int i, long a) {
		this.compTime[i] = a;
	}

	public void setNIter(int i, int a) {
		this.nIter[i] = a;
	}

	public void setHasResults(boolean a) {
		this.resultsExist = a;

	}

	public void setFSum(double a) {
		this.f_sum = a;

	}

	public void setNorm(double a) {
		this.norm = a;

	}

	public void setElapsedTime(long a) {
		this.elapsedTime = a;

	}

	public void setStart(long a) {
		this.start = a;

	}

	public void setEnd(long a) {
		this.end = a;

	}

	public void setGNorm(ArrayList<Double> a) {
		this.gNorm = a;
	}

	public void setN_itr(ArrayList<Integer> a) {
		this.n_itr = a;
	}

	public void set_FSum(ArrayList<Double> a) {
		this.fSum = a;
	}

	public void setC_time(ArrayList<Integer> a) {
		this.c_time = a;
	}

	public void setCopy(ArrayList<Polynomial> a) {
		this.copy_p = a;
	}

	// other methods
	public void init(ArrayList<Polynomial> P) // initialize member arrays to correct size
	{
		for (int i = 0; i < P.size(); i++) {
			copy_p.add(P.get(i));
		}
	}

	public double lineSearch(Polynomial P, double[] x) // find the next step size
	{
		return q.stepSize;

	}

	public void print() // print algorithm parameters
	{
		System.out.println("Tolerance (epsilon): " + this.eps);
		System.out.println("Maximum iterations: " + this.maxIter);
		System.out.println("Starting point (x0): " + this.x0);

	}

	public void printStats() {
		
		System.out.println("---------------------------------------------------");
		System.out.println("          norm(grad)       # iter    Comp time (ms)");
		System.out.println("---------------------------------------------------");
		System.out.print("Average");
		System.out.printf("%13.3f%13.3f%18.3f", s.doubleAve(gNorm), s.intAve(n_itr), s.intAve(c_time));
		System.out.println();
		System.out.print("St Dev");
		System.out.printf("%14.3f%13.3f%18.3f", s.doubleSD(gNorm), s.intSD(n_itr), s.intSD(c_time));
		System.out.println();
		System.out.print("Min");
		System.out.printf("%17.3f%13d%18d", s.doubleMin(gNorm), s.intMin(n_itr), s.intMin(c_time));
		System.out.println();
		System.out.print("Max");
		System.out.printf("%17.3f%13d%18d", s.doubleMax(gNorm), s.intMax(n_itr), s.intMax(c_time));
		System.out.println();
		System.out.println();

	}

	public void printAll() {

		// System.out.println("Detailed results for SD with a fixed line search:");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
		System.out.println("-------------------------------------------------------------------------");
		for (int i = 0; i < fSum.size(); i++) {
			int num = 1 + i;
			System.out.format("%8d%13.6f%13.6f%9d%17d", num, fSum.get(i), gNorm.get(i), n_itr.get(i), c_time.get(i));
			for (int j = 0; j < bestPoint.get(i).length; j++) {
				double bp = bestPoint.get(i)[j];
				if (j == bestPoint.get(i).length - 1) // last element
				{

					System.out.format("%4.4f", bp);

				} else if (j == 0) // first element
				{
					System.out.print("   ");
					System.out.format("%-2.4f", bp);
					System.out.print(", ");
				} else {

					System.out.format("%-4.4f", bp);
					System.out.print(", ");
				}

			}
			System.out.println();
		}
		System.out.println();

	}

	public double returnAve() {
		return s.doubleAve(gNorm);
	}

	public double returnItr() {
		return s.intAve(n_itr);
	}

	public double returnC() {
		return s.intAve(c_time);
	}

	public void printSingleResult(int i, boolean rowOnly) {
		int num = i + 1;
		System.out.println();
		System.out.println("Detailed results for SD with a fixed line search:");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point    ");
		System.out.println("-------------------------------------------------------------------------");
		System.out.printf("%8d%13.6f%13.6f%9d%17d", num, fSum.get(i), gNorm.get(i), n_itr.get(i), c_time.get(i));
		for (int j = 0; j < bestPoint.get(i).length; j++) {
			double bp = bestPoint.get(i)[j];
			if (j == bestPoint.get(i).length - 1) // last element
			{
				System.out.format("%4.4f", bp);

			} else if (j == 0) // first element
			{
				System.out.format("%9.4f", bp);
				System.out.print(", ");
			} else {

				System.out.format("%4.4f", bp);
				System.out.print(", ");
			}

		}
		System.out.println();
	}

	public void run(int i, Polynomial P) // run the steepest descent algorithm
	{

		int counter = 0;
		int size = i;

		// main loop
		for (int k = 0; k < size; k++) {

			P = copy_p.get(k);
			// setting the best point to appropriate size

			bestPoint_a = new double[P.getN()];

			for (int s = 0; s < P.getN(); s++) {
				this.bestPoint_a[s] = x0; // Initializing best point with starting point

			}

			double[] g = new double[this.bestPoint_a.length];

			double a = lineSearch(P, this.bestPoint_a); // gets new alpha

			for (int s = 0; s < this.maxIter + 1; s++) // maxIter +1
			{

				start = System.currentTimeMillis();
				f_sum = P.f(bestPoint_a); // fx
				g = P.gradient(bestPoint_a); // gradient
				norm = P.gradientNorm(g); // norm
				Double n = (Double) norm;
				
				if (norm <= this.eps || n.isNaN() == true) {
					break;
				}

				if (s == maxIter) {
					counter = maxIter;

					this.bestPoint_a = this.bestPoint_a; // best points- new points
				} else {
					
					counter++;
					this.bestPoint_a = direction(P, this.bestPoint_a, a); // best points- new points
					
					// armijo coverage error 
					if (a == -1.507) {
						System.out.println("   Armijo line search did not converge!");
						for (int v = 0; v < P.getN(); v++) // q.n_list.get(k)
						{
							this.bestPoint_a[v] = x0; // Initializing best point with starting point

						}
						
						//recalculating everything with starting point
						f_sum = P.f(bestPoint_a); // fx
						g = P.gradient(bestPoint_a); // gradient
						norm = P.gradientNorm(g);
                        counter = 1;
						break;
					}	

				}

			}

			end = System.currentTimeMillis();
			elapsedTime = end - start;
			// storing values for each polynomial
			gNorm.add(norm);
			n_itr.add(counter);
			counter = 0;
			fSum.add(f_sum);
			int el = (int) elapsedTime;
			c_time.add(el);
			bestPoint.add(bestPoint_a);
			System.out.println("Polynomial " + (k + 1) + " done in " + c_time.get(k) + "ms.");

		}
		System.out.println();

	}

	public double[] direction(Polynomial P, double[] x, double a) // find the next direction
	{
		double[] g = new double[x.length];
		double[] d = new double[x.length];
		g = P.gradient(x);

		for (int i = 0; i < x.length; i++) {

			d[i] = x[i] + a * g[i] * -1;

		}
		return d; // new points
	}

	// copy run armijo
	public void run_ar_c(int i, Polynomial P) // run the steepest descent algorithm
	{

		int counter = 0;
		int size = i;

		// main loop
		for (int k = 0; k < size; k++) {

			P = copy_p.get(k);

			// setting the best point to appropriate size
			bestPoint_a = new double[P.getN()]; // q.n_list.get(k)

			for (int s = 0; s < P.getN(); s++) // q.n_list.get(k)
			{
				this.bestPoint_a[s] = x0; // Initializing best point with starting point

			}

			double[] g = new double[this.bestPoint_a.length];

			double a = lineSearch(P, this.bestPoint_a); // gets new alpha

			for (int s = 0; s < this.maxIter + 1; s++) // maxIter +1
			{

				
				start = System.currentTimeMillis();
				f_sum = P.f(bestPoint_a); // fx
				g = P.gradient(bestPoint_a); // gradient
				norm = P.gradientNorm(g); // norm
				Double n = (Double) norm;

				if (norm <= this.eps || n.isNaN() == true) {
					break;
				}

				if (s == maxIter) {
					counter = maxIter;

					this.bestPoint_a = this.bestPoint_a; // best points- new points
				} else {
					counter++;

					if (a == -1.507) {
						for (int v = 0; v < P.getN(); v++) 
						{
							this.bestPoint_a[v] = x0; // Initializing best point with starting point

						}
						f_sum = P.f(bestPoint_a); // fx
						g = P.gradient(bestPoint_a); // gradient
						norm = P.gradientNorm(g);
						break;
					}
					
					this.bestPoint_a = direction(P, this.bestPoint_a, a); // best points- new points

				}

			}

			end = System.currentTimeMillis();
			elapsedTime = end - start;
			// storing values for each polynomial
			gNorm.add(norm);
			n_itr.add(counter);
			counter = 0;
			fSum.add(f_sum);
			int el = (int) elapsedTime;
			c_time.add(el);
			bestPoint.add(bestPoint_a);
			

		} 

	}
}
