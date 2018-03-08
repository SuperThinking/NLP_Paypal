import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

	////********************************************////
	//Function to find nGrams
	public class ngramfinder {
		
		static Vector<Vector<String>> ngrams(Vector<String> v, int n)
		{
			Vector<Vector<String>> x = new Vector<Vector<String>>();
			int a = 0;
			while(a!=v.size()-n+1)
			{
				Vector<String> temp = new Vector<String>();
				for(int i=a; i<a+n; i++)
				{
					temp.add(v.elementAt(i));
				}
				x.add(temp);
				a++;
			}
			return x;
		}
	////********************************************////
		static int minn(int a, int b, int c)
		{
			int rem=a;
			if(a>b)
			{
				rem = b;
			}
			if(rem>c)
			{
				rem = c;
			}
			return rem;
			
		}
	 ////********************************************////	
		static int edit_dist(String a1, String a2)
		{
			String s1[] = a1.split(""), s2[] = a2.split("");
			int a[][] = new int[s1.length+1][s2.length+1];

			for(int i=0; i<s1.length+1; i++)
			{
				for(int j=0; j<s2.length+1; j++)
				{
					if(i==0){a[i][j] = j;}
					else if(j==0){a[i][j] = i;}
					else
					{
						if(s2[j-1].contentEquals(s1[i-1]))
						{
							a[i][j] = a[i-1][j-1];
						}
						else
						{
							a[i][j] = minn(a[i][j-1], a[i-1][j], a[i-1][j-1])+1;
						}
					}
					//System.out.print(a[i][j]);
				}
				//System.out.println();
			}
			return a[s1.length][s2.length];
		}
	////********************************************////
		static String max_change_classifier(List<String> l, String main)
		{
			String ret=main; int co=-1;
			for(String word:l)
			{
				int i = 0;
				while(word.toCharArray()[i]==main.toCharArray()[i])
				{
					i++;
				}
				if(i>co)
				{
					co = i;
					ret = word;
				}
			}
			return ret;
		}
		
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
	////********************************************////
		
	///////////////////////Main Body of the Program////////////////////////
	public static void main(String[] args) throws FileNotFoundException, IOException 
	{
		///////////////////////////////////////////////////////////
		//////////////////All the English Word List///////////////
		Vector<String> word_list = new Vector<String>();
		try (BufferedReader br = new BufferedReader(new FileReader("src/g_wordlist.txt"))) 
		{
		    String line;
		    while ((line = br.readLine()) != null)
		    {
		       word_list.add(line.toLowerCase());
		    }
		}
		/////////REMOVE THE FIRST LINE OF COMMENT ONLY/////////////
		
		int n, count=0, i1=0; String s;
		s = "He'd gone home.";
		System.out.println("Given String : "+s);
		String arr[] = s.toLowerCase().split(" ");
		Vector<String> v = new Vector<String>();
		v.addAll(Arrays.asList(arr));
		
		String preps = "!@#$%^&*()_+=-[];',./<>?:";
		for(String temp:arr)
		{
			int change=-1;
			String new_Spelling = temp;
			for(String temp1:word_list)
			{
				if(edit_dist(temp, temp1)==0)
				{
					new_Spelling = temp;break;
				}
				else if(edit_dist(temp, temp1)<=2)
				{
					int m_change = max_change_classifier(temp1,temp);
					if(m_change>change)
					{
						new_Spelling = temp1;
						change = m_change; 
					}
				}
			}
			v.remove(i1);
			v.add(i1, new_Spelling);
			System.out.println(arr[i1]+"\t"+v.get(i1));
			i1++;
		}
	////********************************************////
		/*
			//POS Tagging
			InputStream modelIn = null;
			POSModel model = null;
			try {
			  modelIn = new FileInputStream("src/en-pos-maxent.zip");
			  model = new POSModel(modelIn);
			}
			catch (IOException e) {
			  // Model loading failed, handle the error
			  e.printStackTrace();
			}
			finally {
			  if (modelIn != null) {
			    try {
			      modelIn.close();
			    }
			    catch (IOException e) {
			    }
			  }
			}
			////
			POSTaggerME tagger = new POSTaggerME(model);  
			String pos_tags[] = tagger.tag(arr);
		////********************************************////
		/*
		List<String> con1 = new ArrayList<String>();
		List<List<String>> con2 = new ArrayList<List<String>>();
		List<String> con3 = new ArrayList<String>();
		//int deleteme = 0;
		try (BufferedReader br = new BufferedReader(new FileReader("src/contractions.txt"))) 
		{
		    String line;
		    while ((line = br.readLine()) != null)
		    {
		       //List<String> boom = Arrays.asList(line.toLowerCase().split("\t"));
		       //lem.add(boom);
		    	String tparts[] = line.split("\t");
		    	con1.add(tparts[0]);
		    	List<String> boom = Arrays.asList(tparts[1].split(" "));
		    	con2.add(boom);
		    	con3.add(tparts[2]);
		    	//System.out.println(con1.get(deleteme)+"\t"+con2.get(deleteme).get(0)+"\t"+con3.get(deleteme));
		    	//deleteme++;
		    }
		}
		
			
		////********************************************////	
		/*
		//Desi Tokenization
		int shit = 1; 
		String[] remL = ",.!() %;".split("");
		while(shit!=0)
		{
			int index = 0;
			shit = 0;
			for(index=0;index<v.size();index++)
			{
				String w = v.elementAt(index);
				String ch="";
				int pos = -1;
				for(int i=0; i<remL.length; i++)
				{
					if(w.contains(remL[i]))
					{
						ch = remL[i];
						pos = w.indexOf(ch);
						shit = 1;
						if(ch.contentEquals("(")||ch.contentEquals(")")||ch.contentEquals("."))
						{
							ch="\\"+ch;
						}
						
						v.removeElementAt(index);
						List<String> aa = Arrays.asList(w.split(ch));
						v.addAll(index, aa);
						break;
					}
					else if(w.contains("n't"))
					{
						List<String> aa = Arrays.asList(w.split("n't"));
						v.removeElementAt(index);
						v.addAll(index, aa);
						v.add(index+1, "not");
						break;
					}
					else if(w.contains("'ve"))
					{
						List<String> aa = Arrays.asList(w.split("'ve"));
						v.removeElementAt(index);
						v.addAll(index, aa);
						v.add(index+1, "have");
						break;
					}
					else if(w.contains("'re"))
					{
						List<String> aa = Arrays.asList(w.split("'re"));
						v.removeElementAt(index);
						v.addAll(index, aa);
						v.add(index+1, "are");
						break;
					}
					else if(w.contains("'"))
					{
						String temp[] = w.split("'");
						temp[1] = "'"+temp[1];
						String atlast = "";
						for(int temp1=0;temp1<con1.size()-1;temp1++)
						{
							//System.out.println(con2.get(temp1).get(0)+"\t"+pos_tags[index+1]);
							if(con1.get(temp1).contains(temp[1]) && con2.get(temp1).get(0).contains(pos_tags[index+1]))
							{
								atlast = con3.get(temp1);
								break;
							}
						}
						if(atlast.contentEquals(""))
						{
							atlast = temp[1];
						}
						v.removeElementAt(index);
						v.add(index, temp[0]);
						v.add(index+1, atlast);
						break;
					}
				
				}
			}
		}
		for(String printW:v){System.out.println(printW);}
		
		/*
		//Storing the list of all the root and main words in vector. 
		List<List<String>> lem = new ArrayList<List<String>>();
		//root will store the root of the word, main will store the given main string
		Vector<String> root = new Vector<String>(), main = new Vector<String>();
		try (BufferedReader br = new BufferedReader(new FileReader("src/lem.txt"))) 
		{
		    String line;
		    while ((line = br.readLine()) != null)
		    {
		       //List<String> boom = Arrays.asList(line.toLowerCase().split("\t"));
		       //lem.add(boom);
		    	root.add(line.split("\t")[0]);
		    	main.add(line.split("\t")[1]);
		    }
		}
		
		////********************************************////
		/*
		//Lemmatizing of the words by converting the main word to root word
		int i=0;
		for(String w:v)
		{
			if(main.contains(w))
			{
				v.set(i, root.elementAt(main.indexOf(w)));
			}
			System.out.print(v.get(i)+" ");
			i++;
		}
		
		////********************************************////
		
		//////////N Grams Printing////////////
		/*
		Vector<Vector<String>> ng = ngrams(v, 2);
		
		for(Vector<String> temp:ng)
		{
			for(String temp1:temp)
			{
				System.out.print(temp1+" ");
			}
			System.out.print("\n");
		}
		*/
		
	}
	

}
