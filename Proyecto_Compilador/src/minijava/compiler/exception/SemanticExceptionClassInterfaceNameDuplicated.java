package minijava.compiler.exception;

import minijava.compiler.semantic.ClaseInterface;

public class SemanticExceptionClassInterfaceNameDuplicated extends SemanticException{

    private ClaseInterface claseInterface;

    public SemanticExceptionClassInterfaceNameDuplicated(String msg, ClaseInterface claseInterface){
        super(msg);
        this.claseInterface = claseInterface;
    }

    public String getLexeme(){
        return claseInterface.getNombre();
    }

    public int getRow(){
        return claseInterface.getLineaDeclaracion();
    }
}
