/*import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.CoreMap;

public class Contractions {

	private static String[] getContraction(CoreLabel prevToken, CoreLabel token, CoreLabel nextToken) {
		String prevWord = prevToken.get(TextAnnotation.class);
		String prevPos = prevToken.get(PartOfSpeechAnnotation.class);
		String word = token.get(TextAnnotation.class);
		String pos = token.get(PartOfSpeechAnnotation.class);
		String nextWord = nextToken.get(TextAnnotation.class);
		String nextPos = nextToken.get(PartOfSpeechAnnotation.class);

		String[] result;
		if (word.equalsIgnoreCase("'ll")) {
			result = new String [] {"1", "will"};
		} else if (word.equalsIgnoreCase("'re")) {
			result = new String [] {"1", "are"};
		} else if (word.equalsIgnoreCase("'m")) {
			result = new String [] {"1", "am"};
		} else if (word.equalsIgnoreCase("o'clock")) {
			result = new String [] {"1", "of the clock"};
		} else if (word.equalsIgnoreCase("ma'am")) {
			result = new String [] {"1", "madam"};
		} else if (word.equalsIgnoreCase("ne'er")) {
			result = new String [] {"1", "never"};
		} else if (word.equalsIgnoreCase("o'er")) {
			result = new String [] {"1", "over"};
		} else if (word.equalsIgnoreCase("n't")) {
			result = new String [] {"1", "not"};
		} else if (nextPos.equals("VBN") && word.equalsIgnoreCase("'d")) {
			result = new String [] {"1", "had"};
		} else if (word.equalsIgnoreCase("'d")) {
			result = new String [] {"1", "would"};
		} else if (word.equalsIgnoreCase("'t") && nextWord.equalsIgnoreCase("is")) {
			result = new String [] {"1", "it"};
		} else if (word.equalsIgnoreCase("'t") && nextWord.equalsIgnoreCase("was")) {
			result = new String [] {"1", "it"};
		} else if (word.equalsIgnoreCase("y'") && nextWord.equalsIgnoreCase("all")) {
			result = new String [] {"1", "you"};
		} else if (prevWord.equalsIgnoreCase("let") && word.equalsIgnoreCase("'s")) {
			result = new String [] {"1", "us"};
		} else if (prevWord.equalsIgnoreCase("gon") && word.equalsIgnoreCase("na")) {
			result = new String [] {"2", "going", "to"};
		} else if (prevWord.equalsIgnoreCase("got") && word.equalsIgnoreCase("ta")) {
			result = new String [] {"2", "got", "to"};
		} else if (prevWord.equalsIgnoreCase("ca") && word.equalsIgnoreCase("n't")) {
			result = new String [] {"2", "can", "not"};
		} else if (prevWord.equalsIgnoreCase("wo") && word.equalsIgnoreCase("n't")) {
			result = new String [] {"2", "will", "not"};
		} else if (prevWord.equalsIgnoreCase("y") && word.equalsIgnoreCase("'all")) {
			result = new String [] {"2", "you", "all"};
		} else {
			result = new String [] {"0", word};
		}
		return result;
	}
	
	public static List<String> fixContractions(Annotation annotation) {
		List<String> result = new ArrayList<String>();
		List<CoreMap> sentences = annotation.get(SentencesAnnotation.class);
		
		for (CoreMap sentence : sentences) {
			List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);
			int tokensLen = tokens.size();
			for (int i = 1; i < tokensLen-1; i++) {
				CoreLabel token = tokens.get(i); 				
				String[] contraction = getContraction(tokens.get(i-1), token, tokens.get(i+1));

				if (contraction[0].equals("1")) {
					token.setValue(contraction[1]);
					tokens.set(i, token);
				} else if (contraction[0].equals("2")) {
					token.setValue(contraction[2]);
					tokens.set(i, token);
					
					token = tokens.get(i-1);
					token.setValue(contraction[1]);
					tokens.set(i-1, token);
				}
			}
			for (CoreLabel token : tokens)
				result.add(token.value());
		}
		return result;
	}

	public static List<String> main(String arg) throws IOException {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma");

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		Annotation annotation = new Annotation(arg);
		//Annotation annotation = new Annotation("Didn't you do that work y'all bitches. Hi all.");
		//PrintWriter out = new PrintWriter(System.out);
		//pipeline.prettyPrint(new101, out);
		//System.out.println(newString);

		pipeline.annotate(annotation);
		System.out.println(annotation);
		
		List<String> newString = fixContractions(annotation);
		System.out.println(newString);
		Sentence sent = new Sentence(spellCheck.main(newString));
		return sent.lemmas();
		
		//return newString;
	}

}*/


import java.io.*;
import java.util.*;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.lemmatizer.Lemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public class Contractions {

	private static String[] getContraction(String word, String pos, String nextWord, String nextPos) {
		String[] result;
		if (word.equalsIgnoreCase("'ll")) {
			result = new String [] {"1", "will"};
		} else if (word.equalsIgnoreCase("'re")) {
			result = new String [] {"1", "are"};
		} else if (word.equalsIgnoreCase("'ve")) {
			result = new String [] {"1", "have"};
		} else if (word.equalsIgnoreCase("gonna")) {
			result = new String [] {"1", "going", "to"};
		} else if (word.equalsIgnoreCase("gotta")) {
			result = new String [] {"1", "got", "to"};
		} else if (word.equalsIgnoreCase("'m")) {
			result = new String [] {"1", "am"};
		} else if (word.equalsIgnoreCase("o'clock")) {
			result = new String [] {"1", "of", "the", "clock"};
		} else if (word.equalsIgnoreCase("ma'am")) {
			result = new String [] {"1", "madam"};
		} else if (word.equalsIgnoreCase("'tis")) {
			result = new String [] {"1", "it", "is"};
		} else if (word.equalsIgnoreCase("'twas")) {
			result = new String [] {"1", "it", "was"};
		} else if (word.equalsIgnoreCase("'s") && nextPos.equalsIgnoreCase("VBG")) {
			result = new String [] {"1", "is"};
		} else if (word.equalsIgnoreCase("'s") && nextPos.equalsIgnoreCase("DT")) {
			result = new String [] {"1", "has"};
		} else if (word.equalsIgnoreCase("'s") && nextPos.equalsIgnoreCase("JJ")) {
			result = new String [] {"1", "is"};
		} else if (word.equalsIgnoreCase("'s") && nextPos.startsWith("JJ")) {
			result = new String [] {"1", "is"};
		} else if (word.equalsIgnoreCase("'s") && nextPos.startsWith("RB")) {
			result = new String [] {"1", "is"};
		} else if (word.equalsIgnoreCase("'s") && nextPos.startsWith("VB") && (nextWord.toLowerCase()).endsWith("ing")) {
			result = new String [] {"1", "is"};
		} else if (word.equalsIgnoreCase("'d") && nextPos.equals("VBN")) {
			result = new String [] {"1", "had"};
		} else if (word.equalsIgnoreCase("'d")) {
			result = new String [] {"1", "would"};
		} else if (word.equalsIgnoreCase("ne") && nextWord.equalsIgnoreCase("'er")) {
			result = new String [] {"2", "never"};
		} else if (word.equalsIgnoreCase("o") && nextWord.equalsIgnoreCase("'er")) {
			result = new String [] {"2", "over"};
		} else if (word.equalsIgnoreCase("y") && nextWord.equalsIgnoreCase("'all")) {
			result = new String [] {"2", "you", "all"};
		} else if (word.equalsIgnoreCase("let") && nextWord.equalsIgnoreCase("'s")) {
			result = new String [] {"2", "let", "us"};
		} else if (word.equalsIgnoreCase("ai") && nextWord.equalsIgnoreCase("n't")) {
			result = new String [] {"2", "is", "not"};
		} else if (word.equalsIgnoreCase("ca") && nextWord.equalsIgnoreCase("n't")) {
			result = new String [] {"2", "can", "not"};
		} else if (word.equalsIgnoreCase("wo") && nextWord.equalsIgnoreCase("n't")) {
			result = new String [] {"2", "will", "not"};
		} else if (word.equalsIgnoreCase("n't")) {
			result = new String [] {"1", "not"};
		} else {
			result = new String [] {"0", word};
		}
		return result;
	}
	
	private static String[] getContraction(String word, String pos) {
		String[] result;
		if (word.equalsIgnoreCase("'ll")) {
			result = new String [] {"1", "will"};
		} else if (word.equalsIgnoreCase("'re")) {
			result = new String [] {"1", "are"};
		} else if (word.equalsIgnoreCase("'ve")) {
			result = new String [] {"1", "have"};
		} else if (word.equalsIgnoreCase("gonna")) {
			result = new String [] {"1", "going", "to"};
		} else if (word.equalsIgnoreCase("gotta")) {
			result = new String [] {"1", "got", "to"};
		} else if (word.equalsIgnoreCase("'m")) {
			result = new String [] {"1", "am"};
		} else if (word.equalsIgnoreCase("o'clock")) {
			result = new String [] {"1", "of", "the", "clock"};
		} else if (word.equalsIgnoreCase("ma'am")) {
			result = new String [] {"1", "madam"};
		} else if (word.equalsIgnoreCase("'tis")) {
			result = new String [] {"1", "it", "is"};
		} else if (word.equalsIgnoreCase("'twas")) {
			result = new String [] {"1", "it", "was"};
		} else if (word.equalsIgnoreCase("'d")) {
			result = new String [] {"1", "would"};
		} else if (word.equalsIgnoreCase("n't")) {
			result = new String [] {"1", "not"};
		} else {
			result = new String [] {"0", word};
		}
		return result;
	}
	
	public static List<String> fixContractions(String sentence) throws Exception {
		// Tokenize the string
		InputStream inputStreamTokenizer = new FileInputStream("src/en-token.zip");
		TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer);  
		TokenizerME tokenizer = new TokenizerME(tokenModel); 
		String tokens[] = tokenizer.tokenize(sentence);

		// get the pos tags for all tokens
		InputStream posModelIn = new FileInputStream("src/en-pos-maxent.zip");
		POSModel posModel = new POSModel(posModelIn);
		POSTaggerME posTagger = new POSTaggerME(posModel);
		String tags[] = posTagger.tag(tokens);
		
		/*
		for(int k = 0; k < tags.length; k++)
			System.out.println(tokens[k] + "\t" + tags[k]);
		*/
		
		List<String> newTokens = new ArrayList<String>();
		int i, j, contractionLen, len = tokens.length;
		for (i = 0; i < len-1; i++) {
			String[] contraction = getContraction(tokens[i], tags[i], tokens[i+1], tags[i+1]);

			if (contraction[0].equals("2"))
				i++;

			contractionLen = contraction.length;
			for (j = 1; j < contractionLen; j++)
				newTokens.add(contraction[j]);
		}
		if (i == len-1) {
			String[] contraction = getContraction(tokens[len-1], tags[len-1]);
			contractionLen = contraction.length;
			for (j = 1; j < contractionLen; j++)
				newTokens.add(contraction[j]);
		}
		return newTokens;
	}

	public static List<String> posfind(List<String> s1) throws Exception 
	{
		String[] tokens =new String[s1.size()];

		for(int i=0; i<s1.size(); i++)
		{
			tokens[i] = s1.get(i).trim();
		}
		
		InputStream posModelIn = new FileInputStream("src/en-pos-maxent.zip");
		POSModel posModel = new POSModel(posModelIn);
		POSTaggerME posTagger = new POSTaggerME(posModel);
		String tags[] = posTagger.tag(tokens);

		
		InputStream dictLemmatizer = new FileInputStream("src/en-lemmatizer.dict.txt");
		DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
		String[] lemmas = lemmatizer.lemmatize(tokens, tags);

		List<String> lem = new ArrayList<>();
		for(int i = 0; i < tokens.length; i++)
		{
			lem.add(lemmas[i]);
		}
		return lem;
	
	}
	
	public static List<String> lemma(List<String> words) throws InvalidFormatException, IOException
	{
		String wds[] = new String[words.size()];
		for(int i=0; i<words.size(); i++){wds[i] = words.get(i);}
		
		InputStream posModelIn = new FileInputStream("src/en-pos-maxent.zip");
		POSModel posModel = new POSModel(posModelIn);
		POSTaggerME posTagger = new POSTaggerME(posModel);
		String tags[] = posTagger.tag(wds);

		
		InputStream dictLemmatizer = new FileInputStream("src/en-lemmatizer.dict.txt");
		DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
		String[] lemmas = lemmatizer.lemmatize(wds, tags);

		List<String> lem = new ArrayList<>();
		for(int i = 0; i<wds.length; i++)
		{
			if(lemmas[i].equals("O"))
			{
				lem.add(words.get(i));
			}
			else
			{
				lem.add(lemmas[i]);
			}
			
		}
		return lem;

	}
	
	public static List<String> main(String arg) throws Exception {
		//String sentence = "Didn't you do that work y'all bitches. Hi all."; 

		/*		
		Today is 19-0-2018. Mike and Michael are senior programming managers. Rama is a clerk both are working at Turialspoint
		Aryan is a good boy. He is from village area.
		6 o'clock gonna gotta I'll I'd Aryan's ne'er o'er you're y'all 'tis 'twas I'm good
		Didn't you do that work y'all bitches. Hi all.
		*/
		
		List<String> newTokens = fixContractions(arg);
		List<String> finalTokens = fixContractions(arg);
		String punc = "~!@#$%^&*-=_+{}[]:';/.,<>?";
		
		for(int i=0; i<newTokens.size(); i++)
		{
			if(punc.contains(newTokens.get(i).toString()))
			{
				newTokens.remove(i);
			}
		}
		
		return newTokens;
		
	}

}

