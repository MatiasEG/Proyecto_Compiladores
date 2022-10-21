package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.tables.Method;
import minijava.compiler.semantic.tables.variable.VarLocal;

public class SemanticExceptionVarLocalAlreadyExist extends SemanticException {

    public SemanticExceptionVarLocalAlreadyExist(Method m, VarLocal v){
        super("La variable local \'"+v.getVarName()+"\' del metodo \'"+m.getMethodName()+"\' tiene un nombre duplicado.", v.getVarToken());
    }
}
