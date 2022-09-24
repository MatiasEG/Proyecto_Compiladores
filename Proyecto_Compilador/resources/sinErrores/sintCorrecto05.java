// Probando metodos

class Correcto05{

    void test(){
        if(Arrays.asList("assignment", "assignmentAddition", "assignmentSubtraction") || ( a == 4))
            b = a;
    }

    // 53 ------------------------------------------------------------------------------
    // <ExpresionParentizada> ::= ( <Expresion> )
    void expresionParentizada() {   //throws LexicalException, SyntacticException {
        match("punctuationOpeningParenthesis");
        expresion();
        match("punctuationClosingParenthesis");
    }

    // 54 ------------------------------------------------------------------------------
    // <AccesoMetodo> ::= <ArgsActuales> | e
    void accesoMetodo() {   //throws SyntacticException, LexicalException {
        if(Arrays.asList("punctuationOpeningParenthesis").contains(actualToken.getToken())){
            argsActuales();
        }else if(Arrays.asList("assignment", "assignmentAddition", "assignmentSubtraction",
                "punctuationSemicolon", "punctuationComma", "punctuationPoint",
                "opLogicOr", "opLogicAnd", "opEqual", "opDistinct",
                "opGreater", "opGreaterOrEqual", "opLess", "opLessOrEqual",
                "opAddition", "opSubtraction", "opMultiplication", "opDivision", "opModule",
                "punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            // TODO el caso de error 3 entra por aca no entiendo por que...
            // Se esperaban los argumentos del metodo
            //throw new
                    SyntacticException.throwNew(actualToken, Arrays.asList("punctuationOpeningParenthesis",
                    "assignment", "assignmentAddition", "assignmentSubtraction",
                    "punctuationSemicolon", "punctuationComma", "punctuationPoint",
                    "opLogicOr", "opLogicAnd", "opEqual", "opDistinct",
                    "opGreater", "opGreaterOrEqual", "opLess", "opLessOrEqual",
                    "opAddition", "opSubtraction", "opMultiplication", "opDivision", "opModule",
                    "punctuationClosingParenthesis"), lexicalAnalyzer.getActualRow());
        }
    }
}