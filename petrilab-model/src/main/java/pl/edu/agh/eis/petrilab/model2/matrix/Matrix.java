package pl.edu.agh.eis.petrilab.model2.matrix;

import java.util.Arrays;

/**
 * Name: Matrix
 * Description: Matrix
 * Date: 2015-05-23
 * Created by BamBalooon
 */
public class Matrix {
    public static int[][] add(int[][] matrix1, int[][] matrix2) {
        return add(true, matrix1, matrix2);
    }

    public static int[][] substract(int[][] matrix1, int[][] matrix2) {
        return add(false, matrix1, matrix2);
    }

    public static Double[][] add(Double[][] matrix1, Double[][] matrix2) {
        return add(true, matrix1, matrix2);
    }

    public static Double[][] substract(Double[][] matrix1, Double[][] matrix2) {
        return add(false, matrix1, matrix2);
    }

    public static Double[] row(Double[][] matrix, int index) {
        return Arrays.copyOf(matrix[index], matrix[0].length);
    }

    public static int[] row(int[][] matrix, int index) {
        return Arrays.copyOf(matrix[index], matrix[0].length);
    }

    private static int[][] add(boolean positive, int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length, cols = matrix1[0].length;
        int[][] result = new int[rows][cols];
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++) {
                result[i][j] = positive
                        ? matrix1[i][j] + matrix2[i][j]
                        : matrix1[i][j] - matrix2[i][j];
            }
        return result;
    }

    private static Double[][] add(boolean positive, Double[][] matrix1, Double[][] matrix2) {
        int rows = matrix1.length, cols = matrix1[0].length;
        Double[][] result = new Double[rows][cols];
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++) {
                result[i][j] = positive
                        ? matrix1[i][j] + matrix2[i][j]
                        : matrix1[i][j] - matrix2[i][j];
            }
        return result;
    }
}
