// One line comment recognizer
private Token e17() /*throws LexicalException*/ {
        if (actualCharacter == '\n' || actualCharacter == '\u0000'){
            lexeme = ""; /* Comment finished */
            return e0();
        }else{
            updateLexeme();
            updateActualCharacter();
            return e17();
        }
}