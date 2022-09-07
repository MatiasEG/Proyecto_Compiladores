package minijava.compiler.exception.lexical.specificexceptions;

import minijava.compiler.exception.lexical.LexicalException;

public class LexicalExceptionCommentary extends LexicalException {

    public LexicalExceptionCommentary(String lexemeError, int lineError, int columnError) {
        super("Error lexico en l:c -> "+lineError+":"+columnError+": El comentario no fue cerrado correctamente.", lexemeError, lineError, columnError);
    }
}
