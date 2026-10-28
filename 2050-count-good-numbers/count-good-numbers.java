class Solution {
    static final long MOD = 1_000_000_007;

    public int countGoodNumbers(long n) {
        long even = (n + 1) / 2;
        long odd = n / 2;

        long a = myPow(5, even);
        long b = myPow(4, odd);

        return (int) ((a * b) % MOD);
    }

    public long myPow(long x, long n) {
        if (n == 0) {
            return 1;
        }

        long half = myPow(x, n / 2);
        long result = (half * half) % MOD;

        if (n % 2 == 1) {
            result = (result * x) % MOD;
        }

        return result;
    }
}