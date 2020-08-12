//package ass2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class solution1
{
	
//	public static double[] x = {-100, -10, -3, 0, 1, 2, 3, 4, 10, 100};
//	public static double[] y = {9901, 91, 7, 1, 3, 7, 13, 21, 111, 10101};
	public static double[] x = {-10, -3, 0, 1, 2, 3, 4, 10};//, 100, -100};
	public static double[] y = { 91, 7, 1, 3, 7, 13, 21, 111};//, 10101, 9901};	
	
	public static void main(String []args) {
		//Create list of points
		List<List<Double>> points = new ArrayList<List<Double>>();
        List<Double> x_num = new ArrayList<Double>();
        List<Double> y_num = new ArrayList<Double>();
        
        for(int i=0; i<x.length;i++) {
        	x_num.add(x[i]);
        	y_num.add(y[i]);
        }
        points.add(x_num);
        points.add(y_num);
        
        Plot plt = new Plot("non-linear regression", "x", "y", points);
        sleep(500);

        // -------------------------------------------------
        // Gradient Descent
        // -------------------------------------------------
        final int epochs = 20;  // Number of iterations we want to run through the algorithm

        // We want to predict hw( x ) = w0 + w1x + w2x^2
        double w1 = 0;
        double w0 = 0;
        double w2 = 0;

        // Learning rate
        double alpha = 0.0001;
        double err=0;

        // Main Gradient Descent Function for Linear Regression
        for(int i = 0; i < epochs; i++) {

            double cost = 0;

            for(int j = 0; j < points.get(0).size(); j++) {

                double x_j = points.get(0).get(j);
                double y_j = points.get(1).get(j);

                double prediction = w0 + (w1 * x_j) + (w2 * x_j * x_j);

                // cost += (y_j - h(x))^2
                cost += (y_j - prediction) * (y_j - prediction);
                err = (y_j - prediction);

                // Update the parameters for our equation.
                w2 += alpha * (err * x_j * x_j );
                w1 += alpha * (err * x_j );
                w0 += alpha * err;

            }

            System.out.println("Current Cost: " + cost);
            System.out.println("W=("+w0+","+w1+","+w2 +")");


            // ---------------------------------------------
            // Our Hypothesis Function after the epoch
            // (these values are final because of how
            // functional programming works in Java).
            final double w_1 = w1;
            final double w_0 = w0;
            final double w_2 = w2;
            HypothesisFunction h_x = (x) -> (w_1 * x) + (w_2 * x * x) + w_0;
            // ----------------------------------------------
            // Plotting prediction with current values of w
            plt.updatePlot(h_x);
            sleep(50);
            // ----------------------------------------------
        }


        System.out.println("Final Equation: h(x) = (" + w0 + " + "+ w1+"*x + " + w2 + "*x^2)");
		
	}
	
	
	
	
	public static List<List<Double>> plotFunction(double min, double max, double step, HypothesisFunction f_x){
        List<List<Double>> output = new ArrayList<>();
        output.add(new ArrayList<>());
        output.add(new ArrayList<>());

        for(double x = min; x <= max; x += step) {
            output.get(0).add(x);
            output.get(1).add(f_x.evaluate(x));
        }

        return output;
    }
	
	static void sleep(int ticks) {
        try{ Thread.sleep(ticks); } catch(Exception e) { e.printStackTrace(); }
    }
}

	
	


