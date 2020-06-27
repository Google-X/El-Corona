
package SourceDS;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeNode<T> implements Iterable<TreeNode<T>> {

    private T data;
    private String info;
    private TreeNode<T> parent;
    private List<TreeNode<T>> children;
    public static HashMap<String, TreeNode<String>> treeKey;
    
    public TreeNode(T t, String info) {
        data = t;
        children = new LinkedList<TreeNode<T>>();
        this.info = info;
    }

    public void addChild (T child, String info) {
        TreeNode<T> childNode = new TreeNode<T>(child, "");
        childNode.info = info;
        childNode.parent = this;
        this.children.add(childNode);
        treeKey.put(String.valueOf(child), (TreeNode<String>)childNode);
    }
    
    public void showTree(){
        
    }
    public String toString() {
        return "\n" + showTree(0);
    }

    private String showTree(int tab) {
        String re = "";
        String indent = "";
        
        for (int i = 0; i < tab; ++i) {
            indent = indent + " | ";
        }
        
        re = indent + " " + data + " " + info;
        
        for (TreeNode<T> child : children) {
            re += "\n" + child.showTree(tab + 1);
        }
        
        return re;
    }
    
    public boolean containSubRoot(String subRoot){
        return treeKey.containsKey(subRoot);
    }
    
    public TreeNode getTreeNode(String subRoot){
        return treeKey.get(subRoot);
    }
    
    @Override
    public Iterator<TreeNode<T>> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

