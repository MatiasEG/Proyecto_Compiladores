package minijava.compiler.semantic;

import java.util.ArrayList;

public abstract class ClaseInterface {

    protected String nombre;
    protected ArrayList<String> extendsFrom;
    protected ArrayList<Metodo> metodos;
    protected int lineaDeclaracion;

    public void setExtendsFrom(ArrayList<String> extendsFrom){
        this.extendsFrom =  extendsFrom;
    }

    public void setMetodos(ArrayList<Metodo> metodos){ this.metodos = metodos; }

    public void addMetodo(Metodo metodo){ metodos.add(metodo); }

    public void setLineaDeclaracion(int lineaDeclaracion) { this.lineaDeclaracion = lineaDeclaracion; }

    public String getNombre(){ return nombre; }

    public ArrayList<String> getClasesHerencia(){ return extendsFrom; }

    public ArrayList<Metodo> getMetodos(){ return metodos; }

    public int getLineaDeclaracion() { return lineaDeclaracion; }

    public boolean methodRedefinition(Metodo metodo){
        for(Metodo m: metodos){
            if(metodo.equals(m)) return false;
        }
        return true;
    }

}
