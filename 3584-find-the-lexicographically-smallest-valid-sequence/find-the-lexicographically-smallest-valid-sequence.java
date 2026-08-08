import java.util.Arrays;

public class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // last[j] stores the maximum starting index in word1 from which 
        // word2[j...m-1] can be formed as a sub-sequence without any edits.
        int[] last = new int[m + 1];
        last[m] = n;

        int ptr = n - 1;
        for (int j = m - 1; j >= 0; j--) {
            while (ptr >= 0 && word1.charAt(ptr) != word2.charAt(j)) {
                ptr--;
            }
            if (ptr >= 0) {
                last[j] = ptr;
                ptr--;
            } else {
                last[j] = -1;
            }
        }

        int[] result = new int[m];
        boolean changed = false;
        int i = 0;

        for (int j = 0; j < m; j++) {
            // Case 1: Exact match
            if (i < n && word1.charAt(i) == word2.charAt(j)) {
                result[j] = i;
                i++;
            } 
            // Case 2: Mismatch, try to perform 1 edit if allowed
            else if (!changed && i < n && i + 1 <= last[j + 1]) {
                result[j] = i;
                changed = true;
                i++;
            } 
            // Case 3: Skip word1 characters until the next exact match for word2[j]
            else {
                while (i < n && word1.charAt(i) != word2.charAt(j)) {
                    i++;
                }
                if (i >= n) {
                    return new int[0]; // Invalid sequence
                }
                result[j] = i;
                i++;
            }
        }

        return result;
    }
}

// Synced seamlessly with LeetHub Pro
// Pro features: https://bit.ly/leethubpro | Free version: https://bit.ly/leethubv4
// Get it here: https://chromewebstore.google.com/detail/bcilpkkbokcopmabingnndookdogmbna