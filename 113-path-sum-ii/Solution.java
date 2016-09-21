/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public void getPathSum(TreeNode root, int target, int curSum, List<Integer> curList, List<List<Integer>> result) {
        if(root == null)
            return;
            
        curSum += root.val;
        curList.add(root.val);
        
        // root to leaf path
        if(curSum == target && root.left == null && root.right == null){
            result.add(new ArrayList<>(curList));
            curList.remove(curList.size()-1);
            curSum -= root.val;
            return;
        }
        getPathSum(root.left, target, curSum, curList, result);
        getPathSum(root.right, target, curSum, curList, result);
        // retrace
        curSum -= root.val;
        curList.remove(curList.size()-1);
    }

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if(root == null)
            return Collections.EMPTY_LIST;
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> curList = new ArrayList<>();
        getPathSum(root, sum, 0, curList, result);
        return result;
    }
}