package handson.seven;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class HandsOn7 extends Agent {

    // GUI declaration
    private HandsOn7Gui myGui;

    // Value from the input
    double x1_input = 0.0;
    double x2_input = 0.0;

    public void setup(){
        // Show GUI
        myGui = new HandsOn7Gui(this);
        myGui.showGui();

        System.out.println("Agent " + getLocalName() + " started.");
    }

    private class callToActionBehavior extends OneShotBehaviour {
        public void action() {
            ImplementClassification classification = new ImplementClassification();
            classification.runLearningFunction(classification.weights);

            System.out.println("RESULTS --------------------");
            for(int i = 0; i < classification.weights.length; i++){
                System.out.println(classification.weights[i]);
            }

            double result = 0.0;
            result = classification.executeClassification(x1_input, x2_input);
            System.out.println("DEBUG");
            System.out.println(result);
            if(result > 0.5) System.out.println("Belongs to group A: Will be a winner team");
            else System.out.println("Belongs to group B: Will not be a winner team");

        }

        public int onEnd() {
            myAgent.doDelete();
            System.out.println("Agent's task is done, agent killed");
            return super.onEnd();
        }
    }

    public void getXValues(double _x1, double _x2, Agent myAgent) {
        x1_input = _x1;
        x2_input = _x2;
        System.out.println("Value of X1 from the input:" + x1_input);
        System.out.println("Value of X2 from the input:" + x2_input);
        myAgent.addBehaviour(new HandsOn7.callToActionBehavior());
    }
}

class ImplementClassification {
    double lr; // Learning rate
    double nIt; // Number of iterations

    // Training data set - taken from https://kseow.com/logisticregression
    double x[][] = {{1,1,1},{1,4,2},{1,2,4}}; // this includes the appended 1 in each element for calculating w0
    double y[] = {0,1,1};

    // Weights
    double[] weights = new double[y.length];

    public void setUpParams(){
        // Weights
        double[] setUpWeights = {0,0,0}; // The w values that we want to set
        for(int i = 0; i < x[0].length; i++){
            weights[i] = setUpWeights[i];
        }

        // Learning rate
        lr = 0.1;

        // Total number of iterations
        nIt = 1;
    }

    public void runLearningFunction(double[] oldWeights){
        // Running setup
        setUpParams();

        // Running gradient descendant
        for(int i = 0; i < nIt; i++){
            for(int j = 0; j < y.length; j++){
                weights[j] = weights[j] - lr * calcE(j);
            }
        }
    }

    public void calculateFx(){
        double result = 0.0;
        for(int i = 0; i < y.length; i++){
            result += weights[i]*x[i];
        }
    }

    public double calcE(int _x){
        double result = 0.0;
        for(int i = 0; i < y.length; i++){
            result += ((y[i] - 0.5) *  x[_x][i]);
        }
        return result;
    }


    public double executeClassification(double _x1, double _x2){
        double result = 0;
        result = (1 / (1 + (Math.pow(Math.E, -((weights[0]+weights[1] * _x1) + (weights[1] * _x2))))));
        return result;
    }

}
