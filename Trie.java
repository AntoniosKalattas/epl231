
import java.util.Scanner;

public class Trie {
    TrieNode node;
    static int memory=0;
    static int totalObjs=0;
    static int tt=0;
    static int nullPointers=0;
    public Trie(){
        this.node = new TrieNode();
    }
    	public class TrieNode{
        // trieNode array 4 bytes refenrecen *26 length.
        private TrieNode childrenNodes[];
        //int 7x4 ints.
            //136
        public final int numberOfLetters = 26;
        private int wordLength=0;
        private int importance=1;
        //// Gia eksisoropoish mnimis me RB version.

        public TrieNode(){
            childrenNodes = new TrieNode[numberOfLetters];
            for(int i=0;i<numberOfLetters;i++)
                childrenNodes[i]=null;
        }

        public void insert(String word, int i){
        	if(word==null)
        		return;
            if(i!=word.length()){
                int letter = word.charAt(i) - 'a';
                char currentChar = word.charAt(i);
                if (currentChar < 'a' || currentChar > 'z') {
                    throw new IllegalArgumentException("Invalid character in word: " + currentChar);
                }
                if(this.childrenNodes[letter]==null)
                {
                    this.childrenNodes[letter] = new TrieNode();
                    totalObjs++;
                }
                this.childrenNodes[letter].insert(word, i + 1);
            }
            else{
                if(wordLength!=0)
                    importance++;
                this.wordLength = i;
            }
        }

        public boolean search(String word, int i){
            if(i==word.length() && this.wordLength!=0)
                return true;
            else if(i==word.length() && this.wordLength==0)
                return false;
            else if(this.childrenNodes[word.charAt(i)-'a']!=null)
                return  this.childrenNodes[word.charAt(i)-'a'].search(word, ++i);
            else
                return false;
        }

        public void display(String prefix){
            if(this.wordLength!=0)
                System.out.println(prefix+" (importance: "+importance+")");
            for(int i=0;i<numberOfLetters;i++)
                if(childrenNodes[i]!=null)
                    childrenNodes[i].display(prefix+(char)(i+'a'));
        }
        public void calculateObjs(){
            tt++;
            for(int i=0;i<26;i++){
                if(childrenNodes[i]!=null){childrenNodes[i].calculateObjs();}
                else{nullPointers++;}
            }
        }
    }


    public void insert(String word){
        node.insert(filter((word).toLowerCase()), 0);
    }
    public double calculateObj(){
        node.calculateObjs();
        double totalMemory = (totalObjs*136)+(nullPointers*4);
        System.out.printf( "(bytes) Number of Nodes: %d(+1)\t \t \tTotal size: %.2f \t\t\t Total Null Pointer: %d \n",(totalObjs)-1,totalMemory, nullPointers);

        return (totalMemory)/(1024*1024);
    }
    public boolean search(String lookingFor){
        return node.search(lookingFor.toLowerCase(), 0);
    }

    public void display(){
        node.display("");
    }
    
    
    public String filter(String word) {
        String newWord = "";
  
        for(int i = 0; i < word.length(); ++i) {
           if ((word.charAt(i)=='.' || word.charAt(i)=='-') && i != word.length() - 1)
              return null;  
           if (word.charAt(i)>='a' && word.charAt(i)<='z') {
              newWord = newWord + word.charAt(i);
           }
           
        }
  
        return newWord;
     }
    public int calculateMemory(){
        return totalObjs*32;
    }
    public static double bytesToMegabytes(double bytes) {
        return bytes / (1024.0 * 1024.0);
    }
    public static void main(String[] args){
        Trie tr = new Trie();
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()){
            tr.insert(scan.next());
        }


        //tr.insert("A");
        //tr.insert("B");
        //tr.insert("C");
        //tr.insert("D");
        //tr.insert("E");
        //tr.insert("F");
        //tr.insert("G");
        //tr.insert("X");
        //tr.insert("O");
        //tr.insert("AntoniosKalattas");
//
        System.out.println(tr.calculateObj());
        //System.out.println(nullPointers + " " + totalObjs);
        
    }
}