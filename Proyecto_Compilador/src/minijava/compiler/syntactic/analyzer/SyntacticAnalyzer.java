package minijava.compiler.syntactic.analyzer;

import minijava.compiler.exception.SyntacticException;
import minijava.compiler.exception.lexical.LexicalException;
import minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;

import java.util.Arrays;

public class SyntacticAnalyzer {

    private LexicalAnalyzer lexicalAnalyzer;
    private Token actualToken;
    private SymbolTable st;

    public SyntacticAnalyzer(LexicalAnalyzer lexicalAnalyzer, SymbolTable st) {
        this.lexicalAnalyzer = lexicalAnalyzer;
        this.st = st;
    }

    private void match(String tokenName) throws LexicalException, SyntacticException {
        if(tokenName.equals(actualToken.getToken())){
            actualToken = lexicalAnalyzer.nextToken();
        }else{
            throw new SyntacticException(actualToken, "{ "+tokenName+" }");
        }
    }

    // 1 ------------------------------------------------------------------------------
    // <Inicial> ::= <ListaClases>
    // Primeros: {class, interface, e}
    // Siguientes: -
    public void inicial() throws LexicalException, SyntacticException {
        actualToken = this.lexicalAnalyzer.nextToken();
        if(Arrays.asList("idKeyWord_class", "idKeyWord_interface").contains(actualToken.getToken())){
            listaClases();
        }else if(Arrays.asList("").contains(actualToken.getToken())){
            //vacio
            match("");
        }else{
            throw new SyntacticException(actualToken, "{class, interface, \"\" }");
        }
    }

    // 2 ------------------------------------------------------------------------------
    // <ListaClases> ::= <Clase> <ListaClases> | e
    // Primeros: {class, interface, e}
    // Siguientes: { e }
    private void listaClases() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_class", "idKeyWord_interface").contains(actualToken.getToken())) {
            clase();
            listaClases();
        }else if(Arrays.asList("").contains(actualToken.getToken())){
            //vacio
        }else{
            throw new SyntacticException(actualToken, "{class, interface, \"\" }");
        }
    }

    // 3 ------------------------------------------------------------------------------
    // <Clase> ::= <ClaseConcreta> | <Interface>
    // Primeros: {class, interface}
    // Siguientes: -
    private void clase() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_class").contains(actualToken.getToken())){
            claseConcreta();
        }else if(Arrays.asList("idKeyWord_interface").contains(actualToken.getToken())){
            interface_();
        }else{
            throw new SyntacticException(actualToken, "{class, interface}");
        }
    }

    // 4 ------------------------------------------------------------------------------
    // <ClaseConcreta> ::= class idClase <GenericoOpt> <HeredaDe> <ImplementaA> { <ListaMiembros> }
    // Primeros: {class}
    // Siguientes: -
    private void claseConcreta() throws LexicalException, SyntacticException {
        match("idKeyWord_class");
        match("idClass");
        genericoOpt();
        heredaDe();
        implementaA();
        match("punctuationOpeningBracket");
        listaMiembros();
        match("punctuationClosingBracket");
    }

    // 5 ------------------------------------------------------------------------------
    // <Interface> ::= interface idClase <GenericoOpt> <ExtiendeA> { <ListaEncabezados> }
    // Primeros: {interface}
    // Siguientes: -
    private void interface_() throws LexicalException, SyntacticException {
        match("idKeyWord_interface");
        match("idClass");
        genericoOpt();
        extiendeA();
        match("punctuationOpeningBracket");
        listaEncabezados();
        match("punctuationClosingBracket");
    }

    // 6 ------------------------------------------------------------------------------
    // <HeredaDe> ::= extends idClase <GenericoOpt> | e
    // Primeros: {extends, e}
    // Siguientes: {implments, { }
    private void heredaDe() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_extends").contains(actualToken.getToken())){
            match("idKeyWord_extends");
            match("idClass");
            genericoOpt();
        }else if(Arrays.asList("idKeyWord_implements", "punctuationOpeningBracket").contains(actualToken.getToken())){
            //vacio
        }else{
            throw new SyntacticException(actualToken, "{extends, implements, { }");
        }
    }

    // 7 ------------------------------------------------------------------------------
    // <ImplementaA> ::= implements <ListaTipoReferencia> | e
    // Primeros: {implements, e}
    // Siguientes: { { }
    private void implementaA() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_implements").contains(actualToken.getToken())){
            match("idKeyWord_implements");
            listaTipoReferencia();
        }else if(Arrays.asList("punctuationOpeningBracket").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{implements, {");
        }
    }

    // 8 ------------------------------------------------------------------------------
    // <ExtiendeA> ::= extends <ListaTipoReferencia> | e
    // Primeros: {extends, e}
    // Siguientes: { { }
    private void extiendeA() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_extends").contains(actualToken.getToken())){
            match("idKeyWord_extends");
            listaTipoReferencia();
        }else if(Arrays.asList("punctuationOpeningBracket").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{extends, { }");
        }
    }

    // 9 ------------------------------------------------------------------------------
    // <ListaTipoReferencia> ::= idClase <GenericoOpt> <ListaTipoReferenciaResto>
    // Primeros: {idClase}
    // Siguientes: -
    private void listaTipoReferencia() throws LexicalException, SyntacticException {
        match("idClass");
        genericoOpt();
        listaTipoReferenciaResto();
    }

    // 10 ------------------------------------------------------------------------------
    // <ListaTipoReferenciaResto> ::= , <ListaTipoReferencia> | e
    // Primeros: { , , e }
    // Siguientes: { { }
    private void listaTipoReferenciaResto() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationComma").contains(actualToken.getToken())){
            match("punctuationComma");
            listaTipoReferencia();
        }else if(Arrays.asList("punctuationOpeningBracket").contains(actualToken.getToken())){
            //vacio
        }else{
            throw new SyntacticException(actualToken, "{ , , { }");
        }
    }

    // 11 ------------------------------------------------------------------------------
    // <ListaMiembros> ::= <Miembro> <ListaMiembros> | e
    // Primeros: {public, private, idClase, boolean, char, int, void, static, e}
    // Siguientes: { } }
    private void listaMiembros() throws SyntacticException, LexicalException {
        if(Arrays.asList("idKeyWord_public", "idKeyWord_private", "idClass",
                "idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int",
                "idKeyWord_void", "idKeyWord_static").contains(actualToken.getToken())){
            miembro();
            listaMiembros();
        }else if(Arrays.asList("punctuationClosingBracket").contains(actualToken.getToken())){
            //vacio
        }else{
            throw new SyntacticException(actualToken, "{public, private, idClase, boolean, char, int, void, static, } }");
        }
    }

    // 12 ------------------------------------------------------------------------------
    // <ListaEncabezados> ::= <EncabezadoMetodo> ; <ListaEncabezados> | e
    // Primeros: {static, void, boolean, char, int, idClase, e}
    // Siguientes: { } }
    private void listaEncabezados() throws LexicalException, SyntacticException {
        if (Arrays.asList("idKeyWord_static", "idKeyWord_void", "idKeyWord_boolean",
                "idKeyWord_char", "idKeyWord_int", "idClass").contains(actualToken.getToken())) {
            encabezadoMetodo();
            match("punctuationSemicolon");
            listaEncabezados();
        }else if(Arrays.asList("punctuationClosingBracket").contains(actualToken.getToken())){
            //vacio
        }else{
            throw new SyntacticException(actualToken, "{static, void, boolean, char, int, idClase, } }");
        }
    }

    // 13 ------------------------------------------------------------------------------
    // <EncabezadoMetodo> ::= <EstaticoOpt> <TipoMetodo> idMetVar <ArgsFormales>
    // Primeros: {static, void, boolean, char, int, idClase}
    // Siguientes: -
    private void encabezadoMetodo() throws LexicalException, SyntacticException {
        estaticoOpt();
        tipoMetodo();
        match("idMetVar");
        argsFormales();
    }

    // 14 ------------------------------------------------------------------------------
    // <Miembro> ::= <Visibilidad> <Atributo> | <ConstructorOAtrMet> | <MetodoNoEstaticoVoid> | <MetodoEstatico>
    // Primeros: {public, private, idClase, boolean, char, int, void, static}
    // Siguientes: -
    private void miembro() throws SyntacticException, LexicalException {
        if(Arrays.asList("idKeyWord_private", "idKeyWord_public").contains(actualToken.getToken())) {
            visibilidad();
            atributo();
        }else if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass").contains(actualToken.getToken())){
            constructorOAtrMet();
        }else if(Arrays.asList("idKeyWord_void").contains(actualToken.getToken())) {
            metodoNoEstaticoVoid();
        }else if(Arrays.asList("idKeyWord_static").contains(actualToken.getToken())){
            metodoEstatico();
        }else{
            throw new SyntacticException(actualToken, "{public, private, idClase, boolean, char, int, void, static}");
        }
    }

    // 15 ------------------------------------------------------------------------------
    // <ConstructorOAtrMet> ::= idClase <ConstructorOAtrMetResto> | <TipoPrimitivo> <ConstructorOAtrMetResto> | <AccesoMetodoEstatico>
    // Primeros: {idClase, boolean, char, int, . }
    // Siguientes: -
    private void constructorOAtrMet() throws LexicalException, SyntacticException {
        if(Arrays.asList("idClass").contains(actualToken.getToken())){
            match("idClass");
            constructorOAtrMetResto();
        }else if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int").contains(actualToken.getToken())) {
            tipoPrimitivo();
            constructorOAtrMetResto();
        }else if(Arrays.asList("punctuationPoint").contains(actualToken.getToken())){
            accesoMetodoEstatico();
        }else{
            throw new SyntacticException(actualToken, "{idClase, boolean, char, int}");
        }
    }

    // 16 ------------------------------------------------------------------------------
    // <ConstructorOAtrMetResto> ::= <ArgsFormales> <Bloque> | idMetVar <AtributoOMetodo> | idMetVar <AtributoOMetodo>
    // Primeros: { ( , idMetVar, < }
    // Siguientes: -
    private void constructorOAtrMetResto() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationOpeningParenthesis").contains(actualToken.getToken())){
            argsFormales();
            bloque();
        }else if(Arrays.asList("idMetVar").contains(actualToken.getToken())) {
            match("idMetVar");
            atributoOMetodo();
        }else{
            throw new SyntacticException(actualToken, "{ ( , idMetVar, < }");
        }
    }

    // 17 ------------------------------------------------------------------------------
    // <AtributoOMetodo> ::= <ArgsFormales> <Bloque> | <ListaDecAtrs> ;
    // Primeros: { ( , , , ; , e }
    // Siguientes: {public, private, idClase, boolean, char, int, void, static, } }
    public void atributoOMetodo() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationOpeningParenthesis").contains(actualToken.getToken())){
            argsFormales();
            bloque();
        }else if(Arrays.asList("punctuationComma", "punctuationSemicolon").contains(actualToken.getToken())){
            listaDecAtrs();
            match("punctuationSemicolon");
        }else if(Arrays.asList("idKeyWord_public", "idKeyWord_private", "idClass",
                "idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int",
                "idKeyWord_void", "idKeyWord_static", "punctuationClosingBracket").contains(actualToken.getToken())){
            //vacio
        }else{
            throw new SyntacticException(actualToken, "{ ( , , , ; , public, private, idClase, boolean, char, int, void, static, } }");
        }
    }

    // 18 ------------------------------------------------------------------------------
    // <Atributo> ::= <Tipo> idMetVar <ListaDecAtrs> ;
    // Primeros: {boolean, char, int, idClase}
    // Siguientes: -
    private void atributo() throws LexicalException, SyntacticException {
        tipo();
        match("idMetVar");
        listaDecAtrs();
        match("punctuationSemicolon");
    }

    // 19 ------------------------------------------------------------------------------
    // <MetodoEstatico> ::= static <TipoMetodo> idMetVar <ArgsFormales> <Bloque>
    // Primeros: {static}
    // Siguientes: -
    private void metodoEstatico() throws LexicalException, SyntacticException {
        match("idKeyWord_static");
        tipoMetodo();
        match("idMetVar");
        argsFormales();
        bloque();
    }

    // 20 ------------------------------------------------------------------------------
    // <MetodoNoEstaticoVoid> ::= void idMetVar <ArgsFormales> <Bloque>
    // Primeros: {void}
    // Siguientes: -
    private void metodoNoEstaticoVoid() throws LexicalException, SyntacticException {
        match("idKeyWord_void");
        match("idMetVar");
        argsFormales();
        bloque();
    }

    // 21 ------------------------------------------------------------------------------
    // <ListaDecAtrs> ::= , idMetVar <ListaDecAtrs> | e
    // Primeros: { , , e }
    // Siguientes: { ; , = }
    private void listaDecAtrs() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationComma").contains(actualToken.getToken())) {
            match("punctuationComma");
            match("idMetVar");
            listaDecAtrs();
        }else if(Arrays.asList("punctuationSemicolon", "assignment").contains(actualToken.getToken())){
            //vacio
        }else{
            throw new SyntacticException(actualToken, "{ , , ; , = }");
        }
    }

    // 22 ------------------------------------------------------------------------------
    // <Visibilidad> ::= public | private
    // Primeros: {public, private}
    // Siguientes: -
    private void visibilidad() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_public").contains(actualToken.getToken())){
            match("idKeyWord_public");
        }else if(Arrays.asList("idKeyWord_private").contains(actualToken.getToken())){
            match("idKeyWord_private");
        }else{
            throw new SyntacticException(actualToken, "{public, private}");
        }
    }

    // 23 ------------------------------------------------------------------------------
    // <Tipo> ::= <TipoPrimitivo> | idClase <GenericoOpt>
    // Primeros: {boolean, char, int, idClase}
    // Siguientes: -
    private void tipo() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int").contains(actualToken.getToken())){
            tipoPrimitivo();
        }else if(Arrays.asList("idClass").contains(actualToken.getToken())){
            match("idClass");
        }else{
            throw new SyntacticException(actualToken, "{boolean, char, int, idClase}");
        }
    }

    // 24 ------------------------------------------------------------------------------
    // <TipoPrimitivo> ::= boolean | char | int
    // Primeros: {boolean, char, int}
    // Siguientes: -
    private void tipoPrimitivo() throws SyntacticException, LexicalException {
        if(Arrays.asList("idKeyWord_boolean").contains(actualToken.getToken())){
            match("idKeyWord_boolean");
        }else if(Arrays.asList("idKeyWord_char").contains(actualToken.getToken())){
            match("idKeyWord_char");
        }else if(Arrays.asList("idKeyWord_int").contains(actualToken.getToken())){
            match("idKeyWord_int");
        }else{
            throw new SyntacticException(actualToken, "{boolean, char, int}");
        }
    }

    // 25 ------------------------------------------------------------------------------
    // <GenericoOpt> ::= < <GenericoOptResto> > | e
    // Primeros: { < , e }
    // Siguientes: {extends, implements, { , idMetVar, , , > }
    private void genericoOpt() throws LexicalException, SyntacticException {
        if(Arrays.asList("opLess").contains(actualToken.getToken())){
            match("opLess");
            genericoOptResto();
            match("opGreater");
        }else if(Arrays.asList("idKeyWord_extends", "idKeyWord_implements",
                "punctuationOpeningBracket", "punctuationOpeningParenthesis", "idMetVar",
                "punctuationComma", "punctuationPoint", "opGreater").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{ < , extends, implements, { , ( , idMetVar, , , . , > }");
        }
    }

    // 25 AUX ------------------------------------------------------------------------------
    // <GenericoOptResto> ::= <Tipo> <ListaTipos> | e
    // Primeros: {boolean, char, int, idClase}
    // Siguientes: { > }
    private void genericoOptResto() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass").contains(actualToken.getToken())){
            tipo();
            listaTipos();
        }else if(Arrays.asList("opGreater").contains(actualToken.getToken())){
            //vacio
        }else{
            throw new SyntacticException(actualToken, "{boolean, char, int, idClase, > }");
        }
    }

    // 26 ------------------------------------------------------------------------------
    // <ListaTipos> ::= , <Tipo> <ListaTipos> | e
    // Primeros: { , , e }
    // Siguientes: { > }
    private void listaTipos() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationComma").contains(actualToken.getToken())){
            match("punctuationComma");
            tipo();
            listaTipos();
        }else if(Arrays.asList("opGreater").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{ , , > }");
        }
    }

    // 27 ------------------------------------------------------------------------------
    // <EstaticoOpt> ::= static | e
    // Primeros: {static, e}
    // Siguientes: {boolean, char, int, idClase, void}
    private void estaticoOpt() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_static").contains(actualToken.getToken())){
            match("idKeyWord_static");
        }else if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass", "idKeyWord_void").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{static, boolean, char, int, idClase, void}");
        }
    }

    // 28 ------------------------------------------------------------------------------
    // <TipoMetodo> ::= <Tipo> | void
    // Primeros: {boolean, char, int, idClase, void}
    // Siguientes: -
    private void tipoMetodo() throws SyntacticException, LexicalException {
        if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass").contains(actualToken.getToken())){
            tipo();
        }else if(Arrays.asList("idKeyWord_void").contains(actualToken.getToken())){
            match("idKeyWord_void");
        }else{
            throw new SyntacticException(actualToken, "{boolean, char, int, idClase, void}");
        }
    }



    // 29 ------------------------------------------------------------------------------
    // <ArgsFormales> ::= ( <ListaArgsFormalesOpt> )
    // Primeros: { ( }
    // Siguientes: -
    private void argsFormales() throws LexicalException, SyntacticException {
        match("punctuationOpeningParenthesis");
        listaArgsFormalesOpt();
        match("punctuationClosingParenthesis");
    }

    // 30 ------------------------------------------------------------------------------
    // <ListaArgsFormalesOpt> ::= <ListaArgsFormales> | e
    // Primeros: {boolean, char, int, idClase, e}
    // Siguientes: { ) }
    private void listaArgsFormalesOpt() throws SyntacticException, LexicalException {
        if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass").contains(actualToken.getToken())){
            listaArgsFormales();
        }else if(Arrays.asList("punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{boolean, char, int, idClase, ) }");
        }
    }

    // 31 ------------------------------------------------------------------------------
    // <ListaArgsFormales> ::= <ArgFormal> <ListaArgsFormalesResto>
    // Primeros: {boolean, char, int, idClase}
    // Siguientes: -
    private void listaArgsFormales() throws SyntacticException, LexicalException {
        argFormal();
        listaArgsFormalesResto();
    }

    // 32 ------------------------------------------------------------------------------
    // <ListaArgsFormalesResto> ::= , <ListaArgsFormales> | e
    // Primeros: { , , e }
    // Siguientes: { ) }
    private void listaArgsFormalesResto() throws SyntacticException, LexicalException {
        if(Arrays.asList("punctuationComma").contains(actualToken.getToken())){
            match("punctuationComma");
            listaArgsFormales();
        }else if(Arrays.asList("punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{ , , ) }");
        }
    }

    // 33 ------------------------------------------------------------------------------
    // <ArgFormal> ::= <Tipo> idMetVar
    // Primeros: {boolean, char, int, idClase}
    // Siguientes: -
    private void argFormal() throws LexicalException, SyntacticException {
        tipo();
        match("idMetVar");
    }

    // 34 ------------------------------------------------------------------------------
    // <Bloque> ::= { <ListaSentencias> }
    // Primeros: { { }
    // Siguientes: -
    private void bloque() throws LexicalException, SyntacticException {
        match("punctuationOpeningBracket");
        listaSentencias();
        match("punctuationClosingBracket");
    }

    // 35 ------------------------------------------------------------------------------
    // <ListaSentencias> ::= <Sentencia> <ListaSentencias> | e
    // Primeros: { ; , idClase, this, idMetVar, new, ( , var, boolean, char, int, return, if, while, { , e}
    // Siguientes: { } }
    private void listaSentencias() throws SyntacticException, LexicalException {
        if(Arrays.asList("punctuationSemicolon", "idClass", "idKeyWord_this", "idMetVar", "idKeyWord_new",
                "punctuationOpeningParenthesis", "idKeyWord_var", "idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int",
                "idKeyWord_return", "idKeyWord_if", "idKeyWord_while", "punctuationOpeningBracket").contains(actualToken.getToken())){
            sentencia();
            listaSentencias();
        }else if(Arrays.asList("punctuationClosingBracket").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{ ; , idClase, this, idMetVar, new, ( , var, boolean, char, int, return, if, while, { , } }");
        }
    }

    // 36 ------------------------------------------------------------------------------
    // <Sentencia> ::= ; | <LlamadaOVarLocal> ; | <Return> ; | <If> | <While> | <Bloque>
    // Primeros: { ; , idClase, this, idMetVar, new, ( , var, boolean, char, int, return, if, while, { }
    // Siguientes: -
    private void sentencia() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
            match("punctuationSemicolon");
        }else if(Arrays.asList("idClass", "idKeyWord_this", "idMetVar", "idKeyWord_new", "punctuationOpeningParenthesis",
                "idKeyWord_var", "idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int").contains(actualToken.getToken())){
            llamadaOVarLocal();
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
            throw new SyntacticException(actualToken, "{ ; , idClase, this, idMetVar, new, ( , var, boolean, char, int, return, if, while, { }");
        }
    }

    // 37 ------------------------------------------------------------------------------
    // <LlamadaOVarLocal> ::= idClase <GenericoOpt> <LlamadaOVarLocalResto> | <Llamada> | <VarLocal>
    // Primeros: {idClase, this, idMetVar, new, ( , var, boolean, char, int}
    // Siguientes: -
    private void llamadaOVarLocal() throws LexicalException, SyntacticException {
        if(Arrays.asList("idClass").contains(actualToken.getToken())){
            match("idClass");
            llamadaOVarLocalResto();
        }else if(Arrays.asList("idKeyWord_this", "idMetVar", "idKeyWord_new", "punctuationOpeningParenthesis").contains(actualToken.getToken())){
            llamada();
        }else if(Arrays.asList("idKeyWord_var", "idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int").contains(actualToken.getToken())){
            varLocal();
        }else{
            throw new SyntacticException(actualToken, "{idClase, this, idMetVar, new, ( , var, boolean, char, int}");
        }
    }

    // 37 AUX ------------------------------------------------------------------------------
    // <LlamadaOVarLocalResto> ::= <AccesoMetodoEstatico> <Asignacion> |  <VarLocalTipoClase>
    // Primeros: { . , < , idMetVar}
    // Siguientes: -
    private void llamadaOVarLocalResto() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationPoint").contains(actualToken.getToken())){
            accesoMetodoEstatico();
            asignacion();
        }else if(Arrays.asList("opLess", "idMetVar").contains(actualToken.getToken())){
            varLocalTipoClase();
        }
    }

    // 38 ------------------------------------------------------------------------------
    // <VarLocalTipoClaseResto> ::= = <Expresion>
    // Primeros: { = , e }
    // Siguientes: { ; }
    private void varLocalTipoClaseResto() throws LexicalException, SyntacticException {
        if(Arrays.asList("assignment").contains(actualToken.getToken())) {
            match("assignment");
            expresion();
        }else if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
            //vacio
        }else{
            throw new SyntacticException(actualToken, "{ = , ; }");
        }
    }

    // 39 ------------------------------------------------------------------------------
    // <Asignacion> ::= <TipoDeAsignacion> <Expresion> | e
    // Primeros: { = , += , -= , e}
    // Siguientes: { ; }
    private void asignacion() throws SyntacticException, LexicalException {
        if(Arrays.asList("assignment", "assignmentAddition", "assignmentSubtraction").contains(actualToken.getToken())){
            tipoDeAsignacion();
            expresion();
        }else if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{ = , += , -= , ; }");
        }
    }

    // 40 ------------------------------------------------------------------------------
    // <TipoDeAsignacion> ::= = | += | -=
    // Primeros: { = , += , -= }
    // Siguientes: -
    private void tipoDeAsignacion() throws SyntacticException, LexicalException {
        if(Arrays.asList("assignment").contains(actualToken.getToken())){
            match("assignment");
        }else if(Arrays.asList("assignmentAddition").contains(actualToken.getToken())){
            match("assignmentAddition");
        }else if(Arrays.asList("assignmentSubtraction").contains(actualToken.getToken())){
            match("assignmentSubtraction");
        }else{
            throw new SyntacticException(actualToken, "{ = , += , -= }");
        }
    }

    // 41 ------------------------------------------------------------------------------
    // <Llamada> ::= <Acceso> <Asignacion>
    // Primeros: {this, idMetVar, new, ( , idClase}
    // Siguientes: -
    private void llamada() throws SyntacticException, LexicalException {
        acceso();
        asignacion();
    }

    // 42 ------------------------------------------------------------------------------
    // <VarLocal> ::= var idMetVar <VarLocalResto> | <TipoPrimitivo> idMetVar <VarLocalResto>
    // Primeros: {var, boolean, char, int}
    // Siguientes: -
    private void varLocal() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_var").contains(actualToken.getToken())){
            match("idKeyWord_var");
            match("idMetVar");
            varLocalResto();
        }else if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int").contains(actualToken.getToken())){
            tipoPrimitivo();
            match("idMetVar");
            varLocalResto();
        }else{
            throw new SyntacticException(actualToken, "{var, boolean, char, int}");
        }
    }

    // 43 ------------------------------------------------------------------------------
    // <VarLocalResto> ::= = <Expresion> <VarLocalResto> | <ListaDecAtrs> <VarLocalResto> ; | e
    // Primeros: { = , , }
    // Siguientes: -
    private void varLocalResto() throws LexicalException, SyntacticException {
        if(Arrays.asList("assignment").contains(actualToken.getToken())){
            match("assignment");
            expresion();
            varLocalResto();
        }else if(Arrays.asList("punctuationComma").contains(actualToken.getToken())) {
            listaDecAtrs();
            varLocalResto();
        }else if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
            //vacio
        }else{
            throw new SyntacticException(actualToken, "{ = , , , ; }");
        }
    }

    // 44 ------------------------------------------------------------------------------
    // <VarLocalTipoClase> ::= idMetVar
    // Primeros: {idMetVar}
    // Siguientes: -
    private void varLocalTipoClase() throws LexicalException, SyntacticException {
        match("idMetVar");
        listaDecAtrs();
        varLocalTipoClaseResto();
    }

    // 45 ------------------------------------------------------------------------------
    // <Return> ::= return <ExpresionOpt>
    // Primeros: {return}
    // Siguientes: -
    private void return_() throws LexicalException, SyntacticException {
        match("idKeyWord_return");
        expresionOpt();
    }

    // 46 ------------------------------------------------------------------------------
    // <ExpresionOpt> ::= <Expresion> | e
    // Primeros: { + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase,e}
    // Siguientes: { ; }
    private void expresionOpt() throws SyntacticException, LexicalException {
        // TODO saque idclass
        if(Arrays.asList("opAddition", "opSubtraction", "opNegation", "idKeyWord_null",
                "idKeyWord_true", "idKeyWord_false", "literalInteger", "literalCharacter", "literalString",
                "idKeyWord_this", "idMetVar", "idKeyWord_new", "punctuationOpeningParenthesis", "idClass").contains(actualToken.getToken())){
            expresion();
        }else{
            if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
                // vacio
            }else{
                throw new SyntacticException(actualToken,
                        "{ + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase, ; }");
            }
        }
    }

    // 47 ------------------------------------------------------------------------------
    // <If> ::= if ( <Expresion> ) <Sentencia> <IfResto>
    // Primeros: {if}
    // Siguientes: -
    private void if_() throws LexicalException, SyntacticException {
        match("idKeyWord_if");
        match("punctuationOpeningParenthesis");
        expresion();
        match("punctuationClosingParenthesis");
        sentencia();
        ifResto();
    }

    // 48 ------------------------------------------------------------------------------
    // <IfResto> ::= else <Sentencia> | e
    // Primeros: {else, e}
    // Siguientes: {else, ; , idClase, this, idMetVar, new, ( , var, boolean, char, int, return, if, while, { , } }
    private void ifResto() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_else").contains(actualToken.getToken())){
            match("idKeyWord_else");
            sentencia();
        }else if(Arrays.asList("idKeyWord_else", "punctuationSemicolon", "idClass", "idKeyWord_this", "idMetVar",
                "idKeyWord_new", "punctuationOpeningParenthesis", "idKeyWord_var",
                "idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idKeyWord_return",
                "idKeyWord_if", "idKeyWord_while", "punctuationOpeningBracket", "punctuationClosingBracket").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken,
                    "{else, ; , idClase, this, idMetVar, new, ( , var, boolean, char, int, return, if, while, { , } }");
        }
    }

    // 49 ------------------------------------------------------------------------------
    // <While> ::= while ( <Expresion> ) <Sentencia>
    // Primeros: {while}
    // Siguientes: -
    private void while_() throws LexicalException, SyntacticException {
        match("idKeyWord_while");
        match("punctuationOpeningParenthesis");
        expresion();
        match("punctuationClosingParenthesis");
        sentencia();
    }

    // 50 ------------------------------------------------------------------------------
    // <Expresion> ::= <ExpresionUnaria> <ExpresionRec>
    // Primeros: { + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase}
    // Siguientes: -
    private void expresion() throws LexicalException, SyntacticException {
        expresionUnaria();
        expresionRec();
    }

    // 51 ------------------------------------------------------------------------------
    // <ExpresionRec> ::= <OperadorBinario> <ExpresionUnaria> <ExpresionRec> | e
    // Primeros: { || , && , == , != , < , > , <= , >= , + , - , * , / , % , e}
    // Siguientes: { ; , , , ) }
    private void expresionRec() throws SyntacticException, LexicalException {
        if(Arrays.asList("opLogicOr", "opLogicAnd", "opEqual", "opDistinct",
                "opLess", "opGreater", "opLessOrEqual", "opGreaterOrEqual",
                "opAddition", "opSubtraction", "opMultiplication", "opDivision", "opModule").contains(actualToken.getToken())){
            operadorBinario();
            expresionUnaria();
            expresionRec();
        }else if(Arrays.asList("punctuationSemicolon", "punctuationComma", "punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{ || , && , == , != , < , > , <= , >= , + , - , * , / , % , ; , , , ) }");
        }
    }

    // 52 ------------------------------------------------------------------------------
    // <OperadorBinario> ::= || | && | == | != | < | > | <= | >= | + | - | * | / | %
    // Primeros: { || , && , == , != , < , > , <= , >= , + , - , * , / , % }
    // Siguientes: -
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
            throw new SyntacticException(actualToken, "{ || , && , == , != , < , > , <= , >= , + , - , * , / , % }");
        }
    }

    // 53 ------------------------------------------------------------------------------
    // <ExpresionUnaria> ::= <OperadorUnario> <Operando> | <Operando>
    // Primeros: { + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase}
    // Siguientes: -
    private void expresionUnaria() throws LexicalException, SyntacticException {
        if(Arrays.asList("opAddition", "opSubtraction", "opNegation").contains(actualToken.getToken())){
            operadorUnario();
            operando();
        }else if(Arrays.asList("idKeyWord_null", "idKeyWord_true", "idKeyWord_false",
                "literalInteger", "literalCharacter","literalString", "idKeyWord_this",
                "idMetVar", "idKeyWord_new", "punctuationOpeningParenthesis", "idClass").contains(actualToken.getToken())){
            operando();
        }else{
            throw new SyntacticException(actualToken, "{ + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( }");
        }
    }

    // 54 ------------------------------------------------------------------------------
    // <OperadorUnario> ::= + | - | !
    // Primeros: { + , - , ! }
    // Siguientes: -
    private void operadorUnario() throws LexicalException, SyntacticException {
        if(Arrays.asList("opAddition").contains(actualToken.getToken())){
            match("opAddition");
        }else if(Arrays.asList("opSubtraction").contains(actualToken.getToken())){
            match("opSubtraction");
        }else if(Arrays.asList("opNegation").contains(actualToken.getToken())){
            match("opNegation");
        }else{
            throw new SyntacticException(actualToken, "{ + , - , ! }");
        }
    }

    // 55 ------------------------------------------------------------------------------
    // <Operando> ::= <Literal> | <Acceso>
    // Primeros: {null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase}
    // Siguientes: -
    private void operando() throws SyntacticException, LexicalException {
        if(Arrays.asList("idKeyWord_null", "idKeyWord_true", "idKeyWord_false",
                "literalInteger", "literalCharacter", "literalString").contains(actualToken.getToken())){
            literal();
        }else if(Arrays.asList("idKeyWord_this", "idMetVar", "idKeyWord_new", "punctuationOpeningParenthesis", "idClass").contains(actualToken.getToken())){
            acceso();
        }else{
            throw new SyntacticException(actualToken, "{null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( }");
        }
    }

    // 56 ------------------------------------------------------------------------------
    // <Literal> ::= null | true | false | intLiteral | charLiteral | stringLiteral
    // Primeros: {null, true, false, intLiteral, charLiteral, stringLiteral}
    // Siguientes: -
    private void literal() throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_null").contains(actualToken.getToken())){
            match("idKeyWord_null");
        }else if(Arrays.asList("idKeyWord_true").contains(actualToken.getToken())){
            match("idKeyWord_true");
        }else if(Arrays.asList("idKeyWord_false").contains(actualToken.getToken())){
            match("idKeyWord_false");
        }else if(Arrays.asList("literalInteger").contains(actualToken.getToken())){
            match("literalInteger");
        }else if(Arrays.asList("literalCharacter").contains(actualToken.getToken())){
            match("literalCharacter");
        }else if(Arrays.asList("literalString").contains(actualToken.getToken())){
            match("literalString");
        }else{
            throw new SyntacticException(actualToken, "{null, true, false, intLiteral, charLiteral, stringLiteral}");
        }
    }

    // 57 ------------------------------------------------------------------------------
    // <Acceso> ::= <Primario> <EncadenadoOpt>
    // Primeros: {this, idMetVar, new, ( , idClase}
    // Siguientes: -
    private void acceso() throws LexicalException, SyntacticException {
        primario();
        encadenadoOpt();
    }

    // 58 ------------------------------------------------------------------------------
    // <Primario> ::= <AccesoThis> | <AccesoVar> | <AccesoConstructor> | <ExpresionParentizada> | idClase <GenericoOpt> <AccesoMetodoEstatico>
    // Primeros: {this, idMetVar, new, ( , idClase}
    // Siguientes: -
    private void primario() throws SyntacticException, LexicalException {
        if(Arrays.asList("idKeyWord_this").contains(actualToken.getToken())){
            accesoThis();
        }else if(Arrays.asList("idMetVar").contains(actualToken.getToken())){
            accesoVar();
        }else if(Arrays.asList("idKeyWord_new").contains(actualToken.getToken())){
            accesoConstructor();
        }else if(Arrays.asList("punctuationOpeningParenthesis").contains(actualToken.getToken())) {
            expresionParentizada();
        }else if(Arrays.asList("idClass").contains(actualToken.getToken())){
            match("idClass");
            genericoOpt();
            accesoMetodoEstatico();
        }else{
            throw new SyntacticException(actualToken,"{this, idMetVar, new, ( , idClass}");
        }
    }

    // 59 ------------------------------------------------------------------------------
    // <AccesoThis> ::= this
    // Primeros: {this}
    // Siguientes: -
    private void accesoThis() throws LexicalException, SyntacticException {
        match("idKeyWord_this");
    }

    // 60 ------------------------------------------------------------------------------
    // <AccesoVar> ::= idMetVar <AccesoMetodo>
    // Primeros: {idMetVar}
    // Siguientes: -
    private void accesoVar() throws LexicalException, SyntacticException {
        match("idMetVar");
        accesoMetodo();
    }

    // 61 ------------------------------------------------------------------------------
    // <AccesoConstructor> ::= new idClase <GenericoOpt> ( <ListaExpsOpt> )
    // Primeros: {new}
    // Siguientes: -
    private void accesoConstructor() throws LexicalException, SyntacticException {
        match("idKeyWord_new");
        match("idClass");
        genericoOpt();
        match("punctuationOpeningParenthesis");
        listaExpsOpt();
        match("punctuationClosingParenthesis");
    }

    // 62 ------------------------------------------------------------------------------
    // <ExpresionParentizada> ::= ( <Expresion> )
    // Primeros: { ( }
    // Siguientes: -
    private void expresionParentizada() throws LexicalException, SyntacticException {
        match("punctuationOpeningParenthesis");
        expresion();
        match("punctuationClosingParenthesis");
    }

    // 63 ------------------------------------------------------------------------------
    // <AccesoMetodo> ::= <ArgsActuales> | e
    // Primeros: { ( , e }
    // Siguientes: { = , += , -= , || , && , == , != , < , > , <= , >= , + , - , * , / , % , . , , , ; , ) }
    private void accesoMetodo() throws SyntacticException, LexicalException {
        if(Arrays.asList("punctuationOpeningParenthesis").contains(actualToken.getToken())){
            argsActuales();
        }else if(Arrays.asList("assignment", "assignmentAddition", "assignmentSubtraction",
                "opLogicOr", "opLogicAnd", "opEqual", "opDistinct",
                "opGreater", "opGreaterOrEqual", "opLess", "opLessOrEqual",
                "opAddition", "opSubtraction", "opMultiplication", "opDivision", "opModule",
                "punctuationSemicolon", "punctuationPoint", "punctuationComma",
                "punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{ ( , = , += , -= , || , && , == , != , < , > , <= , >= , + , - , * , / , % , . , ; , ) }");
        }
    }

    // 64 ------------------------------------------------------------------------------
    // <AccesoMetodoEstatico> ::= . idMetVar <AccesoMetodoVariableEstatico>
    // Primeros: { . }
    // Siguientes: -
    private void accesoMetodoEstatico() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationPoint").contains(actualToken.getToken())){
            match("punctuationPoint");
            match("idMetVar");
            eccesoMetodoVariableEstatica();
        }
    }

    // <AccesoMetodoVariableEstatico> ::= <argsActuales> <AccesoMetodoEstatico> | e
    // Primeros: { ( , e }
    // Siguientes: { = , += , -= , || , && , == , != , < , > , <= , >= , + , - , * , / , % , . , ; , , , ) }
    private void eccesoMetodoVariableEstatica() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationOpeningParenthesis").contains(actualToken.getToken())){
            argsActuales();
            accesoMetodoEstatico();
        }else if(Arrays.asList("assignment", "assignmentAddition", "assignmentSubtraction",
                "opLogicOr", "opLogicAnd", "opEqual", "opDistinct",
                "opGreater", "opGreaterOrEqual", "opLess", "opLessOrEqual",
                "opAddition", "opSubtraction", "opMultiplication", "opDivision", "opModule",
                "punctuationSemicolon", "punctuationComma", "punctuationSemicolon",
                "punctuationClosingParenthesis").contains(actualToken.getToken())){
            //vacio
        }else{
            throw new SyntacticException(actualToken, "{ = , += , -= , || , && , == , != , < , > , <= , >= , + , - , * , / , % , . , ; , , , ) }");
        }
    }

    // 65 ------------------------------------------------------------------------------
    // <ArgsActuales> ::= ( <ListaExpsOpt> )
    // Primeros: { ( }
    // Siguientes: -
    private void argsActuales() throws LexicalException, SyntacticException {
        match("punctuationOpeningParenthesis");
        listaExpsOpt();
        match("punctuationClosingParenthesis");
    }

    // 66 ------------------------------------------------------------------------------
    // <ListaExpsOpt> ::= <ListaExps> | e
    // Primeros: { + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase, e}
    // Siguientes: { ) }
    private void listaExpsOpt() throws SyntacticException, LexicalException {
        if(Arrays.asList("opAddition", "opSubtraction", "opNegation",
                "idKeyWord_null", "idKeyWord_true", "idKeyWord_false",
                "literalInteger", "literalCharacter", "literalString",
                "idKeyWord_this", "idMetVar", "idKeyWord_new",
                "punctuationOpeningParenthesis", "idClass").contains(actualToken.getToken())){
            listaExps();
        }else if(Arrays.asList("punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            // Se esperaba el inicio de una expresion
            throw new SyntacticException(actualToken, "{ + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase, ) }");
        }
    }

    // 67 ------------------------------------------------------------------------------
    // <ListaExps> ::= <Expresion> <ListaExpsResto>
    // Primeros: { + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase}
    // Siguientes: -
    private void listaExps() throws LexicalException, SyntacticException {
        expresion();
        listaExpsResto();
    }

    // 68 ------------------------------------------------------------------------------
    // <ListaExpsResto> ::= , <ListaExps> | e
    // Primeros: { , , e }
    // Siguientes: { ) }
    private void listaExpsResto() throws SyntacticException, LexicalException {
        if(Arrays.asList("punctuationComma").contains(actualToken.getToken())){
            match("punctuationComma");
            listaExps();
        }else if(Arrays.asList("punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken,"{ , , ) }");
        }
    }

    // 69 ------------------------------------------------------------------------------
    // <EncadenadoOpt> ::= . idMetVar <MetVarEncadenada> | e
    // Primeros: { . , e }
    // Siguientes: { = , += , -= , || , && , == , != , < , > , <= , >= , + , - , * , / , % , ; , , , ) }
    private void encadenadoOpt() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationPoint").contains(actualToken.getToken())){
            match("punctuationPoint");
            match("idMetVar");
            metVarEncadenada();
        }else if(Arrays.asList("assignment", "assignmentAddition", "assignmentSubtraction",
                "opLogicOr", "opLogicAnd", "opEqual", "opDistinct",
                "opGreater", "opGreaterOrEqual", "opLess", "opLessOrEqual",
                "opAddition", "opSubtraction", "opMultiplication", "opDivision", "opModule",
                "punctuationSemicolon", "punctuationComma", "punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{ . , = , += , -= , || , && , == , != , < , > , <= , >= , + , - , * , / , % , ; , , , ) }");
        }
    }

    // 70 ------------------------------------------------------------------------------
    // <MetVarEncadenada> ::= <EncadenadoOpt> | <ArgsActuales> <EncadenadoOpt>
    // Primeros: { . , = , += , -= , || , && , == , != , < , > , <= , >= , + , - , * , / , % , ; , , , ) , ( }
    // Siguientes: -
    private void metVarEncadenada() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationPoint", "assignment", "assignmentAddition", "assignmentSubtraction",
                "opLogicOr", "opLogicAnd", "opEqual", "opDistinct",
                "opGreater", "opGreaterOrEqual", "opLess", "opLessOrEqual",
                "opAddition", "opSubtraction", "opMultiplication", "opDivision",
                "opModule", "punctuationSemicolon", "punctuationComma", "punctuationClosingParenthesis").contains(actualToken.getToken())){
            encadenadoOpt();
        }else if(Arrays.asList("punctuationOpeningParenthesis").contains(actualToken.getToken())){
            argsActuales();
            encadenadoOpt();
        }else{
            throw new SyntacticException(actualToken, "{ . , = , += , -= , || , && , == , != , < , > , <= , >= , + , - , * , / , % , ; , , , ) , ( }");
        }
    }

}
