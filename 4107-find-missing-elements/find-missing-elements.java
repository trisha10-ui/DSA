class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        int min = nums[0];
        int max = nums[0];
        
        Set<Integer> present = new HashSet<>();
        
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
            present.add(num);
        }
        
        List<Integer> result = new ArrayList<>();

        for (int i = min; i <= max; i++) {
            if (!present.contains(i)) {
                result.add(i);
            }
        }
        
        return result;
    }
}

// Synced seamlessly with LeetHub Pro
// Pro features: https://bit.ly/leethubpro | Free version: https://bit.ly/leethubv4
// Get it here: https://chromewebstore.google.com/detail/bcilpkkbokcopmabingnndookdogmbna