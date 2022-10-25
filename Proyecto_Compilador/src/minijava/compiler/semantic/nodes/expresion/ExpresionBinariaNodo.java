package minijava.compiler.semantic.nodes.expresion;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionBinaryExpressionWithDiferentTypes;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

public abstract class ExpresionBinariaNodo extends ExpresionNodo{

    protected Token operatorToken;
    protected ExpresionNodo ladoIzquierdo;
    protected ExpresionNodo ladoDerecho;

    public ExpresionBinariaNodo(Token operatorToken){ this. operatorToken = operatorToken; }

    public void setLeftExpressionNode(ExpresionNodo exp){ ladoIzquierdo = exp; }

    public void setRightExpressionNode(ExpresionNodo exp){ ladoDerecho = exp; }

    //TODO hacer abstracto este metodo
    public Type check(SymbolTable st) throws SemanticException {
        Type tipoLadoIzq = ladoIzquierdo.check(st);
        Type tipoLadoDer = ladoDerecho.check(st);
        if(tipoLadoIzq.getTypeForAssignment().equals(tipoLadoDer.getTypeForAssignment())){
            return tipoLadoIzq;
        }else{
            throw new SemanticExceptionBinaryExpressionWithDiferentTypes(operatorToken);
        }
    }

    @Override
    public boolean isAssignable(SymbolTable st) {
        return false;
    }
}
