package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ClassOrInterface {

    protected ArrayList<Token> extendsFrom;
    protected HashMap<String, Method> metodoHashMap;
    protected ArrayList<Method> methods;
    protected HashMap<String, Method> metodosSinSobrecargaMap;
    protected Token claseOrinterfaceToken;
    protected HashMap<String, Method> metodosDinamicos;
    protected HashMap<Integer, Method> metodosHeredadosPorOffset;
    protected HashMap<Integer, Method> metodosPorOffset;
    protected int offsetMetodo;
    protected HashMap<Integer,Method> metodosPorOffsetCompleto;

    public void setListOfExtends(ArrayList<Token> extendsFrom){
        this.extendsFrom =  extendsFrom;
    }

    public void addMetodo(Method method){
        methods.add(method);
        metodoHashMap.put(method.getMapKey(), method);

        if(method.getMethodName().equals("m1A")){
            System.out.println("PASANDO POR ACA");
        }

        if(!metodosSinSobrecargaMap.containsKey(method.getMethodName())){
            metodosSinSobrecargaMap.put(method.getMethodName(), method);
            if(!method.isStatic()){
                metodosDinamicos.put(method.getMethodName(), method);
                if(method.getClassDeclaredMethod().equals(this.getNombre())){
                    method.setOffsetMetodo(offsetMetodo);
                    metodosPorOffset.put(offsetMetodo, method);
                    offsetMetodo++;
                }else{
                    metodosHeredadosPorOffset.put(method.getOffsetMetodo(), method);
                }
            }
        }
    }

//    public void addMetodoHerencia(Method method){
//        methods.add(method);
//        metodoHashMap.put(method.getMapKey(), method);
//
//        if(!metodosSinSobrecargaMap.containsKey(method.getMethodName())){
//            metodosSinSobrecargaMap.put(method.getMethodName(), method);
//            if(!method.isStatic()){
//                metodosDinamicos.put(method.getMethodName(), method);
//                if(method.getClassDeclaredMethod().equals(this.getNombre())){
//                    metodosPorOffset.put(method.getOffsetMetodo(), method);
//                }else{
//                    metodosHeredadosPorOffset.put(method.getOffsetMetodo(), method);
//                }
//            }
//        }
//    }

    public HashMap<Integer,Method> getMetodosHeredadosPorOffset(){ return metodosHeredadosPorOffset; }

    public HashMap<Integer,Method> getMetodosPropios(){ return metodosPorOffset; }

    public HashMap<Integer, Method> getMetodosPorOffset(){ return metodosPorOffset; }

    public String getNombre() { return claseOrinterfaceToken.getLexeme(); }

    public Token getClassOrinterfaceToken() { return claseOrinterfaceToken; }

    public ArrayList<Token> getExtendedClasses(){ return extendsFrom; }

    public ArrayList<Method> getMethods(){ return methods; }

    public HashMap<String, Method> getHashMapMethods(){ return metodoHashMap; }

    public HashMap<String, Method> getHashMapMethodsWithoutOverloaded(){ return metodosSinSobrecargaMap; }

    public HashMap<String, Method> getMetodosDinamicos(){ return metodosDinamicos; }

    public abstract String subtipo(SymbolTable st, ClassOrInterface claseInterfazSubtipo);

    public boolean sameMethodOverloaded(Method method){
        for(Method m: methods){
            if(method.getMapKey().equals(m.getMapKey())) return true;
        }
        return false;
    }

    public void setOffsetMetodo(int herenciaOffset){ this.offsetMetodo = herenciaOffset; }

    public int getOffsetMetodo(){ return offsetMetodo; }
}
