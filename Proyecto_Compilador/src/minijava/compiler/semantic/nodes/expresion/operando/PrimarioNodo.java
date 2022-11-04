package minijava.compiler.semantic.nodes.expresion.operando;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

public abstract class PrimarioNodo {

    protected Token idPrimario;

    public Token getIdPrimario(){ return idPrimario; }

    public abstract Type check(SymbolTable st) throws SemanticException;

    public abstract boolean isAssignable(SymbolTable st);

    public boolean esLlamable(){ return true; }
}
