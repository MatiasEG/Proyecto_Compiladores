package minijava.compiler.lexical;

import minijava.compiler.exception.LexicalException;
import minijava.compiler.filemanager.FileManager;

public class LexicalAnalyzer {
    private String lexeme;
    private char actualCharacter;
    private FileManager fileManager;

    public LexicalAnalyzer(FileManager fileManager){
        this.fileManager = fileManager;
        actualCharacter = fileManager.getNextChar();
    }

    public Token nextToken() throws LexicalException {
        lexeme = "";
        return e0();
    }

    public void updateLexeme(){
        lexeme+= actualCharacter;
    }

    public void updateActualCharacter(){
        actualCharacter = fileManager.getNextChar();
    }

    private Token e0() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e1();
        }else if(Character.isLetter(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e2();
        }else if (actualCharacter == '+'){
            updateLexeme();
            updateActualCharacter();
            return e13();
        }else if (actualCharacter == '>'){
            updateLexeme();
            updateActualCharacter();
            return e3();
        }else if (actualCharacter == '<'){
            updateLexeme();
            updateActualCharacter();
            return e5();
        }else if (actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e7();
        }else if (actualCharacter == '!'){
            updateLexeme();
            updateActualCharacter();
            return e6();
        }else if (actualCharacter == '"'){
            updateLexeme();
            updateActualCharacter();
            return e10();
        }else if (actualCharacter == '{'){
            updateLexeme();
            updateActualCharacter();
            return e14();
        }else if (actualCharacter == '}'){
            updateLexeme();
            updateActualCharacter();
            return e15();
        }else if (fileManager.isEOF()){
            return ex();
        }else if (Character.isWhitespace(actualCharacter)){
            updateActualCharacter();
            return e0();
        }else{
            updateLexeme();
            throw new LexicalException(lexeme, fileManager.getRow());
        }
    }

    // Digit recognizer
    private Token e1(){
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e1();
        }else{
            return new Token("Entero", lexeme, fileManager.getRow());
        }
    }

    // Identifier recognizer
    private Token e2(){
        if(Character.isLetterOrDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e2();
        }else if (ReservedWords.belongs(lexeme)){
            return new Token("Palabra reservada", lexeme, fileManager.getRow());
        }else{
            return new Token("Identificador", lexeme, fileManager.getRow());
        }
    }

    // ">" recognizer
    private Token e3(){
        if (actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e4();
        }else{
            return new Token("Mayor", lexeme, fileManager.getRow());
        }
    }

    // ">=" & "<=" recognizer
    private Token e4(){
        return new Token(lexeme+"Igual", lexeme, fileManager.getRow());
    }

    // "<" recognizer
    private Token e5(){
        if (actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e4();
        }else{
            return new Token("Menor", lexeme, fileManager.getRow());
        }
    }

    // "!" recognizer
    private Token e6(){
        if (actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e9();
        }else{
            return new Token("Negacion", lexeme, fileManager.getRow());
        }
    }

    // "=" recognizer
    private Token e7(){
        if (actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e8();
        }else{
            return new Token("Asignacion", lexeme, fileManager.getRow());
        }
    }

    // "==" recognizer
    private Token e8(){
        return new Token("Igual", lexeme, fileManager.getRow());
    }

    // "!=" recognizer
    private Token e9(){
        return new Token("Distinto", lexeme, fileManager.getRow());
    }

    private Token e10() throws LexicalException {
        if (Character.isLetterOrDigit(actualCharacter) || Character.isWhitespace(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e10();
        }else if (actualCharacter == '\\'){
            updateLexeme();
            updateActualCharacter();
            return e11();
        }else if (actualCharacter == '"'){
            updateLexeme();
            updateActualCharacter();
            return e12();
        }else{
            throw new LexicalException(lexeme, fileManager.getRow());
        }
    }

    private Token e11() throws LexicalException {
        if (actualCharacter == '"'){
            updateLexeme();
            updateActualCharacter();
            return e10();
        }else{
            throw new LexicalException(lexeme, fileManager.getRow());
        }
    }

    // String recognizer
    private Token e12(){
        return new Token("String", lexeme, fileManager.getRow());
    }

    // "+" recognizer
    private Token e13(){
        return new Token("Suma", lexeme, fileManager.getRow());
    }

    // "{" recognizer
    private Token e14(){
        return new Token("Corchete que abre", lexeme, fileManager.getRow());
    }

    // "}" recognizer
    private Token e15(){
        return new Token("Corchete que cierra", lexeme, fileManager.getRow());
    }

    // EOF recognizer
    private Token ex(){
        return new Token("EOF", lexeme, fileManager.getRow());
    }

}
