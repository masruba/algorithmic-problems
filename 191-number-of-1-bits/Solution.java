 public class Solution {
    public int hammingWeight(int n) {
        int cnt = 0;
        for(int i=31; i>=0; i--){
            if((n & (1<<i)) != 0)
                cnt++;
        }
        return cnt;
    }
}