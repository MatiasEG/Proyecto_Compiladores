package minijava.compiler.semantic.nodes.expresion.operando.encadenado;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionAttributteNotDefinedOrPrivateInClassRef;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.EncadenadoOptNodo;
import minijava.compiler.semantic.nodes.expresion.operando.primario.AccesoThisNodo;
import minijava.compiler.semantic.tables.Type;
import minijava.compiler.semantic.tables.variable.Attribute;

import java.io.IOException;

public class VarEncadenadaNodo extends EncadenadoOptNodo {

    private Attribute atributo;

    public VarEncadenadaNodo(){
        primarioNodo = null;
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
            if(st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapAtributes().get(idMetVar.getLexeme()).isPublic()){
                atributo = st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapAtributes().get(idMetVar.getLexeme());
                if (encadenadoOptNodo == null) {
                    return st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapAtributes().get(idMetVar.getLexeme()).getVarType();
                } else {
                    return encadenadoOptNodo.check(st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapAtributes().get(idMetVar.getLexeme()).getVarType(), st);
                }
            }else if(primarioNodo instanceof AccesoThisNodo){
                atributo = st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapAtributes().get(idMetVar.getLexeme());
                if (encadenadoOptNodo == null) {
                    return st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapAtributes().get(idMetVar.getLexeme()).getVarType();
                } else {
                    return encadenadoOptNodo.check(st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapAtributes().get(idMetVar.getLexeme()).getVarType(), st);
                }
            }else{
                throw new SemanticExceptionAttributteNotDefinedOrPrivateInClassRef(idMetVar);
            }
        }else{
            throw new SemanticExceptionAttributteNotDefinedOrPrivateInClassRef(idMetVar);
        }
    }

    @Override
    public void generar(SymbolTable st) throws IOException {
        if(!esLadoIzquierdo){
            st.write("LOADREF "+atributo.getOffset()+" # Cargo el atributo en el tope\n");
        }else{
            st.write("SWAP # Dejo el valor en el tope y la referencia al atributo en tope-1\n");
            st.write("STOREREF "+atributo.getOffset()+" # Guardo el valor en el atributo\n");
        }

        if(encadenadoOptNodo != null){
            encadenadoOptNodo.esLadoIzquierdo(esLadoIzquierdo);
            encadenadoOptNodo.generar(st);
        }
    }

}
