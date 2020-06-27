/*
 * Linked List for all Lab 4 questions
 */
package SourceDS;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E extends Comparable<E>>{
    // Inner class LinkedListIterator
    class LinkedListIterator implements Iterator<E>{
        
        ListNode<E> index;
        
        public LinkedListIterator(){
            index = head;
        }
        
        @Override
        public boolean hasNext() {
            return index != null;
        }

        @Override
        public E next(){
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            E val = index.getData();
            index = index.getLink();
            return val;
        }
        
    }
    
    private ListNode head;
    private String name;
    
    public LinkedList(String name) {
        head = null;
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    /**
     * c = current node
     * @return the size of the linked list
     */
    public int length(){
        int counter = 0;
        ListNode c = head;
        while(c != null){
            c = c.getLink();
            counter++;
        }
        return counter;
    }
    
    public void clear(){
        head = null;
    }
    
    public boolean isEmpty(){
        return head == null;
    }
    
    /**
     * Specifically for humanID
     */
    public void showList(){
        System.out.print(name + ": ");
        ListNode c = head;
        while(c != null){
            System.out.print(c.toString());
            c = c.getLink();
        }
    }
    
    /**
     * Only for HumanID not the building
     * @return 
     */
    public String getHumanList(String date){
        String re = "\n[" + date + "] : ";
        
        ListNode c = head;
        while(c != null){
            re += c.toString();
            c = c.getLink();
        }
        
        return re;
    }
    /**
     * For buildings
     * @return 
     */
    public String getBuidlingList(){
        String re = "";
        if(name.length() < 8) re += name + "\t\t\t: ";
        else if(name.length() >= 8 && name.length() < 16) re += name + "\t\t: ";
        else re += name + "\t: ";
        
        ListNode c = head;
        while(c != null){
            re += c.toString();
            c = c.getLink();
        }
        
        return re;
    }
    
    public void showBuidlingList(){
        if(name.length() < 8) System.out.print(name + "\t\t\t: ");
        else if(name.length() >= 8 && name.length() < 16) System.out.print(name + "\t\t: ");
        else System.out.print(name + "\t: ");
        
        ListNode c = head;
        while(c != null){
            System.out.print(c.toString());
            c = c.getLink();
        }
    }
    
    /**
     * n = new node; c = current node
     * @param e = data to be added
     */
    public void addLastNode(E e){
        ListNode n = new ListNode(e, null);
        if(head == null) head = n;
        else {
            ListNode c = head;
            while(c.getLink() != null){
                c = c.getLink();
            }
            c.setLink(n);
        }
    }
    
    /**
     * c = current node; p = previous node
     * @return the deleted data
     */
    public E deleteLastNode(){
        ListNode p = null;
        ListNode c = head;
        if(head != null){
            if(c.getLink() == null){
                head = null;
            } else {
                while(c.getLink() != null){
                    p = c;
                    c = c.getLink();
                }
                p.setLink(null);
            }
        }
        return (E) c.getData();
    }
    
    public void addFirstNode(E e){
        head = new ListNode(e, head);
    }
    
    public E deleteFirstNode(){
        if(head == null) return null;
        else {
            E val = (E) head.getData();
            head = head.getLink();
            return val;
        }
    }
    
    public boolean contains(E e){
        ListNode c = head;
        while(c != null){
            if(e.compareTo((E) c.getData()) == 0) return true;
            c = c.getLink();
        }
        return false;
    }
    
    /**
     * @param e = data
     * @param i = index
     * n = new node
     */
    public void addNodeByPosition(E e, int i){
        if(i == 0) addFirstNode(e);
        else if(i == length()) addLastNode(e);
        else if(i > length()) System.err.println("IndexOutOfBound Exception");
        else {
            ListNode c = head;
            for(int j=1;j<i;j++) c = c.getLink();
            ListNode n = new ListNode(e, c.getLink());
            c.setLink(n);
        }
    }
    
    /**
     * @param i = index
     * @return deleted data
     */
    public E deleteNodeByPosition(int i){
        if(i == 0) return deleteFirstNode();
        else if(i == length()-1) return deleteLastNode();
        else if(i >= length()) {
            System.err.println("IndexOutOfBound Exception");
            return null;
        }
        else {
            ListNode c = head;
            for(int j=1;j<i;j++) c = c.getLink(); // <-- Bug found
            c.setLink(c.getLink().getLink());
            return (E) c.getLink().getData();
        }
    }
    
    public void setFrontData(E e){
        if(head != null) head.setData(e);
        else System.err.println("EmptyLinkedList Exception");
    }
    
    public void setLastData(E e){
        if(head != null){
            ListNode c = head;
            while(c.getLink() != null) c = c.getLink();
            c.setData(e);
        } else System.err.println("EmptyLinkedList Exception");
    }
    
    public void set(E e, int i){
        if(i == 0) setFrontData(e);
        else if(i == length()-1) setLastData(e);
        else if(i >= length()) System.err.println("IndexOutOfBound Exception");
        else {
            ListNode c = head;
            for(int j=1;j<=i;j++) c = c.getLink();
            c.setData(e);
        }
    }
    
    public E getFrontData(){
        if(head != null) return (E) head.getData();
        else {
            System.err.println("EmptyLinkedList Exception");
            return null;
        }
    }
    
    public E getLastData(){
        if(head != null){
            ListNode c = head;
            while(c.getLink() != null) c = c.getLink();
            return (E) c.getData();
        } else {
            System.err.println("EmptyLinkedList Exception");
            return null;
        }
    }
    
    public E get(int i){
        if(i == 0) return getFrontData();
        else if(i == length()-1) return getLastData();
        else if(i >= length()){
            System.err.println("IndexOutOfBound Exception");
            return null;
        } else {
            ListNode c = head;
            for(int j=1;j<=i;j++) c = c.getLink();
            return (E) c.getData();
        }
    }
    
// Question 1c method
    public void addSortNode(E e){
        if(head == null) addFirstNode(e);
        else {
            if(length() == 1){
                if(e.compareTo(get(0)) >= 0) addLastNode(e);
                else addFirstNode(e);
            } else {
                for(int i = 0; i < length()-1; i++){
                    if(e.compareTo(get(i)) >= 0){
                        if(e.compareTo(get(i+1)) <= 0){
                            addNodeByPosition(e, i+1);
                            return;
                        }
                    } else {
                        addNodeByPosition(e, i);
                        return;
                    }
                }
                if(e.compareTo(get(length()-1)) >= 0) addLastNode(e);
                else addFirstNode(e);
            }
        }
    }
    
// Question 5 Iterator
    public Iterator<E> iterator(){
        return new LinkedListIterator();
    }
    
}
