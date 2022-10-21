package minijava.compiler.semantic.nodes.expresion.operando.primario;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.tables.Type;

import java.util.ArrayList;

public class AccesoConstructorNodo extends PrimarioNodo {

    private Token idClase;
    private Type returnType;
    private ArrayList<ExpresionNodo> argumentosActuales;

    public AccesoConstructorNodo(Token idClase){
        this.idClase = idClase;
        returnType = new Type(idClase);
    }

    public void setArgumentosActuales(ArrayList<ExpresionNodo> argumentosActuales){ this.argumentosActuales = argumentosActuales; }

    @Override
    public Type check(SymbolTable st) throws SemanticException {
        return returnType;
    }

    @Override
    public boolean isAssignable(SymbolTable st) {
        return false;
    }
}
