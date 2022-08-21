package minijava.compiler.lexical;

import java.util.HashMap;
import java.util.Map;

public class ReservedWords {

    private static Map reservedWords;

    public static void init(){
        reservedWords = new HashMap<String, Boolean>();

        reservedWords.put("class", true);
        reservedWords.put("interface", true);
        reservedWords.put("extends", true);
        reservedWords.put("implements", true);

        reservedWords.put("public", true);
        reservedWords.put("private", true);
        reservedWords.put("static", true);

        reservedWords.put("void", true);
        reservedWords.put("boolean", true);
        reservedWords.put("char", true);
        reservedWords.put("int", true);

        reservedWords.put("if", true);
        reservedWords.put("else", true);
        reservedWords.put("while", true);
        reservedWords.put("return", true);
        reservedWords.put("var", true);

        reservedWords.put("this", true);
        reservedWords.put("new", true);
        reservedWords.put("null", true);
        reservedWords.put("true", true);
        reservedWords.put("false", true);
    }

    public static boolean belongs(String word){ return reservedWords.containsKey(word); }
}
