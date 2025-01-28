public class PolynomialTask implements Runnable {
    private int start;
    private int end;
    private Polynomial first, second, result;

    public PolynomialTask(int start, int end, Polynomial first, Polynomial second, Polynomial result) {
        this.start = start;
        this.end = end;
        this.first = first;
        this.second = second;
        this.result = result;
    }

    @Override
    public void run() {
        for (int index = start; index < end; index++) {
            if (index > result.getLength()) {
                return;
            }
            // for eg coefficient 4, find all 0,4 1,3 2,2 3,1 4,0
            for (int j = 0; j <= index; j++) {
                if (j < first.getLength() && (index - j) < second.getLength()) {
                    int value = first.getCoefficients().get(j) * second.getCoefficients().get(index - j);
                    result.getCoefficients().set(index, result.getCoefficients().get(index) + value);
                }
            }
        }
    }
}