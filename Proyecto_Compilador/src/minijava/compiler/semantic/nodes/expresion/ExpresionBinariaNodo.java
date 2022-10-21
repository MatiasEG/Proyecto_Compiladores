package minijava.compiler.semantic.nodes.expresion;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionBinaryExpressionWithDiferentTypes;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

public class ExpresionBinariaNodo extends ExpresionNodo{

    private Token operatorToken;
    private ExpresionNodo ladoIzquierdo;
    private ExpresionNodo ladoDerecho;

    public ExpresionBinariaNodo(Token operatorToken){
        this.operatorToken = operatorToken;
    }

    public void setLeftExpressionNode(ExpresionNodo exp){ ladoIzquierdo = exp; }

    public void setRightExpressionNode(ExpresionNodo exp){ ladoDerecho = exp; }

    @Override
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
