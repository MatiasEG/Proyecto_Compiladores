package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ClassOrInterface {

    protected ArrayList<Token> extendsFrom;
    protected HashMap<String, Method> metodoHashMap;
    protected ArrayList<Method> methods;
    protected HashMap<String, Method> metodosSinSobrecargaMap;
    protected Token claseOrinterfaceToken;

    public void setListOfExtends(ArrayList<Token> extendsFrom){
        this.extendsFrom =  extendsFrom;
    }

    public void addMetodo(Method method){
        methods.add(method);
        metodoHashMap.put(method.getMapKey(), method);
        metodosSinSobrecargaMap.put(method.getMethodName(), method);
    }

    public String getNombre() { return claseOrinterfaceToken.getLexeme(); }

    public Token getClassOrinterfaceToken() { return claseOrinterfaceToken; }

    public ArrayList<Token> getExtendedClasses(){ return extendsFrom; }

    public ArrayList<Method> getMethods(){ return methods; }

    public HashMap<String, Method> getHashMapMethods(){ return metodoHashMap; }

    public HashMap<String, Method> getHashMapMethodsWithoutOverloaded(){ return metodosSinSobrecargaMap; }

    public boolean sameMethodOverloaded(Method method){
        for(Method m: methods){
            if(method.getMapKey().equals(m.getMapKey())) return true;
        }
        return false;
    }
}
