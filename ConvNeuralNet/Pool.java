package ConvNeuralNet;

public class Pool {
    public static int[][] maxPool(int[][] inputArr, int filterSize){
        int rows = inputArr[0].length;
        int cols = inputArr.length;

        int resultRows = rows / filterSize;
        int resultCols = cols / filterSize;

        int[][] result = new int[resultRows][resultCols];

        for(int i = 0; i < resultRows; i += 1){
            for(int j = 0; j < resultCols; j += 1){

                int maxVal = Integer.MIN_VALUE;

                for(int k = i; k < (i+1)*filterSize; k++){
                    for(int l = j; l < (j+1)*filterSize; l++){
                        maxVal = Math.max(maxVal, inputArr[k][l]);
                    }
                }

                result[i][j] = maxVal;
            }
        }

        return result;
    }

    public static void main(String args[]){
        int[][] input = new int[4][4];
        int[][] output = maxPool(input, 2);
    }
}
