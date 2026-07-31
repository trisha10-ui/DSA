import java.util.Arrays;

class Solution {
    public int minimumPushes(String word) {
        // Step 1: Count frequency of each letter (a-z)
        int[] freq = new int[26];
        for (int i = 0; i < word.length(); i++) {
            freq[word.charAt(i) - 'a']++;
        }

        // Step 2: Sort frequencies in ascending order
        Arrays.sort(freq);

        int totalPushes = 0;

        // Step 3: Iterate backwards (from highest frequency to lowest)
        for (int i = 25; i >= 0; i--) {
            if (freq[i] == 0) {
                break; // No more characters left to assign
            }

            // Rank of character (0-indexed for non-zero elements)
            int rank = 25 - i;
            int multiplier = (rank / 8) + 1;

            totalPushes += freq[i] * multiplier;
        }

        return totalPushes;
    }
}