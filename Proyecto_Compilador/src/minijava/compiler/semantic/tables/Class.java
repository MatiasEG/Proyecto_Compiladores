package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.variable.Attribute;

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
        metodosSinSobrecargaMap = new HashMap<>();
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

    public String subtipo(SymbolTable st, ClassOrInterface claseInterfazSubtipo){
        String nombre = null;
        for(Token padre: extendsFrom){
            if(padre.getLexeme().equals(claseInterfazSubtipo.getNombre()))
                return padre.getLexeme();
            nombre = st.alreadyExist(padre.getLexeme()).subtipo(st, claseInterfazSubtipo);
            if(nombre != null)
                return nombre;
        }
        if(nombre == null){
            for(Token interfazPadre: implement){
                if(interfazPadre.getLexeme().equals(claseInterfazSubtipo.getNombre()))
                    return interfazPadre.getLexeme();
                nombre = st.alreadyExist(interfazPadre.getLexeme()).subtipo(st, claseInterfazSubtipo);
                if(nombre != null)
                    return nombre;
            }
        }
        return null;
    }

    public boolean alreadyHaveAttribute(Attribute attribute){
        for(Attribute a: attributes){
            if(a.getVarName().equals(attribute.getVarName())) return true;
        }
        return false;
    }
}
