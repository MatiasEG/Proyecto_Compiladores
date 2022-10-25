package minijava.compiler.semantic.nodes.expresion.binaria;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionBinaryExpressionWithDiferentTypes;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionBinariaNodo;
import minijava.compiler.semantic.tables.Type;

public class ModuloNodo extends ExpresionBinariaNodo {

    public ModuloNodo(Token modulo){
        super(modulo);
    }

    public Type check(SymbolTable st) throws SemanticException {
        Type tipoLadoIzq = ladoIzquierdo.check(st);
        Type tipoLadoDer = ladoDerecho.check(st);
        if(tipoLadoIzq.getTypeForAssignment().equals(tipoLadoDer.getTypeForAssignment()) && tipoLadoDer.getTypeForAssignment().equals("int")){
            return new Type(new Token("idKeyWord_int", "int", tipoLadoDer.getTokenType().getRow()));
        }else{
            throw new SemanticExceptionBinaryExpressionWithDiferentTypes(operatorToken);
        }
    }
}
