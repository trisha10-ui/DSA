class Solution {
    public int smallestNumber(int n, int t) {
        while(true){
            if(getDigitProduct(n)%t==0){
            return n;
        }
        n++;
    }
}
private int getDigitProduct(int num){
    int product=1;
    while(num>0){
        product*=(num%10);
        num/=10;
    }
    return product;
}
}

// Synced seamlessly with LeetHub Pro
// Pro features: https://bit.ly/leethubpro | Free version: https://bit.ly/leethubv4
// Get it here: https://chromewebstore.google.com/detail/bcilpkkbokcopmabingnndookdogmbna