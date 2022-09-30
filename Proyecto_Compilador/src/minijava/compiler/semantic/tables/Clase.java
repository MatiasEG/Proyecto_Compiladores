package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;

import java.util.ArrayList;
import java.util.HashMap;

public class Clase extends ClaseInterface{

    private ArrayList<Token> implement;
    private ArrayList<Atributo> atributos;
    private HashMap<String, Atributo> atributosHashMap;

    public Clase(Token claseToken){
        extendsFrom = new ArrayList<>();
        implement = new ArrayList<>();
        atributos = new ArrayList<>();
        atributosHashMap = new HashMap<>();
        metodos = new ArrayList<>();
        metodoHashMap = new HashMap<>();
        this.claseOrinterfaceToken= claseToken;
    }

    public void setImplement(ArrayList<Token> implement){
        if(!implement.equals("")){
            this.implement = implement;
        }
    }

    public ArrayList<Token> getClasesImplementadas(){ return implement; }

    public ArrayList<Atributo> getAtributos(){ return atributos; }

    public HashMap<String, Atributo> getAtributosHashMap(){ return atributosHashMap; }

    public void addAtribute(Atributo atributo){
        atributos.add(atributo);
        atributosHashMap.put(atributo.getNombre(), atributo);
    }

    public boolean alreadyHaveAtribute(Atributo atributo){
        for(Atributo a: atributos){
            if(a.getNombre().equals(atributo.getNombre())) return true;
        }
        return false;
    }
}
