package NNLib;

import java.lang.Math;
import java.util.*;

public class Matrix {
    double[][] data;
    int rows;
    int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];

        randomInitialize(data);
    }

    // Since we do not know the number of inputs going into then node, we will just
    // randomly initialise the weights of the matrix to be between (-1, 1)
    private void randomInitialize(double[][] data) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = Math.random() * 2 - 1;
            }
        }
    }

    // Adding by Scaler
    public void addScalar(double scalar) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] += scalar;
            }
        }
    }

    // Adding matrix elementwise
    public void addMatrix(Matrix mm) {
        double[][] matrix = mm.data;
        int matrixRow = matrix.length;
        int matrixCol = matrix[0].length;

        if (rows != matrixRow && cols != matrixCol) {
            throw new RuntimeException("Matrix addition failed due to incompatible row or column length.");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] += matrix[i][j];
            }
        }
    }

    public void multiplyMatrix(Matrix mm){
        double[][] matrix = mm.data;
        int matrixRow = matrix.length;
        int matrixCol = matrix[0].length;

        if (rows != matrixRow && cols != matrixCol) {
            throw new RuntimeException("Matrix addition failed due to incompatible row or column length.");
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] *= matrix[i][j];
            }
        }
    }

    // Gradient may be a matrix, each component of gradient has to be multiplied
    public void multiplyLearningRate(double lr) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] *= lr;
            }
        }
    }

    //Sigmoid function for predictive certainty (1/(1+e^(-x)))
    public void sigmoid(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                data[i][j] = 1/(1 + Math.exp(-this.data[i][j]));
            }
        }
    }

    public List<Double> flattenToList(){
        List<Double> result = new ArrayList<Double>();

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                result.add(data[i][j]);
            }
        }

        return result;
    }

    // In this model, the nodes are all represented by a single matrix.
    // After matrix multiplication, we will have the outputs in a matrix as well
    // each representing a single node. We will have to perofrm a sigmoid function
    // on all values in order to get the actual result from a single node


}