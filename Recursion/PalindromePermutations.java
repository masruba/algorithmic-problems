// Given a string s, return all the palindromic permutations (without duplicates) of it. Return an empty list if no palindromic permutation could be formed.

import java.util.*;

public class PalindromePermutations {
    List<String> result;
    StringBuilder mid;
    public void permute(String candidates, boolean[] taken, int lev, char[] cur){
        if(lev == candidates.length()){
            StringBuilder x = new StringBuilder(String.valueOf(cur));
            StringBuilder t = new StringBuilder(x);
            t.append(mid);
            t.append(x.reverse());
            result.add(t.toString());
            return;
        }
        for(int i=0; i<candidates.length(); ++i){
            // unique
            if(taken[i] || (i > 0 && candidates.charAt(i) == candidates.charAt(i-1) && taken[i-1] == false))
                continue;
            cur[lev] = candidates.charAt(i);
            taken[i] = true;
            permute(candidates, taken, lev+1, cur);
            taken[i] = false;
        }
    }
    public List<String> generatePalindromes(String s) {
        // Check if it's possible to build palindrome from the characters of the string
        result = new ArrayList<>();
        int[] cnt = new int[256];
        for(int i=0; i<s.length(); i++){
          cnt[s.charAt(i)]++;
        }
        
        int odd = 0;
        for(int i=0; i<256; i++){
          if(cnt[i] == 0)       continue;
          if(cnt[i] % 2 != 0)   odd++;
        }  
        // At most one character can have odd frequency
        if(odd > 1)     
            return result;
        
        // Create candidate characters        
        mid = new StringBuilder();
        StringBuilder candidates = new StringBuilder();
        for(int i=0; i<256; i++){
            int val = cnt[i];
            if(val % 2 != 0)        mid.append((char)(i));
            for(int j=0; j<val/2; ++j){
                candidates.append((char)(i));
            }
        } 
        
        int n = candidates.length();
        boolean[] taken = new boolean[n];
        char[] cur = new char[n];
        permute(candidates.toString(), taken, 0, cur);
        return result;
    }
    public static void main(String[] args){
        Solution s = new Solution();
        List<String> r = s.generatePalindromes("abba");
    }
}

