package minijava.compiler.syntactic;

import minijava.compiler.exception.*;
import minijava.compiler.exception.lexical.LexicalException;
import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.semanticP1.duplicated.SemanticExceptionDuplicatedParameter;
import minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.*;
import minijava.compiler.semantic.nodes.expresion.ExpresionBinariaNodo;
import minijava.compiler.semantic.nodes.expresion.operando.literales.*;
import minijava.compiler.semantic.nodes.expresion.operando.primario.*;
import minijava.compiler.semantic.nodes.sentencia.*;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.nodes.expresion.ExpresionUnariaNodo;
import minijava.compiler.semantic.nodes.expresion.operando.OperandoNodo;
import minijava.compiler.semantic.nodes.expresion.operando.AccesoNodo;
import minijava.compiler.semantic.nodes.expresion.operando.EncadenadoOptNodo;
import minijava.compiler.semantic.tables.*;
import minijava.compiler.semantic.tables.Class;
import minijava.compiler.semantic.tables.variable.Attribute;
import minijava.compiler.semantic.tables.variable.Parameter;
import minijava.compiler.semantic.tables.variable.VarLocal;

import java.util.ArrayList;
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
    public void inicial() throws LexicalException, SyntacticException, SemanticException {
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
    private void listaClases() throws LexicalException, SyntacticException, SemanticException {
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
    private void clase() throws LexicalException, SyntacticException, SemanticException {
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
    private void claseConcreta() throws LexicalException, SyntacticException, SemanticException {
        match("idKeyWord_class");
            Token nombreClase = actualToken;
        match("idClass");
        genericoNotOpt(); // No lo contemplo semanticamente
        Class clase = new Class(nombreClase);
            st.setActualClassOrInterface(clase);
        ArrayList<Token> extendsFrom = heredaDe();
            st.setActualClassInterfaceListOfExtends(extendsFrom);
        ArrayList<Token> implement = implementaA();
            st.setActualClassListOfImplements(implement);
        match("punctuationOpeningBracket");
        listaMiembros();
        match("punctuationClosingBracket");
            st.insertarClase();
    }

    // 5 ------------------------------------------------------------------------------
    // <Interface> ::= interface idClase <GenericoOpt> <ExtiendeA> { <ListaEncabezados> }
    // Primeros: {interface}
    // Siguientes: -
    private void interface_() throws LexicalException, SyntacticException, SemanticException {
        match("idKeyWord_interface");
            Token nombreInterface = actualToken;
        match("idClass");
            Interface_ interface_ = new Interface_(nombreInterface);
            st.setActualClassOrInterface(interface_);
        ArrayList<Token> extendsFrom = extiendeA();
        if((extendsFrom.size() == 1) && extendsFrom.get(0).equals("Object")){
            st.setActualClassInterfaceListOfExtends(new ArrayList<>());
        }else{
            st.setActualClassInterfaceListOfExtends(extendsFrom);
        }

        match("punctuationOpeningBracket");
        listaEncabezados();
        match("punctuationClosingBracket");

        st.insertarInterface();
    }

    // 6 ------------------------------------------------------------------------------
    // <HeredaDe> ::= extends idClase <GenericoOpt> | e
    // Primeros: {extends, e}
    // Siguientes: {implments, { }
    private ArrayList<Token> heredaDe() throws LexicalException, SyntacticException {
        ArrayList<Token> extendsFrom = new ArrayList<>();
        if(Arrays.asList("idKeyWord_extends").contains(actualToken.getToken())) {
            match("idKeyWord_extends");
            extendsFrom.add(actualToken);
            match("idClass");
            genericoNotOpt();
            extendsFrom.addAll(heredaDe());
        }else if(Arrays.asList("punctuationComma").contains(actualToken.getToken())){
            match("punctuationComma");
            extendsFrom.add(actualToken);
            match("idClass");
            genericoNotOpt();
            extendsFrom.addAll(heredaDe());
        }else if(Arrays.asList("idKeyWord_implements", "punctuationOpeningBracket").contains(actualToken.getToken())){
            //vacio
            extendsFrom.add(st.getObjectClassToken());
        }else{
            throw new SyntacticException(actualToken, "{extends, implements, { }");
        }
        return extendsFrom;
    }

    // 7 ------------------------------------------------------------------------------
    // <ImplementaA> ::= implements <ListaTipoReferencia> | e
    // Primeros: {implements, e}
    // Siguientes: { { }
    private ArrayList<Token> implementaA() throws LexicalException, SyntacticException {
        ArrayList<Token> implements_ = new ArrayList<>();
        if(Arrays.asList("idKeyWord_implements").contains(actualToken.getToken())){
            match("idKeyWord_implements");
            implements_ = listaTipoReferencia();
        }else if(Arrays.asList("punctuationOpeningBracket").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{implements, {");
        }
        return implements_;
    }

    // 8 ------------------------------------------------------------------------------
    // <ExtiendeA> ::= extends <ListaTipoReferencia> | e
    // Primeros: {extends, e}
    // Siguientes: { { }
    private ArrayList<Token> extiendeA() throws LexicalException, SyntacticException {
        ArrayList<Token> extiendeA = new ArrayList<>();
        if(Arrays.asList("idKeyWord_extends").contains(actualToken.getToken())){
            match("idKeyWord_extends");
                extiendeA = listaTipoReferencia();
        }else if(Arrays.asList("punctuationOpeningBracket").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{extends, { }");
        }
        return extiendeA;
    }

    // 9 ------------------------------------------------------------------------------
    // <ListaTipoReferencia> ::= idClase <GenericoOpt> <ListaTipoReferenciaResto>
    // Primeros: {idClase}
    // Siguientes: -
    private ArrayList<Token> listaTipoReferencia() throws LexicalException, SyntacticException {
            ArrayList<Token> implementa_ = new ArrayList<>();
            implementa_.add(actualToken);
        match("idClass");
        genericoNotOpt(); // No lo contemplo semanticamente
        implementa_.addAll(listaTipoReferenciaResto());
        return implementa_;
    }

    // 10 ------------------------------------------------------------------------------
    // <ListaTipoReferenciaResto> ::= , <ListaTipoReferencia> | e
    // Primeros: { , , e }
    // Siguientes: { { }
    private ArrayList<Token> listaTipoReferenciaResto() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationComma").contains(actualToken.getToken())){
            match("punctuationComma");
            return listaTipoReferencia();
        }else if(Arrays.asList("punctuationOpeningBracket").contains(actualToken.getToken())){
            //vacio
            return new ArrayList<>(); //Caso base de recursion
        }else{
            throw new SyntacticException(actualToken, "{ , , { }");
        }
    }

    // 11 ------------------------------------------------------------------------------
    // <ListaMiembros> ::= <Miembro> <ListaMiembros> | e
    // Primeros: {public, private, idClase, boolean, char, int, void, static, e}
    // Siguientes: { } }
    private void listaMiembros() throws SyntacticException, LexicalException, SemanticException {
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
    private void listaEncabezados() throws LexicalException, SyntacticException, SemanticException {
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
    private void encabezadoMetodo() throws LexicalException, SyntacticException, SemanticException {
        Method method = new Method();
        method.setClassDeclaredMethod(st.getActualClassInterfaceName());
        estaticoOpt(method);
        method.setMethodType(tipoMetodo());
        method.setMethodToken(actualToken);
        match("idMetVar");
        argsFormales(method);
        st.actualClassInterfaceAddMethod(method);
    }

    // 14 ------------------------------------------------------------------------------
    // <Miembro> ::= <Visibilidad> <Atributo> | <ConstructorOAtrMet> | <MetodoNoEstaticoVoid> | <MetodoEstatico>
    // Primeros: {public, private, idClase, boolean, char, int, void, static}
    // Siguientes: -
    private void miembro() throws SyntacticException, LexicalException, SemanticException {
        if(Arrays.asList("idKeyWord_private", "idKeyWord_public").contains(actualToken.getToken())) {
            Attribute attribute = new Attribute();
            attribute.setClass_(st.getActualClassInterfaceName());
            attribute.setVisibilidad(visibilidad());
            atributo(attribute);
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
    private void constructorOAtrMet() throws LexicalException, SyntacticException, SemanticException {
        if(Arrays.asList("idClass").contains(actualToken.getToken())){
                Type type = new Type(actualToken);
            match("idClass");
            constructorOAtrMetRestoTipoClase(type);
        }else if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int").contains(actualToken.getToken())) {
            Type type = tipoPrimitivo();
            constructorOAtrMetResto(type);
        }else{
            throw new SyntacticException(actualToken, "{idClase, boolean, char, int}");
        }
    }

    // 16 ------------------------------------------------------------------------------
    // <ConstructorOAtrMetResto> ::= <ArgsFormales> <Bloque> | idMetVar <AtributoOMetodo> | idMetVar <AtributoOMetodo>
    // Primeros: { ( , idMetVar, < }
    // Siguientes: -
    private void constructorOAtrMetResto(Type type) throws LexicalException, SyntacticException, SemanticException {
        if(Arrays.asList("punctuationOpeningParenthesis").contains(actualToken.getToken())){
                Method method = new Method();
                method.setClassDeclaredMethod(st.getActualClassInterfaceName());
                method.setMethodToken(type.getTokenType());
                method.setMethodType(type);
            argsFormales(method);
            st.setActualMethod(method);
            bloque();
                st.actualClassInterfaceAddConstructor(method);
        }else if(Arrays.asList("idMetVar").contains(actualToken.getToken())) {
                Token nombreMetVar = actualToken;
            match("idMetVar");
            atributoOMetodo(type, nombreMetVar);
        }else{
            throw new SyntacticException(actualToken, "{ ( , idMetVar, < }");
        }
    }

    private void constructorOAtrMetRestoTipoClase(Type type) throws LexicalException, SyntacticException, SemanticException {
        if(Arrays.asList("opLess").contains(actualToken.getToken())){
            genericoNotOpt();
            Token nombreMetVar = actualToken;
            match("idMetVar");
            atributoOMetodo(type, nombreMetVar);
        }else if(Arrays.asList("idMetVar", "punctuationOpeningParenthesis").contains(actualToken.getToken())){
            constructorOAtrMetResto(type);
        }else{
            throw new SyntacticException(actualToken, "{ ( , idMetVar, < }");
        }
    }

    // 17 ------------------------------------------------------------------------------
    // <AtributoOMetodo> ::= <ArgsFormales> <Bloque> | <ListaDecAtrs> ;
    // Primeros: { ( , , , ; , e }
    // Siguientes: {public, private, idClase, boolean, char, int, void, static, } }
    public void atributoOMetodo(Type type, Token nombreMetVar) throws LexicalException, SyntacticException, SemanticException {
        if(Arrays.asList("punctuationOpeningParenthesis").contains(actualToken.getToken())){
            genericoNotOpt();
            Method method = new Method();
            method.setClassDeclaredMethod(st.getActualClassInterfaceName());
            method.setMethodType(type);
            method.setMethodToken(nombreMetVar);
            argsFormales(method);
            st.setActualMethod(method);
            bloque();
            st.actualClassInterfaceAddMethod(method);
        }else if(Arrays.asList("punctuationComma", "punctuationSemicolon").contains(actualToken.getToken())){
            Attribute attribute = new Attribute();
            attribute.setClass_(st.getActualClassInterfaceName());
            attribute.setVarType(type);
            attribute.setVisibilidad(true);
            attribute.setVarToken(nombreMetVar);
            st.actualClassAddAtribute(attribute);
            listaDecAtrs(Attribute.clone(attribute));
            match("punctuationSemicolon");
        }else{
            throw new SyntacticException(actualToken, "{ ( , , , ; , public, private, idClase, boolean, char, int, void, static, } }");
        }
    }

    // 18 ------------------------------------------------------------------------------
    // <Atributo> ::= <Tipo> idMetVar <ListaDecAtrs> ;
    // Primeros: {boolean, char, int, idClase}
    // Siguientes: -
    private void atributo(Attribute attribute) throws LexicalException, SyntacticException, SemanticException {
        Type type = tipo();
            attribute.setVarType(type);
            attribute.setVarToken(actualToken);
        match("idMetVar");
            st.actualClassAddAtribute(attribute);
        listaDecAtrs(Attribute.clone(attribute));
        match("punctuationSemicolon");
    }

    // 19 ------------------------------------------------------------------------------
    // <MetodoEstatico> ::= static <TipoMetodo> idMetVar <ArgsFormales> <Bloque>
    // Primeros: {static}
    // Siguientes: -
    private void metodoEstatico() throws LexicalException, SyntacticException, SemanticException {
            Method method = new Method();
            method.setClassDeclaredMethod(st.getActualClassInterfaceName());
            method.setStatic(true);
        match("idKeyWord_static");
        method.setMethodType(tipoMetodo());
            method.setMethodToken(actualToken);
        match("idMetVar");
        argsFormales(method);
        st.setActualMethod(method);
        bloque();
        st.actualClassInterfaceAddMethod(method);
    }

    // 20 ------------------------------------------------------------------------------
    // <MetodoNoEstaticoVoid> ::= void idMetVar <ArgsFormales> <Bloque>
    // Primeros: {void}
    // Siguientes: -
    private void metodoNoEstaticoVoid() throws LexicalException, SyntacticException, SemanticException {
            Method method = new Method();
            method.setClassDeclaredMethod(st.getActualClassInterfaceName());
            method.setStatic(false);
            Type type = new Type(actualToken);
        match("idKeyWord_void");
            method.setMethodToken(actualToken);
        match("idMetVar");
            method.setMethodType(type);
        argsFormales(method);
        st.setActualMethod(method);
        bloque();
            st.actualClassInterfaceAddMethod(method);
    }

    // 21 ------------------------------------------------------------------------------
    // <ListaDecAtrs> ::= , idMetVar <ListaDecAtrs> | e
    // Primeros: { , , e }
    // Siguientes: { ; , = }
    private void listaDecAtrs(Attribute attribute) throws LexicalException, SyntacticException, SemanticException {
        if(Arrays.asList("punctuationComma").contains(actualToken.getToken())) {
            match("punctuationComma");
                attribute.setVarToken(actualToken);
            match("idMetVar");
            st.actualClassAddAtribute(attribute);
            listaDecAtrs(Attribute.clone(attribute));
        }else if(Arrays.asList("assignment").contains(actualToken.getToken())) {
            match("assignment");
            expresion();
        }else if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
            //vacio
        }else{
            throw new SyntacticException(actualToken, "{ , , ; , = }");
        }
    }

    // 22 ------------------------------------------------------------------------------
    // <Visibilidad> ::= public | private
    // Primeros: {public, private}
    // Siguientes: -
    private boolean visibilidad() throws LexicalException, SyntacticException {
        boolean isVisible = false;
        if(Arrays.asList("idKeyWord_public").contains(actualToken.getToken())){
            match("idKeyWord_public");
            isVisible = true;
        }else if(Arrays.asList("idKeyWord_private").contains(actualToken.getToken())){
            match("idKeyWord_private");
        }else{
            throw new SyntacticException(actualToken, "{public, private}");
        }
        return isVisible;
    }

    // 23 ------------------------------------------------------------------------------
    // <Tipo> ::= <TipoPrimitivo> | idClase <GenericoOpt>
    // Primeros: {boolean, char, int, idClase}
    // Siguientes: -
    private Type tipo() throws LexicalException, SyntacticException {
        Type type;
        if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int").contains(actualToken.getToken())){
            type = tipoPrimitivo();
        }else if(Arrays.asList("idClass").contains(actualToken.getToken())){
                type = new Type(actualToken);
            match("idClass");
        }else{
            throw new SyntacticException(actualToken, "{boolean, char, int, idClase}");
        }
        return type;
    }

    // 24 ------------------------------------------------------------------------------
    // <TipoPrimitivo> ::= boolean | char | int
    // Primeros: {boolean, char, int}
    // Siguientes: -
    private Type tipoPrimitivo() throws SyntacticException, LexicalException {
        Type type;
        if(Arrays.asList("idKeyWord_boolean").contains(actualToken.getToken())){
                type = new Type(actualToken);
            match("idKeyWord_boolean");
        }else if(Arrays.asList("idKeyWord_char").contains(actualToken.getToken())){
                type = new Type(actualToken);
            match("idKeyWord_char");
        }else if(Arrays.asList("idKeyWord_int").contains(actualToken.getToken())){
                type = new Type(actualToken);
            match("idKeyWord_int");
        }else{
            throw new SyntacticException(actualToken, "{boolean, char, int}");
        }
        return type;
    }

    // 25 ------------------------------------------------------------------------------
    // <GenericoOpt> ::= < <GenericoOptResto> > | e
    // Primeros: { < , e }
    // Siguientes: {extends, implements, { , idMetVar, , , > }
    // TODO no lo evaluo semanticamente
    private void genericoNotOpt() throws LexicalException, SyntacticException {
        if(Arrays.asList("opLess").contains(actualToken.getToken())){
            match("opLess");
            genericoNotOptResto();
            match("opGreater");
        }else if(Arrays.asList("idKeyWord_extends", "idKeyWord_implements",
                "punctuationOpeningBracket", "punctuationOpeningParenthesis", "idMetVar",
                "punctuationComma", "punctuationPoint", "opGreater").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{ < , extends, implements, { , ( , idMetVar, , , . , > }");
        }
    }

    private void genericoOptional() throws LexicalException, SyntacticException {
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
    // <GenericoOptResto> ::= idClase <GenericoOpt> <ListaTipos> | e
    // Primeros: {idClase}
    // Siguientes: { > }
    private void genericoOptResto() throws LexicalException, SyntacticException {
        if(Arrays.asList("idClass").contains(actualToken.getToken())){
            match("idClass");
            genericoNotOpt();
            listaTipos();
        }else if(Arrays.asList("opGreater").contains(actualToken.getToken())){
            //vacio
        }else{
            throw new SyntacticException(actualToken, "{idClase, > }");
        }
    }

    private void genericoNotOptResto() throws LexicalException, SyntacticException {
        match("idClass");
        genericoNotOpt();
        listaTipos();
    }

    // 26 ------------------------------------------------------------------------------
    // <ListaTipos> ::= , idClase <GenericoOpt> <ListaTipos> | e
    // Primeros: { , , e }
    // Siguientes: { > }
    private void listaTipos() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationComma").contains(actualToken.getToken())){
            match("punctuationComma");
            match("idClass");
            genericoNotOpt();
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
    private void estaticoOpt(Method method) throws LexicalException, SyntacticException {
        if(Arrays.asList("idKeyWord_static").contains(actualToken.getToken())){
                method.setStatic(true);
            match("idKeyWord_static");
        }else if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass", "idKeyWord_void").contains(actualToken.getToken())){
            // vacio
                method.setStatic(false);
        }else{
            throw new SyntacticException(actualToken, "{static, boolean, char, int, idClase, void}");
        }
    }

    // 28 ------------------------------------------------------------------------------
    // <TipoMetodo> ::= <Tipo> | void
    // Primeros: {boolean, char, int, idClase, void}
    // Siguientes: -
    private Type tipoMetodo() throws SyntacticException, LexicalException {
            Type type;
        if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass").contains(actualToken.getToken())){
            type = tipo();
        }else if(Arrays.asList("idKeyWord_void").contains(actualToken.getToken())){
                type = new Type(actualToken);
            match("idKeyWord_void");
        }else{
            throw new SyntacticException(actualToken, "{boolean, char, int, idClase, void}");
        }
        return type;
    }



    // 29 ------------------------------------------------------------------------------
    // <ArgsFormales> ::= ( <ListaArgsFormalesOpt> )
    // Primeros: { ( }
    // Siguientes: -
    private void argsFormales(Method method) throws LexicalException, SyntacticException, SemanticException {
        match("punctuationOpeningParenthesis");
        listaArgsFormalesOpt(method);
        match("punctuationClosingParenthesis");
    }

    // 30 ------------------------------------------------------------------------------
    // <ListaArgsFormalesOpt> ::= <ListaArgsFormales> | e
    // Primeros: {boolean, char, int, idClase, e}
    // Siguientes: { ) }
    private void listaArgsFormalesOpt(Method method) throws SyntacticException, LexicalException, SemanticException {
        if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int", "idClass").contains(actualToken.getToken())){
            listaArgsFormales(method);
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
    private void listaArgsFormales(Method method) throws SyntacticException, LexicalException, SemanticException {
        argFormal(method);
        listaArgsFormalesResto(method);
    }

    // 32 ------------------------------------------------------------------------------
    // <ListaArgsFormalesResto> ::= , <ListaArgsFormales> | e
    // Primeros: { , , e }
    // Siguientes: { ) }
    private void listaArgsFormalesResto(Method method) throws SyntacticException, LexicalException, SemanticException {
        if(Arrays.asList("punctuationComma").contains(actualToken.getToken())){
            match("punctuationComma");
            listaArgsFormales(method);
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
    private void argFormal(Method method) throws LexicalException, SyntacticException, SemanticException {
            Parameter parameter = new Parameter();
            Type type = tipo();
            if(type.getTokenType().getToken().equals("idClass")){
                genericoNotOpt();
            }
            parameter.setVarType(type);
            parameter.setVarToken(actualToken);
            parameter.setMethod(method);
            if(method.addParameter(parameter) != null) throw new SemanticExceptionDuplicatedParameter(parameter);
        match("idMetVar");
    }

    // 34 ------------------------------------------------------------------------------
    // <Bloque> ::= { <ListaSentencias> }
    // Primeros: { { }
    // Siguientes: -
    private SentenciaNodo bloque() throws LexicalException, SyntacticException, SemanticException {
        Block block = new Block(st.getActualMethod());
//        if(st.getActualMethod().alreadyHaveBlock()){
        st.getActualMethod().setMainBlock(block);
        BloqueNodo bloqueNodo = new BloqueNodo(block);
        block.setBlockNode(bloqueNodo);
        st.getActualMethod().setMainBlock(block);
        match("punctuationOpeningBracket");
        listaSentencias();
        match("punctuationClosingBracket");
        return bloqueNodo;
    }

    // 35 ------------------------------------------------------------------------------
    // <ListaSentencias> ::= <Sentencia> <ListaSentencias> | e
    // Primeros: { ; , idClase, this, idMetVar, new, ( , var, boolean, char, int, return, if, while, { , e}
    // Siguientes: { } }
    private void listaSentencias() throws SyntacticException, LexicalException, SemanticException {
        if(Arrays.asList("punctuationSemicolon", "idClass", "idKeyWord_this", "idMetVar", "idKeyWord_new",
                "punctuationOpeningParenthesis", "idKeyWord_var", "idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int",
                "idKeyWord_return", "idKeyWord_if", "idKeyWord_while", "punctuationOpeningBracket").contains(actualToken.getToken())){
            SentenciaNodo sentenciaNodo = sentencia();
            if(sentenciaNodo != null) st.getActualMethod().getMainBlock().addSentenciaNodo(sentenciaNodo);
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
    private SentenciaNodo sentencia() throws LexicalException, SyntacticException, SemanticException {
        SentenciaNodo sentenciaNodo = null;
        if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
            match("punctuationSemicolon");
        }else if(Arrays.asList("idClass", "idKeyWord_this", "idMetVar", "idKeyWord_new", "punctuationOpeningParenthesis",
                "idKeyWord_var", "idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int").contains(actualToken.getToken())){
            sentenciaNodo = llamadaOVarLocal();
            match("punctuationSemicolon");
        }else if(Arrays.asList("idKeyWord_return").contains(actualToken.getToken())){
            sentenciaNodo = return_();
            match("punctuationSemicolon");
        }else if(Arrays.asList("idKeyWord_if").contains(actualToken.getToken())){
            sentenciaNodo = if_();
        }else if(Arrays.asList("idKeyWord_while").contains(actualToken.getToken())){
            sentenciaNodo = while_();
        }else if(Arrays.asList("punctuationOpeningBracket").contains(actualToken.getToken())){
            sentenciaNodo = bloque();
        }else{
            throw new SyntacticException(actualToken, "{ ; , idClase, this, idMetVar, new, ( , var, boolean, char, int, return, if, while, { }");
        }
        return sentenciaNodo;
    }

    // 37 ------------------------------------------------------------------------------
    // <LlamadaOVarLocal> ::= idClase <GenericoOpt> <LlamadaOVarLocalResto> | <Llamada> | <VarLocal>
    // Primeros: {idClase, this, idMetVar, new, ( , var, boolean, char, int}
    // Siguientes: -
    private SentenciaNodo llamadaOVarLocal() throws LexicalException, SyntacticException, SemanticException {
        SentenciaNodo sentenciaNodo;
        if(Arrays.asList("idClass").contains(actualToken.getToken())){
            Type idClass = new Type(actualToken);
            match("idClass");
            sentenciaNodo = llamadaOVarLocalResto(idClass);
        }else if(Arrays.asList("idKeyWord_this", "idMetVar", "idKeyWord_new", "punctuationOpeningParenthesis").contains(actualToken.getToken())){
            sentenciaNodo = llamada();
        }else if(Arrays.asList("idKeyWord_var", "idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int").contains(actualToken.getToken())){
            sentenciaNodo = varLocal();
        }else{
            throw new SyntacticException(actualToken, "{idClase, this, idMetVar, new, ( , var, boolean, char, int}");
        }
        return sentenciaNodo;
    }

    // 38 ------------------------------------------------------------------------------
    // <LlamadaOVarLocalResto> ::= <AccesoMetodoEstatico> <Asignacion> |  <VarLocalTipoClase>
    // Primeros: { . , < , idMetVar}
    // Siguientes: -
    private SentenciaNodo llamadaOVarLocalResto(Type idClass) throws LexicalException, SyntacticException, SemanticException {
        SentenciaNodo sentenciaNodo = null;
        if(Arrays.asList("punctuationPoint").contains(actualToken.getToken())){
            accesoMetodoEstatico();
            asignacion();
        }else if(Arrays.asList("opLess", "idMetVar").contains(actualToken.getToken())){
            varLocalTipoClase(idClass);
        }
        return sentenciaNodo;
    }

    // 39 ------------------------------------------------------------------------------
    // <Asignacion> ::= <TipoDeAsignacion> <Expresion> | e
    // Primeros: { = , += , -= , e}
    // Siguientes: { ; }
    private AsignacionNodo asignacion() throws SyntacticException, LexicalException {
        AsignacionNodo asignacionNodo;
        if(Arrays.asList("assignment", "assignmentAddition", "assignmentSubtraction").contains(actualToken.getToken())){
            asignacionNodo = new AsignacionNodo(tipoDeAsignacion());
            asignacionNodo.setParteDerecha(expresion());
        }else if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
            // vacio
            asignacionNodo = null;
        }else{
            throw new SyntacticException(actualToken, "{ = , += , -= , ; }");
        }
        return asignacionNodo;
    }

    // 40 ------------------------------------------------------------------------------
    // <TipoDeAsignacion> ::= = | += | -=
    // Primeros: { = , += , -= }
    // Siguientes: -
    private Token tipoDeAsignacion() throws SyntacticException, LexicalException {
        Token tipoAsignacion = actualToken;
        if(Arrays.asList("assignment").contains(actualToken.getToken())){
            match("assignment");
        }else if(Arrays.asList("assignmentAddition").contains(actualToken.getToken())){
            match("assignmentAddition");
        }else if(Arrays.asList("assignmentSubtraction").contains(actualToken.getToken())){
            match("assignmentSubtraction");
        }else{
            throw new SyntacticException(actualToken, "{ = , += , -= }");
        }
        return tipoAsignacion;
    }

    // 41 ------------------------------------------------------------------------------
    // <Llamada> ::= <Acceso> <Asignacion>
    // Primeros: {this, idMetVar, new, ( , idClase}
    // Siguientes: -
    private SentenciaNodo llamada() throws SyntacticException, LexicalException {
        AccesoNodo accesoNodo = acceso();
        AsignacionNodo asignacionNodo = asignacion();
        if(asignacionNodo == null){
            LlamadaNodo llamadaNodo = new LlamadaNodo();
            llamadaNodo.setAccesoNodo(accesoNodo);
            return llamadaNodo;
        }else{
            asignacionNodo.setParteIzquierda(accesoNodo);
            return asignacionNodo;
        }
    }

    // 42 ------------------------------------------------------------------------------
    // <VarLocal> ::= var idMetVar <VarLocalResto> | <TipoPrimitivo> idMetVar <VarLocalResto>
    // Primeros: {var, boolean, char, int}
    // Siguientes: -
    private SentenciaNodo varLocal() throws LexicalException, SyntacticException, SemanticException {
        VarLocal localVar = new VarLocal();
        VarLocalNodo varLocalNodo = null;
        if(Arrays.asList("idKeyWord_var").contains(actualToken.getToken())){
            varLocalNodo = new VarLocalNodo();
            match("idKeyWord_var");
            //TODO asignarle un tipo mediante el tipo resultado de la asignacion?
            localVar.setVarToken(actualToken);
            varLocalNodo.setVarLocalToken(actualToken);
            match("idMetVar");
            st.getActualMethod().getMainBlock().addVar(localVar);
            varLocalResto(varLocalNodo);
        }else if(Arrays.asList("idKeyWord_boolean", "idKeyWord_char", "idKeyWord_int").contains(actualToken.getToken())){
            Type type = tipoPrimitivo();
            localVar.setVarType(type);
            localVar.setVarToken(actualToken);
            match("idMetVar");
            st.getActualMethod().getMainBlock().addVar(localVar);
            //TODO revisar si este nul no perjudica, no deberia.
            varLocalResto(null);
        }else{
            throw new SyntacticException(actualToken, "{var, boolean, char, int}");
        }
        return varLocalNodo;
    }

    // 43 ------------------------------------------------------------------------------
    // <VarLocalResto> ::= = <Expresion> <VarLocalResto> | <ListaDecAtrs> <VarLocalResto> ; | e
    // Primeros: { = , , }
    // Siguientes: -
    private void varLocalResto(VarLocalNodo varLocalNodo) throws LexicalException, SyntacticException, SemanticException {
        if(varLocalNodo != null){
            if(Arrays.asList("assignment").contains(actualToken.getToken())){
                match("assignment");
                varLocalNodo.setParteDerecha(expresion());
                varLocalResto(null);
            }else if(Arrays.asList("punctuationComma").contains(actualToken.getToken())) {
                match("punctuationComma");
                match("idMetVar");
                varLocalResto(null);
            }else if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
                //vacio
            }else{
                throw new SyntacticException(actualToken, "{ = , , , ; }");
            }
        }else{
            varLocalRestoSinNodo();
        }
    }

    private void varLocalRestoSinNodo() throws LexicalException, SyntacticException, SemanticException {
        if(Arrays.asList("assignment").contains(actualToken.getToken())){
            match("assignment");
            expresion();
            varLocalRestoSinNodo();
        }else if(Arrays.asList("punctuationComma").contains(actualToken.getToken())) {
            match("punctuationComma");
            match("idMetVar");
            varLocalRestoSinNodo();
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
    private void varLocalTipoClase(Type idClass) throws LexicalException, SyntacticException, SemanticException {
        VarLocal localVar = new VarLocal();
//        VarLocalNodo varLocalNodo = new VarLocalNodo();
//        varLocalNodo.setVarLocalToken(actualToken);
//        TODO varLocalNodo.setVarLocalNodoType(idClass);
        localVar.setVarType(idClass);
        localVar.setVarToken(actualToken);
        genericoNotOpt();
        match("idMetVar");
        st.getActualMethod().getMainBlock().addVar(localVar);
        listaDecVarLocal(idClass);
        varLocalTipoClaseResto();
//        return varLocalNodo;
    }

    // 44AUX ------------------------------------------------------------------------------
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

    // 44AUX ------------------------------------------------------------------------------
    // <ListaDecAtrs> ::= , idMetVar <ListaDecAtrs> | e
    // Primeros: { , , e }
    // Siguientes: { ; , = }
    private void listaDecVarLocal(Type varLocalType) throws LexicalException, SyntacticException, SemanticException {
        if(Arrays.asList("punctuationComma").contains(actualToken.getToken())) {
            match("punctuationComma");
            VarLocal varLocal = new VarLocal();
            varLocal.setVarToken(actualToken);
            varLocal.setVarType(varLocalType);
            match("idMetVar");
            st.getActualMethod().getMainBlock().addVar(varLocal);
            listaDecVarLocal(varLocalType);
        }else if(Arrays.asList("assignment").contains(actualToken.getToken())) {
            match("assignment");
            expresion();
        }else if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
            //vacio
        }else{
            throw new SyntacticException(actualToken, "{ , , ; , = }");
        }
    }

    // 45 ------------------------------------------------------------------------------
    // <Return> ::= return <ExpresionOpt>
    // Primeros: {return}
    // Siguientes: -
    private SentenciaNodo return_() throws LexicalException, SyntacticException {
        ReturnNodo returnNodo = new ReturnNodo(st.getActualMethod(), actualToken);
        match("idKeyWord_return");
        returnNodo.setExpressionNode(expresionOpt());
        return returnNodo;
    }

    // 46 ------------------------------------------------------------------------------
    // <ExpresionOpt> ::= <Expresion> | e
    // Primeros: { + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase,e}
    // Siguientes: { ; }
    private ExpresionNodo expresionOpt() throws SyntacticException, LexicalException {
        ExpresionNodo expresionNodo = null;
        if(Arrays.asList("opAddition", "opSubtraction", "opNegation", "idKeyWord_null",
                "idKeyWord_true", "idKeyWord_false", "literalInteger", "literalCharacter", "literalString",
                "idKeyWord_this", "idMetVar", "idKeyWord_new", "punctuationOpeningParenthesis", "idClass").contains(actualToken.getToken())){
            expresionNodo = expresion();
        }else{
            if(Arrays.asList("punctuationSemicolon").contains(actualToken.getToken())){
                // vacio
            }else{
                throw new SyntacticException(actualToken,
                        "{ + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase, ; }");
            }
        }
        return expresionNodo;
    }

    // 47 ------------------------------------------------------------------------------
    // <If> ::= if ( <Expresion> ) <Sentencia> <IfResto>
    // Primeros: {if}
    // Siguientes: -
    private SentenciaNodo if_() throws LexicalException, SyntacticException, SemanticException {
        match("idKeyWord_if");
        match("punctuationOpeningParenthesis");
        expresion();
        match("punctuationClosingParenthesis");
        sentencia();
        ifResto();
        return null;
    }

    // 48 ------------------------------------------------------------------------------
    // <IfResto> ::= else <Sentencia> | e
    // Primeros: {else, e}
    // Siguientes: {else, ; , idClase, this, idMetVar, new, ( , var, boolean, char, int, return, if, while, { , } }
    private void ifResto() throws LexicalException, SyntacticException, SemanticException {
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
    private SentenciaNodo while_() throws LexicalException, SyntacticException, SemanticException {
        match("idKeyWord_while");
        match("punctuationOpeningParenthesis");
        expresion();
        match("punctuationClosingParenthesis");
        sentencia();
        return null;
    }

    // 50 ------------------------------------------------------------------------------
    // <Expresion> ::= <ExpresionUnaria> <ExpresionRec>
    // Primeros: { + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase}
    // Siguientes: -
    private ExpresionNodo expresion() throws LexicalException, SyntacticException {
        ExpresionNodo expresionNodo = expresionUnaria();
        ExpresionNodo expresionNodoRec = expresionRec(expresionNodo);
        if(expresionNodoRec == null){
            return expresionNodo;
        }else{
            return expresionNodoRec;
        }
    }

    // 51 ------------------------------------------------------------------------------
    // <ExpresionRec> ::= <OperadorBinario> <ExpresionUnaria> <ExpresionRec> | e
    // Primeros: { || , && , == , != , < , > , <= , >= , + , - , * , / , % , e}
    // Siguientes: { ; , , , ) }
    private ExpresionNodo expresionRec(ExpresionNodo leftExpresionNodo) throws SyntacticException, LexicalException {
        ExpresionBinariaNodo expresionBinariaNodo = null;
        if(Arrays.asList("opLogicOr", "opLogicAnd", "opEqual", "opDistinct",
                "opLess", "opGreater", "opLessOrEqual", "opGreaterOrEqual",
                "opAddition", "opSubtraction", "opMultiplication", "opDivision", "opModule").contains(actualToken.getToken())){
            expresionBinariaNodo = operadorBinario();
            expresionBinariaNodo.setLeftExpressionNode(leftExpresionNodo);
            ExpresionNodo rightExpresionNodo = expresionUnaria();
            expresionBinariaNodo.setRightExpressionNode(rightExpresionNodo);
            expresionRec(rightExpresionNodo);
        }else if(Arrays.asList("punctuationSemicolon", "punctuationComma", "punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{ || , && , == , != , < , > , <= , >= , + , - , * , / , % , ; , , , ) }");
        }
        return expresionBinariaNodo;
    }

    // 52 ------------------------------------------------------------------------------
    // <OperadorBinario> ::= || | && | == | != | < | > | <= | >= | + | - | * | / | %
    // Primeros: { || , && , == , != , < , > , <= , >= , + , - , * , / , % }
    // Siguientes: -
    private ExpresionBinariaNodo operadorBinario() throws LexicalException, SyntacticException {
        ExpresionBinariaNodo binaryOperatorNode = new ExpresionBinariaNodo(actualToken);
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
        return binaryOperatorNode;
    }

    // 53 ------------------------------------------------------------------------------
    // <ExpresionUnaria> ::= <OperadorUnario> <Operando> | <Operando>
    // Primeros: { + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase}
    // Siguientes: -
    private ExpresionUnariaNodo expresionUnaria() throws LexicalException, SyntacticException {
        ExpresionUnariaNodo unaryExpressionNode = new ExpresionUnariaNodo();
        if(Arrays.asList("opAddition", "opSubtraction", "opNegation").contains(actualToken.getToken())){
            unaryExpressionNode.setUnaryOperator(operadorUnario());
            unaryExpressionNode.setOperateNode(operando());
        }else if(Arrays.asList("idKeyWord_null", "idKeyWord_true", "idKeyWord_false",
                "literalInteger", "literalCharacter","literalString", "idKeyWord_this",
                "idMetVar", "idKeyWord_new", "punctuationOpeningParenthesis", "idClass").contains(actualToken.getToken())){
            unaryExpressionNode.setOperateNode(operando());
        }else{
            throw new SyntacticException(actualToken, "{ + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( }");
        }
        return unaryExpressionNode;
    }

    // 54 ------------------------------------------------------------------------------
    // <OperadorUnario> ::= + | - | !
    // Primeros: { + , - , ! }
    // Siguientes: -
    private Token operadorUnario() throws LexicalException, SyntacticException {
        Token unaryOperator = actualToken;
        if(Arrays.asList("opAddition").contains(actualToken.getToken())){
            match("opAddition");
        }else if(Arrays.asList("opSubtraction").contains(actualToken.getToken())){
            match("opSubtraction");
        }else if(Arrays.asList("opNegation").contains(actualToken.getToken())){
            match("opNegation");
        }else{
            throw new SyntacticException(actualToken, "{ + , - , ! }");
        }
        return unaryOperator;
    }

    // 55 ------------------------------------------------------------------------------
    // <Operando> ::= <Literal> | <Acceso>
    // Primeros: {null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase}
    // Siguientes: -
    private OperandoNodo operando() throws SyntacticException, LexicalException {
        OperandoNodo operandoNodo;
        if(Arrays.asList("idKeyWord_null", "idKeyWord_true", "idKeyWord_false",
                "literalInteger", "literalCharacter", "literalString").contains(actualToken.getToken())){
            operandoNodo = literal();
        }else if(Arrays.asList("idKeyWord_this", "idMetVar", "idKeyWord_new", "punctuationOpeningParenthesis", "idClass").contains(actualToken.getToken())){
            operandoNodo = acceso();
        }else{
            throw new SyntacticException(actualToken, "{null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( }");
        }
        return operandoNodo;
    }

    // 56 ------------------------------------------------------------------------------
    // <Literal> ::= null | true | false | intLiteral | charLiteral | stringLiteral
    // Primeros: {null, true, false, intLiteral, charLiteral, stringLiteral}
    // Siguientes: -
    private LiteralNodo literal() throws LexicalException, SyntacticException {
        LiteralNodo literalNodo;
        if(Arrays.asList("idKeyWord_null").contains(actualToken.getToken())){
            literalNodo = new LiteralNullNodo(actualToken);
            match("idKeyWord_null");
        }else if(Arrays.asList("idKeyWord_true").contains(actualToken.getToken())){
            literalNodo = new LiteralBooleanNodo(actualToken);
            match("idKeyWord_true");
        }else if(Arrays.asList("idKeyWord_false").contains(actualToken.getToken())){
            literalNodo = new LiteralBooleanNodo(actualToken);
            match("idKeyWord_false");
        }else if(Arrays.asList("literalInteger").contains(actualToken.getToken())){
            literalNodo = new LiteralIntegerNodo(actualToken);
            match("literalInteger");
        }else if(Arrays.asList("literalCharacter").contains(actualToken.getToken())){
            literalNodo = new LiteralCharacterNodo(actualToken);
            match("literalCharacter");
        }else if(Arrays.asList("literalString").contains(actualToken.getToken())){
            literalNodo = new LiteralStringNodo(actualToken);
            match("literalString");
        }else{
            throw new SyntacticException(actualToken, "{null, true, false, intLiteral, charLiteral, stringLiteral}");
        }
        return literalNodo;
    }

    // 57 ------------------------------------------------------------------------------
    // <Acceso> ::= <Primario> <EncadenadoOpt>
    // Primeros: {this, idMetVar, new, ( , idClase}
    // Siguientes: -
    private AccesoNodo acceso() throws LexicalException, SyntacticException {
        AccesoNodo accesoNodo = new AccesoNodo();
        accesoNodo.setPrimarioNodo(primario());
        accesoNodo.setEncadenadoOptNodo(encadenadoOpt());
        return accesoNodo;
    }

    // 58 ------------------------------------------------------------------------------
    // <Primario> ::= <AccesoThis> | <AccesoVar> | <AccesoConstructor> | <ExpresionParentizada> | idClase <GenericoOpt> <AccesoMetodoEstatico>
    // Primeros: {this, idMetVar, new, ( , idClase}
    // Siguientes: -
    private PrimarioNodo primario() throws SyntacticException, LexicalException {
        PrimarioNodo primarioNodo = null;
        if(Arrays.asList("idKeyWord_this").contains(actualToken.getToken())){
            primarioNodo = accesoThis();
        }else if(Arrays.asList("idMetVar").contains(actualToken.getToken())){
            primarioNodo = accesoVar();
        }else if(Arrays.asList("idKeyWord_new").contains(actualToken.getToken())){
            primarioNodo = accesoConstructor();
        }else if(Arrays.asList("punctuationOpeningParenthesis").contains(actualToken.getToken())) {
            primarioNodo = expresionParentizada();
        }else if(Arrays.asList("idClass").contains(actualToken.getToken())){
            match("idClass");
            genericoNotOpt();
            accesoMetodoEstatico();
        }else{
            throw new SyntacticException(actualToken,"{this, idMetVar, new, ( , idClass}");
        }
        return primarioNodo;
    }

    // 59 ------------------------------------------------------------------------------
    // <AccesoThis> ::= this
    // Primeros: {this}
    // Siguientes: -
    private AccesoThisNodo accesoThis() throws LexicalException, SyntacticException {
        AccesoThisNodo thisPrimaryNode = new AccesoThisNodo(actualToken);
        match("idKeyWord_this");
        return thisPrimaryNode;
    }

    // 60 ------------------------------------------------------------------------------
    // <AccesoVar> ::= idMetVar <AccesoMetodo>
    // Primeros: {idMetVar}
    // Siguientes: -
    private PrimarioNodo accesoVar() throws LexicalException, SyntacticException {
        Token idMetVar = actualToken;
        match("idMetVar");
        AccesoMetNodo accesoMetNodo = accesoMetodo(idMetVar);
        if(accesoMetNodo == null){
            return new AccesoVarNodo(idMetVar);
        }else{
            return accesoMetNodo;
        }
    }

    // 61 ------------------------------------------------------------------------------
    // <AccesoConstructor> ::= new idClase <GenericoOpt> ( <ListaExpsOpt> )
    // Primeros: {new}
    // Siguientes: -
    private AccesoConstructorNodo accesoConstructor() throws LexicalException, SyntacticException {
        match("idKeyWord_new");
        AccesoConstructorNodo accesoConstructorNodo = new AccesoConstructorNodo(actualToken);
        match("idClass");
        genericoOptional();
        match("punctuationOpeningParenthesis");
        accesoConstructorNodo.setArgumentosActuales(listaExpsOpt());
        match("punctuationClosingParenthesis");
        return accesoConstructorNodo;
    }

    // 62 ------------------------------------------------------------------------------
    // <ExpresionParentizada> ::= ( <Expresion> )
    // Primeros: { ( }
    // Siguientes: -
    private ExpresionNodo expresionParentizada() throws LexicalException, SyntacticException {
        match("punctuationOpeningParenthesis");
        ExpresionNodo expresionParentizadaNodo = expresion();
        match("punctuationClosingParenthesis");
        return expresionParentizadaNodo;
    }

    // 63 ------------------------------------------------------------------------------
    // <AccesoMetodo> ::= <ArgsActuales> | e
    // Primeros: { ( , e }
    // Siguientes: { = , += , -= , || , && , == , != , < , > , <= , >= , + , - , * , / , % , . , , , ; , ) }
    private AccesoMetNodo accesoMetodo(Token idMetodo) throws SyntacticException, LexicalException {
        AccesoMetNodo accesoMetNodo = null;
        if(Arrays.asList("punctuationOpeningParenthesis").contains(actualToken.getToken())){
            accesoMetNodo = new AccesoMetNodo(idMetodo);
            accesoMetNodo.setArgsActuales(argsActuales());
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
        return accesoMetNodo;
    }

    // 64 ------------------------------------------------------------------------------
    // <AccesoMetodoEstatico> ::= . idMetVar <AccesoMetodoVariableEstatico>
    // Primeros: { . }
    // Siguientes: -
    private void accesoMetodoEstatico() throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationPoint").contains(actualToken.getToken())){
            match("punctuationPoint");
            match("idMetVar");
            accesoMetodoVariableEstatica();
        }
    }

    // <AccesoMetodoVariableEstatico> ::= <argsActuales> <AccesoMetodoEstatico> | e
    // Primeros: { ( , e }
    // Siguientes: { = , += , -= , || , && , == , != , < , > , <= , >= , + , - , * , / , % , . , ; , , , ) }
    private void accesoMetodoVariableEstatica() throws LexicalException, SyntacticException {
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
    private ArrayList<ExpresionNodo> argsActuales() throws LexicalException, SyntacticException {
        match("punctuationOpeningParenthesis");
        ArrayList<ExpresionNodo> expresionNodos = listaExpsOpt();
        match("punctuationClosingParenthesis");
        return expresionNodos;
    }

    // 66 ------------------------------------------------------------------------------
    // <ListaExpsOpt> ::= <ListaExps> | e
    // Primeros: { + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase, e}
    // Siguientes: { ) }
    private ArrayList<ExpresionNodo> listaExpsOpt() throws SyntacticException, LexicalException {
        ArrayList<ExpresionNodo> expresionNodos = new ArrayList<>();
        if(Arrays.asList("opAddition", "opSubtraction", "opNegation",
                "idKeyWord_null", "idKeyWord_true", "idKeyWord_false",
                "literalInteger", "literalCharacter", "literalString",
                "idKeyWord_this", "idMetVar", "idKeyWord_new",
                "punctuationOpeningParenthesis", "idClass").contains(actualToken.getToken())){
            expresionNodos = listaExps();
        }else if(Arrays.asList("punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            // Se esperaba el inicio de una expresion
            throw new SyntacticException(actualToken, "{ + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase, ) }");
        }
        return expresionNodos;
    }

    // 67 ------------------------------------------------------------------------------
    // <ListaExps> ::= <Expresion> <ListaExpsResto>
    // Primeros: { + , - , ! , null, true, false, intLiteral, charLiteral, stringLiteral, this, idMetVar, new, ( , idClase}
    // Siguientes: -
    private ArrayList<ExpresionNodo> listaExps() throws LexicalException, SyntacticException {
        ExpresionNodo expresionNodo = expresion();
        ArrayList<ExpresionNodo> expresionNodos = listaExpsResto();
        expresionNodos.add(expresionNodo);
        return expresionNodos;
    }

    // 68 ------------------------------------------------------------------------------
    // <ListaExpsResto> ::= , <ListaExps> | e
    // Primeros: { , , e }
    // Siguientes: { ) }
    private ArrayList<ExpresionNodo> listaExpsResto() throws SyntacticException, LexicalException {
        ArrayList<ExpresionNodo> expresionNodos = new ArrayList<>();
        if(Arrays.asList("punctuationComma").contains(actualToken.getToken())){
            match("punctuationComma");
            expresionNodos = listaExps();
        }else if(Arrays.asList("punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken,"{ , , ) }");
        }
        return expresionNodos;
    }

    // 69 ------------------------------------------------------------------------------
    // <EncadenadoOpt> ::= . idMetVar <MetVarEncadenada> | e
    // Primeros: { . , e }
    // Siguientes: { = , += , -= , || , && , == , != , < , > , <= , >= , + , - , * , / , % , ; , , , ) }
    private EncadenadoOptNodo encadenadoOpt() throws LexicalException, SyntacticException {
        EncadenadoOptNodo encadenadoOptNodo = null;
        if(Arrays.asList("punctuationPoint").contains(actualToken.getToken())){
            match("punctuationPoint");
            encadenadoOptNodo = new EncadenadoOptNodo();
            encadenadoOptNodo.setIdMetVarToken(actualToken);
            match("idMetVar");
            metVarEncadenada(encadenadoOptNodo);
        }else if(Arrays.asList("assignment", "assignmentAddition", "assignmentSubtraction",
                "opLogicOr", "opLogicAnd", "opEqual", "opDistinct",
                "opGreater", "opGreaterOrEqual", "opLess", "opLessOrEqual",
                "opAddition", "opSubtraction", "opMultiplication", "opDivision", "opModule",
                "punctuationSemicolon", "punctuationComma", "punctuationClosingParenthesis").contains(actualToken.getToken())){
            // vacio
        }else{
            throw new SyntacticException(actualToken, "{ . , = , += , -= , || , && , == , != , < , > , <= , >= , + , - , * , / , % , ; , , , ) }");
        }
        return encadenadoOptNodo;
    }

    // 70 ------------------------------------------------------------------------------
    // <MetVarEncadenada> ::= <EncadenadoOpt> | <ArgsActuales> <EncadenadoOpt>
    // Primeros: { . , = , += , -= , || , && , == , != , < , > , <= , >= , + , - , * , / , % , ; , , , ) , ( }
    // Siguientes: -
    private void metVarEncadenada(EncadenadoOptNodo encadenadoOptNodo) throws LexicalException, SyntacticException {
        if(Arrays.asList("punctuationPoint", "assignment", "assignmentAddition", "assignmentSubtraction",
                "opLogicOr", "opLogicAnd", "opEqual", "opDistinct",
                "opGreater", "opGreaterOrEqual", "opLess", "opLessOrEqual",
                "opAddition", "opSubtraction", "opMultiplication", "opDivision",
                "opModule", "punctuationSemicolon", "punctuationComma", "punctuationClosingParenthesis").contains(actualToken.getToken())){
            encadenadoOpt();
        }else if(Arrays.asList("punctuationOpeningParenthesis").contains(actualToken.getToken())){
            encadenadoOptNodo.setArgumentos(argsActuales());
            encadenadoOptNodo.setChainedOptNode(encadenadoOpt());
        }else{
            throw new SyntacticException(actualToken, "{ . , = , += , -= , || , && , == , != , < , > , <= , >= , + , - , * , / , % , ; , , , ) , ( }");
        }
    }

}
