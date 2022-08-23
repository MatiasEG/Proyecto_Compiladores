package minijava.compiler.exception;

public class LexicalException extends Exception{
    public LexicalException(String lexeme, int lineError){
        super("[Error:"+lexeme+"|"+lineError+"]");
    }

    public String getMessage(){
        return super.getMessage();
    }
}
