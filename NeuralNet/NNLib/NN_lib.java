package NNLib;

public class NN_lib {

    public static void printMatrix(Matrix mm) {
        double[][] mm_data = mm.data;

        for (int i = 0; i < mm.rows; i++) {
            for (int j = 0; j < mm.cols; j++) {
                System.out.print(mm_data[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static Matrix matrixMultiplication(Matrix a, Matrix b) {
        int aRows = a.rows;
        int aCols = a.cols;
        int bCols = b.cols;

        Matrix resultantMatrix = new Matrix(aRows, bCols);
        double[][] resultantData = resultantMatrix.data;

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {

                double dotProductValue = 0;

                for (int k = 0; k < aCols; k++) {
                    dotProductValue += a.data[i][k] * b.data[k][j];
                }

                resultantData[i][j] = dotProductValue;
            }
        }

        return resultantMatrix;
    }

    //a-b
    public static Matrix matrixSubtraction(Matrix a, Matrix b){
        int aRows = a.rows;
        int aCols = a.cols;
        int bRows = b.rows;
        int bCols = b.cols;

        double[][] aData = a.data;
        double[][] bData = b.data;

        if(aRows != bRows || aCols != bCols){
            throw new RuntimeException("Matrix subtraction failed due to incompatible row and column sizes.");
        }

        Matrix resultantMatrix = new Matrix(aRows, aCols);
        double[][] resultantData = resultantMatrix.data;

        for(int i = 0; i < aRows; i++){
            for(int j = 0; j < aCols; j++){
                resultantData[i][j] = aData[i][j] - bData[i][j];
            }
        }

        return resultantMatrix;
    }

    public static Matrix transpose(Matrix mm){
        Matrix transposedMatrix = new Matrix(mm.cols, mm.rows);

        for(int i = 0; i < mm.rows; i++){
            for(int j = 0; j < mm.cols; j++){
                transposedMatrix.data[j][i] = mm.data[i][j];
            }
        }

        return transposedMatrix;
    }

    //Need to obtain sigmoid derivtive for backpropagation
    // dE/dw = dE/dy_final * dy_final/dy * dy/dw
    // dy_final = sigmoid(dy)
    // dy_final/dy = (1 - dy_final) * dy_final
    public static Matrix sigmoidDerivative(Matrix mm) {
        Matrix derivativeMatrix = new Matrix(mm.rows, mm.cols);

        for(int i = 0; i < mm.rows; i++){
            for(int j = 0; j < mm.cols; j++){
                derivativeMatrix.data[i][j] = mm.data[i][j];
            }
        }

        return derivativeMatrix;
    }

    //Creation of a matrix from an array so as to perform matrix operations
    public static Matrix arrayToMatrix(double[] input){
        Matrix result = new Matrix(input.length, 1);

        for(int i = 0; i < input.length; i++){
            result.data[i][0] = input[i];
        }

        return result;
    }

}
