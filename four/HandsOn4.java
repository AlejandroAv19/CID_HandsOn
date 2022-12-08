package handson.four;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class HandsOn4 extends Agent{

    // GUI declaration
    private HandsOn4Gui myGui;

    // Value from the input
    double x_input = 0.0;

    // Data set
    double[] x = {23.00,26.00,30.00,34.00,43.00,48.00,52.00,57.00,58.00};
    double[] y = {651.00,762.00,856.00,1063.00,1190.00,1298.00,1421.00,1440.00,1518.00};
    int n = 9;

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

    protected void setup(){

        // Show GUI
        myGui = new HandsOn4Gui(this);
        myGui.showGui();

        // Say hello
        System.out.println("Agent "+getLocalName()+" started.");

        // Add behaviors
        addBehaviour(new calculate_ex());
        addBehaviour(new calculate_ey());
        addBehaviour(new calculate_x2());
        addBehaviour(new calculate_xy());
        addBehaviour(new calculate_ex2());
        addBehaviour(new calculate_exy());
        addBehaviour(new calculate_ds());
        addBehaviour(new calculate_db0());
        addBehaviour(new calculate_db1());
        addBehaviour(new calculate_b0());
        addBehaviour(new calculate_b1());
    }

    private class calculate_ex extends OneShotBehaviour {
        public void action(){
            System.out.println("Action: Adding all elements of x into variable ex");
            for(int i = 0; i < n; i++){
                ex += x[i];
            }
        }
    }

    private class calculate_ey extends OneShotBehaviour {
        public void action(){
            System.out.println("Action: Adding all elements of y into variable ey");
            for(int i = 0; i < n; i++){
                ey += y[i];
            }
        }
    }

    private class calculate_x2 extends OneShotBehaviour {
        public void action(){
            System.out.println("Action: Calculating x^2 for every element of x and adding them to the x2 array");
            for(int i = 0; i < n; i++){
                x2[i] = Math.pow(x[i],2);
            }
        }
    }

    private class calculate_xy extends OneShotBehaviour {
        public void action(){
            System.out.println("Action: Multiplying x times y and adding the results to the xy array");
            for(int i = 0; i < n; i++){
                xy[i] = x[i] * y[i];
            }
        }
    }

    private class calculate_ex2 extends OneShotBehaviour {
        public void action(){
            System.out.println("Action: Adding all element of x2 into variable ex2");
            for(int i = 0; i < n; i++){
                ex2 += x2[i];
            }
        }
    }

    private class calculate_exy extends OneShotBehaviour {
        public void action(){
            System.out.println("Action: Adding all element of xy into variable exy");
            for(int i = 0; i < n; i++){
                exy += xy[i];
            }
        }
    }

    private class calculate_ds extends OneShotBehaviour {
        public void action(){
            System.out.println("Action: Calculating the value of the system determinant");
            ds = ((n * ex2) - Math.pow(ex,2));
            System.out.println("Ds = " + ds);
        }
    }

    private class calculate_db0 extends OneShotBehaviour {
        public void action(){
            System.out.println("Action: Calculating the value of the b0 determinant");
            db0 = ((ey * ex2) - (exy * ex));
            System.out.println("Db0 = " + db0);
        }
    }

    private class calculate_db1 extends OneShotBehaviour {
        public void action(){
            System.out.println("Action: Calculating the value of the b1 determinant");
            db1 = ((n * exy) - (ex * ey));
            System.out.println("Db1 = " + db1);
        }
    }

    private class calculate_b0 extends OneShotBehaviour {
        public void action(){
            System.out.println("Action: Calculating the value of b0");
            b0 = (db0/ds);
            System.out.println("b0 = " + b0);
        }
    }

    private class calculate_b1 extends OneShotBehaviour {
        public void action(){
            System.out.println("Action: Calculating the value of b1");
            b1 = (db1/ds);
            System.out.println("b1 = " + b1);
        }
    }

    private class predict_y extends OneShotBehaviour {
        public void action(){
            System.out.println("Action: Predicting the value of Y based of given value of X: " + x_input);
            System.out.println("Regression equation: y = (b0 + (b1 * x))");
            System.out.println("Regression equation with replaced values: y = ("+b0+" + ("+b1+" * "+x_input+"))");
            double y_predicted = (b0 + (b1*x_input));
            System.out.println("Y predicted: "+y_predicted);
        }
    }

    public void update_x(double _x, Agent myAgent){
        x_input = _x;
        System.out.println("Value of X from the input:" + x_input);
        myAgent.addBehaviour(new predict_y());
    }
}


