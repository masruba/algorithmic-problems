/*
Given a string which contains only lowercase letters, remove duplicate letters so that every letter appear once and only once. You must make sure your result is the smallest in lexicographical order among all possible results.

Example:
Given "bcabc"
Return "abc"

Given "cbacdcbc"
Return "acdb"
*/

/*
Greedy (Use stack)
- Keep a count of each character
- Keep a visited array
- If current character is already visited then continue
- if stack is not empty and top of the stack is greater than current character and more of top of the stack is available later, then pop the 
stack and mark it as not visited
- Push the current character and mark it as visited

- Contents of the stack is the result
*/
public class RemoveDuplicateLetters {
    // O(n) Solution
    public String removeDuplicateLetters(String s) {
        int[] cnt = new int[26];
        boolean[] visited = new boolean[26];
        
        for(int i=0; i<s.length(); ++i){
            cnt[s.charAt(i) - 'a']++;
        }
        
        Deque<Character> stk = new LinkedList<>();
        for(int i=0; i<s.length(); ++i){
            char c = s.charAt(i);
            // decrease count
            cnt[c - 'a']--;
            // if already visited
            if(visited[c - 'a'])
                continue;
            
            // More count of the stack top is available later
            while(!stk.isEmpty() && stk.peekFirst() > c && cnt[stk.peekFirst() - 'a'] > 0){
                // remove the character from the stack
                visited[stk.pop() - 'a'] = false;
            }   
            stk.push(c);
            visited[c - 'a'] = true;
        } 
        
        StringBuilder r = new StringBuilder();
        while(!stk.isEmpty()){
            r.append(stk.pop());
        }
        r.reverse();
        return r.toString();
    }
}