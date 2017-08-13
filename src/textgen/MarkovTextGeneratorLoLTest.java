package textgen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Oluwatimilehin on 12/08/2017.
 */
public class MarkovTextGeneratorLoLTest {
    @Test
    public void train() throws Exception {
        MarkovTextGeneratorLoL generatorLoL = new MarkovTextGeneratorLoL(new Random(12));
        generatorLoL.train("The boy is a boy not a girl");
        List<ListNode> currentList = generatorLoL.getWordList();
        String returnedString = "";

        for (ListNode node : currentList){
            returnedString += node.toString();
        }

        assertEquals("Trained words","The: boy->\n" +
                "boy: is->not->\n" +
                "is: a->\n" +
                "a: boy->girl->\n" +
                "not: a->\n" +
                "girl: The->\n"
                , returnedString);
    }

    @Test
    public void generateText() throws Exception {

    }

    @Test
    public void retrain() throws Exception {

    }


}