package minijava.compiler.exception.specificexceptions;

import minijava.compiler.exception.LexicalException;

public class LexicalExceptionUnicode extends LexicalException {
    public LexicalExceptionUnicode(String lexemeError, int lineError, int columnError) {
        super("Error lexico en l:c -> "+lineError+":"+columnError+": Caracter unicode invalido.", lexemeError, lineError, columnError);
    }
}
