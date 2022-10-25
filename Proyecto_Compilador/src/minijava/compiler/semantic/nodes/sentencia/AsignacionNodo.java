package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.nodes.expresion.operando.AccesoNodo;

public abstract class AsignacionNodo extends SentenciaNodo{

    protected AccesoNodo parteIzquierda;
    protected ExpresionNodo parteDerecha;
    protected Token tipoAsignacion;

    public AsignacionNodo(Token tipoAsignacion){
        this.tipoAsignacion = tipoAsignacion;
    }

    public void setParteIzquierda(AccesoNodo accesoNodo){ parteIzquierda = accesoNodo; }

    public void setParteDerecha(ExpresionNodo exp){ parteDerecha = exp; }

    @Override
    public abstract void check(SymbolTable st) throws SemanticException;

}
