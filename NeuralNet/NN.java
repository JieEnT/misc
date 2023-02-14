import NNLib.*;

class NN{

    public NN(){
        
    }

    public static void main(String[] args){
        Matrix mm = new Matrix(5, 5);
        Matrix mm2 = new Matrix(5, 5);

        NN_lib.printMatrix(mm);
        System.out.println();

        NN_lib.printMatrix(mm2);
        System.out.println();

        Matrix new_mm = NN_lib.matrixSubtraction(mm, mm2);

        NN_lib.printMatrix(new_mm);
    }
}