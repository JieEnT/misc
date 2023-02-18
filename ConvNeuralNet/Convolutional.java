package ConvNeuralNet;

public class Convolutional {
    //conv
    public static int[][] convolve(int[][] inputArr, int[][] convLayer, int stride){
        int inputCols = inputArr[0].length;
        int inputRows = inputArr.length;
        int convCols = convLayer[0].length;
        int convRows = convLayer.length;

        int outputCols = (inputCols - convCols)/stride + 1;
        int outputRows = (inputRows - convRows)/stride + 1;

        int[][] output = new int[outputRows][outputCols];

        for(int i = 0; i < outputRows; i++){
            for(int j = 0; j < outputCols; j++){

                int sum = 0;

                for(int k = i*stride; k < (i + 1)*stride; k++){
                    for(int l = j*stride; l < (j + 1) * stride; l++){
                        sum += inputArr[k][l] * convLayer[k-(i*stride)][l-(j*stride)];
                    }
                }

                output[i][j] = sum;
            }
        }
        
        return output;
    }
    public static void main(String[] args){
        int[][] inputArr = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] convLayer = {{1,0},{0,0}};

        int[][] output = convolve(inputArr, convLayer, 1);
        Printer.printMatrix(output);
    }
}
