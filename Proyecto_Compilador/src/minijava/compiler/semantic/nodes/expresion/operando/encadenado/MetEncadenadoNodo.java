package minijava.compiler.semantic.nodes.expresion.operando.encadenado;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionMethodNotDefinedInClassRef;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongQuantityParameters;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongTypeActualArgs;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.nodes.expresion.operando.EncadenadoOptNodo;
import minijava.compiler.semantic.tables.Method;
import minijava.compiler.semantic.tables.Type;
import minijava.compiler.semantic.tables.variable.Parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class MetEncadenadoNodo extends EncadenadoOptNodo {

    private ArrayList<ExpresionNodo> actualArgsExpresionNodes;

    public MetEncadenadoNodo(){
        actualArgsExpresionNodes = null;
        encadenadoOptNodo = null;
        idMetVar = null;
        tipoPrimarioNodo = null;
    }

    public void setArgumentos(ArrayList<ExpresionNodo> expresionNodos){
        Collections.reverse(expresionNodos);
        actualArgsExpresionNodes = expresionNodos;
    }

    public boolean isAssignable(SymbolTable st){
        if(encadenadoOptNodo == null){
            if(isMethod(tipoPrimarioNodo, st) && tipoPrimarioNodo.isClassRef())
                return true;
            else
                return false;
        }else{
            return encadenadoOptNodo.isAssignable(st);
        }
    }

    private boolean isMethod(Type tipoPrimarioNodo, SymbolTable st){
        return st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapMethodsWithoutOverloaded().containsKey(idMetVar.getLexeme());
    }

    public Type check(Type tipoPrimarioNodo, SymbolTable st) throws SemanticException {
        this.tipoPrimarioNodo = tipoPrimarioNodo;
        if(isMethod(tipoPrimarioNodo, st)) {
            Method m = st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapMethodsWithoutOverloaded().get(idMetVar.getLexeme());
            ArrayList<Parameter> tiposParametrosDeclarados = m.getParameters();
            Iterator<ExpresionNodo> expresionNodosIterablre = actualArgsExpresionNodes.iterator();
            if (actualArgsExpresionNodes.size() == tiposParametrosDeclarados.size()) {
                ExpresionNodo exp;
                for (Parameter p : tiposParametrosDeclarados) {
                    exp = expresionNodosIterablre.next();
                    Type typeExp = exp.check(st);
                    if (typeExp.isClassRef() && p.getVarType().isClassRef() &&
                            st.bSubtipoA(typeExp.getLexemeType(), p.getVarType().getLexemeType()) != null) {
                        // vacio, si se da el caso de que no coinciden el tercer if lo va a detectar
                    } else if (typeExp.getLexemeType().equals("null") && p.getVarType().isClassRef()) {
                        // vacio, si se da el caso de que no coinciden el tercer if lo va a detectar
                    } else if (!typeExp.getTypeForAssignment().equals(p.getVarType().getTypeForAssignment())) {
                        throw new SemanticExceptionWrongTypeActualArgs(idMetVar);
                    }
                }

            } else {
                throw new SemanticExceptionWrongQuantityParameters(m.getMethodToken());
            }
            if (encadenadoOptNodo == null) {
                return m.getMethodType();
            } else {
                return encadenadoOptNodo.check(m.getMethodType(), st);
            }
        }else{
            throw new SemanticExceptionMethodNotDefinedInClassRef(idMetVar);
        }
    }

    @Override
    public void generar(SymbolTable st) {
        //TODO generar
    }
}
