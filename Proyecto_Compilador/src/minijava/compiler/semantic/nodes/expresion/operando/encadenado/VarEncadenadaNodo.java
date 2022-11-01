package minijava.compiler.semantic.nodes.expresion.operando.encadenado;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionAttributteNotDefinedInClassRef;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.EncadenadoOptNodo;
import minijava.compiler.semantic.tables.Type;

public class VarEncadenadaNodo extends EncadenadoOptNodo {

    public VarEncadenadaNodo(){
        encadenadoOptNodo = null;
        idMetVar = null;
        tipoPrimarioNodo = null;
    }

    public boolean isAssignable(SymbolTable st){
        if(encadenadoOptNodo == null){
            return isAtribute(tipoPrimarioNodo, st);
        }else{
            return encadenadoOptNodo.isAssignable(st);
        }
    }

    private boolean isAtribute(Type tipoPrimarioNodo, SymbolTable st){
        return st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapAtributes().containsKey(idMetVar.getLexeme());
    }

    public Type check(Type tipoPrimarioNodo, SymbolTable st) throws SemanticException{
        this.tipoPrimarioNodo = tipoPrimarioNodo;
        if(isAtribute(tipoPrimarioNodo, st)) {
            if (encadenadoOptNodo == null) {
                return st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapAtributes().get(idMetVar.getLexeme()).getVarType();
            } else {
                return encadenadoOptNodo.check(st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapAtributes().get(idMetVar.getLexeme()).getVarType(), st);
            }
        }else{
            throw new SemanticExceptionAttributteNotDefinedInClassRef(idMetVar);
        }
    }

}
