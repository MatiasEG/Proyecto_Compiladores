package minijava.compiler.semantic.nodes.expresion.binaria;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionBinariaNodo;
import minijava.compiler.semantic.tables.Type;

public class DistinctNodo extends ExpresionBinariaNodo {

    public DistinctNodo(Token distinct){
        super(distinct);
    }

    public Type check(SymbolTable st) throws SemanticException {
        Type tipoLadoIzq = ladoIzquierdo.check(st);
        Type tipoLadoDer = ladoDerecho.check(st);

        if(tipoLadoIzq.isClassRef() && tipoLadoDer.isClassRef()){
            String subtipo = st.bSubtipoA(tipoLadoIzq.getLexemeType(), tipoLadoDer.getLexemeType());
            if(subtipo != null){
                return new Type(new Token("idKeyWord_false", "false", tipoLadoIzq.getTokenType().getRow()));
            }else{
                return new Type(new Token("idKeyWord_true", "true", tipoLadoIzq.getTokenType().getRow()));
            }
        }else{
            if(tipoLadoIzq.getTypeForAssignment().equals(tipoLadoDer.getTypeForAssignment())){
                return new Type(new Token("idKeyWord_false", "false", tipoLadoIzq.getTokenType().getRow()));
            }else{
                return new Type(new Token("idKeyWord_true", "true", tipoLadoIzq.getTokenType().getRow()));
            }
        }
    }
}
