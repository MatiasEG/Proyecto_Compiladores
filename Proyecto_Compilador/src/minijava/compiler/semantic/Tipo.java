package minijava.compiler.semantic;

public class Tipo {

    private final int INT = 1;
    private final int BOOLEAN = 2;
    private final int CHAR = 3;
    private final int VOID = 4;
    private final int CLASS = 0;

    private int actualType;
    private String lexemeType;

    public Tipo(String lexeme, String token){
        actualType = -1;
        lexemeType = lexeme;

        if(token.equals("idKeyWord_int")){
            actualType = INT;
        }else if(token.equals("idKeyWord_boolean")){
            actualType = BOOLEAN;
        }if(token.equals("idKeyWord_char")){
            actualType = CHAR;
        }else if(token.equals("idClass")){
            actualType = CLASS;
        }else if(token.equals("idKeyWord_void")){
            actualType = VOID;
        }
    }

    public String getLexemeType() { return lexemeType; }

    public boolean equals(Tipo tipo){
        return this.getLexemeType().equals(tipo.getLexemeType());
    }
}
