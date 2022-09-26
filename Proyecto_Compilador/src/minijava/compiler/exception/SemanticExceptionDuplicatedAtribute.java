package minijava.compiler.exception;

import minijava.compiler.semantic.Atributo;

public class SemanticExceptionDuplicatedAtribute extends SemanticException{

    private Atributo atributo;

    public SemanticExceptionDuplicatedAtribute(Atributo atributo){
        super("El atributo \'"+atributo.getNombre()+"\' posee un nombre que ya esta siendo utilizado.");
        this.atributo = atributo;
    }

    @Override
    public int getRow() {
        return atributo.getLineaDeclaracion();
    }

    @Override
    public String getLexeme() {
        return atributo.getNombre();
    }
}
