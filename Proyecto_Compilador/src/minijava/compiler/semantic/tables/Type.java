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
}
