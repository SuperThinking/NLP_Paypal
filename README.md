# NLP_Paypal
Correcting the spelling, opening the contractions and then finding the n-Grams. (Making a dictionary also //Pending).
testingtrie is used to only test the Trie Data Structure.
finalDictionary is the main file for executing Spellcheck, Contractions and nGramFinder.
(OpenNlp is the library used for POS and Tokenizing the words).

//Challenges
Adding a faster NER.
//NER is necessary as for example the sentence "Vishal is a goood boy" will be changed to "Visual is a good boy".
//If NER is used, it detects that Vishal is a name and we can neglect it for SpellCheck but it takes time to execute.
