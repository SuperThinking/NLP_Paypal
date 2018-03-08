import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class testingtrie 
{	
	public static void main(String args[]) throws FileNotFoundException, IOException, InterruptedException
	{
		System.out.println("_TESTING_");
		trie_paypal tp = new trie_paypal();
		
		try (BufferedReader br = new BufferedReader(new FileReader("src/g_wordlist.txt"))) 
		{
		    String line;
		    while ((line = br.readLine()) != null)
		    {
		       tp.insert(line.toLowerCase());
		       //v.add(line.toLowerCase());
		    }
		}
		
		
		//Vector<String> words = tp.testPrint("vishal", 2);
		
		Vector<String> words = tp.testPrint("vishal", 2);
		System.out.println("\n");
		for(String w:words)
		{
			System.out.println(w);
		}
		
		/*
		long startTime = System.nanoTime();
		//Comparator<? super String> c = null;
		//v.sort(c);
		//for(i=0;i<v.size();i++){System.out.println(v.elementAt(i));}
		tp.print(tp.giveme());
		//System.out.println(tp.search("vishal"));
		//System.out.println(v.contains("vishal"));
		long endTime = System.nanoTime();

		long duration = (endTime - startTime)/10000;
		System.out.println(duration);
		*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		///////////SOME TESTS//////////////
		//insertion+print
		//199817, 185225, 196285 - trie 
		//161180, 165680, 164192 - vector
		
		//search + insertion + print
		//190215, 185969, 180175 - trie
		//140232, 156027, 166565 - vector
		
		//search + insertion
		//29579, 27691, 26379, 26138 - trie
		//10685, 10127, 10175, 12121 - vector
		
		//search
		//91, 66, 129 - trie
		//1147, 1140, 972 - vector
		
		///The above results mean that trie is faster for searching purposes as it has to scan the elements in the branch only///
	}
}
