public class Problem2 {

    public double getMedianBrute(int[] A, int[] B) {
        int n = (A != null) ? A.length : 0;
        int m = (B != null) ? B.length : 0;
        int total = n + m;
        int[] merged = new int[total];

        int i = 0, j = 0, k = 0;
        while (i < n && j < m) {
            if (A[i] <= B[j]) {
                merged[k++] = A[i++];
            } else {
                merged[k++] = B[j++];
            }
        }
        while (i < n) {
            merged[k++] = A[i++];
        }
        while (j < m) {
            merged[k++] = B[j++];
        }

        if (total % 2 == 1) {
            return merged[total / 2];
        } else {
            return (merged[(total / 2) - 1] + merged[total / 2]) / 2.0;
        }
    }

    public double getMedianSmart(int[] A, int[] B) {
        if (A == null) A = new int[0];
        if (B == null) B = new int[0];

        if (A.length > B.length) {
            return getMedianSmart(B, A);
        }

        int n = A.length;
        int m = B.length;
        int low = 0, high = n;

        while (low <= high) {
            int partitionA = low + (high - low) / 2;
            int partitionB = (n + m + 1) / 2 - partitionA;

            int maxLeftA = (partitionA == 0) ? Integer.MIN_VALUE : A[partitionA - 1];
            int minRightA = (partitionA == n) ? Integer.MAX_VALUE : A[partitionA];

            int maxLeftB = (partitionB == 0) ? Integer.MIN_VALUE : B[partitionB - 1];
            int minRightB = (partitionB == m) ? Integer.MAX_VALUE : B[partitionB];

            if (maxLeftA <= minRightB && maxLeftB <= minRightA) {
                if ((n + m) % 2 == 1) {
                    return Math.max(maxLeftA, maxLeftB);
                } else {
                    return (Math.max(maxLeftA, maxLeftB) + Math.min(minRightA, minRightB)) / 2.0;
                }
            } else if (maxLeftA > minRightB) {
                high = partitionA - 1;
            } else {
                low = partitionA + 1;
            }
        }

        return 0.0;
    }

    public static void main(String[] args) {
        Problem2 p = new Problem2();
        System.out.println(p.getMedianBrute(new int[]{1, 2, 3}, new int[]{3, 4, 5}));
        System.out.println(p.getMedianSmart(new int[]{1, 2, 3}, new int[]{3, 4, 5}));
    }
}