package minijava.compiler.exception.semanticP1.duplicated;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.tables.variable.Parameter;

public class SemanticExceptionDuplicatedParameter extends SemanticException {

    private Parameter parameter;

    public SemanticExceptionDuplicatedParameter(Parameter parameter){
        super("El parametro numero ("+ parameter.getParameterPosition()+"): \'"+ parameter.getVarName()+"\' del metodo \'"+ parameter.getMethodOfDefinedParameter().getMethodName()+"\' tiene un nombre repetido.", parameter.getVarToken());
        this.parameter = parameter;
    }
}
