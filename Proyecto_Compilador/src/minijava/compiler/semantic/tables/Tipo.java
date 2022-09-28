package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;

public class Tipo {

    private final int INT = 1;
    private final int BOOLEAN = 2;
    private final int CHAR = 3;
    private final int VOID = 4;
    private final int CLASS = 0;

    private int actualType;
    private Token tipoToken;

    public Tipo(Token tipoToken){
        actualType = -1;
        this.tipoToken = tipoToken;

        if(tipoToken.getToken().equals("idKeyWord_int")){
            actualType = INT;
        }else if(tipoToken.getToken().equals("idKeyWord_boolean")){
            actualType = BOOLEAN;
        }if(tipoToken.getToken().equals("idKeyWord_char")){
            actualType = CHAR;
        }else if(tipoToken.getToken().equals("idClass")){
            actualType = CLASS;
        }else if(tipoToken.getToken().equals("idKeyWord_void")){
            actualType = VOID;
        }
    }

    public String getLexemeType() { return tipoToken.getLexeme(); }

    public Token getTipoToken() { return tipoToken; }

    public boolean equals(Tipo tipo){
        return this.getLexemeType().equals(tipo.getLexemeType());
    }
}
