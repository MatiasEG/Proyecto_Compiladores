package minijava.compiler.exception.specificexceptions;

import minijava.compiler.exception.LexicalException;

public class LexicalExceptionInteger extends LexicalException {
    public LexicalExceptionInteger(String lexemeError, int lineError, int columnError) {
        super("Error lexico en l:c -> "+lineError+":"+columnError+": El entero no puede ser representado, posee mas de 9 digitos.", lexemeError, lineError, columnError);
    }
}
