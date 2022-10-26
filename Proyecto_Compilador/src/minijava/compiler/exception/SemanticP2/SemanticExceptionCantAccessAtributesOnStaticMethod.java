package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionCantAccessAtributesOnStaticMethod extends SemanticException {

    public SemanticExceptionCantAccessAtributesOnStaticMethod(Token atributoToken){
        super("No es posible acceder a los atributos del objeto en un metodo estatico.", atributoToken);
    }
}
