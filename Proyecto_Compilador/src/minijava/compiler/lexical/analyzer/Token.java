package minijava.compiler.lexical.analyzer;

public class Token {

    private String token;
    private String lexeme;
    private int lineNumber;


    public Token(String token, String lexeme, int lineNumber){
        this.token = token;
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }

    public String getToken() { return token; }

    public String getLexeme() { return lexeme; }

    public int getLineNumber() { return lineNumber; }

    public void setToken(String token) { this.token = token; }

    public void setLexeme(String lexeme) { this.lexeme = lexeme; }

    public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }
}
