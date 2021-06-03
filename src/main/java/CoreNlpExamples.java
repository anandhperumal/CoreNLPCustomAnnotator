import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;

import java.util.List;
import java.util.Properties;


public class CoreNlpExamples {

    public static void main(String [] args) {
        Properties prop = new Properties();
        prop.setProperty("customAnnotatorClass.statements", "StatementAnnotator");

        prop.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, statements");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);


        String example =
                "The ceremony is happening now. \n" +
                        "The wedding wasn't ever not happening. \n" +
                        "That kind of stuff doesn't happen to me.\n" +
                        "It just never really happened.";


        Annotation document = new Annotation(example);
        pipeline.annotate(document);

        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                // Label Weight dummy
                String lw = token.get(CustomLabeller.class);

                System.out.println(String.format("Print: word: [%s] pos: [%s] ne:[%s] lw:[%s]", word, pos, ne, lw));

            }


        }
    }

}
