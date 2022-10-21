package minijava.compiler.exception.semanticP1.classinterface;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.tables.Method;

public class SemanticExceptionStaticMethodOnInterface extends SemanticException {

    public SemanticExceptionStaticMethodOnInterface(Method method){
        super("El metodo \'"+method.getMethodName()+"\' no puede ser estatico porque se encuentra definido en una interfaz.", method.getMethodToken());
    }
}
