package handson.five;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class HandsOn5V2 extends Agent {

    // GUI declaration
    private HandsOn5V2Gui myGui;

    // Values from the input
    double x1_input = 0.0;
    double x2_input = 0.0;
    double x3_input = 0.0;

    public void setup() {
        // Show GUI
        myGui = new HandsOn5V2Gui(this);
        myGui.showGui();

        // Say hello
        System.out.println("Agent " + getLocalName() + " started.");
    }

    private class callToActionBehavior extends OneShotBehaviour {
        public void action() {
            System.out.println("Calling regression execution");
            ImplementRegression regression = new ImplementRegression();
            regression.executeRegression(x1_input, x2_input);
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
        myAgent.addBehaviour(new HandsOn5V2.callToActionBehavior());
    }
}

class ImplementRegression {
    double xMatrix[][] = {{1,37.8,69.1},{1, 39.3, 45.1},{1, 45.9, 69.3}, {1, 41.3, 58.5}, {1, 10.8, 58.4}};
    double yMatrix[][] = {{22.1}, {10.4}, {9.3}, {18.5}, {12.9}};

    public void executeRegression(double x1_input, double x2_input) {
        double xTransposed[][] = tansposeMatrix(xMatrix);

        double xTx[][] = multiplyMatrix(xTransposed, xMatrix);

        double xTxInversed[][] = inverseMatrix(xTx);

        double xTy[][] = multiplyMatrix(xTransposed, yMatrix);

        double bResults[][] = multiplyMatrix(xTxInversed, xTy);

        double[] betas = new double[bResults[0].length];
        for(int i = 0; i < bResults[0].length; i++){
            betas[i] = bResults[i][0];
        }

        switch(betas.length){
            case 2:{
                System.out.println("Regression equation: y = b0 + (b1 * x1)");
                System.out.println("Regression equation with replaced values: y = (" + betas[0] + " + (" + betas[1] + " * " + x1_input + "))");
                double y_predicted = (betas[0] + (betas[1] * x1_input));
                System.out.println("Y predicted: "+y_predicted);
                break;
            }
            case 3:{
                System.out.println("Regression equation: y = b0 + (b1 * x1) + (b2 * x2)");
                System.out.println("Regression equation with replaced values: y = (" + betas[0] + " + (" + betas[1] + " * " + x1_input + ") + ("+ betas[2] + " * " + x2_input + ")");
                double y_predicted = (betas[0] + (betas[1] * x1_input) + (betas[2] * x2_input));
                System.out.println("Y predicted: "+ y_predicted);
                break;
            }
            /*
            case 4:{
                System.out.println("Regression equation: y = b0 + (b1 * x1) + (b2 * x2) + (b3 * x3))");
                System.out.println("Regression equation with replaced values: y = (" + betas[0] + " + (" + betas[1] + " * " + x1_input + ") + ("+ betas[2] + " * " + x2_input + ") + ("+ betas[3] + " * " + x3_input + ")");
                double y_predicted = (betas[0] + (betas[1] * x1_input) + (betas[2] * x2_input) * (betas[3] * x3_input));
                System.out.println("Y predicted: "+ y_predicted);
            }
            */
            default:{
                System.out.println("Invalid case...");
            }
        }
    }

    // For debuging
    public void printMatrix(double matrix[][]){
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    public double[][] tansposeMatrix(double matrix[][]){
        int matrixColumns =  matrix[0].length;
        int matrixRows =  matrix.length;

        double transposedMatrix[][] = new double[matrixColumns][matrixRows];

        for(int i = 0; i < matrixRows; i++){
            for(int j = 0; j < matrixColumns; j++){
                transposedMatrix[j][i] = matrix[i][j];
            }
        }

        return transposedMatrix;
    }

    public double[][] multiplyMatrix(double a[][], double b[][]){
        int r1 = a.length, c1 = a[0].length;
        int r2 = a.length, c2 = b[0].length;

        int n = r1;
        double result[][] = new double[n][n];
        for(int i = 0; i < r1; i++){
            for(int j = 0; j < c2; j++){
                result[i][j] = 0;
                for(int k = 0; k < c1; k++) { result[i][j] += a[i][k] * b[k][j]; }
            }
        }
        return result;
    }

    /*  InverseMatrix logic and gaussian method logic taken from
        https://www.sanfoundry.com/java-program-find-inverse-matrix/
        All credits goes to the respective author  */

    public double[][] inverseMatrix(double matrix[][]){
       int n = matrix.length;

        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i)
            b[i][i] = 1;

        // Transform the matrix into an upper triangle
        gaussian(matrix, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]
                            -= matrix[index[j]][i]*b[index[i]][k];

        // Perform backward substitutions
        for (int i=0; i<n; ++i)
        {
            x[n-1][i] = b[index[n-1]][i]/matrix[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j)
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k)
                {
                    x[j][i] -= matrix[index[j]][k]*x[k][i];
                }
                x[j][i] /= matrix[index[j]][j];
            }
        }
        return x;
    }

    public static void gaussian(double a[][], int index[]) {
        int n = index.length;
        double c[] = new double[n];

        // Initialize the index
        for (int i = 0; i < n; ++i)
            index[i] = i;

        // Find the rescaling factors, one from each row
        for (int i = 0; i < n; ++i) {
            double c1 = 0;
            for (int j = 0; j < n; ++j) {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j = 0; j < n - 1; ++j) {
            double pi1 = 0;
            for (int i = j; i < n; ++i) {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i = j + 1; i < n; ++i) {
                double pj = a[index[i]][j] / a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l = j + 1; l < n; ++l)
                    a[index[i]][l] -= pj * a[index[j]][l];
            }
        }
    }
}