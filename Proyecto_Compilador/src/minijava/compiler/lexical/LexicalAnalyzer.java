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
        }else if (actualCharacter == '\''){
            updateLexeme();
            updateActualCharacter();
            return e21();
        }else if (actualCharacter == '/'){
            updateActualCharacter();
            e16();
            return e0();
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
            while(Character.isWhitespace(actualCharacter)){
                updateActualCharacter();
            }
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
        updateLexeme();
        updateActualCharacter();    // TODO chquear si cualquier char me sirve
        return e10();
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

    private void e16() throws LexicalException {
        if (actualCharacter == '/') {
            updateActualCharacter();
            e17();
        }else if (actualCharacter == '*'){
            updateActualCharacter();
            e18();
        }else{
            throw new LexicalException(lexeme, fileManager.getRow());
        }
    }

    // One line comment recognizer
    private void e17() throws LexicalException {
        if ((Character.isLetterOrDigit(actualCharacter) || Character.isWhitespace(actualCharacter)) & (actualCharacter != '\n')){
            updateActualCharacter();
            e17();
        }else if ((Character.getNumericValue(actualCharacter) == Character.getNumericValue('\n')) ||
                actualCharacter == '\u0000'){ // Comment finished
        }else{
            throw new LexicalException(lexeme, fileManager.getRow());
        }
    }

    private void e18() throws LexicalException {
        if (    Character.isLetterOrDigit(actualCharacter) ||
                Character.isWhitespace(actualCharacter) ||
                actualCharacter == '\n'){
            updateActualCharacter();
            e18();
        }else if (actualCharacter == '*'){
            updateActualCharacter();
            e19();
        }else{
            throw new LexicalException(lexeme, fileManager.getRow());
        }
    }

    private void e19() throws LexicalException {
        if (actualCharacter == '/'){
            updateActualCharacter();
            e20();
        }else{
            throw new LexicalException(lexeme, fileManager.getRow());
        }
    }

    // Multi-line commentary recognizer
    private void e20(){ /* Comment finished */ }

    private Token e21() throws LexicalException {
        if (actualCharacter == '\\'){
            updateLexeme();
            updateActualCharacter();
            return e24();
        }else if (actualCharacter != '\\' && actualCharacter != '\''){
            updateLexeme();
            updateActualCharacter();
            return e22();
        }else{
            throw new LexicalException(lexeme, fileManager.getRow());
        }
    }

    private Token e22() throws LexicalException {
        if (actualCharacter == '\''){
            updateLexeme();
            updateActualCharacter();
            return e23();
        }else{
            throw new LexicalException(lexeme, fileManager.getRow());
        }
    }

    // Character recognizer
    private Token e23(){
        return new Token("Caracter", lexeme, fileManager.getRow());
    }

    private Token e24(){
        updateLexeme();
        updateActualCharacter();    // TODO consultar si puedo agregar cualquier cosa
        return e23();
    }

    // EOF recognizer
    private Token ex(){
        return new Token("EOF", lexeme, fileManager.getRow());
    }

}
