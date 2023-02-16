import NNLib.*;

import java.util.List;

class NN{

    Matrix inputHiddenWeights, hiddenOutputWeights, hiddenBias, outputBias;
    double learningRate = 0.01;

    public NN(int inputDim, int hiddenDim, int outputDim){
        inputHiddenWeights = new Matrix(hiddenDim, inputDim);
        hiddenOutputWeights = new Matrix(outputDim, hiddenDim);

        hiddenBias = new Matrix(hiddenDim, 1);
        outputBias = new Matrix(outputDim, 1);
    }

    public Matrix predict(double[] input){
        Matrix inputMatrix = NN_lib.arrayToMatrix(input);
        Matrix hiddenLayer = NN_lib.matrixMultiplication(inputHiddenWeights, inputMatrix);
        hiddenLayer.addMatrix(hiddenBias); //Since each Node has a hiddenBias
        hiddenLayer.sigmoid(); //prediction of the first hidden layer

        Matrix outputLayer = NN_lib.matrixMultiplication(hiddenOutputWeights, hiddenLayer);
        outputLayer.addMatrix(outputBias);
        outputLayer.sigmoid();

        return outputLayer;
    }

    public List<Double> predictToList(double[] input){
        Matrix output = predict(input);
        return output.flattenToList();
    }

    public void fit(double[][] xTrain, double[][] yTrain, int epochs){ //Training the algorithm
        int trainingSetLength = xTrain.length;

        for(int i = 0; i < epochs; i++){
            for(int j = 0; j < trainingSetLength; j++){
                this.train(xTrain[j], yTrain[j]);
            }
        }
    }

    public void train(double[] X, double[] Y){
        Matrix inputLayer = NN_lib.arrayToMatrix(X);
        Matrix hiddenLayer = NN_lib.matrixMultiplication(inputHiddenWeights, inputLayer);
        hiddenLayer.addMatrix(hiddenBias); //Since each Node has a hiddenBias
        hiddenLayer.sigmoid(); //prediction of the first hidden layer

        Matrix outputLayer = NN_lib.matrixMultiplication(hiddenOutputWeights, hiddenLayer);
        outputLayer.addMatrix(outputBias);
        outputLayer.sigmoid();

        Matrix target = NN_lib.arrayToMatrix(Y);

        Matrix error = NN_lib.matrixSubtraction(target, outputLayer);
        Matrix gradient = NN_lib.sigmoidDerivative(outputLayer);

        // d(E_total)/ d(w) = d(E_total)/d(y_final) * d(y_final)/d(y) * d(y)/d(w)
        // (-(True - predicted)) * (y_final * (1 - y_final)) * input
        gradient.multiplyMatrix(error);
        gradient.multiplyLearningRate(learningRate);

        //hiddenLayer = hidden_final (input)
        Matrix hiddenTranspose = NN_lib.transpose(hiddenLayer);
        Matrix deltaOfHiddenToOutputWeights = NN_lib.matrixMultiplication(gradient, hiddenTranspose);

        //Training
        Matrix oldHiddenOutputWeights = hiddenOutputWeights;
        hiddenOutputWeights.addMatrix(deltaOfHiddenToOutputWeights);
        
        //The update to the bias is (-(True - predicted)) * (y_final * (1 - y_final)) * input
        //Where the input is 1, hence this is represented by the gradient       
        outputBias.addMatrix(gradient);

        //Now updated weights_ho is the ** more ideal ** weights and we will use that as the correct outputs for the
        // second layer so we only have to perform derivatives through one layer 
        Matrix oldHiddenOutputWeightsTranspose = NN_lib.transpose(oldHiddenOutputWeights);
        
        //Matrix hiddenErrors = NN_lib.matrixSubtraction(hiddenOutputWeightsTranspose, hiddenLayer); //Doesn't work since this is the hiddenoutput weights while hiddenlayer is currently the prediction

        //Ahttps://www.javatpoint.com/pytorch-backpropagation-process-in-deep-neural-network
        Matrix hiddenErrors = NN_lib.matrixMultiplication(oldHiddenOutputWeightsTranspose, error); 

        Matrix hiddenGradient = NN_lib.sigmoidDerivative(hiddenLayer);
        hiddenGradient.multiplyMatrix(hiddenErrors);
        hiddenGradient.multiplyLearningRate(learningRate);

        Matrix inputTranspose = NN_lib.transpose(inputLayer);
        Matrix deltaOfInputToHiddenWeights = NN_lib.matrixMultiplication(hiddenGradient, inputTranspose);

        inputHiddenWeights.addMatrix(deltaOfInputToHiddenWeights);
        hiddenBias.addMatrix(hiddenGradient);
    }

    static double[][] X_train_data = {
            { 0, 0 },
            { 1, 0 },
            { 0, 1 },
            { 1, 1 }
    };
    static double[][] Y_train_data = {
            { 0 }, { 1 }, { 1 }, { 0 }
    };

    public static void main(String[] args){
        NN nn = new NN(2, 10, 1);

        System.out.println("Prediction for {0,0} train data = " + nn.predictToList(X_train_data[0]));
        System.out.println("Prediction for {1,0} train data = " + nn.predictToList(X_train_data[1]));
        System.out.println("Prediction for {0,1} train data = " + nn.predictToList(X_train_data[2]));
        System.out.println("Prediction for {1,1} train data = " + nn.predictToList(X_train_data[3]));

        nn.fit(X_train_data, Y_train_data, 100000);

        System.out.println("{0,0}: Expected = 0, Predicted = " + nn.predictToList(X_train_data[0]));
        System.out.println("{1,0}: Expected = 1, Predicted = " + nn.predictToList(X_train_data[1]));
        System.out.println("{0,1}: Expected = 1, Predicted = " + nn.predictToList(X_train_data[2]));
        System.out.println("{1,1}: Expected = 0, Predicted = " + nn.predictToList(X_train_data[3]));
    }
}