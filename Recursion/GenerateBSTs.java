/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
// Link: https://leetcode.com/problems/unique-binary-search-trees-ii/
public class GenerateBSTs {
    // Time complexity: Catalan Number (2n)! / (n! (n+1)!)
    // C(n) = /sum_{i=1_n} C(i)*C(n-1)
    public List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> result = new ArrayList<>();
        if(start > end){
            result.add(null);
            return result;
        }
        for(int i=start; i<=end; i++){
            List<TreeNode> leftSubtrees = generateTrees(start, i-1);
            List<TreeNode> rightSubtrees = generateTrees(i+1, end);
            // Generate all combinations of left and right subtrees
            for(TreeNode left : leftSubtrees){
                for(TreeNode right : rightSubtrees){
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    result.add(root);
                }
            }
        }
        return result;
    }
    public List<TreeNode> generateTrees(int n){
        if(n == 0)      return Collections.EMPTY_LIST;
        return generateTrees(1, n);
    }
}