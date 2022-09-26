package minijava.compiler.exception.semantic;

import minijava.compiler.semantic.Metodo;

public class SemanticExceptionDuplicatedMethod extends SemanticException{

    private Metodo metodo;

    public SemanticExceptionDuplicatedMethod(Metodo metodo){
        super("El metodo \'"+metodo.getNombre()+"\' de la clase \'"+metodo.getClaseDefinido()+"\' esta mal redefinido.");
        this.metodo = metodo;
    }

    public String getLexeme(){ return metodo.getNombre(); }

    public int getRow(){ return metodo.getLineaDeclaracion(); }
}
