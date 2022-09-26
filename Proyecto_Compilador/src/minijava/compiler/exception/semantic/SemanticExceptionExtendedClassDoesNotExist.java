package minijava.compiler.exception.semantic;

import minijava.compiler.semantic.ClaseInterface;

public class SemanticExceptionExtendedClassDoesNotExist extends SemanticException{

    private ClaseInterface claseInterface;

    public SemanticExceptionExtendedClassDoesNotExist(ClaseInterface claseInterface, String errorClass){
        super("La clase \'"+errorClass+"\' de la que busca heredar la clase \'"+claseInterface.getNombre()+"\' no esta definida en el codigo.");
        this.claseInterface = claseInterface;
    }

    public String getLexeme(){
        return claseInterface.getNombre();
    }

    public int getRow(){
        return claseInterface.getLineaDeclaracion();
    }
}
