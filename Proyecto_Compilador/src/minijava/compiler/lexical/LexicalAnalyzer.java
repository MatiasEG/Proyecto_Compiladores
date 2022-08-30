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
    private int positionErrorOnInteger;

    public LexicalAnalyzer(FileManager fileManager){
        multilineCommentLine = -1;
        multilineCommentColumn = -1;
        positionErrorOnInteger = -1;
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
        if(Character.isLetter(actualCharacter) && actualCharacter <= 'Z' && actualCharacter >= 'A') {
            updateLexeme();
            updateActualCharacter();
            return e1();
        }else if(Character.isLetter(actualCharacter) && actualCharacter <= 'z' && actualCharacter >= 'a'){
            updateLexeme();
            updateActualCharacter();
            return e2();
        }else if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            positionErrorOnInteger = -1;
            return e3();
        }else if (actualCharacter == '"'){
            updateLexeme();
            updateActualCharacter();
            return e13();
        }else if (actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e16();
        }else if (actualCharacter == '>'){
            updateLexeme();
            updateActualCharacter();
            return e18();
        }else if (actualCharacter == '<'){
            updateLexeme();
            updateActualCharacter();
            return e20();
        }else if (actualCharacter == '!'){
            updateLexeme();
            updateActualCharacter();
            return e22();
        }else if (actualCharacter == '+'){
            updateLexeme();
            updateActualCharacter();
            return e24();
        }else if (actualCharacter == '-'){
            updateLexeme();
            updateActualCharacter();
            return e26();
        }else if (actualCharacter == '*'){
            updateLexeme();
            updateActualCharacter();
            return e28();
        }else if (actualCharacter == '/'){
            updateLexeme();
            updateActualCharacter();
            return e29();
        }else if (actualCharacter == '%'){
            updateLexeme();
            updateActualCharacter();
            return e30();
        }else if (actualCharacter == '&'){
            updateLexeme();
            updateActualCharacter();
            return e31();
        }else if (actualCharacter == '|'){
            updateLexeme();
            updateActualCharacter();
            return e33();
        }else if (actualCharacter == '{'){
            updateLexeme();
            updateActualCharacter();
            return e38();
        }else if (actualCharacter == '}'){
            updateLexeme();
            updateActualCharacter();
            return e39();
        }else if (actualCharacter == '('){
            updateLexeme();
            updateActualCharacter();
            return e40();
        }else if (actualCharacter == ')'){
            updateLexeme();
            updateActualCharacter();
            return e41();
        }else if (actualCharacter == ';'){
            updateLexeme();
            updateActualCharacter();
            return e42();
        }else if (actualCharacter == ','){
            updateLexeme();
            updateActualCharacter();
            return e43();
        }else if (actualCharacter == '.'){
            updateLexeme();
            updateActualCharacter();
            return e44();
        }else if (actualCharacter == '\''){
            updateLexeme();
            updateActualCharacter();
            return e45();
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

    // Method/Variable identifier recognizer
    private Token e1(){
        if(Character.isLetterOrDigit(actualCharacter) || actualCharacter == '_'){
            updateLexeme();
            updateActualCharacter();
            return e1();
        }else{
            return new Token("idClass", lexeme, fileManager.getRow());
        }
    }

    // Class identifier recognizer
    private Token e2(){
        if(Character.isLetterOrDigit(actualCharacter) || actualCharacter == '_'){
            updateLexeme();
            updateActualCharacter();
            return e2();
        }else if (ReservedWords.belongs(lexeme)){
            return new Token(ReservedWords.getToken(lexeme), lexeme, fileManager.getRow());
        }else{
            return new Token("idMetVar", lexeme, fileManager.getRow());
        }
    }

    // Digit recognizer
    private Token e3() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e4();
        }else{
            return new Token("LiteralInteger", lexeme, fileManager.getRow());
        }
    }

    // Digit recognizer
    private Token e4() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e5();
        }else{
            return new Token("literalInteger", lexeme, fileManager.getRow());
        }
    }

    // Digit recognizer
    private Token e5() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e6();
        }else{
            return new Token("literalInteger", lexeme, fileManager.getRow());
        }
    }

    // Digit recognizer
    private Token e6() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e7();
        }else{
            return new Token("literalInteger", lexeme, fileManager.getRow());
        }
    }

    // Digit recognizer
    private Token e7() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e8();
        }else{
            return new Token("literalInteger", lexeme, fileManager.getRow());
        }
    }

    // Digit recognizer
    private Token e8() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e9();
        }else{
            return new Token("literalInteger", lexeme, fileManager.getRow());
        }
    }

    // Digit recognizer
    private Token e9() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e10();
        }else{
            return new Token("literalInteger", lexeme, fileManager.getRow());
        }
    }

    // Digit recognizer
    private Token e10() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateLexeme();
            updateActualCharacter();
            return e11();
        }else{
            return new Token("literalInteger", lexeme, fileManager.getRow());
        }
    }

    // Digit recognizer
    private Token e11() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateActualCharacter();
            positionErrorOnInteger = fileManager.getColumn()-1;
            return e12();
        }else{
            return new Token("literalInteger", lexeme, fileManager.getRow());
        }
    }

    private Token e12() throws LexicalException {
        if(Character.isDigit(actualCharacter)){
            updateActualCharacter();
            return e12();
        }else{
            throw new LexicalExceptionInteger(lexeme, fileManager.getRow(), positionErrorOnInteger);
        }
    }

    private Token e13() throws LexicalException {
        if (actualCharacter == '\\'){
            updateLexeme();
            updateActualCharacter();
            return e14();
        }else if (actualCharacter == '"'){
            updateLexeme();
            updateActualCharacter();
            return e15();
        }else if (actualCharacter == '\n' || actualCharacter == '\u0000'){
            throw new LexicalExceptionString(lexeme, fileManager.getRow(), fileManager.getColumn());
        }else{
            updateLexeme();
            updateActualCharacter();
            return e13();
        }
    }

    private Token e14() throws LexicalException {
        if(actualCharacter == '\n' || actualCharacter == '\u0000'){
            throw new LexicalExceptionString(lexeme, fileManager.getRow(), fileManager.getColumn());
        }else{
            updateLexeme();
            updateActualCharacter();
            return e13();
        }
    }

    // String recognizer
    private Token e15(){
        return new Token("literalString", lexeme, fileManager.getRow());
    }

    // "=" recognizer
    private Token e16(){
        if (actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e17();
        }else{
            return new Token("assignment", lexeme, fileManager.getRow());
        }
    }

    // "==" recognizer
    private Token e17(){
        return new Token("opEqual", lexeme, fileManager.getRow());
    }

    // ">" recognizer
    private Token e18(){
        if (actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e19();
        }else{
            return new Token("opGreater", lexeme, fileManager.getRow());
        }
    }

    // ">=" recognizer
    private Token e19(){
        return new Token("opGreaterOrEqual", lexeme, fileManager.getRow());
    }

    // "<" recognizer
    private Token e20(){
        if (actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e21();
        }else{
            return new Token("opLess", lexeme, fileManager.getRow());
        }
    }

    // "<=" recognizer
    private Token e21(){
        return new Token("opLessOrEqual", lexeme, fileManager.getRow());
    }

    // "!" recognizer
    private Token e22(){
        if (actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e23();
        }else{
            return new Token("opNegation", lexeme, fileManager.getRow());
        }
    }

    // "!=" recognizer
    private Token e23(){
        return new Token("opDistinct", lexeme, fileManager.getRow());
    }

    // "+" recognizer
    private Token e24(){
        if(actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e25();
        }else{
            return new Token("opAddition", lexeme, fileManager.getRow());
        }
    }

    // '+=' recognizer
    private Token e25(){
        return new Token("assignmentAddition", lexeme, fileManager.getRow());
    }

    // '-' recognizer
    private Token e26(){
        if(actualCharacter == '='){
            updateLexeme();
            updateActualCharacter();
            return e27();
        }else{
            return new Token("opSubtraction", lexeme, fileManager.getRow());
        }
    }

    // '-=' recognizer
    private Token e27(){
        return new Token("assignmentSubtraction", lexeme, fileManager.getRow());
    }

    // '*' recognizer
    private Token e28(){
        return new Token("opMultiplication", lexeme, fileManager.getRow());
    }

    // "/" recognizer
    private Token e29() throws LexicalException {
        if (actualCharacter == '/') {
            updateLexeme();
            updateActualCharacter();
            return e35();
        }else if (actualCharacter == '*'){
            updateLexeme();
            updateActualCharacter();
            multilineCommentColumn = fileManager.getColumn()-2;
            return e36();
        }else{
            return new Token("opDivision", lexeme, fileManager.getRow());
        }
    }

    // '%' recognizer
    private Token e30(){
        return new Token("opModule", lexeme, fileManager.getRow());
    }

    private Token e31() throws LexicalException {
        if(actualCharacter == '&'){
            updateLexeme();
            updateActualCharacter();
            return e32();
        }else{
            throw new LexicalExceptionLogicAnd(lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    // '&&' recognizer
    private Token e32(){
        return new Token("opLogicAnd", lexeme, fileManager.getRow());
    }

    private Token e33() throws LexicalException {
        if(actualCharacter == '|'){
            updateLexeme();
            updateActualCharacter();
            return e34();
        }else{
            throw new LexicalExceptionLogicOr(lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    // '||' recognizer
    private Token e34(){
        return new Token("opLogicOr", lexeme, fileManager.getRow());
    }

    // One line comment recognizer
    private Token e35() throws LexicalException {
        if (actualCharacter == '\n' || actualCharacter == '\u0000'){
            lexeme = ""; /* Comment finished */
            return e0();
        }else{
            updateLexeme();
            updateActualCharacter();
            return e35();
        }
    }

    private Token e36() throws LexicalException {
        if (actualCharacter == '*'){
            if(multilineCommentLine == -1) updateLexeme();
            updateActualCharacter();
            return e37();
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
            return e36();
        }else{
            if(multilineCommentLine == -1) updateLexeme();
            updateActualCharacter();
            return e36();
        }
    }

    // Multi-line commentary recognizer
    private Token e37() throws LexicalException {
        if (actualCharacter == '/') { /* Comment finished */
            if(multilineCommentLine == -1) updateLexeme();
            updateActualCharacter();
            lexeme = "";
            return e0();
        }else if (actualCharacter == '*'){
            if(multilineCommentLine == -1) updateLexeme();
            updateActualCharacter();
            return e37();
        }else if (actualCharacter == '\u0000'){
            if(multilineCommentLine == -1){
                throw new LexicalExceptionCommentary(lexeme, fileManager.getRow(), fileManager.getColumn());
            }else{
                throw new LexicalExceptionCommentary(lexeme, multilineCommentLine, multilineCommentColumn);
            }
        }else{
            if(multilineCommentLine == -1) updateLexeme();
            updateActualCharacter();
            return e36();
        }
    }

    // "{" recognizer
    private Token e38(){
        return new Token("punctuationOpeningBracket", lexeme, fileManager.getRow());
    }

    // "}" recognizer
    private Token e39(){
        return new Token("punctuationClosingBracket", lexeme, fileManager.getRow());
    }

    // '(' recognizer
    private Token e40(){
        return new Token("punctuationOpeningParenthesis", lexeme, fileManager.getRow());
    }

    // ')' recognizer
    private Token e41(){
        return new Token("punctuationClosingParenthesis", lexeme, fileManager.getRow());
    }

    // ';' recognizer
    private Token e42(){
        return new Token("punctuationSemicolon", lexeme, fileManager.getRow());
    }

    // ',' recognizer
    private Token e43(){
        return new Token("punctuationComma", lexeme, fileManager.getRow());
    }

    // '.' recognizer
    private Token e44(){
        return new Token("punctuationPoint", lexeme, fileManager.getRow());
    }

    private Token e45() throws LexicalException {
        if (actualCharacter == '\\'){
            updateLexeme();
            updateActualCharacter();
            return e48();
        }else if (actualCharacter == '\''){
            throw new LexicalExceptionCharacterEmpty(lexeme, fileManager.getRow(), fileManager.getColumn());
        }else if (actualCharacter == '\n' || actualCharacter == '\u0000'){
            throw new LexicalExceptionCharacterNotClosed(lexeme, fileManager.getRow(), fileManager.getColumn());
        }else{
            updateLexeme();
            updateActualCharacter();
            return e46();
        }
    }

    private Token e46() throws LexicalException {
        if (actualCharacter == '\''){
            updateLexeme();
            updateActualCharacter();
            return e47();
        }else{
            throw new LexicalExceptionCharacterNotClosed(lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    // Character recognizer
    private Token e47(){
        return new Token("literalCharacter", lexeme, fileManager.getRow());
    }

    private Token e48() throws LexicalException {
        if(actualCharacter == '\n' || actualCharacter == '\u0000'){
            throw new LexicalExceptionCharacterNotClosed(lexeme, fileManager.getRow(), fileManager.getColumn());
        }else if (actualCharacter == 'u'){
            updateLexeme();
            updateActualCharacter();
            return e49();
        }else{
            updateLexeme();
            updateActualCharacter();
            return e46();
        }
    }

    private Token e49() throws LexicalException {
        if(Character.isDigit(actualCharacter) ||
                ((Character.getNumericValue(actualCharacter) <= Character.getNumericValue('F')) &&
                        (Character.getNumericValue(actualCharacter) >= Character.getNumericValue('A')))) {
            updateLexeme();
            updateActualCharacter();
            return e50();
        }else if (actualCharacter == 'u'){
            updateLexeme();
            updateActualCharacter();
            return e47();
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
        if(Character.isDigit(actualCharacter) ||
                ((Character.getNumericValue(actualCharacter) <= Character.getNumericValue('F')) &&
                        (Character.getNumericValue(actualCharacter) >= Character.getNumericValue('A')))){
            updateLexeme();
            updateActualCharacter();
            return e52();
        }else{
            throw new LexicalExceptionUnicode(lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    private Token e52() throws LexicalException {
        if (Character.isDigit(actualCharacter) ||
                ((Character.getNumericValue(actualCharacter) <= Character.getNumericValue('F')) &&
                        (Character.getNumericValue(actualCharacter) >= Character.getNumericValue('A')))){
            updateLexeme();
            updateActualCharacter();
            return e53();
        }else{
            throw new LexicalExceptionUnicode(lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    private Token e53() throws LexicalExceptionUnicode {
        if (actualCharacter == '\''){
            updateLexeme();
            updateActualCharacter();
            return e47();
        }else{
            throw new LexicalExceptionUnicode(lexeme, fileManager.getRow(), fileManager.getColumn());
        }
    }

    // EOF recognizer
    private Token ex(){
        return new Token("EOF", lexeme, fileManager.getRow());
    }

}
