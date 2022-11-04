package minijava.compiler.semantic.nodes.sentencia.asignacion;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionAssignmentWithDiferentTypes;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongLeftTypeInAssignment;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.sentencia.AsignacionNodo;
import minijava.compiler.semantic.tables.Type;

public class AsignacionEstandarNodo extends AsignacionNodo {

    public AsignacionEstandarNodo(Token tipoAsignacion){
        super(tipoAsignacion);
    }

    @Override
    public void check(SymbolTable st) throws SemanticException{
        Type tipoParteIzquierda = parteIzquierda.check(st);
        Type tipoParteDerecha = parteDerecha.check(st);

        if(parteIzquierda.isAssignable(st)){
            if(!tipoParteIzquierda.isClassRef()){
                if(!tipoParteIzquierda.getTypeForAssignment().equals(tipoParteDerecha.getTypeForAssignment())){
                    throw new SemanticExceptionAssignmentWithDiferentTypes(tipoAsignacion);
                }
            }else if(!tipoParteIzquierda.getLexemeType().equals("Object")){
                if(!tipoParteIzquierda.getLexemeType().equals(tipoParteDerecha.getTypeForAssignment())){
                    if(!tipoParteDerecha.getLexemeType().equals("null")){
                        if(tipoParteDerecha.isClassRef() &&
                                st.bSubtipoA(tipoParteDerecha.getLexemeType(), tipoParteIzquierda.getLexemeType()) == null)
                            throw new SemanticExceptionAssignmentWithDiferentTypes(tipoAsignacion);
                        if(!tipoParteDerecha.isClassRef()) throw new SemanticExceptionAssignmentWithDiferentTypes(tipoAsignacion);
                    }
                }
            }
        }else{
            throw new SemanticExceptionWrongLeftTypeInAssignment(tipoAsignacion);
        }

    }
}
