package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;

import java.util.ArrayList;
import java.util.HashMap;

public class Class extends ClassOrInterface {

    private ArrayList<Token> implement;
    private ArrayList<Attribute> attributes;
    private HashMap<String, Attribute> atributosHashMap;

    public Class(Token claseToken){
        extendsFrom = new ArrayList<>();
        implement = new ArrayList<>();
        attributes = new ArrayList<>();
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

    public ArrayList<Attribute> getAttributes(){ return attributes; }

    public HashMap<String, Attribute> getHashMapAtributes(){ return atributosHashMap; }

    public void addAttribute(Attribute attribute){
        attributes.add(attribute);
        atributosHashMap.put(attribute.getVarName(), attribute);
    }

    public boolean alreadyHaveAttribute(Attribute attribute){
        for(Attribute a: attributes){
            if(a.getVarName().equals(attribute.getVarName())) return true;
        }
        return false;
    }
}
