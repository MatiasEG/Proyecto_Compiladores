package minijava.compiler.exception.semantic.duplicated;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Method;

public class SemanticExceptionDuplicatedMethod extends SemanticException {

    private Method method;

    public SemanticExceptionDuplicatedMethod(Method method){
        super("El metodo \'"+ method.getMethodName()+"\' de la clase \'"+ method.getClassDeclaredMethod()+"\' esta mal sobrecargado.", method.getMethodToken());
        this.method = method;
    }

}
