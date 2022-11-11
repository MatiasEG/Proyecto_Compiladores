package minijava.compiler.semantic.nodes.sentencia.asignacion;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionInvalidTypeForIntAsignation;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongLeftTypeInAssignment;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.sentencia.AsignacionNodo;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public class AsignacionMasNodo extends AsignacionNodo {

    public AsignacionMasNodo(Token tipoAsignacion){
        super(tipoAsignacion);
    }

    @Override
    public void check(SymbolTable st) throws SemanticException {
        Type tipoParteIzquierda = parteIzquierda.check(st);
        Type tipoParteDerecha = parteDerecha.check(st);

        if(parteIzquierda.isAssignable(st)){
            if(!(tipoParteDerecha.getTypeForAssignment().equals(tipoParteIzquierda.getTypeForAssignment()) && tipoParteDerecha.getTypeForAssignment().equals("int")))
                throw new SemanticExceptionInvalidTypeForIntAsignation(tipoAsignacion);
        }else{
            throw new SemanticExceptionWrongLeftTypeInAssignment(tipoAsignacion);
        }

    }

    @Override
    public void generar(SymbolTable st) throws IOException {
        parteIzquierda.generar(st);
        parteDerecha.generar(st);
        st.write("ADD\n");
        parteIzquierda.esLadoIzquierod(true);
        parteIzquierda.generar(st);
    }
}
