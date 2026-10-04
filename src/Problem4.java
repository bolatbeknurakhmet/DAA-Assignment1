import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Problem4 {

    public double minDistBrute(double[][] p) {
        if (p == null || p.length < 2) {
            return 0.0;
        }

        double minDistance = Double.POSITIVE_INFINITY;
        int n = p.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dist = distance(p[i], p[j]);
                if (dist < minDistance) {
                    minDistance = dist;
                }
            }
        }

        return minDistance;
    }

    public double minDistSmart(double[][] p) {
        if (p == null || p.length < 2) {
            return 0.0;
        }

        double[][] sortedX = p.clone();
        Arrays.sort(sortedX, Comparator.comparingDouble(a -> a[0]));

        double[][] sortedY = p.clone();
        Arrays.sort(sortedY, Comparator.comparingDouble(a -> a[1]));

        return closestPairRecursive(sortedX, sortedY);
    }

    private double closestPairRecursive(double[][] sortedX, double[][] sortedY) {
        int n = sortedX.length;

        if (n <= 3) {
            return minDistBrute(sortedX);
        }

        int mid = n / 2;
        double[] midPoint = sortedX[mid];

        double[][] leftX = Arrays.copyOfRange(sortedX, 0, mid);
        double[][] rightX = Arrays.copyOfRange(sortedX, mid, n);

        List<double[]> leftYList = new ArrayList<>();
        List<double[]> rightYList = new ArrayList<>();

        for (double[] point : sortedY) {
            if (point[0] < midPoint[0] || (point[0] == midPoint[0] && leftYList.size() < leftX.length)) {
                leftYList.add(point);
            } else {
                rightYList.add(point);
            }
        }

        double[][] leftY = leftYList.toArray(new double[0][]);
        double[][] rightY = rightYList.toArray(new double[0][]);

        double dLeft = closestPairRecursive(leftX, leftY);
        double dRight = closestPairRecursive(rightX, rightY);

        double d = Math.min(dLeft, dRight);

        List<double[]> strip = new ArrayList<>();
        for (double[] point : sortedY) {
            if (Math.abs(point[0] - midPoint[0]) < d) {
                strip.add(point);
            }
        }

        return Math.min(d, stripClosest(strip, d));
    }

    private double stripClosest(List<double[]> strip, double d) {
        double min = d;
        int size = strip.size();

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size && (strip.get(j)[1] - strip.get(i)[1]) < min; j++) {
                double dist = distance(strip.get(i), strip.get(j));
                if (dist < min) {
                    min = dist;
                }
            }
        }

        return min;
    }

    private double distance(double[] p1, double[] p2) {
        double dx = p1[0] - p2[0];
        double dy = p1[1] - p2[1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    static void main() {
        var solver = new Problem4();
        System.out.println("Brute: " + solver.minDistBrute(new double[][]{{0,0},{3,4},{-5,-3}}));
        System.out.println("Smart: " + solver.minDistSmart(new double[][]{{0,0},{3,4},{-5,-3}}));
    }
}