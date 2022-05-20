package utility;

public class Distance {
    private Distance() {
    }

    public static double euclidean(int fromX, int fromY, int toX, int toY) {
        return Math.sqrt(Math.pow((fromX - toX), 2) + Math.pow((fromY - toY), 2));
    }
}
