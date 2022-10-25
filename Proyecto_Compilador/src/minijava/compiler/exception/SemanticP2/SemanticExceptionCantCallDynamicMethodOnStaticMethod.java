package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionCantCallDynamicMethodOnStaticMethod extends SemanticException {

    public SemanticExceptionCantCallDynamicMethodOnStaticMethod(Token idMetodo){
        super("No es posible invocar metodos dinamicos dentro de metodos estaticos.", idMetodo);
    }
}
