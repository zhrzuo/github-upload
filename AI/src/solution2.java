//package ass2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class solution2
{
	
	public static double[] x1 = {1, 2, 0.3, 0.6, 1.2, 1.3, 1.8, 1.5, 3, 4,3.1,3.6,3.8,3.5,3.25};
	public static double[] x2 = {1, 2, 1.2, 0.8, 1, 1, 2, 1.4, 3, 4, 3.3,3.8,2.1,2.2,2.8};
	public static double[] y = {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1,1,1,1,1};
 
	
	public static void main(String []args) {
		//Create list of points
		List<List<Double>> points = new ArrayList<List<Double>>();
        List<Double> x1_num = new ArrayList<Double>();
        List<Double> x2_num = new ArrayList<Double>();
        List<Double> y_num = new ArrayList<Double>();
        
        for(int i=0; i<x1.length;i++) {
        	x1_num.add(x1[i]);
        	x2_num.add(x2[i]);
        	y_num.add(y[i]);
        }
        points.add(x1_num);
        points.add(x2_num);
//        points.add(y_num);
        
        Plot plt = new Plot("Logistic regression", "x1", "x2", points);
        sleep(500);

        // -------------------------------------------------
        // Gradient Descent
        // -------------------------------------------------
        final int epochs = 100;  // Number of iterations we want to run through the algorithm

        // We want to predict hw( x ) = w0 + w1*x + w2*x2
        double w1 = 0;
        double w0 = 0;
        double w2 = 0;

        // Learning rate
        double alpha = 0.1;
        int data_size = points.get(0).size();

        // Main Gradient Descent Function for Linear Regression
        for(int i = 0; i < epochs; i++) {

            double cost = 0;

            for(int j = 0; j < data_size; j++) {

                double x1_j = points.get(0).get(j);
                double x2_j = points.get(1).get(j);
                double y_j = y_num.get(j);
                

                double hw_j = (w0+ w1*x1_j + w2 * x2_j);
                double err = y_j - logisticFun(hw_j);

                // cost += (y_j - h(x))^2
                cost += -(y_j*logisticFun(hw_j) + (1-y_j)*logisticFun(1-hw_j))/data_size;

                // Update the parameters for our equation.
                w2 += alpha * err * x2_j;
                w1 += alpha * err * x1_j;
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
            HypothesisFunction h_x = (x) -> -((w_1 * x) + w_0)/w_2;
            // ----------------------------------------------
            // Plotting prediction with current values of w
            plt.updatePlot(h_x);
            sleep(50);
            // ----------------------------------------------
        }


        System.out.println("Final Equation: h(x) = (" + w2 + "*x2 + " + w1 + "*x1 + " + w0);
		
	}
	
	
	public static double logisticFun(double z) {
		return (1.0/(1+Math.exp(-z)));
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

	
	


