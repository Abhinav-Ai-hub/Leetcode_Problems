class Solution {
       boolean isValidBSThelper(TreeNode node, Integer lower,Integer upper){
    
        if(node == null){
            return true;
        }

       
        if((upper != null && node.val>= upper) || (lower!=null && node.val<=lower)){
            return false;
        }

        boolean left=isValidBSThelper(node.left,lower,node.val);

        if(!left){
            return false;
        }
    return isValidBSThelper(node.right,node.val,upper);

    }
     public boolean isValidBST(TreeNode root) {
        if(root.left==null && root.right == null){
            return true;
        }
    
        return isValidBSThelper(root,null,null);
        
        
    }
}