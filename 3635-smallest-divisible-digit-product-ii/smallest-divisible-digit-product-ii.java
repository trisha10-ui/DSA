import java.util.Arrays;

class Solution {
    public String smallestNumber(String num, long t) {
        // Step 1: Factorize t into counts of prime factors (2, 3, 5, 7)
        long temp = t;
        int c2 = 0, c3 = 0, c5 = 0, c7 = 0;
        
        while (temp % 2 == 0) { temp /= 2; c2++; }
        while (temp % 3 == 0) { temp /= 3; c3++; }
        while (temp % 5 == 0) { temp /= 5; c5++; }
        while (temp % 7 == 0) { temp /= 7; c7++; }
        
        // If t has prime factors other than 2, 3, 5, 7, it's impossible
        if (temp > 1) return "-1";

        int n = num.length();

        // Step 2: Track required remaining prime factors for each prefix of `num`
        int[] req2 = new int[n + 1];
        int[] req3 = new int[n + 1];
        int[] req5 = new int[n + 1];
        int[] req7 = new int[n + 1];

        req2[0] = c2; req3[0] = c3; req5[0] = c5; req7[0] = c7;

        int firstZeroIndex = n;
        for (int i = 0; i < n; i++) {
            int digit = num.charAt(i) - '0';
            if (digit == 0) {
                firstZeroIndex = i;
                break;
            }

            req2[i + 1] = Math.max(0, req2[i] - countFactor(digit, 2));
            req3[i + 1] = Math.max(0, req3[i] - countFactor(digit, 3));
            req5[i + 1] = Math.max(0, req5[i] - countFactor(digit, 5));
            req7[i + 1] = Math.max(0, req7[i] - countFactor(digit, 7));
        }

        // If `num` itself is valid (no 0s and satisfies divisibility by t)
        if (firstZeroIndex == n && req2[n] == 0 && req3[n] == 0 && req5[n] == 0 && req7[n] == 0) {
            return num;
        }

        // Step 3: Try to find a pivot position from right to left to increase a digit
        for (int i = Math.min(n - 1, firstZeroIndex); i >= 0; i--) {
            int startDigit = (num.charAt(i) - '0') + 1;

            for (int d = startDigit; d <= 9; d++) {
                int rem2 = Math.max(0, req2[i] - countFactor(d, 2));
                int rem3 = Math.max(0, req3[i] - countFactor(d, 3));
                int rem5 = Math.max(0, req5[i] - countFactor(d, 5));
                int rem7 = Math.max(0, req7[i] - countFactor(d, 7));

                int minLen = getMinLength(rem2, rem3, rem5, rem7);

                if (minLen <= (n - 1 - i)) {
                    String prefix = num.substring(0, i) + d;
                    String suffix = constructSuffix(rem2, rem3, rem5, rem7, n - 1 - i);
                    return prefix + suffix;
                }
            }
        }

        // Step 4: If no valid number of length n exists, expand length to > n
        int totalMinLen = getMinLength(c2, c3, c5, c7);
        int targetLen = Math.max(n + 1, totalMinLen);

        return constructSuffix(c2, c3, c5, c7, targetLen);
    }

    // Helper: Counts how many times factor `p` divides `val`
    private int countFactor(int val, int p) {
        int count = 0;
        while (val > 0 && val % p == 0) {
            count++;
            val /= p;
        }
        return count;
    }

    // Helper: Minimum digits required to cover required prime factors
    private int getMinLength(int r2, int r3, int r5, int r7) {
        int len = r5 + r7;
        len += r3 / 2;
        r3 %= 2;

        len += r2 / 3;
        r2 %= 3;

        if (r3 == 1 && r2 == 2) {
            len += 2; // Split into digits like 6 and 2
        } else if (r3 == 1 || r2 > 0) {
            len += 1;
        }

        return len;
    }

    // Helper: Greedily builds the lexicographically smallest suffix matching required factors
    private String constructSuffix(int r2, int r3, int r5, int r7, int targetLen) {
        StringBuilder tail = new StringBuilder();

        while (r3 >= 2) { tail.append('9'); r3 -= 2; }
        while (r2 >= 3) { tail.append('8'); r2 -= 3; }
        while (r7 >= 1) { tail.append('7'); r7 -= 1; }
        while (r2 >= 1 && r3 >= 1) { tail.append('6'); r2 -= 1; r3 -= 1; }
        while (r5 >= 1) { tail.append('5'); r5 -= 1; }
        while (r2 >= 2) { tail.append('4'); r2 -= 2; }
        while (r3 >= 1) { tail.append('3'); r3 -= 1; }
        while (r2 >= 1) { tail.append('2'); r2 -= 1; }

        while (tail.length() < targetLen) {
            tail.append('1');
        }

        char[] arr = tail.toString().toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }
}

// Synced seamlessly with LeetHub Pro
// Pro features: https://bit.ly/leethubpro | Free version: https://bit.ly/leethubv4
// Get it here: https://chromewebstore.google.com/detail/bcilpkkbokcopmabingnndookdogmbna