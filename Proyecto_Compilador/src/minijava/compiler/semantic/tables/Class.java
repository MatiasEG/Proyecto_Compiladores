package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;

import java.util.ArrayList;
import java.util.HashMap;

public class Class extends ClassOrInterface {

    private ArrayList<Token> implement;
    private ArrayList<Atributo> atributos;
    private HashMap<String, Atributo> atributosHashMap;

    public Class(Token claseToken){
        extendsFrom = new ArrayList<>();
        implement = new ArrayList<>();
        atributos = new ArrayList<>();
        atributosHashMap = new HashMap<>();
        methods = new ArrayList<>();
        metodoHashMap = new HashMap<>();
        this.claseOrinterfaceToken= claseToken;
    }

    public void setListOfImplements(ArrayList<Token> implement){
        if(!implement.equals("")){
            this.implement = implement;
        }
    }

    public ArrayList<Token> getImplementedClasses(){ return implement; }

    public ArrayList<Atributo> getAttributes(){ return atributos; }

    public HashMap<String, Atributo> getHashMapAtributes(){ return atributosHashMap; }

    public void addAttribute(Atributo atributo){
        atributos.add(atributo);
        atributosHashMap.put(atributo.getVarName(), atributo);
    }

    public boolean alreadyHaveAttribute(Atributo atributo){
        for(Atributo a: atributos){
            if(a.getVarName().equals(atributo.getVarName())) return true;
        }
        return false;
    }
}
