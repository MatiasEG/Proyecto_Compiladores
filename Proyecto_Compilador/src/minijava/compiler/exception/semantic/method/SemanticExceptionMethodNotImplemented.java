package minijava.compiler.exception.semantic.method;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Clase;
import minijava.compiler.semantic.tables.Metodo;

public class SemanticExceptionMethodNotImplemented extends SemanticException {

    private Clase claseQueNoImplementaMetodo;
    private Metodo metodoNoImplementado;

    public SemanticExceptionMethodNotImplemented(Clase claseQueNoImplementaMetodo, Metodo metodoNoImplementado){
        super("La clase \'"+claseQueNoImplementaMetodo.getNombre()+"\' no implementa el metodo \'" +metodoNoImplementado.getLexeme()+
                "\' de la interface \'"+metodoNoImplementado.getClaseDefinido()+"\'", metodoNoImplementado.getToken());
        this.claseQueNoImplementaMetodo = claseQueNoImplementaMetodo;
        this.metodoNoImplementado = metodoNoImplementado;
    }
}
