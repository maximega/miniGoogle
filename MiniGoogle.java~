/*
 * MiniGoogle.java
 *
 * A client program that uses the DatabaseIterator
 * and Article classes, along with additional data
 * structures, to allow a user to create, modify
 * and interact with a google type database.
 *
 * Author: Maxime Gavronsky (maximega@gmail.com)
 * Date: March 24, 2014
 */

import java.util.*;


public class MiniGoogle {
    
    
    private static final String [] blackList = { "the", "of", "and", "a", "to", "in", "is", 
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
    
    private static Article[] getArticleTable(DatabaseIterator db) {
        
        // count how many articles are in the directory
        int count = db.getNumArticles(); 
        
        // now create array
        Article[] list = new Article[count];
        for(int i = 0; i < count; ++i)
            list[i] = db.next();
        
        return list; 
    }
    // take a string, turn it into all lower case, and remove all characters except for 
    // letters, digits, and whitespace
    // (use Character.isWhitespace(..) and similar methods)
    private static String preprocess(String s) {
        char[] charsToRemove = { '.' , ',', ':', ';', '!', '?', '"', '\'', '/', '-', '(', ')', '~' };
        s = s.toLowerCase(); 
        for(int i = 0; i < charsToRemove.length; ++i)
        {
            String c = Character.toString(charsToRemove[i]);
            s = s.replace(c, "");   
        }
        return s;
    }
    // determine if the string s is a member of the blacklist (given at the bottom of 
    // this assignment); if so do no process it!
    private static boolean blacklisted(String s) {
        for(int j = 0; j < blackList.length; ++j){
            if(s.equals(blackList[j]))
                return true;
        }
        return false; 
    }
    // take two strings (e.g., the search phrase and the body of an article) and
    // preprocess each to remove all but letters, digits, and whitespace, and then
    // use the StringTokenizer class to extract each of the terms; create a TermFrequencyTable and 
    // insert each of the terms which is NOT in the blacklist into the table with its docNum 
    // (String s being document 0 and String t being document 1); 
    // finally extract the cosine similarity and return it.
    private static double getCosineSimilarity(String s, String t) {
        s = preprocess(s);
        t = preprocess(t);
        StringTokenizer token_s = new StringTokenizer(s);
        StringTokenizer token_t = new StringTokenizer(t);
        TermFrequencyTable S = new TermFrequencyTable(); 
        while (token_s.hasMoreTokens()) {
            String current_0 = token_s.nextToken();
            if(!blacklisted(current_0))
                S.insert((current_0), 0);
        }
        while(token_t.hasMoreTokens()){
            String current_1 = token_t.nextToken();
            if(!blacklisted(current_1))
                S.insert((current_1), 1);
        }
        return S.cosineSimilarity();
    }
    // Take an ArticleTable and search it for articles most similar to
    // the phrase; return a string response that includes the top three
    // as shown in the sample session shown below
    public static String  phraseSearch(String phrase, ArticleTable T) {
        MaxHeap F = new MaxHeap();
        T.reset();
        while(T.hasNext()){
            //System.out.println("here");
            Article a = T.next();
            a.putCS(getCosineSimilarity(phrase, a.getBody()));
            F.insert(a);
        }
        Article max_1 = F.getMax();
        Article max_2 = F.getMax();
        Article max_3 = F.getMax();
        if(max_1.getCS() < 0.001)
            return "There are no matching articles.";
        else if (max_2.getCS() < 0.001){
            String print = "Top Three matches: \nMatch 1 with cosinde similarity of " + max_1.getCS() +": \n" + max_1;
            return print;
        }
        else if (max_3.getCS() < 0.001){
            String print = "Top Three matches: \nMatch 1 with cosinde similarity of " + max_1.getCS() +": \n" + max_1 + " \n \n" + "\nMatch 2 with cosinde similarity of " + max_2.getCS() + ": \n" + max_2;
            return print;
        }    
        else{
            String print = "Top Three matches: \nMatch 1 with cosinde similarity of " + max_1.getCS() +": \n" + max_1 + " \n \n" + "\nMatch 2 with cosinde similarity of " + max_2.getCS() +": \n" + max_2 + " \n \n" + "\nMatch 3 with cosinde similarity of " + max_3.getCS() +": \n" + max_3;
            return print;
        }
    }
    
    
    private static DatabaseIterator setupDatabase(String path) {
        return new DatabaseIterator(path);
    }
    
    private static void addArticle(Scanner s, ArticleTable D) {
        System.out.println();
        System.out.println("Add an article");
        System.out.println("==============");
        
        System.out.print("Enter article title: ");
        String title = s.nextLine();
        
        System.out.println("You may now enter the body of the article.");
        System.out.println("Press return two times when you are done.");
        
        String body = "";
        String line = "";
        do {
            line = s.nextLine();
            body += line + "\n";
        } while (!line.equals(""));
        
        D.insert(new Article(title, body));
    }
    
    
    private static void removeArticle(Scanner s, ArticleTable D) {
        System.out.println();
        System.out.println("Remove an article");
        System.out.println("=================");
        
        System.out.print("Enter article title: ");
        String title = s.nextLine();
        
        
        D.delete(title);
    }
    
    
    private static void titleSearch(Scanner s, ArticleTable D) {
        System.out.println();
        System.out.println("Search by article title");
        System.out.println("=======================");
        
        System.out.print("Enter article title: ");
        String title = s.nextLine();
        
        Article a = D.lookup(title);
        if(a != null)
            System.out.println(a);
        else {
            System.out.println("Article not found!"); 
            return; 
        }
        
        System.out.println("Press return when finished reading.");
        s.nextLine();
    }
    
    private static void phraseSearcher(Scanner s, ArticleTable D) {
        System.out.println();
        System.out.println("Search by article title");
        System.out.println("=======================");
        
        System.out.print("Enter a search phrase: ");
        String title = s.nextLine();
        
        String a = phraseSearch(title, D);
        System.out.println(a);
        
        
        
        System.out.println("Press return when finished reading.");
        s.nextLine();
    }
    
    public static void main(String[] args) {
        Scanner user = new Scanner(System.in);
        
        String dbPath = "articles/";
        
        DatabaseIterator db = setupDatabase(dbPath);
        
        System.out.println("Read " + db.getNumArticles() + 
                           " articles from disk.");
        
        ArticleTable L = new ArticleTable(); 
        Article[] A = getArticleTable(db);
        for(int i = 0; i < A.length; ++i){ 
            L.insert(A[i]); 
        }
        
        int choice = -1;
        do {
            System.out.println();
            System.out.println("Welcome to Mini-Google!");
            System.out.println("=====================");
            System.out.println("Make a selection from the " +
                               "following options:");
            System.out.println();
            System.out.println("Manipulating the database");
            System.out.println("-------------------------");
            System.out.println("    1. add a new article");
            System.out.println("    2. remove an article");
            System.out.println("    3. search by exact article title");
            System.out.println("    4. search by phrase (list of keywords)");
            System.out.println();
            
            System.out.print("Enter a selection (1-4, or 0 to quit): ");
            
            choice = user.nextInt();
            user.nextLine();
            
            switch (choice) {
                case 0:
                    return;
                    
                case 1:
                    addArticle(user, L);
                    break;
                    
                case 2:
                    removeArticle(user, L);
                    break;
                    
                case 3:
                    titleSearch(user, L);
                    break;
                    
                case 4:
                    phraseSearcher(user, L);
                    break;
                    
                default:
                    break;
            }
            
            choice = -1;
            
        } while (choice < 0 || choice > 4);
        
    }
    
    
}
