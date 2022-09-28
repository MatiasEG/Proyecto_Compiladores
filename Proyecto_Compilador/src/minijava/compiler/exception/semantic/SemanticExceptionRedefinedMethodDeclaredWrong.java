package minijava.compiler.exception.semantic;

import minijava.compiler.semantic.tables.ClaseInterface;
import minijava.compiler.semantic.tables.Metodo;

public class SemanticExceptionRedefinedMethodDeclaredWrong extends SemanticException{

    private Metodo metodoMalRedefinido;
    private ClaseInterface claseInterfaceHeredada;

    public SemanticExceptionRedefinedMethodDeclaredWrong(Metodo metodoMalRedefinido, ClaseInterface claseInterfaceHeredada){
        super("El metodo \'"+metodoMalRedefinido.getNombre()+"\' de la clase \'"+metodoMalRedefinido.getClaseDefinido()+"\' no esta definido como en su padre \'"+claseInterfaceHeredada.getNombre()+"\'.");
        this.metodoMalRedefinido = metodoMalRedefinido;
        this.claseInterfaceHeredada = claseInterfaceHeredada;
    }

    @Override
    public int getRow() {
        return metodoMalRedefinido.getLineaDeclaracion();
    }

    @Override
    public String getLexeme() {
        return metodoMalRedefinido.getNombre();
    }
}
