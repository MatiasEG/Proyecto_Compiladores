package minijava.compiler.semantic.nodes.expresion.operando.primario;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

public class AccesoClase extends PrimarioNodo {

    private Type classType;
    private Token classToken;
    private AccesoMetVarEstaticoNodo accesoMetVarEstaticoNodo;

    public AccesoClase(Token classToken){ this.classToken = classToken; classType = new Type(classToken); }

    public void setEncadenadoClaseEstatico(AccesoMetVarEstaticoNodo accesoMetVarEstaticoNodo){ this.accesoMetVarEstaticoNodo = accesoMetVarEstaticoNodo; }

    @Override
    public Type check(SymbolTable st) throws SemanticException {
        return accesoMetVarEstaticoNodo.check(st);
    }

    @Override
    public boolean isAssignable(SymbolTable st) {
        return false;
    }
}
