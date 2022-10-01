package minijava.compiler.exception.semantic.method;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Method;

public class SemanticExceptionWrongDefinedConstructor extends SemanticException {

    public SemanticExceptionWrongDefinedConstructor(Method method){
        super("El constructor \'"+ method.getMethodName()+"\' no coincide con el nombre de clase.", method.getMethodToken());
    }
}
