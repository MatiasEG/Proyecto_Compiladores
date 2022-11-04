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

    public void setIdMetVarToken(Token idMetVarToken){ idMetVar = idMetVarToken; }

    public void setChainedOptNode(EncadenadoOptNodo encadenadoOptNodo){ this.encadenadoOptNodo = encadenadoOptNodo; }

    public abstract boolean isAssignable(SymbolTable st);

    public abstract Type check(Type tipoPrimarioNodo, SymbolTable st) throws SemanticException;

    public abstract void generar(SymbolTable st) throws IOException;

}
