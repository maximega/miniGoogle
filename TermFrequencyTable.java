// Maxime Gavronsky

// maximega@bu.edu

// stores the words from two Strings (i.e., documents) in such a way that you can easily 
// calculate the "cosine" similarity of the two.

public class TermFrequencyTable{
    
    public static int arraysize = 107;
    
    public Node [] A = new Node[arraysize];
    
    public Node root = null;
    
    public class Node {
        
        
        int[] termFreq = new int[2]; 
        public String term;
        public Node next;
        
        public Node(String term, int[] freq, Node n) {
            this.termFreq = freq;
            this.term = term;
            this.next = n;
        }
    } 
    
    
    private final String [] blackList = { "the", "of", "and", "a", "to", "in", "is", 
        "you", "that", "it", "he", "was", "for", "on", "are", "as", "with", 
        "his", "they", "i", "at", "be", "this", "have", "from", "or", "one", 
        "had", "by", "word", "but", "not", "what", "all", "were", "we", "when", 
        "your", "can", "said", "there", "use", "an", "each", "which", "she", 
        "do", "how", "their", "if", "will", "up", "other", "about", "out", "many", 
        "then", "them", "these", "so", "some", "her", "would", "make", "like", 
        "him", "into", "time", "has", "look", "two", "more", "write", "go", "see", 
        "number", "no", "way", "could", "people",  "my", "than", "first", "water", 
        "been", "call", "who", "oil", "its", "now", "find", "long", "down", "day", 
        "did", "get", "come", "made", "may", "part" };
    
    private char[] charsToRemove = { '.' , ',', ':', ';', '!', '?', '"', '\'', '/', '-', '(', ')', '~' };
    
    
    //inserting string term into the hash table and incrementing the proper termFrew value
    public void insert(String term, int docNum) {
        String []master = remove(term);
        for(int i = 0; i < master.length; i++)
        {
            if(master[i] == "")
                continue;
            else
                A[hash(master[i])] = insertHelper(master[i], docNum, A[hash(master[i])]);
        }
        
    } 
    //worked with Selina Gerosa to obtain a recursive solution to insertion
    //helper function for inserting a term
    private Node insertHelper(String term, int docNum , Node p) {
        int[] termFreq = new int [2];
        if (p == null){
            termFreq[docNum] = 1;  // insert 1 to the docNum in termFreq
            p = new Node (term, termFreq, null);
            return p;
        }
        //if term is already in the table then the proper termFreq value will be changed
        //without inserting the element
        else if (term.compareTo(p.term) == 0) { 
            p.termFreq[docNum] += 1; //add 1 to the docNum indew in termFreq
            return p;
        }
        else{  
            p.next = insertHelper(term, docNum, p.next);
            return p;
        }
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
    //removes puncuation, makes it lower case and removes black listed words
    private String[] remove(String term){
        term = term.toLowerCase();
        
        for(int i = 0; i < charsToRemove.length; ++i)
        {
            String s = Character.toString(charsToRemove[i]);
            term = term.replace(s, "");   
        }   
        
        String [] arrayTerm = term.split("\\s+");
        
        for(int j = 0; j < blackList.length; ++j){
            for( int z = 0; z < arrayTerm.length; ++z){
                if (arrayTerm[z].equals(blackList[j]))
                    arrayTerm[z] = "";
            }
        }
        
        return arrayTerm;
    }
    //finds the cosine Similarity of the two documnets
    public double cosineSimilarity() {
        int aa = 0;
        int bb = 0; 
        int ab = 0; 
         for(int i = 0; i < arraysize; i++){
             if (A[i] != null){
                 aa += dotProductAA(A[i]);
                 bb += dotProductBB(A[i]);
                 ab += dotProductAB(A[i]);
             }
         }
        double similarity = ab / (Math.sqrt(aa) * Math.sqrt(bb));
        return similarity;
    }
    //helps interate through the hash table for doc 0
    private int dotProductAA(Node p){
        int product = 0;
        while (p!=null){
            product += (p.termFreq[0] * p.termFreq[0]);
            p = p.next;
        }
        return product;    
    }
    //helps interate through the hash table for doc 1
    private int dotProductBB(Node p){
         int product = 0;
        while (p!=null){
            product += (p.termFreq[1] * p.termFreq[1]);
            p = p.next;
        }
        return product;   
    }
    //helps interate through the hash table for doc 0 and doc1
    private int dotProductAB(Node p){
        int product = 0;
        while (p!=null){
            product += (p.termFreq[0] * p.termFreq[1]);
            p = p.next;
        }
        return product;   
            
    }
    //printing method for the hash table and the term frequency 
    public void print(){
        String printout = "";
        for(int i = 0; i < arraysize; i++){
            if (A[i] != null)
                printHelper(printout, A[i]);
         }
    }
    //helper method for printing
    private void printHelper(String printout, Node p){
        while (p != null){
            printout = (p.term + ": is present " + p.termFreq[0] + " times in Document 0 and " + p.termFreq[1] + " times in Document 1");
            System.err.println(printout);
            p = p.next;
        }
    }
    public static void main(String args[]){
        TermFrequencyTable T = new TermFrequencyTable();
        String doc0 = "hello is this a test test test, just just checking";
        String doc1 = "that this and the other thing, TESTING test! just checking?";
        T.insert(doc0, 0);
        T.insert(doc1, 1);
        System.out.println("should print testing: \nis present 0 times in Document 0 and 1 times in Document 1 \nhello: is present 1 times in Document 0 and 0 times in Document 1 \ntest: is present 3 times in Document 0 and 1 times in Document 1 \nthing: is present 0 times in Document 0 and 1 times in Document 1 \njust: is present 2 times in Document 0 and 1 times in Document 1 \nchecking: is present 1 times in Document 0 and 1 times in Document 1");
        T.print();
        TermFrequencyTable F = new TermFrequencyTable();
        String doc2 = "A A B B";
        String doc3 = "A B";
        F.insert(doc2, 0);
        F.insert(doc3, 1);
        System.out.println("\nShould print: \nb: is present 2 times in Document 0 and 1 times in Document 1");
        F.print();
        System.out.println("\nShould print: \n1.0");
        System.out.println(F.cosineSimilarity());
        TermFrequencyTable J = new TermFrequencyTable();
        String doc4 = "A B";
        String doc5 = "C D";
        J.insert(doc4, 0);
        J.insert(doc5, 1);
        System.out.println("\nShould print: \nb: is present 1 times in Document 0 and 0 times in Document 1 \nc: is present 0 times in Document 0 and 1 times in Document 1 \nd: is present 0 times in Document 0 and 1 times in Document 1");
        J.print();
        System.out.println("\nShould print: \n0.0");
        System.out.println(J.cosineSimilarity());
        
        TermFrequencyTable P = new TermFrequencyTable();
        String doc6 = "CS112 HW10";
        String doc7 = "CS112 HW10 HW10";
        P.insert(doc6, 0);
        P.insert(doc7, 1);
        System.out.println("\nShould print: \ncs112: is present 1 times in Document 0 and 1 times in Document 1 \nhw10: is present 1 times in Document 0 and 2 times in Document 1");
        P.print();
        System.out.println("\nShould print: \n0.9487");
        System.out.printf("%.4f", P.cosineSimilarity());
        
        
    }
}