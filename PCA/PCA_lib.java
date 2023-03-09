//PCA library for when the data is in two dimensions
package PCA;

import java.util.*;

public class PCA_lib {
    //Let the first value of data be the horizontal values : x
    //Let the second value of the data be the vertical values : y

    public static double[] mean(double[][] dataset){
         double datasetLen = dataset.length;
         double horizontal = 0;
         double vertical = 0;

        for(double[] data: dataset){
            horizontal += data[0];
            vertical += data[1];
        }

         double hMean = horizontal/datasetLen;
         double vMean = vertical/datasetLen;

        return new double[]{hMean, vMean};
    }

    public double mean(double[] input){
        int inputLen = input.length;

        double sum = 0;
        for(int no: input){
            sum += no;
        }

        return sum/inputLen;
    }

    public ArrayList<double[]> center(double[][] dataset, double[] mean){
        ArrayList<double[]> store = new ArrayList<>();

        for(int[] data: dataset){
            store.add(new double[]{data[0] - mean[0], data[1] - mean[1]});
        }

        return store;
    }

    //Covariance formula: sum((x-x_avg)(y-y_avg))/N-1
    public double covariance(double[] x, double[] y){
        if(x.length != y.length){
            throw new IllegalArgument("Arrays must be of the same length.");
        }
        double meanX = mean(x);
        double meanY = mean(y);

        double sum = 0;

        for(int i = 0; i < x.length; i++){
            sum += (x[i]-meanX) * (y[i]-meanY);
        }

        //x.length-1 if sample
        return sum/x.length-1;
    }
    

    public double[][] eigenvectors(double[][] matrix) {
        int n = matrix.length;
        double[][] eigenvectors = new double[n][n];
        double tolerance = 1e-6;
        int maxIterations = 1000;

        // Initialize eigenvectors to the identity matrix
        for (int i = 0; i < n; i++) {
            eigenvectors[i][i] = 1.0;
        }

        // Loop over eigenvalues
        for (int k = 0; k < n; k++) {
            double[] x = new double[n];
            Arrays.fill(x, 1.0);

            double lambda = 0.0;
            double prevLambda = Double.MAX_VALUE;

            // Power iteration loop
            int iterations = 0;
            while (Math.abs(lambda - prevLambda) > tolerance && iterations < maxIterations) {
                prevLambda = lambda;
                double[] y = multiply(matrix, x);
                lambda = dot(x, y);
                x = scale(y, 1.0 / norm(y));
                iterations++;
            }

            // Store eigenvector
            for (int i = 0; i < n; i++) {
                eigenvectors[i][k] = x[i];
            }

            // Deflate matrix
            matrix = deflate(matrix, lambda, x);
        }

        return eigenvectors;
    }

    // Helper function to multiply a matrix and a vector
    public double[] multiply(double[][] matrix, double[] vector) {
        int n = matrix.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            double sum = 0.0;
            for (int j = 0; j < n; j++) {
                sum += matrix[i][j] * vector[j];
            }
            result[i] = sum;
        }
        return result;
    }

    // Helper function to compute the dot product of two vectors
    public double dot(double[] x, double[] y) {
        int n = x.length;
        double result = 0.0;
        for (int i = 0; i < n; i++) {
            result += x[i] * y[i];
        }
        return result;
    }

}
