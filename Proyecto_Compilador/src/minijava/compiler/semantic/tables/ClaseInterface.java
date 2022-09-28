package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ClaseInterface {

    protected ArrayList<String> extendsFrom;
    protected HashMap<String, Metodo> metodoHashMap;
    protected ArrayList<Metodo> metodos;
    protected Token claseOrinterfaceToken;

    public void setExtendsFrom(ArrayList<String> extendsFrom){
        this.extendsFrom =  extendsFrom;
    }

    public void setMetodos(ArrayList<Metodo> metodos){
        this.metodos = metodos;
        for(Metodo m: metodos){
            metodoHashMap.put(m.getMapKey(), m);
        }
    }

    public Token getClaseOrinterfaceToken() { return claseOrinterfaceToken; }

    public void setClaseOrinterfaceToken(Token claseOrinterfaceToken) { this.claseOrinterfaceToken = claseOrinterfaceToken; }

    public void addMetodo(Metodo metodo){
        metodos.add(metodo);
        metodoHashMap.put(metodo.getMapKey(), metodo);
    }

    public ArrayList<String> getClasesHerencia(){ return extendsFrom; }

    public ArrayList<Metodo> getMetodos(){ return metodos; }

    public HashMap<String,Metodo> getMetodoHashMap(){ return metodoHashMap; }

    public boolean methodRedefinition(Metodo metodo){
        for(Metodo m: metodos){
            if(metodo.getMapKey().equals(m.getMapKey())) return false;
        }
        return true;
    }

    public String getNombre() { return claseOrinterfaceToken.getLexeme(); }


    public int getLineaDeclaracion() { return claseOrinterfaceToken.getLineNumber(); }

    public void setMetodoHashMap(HashMap<String, Metodo> metodoHashMap) { this.metodoHashMap = metodoHashMap; }

    public void addMetodoHashMap(Metodo m){ metodoHashMap.put(m.getMapKey(), m); }
}
