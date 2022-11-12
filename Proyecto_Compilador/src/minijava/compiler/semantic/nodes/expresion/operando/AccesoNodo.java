package minijava.compiler.semantic.nodes.expresion.operando;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionThisNeedChained;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.primario.AccesoThisNodo;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public class AccesoNodo extends OperandoNodo {

    private PrimarioNodo primarioNodo;
    private EncadenadoOptNodo encadenadoOptNodo;

    public AccesoNodo(){
        encadenadoOptNodo = null;
    }

    public void setPrimarioNodo(PrimarioNodo primarioNodo){ this.primarioNodo = primarioNodo; }

    public void setEncadenadoOptNodo(EncadenadoOptNodo encadenadoOptNodo){ this.encadenadoOptNodo = encadenadoOptNodo; }

    public boolean esLlamable(){
        return primarioNodo.esLlamable();
    }

    public boolean isAssignable(SymbolTable st){
        if(encadenadoOptNodo == null){
            return primarioNodo.isAssignable(st);
        }else{
            return encadenadoOptNodo.isAssignable(st);
        }
    }

    public Token getIdPrimario(){
        return primarioNodo.getIdPrimario();
    }

    public Type check(SymbolTable st) throws SemanticException{
        Type tipoPrimarioNodo = primarioNodo.check(st);

        if(encadenadoOptNodo == null){
            if(primarioNodo.esLadoIzquierdo && primarioNodo instanceof AccesoThisNodo){
                throw new SemanticExceptionThisNeedChained(primarioNodo.getIdPrimario());
            }else{
                return tipoPrimarioNodo;
            }
        }else{
            encadenadoOptNodo.setPrimarioNodo(primarioNodo);
            return encadenadoOptNodo.check(tipoPrimarioNodo, st);
        }
    }

    public void esLadoIzquierod(boolean esLadoIzquierdo){ primarioNodo.esLadoIzquierdo(esLadoIzquierdo); }

    @Override
    public void generar(SymbolTable st) throws IOException {
        primarioNodo.tieneEncadenado(encadenadoOptNodo!=null);
        primarioNodo.generar(st);

        if(encadenadoOptNodo != null){
            encadenadoOptNodo.esLadoIzquierdo(primarioNodo.esLadoIzquierdo);
            encadenadoOptNodo.generar(st);
        }
        //TODO probablemente haya que hacer el CALL aca
    }
}
