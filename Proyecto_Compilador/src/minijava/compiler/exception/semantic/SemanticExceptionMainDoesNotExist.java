package minijava.compiler.exception.semantic;

public class SemanticExceptionMainDoesNotExist extends SemanticException{


    public SemanticExceptionMainDoesNotExist(){
        super("No existe ninguna clase con metodo main.");
    }

    // TODO completar los metodos

    @Override
    public int getRow() {
        return 0;
    }

    @Override
    public String getLexeme() {
        return "main";
    }
}
