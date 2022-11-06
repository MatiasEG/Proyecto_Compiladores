package minijava.compiler.semantic.nodes.expresion.binaria;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionBinaryExpressionWithDiferentTypes;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionBinariaNodo;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public class AndNodo extends ExpresionBinariaNodo {

    public AndNodo(Token and){
        super(and);
    }

    public Type check(SymbolTable st) throws SemanticException {
        Type tipoLadoIzq = ladoIzquierdo.check(st);
        Type tipoLadoDer = ladoDerecho.check(st);
        if(tipoLadoIzq.getTypeForAssignment().equals(tipoLadoDer.getTypeForAssignment()) && tipoLadoDer.getTypeForAssignment().equals("boolean")){
            return new Type(new Token("idKeyWord_boolean", "boolean", tipoLadoDer.getTokenType().getRow()));
        }else{
            throw new SemanticExceptionBinaryExpressionWithDiferentTypes(operatorToken);
        }
    }
    @Override
    public void generar(SymbolTable st) throws IOException {
        ladoIzquierdo.generar(st);
        ladoDerecho.generar(st);
        st.write("AND\n");
    }
}
