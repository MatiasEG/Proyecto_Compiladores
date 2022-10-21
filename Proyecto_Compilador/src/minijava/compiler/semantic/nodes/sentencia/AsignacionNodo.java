package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionAssignmentWithDiferentTypes;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongLeftTypeInAssignment;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.nodes.expresion.operando.AccesoNodo;
import minijava.compiler.semantic.tables.Type;

public class AsignacionNodo extends SentenciaNodo{

    private AccesoNodo parteIzquierda;
    private ExpresionNodo parteDerecha;
    private Token tipoAsignacion;

    public AsignacionNodo(Token tipoAsignacion){
        this.tipoAsignacion = tipoAsignacion;
    }

    public void setParteIzquierda(AccesoNodo accesoNodo){ parteIzquierda = accesoNodo; }

    public void setParteDerecha(ExpresionNodo exp){ parteDerecha = exp; }

    @Override
    public void check(SymbolTable st) throws SemanticException {
        Type tipoParteIzquierda = parteIzquierda.check(st);
        Type tipoParteDerecha = parteDerecha.check(st);

        if(parteIzquierda.isAssignable(st)){
            if(!tipoParteIzquierda.isClassRef()){
                if(!tipoParteIzquierda.getTypeForAssignment().equals(tipoParteDerecha.getTypeForAssignment())){
                    throw new SemanticExceptionAssignmentWithDiferentTypes(tipoAsignacion);
                }
            }else if(!tipoParteIzquierda.getLexemeType().equals(tipoParteDerecha.getTypeForAssignment())){
                throw new SemanticExceptionAssignmentWithDiferentTypes(tipoAsignacion);
            }
        }else{
            throw new SemanticExceptionWrongLeftTypeInAssignment(tipoAsignacion);
        }

    }
}
