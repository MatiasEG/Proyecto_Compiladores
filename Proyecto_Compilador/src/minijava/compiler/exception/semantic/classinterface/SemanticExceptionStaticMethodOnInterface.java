package minijava.compiler.exception.semantic.classinterface;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Method;

public class SemanticExceptionStaticMethodOnInterface extends SemanticException {

    public SemanticExceptionStaticMethodOnInterface(Method method){
        super("El metodo \'"+method.getMethodName()+"\' no puede ser estatico porque se encuentra definido en una interfaz.", method.getMethodToken());
    }
}
