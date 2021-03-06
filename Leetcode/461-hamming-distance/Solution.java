/*
The Hamming distance between two integers is the number of positions at which 
the corresponding bits are different.

Given two integers x and y, calculate the Hamming distance.

Note:
0 ≤ x, y < 231.

Example:

Input: x = 1, y = 4

Output: 2

Explanation:
1   (0 0 0 1)
4   (0 1 0 0)
       ↑   ↑

The above arrows point to positions where the corresponding bits are different.
*/
public class Solution {
    public int hammingDistance(int x, int y) {
        int cnt = 0;
        // Count the number of different bit in the xor
        long xor = x ^ y;
        for(int i=31; i>=0; i--){
            if((xor & (1<<i)) != 0)
                cnt++;
        }
        return cnt;
    }
}
