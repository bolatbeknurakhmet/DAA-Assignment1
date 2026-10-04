import java.math.BigInteger;

public class Problem5 {

    public String multBrute(String A, String B) {
        if (A == null || B == null || A.length() == 0 || B.length() == 0) {
            return "0";
        }
        if (A.equals("0") || B.equals("0")) {
            return "0";
        }

        boolean isNegative = (A.charAt(0) == '-') ^ (B.charAt(0) == '-');
        if (A.charAt(0) == '-') A = A.substring(1);
        if (B.charAt(0) == '-') B = B.substring(1);

        int n = A.length();
        int m = B.length();
        int[] result = new int[n + m];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                int mul = (A.charAt(i) - '0') * (B.charAt(j) - '0');
                int sum = mul + result[i + j + 1];

                result[i + j + 1] = sum % 10;
                result[i + j] += sum / 10;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int num : result) {
            if (!(sb.length() == 0 && num == 0)) {
                sb.append(num);
            }
        }

        if (sb.length() == 0) return "0";
        if (isNegative) sb.insert(0, "-");

        return sb.toString();
    }

    public String multSmart(String A, String B) {
        if (A == null || B == null || A.length() == 0 || B.length() == 0) {
            return "0";
        }
        if (A.equals("0") || B.equals("0")) {
            return "0";
        }

        boolean isNegative = (A.charAt(0) == '-') ^ (B.charAt(0) == '-');
        if (A.charAt(0) == '-') A = A.substring(1);
        if (B.charAt(0) == '-') B = B.substring(1);

        String result = karatsuba(A, B);

        if (result.equals("0")) return "0";
        return isNegative ? "-" + result : result;
    }

    private String karatsuba(String A, String B) {
        A = removeLeadingZeros(A);
        B = removeLeadingZeros(B);

        if (A.length() == 0 || B.length() == 0) return "0";

        if (A.length() < 10 || B.length() < 10) {
            return multBrute(A, B);
        }

        int n = Math.max(A.length(), B.length());
        A = padLeft(A, n);
        B = padLeft(B, n);

        int half = n / 2;

        String a = A.substring(0, n - half);
        String b = A.substring(n - half);
        String c = B.substring(0, n - half);
        String d = B.substring(n - half);

        String ac = karatsuba(a, c);
        String bd = karatsuba(b, d);

        String aPlusB = addStrings(a, b);
        String cPlusD = addStrings(c, d);
        String abcd = karatsuba(aPlusB, cPlusD);

        String sub1 = subtractStrings(abcd, ac);
        String adPlusBc = subtractStrings(sub1, bd);

        String term1 = shiftLeft(ac, 2 * half);
        String term2 = shiftLeft(adPlusBc, half);

        return removeLeadingZeros(addStrings(addStrings(term1, term2), bd));
    }

    private String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int i = num1.length() - 1, j = num2.length() - 1, carry = 0;

        while (i >= 0 || j >= 0 || carry > 0) {
            int sum = carry;
            if (i >= 0) sum += num1.charAt(i--) - '0';
            if (j >= 0) sum += num2.charAt(j--) - '0';
            carry = sum / 10;
            sb.append(sum % 10);
        }

        return sb.reverse().toString();
    }

    private String subtractStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int i = num1.length() - 1, j = num2.length() - 1, borrow = 0;

        while (i >= 0) {
            int sub = (num1.charAt(i--) - '0') - borrow;
            if (j >= 0) {
                sub -= (num2.charAt(j--) - '0');
            }
            if (sub < 0) {
                sub += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            sb.append(sub);
        }

        return removeLeadingZeros(sb.reverse().toString());
    }

    private String shiftLeft(String str, int digits) {
        if (str.equals("0")) return "0";
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < digits; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    private String padLeft(String str, int length) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - str.length()) {
            sb.append('0');
        }
        sb.append(str);
        return sb.toString();
    }

    private String removeLeadingZeros(String str) {
        int i = 0;
        while (i < str.length() - 1 && str.charAt(i) == '0') {
            i++;
        }
        return str.substring(i);
    }

    static void main() {
        Problem5 solver = new Problem5();
        String strA = "12345678987654321";
        String strB = "98765432123456789";

        System.out.println("Brute: " + solver.multBrute(strA, strB));
        System.out.println("Smart: " + solver.multSmart(strA, strB));

        BigInteger a = new BigInteger(strA);
        BigInteger b = new BigInteger(strB);
        System.out.println("BigInteger: " + a.multiply(b));
    }
}
