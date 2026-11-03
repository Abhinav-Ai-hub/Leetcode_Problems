/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if(root==null){
            return new TreeNode(val);
        }
        insertIntoBSThelper(root,val);
        return root;

        
    }
    void insertIntoBSThelper(TreeNode root, int val){
        if(val<root.val){
            if(root.left==null){// we are checking beforehand about the availability of a node to the left or right of the current one here the current one is root.
                root.left= new TreeNode(val);
                return;
            }else{
                insertIntoBSThelper(root.left,val);
             }
        }
        else if(val>root.val){
            if(root.right==null){
              root.right=new TreeNode(val);
              return;
            }
            else{
                insertIntoBSThelper(root.right,val);
            }
        }
       

    }
}