package minijava.compiler.exception.semantic;

public abstract class SemanticException extends Exception{

    public SemanticException(String msg){
        super(msg);
    }

    public abstract int getRow();

    public abstract String getLexeme();
}
