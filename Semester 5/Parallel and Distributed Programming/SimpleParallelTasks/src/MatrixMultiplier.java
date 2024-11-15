public class MatrixMultiplier {
    private final int[][] firstMatrix;
    private final int[][] secondMatrix;
    private final int[][] resultMatrix;
    private final int rowSize;
    private final int columnSize;

    private final int commonSize;

    public MatrixMultiplier(int[][] firstMatrix, int[][] secondMatrix, int rowSize, int columnSize, int commonSize) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.commonSize = commonSize;
        this.resultMatrix = new int[rowSize][columnSize];
    }

    public int computeElement(int row, int col) {
        int element = 0;
        for (int index = 0; index < commonSize; index++) {
            element += firstMatrix[row][index] * secondMatrix[index][col];
        }
        return element;
    }

    public void setElement(int row, int col, int value) {
        resultMatrix[row][col] = value;
    }

    public int[][] getResultMatrix() {
        return resultMatrix;
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }
}
