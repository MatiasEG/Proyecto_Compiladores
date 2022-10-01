package minijava.compiler.exception.semantic.duplicated;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Parameter;

public class SemanticExceptionDuplicatedParameter extends SemanticException {

    private Parameter parameter;

    public SemanticExceptionDuplicatedParameter(Parameter parameter){
        super("El parametro numero ("+ parameter.getParameterPosition()+"): \'"+ parameter.getVarName()+"\' del metodo \'"+ parameter.getMethodOfDefinedParameter().getMethodName()+"\' tiene un nombre repetido.", parameter.getVarToken());
        this.parameter = parameter;
    }
}
