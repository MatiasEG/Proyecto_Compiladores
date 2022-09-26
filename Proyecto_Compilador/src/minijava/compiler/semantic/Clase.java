package minijava.compiler.semantic;

import java.util.ArrayList;

public class Clase extends ClaseInterface {

    private ArrayList<String> implement;
    private ArrayList<Atributo> atributos;

    public Clase(String nombre, int lineaDeclaracion){
        extendsFrom = new ArrayList<>();
        implement = new ArrayList<>();
        atributos = new ArrayList<>();
        metodos = new ArrayList<>();
        this.nombre = nombre;
        this.lineaDeclaracion = lineaDeclaracion;
    }

    public void setImplement(ArrayList<String> implement){
        if(!implement.equals("")){
            this.implement = implement;
        }
    }

    public ArrayList<String> getClasesImplementadas(){ return implement; }

    public void setAtributos(ArrayList<Atributo> atributos){ this.atributos = atributos; }

    public ArrayList<Atributo> getAtributos(){ return atributos; }

    public void addAtribute(Atributo atributo){ atributos.add(atributo); }

    public boolean alreadyHaveAtribute(Atributo atributo){
        for(Atributo a: atributos){
            if(a.getNombre().equals(atributo.getNombre())) return true;
        }
        return false;
    }
}
