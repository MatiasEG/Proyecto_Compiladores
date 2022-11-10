package minijava.compiler.semantic.nodes.expresion.operando;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public abstract class PrimarioNodo {

    protected Token idPrimario;
    protected boolean esLadoIzquierdo;
    protected boolean tieneEncadenado;

    public Token getIdPrimario(){ return idPrimario; }

    public abstract Type check(SymbolTable st) throws SemanticException;

    public abstract boolean isAssignable(SymbolTable st);

    public boolean esLlamable(){ return true; }

    public void esLadoIzquierdo(boolean esLadoIzquierdo){ this.esLadoIzquierdo = esLadoIzquierdo; }

    public void tieneEncadenado(boolean tieneEncadenado){ this.tieneEncadenado = tieneEncadenado; }

    public abstract void generar(SymbolTable st) throws IOException;
}
