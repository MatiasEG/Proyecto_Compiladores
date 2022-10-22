package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;

public class Type {
    private Token typeToken;

    public Type(Token typeToken){
        this.typeToken = typeToken;
    }

    public String getLexemeType() { return typeToken.getLexeme(); }

    public Token getTokenType() { return typeToken; }

    public boolean equals(Type type){
        return this.getLexemeType().equals(type.getLexemeType());
    }

    public String getTypeForAssignment(){
        if(typeToken.getToken().equals("idKeyWord_boolean"))
            return "boolean";
        else if(typeToken.getToken().equals("idKeyWord_true"))
            return "boolean";
        else if(typeToken.getToken().equals("idKeyWord_false"))
            return "boolean";
        else if(typeToken.getToken().equals("literalInteger"))
            return "int";
        else if(typeToken.getToken().equals("idKeyWord_int"))
            return "int";
        else if(typeToken.getToken().equals("literalCharacter"))
            return "char";
        else if(typeToken.getToken().equals("idKeyWord_char"))
            return "char";
        else if(typeToken.getToken().equals("literalString"))
            return "String";
        else if(typeToken.getToken().equals("idClass"))
            return typeToken.getLexeme();
        else
            return "null";
    }

    public boolean isClassRef(){ return typeToken.getToken() == "idClass";}
}
