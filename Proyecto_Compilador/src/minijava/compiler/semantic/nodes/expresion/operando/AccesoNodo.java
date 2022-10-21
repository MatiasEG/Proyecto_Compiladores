package minijava.compiler.semantic.nodes.expresion.operando;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.primario.PrimarioNodo;
import minijava.compiler.semantic.tables.Type;

public class AccesoNodo extends OperandoNodo {

    private PrimarioNodo primarioNodo;
    private EncadenadoOptNodo encadenadoOptNodo;

    public AccesoNodo(){
        encadenadoOptNodo = null;
    }

    public void setPrimarioNodo(PrimarioNodo primarioNodo){ this.primarioNodo = primarioNodo; }

    public void setEncadenadoOptNodo(EncadenadoOptNodo encadenadoOptNodo){ this.encadenadoOptNodo = encadenadoOptNodo; }

    public boolean isAssignable(SymbolTable st){
        if(encadenadoOptNodo == null){
            return primarioNodo.isAssignable(st);
        }else{
            return encadenadoOptNodo.isAssignable(st);
        }
    }

    public Type check(SymbolTable st) throws SemanticException{
        Type tipoPrimarioNodo = primarioNodo.check(st);

        if(encadenadoOptNodo == null){
            return tipoPrimarioNodo;
        }else{
            return encadenadoOptNodo.check(tipoPrimarioNodo, st);
        }
    }
}
