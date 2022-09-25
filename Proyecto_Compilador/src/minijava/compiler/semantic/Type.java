package minijava.compiler.semantic;

import minijava.compiler.lexical.analyzer.Token;

public class Type {

    private final int INT = 1;
    private final int BOOLEAN = 2;
    private final int CHAR = 3;
    private final int CLASS = 0;

    private int actualType;
    private String classType;

    public Type(String type, Token token){
        actualType = -1;
        classType = "";

        if(type.equals("idKeyWord_int")){
            actualType = INT;
        }else if(type.equals("idKeyWord_boolean")){
            actualType = BOOLEAN;
        }if(type.equals("idKeyWord_char")){
            actualType = CHAR;
        }else if(type.equals("idClass")){
            actualType = CLASS;
            classType = token.getLexeme();
        }
    }
}
