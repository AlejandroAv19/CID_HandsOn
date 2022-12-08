package handson.five;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.introspection.AddedBehaviour;

public class HandsOn5 extends Agent{

    // GUI declaration
    private HandsOn5Gui myGui;

    // Value from the input
    double x1_input = 0.0;
    double x2_input = 0.0;

    // Data set
    double[] x1 = {37.8, 39.3, 45.9, 41.3, 10.8};
    double[] x2 = {69.2, 45.1, 69.3, 58.5, 58.4};
    double[] y = {22.1, 10.4, 9.3, 18.5, 12.9};
    int n = x1.length;

    // Variables
    double ex1 = 0.0;
    double ex2 = 0.0;
    double ey = 0.0;
    double ex1pow2 = 0.0;
    double ex2pow2 = 0.0;
    double ex1x2 = 0.0;
    double ex1y = 0.0;
    double ex2y = 0.0;

    double ds = 0.0;
    double db0 = 0.0;
    double db1 = 0.0;
    double db2 = 0.0;

    double b0 = 0.0;
    double b1 = 0.0;
    double b2 = 0.0;

    // Flag to determine if the agent has finished its work
    boolean isYPredicted = false;

    protected void setup(){
        // Show GUI
        myGui = new HandsOn5Gui(this);
        myGui.showGui();

        // Say hello
        System.out.println("Agent "+getLocalName()+" started.");
    }

    private class predict_y extends Behaviour {
        public void action(){
            // Calculate ex1
            System.out.println("Action: Adding all elements of x1 into variable ex1");
            for(int i = 0; i < n; i++){
                ex1 += x1[i];
            }
            System.out.println("EX1: " + ex1);

            // Calculate ex2
            System.out.println("Action: Adding all elements of x2 into variable ex2");
            for(int i = 0; i < n; i++){
                ex2 += x2[i];
            }
            System.out.println("EX2: " + ex2);

            // Calculate ey
            System.out.println("Action: Adding all elements of y into variable ey");
            for(int i = 0; i < n; i++){
                ey += y[i];
            }
            System.out.println("EY: " + ey);

            // Calculate ex1pow2
            System.out.println("Action: Calculating ex1 to the power of 2 and assign the value to variable ex1pow2");
            for(int i = 0; i < n; i++){
                ex1pow2 += Math.pow(x1[i],2);
            }
            System.out.println("EX1POW2: " + ex1pow2);

            // Calculate ex2pow2
            System.out.println("Action: Calculating ex2 to the power of 2 and assign the value to variable ex2pow2");
            for(int i = 0; i < n; i++){
                ex2pow2 += Math.pow(x2[i],2);
            }
            System.out.println("EX2POW2: " + ex2pow2);

            // Calculate ex1x2
            System.out.println("Action: Multiplying x1 by x2 and adding the results into the ex1x2 variable");
            for(int i = 0; i < n; i++){
                ex1x2 += x1[i] * x2[i];
            }

            System.out.println("EX1X2: " + ex1x2);

            // Calculate ex1y
            System.out.println("Action: multiplying x1 by y and adding the results into the ex1y variable");
            for(int i = 0; i < n; i++){
                ex1y += x1[i] * y[i];
            }

            System.out.println("EX1Y: " + ex1y);

            // Calculate ex2y
            System.out.println("Action: multiplying x2 by y and adding the results into the ex2y variable");
            for(int i = 0; i < n; i++){
                ex2y += x2[i] * y[i];
            }

            System.out.println("EX2Y: " + ex2y);

            // Calculate ds
            System.out.println("Action: Calculating the value of the system determinant");
            ds = ((n * ex1pow2 * ex2pow2) +
                    (ex1 * ex1x2 * ex2) +
                    (ex2 * ex1 * ex1x2) -
                    (ex2 * ex1pow2 * ex2) -
                    (ex1x2 * ex1x2 * n) -
                    (ex2pow2 * ex1 * ex1));
            System.out.println("Ds = " + ds);

            // Calculate db0
            System.out.println("Action: Calculating the value of the b0 determinant");
            db0 = ((ey * ex1pow2 * ex2pow2) +
                    (ex1 * ex1x2 * ex2y) +
                    (ex2 * ex1y * ex1x2) -
                    (ex2y * ex1pow2 * ex2) -
                    (ex1x2 * ex1x2 * ey) -
                    (ex2pow2 * ex1y * ex1));
            System.out.println("Db0 = " + db0);

            // Calculate db1
            System.out.println("Action: Calculating the value of the b1 determinant");
            db1 = ((n * ex1y * ex2pow2) +
                    (ey * ex1x2 * ex2) +
                    (ex2 * ex1 * ex2y) -
                    (ex2 * ex1y * ex2) -
                    (ex2y * ex1x2 * n) -
                    (ex2pow2 * ex1 * ey));
            System.out.println("Db1 = " + db1);

            // Calculate db2
            System.out.println("Action: Calculating the value of the b2 determinant");
            db2 = ((n * ex1pow2 * ex2y) +
                    (ex1 * ex1y * ex2) +
                    (ey * ex1 * ex1x2) -
                    (ex2 * ex1pow2 * ey) -
                    (ex1x2 * ex1y * n) -
                    (ex2y * ex1 * ex1));
            System.out.println("Db2 = " + db2);

            // Calculate b0
            System.out.println("Action: Calculating the value of b0");
            b0 = (db0/ds);
            System.out.println("b0 = " + b0);

            // Calculate b1
            System.out.println("Action: Calculating the value of b1");
            b1 = (db1/ds);
            System.out.println("b1 = " + b1);

            // Calculate b1
            System.out.println("Action: Calculating the value of b1");
            b2 = (db2/ds);
            System.out.println("b2 = " + b2);

            // Predict Y based on all calculations
            System.out.println("Action: Predicting the value of Y based of given values of X1 (" + x1_input + ") and X2 (" + x2_input + ")");
            System.out.println("Regression equation: y = (b0 + (b1 * x1) + (b2 * x2)");
            System.out.println("Regression equation with replaced values: y = (" + b0 + " + (" + b1 + " * " + x1_input + ") + (" + b2 + " * " + x2_input + "))");
            double y_predicted = (b0 + (b1 * x1_input) + (b2 * x2_input));
            System.out.println("Y predicted: " + y_predicted);
            isYPredicted = true;
        }

        public boolean done(){
            return isYPredicted;
        }
    }

    public void receiveXValues(double _x1, double _x2, Agent myAgent){
        x1_input = _x1;
        x2_input = _x2;
        System.out.println("Value of X1 from the input:" + x1_input);
        System.out.println("Value of X2 from the input:" + x2_input);
        myAgent.addBehaviour(new predict_y());
    }

}



