//PCA implementation for when the PCA is in two dimensions.
package PCA;

public class PCAImplementation {
    /*
        1. caluclate the mean of the dataset
        2. Center the dataset by subtracting the mean
        3. Find the covariance matrix of the dataset since that would tell us how much each variable is related to each other.
            (The covariance matrix of the dataset will help us identify the directions and eigenvectors that may maximize the spread
            between the points the most.)
        4. Find the eigenvalues and the eigenvectors of the covraiance matrix.
            (The eigenvalues will tell us which eigenvectors are the best axes to be used for the pca. The eigenvector that will capture
            the most variance.)
        5. After getting the eigenvectors, we can just perform a scalar projection to get the points in terms of when the new
            eigenvectors are used as axis.
    */

    public static void main(String[] args){
        double[][] matrix = new double[][]{{2, 1, 0}, {1, 2, 1}, {0, 1, 2}};

    }
}
