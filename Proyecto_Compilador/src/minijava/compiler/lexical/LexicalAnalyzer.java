package minijava.compiler.lexical;

import minijava.compiler.exception.LexicalException;
import minijava.compiler.filemanager.FileManager;

public class LexicalAnalyzer {
    private String lexeme;
    private char actualCharacter;
    private FileManager fileManager;

    private int multilineCommentLine;
    private int multilineCommentColumn;

    public LexicalAnalyzer(FileManager fileManager){
        multilineCommentLine = -1;
        multilineCommentColumn = -1;
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
        }else if(Character.isLetter(actualCharacter) && actualCharacter <= 'z' && actualCharacter >= 'a'){
            updateLexeme();
            updateActualCharacter();
            return e2();
        }else if(Character.isLetter(actualCharacter) && actualCharacter <= 'Z' && actualCharacter >= 'A'){
            updateLexeme();
            updateActualCharacter();
            return e53();
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
            return e16();
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
            updateActualCharacter();
            return e0();
        }else{
            updateLexeme();
            updateActualCharacter();
            // TODO lexicalexceptiongeneric
            String msg = "Error lexico en linea "+fileManager.getRow()+": Error al analizar el siguiente token.";
            throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    // Digit recognizer
    private Token e1() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e39();
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
            return new Token("IdentificadorMetodoVariable", lexeme, fileManager.getRow());
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
            // TODO lexicalexceptionstring
            String msg = "Error lexico en linea "+fileManager.getRow()+": El string no fue cerrado correctamente.";
            throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
        }else{
            updateLexeme();
            updateActualCharacter();
            return e10();
        }
    }

    private Token e11() throws LexicalException {
        if(actualCharacter == '\n' || actualCharacter == '\u0000'){
            // TODO lexicalexceptionstring
            String msg = "Error lexico en linea "+fileManager.getRow()+": El string no fue cerrado correctamente.";
            throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
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
            return e17();
        }else if (actualCharacter == '*'){
            updateLexeme();
            updateActualCharacter();
            multilineCommentLine = -1;
            multilineCommentColumn = fileManager.getColumn()-2;
            return e18();
        }else{
            return new Token("Division", lexeme, fileManager.getRow());
        }
    }

    // One line comment recognizer
    private Token e17() throws LexicalException {
        if (actualCharacter == '\n' || actualCharacter == '\u0000'){
            lexeme = ""; /* Comment finished */
            return e0();
        }else{
            updateLexeme();
            updateActualCharacter();
            return e17();
        }
    }

    private Token e18() throws LexicalException {
        if (actualCharacter == '*'){
            if(multilineCommentLine == -1) updateLexeme();
            updateActualCharacter();
            return e19();
        }else if (actualCharacter == '\u0000'){
            if(multilineCommentLine == -1){
                // TODO lexicalexceptioncommentary
                String msg = "Error lexico en linea "+fileManager.getRow()+": El comentario no fue cerrado correctamente.";
                throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
            }else{
                // TODO lexicalexceptioncommentary
                String msg = "Error lexico en linea "+multilineCommentLine+": El comentario no fue cerrado correctamente.";
                throw new LexicalException(msg, lexeme, multilineCommentLine, multilineCommentColumn);
            }
        }else{
            if(multilineCommentLine == -1){
                multilineCommentLine = fileManager.getRow();
                actualCharacter = ' ';
                updateLexeme();
            }
            updateActualCharacter();
            return e18();
        }
    }

    private Token e19() throws LexicalException {
        if (actualCharacter == '/') {
            if(multilineCommentLine == -1) updateLexeme();
            updateActualCharacter();
            return e20();
        }else if (actualCharacter == '*'){
            if(multilineCommentLine == -1) updateLexeme();
            updateActualCharacter();
            return e19();
        }else if (actualCharacter == '\u0000'){
            if(multilineCommentLine == -1){
                // TODO lexicalexceptioncommentary
                String msg = "Error lexico en linea "+fileManager.getRow()+": El comentario no fue cerrado correctamente.";
                throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
            }else{
                // TODO lexicalexceptioncommentary
                String msg = "Error lexico en linea "+multilineCommentLine+": El comentario no fue cerrado correctamente.";
                throw new LexicalException(msg, lexeme, multilineCommentLine, multilineCommentColumn);
            }
        }else{
            if(multilineCommentLine == -1) updateLexeme();
            updateActualCharacter();
            return e18();
        }
    }

    // Multi-line commentary recognizer
    private Token e20() throws LexicalException { lexeme = ""; return e0(); /* Comment finished */ }

    private Token e21() throws LexicalException {
        if (actualCharacter == '\\'){
            updateLexeme();
            updateActualCharacter();
            return e24();
        }else if (actualCharacter != '\''){
            updateLexeme();
            updateActualCharacter();
            return e22();
        }else if (actualCharacter != '\''){
            // TODO lexicalexceptionliteralcaracterempty
            String msg = "Error lexico en linea "+fileManager.getRow()+": Literal caracter vacio.";
            throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
        }else{
            // TODO lexicalexceptionliteralcaracternotclosed
            String msg = "Error lexico en linea "+fileManager.getRow()+": Literal caracter no fue cerrado correctamente.";
            throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    private Token e22() throws LexicalException {
        if (actualCharacter == '\''){
            updateLexeme();
            updateActualCharacter();
            return e23();
        }else{
            // TODO lexicalexceptionliteralcaracternotclosed
            String msg = "Error lexico en linea "+fileManager.getRow()+": Literal caracter no fue cerrado correctamente.";
            throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    // Character recognizer
    private Token e23(){
        return new Token("Caracter", lexeme, fileManager.getRow());
    }

    private Token e24() throws LexicalException {
        if(actualCharacter == '\n' || actualCharacter == '\u0000'){
            // TODO lexicalexceptionliteralcaracternotclosed
            String msg = "Error lexico en linea "+fileManager.getRow()+": Literal caracter no fue cerrado correctamente.";
            throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
        }else{
            updateLexeme();
            updateActualCharacter();
            return e22();
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
            // TODO lexicalexceptionlogicand
            String msg = "Error lexico en linea "+fileManager.getRow()+": Falto '&' para representar el operador logico AND";
            throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
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
            // TODO lexicalexceptionlogicor
            String msg = "Error lexico en linea "+fileManager.getRow()+": Falto '|' para representar el operador logico OR.";
            throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
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

    private Token e39() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e40();
        }else{
            return new Token("Entero", lexeme, fileManager.getRow());
        }
    }

    private Token e40() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e41();
        }else{
            return new Token("Entero", lexeme, fileManager.getRow());
        }
    }

    private Token e41() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e42();
        }else{
            return new Token("Entero", lexeme, fileManager.getRow());
        }
    }

    private Token e42() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e43();
        }else{
            return new Token("Entero", lexeme, fileManager.getRow());
        }
    }

    private Token e43() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e44();
        }else{
            return new Token("Entero", lexeme, fileManager.getRow());
        }
    }

    private Token e44() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e45();
        }else{
            return new Token("Entero", lexeme, fileManager.getRow());
        }
    }

    private Token e45() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e46();
        }else{
            return new Token("Entero", lexeme, fileManager.getRow());
        }
    }

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
            // TODO lexicalexceptionint
            String msg = "Error lexico en linea "+fileManager.getRow()+": El entero no puede ser representado, posee mas de 9 digitos.";
            throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    private Token e48() throws LexicalException {
        if(Character.isDigit(actualCharacter) ||
                ((Character.getNumericValue(actualCharacter) <= Character.getNumericValue('F')) &&
                        (Character.getNumericValue(actualCharacter) >= Character.getNumericValue('A')))){
            updateLexeme();
            updateActualCharacter();
            return e49();
        }else{
            // TODO lexicalexceptionunicode
            String msg = "Error lexico en linea "+fileManager.getRow()+": Caracter unicode invalido.";
            throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    private Token e49() throws LexicalException {
        if(Character.isDigit(actualCharacter) ||
                ((Character.getNumericValue(actualCharacter) <= Character.getNumericValue('F')) &&
                        (Character.getNumericValue(actualCharacter) >= Character.getNumericValue('A')))){
            updateLexeme();
            updateActualCharacter();
            return e50();
        }else{
            // TODO lexicalexceptionunicode
            String msg = "Error lexico en linea "+fileManager.getRow()+": Caracter unicode invalido.";
            throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    private Token e50() throws LexicalException {
        if(Character.isDigit(actualCharacter) ||
                ((Character.getNumericValue(actualCharacter) <= Character.getNumericValue('F')) &&
                        (Character.getNumericValue(actualCharacter) >= Character.getNumericValue('A')))){
            updateLexeme();
            updateActualCharacter();
            return e51();
        }else{
            // TODO lexicalexceptionunicode
            String msg = "Error lexico en linea "+fileManager.getRow()+": Caracter unicode invalido.";
            throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    private Token e51() throws LexicalException {
        if(Character.isDigit(actualCharacter) ||
                ((Character.getNumericValue(actualCharacter) <= Character.getNumericValue('F')) &&
                        (Character.getNumericValue(actualCharacter) >= Character.getNumericValue('A')))){
            updateLexeme();
            updateActualCharacter();
            return e52();
        }else{
            // TODO lexicalexceptionunicode
            String msg = "Error lexico en linea "+fileManager.getRow()+": Caracter unicode invalido.";
            throw new LexicalException(msg, lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    private Token e52(){
        return new Token("CaracterUnicode", lexeme, fileManager.getRow());
    }

    private Token e53(){
        if(Character.isLetterOrDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e53();
        }else{
            return new Token("IdentificadorClase", lexeme, fileManager.getRow());
        }
    }

    // EOF recognizer
    private Token ex(){
        return new Token("EOF", lexeme, fileManager.getRow());
    }

}
