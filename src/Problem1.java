public class Problem1 {

    public int countFreqBrute(int key, int [] A){
        if (A == null || A.length == 0) {
            return 0;
        }
        int count = 0;
        for (int num : A) {
            if (num == key) {
                count++;
            }
        }
        return count;
    }
    public int countFreqSmart(int key, int [] A){
        if (A == null || A.length == 0) {
            return 0;
        }

        int first = findFirstOccurrence(key, A);
        if (first == -1) {
            return 0;
        }
        int last = findLastOccurrence(key, A);

        return last - first + 1;
    }
    private int findFirstOccurrence(int key, int[] A) {
        int low = 0, high = A.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (A[mid] == key) {
                result = mid;
                high = mid - 1;
            } else if (A[mid] < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }
    private int findLastOccurrence(int key, int[] A) {
        int low = 0, high = A.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (A[mid] == key) {
                result = mid;
                low = mid + 1;
            } else if (A[mid] < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }
    static void main() {
        Problem1 p = new Problem1();
        int[] A1 = {1, 1, 1, 2, 2, 2, 2, 2, 2, 4, 4, 4, 5, 5, 5, 5};

        System.out.println("Brute Result (key=4): " + p.countFreqBrute(4, A1));
        System.out.println("Smart Result (key=4): " + p.countFreqSmart(4, A1));
    }
}