package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionThisNeedChained;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.AccesoNodo;

public class LlamadaNodo extends SentenciaNodo{

    private AccesoNodo accesoNodo;

    public void setAccesoNodo(AccesoNodo accesoNodo){ this.accesoNodo = accesoNodo; }

    @Override
    public void check(SymbolTable st) throws SemanticException {
        if(accesoNodo.esLlamable()){
            accesoNodo.check(st);
        }else{
            throw new SemanticExceptionThisNeedChained(accesoNodo.getIdPrimario());
        }
    }
}
