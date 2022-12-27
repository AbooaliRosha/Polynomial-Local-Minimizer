import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class Pro5_abooalir {

	// global variables
	public static double[][] coefs;
	public static double eps_fixed;
	public static int itr_fixed;
	public static double alpha_fixed;
	public static double x_fixed;
	public static double armijo_alpha_fixed;
	public static double armijo_beta_fixed;
	public static double armijo_tau_fixed;
	public static int armijo_k_fixed;
	public static double armijo_eps_fixed;
	public static int armijo_itr_fixed;
	public static double armijo_sp_fixed;
	public static double gss_max_fixed;
	public static double gss_min_fixed;
	public static double gss_delta_fixed;
	public static double gss_eps_fixed;
	public static int gss_itr_fixed;
	public static double gss_sp_fixed;
	public static ArrayList<Integer> n_list = new ArrayList<Integer>();
	public static ArrayList<Integer> degree_list = new ArrayList<Integer>(); // list that keeps track of the polynomial																			// degree
	public static ArrayList<Double> stepSize_ar = new ArrayList<Double>();
	public static ArrayList<Polynomial> list = new ArrayList<Polynomial>(); // list of type polynomial
	public static Polynomial a;
	public static double stepSize;
	public static int k;

	public static void main(String[] args) throws IOException {

		Scanner userInput = new Scanner(System.in); // Initializing scanner
		// string version of param values
		String eps_1 = " ";
		String itr_1 = " ";
		String alpha_1 = " ";
		String x_1 = " ";
		String armijo_alpha_1 = " ";
		String armijo_beta_1 = " ";
		String armijo_tau_1 = " ";
		String armijo_k_1 = " ";
		String armijo_eps_1 = " ";
		String armijo_itr_1 = " ";
		String armijo_sp_1 = " ";
		String gss_max_1 = " ";
		String gss_min_1 = " ";
		String gss_delta_1 = " ";
		String gss_eps_1 = " ";
		String gss_itr_1 = " ";
		String gss_sp_1 = " ";
		boolean set_f = false; // check if polynomial exists
		boolean set_sd = false; // check if parameters exists for fixed SD
		boolean set_ar = false;// check if parameters exists for armijo
		boolean set_gss = false;// check if parameters exists for gss
		boolean set_run = false; // check if "R" has been ran
		boolean cd = true; // cd=condition
		// int/double version of param values
		double new_eps;
		int new_itr;
		double new_alpha;
		double new_x;
		double new_armijo_alpha;
		double new_armijo_beta;
		double new_armijo_tau;
		int new_armijo_k;
		double new_armijo_eps;
		int new_armijo_itr;
		double new_armijo_sp;
		double new_gss_max;
		double new_gss_min;
		double new_gss_delta;
		double new_gss_eps;
		int new_gss_itr;
		double new_gss_sp;
		// set +- infinity bounds for int and double
		double inf = Double.POSITIVE_INFINITY;
		double neg_inf = Double.NEGATIVE_INFINITY;
		Integer inf_int = Integer.MAX_VALUE;
		// other variables
		int n = 0;
		String line = null;
		String result[];
		int pre = 0; // previous degree
		boolean cnd = false; // condition which the lengths are not equal
		int count_s = 1; // count the number of polynomials
		boolean r = true; // checks if the length of each polynomials is equal
		ArrayList<Integer> invalid_list = new ArrayList<Integer>(); // list that has the number of the polynomial with invalid length
		ArrayList<Double> list_cooefs = new ArrayList<Double>(); // 1D list of coefs as arraylist
		ArrayList<Integer> f_l = new ArrayList<Integer>(); // list of the polynomials with unequal length
		ArrayList<Integer> lengthx = new ArrayList<Integer>(); // list of the length of each polynomial in each line
		double norm_sd = 0;
		double norm_ar = 0;
		double norm_gss = 0;
		double itr_sd = 0;
		double itr_ar = 0;
		double itr_gss = 0;
		double c_sd = 0;
		double c_ar = 0;
		double c_gss = 0;
		String input;
		
		displayMenu();
		input = userInput.nextLine();
		// checks if the input is not empty
		while (input.isEmpty()) {
			System.out.println();
			System.out.println("ERROR: Invalid menu choice!");
			System.out.println();
			displayMenu();
			input = userInput.nextLine();
		}
		String new_input = input.toLowerCase(); // converts capital letters to small case letters

		while (!new_input.equals("q")) {
			// setting the classes Polynomial
			a = new Polynomial(n, pre - 1, coefs);
			SteepestDescent sd = new SteepestDescent(eps_fixed, itr_fixed, x_fixed);
			SteepestDescent sd_ar = new SteepestDescent(armijo_eps_fixed, armijo_itr_fixed, armijo_sp_fixed);
			SteepestDescent sd_gss = new SteepestDescent(gss_eps_fixed, gss_itr_fixed, gss_sp_fixed);
			SteepestDescent sd_1 = new SteepestDescent(); // default params for all three
			// default params for each child class
			SDFixed sd_a = new SDFixed();
			SDArmijo ar_fixed = new SDArmijo();
			SDGSS gss_fixed = new SDGSS();
			// non-default params for each child class
			SDFixed sd_a_1 = new SDFixed(alpha_fixed);
			SDArmijo ar_params = new SDArmijo(armijo_alpha_fixed, armijo_beta_fixed, armijo_tau_fixed, armijo_k_fixed);
			SDGSS gss_params = new SDGSS(gss_max_fixed, gss_min_fixed, gss_delta_fixed);

			if (new_input.equals("l")) {
				int counter = 0;
				System.out.println();
				System.out.print("Enter file name (0 to cancel): ");
				String file = userInput.nextLine();
				if (file.equals("0")) {
					System.out.println();
					System.out.println("File loading process canceled.");
					System.out.println();
				} else {
					File file_ch = new File(file); // creates a new file instance

					if (file_ch.exists() == false) {
						System.out.println();
						System.out.println("ERROR: File not found!");
						System.out.println();

					}

					else {
						BufferedReader fin = new BufferedReader(new FileReader(file_ch)); // reading file

						while ((line = fin.readLine()) != null) {

							if (line.equals("*")) {
								r = verify(lengthx);
								if (r == true) {
									n_list.add(n);
									degree_list.add(pre - 1);

									Double[] coefs_1d = list_cooefs.toArray(new Double[0]);
									coefs = new double[n][pre];

									// convert 1d array to 2d array
									for (int i = 0; i < n; i++) {
										for (int j = 0; j < pre; j++) {
											coefs[i][j] = coefs_1d[i * pre + j];

										}
									}

									list.add(new Polynomial(n, (pre - 1), coefs));
									counter++;
								}

								// reseting every values to avoid overloading
								count_s++;
								cnd = false;
								list_cooefs.clear();
								lengthx.clear();
								n = 0;

							} else {
								result = line.split(",");
								for (int i = 0; i < result.length; i++) {
									double val = Double.parseDouble(result[i]);
									list_cooefs.add(val);
								}
								n++;

								if (n != 1 || cnd == true) {
									if (result.length != pre) {
										f_l.add(count_s);
									}
								}

								pre = length(result.length); // fixed pre
								cnd = true;
								lengthx.add(result.length);
							}

						}

						fin.close();

						// last polynomial
						r = verify(lengthx);
						if (r == true) {
							n_list.add(n);
							degree_list.add(pre - 1);

							Double[] coefs_1d = list_cooefs.toArray(new Double[0]);
							coefs = new double[n][pre];

							// convert 1d array to 2d array
							for (int i = 0; i < n; i++) {
								for (int j = 0; j < pre; j++) {
									coefs[i][j] = coefs_1d[i * pre + j];

								}
							}

							list.add(new Polynomial(n, (pre - 1), coefs));
							counter++;

						}

						// remove the repeated lengths in order to find which polynomials had different
						// lengths
						for (int i : f_l) {
							if (!invalid_list.contains(i)) {
								invalid_list.add(i);
							}
						}

						if (invalid_list.size() != 0) {
							for (int i = 0; i < invalid_list.size(); i++) {
								System.out.println();
								System.out.println(
										"ERROR: Inconsistent dimensions in polynomial " + invalid_list.get(i) + "!");
							}
						}
						System.out.println();
						System.out.println(counter + " polynomials loaded!");
						set_f = true;
						System.out.println();

						// reseting every values to avoid overloading
						cnd = false;
						list_cooefs.clear();
						lengthx.clear();
						n = 0;
						f_l.clear();
						count_s = 1;
						counter = 0;
						invalid_list.clear();

					}

				}

			} else if (new_input.equals("c")) {
				list.clear();
				System.out.println();
				System.out.println("All polynomials cleared.");
				System.out.println();
				set_run = false;

			}

			else if (new_input.equals("f")) {
				printPolynomials(list);
				set_run = false;

			} else if (new_input.equals("s")) {
				// *******************fixed line search******************
				set_run = false;
				System.out.println();
				System.out.println("Set parameters for SD with a fixed line search:");
				while (!eps_1.equals("0") || !itr_1.equals("0") || !alpha_1.equals("0") || !x_1.equals("0")) {
					String eps;
					String itr;
					String alpha;
					String x;
					// fixed step size (alpha)
					System.out.print("Enter fixed step size (0 to cancel): ");
					alpha_1 = userInput.nextLine();

					if (alpha_1.equals("0")) {

						break;
					} else {
						alpha = alpha_1;
						new_alpha = getDouble(alpha, 0, inf);
						cd = true;
						while (new_alpha == -1.90902 || alpha.isEmpty()) // check if the user input is valid for x
						{
							System.out.println("ERROR: Input must be a real number in [0.00, infinity]!");
							System.out.println();
							System.out.print("Enter fixed step size (0 to cancel): ");
							alpha_1 = userInput.nextLine();

							if (alpha_1.equals("0")) {

								cd = false;
								break;
							} else {
								alpha = alpha_1;
								new_alpha = getDouble(alpha, 0, inf_int);
								cd = true;
							}
						}

					}

					if (cd == false) {
						break;
					}

					// Tolerance
					System.out.print("Enter tolerance epsilon (0 to cancel): ");
					eps_1 = userInput.nextLine();
					if (eps_1.equals("0")) {
						break;
					} else {
						eps = eps_1;
						new_eps = getDouble(eps, 0, inf);
						cd = true;
						while (new_eps == -1.90902 || eps.isEmpty()) // check if the user input is valid for x
						{
							System.out.println("ERROR: Input must be a real number in [0.00, infinity]!");
							System.out.println();
							System.out.print("Enter tolerance epsilon (0 to cancel): ");
							eps_1 = userInput.nextLine();

							if (eps_1.equals("0")) {
								cd = false;
								break;
							} else {
								eps = eps_1;
								new_eps = getDouble(eps, 0, inf);
								cd = true;
							}
						}

					}
					if (cd == false) {
						break;
					}

					// Iteration
					System.out.print("Enter maximum number of iterations (0 to cancel): ");
					itr_1 = userInput.nextLine();
					if (itr_1.equals("0")) {
						break;
					} else {
						itr = itr_1;
						new_itr = getInteger(itr, 0, inf_int);
						cd = true;
						while (new_itr == -1 || itr.isEmpty()) // check if the user input is valid for x
						{
							System.out.println("ERROR: Input must be an integer in [0, 10000]!");
							System.out.println();
							System.out.print("Enter maximum number of iterations (0 to cancel): ");
							itr_1 = userInput.nextLine();

							if (itr_1.equals("0")) {
								cd = false;
								break;
							} else {
								itr = itr_1;
								new_itr = getInteger(itr, 0, inf_int);
								cd = true;

							}

						}
					}
					if (cd == false) {
						break;
					}

					// Starting point
					System.out.print("Enter value for starting point (0 to cancel): ");
					x_1 = userInput.nextLine();
					if (x_1.equals("0")) {
						break;
					} else {
						x = x_1;
						new_x = getDouble(x, neg_inf, inf);
						cd = true;
						while (new_x == -1.90902 || x.isEmpty()) // check if the user input is valid for x
						{
							System.out.println("ERROR: Input must be a real number in [-infinity, infinity]!");
							System.out.println();
							System.out.print("Enter value for starting point (0 to cancel): ");
							x_1 = userInput.nextLine();

							if (x_1.equals("0")) {
								cd = false;
								break;
							} else {
								x = x_1;
								new_x = getDouble(x, neg_inf, inf);
								cd = true;
							}
						}

					}
					if (cd == false) {
						break;
					}
					set_sd = true;
					eps_fixed = fix_eps(new_eps);
					x_fixed = fix_x(new_x);
					itr_fixed = fix_itr(new_itr);
					alpha_fixed = fix_alpha(new_alpha);
					sd = new SteepestDescent(eps_fixed, itr_fixed, x_fixed);
					sd_a_1 = new SDFixed(alpha_fixed);
					
					System.out.println();
					System.out.println("Algorithm parameters set!");
					break;
				}
				if (eps_1.equals("0") || itr_1.equals("0") || alpha_1.equals("0") || x_1.equals("0")) {

					System.out.println();
					System.out.println("Process canceled. No changes made to algorithm parameters.");

				}

				System.out.println();

				// **************Armijo line search**************

				set_run = false;
				System.out.println("Set parameters for SD with an Armijo line search:");
				while (!armijo_alpha_1.equals("0") || !armijo_beta_1.equals("0") || !armijo_tau_1.equals("0")
						|| !armijo_k_1.equals("0") || !armijo_eps_1.equals("0") || !armijo_itr_1.equals("0")
						|| !armijo_sp_1.equals("0")) {
					String armijo_alpha;
					String armijo_beta;
					String armijo_tau;
					String armijo_k;
					String armijo_eps;
					String armijo_itr;
					String armijo_sp;

					// step size (alpha)

					System.out.print("Enter Armijo max step size (0 to cancel): ");
					armijo_alpha_1 = userInput.nextLine();

					if (armijo_alpha_1.equals("0")) {

						break;
					} else {
						armijo_alpha = armijo_alpha_1;
						new_armijo_alpha = getDouble(armijo_alpha, 0, inf);
						cd = true;
						while (new_armijo_alpha == -1.90902 || armijo_alpha.isEmpty()) // check if the user input is
																						// valid for x
						{
							System.out.println("ERROR: Input must be a real number in [0.00, infinity]!");
							System.out.println();
							System.out.print("Enter Armijo max step size (0 to cancel): ");
							armijo_alpha_1 = userInput.nextLine();

							if (armijo_alpha_1.equals("0")) {

								cd = false;
								break;
							} else {
								armijo_alpha = armijo_alpha_1;
								new_armijo_alpha = getDouble(armijo_alpha, 0, inf_int);
								cd = true;
							}
						}

					}

					if (cd == false) {
						break;
					}

					// beta
					System.out.print("Enter Armijo beta (0 to cancel): ");
					armijo_beta_1 = userInput.nextLine();
					if (armijo_beta_1.equals("0")) {
						break;
					} else {
						armijo_beta = armijo_beta_1;
						new_armijo_beta = getDouble(armijo_beta, 0, 1.00);
						cd = true;
						while (new_armijo_beta == -1.90902 || armijo_beta.isEmpty()) // check if the user input is valid
																						// for x
						{
							System.out.println("ERROR: Input must be a real number in [0.00, 1.00]!");
							System.out.println();
							System.out.print("Enter Armijo beta (0 to cancel): ");
							armijo_beta_1 = userInput.nextLine();

							if (armijo_beta_1.equals("0")) {
								cd = false;
								break;
							} else {
								armijo_beta = armijo_beta_1;
								new_armijo_beta = getDouble(armijo_beta, 0, 1.00);
								cd = true;
							}
						}

					}
					if (cd == false) {
						break;
					}

					// Tau
					System.out.print("Enter Armijo tau (0 to cancel): ");
					armijo_tau_1 = userInput.nextLine();
					if (armijo_tau_1.equals("0")) {
						break;
					} else {
						armijo_tau = armijo_tau_1;
						new_armijo_tau = getDouble(armijo_tau, 0, 1.00);
						cd = true;
						while (new_armijo_tau == -1.90902 || armijo_tau.isEmpty()) // check if the user input is valid
																					// for x
						{
							System.out.println("ERROR: Input must be a real number in [0.00, 1.00]!");
							System.out.println();
							System.out.print("Enter Armijo tau (0 to cancel): ");
							armijo_tau_1 = userInput.nextLine();

							if (armijo_tau_1.equals("0")) {
								cd = false;
								break;
							} else {
								armijo_tau = armijo_tau_1;
								new_armijo_tau = getDouble(armijo_tau, 0, 1.00);
								cd = true;

							}

						}
					}
					if (cd == false) {
						break;
					}
					// K
					System.out.print("Enter Armijo K (0 to cancel): ");
					armijo_k_1 = userInput.nextLine();
					if (armijo_k_1.equals("0")) {
						break;
					} else {
						armijo_k = armijo_k_1;
						new_armijo_k = getInteger(armijo_k, 0, inf_int);
						cd = true;
						while (new_armijo_k == -1 || armijo_k.isEmpty()) // check if the user input is valid for x
						{
							System.out.println("ERROR: Input must be an integer in [0, infinity]!");
							System.out.println();
							System.out.print("Enter Armijo K (0 to cancel): ");
							armijo_k_1 = userInput.nextLine();

							if (armijo_k_1.equals("0")) {
								cd = false;
								break;
							} else {
								armijo_k = armijo_k_1;
								new_armijo_k = getInteger(armijo_k, 0, inf_int);
								cd = true;

							}

						}
					}
					if (cd == false) {
						break;
					}

					// Tolerance
					System.out.print("Enter tolerance epsilon (0 to cancel): ");
					armijo_eps_1 = userInput.nextLine();
					if (armijo_eps_1.equals("0")) {
						break;
					} else {
						armijo_eps = armijo_eps_1;
						new_armijo_eps = getDouble(armijo_eps, 0, inf);
						cd = true;
						while (new_armijo_eps == -1.90902 || armijo_eps.isEmpty()) // check if the user input is valid
																					// for x
						{
							System.out.println("ERROR: Input must be a real number in [0.00, infinity]!");
							System.out.println();
							System.out.print("Enter tolerance epsilon (0 to cancel): ");
							armijo_eps_1 = userInput.nextLine();

							if (armijo_eps_1.equals("0")) {
								cd = false;
								break;
							} else {
								armijo_eps = armijo_eps_1;
								new_armijo_eps = getDouble(armijo_eps, 0, inf);
								cd = true;
							}
						}

					}
					if (cd == false) {
						break;
					}

					// Iteration
					System.out.print("Enter maximum number of iterations (0 to cancel): ");
					armijo_itr_1 = userInput.nextLine();
					if (armijo_itr_1.equals("0")) {
						break;
					} else {
						armijo_itr = armijo_itr_1;
						new_armijo_itr = getInteger(armijo_itr, 0, inf_int);
						cd = true;
						while (new_armijo_itr == -1 || armijo_itr.isEmpty()) // check if the user input is valid for x
						{
							System.out.println("ERROR: Input must be an integer in [0, 10000]!");
							System.out.println();
							System.out.print("Enter maximum number of iterations (0 to cancel): ");
							armijo_itr_1 = userInput.nextLine();

							if (armijo_itr_1.equals("0")) {
								cd = false;
								break;
							} else {
								armijo_itr = armijo_itr_1;
								new_armijo_itr = getInteger(armijo_itr, 0, inf_int);
								cd = true;

							}

						}
					}
					if (cd == false) {
						break;
					}

					// Starting point
					System.out.print("Enter value for starting point (0 to cancel): ");
					armijo_sp_1 = userInput.nextLine();
					if (armijo_sp_1.equals("0")) {
						break;
					} else {
						armijo_sp = armijo_sp_1;
						new_armijo_sp = getDouble(armijo_sp, neg_inf, inf);
						cd = true;
						while (new_armijo_sp == -1.90902 || armijo_sp.isEmpty()) // check if the user input is valid for
																					// x
						{
							System.out.println("ERROR: Input must be a real number in [-infinity, infinity]!");
							System.out.println();
							System.out.print("Enter value for starting point (0 to cancel): ");
							armijo_sp_1 = userInput.nextLine();

							if (armijo_sp_1.equals("0")) {
								cd = false;
								break;
							} else {
								armijo_sp = armijo_sp_1;
								new_armijo_sp = getDouble(armijo_sp, neg_inf, inf);
								cd = true;
							}
						}

					}
					if (cd == false) {
						break;
					}
					set_ar = true;
					armijo_alpha_fixed = fix_ar_alpha(new_armijo_alpha);
					armijo_beta_fixed = fix_ar_beta(new_armijo_beta);
					armijo_tau_fixed = fix_ar_t(new_armijo_tau);
					armijo_k_fixed = fix_ar_k(new_armijo_k);
					armijo_eps_fixed = fix_ar_eps(new_armijo_eps);
					armijo_itr_fixed = fix_ar_itr(new_armijo_itr);
					armijo_sp_fixed = fix_ar_sp(new_armijo_sp);
					sd_ar = new SteepestDescent(armijo_eps_fixed, armijo_itr_fixed, armijo_sp_fixed);
					ar_params = new SDArmijo(armijo_alpha_fixed, armijo_beta_fixed, armijo_tau_fixed, armijo_k_fixed);
					System.out.println();
					System.out.println("Algorithm parameters set!");
					break;
				}
				if (armijo_alpha_1.equals("0") || armijo_beta_1.equals("0") || armijo_tau_1.equals("0")
						|| armijo_k_1.equals("0") || armijo_eps_1.equals("0") || armijo_itr_1.equals("0")
						|| armijo_sp_1.equals("0")) {

					System.out.println();
					System.out.println("Process canceled. No changes made to algorithm parameters.");

				}

				System.out.println();

				// ****************golden section line search******************

				set_run = false;
				System.out.println("Set parameters for SD with a golden section line search:");
				while (!gss_max_1.equals("0") || !gss_min_1.equals("0") || !gss_delta_1.equals("0")
						|| !gss_eps_1.equals("0") || !gss_itr_1.equals("0") || !gss_sp_1.equals("0")) {
					String gss_max;
					String gss_min;
					String gss_delta;
					String gss_eps;
					String gss_itr;
					String gss_sp;

					// step size max (alpha)
					System.out.print("Enter GSS maximum step size (0 to cancel): ");
					gss_max_1 = userInput.nextLine();

					if (gss_max_1.equals("0")) {

						break;
					} else {
						gss_max = gss_max_1;
						new_gss_max = getDouble(gss_max, 0, inf);
						cd = true;
						while (new_gss_max == -1.90902 || gss_max.isEmpty()) // check if the user input is valid for x
						{
							System.out.println("ERROR: Input must be a real number in [0.00, infinity]!");
							System.out.println();
							System.out.print("Enter GSS maximum step size (0 to cancel): ");
							gss_max_1 = userInput.nextLine();

							if (gss_max_1.equals("0")) {

								cd = false;
								break;
							} else {
								gss_max = gss_max_1;
								new_gss_max = getDouble(gss_max, 0, inf);
								cd = true;
							}
						}

					}

					if (cd == false) {
						break;
					}

					// min step size
					System.out.print("Enter GSS minimum step size (0 to cancel): ");
					gss_min_1 = userInput.nextLine();
					if (gss_min_1.equals("0")) {
						break;
					} else {
						gss_min = gss_min_1;
						new_gss_min = getDouble(gss_min, 0, new_gss_max);
						cd = true;
						while (new_gss_min == -1.90902 || gss_min.isEmpty()) // check if the user input is valid for x
						{
							System.out.print("ERROR: Input must be a real number in [0.00, ");
							System.out.printf("%4.2f", new_gss_max);
							System.out.print("]!");
							System.out.println();
							System.out.println();
							System.out.print("Enter GSS minimum step size (0 to cancel): ");
							gss_min_1 = userInput.nextLine();

							if (gss_min_1.equals("0")) {
								cd = false;
								break;
							} else {
								gss_min = gss_min_1;
								new_gss_min = getDouble(gss_min, 0, new_gss_max);
								cd = true;
							}
						}

					}
					if (cd == false) {
						break;
					}

					// delta
					System.out.print("Enter GSS delta (0 to cancel): ");
					gss_delta_1 = userInput.nextLine();
					if (gss_delta_1.equals("0")) {
						break;
					} else {
						gss_delta = gss_delta_1;
						new_gss_delta = getDouble(gss_delta, 0, inf);
						cd = true;
						while (new_gss_delta == -1.90902 || gss_delta.isEmpty()) // check if the user input is valid for
																					// x
						{
							System.out.println("ERROR: Input must be a real number in [0.00, infinity]!");
							System.out.println();
							System.out.print("Enter GSS delta (0 to cancel): ");
							gss_delta_1 = userInput.nextLine();

							if (gss_delta_1.equals("0")) {
								cd = false;
								break;
							} else {
								gss_delta = gss_delta_1;
								new_gss_delta = getDouble(gss_delta, 0, inf);
								cd = true;

							}

						}
					}
					if (cd == false) {
						break;
					}

					// Tolerance
					System.out.print("Enter tolerance epsilon (0 to cancel): ");
					gss_eps_1 = userInput.nextLine();
					if (gss_eps_1.equals("0")) {
						break;
					} else {
						gss_eps = gss_eps_1;
						new_gss_eps = getDouble(gss_eps, 0, inf);
						cd = true;
						while (new_gss_eps == -1.90902 || gss_eps.isEmpty()) // check if the user input is valid for x
						{
							System.out.println("ERROR: Input must be a real number in [0.00, infinity]!");
							System.out.println();
							System.out.print("Enter tolerance epsilon (0 to cancel): ");
							gss_eps_1 = userInput.nextLine();

							if (gss_eps_1.equals("0")) {
								cd = false;
								break;
							} else {
								gss_eps = gss_eps_1;
								new_gss_eps = getDouble(gss_eps, 0, inf);
								cd = true;
							}
						}

					}
					if (cd == false) {
						break;
					}

					// Iteration
					System.out.print("Enter maximum number of iterations (0 to cancel): ");
					gss_itr_1 = userInput.nextLine();
					if (gss_itr_1.equals("0")) {
						break;
					} else {
						gss_itr = gss_itr_1;
						new_gss_itr = getInteger(gss_itr, 0, inf_int);
						cd = true;
						while (new_gss_itr == -1 || gss_itr.isEmpty()) // check if the user input is valid for x
						{
							System.out.println("ERROR: Input must be an integer in [0, 10000]!");
							System.out.println();
							System.out.print("Enter maximum number of iterations (0 to cancel): ");
							gss_itr_1 = userInput.nextLine();

							if (gss_itr_1.equals("0")) {
								cd = false;
								break;
							} else {
								gss_itr = gss_itr_1;
								new_gss_itr = getInteger(gss_itr, 0, inf_int);
								cd = true;

							}

						}
					}
					if (cd == false) {
						break;
					}

					// Starting point
					System.out.print("Enter value for starting point (0 to cancel): ");
					gss_sp_1 = userInput.nextLine();
					if (gss_sp_1.equals("0")) {
						break;
					} else {
						gss_sp = gss_sp_1;
						new_gss_sp = getDouble(gss_sp, neg_inf, inf);
						cd = true;
						while (new_gss_sp == -1.90902 || gss_sp.isEmpty()) // check if the user input is valid for x
						{
							System.out.println("ERROR: Input must be a real number in [-infinity, infinity]!");
							System.out.println();
							System.out.print("Enter value for starting point (0 to cancel): ");
							gss_sp_1 = userInput.nextLine();

							if (gss_sp_1.equals("0")) {
								cd = false;
								break;
							} else {
								gss_sp = gss_sp_1;
								new_gss_sp = getDouble(gss_sp, neg_inf, inf);
								cd = true;
							}
						}

					}
					if (cd == false) {
						break;
					}
					set_gss = true;
					gss_max_fixed = fix_ar_alpha(new_gss_max);
					gss_min_fixed = fix_ar_beta(new_gss_min);
					gss_delta_fixed = fix_ar_t(new_gss_delta);
					gss_eps_fixed = fix_ar_eps(new_gss_eps);
					gss_itr_fixed = fix_ar_itr(new_gss_itr);
					gss_sp_fixed = fix_ar_sp(new_gss_sp);
					sd_gss = new SteepestDescent(gss_eps_fixed, gss_itr_fixed, gss_sp_fixed);
					gss_params = new SDGSS(gss_max_fixed, gss_min_fixed, gss_delta_fixed);
					System.out.println();
					System.out.println("Algorithm parameters set!");
					break;
				}
				if (gss_max_1.equals("0") || gss_min_1.equals("0") || gss_delta_1.equals("0") || gss_eps_1.equals("0")
						|| gss_itr_1.equals("0") || gss_sp_1.equals("0")) {

					System.out.println();
					System.out.println("Process canceled. No changes made to algorithm parameters.");

				}

				System.out.println();

			} else if (new_input.equals("p")) {

				
				// fixed sd
				if (set_sd == true) {
					System.out.println();
					System.out.println("SD with a fixed line search:");
					sd.print();
					sd_a_1.print();
					set_run = false;
				} else {
					// Default params
					System.out.println();
					System.out.println("SD with a fixed line search:");
					sd_1.print();
					sd_a.print();
					set_run = false;
				}
				// armijo
				if (set_ar == true) {
					System.out.println();
					System.out.println("SD with an Armijo line search:");
					sd_ar.print();
					ar_params.print();
					set_run = false;
				} else {
					// Default params
					System.out.println();
					System.out.println("SD with an Armijo line search:");
					sd_1.print();
					ar_fixed.print();
					set_run = false;
				}
				// gss
				if (set_gss == true) {
					System.out.println();
					System.out.println("SD with a golden section line search:");
					sd_gss.print();
					gss_params.print();
					System.out.println();
					set_run = false;
				} else {
					// Default params
					System.out.println();
					System.out.println("SD with a golden section line search:");
					sd_1.print();
					gss_fixed.print();
					System.out.println();
					set_run = false;
				}
			} else if (new_input.equals("r")) {

				if (list.size() == 0) {
					System.out.println();
					System.out.println("ERROR: No polynomial functions are loaded!");
					System.out.println();
					set_run = false;

				} else {
					// fixed sd
					if (set_sd == false) {
						// default
						set_run = true;
						sd_1.init(list);
						stepSize = sd_a.lineSearch(a, null);
						System.out.println();
						System.out.println("Running SD with a fixed line search:");
						sd_1.run(list.size(), a);

					} else {
						set_run = true;
						sd.init(list);
						stepSize = sd_a_1.lineSearch(a, null);
						System.out.println();
						System.out.println("Running SD with a fixed line search:");
						sd.run(list.size(), a);

					}
					// armijo
					if (set_ar == false) // default
					{
						set_run = true;
						ar_fixed.init(list);
						System.out.println("Running SD with an Armijo line search:");
						ar_fixed.run(list.size(), a);

					} else {
						set_run = true;
						ar_params.set(armijo_eps_fixed, armijo_itr_fixed, armijo_sp_fixed);
						ar_params.init(list);
						System.out.println("Running SD with an Armijo line search:");
						ar_params.run(list.size(), a);

					}
					// gss
					if (set_gss == false) {
						// default
						set_run = true;
						gss_fixed.init(list);
						System.out.println("Running SD with a golden section line search:");
						gss_fixed.run(list.size(), a);
						System.out.println("All polynomials done.");
						System.out.println();

					} else {
						set_run = true;
						gss_params.set(gss_eps_fixed, gss_sp_fixed, gss_itr_fixed);
						gss_params.init(list);
						System.out.println("Running SD with a golden section line search:");
						gss_params.run(list.size(), a);
						System.out.println("All polynomials done.");
						System.out.println();

					}
				}

			} else if (new_input.equals("d")) {

				if (list.size() == 0 || set_run == false) {
					System.out.println();
					System.out.println("ERROR: Results do not exist for all line searches!");
					System.out.println();
				} else {
					if (set_sd == false) // default params
					{
						System.out.println();
						System.out.println("Detailed results for SD with a fixed line search:");
						sd_1.init(list);
						sd_1.run_ar_c(list.size(), a);
						sd_1.printAll();
						System.out.println("Statistical summary for SD with a fixed line search:");
						sd_1.printStats();
						System.out.println();
						norm_sd = sd_1.returnAve();
						itr_sd = sd_1.returnItr();
						c_sd = sd_1.returnC();

					} else // entered params
					{

						System.out.println();
						System.out.println("Detailed results for SD with a fixed line search:");
						sd.init(list);
						sd.run_ar_c(list.size(), a);
						sd.printAll();
						System.out.println("Statistical summary for SD with a fixed line search:");
						sd.printStats();
						System.out.println();
						norm_sd = sd.returnAve();
						itr_sd = sd.returnItr();
						c_sd = sd.returnC();
					}

					if (set_ar == false) // default params
					{
						System.out.println("Detailed results for SD with an Armijo line search:");
						ar_fixed.init(list);
						ar_fixed.run_ar_c(list.size(), a);
						ar_fixed.printAll();
						System.out.println("Statistical summary for SD with an Armijo line search:");
						ar_fixed.printStats();
						System.out.println();
						norm_ar = ar_fixed.returnAve();
						itr_ar = ar_fixed.returnItr();
						c_ar = ar_fixed.returnC();

					} else // entered params
					{
						System.out.println("Detailed results for SD with an Armijo line search:");
						ar_params.set(armijo_eps_fixed, armijo_itr_fixed, armijo_sp_fixed);
						ar_params.init(list);
						ar_params.run_ar_c(list.size(), a);
						ar_params.printAll();
						System.out.println("Statistical summary for SD with an Armijo line search:");
						ar_params.printStats();
						System.out.println();
						norm_ar = ar_params.returnAve();
						itr_ar = ar_params.returnItr();
						c_ar = ar_params.returnC();

					}
					if (set_gss == false) {
						System.out.println("Detailed results for SD with a golden section line search:");
						gss_fixed.init(list);
						gss_fixed.run_ar_c(list.size(), a);
						gss_fixed.printAll();
						System.out.println("Statistical summary for SD with a golden section line search:");
						gss_fixed.printStats();
						norm_gss = gss_fixed.returnAve();
						itr_gss = gss_fixed.returnItr();
						c_gss = gss_fixed.returnC();
					} else {
						System.out.println("Detailed results for SD with a golden section line search:");
						gss_params.set(gss_eps_fixed, gss_sp_fixed, gss_itr_fixed);
						gss_params.init(list);
						gss_params.run_ar_c(list.size(), a);
						gss_params.printAll();
						System.out.println("Statistical summary for SD with a golden section line search:");
						gss_params.printStats();
						norm_gss = gss_params.returnAve();
						itr_gss = gss_params.returnItr();
						c_gss = gss_params.returnC();
					}
				}

			} else if (new_input.equals("x")) {
				if (list.size() == 0 || set_run == false) {
					System.out.println();
					System.out.println("ERROR: Results do not exist for all line searches!");
					System.out.println();
				} else {
					String winner_norm = " ";
					String winner_itr = " ";
					String winner_c = " ";
					String winner_final = "";
					double norm = norm_gss > (norm_sd > norm_ar ? norm_sd : norm_ar) ? norm_gss
							: ((norm_sd > norm_ar) ? norm_sd : norm_ar); // find the largest
					if (norm == norm_gss) {
						winner_norm = "GSS";
					} else if (norm == norm_ar) {
						winner_norm = "Armijo";
					} else if (norm == norm_sd) {
						winner_norm = "Fixed";
					}

					double itr = itr_gss > (itr_sd > itr_ar ? itr_sd : itr_ar) ? itr_gss
							: ((itr_sd > itr_ar) ? itr_sd : itr_ar); // find the largest
					if (itr == itr_gss) {
						winner_itr = "GSS";
					} else if (itr == itr_ar) {
						winner_itr = "Armijo";
					} else {
						winner_itr = "Fixed";
					}
					double c = c_gss > (c_sd > c_ar ? c_sd : c_ar) ? c_gss : ((c_sd > c_ar) ? c_sd : c_ar); // find the
																											// largest
					if (c == c_gss) {
						winner_c = "GSS";
					} else if (c == c_ar) {
						winner_c = "Armijo";
					} else {
						winner_c = "Fixed";
					}
					if (winner_c.equals("GSS") && winner_itr.equals("GSS") && winner_norm.equals("GSS")) {
						winner_final = "GSS";
					} else if (winner_c.equals("Fixed") && winner_itr.equals("Fixed") && winner_norm.equals("Fixed")) {
						winner_final = "GSS";
					} else if (winner_c.equals("Armijo") && winner_itr.equals("Armijo")
							&& winner_norm.equals("Armijo")) {
						winner_final = "Armijo";
					} else {
						winner_final = "Unclear";
					}
					System.out.println();
					System.out.println("---------------------------------------------------");
					System.out.println("          norm(grad)       # iter    Comp time (ms)");
					System.out.println("---------------------------------------------------");
					System.out.printf("%-6s%14.3f%13.3f%18.3f", "Fixed", norm_sd, itr_sd, c_sd);
					System.out.println();
					System.out.printf("%-6s%14.3f%13.3f%18.3f", "Armijo", norm_ar, itr_ar, c_ar);
					System.out.println();
					System.out.printf("%-6s%14.3f%13.3f%18.3f", "GSS", norm_gss, itr_gss, c_gss);
					System.out.println();
					// prtint contents
					System.out.println("---------------------------------------------------");
					System.out.printf("%-6s%14s%13s%18s", "Winner", winner_norm, winner_itr, winner_c);
					System.out.println();
					System.out.println("---------------------------------------------------");
					System.out.println("Overall winner: " + winner_final);
					System.out.println();
				}

			} else {
				System.out.println();
				System.out.println("ERROR: Invalid menu choice!\n");
			}
			displayMenu();
			input = userInput.nextLine();
			// checks if the input is not empty
			while (input.isEmpty()) {
				System.out.println();
				System.out.println("ERROR: Invalid menu choice!");
				System.out.println();
				displayMenu();
				input = userInput.nextLine();
			}
			new_input = input.toLowerCase();
		}
		System.out.println();
		System.out.println("Arrivederci.");

	}

	public static void displayMenu() {
		System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)\n" + "L - Load polynomials from file\n"
				+ "F - View polynomial functions\n" + "C - Clear polynomial functions\n"
				+ "S - Set steepest descent parameters\n" + "P - View steepest descent parameters\n"
				+ "R - Run steepest descent algorithms\n" + "D - Display algorithm performance\n"
				+ "X - Compare average algorithm performance\n" + "Q - Quit\n");
		System.out.print("Enter choice: ");
	}

	public static void printPolynomials(ArrayList<Polynomial> P) {
		if (P.size() == 0) {
			System.out.println();
			System.out.println("ERROR: No polynomial functions are loaded!");
			System.out.println();
		} else {
			// pritng Polynomial list
			System.out.println();
			System.out.println("---------------------------------------------------------");
			System.out.println("Poly No.  Degree   # vars   Function");
			System.out.println("---------------------------------------------------------");

			for (int i = 0; i < P.size(); i++) {
				System.out.print(
						"       " + (i + 1) + "       " + degree_list.get(i) + "        " + n_list.get(i) + "   ");
				a = P.get(i);
				a.print();
			}
			System.out.println();
		}
	}

	public static int getInteger(String prompt, int LB, int UB) {
		
		int newInt = 0; // the integer that will be returned

		try {
			Integer.parseInt(prompt); // converting the prompt to int

		} catch (NumberFormatException e) {
			return -1; // return false if the input is a string
		}

		catch (Exception e) {
			return -1; // return false if the input is a double

		}

		newInt = Integer.parseInt(prompt); // if the prompt is an int number, convert it to int

		if (Integer.parseInt(prompt) < LB || Integer.parseInt(prompt) > UB) {
			return -1; // return false if the number is not in the boundary

		} else {
			return newInt; // Return the valid number
		}

	}

	public static double getDouble(String prompt, double LB, double UB) {


		double newDouble = 0; // the double that will be returned

		try {
			Double.parseDouble(prompt); // converting the prompt to int

		} catch (NumberFormatException e) {
			return -1.90902; // return false if the input is a string
		}

		newDouble = Double.parseDouble(prompt); // if the prompt is a double number, convert it to double

		if (Double.parseDouble(prompt) < LB || Double.parseDouble(prompt) > UB) {
			return -1.90902; // return false if the number is not in the boundary

		} else {
			return newDouble; // Return the valid number
		}

	}

	public static int length(int a) // keeps track of the lengths of each line of the file
	{
		int pre = a;
		return pre;
	}

	public static boolean verify(ArrayList<Integer> list) // checks if the length of the polynomials match
	{
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != list.get(0)) {
				return false;
			}
		}
		return true; // equal
	}

	// avoid losing algo params data
	public static double fix_eps(double a) {
		double e = a;
		return e;
	}

	public static int fix_itr(int a) {
		int e = a;
		return e;
	}

	public static double fix_alpha(double a) {
		double e = a;
		return e;

	}

	public static double fix_x(double a) {
		double e = a;
		return e;
	}

	// armijo
	public static double fix_ar_eps(double a) {
		double e = a;
		return e;
	}

	public static double fix_ar_beta(double a) {
		double e = a;
		return e;
	}

	public static double fix_ar_t(double a) {
		double e = a;
		return e;
	}

	public static double fix_ar_alpha(double a) {
		double e = a;
		return e;
	}

	public static double fix_ar_sp(double a) {
		double e = a;
		return e;
	}

	public static int fix_ar_itr(int a) {
		int e = a;
		return e;
	}

	public static int fix_ar_k(int a) {
		int e = a;
		return e;
	}

	// gss
	public static double fix_gss_max(double a) {
		double e = a;
		return e;
	}

	public static double fix_gss_min(double a) {
		double e = a;
		return e;
	}

	public static double fix_gss_delta(double a) {
		double e = a;
		return e;
	}

	public static double fix_gss_eps(double a) {
		double e = a;
		return e;
	}

	public static double fix_gss_sp(double a) {
		double e = a;
		return e;
	}

	public static int fix_gss_itr(int a) {
		int e = a;
		return e;
	}

}
