class Solution {
public:
    int maxSubarrayLength(vector<int>& nums, int k) {
        unordered_map<int, int> freq;
        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < nums.size(); ++right) {
            // Expand the window by adding the current element
            freq[nums[right]]++;

            // Shrink the window if frequency of nums[right] exceeds k
            while (freq[nums[right]] > k) {
                freq[nums[left]]--;
                left++;
            }

            // Record the maximum valid window length found
            maxLength = max(maxLength, right - left + 1);
        }

        return maxLength;
    }
};

// Synced seamlessly with LeetHub Pro
// Pro features: https://bit.ly/leethubpro | Free version: https://bit.ly/leethubv4
// Get it here: https://chromewebstore.google.com/detail/bcilpkkbokcopmabingnndookdogmbna