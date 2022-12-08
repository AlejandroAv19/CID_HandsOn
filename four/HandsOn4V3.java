package handson.four;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class HandsOn4V3 extends Agent{

    // GUI declaration
    private HandsOn4V3Gui myGui;

    // Value from the input
    double x_input = 0.0;

    public void setup() {
        // Show GUI
        myGui = new HandsOn4V3Gui(this);
        myGui.showGui();

        // Say hello
        System.out.println("Agent " + getLocalName() + " started.");
    }

    private class callToActionBehavior extends OneShotBehaviour {
        public void action(){
            System.out.println("Calling regression execution");
            ImplementRegression regression = new ImplementRegression();
            regression.executeRegression(x_input);
        }

        public int onEnd(){
            myAgent.doDelete();
            System.out.println("Agent's task is done, agent killed");
            return super.onEnd();
        }
    }

    public void update_x(double _x, Agent myAgent){
        x_input = _x;
        System.out.println("Value of X from the input:" + x_input);
        myAgent.addBehaviour(new callToActionBehavior());
    }
}

class ImplementRegression {

    // Data set
    double[] x = {23.00,26.00,30.00,34.00,43.00};
    double[] y = {651.00,762.00,856.00,1063.00,1190.00};
    int n = x.length;

    // Variables
    double[] x2 = new double[n];
    double[] xy = new double[n];
    double ex = 0.0;
    double ey = 0.0;
    double ex2 = 0.0;
    double exy = 0.0;

    double ds = 0.0;
    double db0 = 0.0;
    double db1 = 0.0;

    double b0 = 0.0;
    double b1 = 0.0;

    public void executeRegression(double x_input){
        // Calculate ex
        System.out.println("Action: Adding all elements of x into variable ex");
        for(int i = 0; i < n; i++){
            ex += x[i];
        }

        // Calculate ey
        System.out.println("Action: Adding all elements of y into variable ey");
        for(int i = 0; i < n; i++){
            ey += y[i];
        }

        // Calculate x2
        System.out.println("Action: Calculating x^2 for every element of x and adding them to the x2 array");
        for(int i = 0; i < n; i++){
            x2[i] = Math.pow(x[i],2);
        }

        // Calculate xy
        System.out.println("Action: Multiplying x times y and adding the results to the xy array");
        for(int i = 0; i < n; i++){
            xy[i] = x[i] * y[i];
        }

        // Calculate ex2
        System.out.println("Action: Adding all element of x2 into variable ex2");
        for(int i = 0; i < n; i++){
            ex2 += x2[i];
        }

        // Calculate exy
        System.out.println("Action: Adding all element of xy into variable exy");
        for(int i = 0; i < n; i++){
            exy += xy[i];
        }

        // Calculate ds
        System.out.println("Action: Calculating the value of the system determinant");
        ds = ((n * ex2) - Math.pow(ex,2));
        System.out.println("Ds = " + ds);

        // Calculate db0
        System.out.println("Action: Calculating the value of the b0 determinant");
        db0 = ((ey * ex2) - (exy * ex));
        System.out.println("Db0 = " + db0);

        // Calculate db1
        System.out.println("Action: Calculating the value of the b1 determinant");
        db1 = ((n * exy) - (ex * ey));
        System.out.println("Db1 = " + db1);

        // Calculate b0
        System.out.println("Action: Calculating the value of b0");
        b0 = (db0/ds);
        System.out.println("b0 = " + b0);

        // Calculate b1
        System.out.println("Action: Calculating the value of b1");
        b1 = (db1/ds);
        System.out.println("b1 = " + b1);

        // Predict Y based on all the calculations
        System.out.println("Action: Predicting the value of Y based of given value of X: " + x_input);
        System.out.println("Regression equation: y = (b0 + (b1 * x))");
        System.out.println("Regression equation with replaced values: y = ("+b0+" + ("+b1+" * "+x_input+"))");
        double y_predicted = (b0 + (b1*x_input));
        System.out.println("Y predicted: "+y_predicted);
    }
}


