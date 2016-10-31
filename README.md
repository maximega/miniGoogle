# miniGoogle

This was an assignment from my data structures and algorithms class at Boston University. This project first reads a text file of Articles. Each article has a header phrase such as, 'Cat' or 'Pet'. Each article has a body of text attached to it describing said Article. It's kind of like a Wikepedia entry. Open every file of .java type, and there will be a header comment describing the files' function and it's author (not every file in this repository was created by me). The most significant file in this repository is MiniGoogle.java.


When compiled and run, MiniGoogle.java will present the user with four options:

    1. add a new article
    2. remove an article
    3. search by exact article title
    4. search by phrase (list of keywords)
    
If option 1 is chosen, the program will add the user's input into a hash table of sorted Articles.

If option 2 is chosen, the program will remove said article from the hash table of sorted Articles.

If option 3 is chosen, the program will return the article and it's corresponding body of text if it exists in the Article hash table. Otherwise it will return 'article not found'

Lastly, and my personal favorite feature is the search by phrase option. This option will take the user's input, compare it against every article in search for the cosine similarity. (https://en.wikipedia.org/wiki/Cosine_similarity#Definition) It takes the cosine similarity of the UI and each article, and puts the result paired with the article into a max heap. What this option returns is the articles with the highest cosine similarity to the user's input. 

EX: 'Google' returns:'Google', 'Google Mail', and 'Search Engine' in that order!



