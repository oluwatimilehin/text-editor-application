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
        generatorLoL.train("hi there hi Leo");
        List<ListNode> currentList = generatorLoL.getWordList();
        String returnedString = "";

        for (ListNode node : currentList){
            returnedString += node.toString();
        }

        assertEquals("Trained words","hi: there->Leo->\n" +
                "there: hi->\n" +
                "Leo: hi->\n", returnedString);
    }

    @Test
    public void generateText() throws Exception {

    }

    @Test
    public void retrain() throws Exception {

    }


}