import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.nio.charset.StandardCharsets;



public class spellCheck1
{
	static int max_change_classifier(String l, String main)
	{
		int i = 0;
		while(l.toCharArray()[i]==main.toCharArray()[i])
		{
			i++;
			if(i==l.length()||i==main.length())
			{break;}
		}
			
		return i;
	}
	
	static String max_change_classifier(List<String> l, String main)
	{
		String ret=main; int co=-1;
		for(String word:l)
		{
			int i = 0;
			while(word.toCharArray()[i]==main.toCharArray()[i])
			{
				i++;
				if(i==word.length()||i==main.length())
				{break;}
			}
			if(i>co)
			{
				co = i;
				ret = word;
			}
		}
		return ret;
	}
	
	static String bigram_check(List<String> s, String s1, String s2) throws FileNotFoundException, IOException
	{
		String fin="";
		List<String> l1 = new ArrayList<>();
		List<String> l2 = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("src/bigram_new.txt"))) 
		{
		    String line;
		    while ((line = br.readLine()) != null)
		    {
		       l1.add(line.split(" ")[0].toLowerCase());
		       l2.add(line.split(" ")[1].toLowerCase());
		    }
		}
		int max=-1;
		for(int i=0; i<s.size(); i++)
		{
			int c = 0;
			if(l1.contains(s1)&&l2.contains(s.get(i))){c++;}
			if(l2.contains(s2)&&l1.contains(s.get(i))){c++;}
			if(c>max)
			{
				max = c;
				fin = s.get(i);
				//break;
			}
		}
		
		return fin;
	}
	
	
	public static List<String> main(List<String> words) throws IOException, InterruptedException
	{
		trie_paypal tp = new trie_paypal();

		try (BufferedReader br = new BufferedReader(new FileReader("src/g_wordlist.txt"))) 
		{
		    String line;
		    while ((line = br.readLine()) != null)
		    {
		       tp.insert(line.toLowerCase());
		    }
		}
		
		List<String> sim_words = new ArrayList<String>();
		
		String punc = "~!@#$%^&*-=_+{}[]:';/.,<>?";
		for(int i=0; i<words.size(); i++)
		{
			String word = words.get(i);
			//System.out.println(sim_words);
			if(!tp.search(word))
			{
				for(String str:tp.testPrint(word, 2)){sim_words.add(str);}
				//System.out.println(bigram_check(sim_words, words.get(i-1), words.get(i+1)));
				//words.add(i, max_change_classifier(sim_words, word.toLowerCase()));
				//if(i==words.size()-1||i==0)
				//{
					words.remove(i);
					words.add(i, max_change_classifier(sim_words, word.toLowerCase()));
				//}
				/*else
				{
					String b_c = bigram_check(sim_words, words.get(i-1), words.get(i));
					if(b_c.length()==0)
					{
						words.remove(i);
						words.add(i, max_change_classifier(sim_words, word.toLowerCase()));
					}
					else
					{
						words.remove(i);
						words.add(i, b_c);
						//words.add(i, max_change_classifier(sim_words, word.toLowerCase()));
					}
				}
				*/
			}
			sim_words.clear();
		}
		return words;
	}
}
