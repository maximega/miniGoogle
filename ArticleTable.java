/*
 * ArticleTable.java
 * 
 * This is an unordered list of articles. Dumb, dumb, dumb. You will rewrite this as a hash table. 
 *
 * Author: Maxime Gavronsky (maximega@bu.edu)
 * Date: March 24, 2014
 */

public class ArticleTable {
    
    public static class Node {
        public Article data;
        public Node next;
        public Node next2;
        
        public Node(Article data, Node n, Node n2) {
            this.data = data;
            this.next = n;
            this.next2 = n2;
        }
        
        public Node(Article data) {
            this(data, null, null);
        }
    }
    
    public Node root = null;
    
    public Node place = null;
    
    public static int arraysize = 2521;
    
    public Node [] A = new Node[arraysize];
    
    public void initialize(Article[] A) {
        for(int i = 0; i < A.length; ++i) 
            insert(A[i]); 
    }
    //research.cs.vt.edu/AVresearch/hashing/strings.php
    int hash(String x) {
        char ch[];
        ch = x.toCharArray();
        int xlength = x.length();
        int i, sum;
        for (sum=0, i=0; i < x.length(); i++)
            sum += ch[i];
        return sum % arraysize;
    }
    
    
    //inserting article into hash table and into 'master linked list'
    public void insert(Article a) {
        A[hash(a.getTitle())] = insertHelper(a, A[hash(a.getTitle())]);
    } 
    
    //helper function for inserting an article
    private Node insertHelper(Article a, Node p) {
        if (p == null){ 
            p = new Node (a, null, root);
            root = p;
            return p;
        }
        else{
            p.next = insertHelper(a, p.next);
            return p;
        }
    }
    
    
    //looks up a title
    public Article lookup(String title) {
        Node n = lookupHelper(A[hash(title)],title); 
        if(n != null)
            return n.data; 
        return null; 
    }
    private  Node lookupHelper(Node t, String key) {
        if (t == null)
            return null;
        else if (key.compareTo(t.data.getTitle()) == 0) {
            return t;
        } else 
            return lookupHelper(t.next,key); 
    }
    
    public int length() {
        
        return length(root); 
    }
    
    private int length(Node t) {
        if(t == null)
            return 0;
        else
            return 1 + length(t.next); 
    }
    
    // Recursively reconstructs list without the key n in it
    public  Node delete2(String title, Node p){
        if (p == null)
            return p;
        else if (title.compareTo(p.data.getTitle()) == 0)
            return p.next2;
        else {
            p.next2 = delete2(title, p.next2);
            return p;
        }
        
    }
    
    //delets title from hash table
    public void delete(String title){
        A[hash(title)] = deleteHelper(title, A[hash(title)]);
        root = delete2(title, root);
    }
    //delete function helper 
    public  Node deleteHelper(String title, Node t) {
        if (t == null)                             // Case 1: tree is null
            return t;
        else if (title.compareTo(t.data.getTitle()) == 0){
            return t.next; 
        }
        else {
            t.next = deleteHelper(title, t.next); 
            return t;
        }
    }
    //finds the next node in the array that is not null
    public void reset() {
        place = root;
    }
    //returns true if the root has a next value in the array
    public boolean hasNext() {
        return(place != null);
    }
    //finds the next node in the array that is not empty
    public Article next(){
        if (place == null && place.next2 == null)
            return null;
        Article temp = place.data;
        place = place.next2;
        return temp;
    }
    public static void main(String[] args){
        ArticleTable h = new ArticleTable();
        Article i = new Article("cat", "some text");
        h.insert(i);
        h.insert(new Article("dog", "more text!"));
        h.insert(new Article("bird", "even more text!"));
        h.insert(new Article("hamster", "too much text!"));
        h.insert(new Article("parrot", "so much more text!"));
        h.insert(new Article("pig", "so much more text!"));
        h.insert(new Article("pet", "so much more text!"));
        h.insert(new Article("sheep", "so much more text!"));
        System.err.println("should print the following titles out: cat, dog, hamster, bird, pet, pig, parrot");
        System.out.println(h.lookup("cat"));
        System.out.println(h.lookup("dog"));
        System.out.println(h.lookup("hamster"));
        System.out.println(h.lookup("bird"));
        System.out.println(h.lookup("pet"));
        System.out.println(h.lookup("pig"));
        System.out.println(h.lookup("parrot"));
        
        h.delete("cat");
        h.delete("dog");
        h.delete("pet");
        h.delete("bird");
        System.err.println("should print out \nnull \nnull \nnull \nnull \nsheep \npig \nparrot \nhamster");
        System.out.println(h.lookup("cat"));
        System.out.println(h.lookup("dog"));
        System.out.println(h.lookup("pet"));
        System.out.println(h.lookup("bird"));
        h.reset();
        while(h.hasNext()) {
            Article a = h.next(); 
            System.out.println(a.getTitle());
        }
        
        ArticleTable s = new ArticleTable();
        s.insert(new Article("A", ""));
        System.err.println("Should print: A");
        System.out.println(s.lookup("A"));
        s.delete("s");
        System.err.println("Should print: A");
        System.out.println(s.lookup("A"));
        System.err.println("Should print: \nnull");
        System.out.println(s.lookup("B"));
        s.insert(new Article("B", ""));
        System.err.println("Should print: \nB \nA");
        s.reset();
        while(s.hasNext()) {
            Article a = s.next(); 
            System.out.println(a.getTitle());
        }
        
        s.delete("A");
        System.err.println("Should print: \nnull");
        System.out.println(s.lookup("A"));
        
        
    }
    
    
    
}
