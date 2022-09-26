package minijava.compiler.exception;

public abstract class SemanticException extends Exception{

    public SemanticException(String msg){
        super(msg);
    }

    public abstract int getRow();

    public abstract String getLexeme();
}
