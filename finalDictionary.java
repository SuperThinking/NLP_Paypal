import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class finalDictionary 
{
	public static void main(String args[]) throws Exception
	{
		//Dissapointed and forr have the wrong spelling.
		String s = "very dissapointed with the product, ordered forr the black but received the silver one, not as good as expected.";
		System.out.println(s);
		List<String> aft_contractions = Contractions.main(s.toLowerCase());
		System.out.println("\nAfter opening contractions:"+aft_contractions);
		
		List<String> pos = Contractions.posfind(aft_contractions);
		//System.out.println(pos);
		
		List<String> aft_spellcheck = spellCheck1.main(aft_contractions);
		System.out.println("\nAfter correcting spelling mistakes:"+aft_spellcheck);
		
		
		//Lemmatixation of all the words
		aft_contractions = Contractions.lemma(aft_spellcheck);
		
		Vector<String> v = new Vector(aft_contractions);
		System.out.println("\nBigrams before StopWords:"+ngramfinder.ngrams(v, 2));
		
		List<String> rem_stopWords = stop_words.main(aft_contractions);
		System.out.println("\nAfter removing StopWords:"+rem_stopWords);
		
		v.clear();
		v = new Vector(rem_stopWords);
		System.out.println("\nBigrams after StopWords:"+ngramfinder.ngrams(v, 2));
	}
}
