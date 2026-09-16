class Solution {
    public double myPow(double x, int n) {

        if (n == 0) {
            return 1;
        }

        if (n < 0) {
            if(n==Integer.MIN_VALUE){
            return 1 /(x* myPow(x, Integer.MAX_VALUE));
            }
            n=-1*n;
              return 1 / myPow(x, n);
        }

        double half = myPow(x, n / 2);

        if (n % 2 == 0) {
            return half * half;
        }

        return x * half * half;
    }
}