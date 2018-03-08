import java.util.Stack;
import java.util.Vector;

public class trie_paypal {

    private TrieNode root;
    public trie_paypal() 
    {
        root = new TrieNode();
    }

    //Insertion in Trie
    public void insert(String word) 
    {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                node = new TrieNode();
                current.children.put(ch, node);
            }
            current = node;
        }
        current.endOfWord = true;
    }

    
    //Searching in Trie
    public boolean search(String word) 
    {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.endOfWord;
    }
    
  
    int stop=0;
    
   //Returns the TrieNode
    public TrieNode giveme()
    {
    	return root;
    }
    
    /////////////////////////////////////////////////
    //Helps in traversing the trie
    String s = "", s1="", temp="";int c=0, stay=1, totCount=0, p=0;
    /////////////////////////////////////////////////
    
    /////////////////////////////////////////////////
    //Helps in founding the Edit Distance//
    Stack<Vector<Integer>> stack = new Stack<Vector<Integer>>();
    Vector<String> storeString = new Vector<String>();
    int where=1;
    
    
    /*Using Math.min inspite of this function
     * public int minimumof(int a, int b, int c)
    {
    	int m = b;
    	if(a<b){m = a;}
    	if(c<m){return c;}
    	return m;
    }*/
    /////////////////////////////////////////////////
    
    public void print(TrieNode rooot, int l1, int l2, String word) throws InterruptedException
    {
    	for(Character h : rooot.children.keySet())
    	{
    		s=s+h;
    		//////////////////////////////////////////////
    		//////////Adds a new row in the Stack/////////
    		
    		Vector<Integer> temp1 = new Vector<Integer>();
    		temp1.add(where);
    		for(int i=1; i<l1+1; i++)
    		{
    			if(h.equals(word.charAt(i-1)))
    			{
    				temp1.add(stack.elementAt(where-1).elementAt(i-1));
    			}
    			else
    			{
    				int bef = temp1.elementAt(i-1);
    				//temp1.add(minimumof(stack.elementAt(where-1).elementAt(i-1), bef, stack.elementAt(where-1).elementAt(i))+1);
    				temp1.add(Math.min(stack.elementAt(where-1).elementAt(i-1), Math.min(stack.elementAt(where-1).elementAt(i), bef))+1);
    			}
    		}
    		where++;
    		stack.push(temp1);
    		
    		/////////////////////////////////////////////
    		if(rooot.children.get(h).endOfWord==true&&temp1.lastElement()<=l2)
    		{
    			///////The Whole Word////////
    			//System.out.println(s);
    			storeString.add(s);
    		}
    		
    		print(rooot.children.get(h), l1, l2, word);
    		
    		if(c==0){s1 = h.toString();c++;}
    	}
    	c=0;
    	temp="";p=s.length()-1;
   		for(int i=s.length()-1; i>-1; i--)
   		{
   			if(s1.equals(s.charAt(i)))
   			{
   				p = i;
   				break;
   			}
   		}
   		for(int i=0; i<s.length(); i++)
   		{
   			if(p!=i)
   			{
   				temp = temp+s.charAt(i);
   			}
   		}
   		stack.pop();
   		where--;
   		s = temp;
    }
    
    public Vector<String> testPrint(String word, int ed) throws InterruptedException
    {	
    	storeString.removeAllElements();
    	where = 1;
    	stack.removeAllElements();
    	Vector<Integer> k = new Vector<Integer>();
		for(int i=0; i<word.length()+1; i++)
		{
			k.add(i);
		}
		stack.add(k);
    	
    	print(root, word.length(), ed, word);
    	
    	return storeString;
    }
}