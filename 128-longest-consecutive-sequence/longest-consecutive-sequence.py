class Solution:
    def longestConsecutive(self, nums: list[int]) -> int:
        num_value=set(nums)
        result=0
        for i in num_value:
            if i-1 not in num_value:
                current=i
                length=1
                while current+1 in num_value:
                    current+=1
                    length+=1
                result=max(result,length)
        return result

# Synced seamlessly with LeetHub Pro
# Pro features: https://bit.ly/leethubpro | Free version: https://bit.ly/leethubv4
# Get it here: https://chromewebstore.google.com/detail/bcilpkkbokcopmabingnndookdogmbna