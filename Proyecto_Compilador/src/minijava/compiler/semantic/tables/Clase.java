package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;

import java.util.ArrayList;
import java.util.HashMap;

public class Clase extends ClaseInterface {

    private ArrayList<String> implement;
    private ArrayList<Atributo> atributos;

    public Clase(Token claseToken){
        extendsFrom = new ArrayList<>();
        implement = new ArrayList<>();
        atributos = new ArrayList<>();
        metodos = new ArrayList<>();
        metodoHashMap = new HashMap<>();
        this.claseOrinterfaceToken= claseToken;
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
