package minijava.compiler.lexical;

import minijava.compiler.exception.*;
import minijava.compiler.exception.specificexceptions.*;
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
        multilineCommentColumn = -1;
        multilineCommentLine = -1;
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
            return e54();
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
            throw new LexicalExceptionGeneric(lexeme, fileManager.getRow(), fileManager.getColumn()-1);
        }
    }

    // Digit recognizer
    private Token e1() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e39();
        }else{
            return new Token("idInteger", lexeme, fileManager.getRow());
        }
    }

    // Identifier recognizer
    private Token e2(){
        if(Character.isLetterOrDigit(actualCharacter) || actualCharacter == '_'){
            updateLexeme();
            updateActualCharacter();
            return e2();
        }else if (ReservedWords.belongs(lexeme)){
            return new Token("idKeyWord", lexeme, fileManager.getRow());
        }else{
            return new Token("idMetVar", lexeme, fileManager.getRow());
        }
    }

    // ">" recognizer
    private Token e3(){
        if (actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e4();
        }else{
            return new Token("opGreater", lexeme, fileManager.getRow());
        }
    }

    // ">=" recognizer
    private Token e4(){
        return new Token("opGreaterOrEqual", lexeme, fileManager.getRow());
    }

    // "<" recognizer
    private Token e5(){
        if (actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e55();
        }else{
            return new Token("opLess", lexeme, fileManager.getRow());
        }
    }

    // "!" recognizer
    private Token e6(){
        if (actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e9();
        }else{
            return new Token("opNegation", lexeme, fileManager.getRow());
        }
    }

    // "=" recognizer
    private Token e7(){
        if (actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e8();
        }else{
            return new Token("assignment", lexeme, fileManager.getRow());
        }
    }

    // "==" recognizer
    private Token e8(){
        return new Token("opEqual", lexeme, fileManager.getRow());
    }

    // "!=" recognizer
    private Token e9(){
        return new Token("opDistinct", lexeme, fileManager.getRow());
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
            throw new LexicalExceptionString(lexeme, fileManager.getRow(), fileManager.getColumn());
        }else{
            updateLexeme();
            updateActualCharacter();
            return e10();
        }
    }

    private Token e11() throws LexicalException {
        if(actualCharacter == '\n' || actualCharacter == '\u0000'){
            throw new LexicalExceptionString(lexeme, fileManager.getRow(), fileManager.getColumn());
        }else{
            updateLexeme();
            updateActualCharacter();
            return e10();
        }
    }

    // String recognizer
    private Token e12(){
        return new Token("stringLiteral", lexeme, fileManager.getRow());
    }

    // "+" recognizer
    private Token e13(){
        if(actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e37();
        }else{
            return new Token("opAddition", lexeme, fileManager.getRow());
        }
    }

    // "{" recognizer
    private Token e14(){
        return new Token("punctuationOpeningBracket", lexeme, fileManager.getRow());
    }

    // "}" recognizer
    private Token e15(){
        return new Token("punctuationClosingBracket", lexeme, fileManager.getRow());
    }

    private Token e16() throws LexicalException {
        if (actualCharacter == '/') {
            updateLexeme();
            updateActualCharacter();
            return e17();
        }else if (actualCharacter == '*'){
            updateLexeme();
            updateActualCharacter();
            multilineCommentColumn = fileManager.getColumn()-2;
            return e18();
        }else{
            return new Token("opDivision", lexeme, fileManager.getRow());
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
                throw new LexicalExceptionCommentary(lexeme, fileManager.getRow(), multilineCommentColumn);
            }else{
                throw new LexicalExceptionCommentary(lexeme, multilineCommentLine, multilineCommentColumn);
            }
        }else if (actualCharacter == '\n'){
            if(multilineCommentLine == -1){
                multilineCommentLine = fileManager.getRow();
            }
            updateActualCharacter();
            return e18();
        }else{
            if(multilineCommentLine == -1) updateLexeme();
            updateActualCharacter();
            return e18();
        }
    }

    // Multi-line commentary recognizer
    private Token e19() throws LexicalException {
        if (actualCharacter == '/') { /* Comment finished */
            if(multilineCommentLine == -1) updateLexeme();
            updateActualCharacter();
            lexeme = "";
            return e0();
        }else if (actualCharacter == '*'){
            if(multilineCommentLine == -1) updateLexeme();
            updateActualCharacter();
            return e19();
        }else if (actualCharacter == '\u0000'){
            if(multilineCommentLine == -1){
                throw new LexicalExceptionCommentary(lexeme, fileManager.getRow(), fileManager.getColumn());
            }else{
                throw new LexicalExceptionCommentary(lexeme, multilineCommentLine, multilineCommentColumn);
            }
        }else{
            if(multilineCommentLine == -1) updateLexeme();
            updateActualCharacter();
            return e18();
        }
    }

    private Token e21() throws LexicalException {
        if (actualCharacter == '\\'){
            updateLexeme();
            updateActualCharacter();
            return e24();
        }else if (actualCharacter == '\''){
            throw new LexicalExceptionCharacterEmpty(lexeme, fileManager.getRow(), fileManager.getColumn());
        }else if (actualCharacter == '\n' || actualCharacter == '\u0000'){
            throw new LexicalExceptionCharacterNotClosed(lexeme, fileManager.getRow(), fileManager.getColumn());
        }else{
            updateLexeme();
            updateActualCharacter();
            return e22();
        }
    }

    private Token e22() throws LexicalException {
        if (actualCharacter == '\''){
            updateLexeme();
            updateActualCharacter();
            return e23();
        }else{
            throw new LexicalExceptionCharacterNotClosed(lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    // Character recognizer
    private Token e23(){
        return new Token("idCharacter", lexeme, fileManager.getRow());
    }

    private Token e24() throws LexicalException {
        if(actualCharacter == '\n' || actualCharacter == '\u0000'){
            throw new LexicalExceptionCharacterNotClosed(lexeme, fileManager.getRow(), fileManager.getColumn());
        }else if (actualCharacter == 'u'){
            updateLexeme();
            updateActualCharacter();
            return e48();
        }else{
            updateLexeme();
            updateActualCharacter();
            return e22();
        }
    }

    // '(' recognizer
    private Token e25(){
        return new Token("punctuationOpeningParenthesis", lexeme, fileManager.getRow());
    }

    // ')' recognizer
    private Token e26(){
        return new Token("punctuationClosingParenthesis", lexeme, fileManager.getRow());
    }

    // ';' recognizer
    private Token e27(){
        return new Token("punctuationSemicolon", lexeme, fileManager.getRow());
    }

    // ',' recognizer
    private Token e28(){
        return new Token("punctuationComma", lexeme, fileManager.getRow());
    }

    // '.' recognizer
    private Token e29(){
        return new Token("punctuationPoint", lexeme, fileManager.getRow());
    }

    // '-' recognizer
    private Token e30(){
        if(actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e38();
        }else{
            return new Token("opSubtraction", lexeme, fileManager.getRow());
        }
    }

    // '*' recognizer
    private Token e31(){
        return new Token("opMultiplication", lexeme, fileManager.getRow());
    }

    // '%' recognizer
    private Token e32(){
        return new Token("opModule", lexeme, fileManager.getRow());
    }

    private Token e33() throws LexicalException {
        if(actualCharacter == '&'){
            updateLexeme();
            updateActualCharacter();
            return e34();
        }else{
            throw new LexicalExceptionLogicAnd(lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    // '&&' recognizer
    private Token e34(){
        return new Token("opLogicAnd", lexeme, fileManager.getRow());
    }

    private Token e35() throws LexicalException {
        if(actualCharacter == '|'){
            updateLexeme();
            updateActualCharacter();
            return e36();
        }else{
            throw new LexicalExceptionLogicOr(lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    // '||' recognizer
    private Token e36(){
        return new Token("opLogicOr", lexeme, fileManager.getRow());
    }

    // '+=' recognizer
    private Token e37(){
        return new Token("assignmentAddition", lexeme, fileManager.getRow());
    }

    // '-=' recognizer
    private Token e38(){
        return new Token("assignmentSubtraction", lexeme, fileManager.getRow());
    }

    private Token e39() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e40();
        }else{
            return new Token("idInteger", lexeme, fileManager.getRow());
        }
    }

    private Token e40() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e41();
        }else{
            return new Token("idInteger", lexeme, fileManager.getRow());
        }
    }

    private Token e41() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e42();
        }else{
            return new Token("idInteger", lexeme, fileManager.getRow());
        }
    }

    private Token e42() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e43();
        }else{
            return new Token("idInteger", lexeme, fileManager.getRow());
        }
    }

    private Token e43() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e44();
        }else{
            return new Token("idInteger", lexeme, fileManager.getRow());
        }
    }

    private Token e44() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e45();
        }else{
            return new Token("idInteger", lexeme, fileManager.getRow());
        }
    }

    private Token e45() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e46();
        }else{
            return new Token("idInteger", lexeme, fileManager.getRow());
        }
    }

    private Token e46() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e47();
        }else{
            return new Token("idInteger", lexeme, fileManager.getRow());
        }
    }

    private Token e47() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e47();
        }else{
            throw new LexicalExceptionInteger(lexeme, fileManager.getRow(), fileManager.getColumn());
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
            throw new LexicalExceptionUnicode(lexeme, fileManager.getRow(), fileManager.getColumn());
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
            throw new LexicalExceptionUnicode(lexeme, fileManager.getRow(), fileManager.getColumn());
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
            throw new LexicalExceptionUnicode(lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    private Token e51() throws LexicalException {
        if (Character.isDigit(actualCharacter) ||
                ((Character.getNumericValue(actualCharacter) <= Character.getNumericValue('F')) &&
                        (Character.getNumericValue(actualCharacter) >= Character.getNumericValue('A')))){
            updateLexeme();
            updateActualCharacter();
            return e52();
        }else{
            throw new LexicalExceptionUnicode(lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    private Token e52() throws LexicalExceptionUnicode {
        if (actualCharacter == '\''){
            updateLexeme();
            updateActualCharacter();
            return e53();
        }else{
            throw new LexicalExceptionUnicode(lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    private Token e53(){
        return new Token("CaracterUnicode", lexeme, fileManager.getRow());
    }

    private Token e54(){
        if(Character.isLetterOrDigit(actualCharacter) || actualCharacter == '_'){
            updateLexeme();
            updateActualCharacter();
            return e54();
        }else{
            return new Token("idClass", lexeme, fileManager.getRow());
        }
    }

    // "<=" recognizer
    private Token e55(){
        return new Token("opLessOrEqual", lexeme, fileManager.getRow());
    }

    // EOF recognizer
    private Token ex(){
        return new Token("EOF", lexeme, fileManager.getRow());
    }

}
