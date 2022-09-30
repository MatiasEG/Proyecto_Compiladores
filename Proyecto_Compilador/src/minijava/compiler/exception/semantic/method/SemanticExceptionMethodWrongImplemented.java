package minijava.compiler.exception.semantic.method;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Clase;
import minijava.compiler.semantic.tables.Metodo;

public class SemanticExceptionMethodWrongImplemented extends SemanticException {

    private Clase claseQueImplementaMal;
    private Metodo metodoMalImplementado;

    public SemanticExceptionMethodWrongImplemented(Clase claseQueImplementaMal, Metodo metodoMalImplementado){
        super("La clase \'"+claseQueImplementaMal.getNombre()+"\' implementa, pero no correctamente el metodo \'" +metodoMalImplementado.getLexeme()+
                "\' de la interface \'"+metodoMalImplementado.getClaseDefinido()+"\'", metodoMalImplementado.getToken());
        this.claseQueImplementaMal = claseQueImplementaMal;
        this.metodoMalImplementado = metodoMalImplementado;
    }
}
