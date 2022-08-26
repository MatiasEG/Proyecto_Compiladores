package minijava.compiler.exception.specificexceptions;

import minijava.compiler.exception.LexicalException;

public class LexicalExceptionCommentary extends LexicalException {

    public LexicalExceptionCommentary(String lexemeError, int lineError, int columnError) {
        super("Error lexico en l:c -> "+lineError+":"+columnError+": El comentario no fue cerrado correctamente.", lexemeError, lineError, columnError);
    }
}
