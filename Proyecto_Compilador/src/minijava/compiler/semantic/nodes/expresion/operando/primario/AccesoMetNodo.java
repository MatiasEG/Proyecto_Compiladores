package minijava.compiler.semantic.nodes.expresion.operando.primario;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionCantCallDynamicMethodOnStaticMethod;
import minijava.compiler.exception.SemanticP2.SemanticExceptionMethodNotExistInCallerClass;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongQuantityParameters;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongTypeActualArgs;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.tables.Method;
import minijava.compiler.semantic.tables.Type;
import minijava.compiler.semantic.tables.variable.Parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class AccesoMetNodo extends PrimarioNodo{

    private ArrayList<ExpresionNodo> actualArgsExpresionNodes;
    private Token idMetodo;

    public AccesoMetNodo(Token metToken) {
        idMetodo = metToken;
    }

    public void setArgsActuales(ArrayList<ExpresionNodo> expresionNodos){
        Collections.reverse(expresionNodos);
        this.actualArgsExpresionNodes = expresionNodos;
    }

    @Override
    public Type check(SymbolTable st) throws SemanticException {
        if(!st.getActualClass().getHashMapMethodsWithoutOverloaded().containsKey(idMetodo.getLexeme()))
            throw new SemanticExceptionMethodNotExistInCallerClass(idMetodo);

        Method m = st.getActualClass().getHashMapMethodsWithoutOverloaded().get(idMetodo.getLexeme());
        ArrayList<Parameter> tiposParametrosDeclarados = m.getParameters();
        Iterator<ExpresionNodo> expresionNodosIterablre = actualArgsExpresionNodes.iterator();
        if(actualArgsExpresionNodes.size() == tiposParametrosDeclarados.size()){
            ExpresionNodo exp;
            for(Parameter p: tiposParametrosDeclarados){
                exp = expresionNodosIterablre.next();
                Type typeExp = exp.check(st);
                if(typeExp.isClassRef() && p.getVarType().isClassRef() &&
                        st.bSubtipoA(typeExp.getLexemeType(), p.getVarType().getLexemeType()) != null){
                    // vacio, si se da el caso de que no coinciden el tercer if lo va a detectar
                }else if(typeExp.getLexemeType().equals("null") && p.getVarType().isClassRef()){
                    // vacio, si se da el caso de que no coinciden el tercer if lo va a detectar
                }else if(!typeExp.getTypeForAssignment().equals(p.getVarType().getTypeForAssignment())){
                    throw new SemanticExceptionWrongTypeActualArgs(idMetodo);
                }
            }

            if(st.getActualMethod().isStatic()) throw new SemanticExceptionCantCallDynamicMethodOnStaticMethod(idMetodo);
            
        }else{
            throw new SemanticExceptionWrongQuantityParameters(m.getMethodToken());
        }
        return m.getMethodType();
    }

    @Override
    public boolean isAssignable(SymbolTable st) {
        return false;
    }
}
