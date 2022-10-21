package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.Method;

public class SemanticExceptionWrongReturnType extends SemanticException {

    public SemanticExceptionWrongReturnType(Method m, Token token){
        super("El metodo \'"+m.getMethodName()+"\' espera un retorno tipo \'"+m.getMethodType().getLexemeType()+
                "\' pero encontro un tipo \'"+token.getLexeme()+"\'.", token);
    }
}
