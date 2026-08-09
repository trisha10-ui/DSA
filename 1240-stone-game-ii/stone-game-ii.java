class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        
        // Compute suffix sums: suffixSum[i] holds the sum of stones from index i to n-1
        int[] suffixSum = new int[n];
        suffixSum[n - 1] = piles[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixSum[i] = suffixSum[i + 1] + piles[i];
        }
        
        // Memoization table: memo[i][M] stores max stones obtainable starting at index i with limit M
        int[][] memo = new int[n][n + 1];
        
        return solve(0, 1, piles, suffixSum, memo);
    }
    
    private int solve(int i, int M, int[] piles, int[] suffixSum, int[][] memo) {
        int n = piles.length;
        
        // Base case: if we can take all remaining piles, take them all
        if (i + 2 * M >= n) {
            return suffixSum[i];
        }
        
        // Return cached result if available
        if (memo[i][M] != 0) {
            return memo[i][M];
        }
        
        int maxStones = 0;
        
        // Try taking X piles (1 <= X <= 2 * M)
        for (int X = 1; X <= 2 * M; X++) {
            int nextM = Math.max(M, X);
            // Current player gets remaining stones minus the max stones Bob can get next
            int currentStones = suffixSum[i] - solve(i + X, nextM, piles, suffixSum, memo);
            maxStones = Math.max(maxStones, currentStones);
        }
        
        memo[i][M] = maxStones;
        return maxStones;
    }
}

// Synced seamlessly with LeetHub Pro
// Pro features: https://bit.ly/leethubpro | Free version: https://bit.ly/leethubv4
// Get it here: https://chromewebstore.google.com/detail/bcilpkkbokcopmabingnndookdogmbna