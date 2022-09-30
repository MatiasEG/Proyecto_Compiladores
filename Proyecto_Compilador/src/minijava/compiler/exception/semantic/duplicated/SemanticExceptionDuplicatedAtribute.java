package minijava.compiler.exception.semantic.duplicated;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Atributo;

public class SemanticExceptionDuplicatedAtribute extends SemanticException {

    private Atributo atributo;

    public SemanticExceptionDuplicatedAtribute(Atributo atributo){
        super("El atributo \'"+atributo.getNombre()+"\' posee un nombre que ya esta siendo utilizado.", atributo.getVarToken());
        this.atributo = atributo;
    }
}
