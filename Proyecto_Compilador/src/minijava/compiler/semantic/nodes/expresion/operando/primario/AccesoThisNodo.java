package minijava.compiler.semantic.nodes.expresion.operando.primario;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

public class AccesoThisNodo extends PrimarioNodo {

    private Token thisToken;

    public AccesoThisNodo(Token thisToken){
        this.thisToken = thisToken;
    }

    @Override
    public Type check(SymbolTable st) throws SemanticException {
        return null;
    }

    @Override
    public boolean isAssignable(SymbolTable st) {
        return false;
    }
}
