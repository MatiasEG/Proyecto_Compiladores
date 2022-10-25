package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionCantCallThisOnStaticMethod extends SemanticException {

    public SemanticExceptionCantCallThisOnStaticMethod(Token thisToken){
        super("No es posible acceder al objeto this en un metodo estatico ya que puede el metodo puede invocarse sin necesidad de instanciar un objeto.", thisToken);
    }
}
