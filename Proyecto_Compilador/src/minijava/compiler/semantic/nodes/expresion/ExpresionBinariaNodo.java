package minijava.compiler.semantic.nodes.expresion;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public abstract class ExpresionBinariaNodo extends ExpresionNodo{

    protected Token operatorToken;
    protected ExpresionNodo ladoIzquierdo;
    protected ExpresionNodo ladoDerecho;

    public ExpresionBinariaNodo(Token operatorToken){ this. operatorToken = operatorToken; }

    public void setLeftExpressionNode(ExpresionNodo exp){ ladoIzquierdo = exp; }

    public void setRightExpressionNode(ExpresionNodo exp){ ladoDerecho = exp; }

    public abstract Type check(SymbolTable st) throws SemanticException;

    @Override
    public boolean isAssignable(SymbolTable st) {
        return false;
    }

    public abstract void generar(SymbolTable st) throws IOException;
}
