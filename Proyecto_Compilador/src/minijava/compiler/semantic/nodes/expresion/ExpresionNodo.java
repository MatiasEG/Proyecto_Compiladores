package minijava.compiler.semantic.nodes.expresion;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.primario.PrimarioNodo;
import minijava.compiler.semantic.tables.Type;

public abstract class ExpresionNodo extends PrimarioNodo {

    public abstract Type check(SymbolTable st) throws SemanticException;
//    {
//        Type type1 = unaryExpressionNode.check(st);
//        Type type2 = expresionNodo.check(st);
//        if(type1.getLexemeType().equals(type2.getLexemeType())){
//            return type1;
//        }else{
//            throw new SemanticExceptionAux("ExpressionNodeError");
//        }
//    }
}
