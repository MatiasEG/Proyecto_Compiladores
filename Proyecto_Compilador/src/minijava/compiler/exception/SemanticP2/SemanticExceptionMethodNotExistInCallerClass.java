package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionMethodNotExistInCallerClass extends SemanticException {

    public SemanticExceptionMethodNotExistInCallerClass(Token metodoLlamado){
        super("La llamada a metodo no puede llevarse a cabo, ya que \'"+metodoLlamado.getLexeme()+"\' no esta en este alcance.", metodoLlamado);
    }
}
