/*
Check whether the original sequence org can be uniquely reconstructed from the 
sequences in seqs. The org sequence is a permutation of the integers from 1 to n, 
with 1 ≤ n ≤ 104. Reconstruction means building a shortest common supersequence 
of the sequences in seqs (i.e., a shortest sequence so that all sequences in seqs 
are subsequences of it). Determine whether there is only one sequence that can be 
reconstructed from seqs and it is the org sequence.

Example 1:

Input:
org: [1,2,3], seqs: [[1,2],[1,3]]

Output:
false

Explanation:
[1,2,3] is not the only one sequence that can be reconstructed, because [1,3,2] 
is also a valid sequence that can be reconstructed.
Example 2:

Input:
org: [1,2,3], seqs: [[1,2]]

Output:
false

Explanation:
The reconstructed sequence can only be [1,2].
Example 3:

Input:
org: [1,2,3], seqs: [[1,2],[1,3],[2,3]]

Output:
true

Explanation:
The sequences [1,2], [1,3], and [2,3] can uniquely reconstruct the 
original sequence [1,2,3].
Example 4:

Input:
org: [4,1,5,2,6,3], seqs: [[5,2,6,3],[4,1,5,2]]

Output:
true
*/
public class Solution {
    // return false if cycle exists
    List<Integer> result;
    int[] visited;
    Map<Integer, List<Integer>> graph;

    class Pair{
        int node;
        int state;
        public Pair(int n, int s){
            node = n;
            state = s;
        }
    }
    
    // DFS using stack
    public boolean dfs(int source){
        Deque<Pair> stk = new LinkedList<>();
        stk.push(new Pair(source, 0));
        
        // Already explored the neighbors of the nodes in the pushed set
        Set<Integer> pushed = new HashSet<>();
        while(!stk.isEmpty()){
            Pair top = stk.pop();
            if(top.state == 0){
                if(pushed.contains(top.node))
                    continue;
                pushed.add(top.node);
                
                visited[top.node] = 1;
                stk.push(new Pair(top.node, 1));

                if(graph.containsKey(top.node)){
                    List<Integer> adj = graph.get(top.node);
                    for(int neighbor : adj){
                        if(visited[neighbor] == 2)
                            continue;
                        // gray cells
                        if(visited[neighbor] == 1)
                            return false;
                        stk.push(new Pair(neighbor, 0));
                    }
                }
            }            
            else{
                visited[top.node] = 2;
                result.add(top.node);
            }    
        }
        return true;
    }
    
    public boolean checkSeq(int n, int source, int[] org){
        visited = new int[n+1];

        result = new ArrayList<>();
        if(!dfs(source))                    return false;

        if(result.size() != org.length)     return false;

        Collections.reverse(result);
        // Compare two sequences
        for(int i=0; i<org.length; ++i){
            if(result.get(i) != org[i]){
                return false;
            }
        }
        return true;
    }
    
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        int n = org.length;
        // Check if all the numbers of the original sequeunce is found in the
        // sequence array
        int[] copy = new int[org.length+1];
        for(int i=0; i<org.length; ++i)
            copy[i] = org[i];

        for(List<Integer> seq: seqs){
            for(int t : seq){
                if(t <= 0 || t > n)         return false;
                copy[t-1] = 0;
            }
        }
        for(int i : copy){
            if(i != 0)
                return false;
        }
    
        // All sequences cover this array        
        // Topological sort
        // Construct the graph
        graph = new HashMap<>();
        // Compute inDegree
        int[] inDegree = new int[n+1];
        for(List<Integer> seq: seqs){
            for(int i=0; i<seq.size()-1; ++i){
                // Directed
                int x = seq.get(i);
                int y = seq.get(i+1);
                List<Integer> edges;
                if(!graph.containsKey(x)){
                    graph.put(x, new ArrayList<>());
                }
                graph.get(x).add(y);
                inDegree[y]++;
            }
        }
        
        // if multiple nodes have 0 indegree return false (ambiguity)
        // If no nodes has 0 indegree return false
        int nRoot = 0;
        int source = -1;
        for(int i = 1; i<=n; ++i){
            int inDeg = inDegree[i];
            if(inDeg == 0){
                source = i;
                nRoot++;   
            }
        }
        
        if(nRoot > 1 || nRoot == 0)
            return false;

        if(!checkSeq(n, source, org)){
            return false;
        }
        
        // Reverse the order of the edges
        for(Map.Entry<Integer, List<Integer>> entry : graph.entrySet()){
            List<Integer> t = entry.getValue();
            Collections.reverse(t);
            graph.put(entry.getKey(), t);
        }
        if(!checkSeq(n, source, org)){
            return false;
        }
        return true;
    }
}

