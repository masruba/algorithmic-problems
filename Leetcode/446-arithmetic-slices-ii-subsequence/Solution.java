/*
A sequence of numbers is called arithmetic if it consists of at least three elements 
and if the difference between any two consecutive elements is the same.

For example, these are arithmetic sequences:

1, 3, 5, 7, 9
7, 7, 7, 7
3, -1, -5, -9
The following sequence is not arithmetic.

1, 1, 2, 5, 7

A zero-indexed array A consisting of N numbers is given. A subsequence slice of 
that array is any sequence of integers (P0, P1, ..., Pk) such that 0 ≤ P0 < P1 < ... < Pk < N.

A subsequence slice (P0, P1, ..., Pk) of array A is called arithmetic if the 
sequence A[P0], A[P1], ..., A[Pk-1], A[Pk] is arithmetic. In particular, this means that k ≥ 2.

The function should return the number of arithmetic subsequence slices in the array A.

The input contains N integers. Every integer is in the range of -2^31 and 2^31-1 
and 0 ≤ N ≤ 1000. The output is guaranteed to be less than 2^31-1.


Example:

Input: [2, 4, 6, 8, 10]

Output: 7

Explanation:
All arithmetic subsequence slices are:
[2,4,6]
[4,6,8]
[6,8,10]
[2,4,6,8]
[4,6,8,10]
[2,4,6,8,10]
[2,6,10]
*/

public class Solution {
    // O(n^3) bruteforce
    public int numberOfArithmeticSlices(int[] A) {
        int n = A.length;
        int[][] dp = new int[n][n];
        
        int result = 0;
        for(int i=2; i<n; ++i){
            for(int j=1; j<i; ++j){
                for(int k=0; k<j; ++k){ 
                    long diff = (long)A[i] - A[j];
                    if(diff == (long)A[j] - A[k]){
                        dp[i][j] += dp[j][k] + 1;
                    }
                }
                result += dp[i][j];
            }
        }
        return result;
    }
}

// O(n^2)
class Solution2 {
    public int numberOfArithmeticSlices(int[] A) {
        int n = A.length;
        Map<Integer,Integer>[] map = new Map[n];
        int ans = 0;
        for(int i=0; i<n; ++i){
            map[i] = new HashMap<>();
            for(int j=0; j<i; ++j){
                long diff = (long)A[i] - A[j];
                if(diff < Integer.MIN_VALUE || diff > Integer.MAX_VALUE) 
                    continue;
                int cnt1 = map[i].getOrDefault((int)diff, 0); 
                int cnt2 = map[j].getOrDefault((int)diff, 0);
                ans += cnt2;
                map[i].put((int)diff, cnt1 + cnt2 + 1);
            }
        }
        return ans;
    }
}
