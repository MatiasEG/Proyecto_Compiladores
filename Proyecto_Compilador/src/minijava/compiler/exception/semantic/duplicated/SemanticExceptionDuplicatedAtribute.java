package minijava.compiler.exception.semantic.duplicated;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Attribute;

public class SemanticExceptionDuplicatedAtribute extends SemanticException {

    private Attribute attribute;

    public SemanticExceptionDuplicatedAtribute(Attribute attribute){
        super("El atributo \'"+ attribute.getVarName()+"\' posee un nombre que ya esta siendo utilizado.", attribute.getVarToken());
        this.attribute = attribute;
    }
}
