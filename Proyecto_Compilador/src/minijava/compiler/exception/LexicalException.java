package minijava.compiler.exception;

public class LexicalException extends Exception{
    public LexicalException(String lexeme, int lineError){
        super("Se produjo un error con el lexema: "+lexeme+" en la linea "+lineError+".");
    }
}
