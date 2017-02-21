package me.beresnev.algorithms;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 18.02.17.
 */
public class MatrixPeak {

    /**
     * Matrix Peak
     * <p>
     * Time complexity:
     * O(N * logM), where N - rows, M - columns.
     * <p>
     * {0, 2, 0} | X is the peak IF and only IF
     * {3, 5, 1} | X >= top, bottom, left and right
     * {0, 4, 0} | neighbours. In this example, 5 is the peak
     */
    private MatrixPeak() {
    }

    /**
     * @param matrix input matrix
     * @return value of the peak
     */
    public static int find(int[][] matrix) {
        return getMatrixPeak(matrix, 0, matrix[0].length - 1);
    }

    /**
     * We start with the middle column, and find the biggest number there O(n).
     * Then we compare that biggest number with its left and right neighbours.
     * If our biggest number >= left && >= right, then we have our peak by
     * definition. It's >= than numbers on top and bottom (since it's the biggest),
     * and it's >= than numbers on left and right. If not, we look at neighbours
     * and go to whichever side has the biggest neighbour (left->left, right->right).
     * We find new middle through binary search, and then repeat everything above.
     *
     * @param fromColumn index of the column to start the search from (inclusive)
     * @param toColumn   index of the column to finish the search (inclusive)
     * @see me.beresnev.algorithms.searching.BinarySearch
     * <p>
     * Since it's a combination of liear search (biggest number in column) and
     * binary search (logN), we get O(nlogm) compl., where N - rows, M - columns.
     */
    private static int getMatrixPeak(int[][] matrix, int fromColumn, int toColumn) {
        // avoiding computation of average overflow
        int midColumn = (fromColumn + toColumn) >>> 1;

        int biggestInColumn = Integer.MIN_VALUE;
        int biggestValueRow = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][midColumn] > biggestInColumn) {
                biggestInColumn = matrix[i][midColumn];
                biggestValueRow = i;
            }
        }

        if (midColumn != 0 && midColumn != matrix[0].length - 1) {
            int valueOnLeft = matrix[biggestValueRow][midColumn - 1];
            int valueOnRight = matrix[biggestValueRow][midColumn + 1];

            if (biggestInColumn >= valueOnLeft && biggestInColumn >= valueOnRight) {
                return biggestInColumn;
            } else {
                if (valueOnLeft > valueOnRight) {
                    return getMatrixPeak(matrix, 0, midColumn - 1);
                } else {
                    // go right
                    return getMatrixPeak(matrix, midColumn + 1, toColumn);
                }
            }
        } else {
            // if we hit either side, then biggest value here is the peak
            return biggestInColumn;
        }
    }
}
