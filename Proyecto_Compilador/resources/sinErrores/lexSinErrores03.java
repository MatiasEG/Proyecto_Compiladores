private Token e46() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
        updateLexeme();
        updateActualCharacter();
        return e47();
        }else{
        return new Token("Entero", lexeme, fileManager.getRow());
        }
        }

private Token e47() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
        updateLexeme();
        updateActualCharacter();
        return e47();
        }else{
        String msg = "Error lexico en linea "+fileManager.getRow()+": El entero no puede ser representado, posee mas de 9 digitos.";
        throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
        }
        }