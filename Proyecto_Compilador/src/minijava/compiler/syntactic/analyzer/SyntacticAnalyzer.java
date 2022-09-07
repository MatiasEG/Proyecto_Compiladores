package minijava.compiler.syntactic.analyzer;

import minijava.compiler.exception.SyntacticException;
import minijava.compiler.exception.lexical.LexicalException;
import minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import minijava.compiler.lexical.analyzer.Token;

import java.util.Arrays;

public class SyntacticAnalyzer {

    private LexicalAnalyzer lexicalAnalyzer;
    private Token actualToken;

    public SyntacticAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        this.lexicalAnalyzer = lexicalAnalyzer;
    }

    private void match(String tokenName) throws LexicalException, SyntacticException {
        if(tokenName.equals(actualToken.getToken())){
            actualToken = lexicalAnalyzer.nextToken();
        }else{
            throw new SyntacticException(actualToken, Arrays.asList(tokenName));
        }
    }

    // 1 ------------------------------------------------------------------------------
    // <Inicial> ::= <ListaClases>
    public void inicial() throws LexicalException, SyntacticException {
        actualToken = this.lexicalAnalyzer.nextToken();
        listaClases();
        match("");
    }

    // 2 ------------------------------------------------------------------------------
    // <ListaClases> ::= <Clase> <ListaClases> | e
    private void listaClases() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_class").contains(actualToken.getToken())) {
            clase();
            listaClases();
        }else{ // TODO siguientes de listaClases?
            //nada por ahora
        }
    }

    // 3 ------------------------------------------------------------------------------
    // <Clase> ::= <ClaseConcreta> | <Interface>
    private void clase() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_class").contains(actualToken.getToken())){
            claseConcreta();
        }else if(Arrays.asList("idKeyWord_interface").contains(actualToken.getToken())){
            interface_();
        }
    }

    // 4 ------------------------------------------------------------------------------
    // <ClaseConcreta> ::= class idClase <HeredaDe> <ImplementaA> { <ListaMiembros> }
    private void claseConcreta() throws LexicalException, SyntacticException {
        match("idKeyWord_class");
        match("idClass");
        heredaDe();
        implementaA();
        match("punctuationOpeningBracket");
        listaMiembros();
        match("punctuationClosingBracket");
    }

    // 5 ------------------------------------------------------------------------------
    // <Interface> ::= interface idClase <ExtiendeA> { <ListaEncabezados> }
    private void interface_() throws LexicalException, SyntacticException {
        match("idKeyWord_interface");
        match("idClass");
        extiendeA();
        match("punctuationOpeningBracket");
        listaMiembros();
        match("punctuationClosingBracket");
    }

    // 6 ------------------------------------------------------------------------------
    // <HeredaDe> ::= extends idClase | e
    private void heredaDe() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_extends").contains(actualToken.getToken())){
            match("idKeyWord_extends");
            match("idClass");
        }else if(Arrays.asList("idKeyWord_implements" , "punctuationOpeningBracket").contains(actualToken.getToken())){
            // vacio
        }else{
            // Se esperaba un token "implement" o bien el "{" del inicio del bloque de clase.
            throw new SyntacticException(actualToken, Arrays.asList("idKeyWord_extends , idClase"));
        }
    }

    // 7 ------------------------------------------------------------------------------
    // <ImplementaA> ::= implements <ListaTipoReferencia> | e
    private void implementaA() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_implements").contains(actualToken.getToken())){
            match("idKeyWord_implements");
            listaTipoReferencia();
        }else if(Arrays.asList("punctuationOpeningBracket").contains(actualToken.getToken())){
            // vacio
        }else{
            // Se esperaba un "{" del inicio del bloque de clase.
            throw new SyntacticException(actualToken, Arrays.asList("punctuationOpeningBracket"));
        }
    }

    // 8 ------------------------------------------------------------------------------
    // <ExtiendeA> ::= extends <ListaTipoReferencia> | e
    private void extiendeA() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_extends").contains(actualToken.getToken())){
            match("idKeyWord_extends");
            listaTipoReferencia();
        }else if(Arrays.asList("punctuationOpeningBracket").contains(actualToken.getToken())){
            // vacio
        }else{
            // Se esperaba un "{" del inicio del bloque de clase.
            throw new SyntacticException(actualToken, Arrays.asList("punctuationOpeningBracket"));
        }
    }

    // 9 ------------------------------------------------------------------------------
    // <ListaTipoReferencia> ::= idClase <ListaTipoReferenciaResto>
    private void listaTipoReferencia() throws LexicalException, SyntacticException {
        match("idClass");
        listaTipoReferenciaResto();
    }

    // 10 ------------------------------------------------------------------------------
    // <ListaTipoReferenciaResto> ::= , <ListaTipoReferencia> | e
    private void listaTipoReferenciaResto() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationComma").contains(actualToken.getToken())){
            match("punctuationComma");
            listaTipoReferencia();
        }else if(Arrays.asList("punctuationOpeningBracket").contains(actualToken.getToken())){
            // vacio
        }else{
            // Se esperaba un "{" del inicio del bloque de clase.
            throw new SyntacticException(actualToken, Arrays.asList("punctuationOpeningBracket"));
        }
    }

    // 11 ------------------------------------------------------------------------------
    // <ListaMiembros> ::= <Miembro> <ListaMiembros> | e
    private void listaMiembros() throws SyntacticException, LexicalException {
        if(Arrays.asList("idKeyWord_public", "idKeyWord_private", "idKeyWord_static",
                "idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int",
                "idClass", "idKeyWord_void").contains(actualToken.getToken())){
            miembro();
            listaMiembros();
        }else if(Arrays.asList("punctuationClosingBracket").contains(actualToken.getToken())){
            // vacio
        }else{
            // Se esperaba un "}"
            throw new SyntacticException(actualToken, Arrays.asList("punctuationClosingBracket"));
        }
    }

    // 12 ------------------------------------------------------------------------------
    // <ListaEncabezados> ::= <EncabezadoMetodo> ; <ListaEncabezados> | e
    private void listaEncabezados() throws LexicalException, SyntacticException {
        if (Arrays.asList("idKeyWord_static", "idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int",
                "idClass", "idKeyWord_void").contains(actualToken.getToken())) {
            encabezadoMetodo();
            match("punctuationSemicolon");
            listaEncabezados();
        }
    }

    // 13 ------------------------------------------------------------------------------
    // <Miembro> ::= <Atributo> | <Metodo>
    private void miembro() throws SyntacticException, LexicalException {
        if(Arrays.asList("idKeyWord_private", "idKeyWord_public").contains(actualToken.getToken())){
            atributo();
        }else if(Arrays.asList("idKeyWord_static", "idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int",
                "idClass", "idKeyWord_void").contains(actualToken.getToken())){
            metodo();
        }else{
            throw new SyntacticException(actualToken, Arrays.asList("idKeyWord_private", "idKeyWord_public",
                    "idKeyWord_static", "idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass", "idKeyWord_void"));
        }
    }

    // 14 ------------------------------------------------------------------------------
    // <Atributo> ::= <Visibilidad> <Tipo> <ListaDecAtrs> ;
    private void atributo() throws LexicalException, SyntacticException {
        visibilidad();
        tipo();
        listaDecAtrs();
        match("punctuationSemicolon");
    }

    // 15 ------------------------------------------------------------------------------
    // <Metodo> ::= <EncabezadoMetodo> <Bloque>
    private void metodo() throws LexicalException, SyntacticException {
        encabezadoMetodo();
        bloque();
    }

    // 16 ------------------------------------------------------------------------------
    // <EncabezadoMetodo> ::= <EstaticoOpt> <TipoMetodo> idMetVar <ArgsFormales>
    private void encabezadoMetodo() throws LexicalException, SyntacticException {
        estaticoOpt();
        tipoMetodo();
        match("idMetVar");
        argsFormales();
    }

    // 17 ------------------------------------------------------------------------------
    // <Visibilidad> ::= public | private
    private void visibilidad() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_public").contains(actualToken.getToken())){
            match("idKeyWord_public");
        }else if(Arrays.asList("idKeyWord_private").contains(actualToken.getToken())){
            match("idKeyWord_private");
        }else{
            // Se esperaba un token de visibilidad
            throw new SyntacticException(actualToken, Arrays.asList("idKeyWord_public", "idKeyWord_private"));
        }
    }

    // 18 ------------------------------------------------------------------------------
    // <Tipo> ::= <TipoPrimitivo> | idClase
    private void tipo() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int").contains(actualToken.getToken())){
            tipoPrimitivo();
        }else if(Arrays.asList("idClass").contains(actualToken.getToken())){
            match("idClass");
        }else{
            // Se esperaba un tipo para asignar a la variable.
            throw new SyntacticException(actualToken, Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass"));
        }
    }

    // 19 ------------------------------------------------------------------------------
    // <TipoPrimitivo> ::= boolean | char | int
    private void tipoPrimitivo() throws SyntacticException, LexicalException {
        if(Arrays.asList("idKeyWord_boolean").contains(actualToken.getToken())){
            match("idKeyWord_boolean");
        }else if(Arrays.asList("idKeyWord_char").contains(actualToken.getToken())){
            match("idKeyWord_char");
        }else if(Arrays.asList("idKeyWord_int").contains(actualToken.getToken())){
            match("idKeyWord_int");
        }else{
            //Se esperaba un tipo primitivo
            throw new SyntacticException(actualToken, Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int"));
        }
    }

    // 20 ------------------------------------------------------------------------------
    // <ListaDecAtrs> ::= idMetVar <ListaDecAtrsResto>
    private void listaDecAtrs() throws LexicalException, SyntacticException {
        match("idMetVar");
        listaDecAtrsResto();
    }

    // 21 ------------------------------------------------------------------------------
    // <ListaDecAtrsResto> ::= , <ListaDecAtrs> | e
    private void listaDecAtrsResto() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationComma").contains(actualToken.getToken())){
            match("punctuationComma");
            listaDecAtrs();
        }else if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
            // vacio
        }else{
            //Se esperaba otro atributo, o bien un ";" denotando que no se definirian mas atributos.
            throw new SyntacticException(actualToken, Arrays.asList("punctuationComma", "punctuationSemicolon"));
        }
    }

    // 22 ------------------------------------------------------------------------------
    // <EstaticoOpt> ::= static | e
    private void estaticoOpt() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_static").contains(actualToken.getToken())){
            match("idKeyWord_static");
        }else if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass", "idKeyWord_void").contains(actualToken.getToken())){
            // vacio
        }else{
            // Se esperaba una declaracion de static o bien el tipo del metodo
            throw new SyntacticException(actualToken, Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass", "idKeyWord_void", "idKeyWord_static"));
        }
    }

    // 23 ------------------------------------------------------------------------------
    // <TipoMetodo> ::= <Tipo> | void
    private void tipoMetodo() throws SyntacticException, LexicalException {
        if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass").contains(actualToken.getToken())){
            tipo();
        }else if(Arrays.asList("idKeyWord_void").contains(actualToken.getToken())){
            match("idKeyWord_void");
        }else{
            // Se esperaba el tipo del metodo.
            throw new SyntacticException(actualToken, Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass", "idKeyWord_void"));
        }
    }

    // 24 ------------------------------------------------------------------------------
    // <ArgsFormales> ::= ( <ListaArgsFormalesOpt> )
    private void argsFormales() throws LexicalException, SyntacticException {
        match("punctuationOpeningParenthesis");
        listaArgsFormalesOpt();
        match("punctuationClosingParenthesis");
    }

    // 25 ------------------------------------------------------------------------------
    // <ListaArgsFormalesOpt> ::= <ListaArgsFormales> | e
    private void listaArgsFormalesOpt() throws SyntacticException, LexicalException {
        if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass").contains(actualToken.getToken())){
            listaArgsFormales();
        }else if(Arrays.asList("punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            // Se esperaba el tipo de otro argumento, o bien la finalizacion de la declaracion de los mismos con un ")"
            throw new SyntacticException(actualToken, Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass", "punctuationClosingParenthesis"));
        }
    }

    // 26 ------------------------------------------------------------------------------
    // <ListaArgsFormales> ::= <ArgFormal> <ListaArgsFormalesResto>
    private void listaArgsFormales() throws SyntacticException, LexicalException {
        argFormal();
        listaArgsFormalesResto();
    }

    // 27 ------------------------------------------------------------------------------
    // <ListaArgsFormalesResto> ::= , <ListaArgsFormales> | e
    private void listaArgsFormalesResto() throws SyntacticException, LexicalException {
        if(Arrays.asList("punctuationComma").contains(actualToken.getToken())){
            match("punctuationComma");
            listaArgsFormales();
        }else if(Arrays.asList("punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            // Se esperaba la coma para otro argumento o bien la finalicacion de la declaracion de los mismos con un ")"
            throw new SyntacticException(actualToken, Arrays.asList("punctuationComma", "punctuationClosingParenthesis"));
        }
    }

    // 28 ------------------------------------------------------------------------------
    // <ArgFormal> ::= <Tipo> idMetVar
    private void argFormal() throws LexicalException, SyntacticException {
        tipo();
        match("idMetVar");
    }

    // 29 ------------------------------------------------------------------------------
    // <Bloque> ::= { <ListaSentencias> }
    private void bloque() throws LexicalException, SyntacticException {
        match("punctuationOpeningBracket");
        listaSentencias();
        match("punctuationClosingBracket");
    }

    // 30 ------------------------------------------------------------------------------
    // <ListaSentencias> ::= <Sentencia> <ListaSentencias> | e
    private void listaSentencias() throws SyntacticException, LexicalException {
        if(Arrays.asList("punctuationSemicolon", "punctuationOpeningParenthesis", "punctuationOpeningBracket",
                "idKeyWord_this", "idMetVar", "idKeyWord_new", "idClass", "idKeyWord_var",
                "idKeyWord_return", "idKeyWord_if", "idKeyWord_while").contains(actualToken.getToken())){
            sentencia();
            listaSentencias();
        }else if(Arrays.asList("punctuationClosingBracket").contains(actualToken.getToken())){
            // vacio
        }else{
            // TODO definir error
            throw new SyntacticException(actualToken, Arrays.asList("punctuationSemicolon", "punctuationOpeningParenthesis", "punctuationOpeningBracket",
                    "idKeyWord_this", "idMetVar", "idKeyWord_new", "idClass", "idKeyWord_var",
                    "idKeyWord_return", "idKeyWord_if", "idKeyWord_while", "punctuationClosingBracket"));
        }
    }

    // 31 ------------------------------------------------------------------------------
    // <Sentencia> ::= ; | <Llamada> ; | <VarLocal> ; | <Return> ; | <If> | <While> | <Bloque>
    private void sentencia() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
            match("punctuationSemicolon");
        }else if(Arrays.asList("idKeyWord_this", "idMetVar", "idKeyWord_new", "idClass", "punctuationOpeningParenthesis").contains(actualToken.getToken())){
            llamada();
            match("punctuationSemicolon");
        }else if(Arrays.asList("idKeyWord_var").contains(actualToken.getToken())){
            varLocal();
            match("punctuationSemicolon");
        }else if(Arrays.asList("idKeyWord_return").contains(actualToken.getToken())){
            return_();
            match("punctuationSemicolon");
        }else if(Arrays.asList("idKeyWord_if").contains(actualToken.getToken())){
            if_();
        }else if(Arrays.asList("idKeyWord_while").contains(actualToken.getToken())){
            while_();
        }else if(Arrays.asList("punctuationOpeningBracket").contains(actualToken.getToken())){
            bloque();
        }else{
            // Se esperaba el inicio de una sentencia, o bien una sentencia vacia
            throw new SyntacticException(actualToken, Arrays.asList("punctuationSemicolon",
                    "idKeyWord_this", "idMetVar", "idKeyWord_new", "idClass", "punctuationOpeningParenthesis",
                    "idKeyWord_var",
                    "idKeyWord_return",
                    "idKeyWord_if",
                    "idKeyWord_while",
                    "punctuationOpeningBracket"));
        }
    }

    // 32 ------------------------------------------------------------------------------
    // <Asignacion> ::= <TipoDeAsignacion> <Expresion> | e
    private void asignacion() throws SyntacticException, LexicalException {
        if(Arrays.asList("assignment", "assignmentAddition", "assignmentSubtraction").contains(actualToken.getToken())){
            tipoDeAsignacion();
            expresion();
        }else if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
            // vacio
        }else{
            // Se esperaba una asignacion, o bien una expresion sin asignacion
            throw new SyntacticException(actualToken, Arrays.asList("assignment", "assignmentAddition", "assignmentSubtraction", "punctuationSemicolon"));
        }
    }

    // 33 ------------------------------------------------------------------------------
    // <TipoDeAsignacion> ::= = | += | -=
    private void tipoDeAsignacion() throws SyntacticException, LexicalException {
        if(Arrays.asList("assignment").contains(actualToken.getToken())){
            match("assignment");
        }else if(Arrays.asList("assignmentAddition").contains(actualToken.getToken())){
            match("assignmentAddition");
        }else if(Arrays.asList("assignmentSubtraction").contains(actualToken.getToken())){
            match("assignmentSubtraction");
        }else{
            // Se esperaba un operador de asignacion
            throw new SyntacticException(actualToken, Arrays.asList("assignment", "assignmentAddition", "assignmentSubtraction"));
        }
    }

    // 34 ------------------------------------------------------------------------------
    // <Llamada> ::= <Acceso> <Asignacion>
    private void llamada() throws SyntacticException, LexicalException {
        acceso();
        asignacion();
    }

    // 35 ------------------------------------------------------------------------------
    // <VarLocal> ::= var idMetVar = <Expresion>
    private void varLocal() throws LexicalException, SyntacticException {
        match("idKeyWord_var");
        match("idMetVar");
        match("assignment");
        expresion();
    }

    // 36 ------------------------------------------------------------------------------
    // <Return> ::= return <ExpresionOpt>
    private void return_() throws LexicalException, SyntacticException {
        match("idKeyWord_return");
        expresionOpt();
    }

    // 37 ------------------------------------------------------------------------------
    // <ExpresionOpt> ::= <Expresion> | e
    private void expresionOpt() throws SyntacticException, LexicalException {
        if(Arrays.asList("opAddition", "opSubtraction", "opNegation", "idKeyWord_null",
                "idKeyWord_true", "idKeyWord_false", "LiteralInteger", "literalCharacter","literalString",
                "idKeyWord_this", "idMetVar", "idKeyWord_new", "idClass", "punctuationOpeningParenthesis").contains(actualToken.getToken())){
            expresion();
        }else{
            if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
                // vacio
            }else{
                // Se esperaba un operando o un operador
                throw new SyntacticException(actualToken, Arrays.asList("opAddition", "opSubtraction", "opNegation", "idKeyWord_null",
                        "idKeyWord_true", "idKeyWord_false", "LiteralInteger", "literalCharacter","literalString",
                        "idKeyWord_this", "idMetVar", "idKeyWord_new", "idClass", "punctuationOpeningParenthesis", "punctuationSemicolon"));
            }
        }
    }

    // 38 ------------------------------------------------------------------------------
    // <If> ::= if ( <Expresion> ) <Sentencia> <IfResto>
    private void if_() throws LexicalException, SyntacticException {
        match("idKeyWord_if");
        match("punctuationOpeningParenthesis");
        expresion();
        match("punctuationClosingParenthesis");
        sentencia();
        ifResto();
    }

    // 39 ------------------------------------------------------------------------------
    // <IfResto> ::= else <Sentencia> | e
    private void ifResto() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_else").contains(actualToken.getToken())){
            match("idKeyWord_else");
            sentencia();
        }else if(Arrays.asList("punctuationSemicolon", "idKeyWord_this", "idMetVar", "idClass",
                "idKeyWord_new", "punctuationOpeningParenthesis", "idKeyWord_var", "idKeyWord_return",
                "idKeyWord_if", "idKeyWord_while", "punctuationOpeningBracket", "punctuationClosingBracket").contains(actualToken.getToken())){
            // vacio
        }else{
            // Se esperaba un else, o bien otra sentencia
            throw new SyntacticException(actualToken, Arrays.asList("punctuationSemicolon", "idKeyWord_this", "idMetVar", "idClass",
                    "idKeyWord_new", "punctuationOpeningParenthesis", "idKeyWord_var", "idKeyWord_return",
                    "idKeyWord_if", "idKeyWord_while", "punctuationOpeningBracket", "punctuationClosingBracket", "idKeyWord_else"));
        }
    }

    // 40 ------------------------------------------------------------------------------
    // <While> ::= while ( <Expresion> ) <Sentencia>
    private void while_() throws LexicalException, SyntacticException {
        match("idKeyWord_while");
        match("punctuationOpeningParenthesis");
        expresion();
        match("punctuationClosingParenthesis");
        sentencia();
    }

    // 41 ------------------------------------------------------------------------------
    // <Expresion> ::= <ExpresionUnaria> <ExpresionRec>
    private void expresion() throws LexicalException, SyntacticException {
        expresionUnaria();
        expresionRec();
    }

    // 42 ------------------------------------------------------------------------------
    // <ExpresionRec> ::= <OperadorBinario> <ExpresionUnaria> <ExpresionRec> | e
    private void expresionRec() throws SyntacticException, LexicalException {
        if(Arrays.asList("opLogicOr", "opLogicAnd", "opEqual", "opDistinct",
                "opGreater", "opLess", "opGreaterOrEqual", "opLessOrEqual",
                "opAddition", "opSubtraction", "opMultiplication", "opDivision", "opModule").contains(actualToken.getToken())){
            operadorBinario();
            expresionUnaria();
            expresionRec();
        }else if(Arrays.asList("punctuationSemicolon", "punctuationComma", "punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            //No se encontro un operador binario ni un signo de puntuacion
            throw new SyntacticException(actualToken, Arrays.asList("opLogicOr", "opLogicAnd", "opEqual", "opDistinct",
                    "opGreater", "opLess", "opGreaterOrEqual", "opLessOrEqual",
                    "opAddition", "opSubtraction", "opMultiplication", "opDivision", "opModule",
                    "punctuationSemicolon", "punctuationComma", "punctuationClosingParenthesis"));
        }
    }

    // 43 ------------------------------------------------------------------------------
    // <OperadorBinario> ::= || | && | == | != | < | > | <= | >= | + | - | * | / | %
    private void operadorBinario() throws LexicalException, SyntacticException {
        if(Arrays.asList("opLogicOr").contains(actualToken.getToken())){
            match("opLogicOr");
        }else if(Arrays.asList("opLogicAnd").contains(actualToken.getToken())){
            match("opLogicAnd");
        }else if(Arrays.asList("opEqual").contains(actualToken.getToken())){
            match("opEqual");
        }else if(Arrays.asList("opDistinct").contains(actualToken.getToken())){
            match("opDistinct");
        }else if(Arrays.asList("opLess").contains(actualToken.getToken())){
            match("opLess");
        }else if(Arrays.asList("opGreater").contains(actualToken.getToken())){
            match("opGreater");
        }else if(Arrays.asList("opLessOrEqual").contains(actualToken.getToken())){
            match("opLessOrEqual");
        }else if(Arrays.asList("opGreaterOrEqual").contains(actualToken.getToken())){
            match("opGreaterOrEqual");
        }else if(Arrays.asList("opAddition").contains(actualToken.getToken())){
            match("opAddition");
        }else if(Arrays.asList("opSubtraction").contains(actualToken.getToken())){
            match("opSubtraction");
        }else if(Arrays.asList("opMultiplication").contains(actualToken.getToken())){
            match("opMultiplication");
        }else if(Arrays.asList("opDivision").contains(actualToken.getToken())){
            match("opDivision");
        }else if(Arrays.asList("opModule").contains(actualToken.getToken())){
            match("opModule");
        }else{
            // Se esperaba un operador binario
            throw new SyntacticException(actualToken, Arrays.asList("opLogicOr", "opLogicAnd", "opEqual", "opDistinct",
                    "opGreater", "opLess", "opGreaterOrEqual", "opLessOrEqual",
                    "opAddition", "opSubtraction", "opMultiplication", "opDivision", "opModule"));
        }
    }

    // 44 ------------------------------------------------------------------------------
    // <ExpresionUnaria> ::= <OperadorUnario> <Operando> | <Operando>
    private void expresionUnaria() throws LexicalException, SyntacticException {
        if(Arrays.asList("opAddition", "opSubtraction", "opNegation").contains(actualToken.getToken())){
            operadorUnario();
            operando();
        }else if(Arrays.asList("idKeyWord_null", "idKeyWord_true", "idKeyWord_false",
                "LiteralInteger", "literalCharacter","literalString",
                "idKeyWord_this", "idMetVar", "idKeyWord_new", "idClass", "punctuationOpeningParenthesis").contains(actualToken.getToken())){
            operando();
        }
    }

    // 45 ------------------------------------------------------------------------------
    // <OperadorUnario> ::= + | - | !
    private void operadorUnario() throws LexicalException, SyntacticException {
        if(Arrays.asList("opAddition").contains(actualToken.getToken())){
            match("opAddition");
        }else if(Arrays.asList("opSubtraction").contains(actualToken.getToken())){
            match("opSubtraction");
        }else if(Arrays.asList("opNegation").contains(actualToken.getToken())){
            match("opNegation");
        }else{
            // Se esperaba un operador unario
            throw new SyntacticException(actualToken, Arrays.asList("opAddition", "opSubtraction", "opNegation"));
        }
    }

    private void operando(){}

    private void acceso(){}












}
