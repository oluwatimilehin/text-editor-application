package textgen;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An implementation of the MTG interface that uses a list of lists.
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

    public List<ListNode> getWordList() {
        return wordList;
    }

    // The list of words with their next words
    private List<ListNode> wordList;

    private List<String> currentWords = new ArrayList<>();

    // The starting "word"
    private String starter;

    // The random number generator
    private Random rnGenerator;


    public MarkovTextGeneratorLoL(Random generator) {
        wordList = new LinkedList<ListNode>();
        starter = "";
        rnGenerator = generator;
    }


    /**
     * This method returns a list of the strings that matched a particular pattern
     *
     * @param text    The string to be searched
     * @param pattern The regex pattern to be matched
     * @return
     */
    private static List<String> getTokens(String text, String pattern) {
        Pattern regPattern = Pattern.compile(pattern);
        Matcher m = regPattern.matcher(text);
        List<String> tokens = new ArrayList<String>();

        while (m.find()) {
            tokens.add(m.group());
        }

        return tokens;
    }

    /**
     * This is a minimal set of tests.  Note that it can be difficult
     * to test methods/classes with randomized behavior.
     *
     * @param args
     */
    public static void main(String[] args) {
        // feed the generator a fixed random value for repeatable behavior
        MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        System.out.println(textString);
        gen.train(textString);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
        String textString2 = "You say yes, I say no, " +
                "You say stop, and I say go, go, go, " +
                "Oh no. You say goodbye and I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "I say high, you say low, " +
                "You say why, and I say I don't know. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "Why, why, why, why, why, why, " +
                "Do you say goodbye. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "You say yes, I say no, " +
                "You say stop and I say go, go, go. " +
                "Oh, oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello,";
        System.out.println(textString2);
        gen.retrain(textString2);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
    }

    /**
     * Train the generator by adding the sourceText
     */
    @Override
    public void train(String sourceText) {
        List<String> words = null;

        if (!sourceText.isEmpty()) {
            words = getTokens(sourceText, "([^\\s]+)");
            starter = words.get(0);

            String currentWord;
            String nextWord;

            for (int i = 0; i < words.size(); i++) {
                currentWord = words.get(i);

                if (!(i == words.size() - 1)) { //If it's not the last word
                    nextWord = words.get(i + 1);
                } else {
                    nextWord = starter;
                }

                ListNode node = new ListNode(currentWord);
                int index = 0;

                if (!wordList.contains(node)) {
                    node.addNextWord(nextWord);
                    wordList.add(node);
                } else {
                    for (ListNode e : wordList) {
                        if (e.equals(node)) {
                            wordList.get(index).addNextWord(nextWord);
                        }
                        index++;
                    }
                }

            }
        } else{
            System.out.println("Empty string");
        }

    }

    /**
     * Generate the number of words requested.
     */
    @Override
    public String generateText(int numWords) {
       String output = "";
       String nextWord = "";
       int index = 0;
        ListNode currentNode = wordList.get(0);
        while (true){
            if(index < numWords) {
                output += " " + currentNode.getWord();
                index++;
            }
            nextWord = currentNode.getRandomNextWord(rnGenerator);
            if(index < numWords) {
                output += " " + nextWord;
                index++;
            }

            int loopIndex = 0;

            ListNode nextWordNode = new ListNode(nextWord);
            for(ListNode node: wordList){
                if (nextWordNode.equals(node)){
                    nextWord = node.getRandomNextWord(rnGenerator);
                    if(index < numWords) {
                        output += " " + nextWord;
                        currentNode = wordList.get(loopIndex);
                        index ++;
                    }

                }
                loopIndex++;
            }

            if(index == numWords){
                break;
            }
        }

        return output;
    }

    // Can be helpful for debugging
    @Override
    public String toString() {
        String toReturn = "";
        for (ListNode n : wordList) {
            toReturn += n.toString();
        }
        return toReturn;
    }

    /**
     * Retrain the generator from scratch on the source text
     */
    @Override
    public void retrain(String sourceText) {
        wordList = new LinkedList<ListNode>();
        starter = "";

        this.train(sourceText);
    }

}

/**
 * Links a word to the next words in the list
 * You should use this class in your implementation.
 */
class ListNode {
    // The word that is linking to the next words
    private String word;

    // The next words that could follow it
    private List<String> nextWords;

    ListNode(String word) {
        this.word = word;
        nextWords = new LinkedList<String>();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof ListNode) {
            return this.word.equals(((ListNode) obj).word);
        }
        return false;
    }

    public String getWord() {
        return word;
    }

    public void addNextWord(String nextWord) {
        nextWords.add(nextWord);
    }

    public String getRandomNextWord(Random generator) {

        int position = generator.nextInt(this.nextWords.size());

        return nextWords.get(position);
    }

    public String toString() {
        String toReturn = word + ": ";
        for (String s : nextWords) {
            toReturn += s + "->";
        }
        toReturn += "\n";
        return toReturn;
    }

}


