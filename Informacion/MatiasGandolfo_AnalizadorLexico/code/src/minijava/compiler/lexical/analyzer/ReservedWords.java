package minijava.compiler.lexical.analyzer;

import java.util.HashMap;
import java.util.Map;

public class ReservedWords {

    private static Map<String, String> reservedWords;

    public static void init(){
        reservedWords = new HashMap<String, String>();

        reservedWords.put("class", "idKeyWord_class");
        reservedWords.put("interface", "idKeyWord_interface");
        reservedWords.put("extends", "idKeyWord_extends");
        reservedWords.put("implements", "idKeyWord_implements");
        reservedWords.put("public", "idKeyWord_public");
        reservedWords.put("private", "idKeyWord_private");
        reservedWords.put("static", "idKeyWord_static");
        reservedWords.put("void", "idKeyWord_void");
        reservedWords.put("boolean", "idKeyWord_boolean");
        reservedWords.put("char", "idKeyWord_char");
        reservedWords.put("int", "idKeyWord_int");
        reservedWords.put("if", "idKeyWord_if");
        reservedWords.put("else", "idKeyWord_else");
        reservedWords.put("while", "idKeyWord_while");
        reservedWords.put("return", "idKeyWord_return");
        reservedWords.put("var", "idKeyWord_var");
        reservedWords.put("this", "idKeyWord_this");
        reservedWords.put("new", "idKeyWord_new");
        reservedWords.put("null", "idKeyWord_null");
        reservedWords.put("true", "idKeyWord_true");
        reservedWords.put("false", "idKeyWord_false");
    }

    public static boolean belongs(String word){ return reservedWords.containsKey(word); }

    public static String getToken(String lexeme){ return reservedWords.get(lexeme); }
}
