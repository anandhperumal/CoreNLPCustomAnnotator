import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;


import java.util.*;

/**
 * Annotates sentences with statements as represented by the Statement class.
 */
public class StatementAnnotator implements Annotator {


    public final static String STATEMENT = "statement";


    /**
     * This constructor allows for the annotator to accept different properties to alter its behaviour.
     * It doesn't seem to be documented anywhere, but a method in AnnotatorImplementations.java with signature
     *      public Annotator custom(Properties properties, String property) { ... }
     * allows for various constructor signatures to be implemented for a custom annotator.
     * @param properties
     */
    public StatementAnnotator(String name, Properties properties) {
        String prefix = (name != null && !name.isEmpty())? name + ".":"";
    }

    @Override
    public void annotate(Annotation annotation)  {
        String sentences = annotation.get(CoreAnnotations.TextAnnotation.class);
        PythonConnector newClass = new PythonConnector();
        Hashtable<String, String> data = newClass.main(sentences);

        for (CoreLabel token : annotation.get(CoreAnnotations.TokensAnnotation.class)) {


            token.set(CustomLabeller.class, data.get(token.get(CoreAnnotations.TextAnnotation.class)));
        }

    }

    @Override
    public Set<Requirement> requirementsSatisfied() {
        Set<Requirement> requirementsSatisfied = new HashSet<>();
        requirementsSatisfied.add(new Requirement(STATEMENT));
        return requirementsSatisfied;
    }

    @Override
    public Set<Requirement> requires() {
        Set<Requirement> requirements = new HashSet<>();
        // TODO: find out why it fails when requirements are set
//        requirements.add(new Requirement(Annotator.STANFORD_PARSE));
        return requirements;
    }
}