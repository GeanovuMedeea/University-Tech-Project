public class MatrixMultiplierTask implements Runnable {
    private final MatrixMultiplier multiplier;
    private final int taskIndex;
    private final int taskCount;

    public MatrixMultiplierTask(MatrixMultiplier multiplier, int taskIndex, int taskCount) {
        this.multiplier = multiplier;
        this.taskIndex = taskIndex;
        this.taskCount = taskCount;
    }

    @Override
    public void run() {
        int rowSize = multiplier.getRowSize();
        int columnSize = multiplier.getColumnSize();
        for (int elemIndex = this.taskIndex; elemIndex < rowSize * columnSize; elemIndex += taskCount) {
            int row = elemIndex / columnSize;
            int col = elemIndex % columnSize;
            int result = multiplier.computeElement(row, col);
            multiplier.setElement(row, col, result);
        }
    }
}
