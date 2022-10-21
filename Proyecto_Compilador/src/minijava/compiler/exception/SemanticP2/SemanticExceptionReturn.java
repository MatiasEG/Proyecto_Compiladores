package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.Method;

public class SemanticExceptionReturn extends SemanticException {

    public SemanticExceptionReturn(Method m, Token returnToken){
        super("El metodo \'"+m.getMethodName()+"\' es de tipo void pero posee un return en su bloque.", returnToken);
    }
}
