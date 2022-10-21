package minijava.compiler.exception.semanticP1.duplicated;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.tables.variable.Attribute;

public class SemanticExceptionDuplicatedAtribute extends SemanticException {

    private Attribute attribute;

    public SemanticExceptionDuplicatedAtribute(Attribute attribute){
        super("El atributo \'"+ attribute.getVarName()+"\' posee un nombre que ya esta siendo utilizado.", attribute.getVarToken());
        this.attribute = attribute;
    }
}
