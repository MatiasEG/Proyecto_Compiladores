package minijava.compiler.semantic.nodes.expresion.operando;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public abstract class EncadenadoOptNodo {

    protected EncadenadoOptNodo encadenadoOptNodo;
    protected Token idMetVar;
    protected Type tipoPrimarioNodo;
    protected PrimarioNodo primarioNodo;
    protected boolean esLadoIzquierdo;

    public void setIdMetVarToken(Token idMetVarToken){ idMetVar = idMetVarToken; }

    public void setPrimarioNodo(PrimarioNodo primarioNodo){ this.primarioNodo = primarioNodo; }

    public void setChainedOptNode(EncadenadoOptNodo encadenadoOptNodo){ this.encadenadoOptNodo = encadenadoOptNodo; }

    public abstract boolean isAssignable(SymbolTable st);

    public abstract Type check(Type tipoPrimarioNodo, SymbolTable st) throws SemanticException;

    public abstract void generar(SymbolTable st) throws IOException;

    public void esLadoIzquierdo(boolean esLadoIzquierdo){ this.esLadoIzquierdo = esLadoIzquierdo; }

}
