package minijava.compiler.semantic.nodes.expresion.operando.primario;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.*;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.nodes.expresion.operando.PrimarioNodo;
import minijava.compiler.semantic.tables.Method;
import minijava.compiler.semantic.tables.Type;
import minijava.compiler.semantic.tables.variable.Parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class AccesoMetEstaticoNodo extends PrimarioNodo {

    private ArrayList<ExpresionNodo> actualArgsExpresionNodes;
    private Token idMet;
    private Type classType;

    public AccesoMetEstaticoNodo(Type classType, Token metVarToken) {
        idMet = metVarToken;
        this.classType = classType;
        actualArgsExpresionNodes = null;
    }

    public void setArgsActuales(ArrayList<ExpresionNodo> expresionNodos){
        Collections.reverse(expresionNodos);
        this.actualArgsExpresionNodes = expresionNodos;
    }

    @Override
    public Type check(SymbolTable st) throws SemanticException {

        if(actualArgsExpresionNodes==null){
            throw new SemanticExeptionInvalidStaticInvocation(idMet);
        }else if(st.getClass(classType.getLexemeType()).getHashMapMethodsWithoutOverloaded().containsKey(idMet.getLexeme())) {
            Method m = st.getClass(classType.getLexemeType()).getHashMapMethodsWithoutOverloaded().get(idMet.getLexeme());
            if (m.isStatic()) {
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
                            throw new SemanticExceptionWrongTypeActualArgs(idMet);
                        }
                    }
                } else {
                    throw new SemanticExceptionWrongQuantityParameters(m.getMethodToken());
                }
                return m.getMethodType();
            } else {
                throw new SemanticExeptionInvalidStaticInvocation(idMet);
            }
        }else{
            throw new SemanticExceptionMethodNotExistInCallerClass(idMet);
        }
    }

    public boolean isAssignable(SymbolTable st) { return false; }
}
