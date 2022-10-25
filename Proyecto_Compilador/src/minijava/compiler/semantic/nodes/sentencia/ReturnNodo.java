package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionReturn;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongReturnType;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongTypeActualArgs;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.tables.Method;
import minijava.compiler.semantic.tables.Type;

public class ReturnNodo extends SentenciaNodo {

    private ExpresionNodo expresionNodo;
    private Token returnToken;


    public ReturnNodo(Token returnToken){
        this.returnToken = returnToken;
    }

    public void setExpressionNode(ExpresionNodo expresionNodo){ this.expresionNodo = expresionNodo; }

    public void check(SymbolTable st) throws SemanticException {
        Type typeExp = expresionNodo.check(st);
        Type mType = st.getActualMethod().getMethodType();

        if(st.getActualMethod().needReturn()){

            if(typeExp.isClassRef() && mType.isClassRef() &&
                    st.bSubtipoA(mType.getLexemeType(), typeExp.getLexemeType()) != null){
                // vacio, si se da el caso de que no coinciden el tercer if lo va a detectar
            }else if(typeExp.getLexemeType().equals("null") && typeExp.isClassRef()){
                // vacio, si se da el caso de que no coinciden el tercer if lo va a detectar
            }else if(!typeExp.getTypeForAssignment().equals(mType.getTypeForAssignment())){
                throw new SemanticExceptionWrongReturnType(st.getActualMethod(), typeExp.getTokenType());
            }

        }else{
            throw new SemanticExceptionReturn(st.getActualMethod(), returnToken);
        }
    }
}
