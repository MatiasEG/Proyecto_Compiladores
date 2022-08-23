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
        if(Character.isDigit(actualCharacter)){ // TODO contar hasta 9 digitos
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
        }else if (actualCharacter == '-'){
            updateLexeme();
            updateActualCharacter();
            return e30();
        }else if (actualCharacter == '*'){
            updateLexeme();
            updateActualCharacter();
            return e31();
        }else if (actualCharacter == '/'){
            updateLexeme();
            updateActualCharacter();
            Token tAux = e16();
            if(tAux == null) return e0();
            else return tAux;
        }else if (actualCharacter == '%'){
            updateLexeme();
            updateActualCharacter();
            return e32();
        }else if (actualCharacter == '&'){
            updateLexeme();
            updateActualCharacter();
            return e33();
        }else if (actualCharacter == '|'){
            updateLexeme();
            updateActualCharacter();
            return e35();
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
        }else if (actualCharacter == '{'){
            updateLexeme();
            updateActualCharacter();
            return e14();
        }else if (actualCharacter == '}'){
            updateLexeme();
            updateActualCharacter();
            return e15();
        }else if (actualCharacter == '('){
            updateLexeme();
            updateActualCharacter();
            return e25();
        }else if (actualCharacter == ')'){
            updateLexeme();
            updateActualCharacter();
            return e26();
        }else if (actualCharacter == ';'){
            updateLexeme();
            updateActualCharacter();
            return e27();
        }else if (actualCharacter == ','){
            updateLexeme();
            updateActualCharacter();
            return e28();
        }else if (actualCharacter == '.'){
            updateLexeme();
            updateActualCharacter();
            return e29();
        }else if (fileManager.isEOF()){
            return ex();
        }else if (Character.isWhitespace(actualCharacter)){
            while(Character.isWhitespace(actualCharacter)){
                updateActualCharacter();
            }
            return e0();
        }else{
            updateLexeme();
            updateActualCharacter();
            throw new LexicalException(lexeme, fileManager.getRow());
        }
    }

    // Digit recognizer
    // TODO esta bien pensado con el while? o estoy rompiendo el esquema de la maquina de estados demasiado?
    private Token e1() throws LexicalException {
        int cantDigits = 1;
        while(Character.isDigit(actualCharacter)){
            cantDigits++;
            updateLexeme();
            updateActualCharacter();
        }
        if(cantDigits <= 9){
            return new Token("Entero", lexeme, fileManager.getRow());
        }else{
            throw new LexicalException(lexeme, fileManager.getRow());
        }
//        if(Character.isDigit(actualCharacter)){
//            updateLexeme();
//            updateActualCharacter();
//            return e1();
//        }else{
//            return new Token("Entero", lexeme, fileManager.getRow());
//        }
    }

    // Identifier recognizer
    // TODO tenemos que reconocer si corresponde a una palabra reservada, clase o variable?
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
        if (actualCharacter == '\\'){
            updateLexeme();
            updateActualCharacter();
            return e11();
        }else if (actualCharacter == '"'){
            updateLexeme();
            updateActualCharacter();
            return e12();
        }else if (actualCharacter == '\n' || actualCharacter == '\u0000'){
            throw new LexicalException(lexeme, fileManager.getRow());
        }else{
            updateLexeme();
            updateActualCharacter();
            return e10();
        }
    }

    private Token e11() throws LexicalException {
        if(actualCharacter == '\n' || actualCharacter == '\u0000'){
            throw new LexicalException(lexeme, fileManager.getRow());
        }else{
            updateLexeme();
            updateActualCharacter();
            return e10();
        }
    }

    // String recognizer
    private Token e12(){
        return new Token("String", lexeme, fileManager.getRow());
    }

    // "+" recognizer
    private Token e13(){
        if(actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e37();
        }else{
            return new Token("Suma", lexeme, fileManager.getRow());
        }
    }

    // "{" recognizer
    private Token e14(){
        return new Token("Corchete que abre", lexeme, fileManager.getRow());
    }

    // "}" recognizer
    private Token e15(){
        return new Token("Corchete que cierra", lexeme, fileManager.getRow());
    }

    private Token e16() throws LexicalException {
        if (actualCharacter == '/') {
            updateLexeme();
            updateActualCharacter();
            e17();
            return null;
        }else if (actualCharacter == '*'){
            updateLexeme();
            updateActualCharacter();
            e18();
            return null;
        }else{
            return new Token("Division", lexeme, fileManager.getRow());
        }
    }

    // One line comment recognizer
    // TODO en los comentarios multilinea el lexema toma los enter y cuando muestra un error queda horrible.
    private void e17(){
        if (actualCharacter == '\n' || actualCharacter == '\u0000'){
            lexeme = ""; /* Comment finished */
        }else{
            updateLexeme();
            updateActualCharacter();
            e17();
        }
    }

    private void e18() throws LexicalException {
        if (actualCharacter == '*'){
            updateLexeme();
            updateActualCharacter();
            e19();
        }else if (actualCharacter == '\u0000'){
            throw new LexicalException(lexeme, fileManager.getRow());
        }else {
            updateLexeme();
            updateActualCharacter();
            e18();
        }
    }

    private void e19() throws LexicalException {
        if (actualCharacter == '/'){
            updateLexeme();
            updateActualCharacter();
            e20();
        }else if (actualCharacter == '\u0000'){
            throw new LexicalException(lexeme, fileManager.getRow());
        }else{
            updateLexeme();
            updateActualCharacter();
            e18();
        }
    }

    // Multi-line commentary recognizer
    private void e20(){ lexeme = ""; /* Comment finished */ }

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

    private Token e24() throws LexicalException {
        if(actualCharacter == '\n' || actualCharacter == '\u0000'){
            throw new LexicalException(lexeme, fileManager.getRow());
        }else{
            updateLexeme();
            updateActualCharacter();
            return e23();
        }
    }

    // '(' recognizer
    private Token e25(){
        return new Token("ParentecisAbre", lexeme, fileManager.getRow());
    }

    // ')' recognizer
    private Token e26(){
        return new Token("ParentecisCierra", lexeme, fileManager.getRow());
    }

    // ';' recognizer
    private Token e27(){
        return new Token("PuntoYComa", lexeme, fileManager.getRow());
    }

    // ',' recognizer
    private Token e28(){
        return new Token("Coma", lexeme, fileManager.getRow());
    }

    // '.' recognizer
    private Token e29(){
        return new Token("Punto", lexeme, fileManager.getRow());
    }

    // '-' recognizer
    private Token e30(){
        if(actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e38();
        }else{
            return new Token("Resta", lexeme, fileManager.getRow());
        }
    }

    // '*' recognizer
    private Token e31(){
        return new Token("Producto", lexeme, fileManager.getRow());
    }

    // '%' recognizer
    private Token e32(){
        return new Token("Porcentaje", lexeme, fileManager.getRow());
    }

    private Token e33() throws LexicalException {
        if(actualCharacter == '&'){
            updateLexeme();
            updateActualCharacter();
            return e34();
        }else{
            throw new LexicalException(lexeme, fileManager.getRow());
        }
    }

    // '&&' recognizer
    private Token e34(){
        return new Token("OperadorLogicoAnd", lexeme, fileManager.getRow());
    }

    private Token e35() throws LexicalException {
        if(actualCharacter == '|'){
            updateLexeme();
            updateActualCharacter();
            return e36();
        }else{
            throw new LexicalException(lexeme, fileManager.getRow());
        }
    }

    // '||' recognizer
    private Token e36(){
        return new Token("OperadorLogicoOr", lexeme, fileManager.getRow());
    }

    // '+=' recognizer
    private Token e37(){
        return new Token("AsignacionCompuestaSuma", lexeme, fileManager.getRow());
    }

    // '-=' recognizer
    private Token e38(){
        return new Token("AsignacionCompuestaResta", lexeme, fileManager.getRow());
    }

    // EOF recognizer
    // TODO cual es el lexema del EOF?
    private Token ex(){
        return new Token("EOF", lexeme, fileManager.getRow());
    }

}
