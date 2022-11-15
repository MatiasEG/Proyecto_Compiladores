package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Interface_ extends ClassOrInterface {

    private boolean offsetChequeados;

    public boolean isOffsetChequeados(){ return offsetChequeados; }

    public Interface_(Token interfaceToken){
        extendsFrom = new ArrayList<>();
        methods = new ArrayList<>();
        metodosSinSobrecargaMap = new HashMap<>();
        metodoHashMap = new HashMap<>();
        this.claseOrinterfaceToken = interfaceToken;
        metodosDinamicos = new HashMap<>();
        metodosPorOffset = new HashMap<>();
        metodosHeredadosPorOffset = new HashMap<>();
        mapeoDeMetodosFinal = new HashMap<>();
        metodosPorOffsetCompleto = new HashMap<>();
    }


    public void addMetodoHerencia(Method metodoHeredado){
        metodosHeredadosPorOffset.put(metodoHeredado.getOffsetMetodo(), metodoHeredado);
    }

    public String subtipo(SymbolTable st, ClassOrInterface claseInterfazSubtipo){
        String nombre = null;
        for(Token padre: extendsFrom){
            if(padre.getLexeme().equals(claseInterfazSubtipo.getNombre())) return padre.getLexeme();
            nombre = st.alreadyExist(padre.getLexeme()).subtipo(st, claseInterfazSubtipo);
        }
        return nombre;
    }

    public void generarOffsetInterfaces(SymbolTable st){
        this.generarMapeoMetodosPorOffset();
        Method mAux;
//        offsetMetodo =

        if(!offsetChequeados){
            for(Token padre: extendsFrom){
                if(!st.getInterface(padre.getLexeme()).isOffsetChequeados()){
                    st.getInterface(padre.getLexeme()).setOffsetMetodo(st.getMaxOffsetInterface());
                    st.getInterface(padre.getLexeme()).generarOffsetInterfaces(st);
                }
    //            offsetMetodo = st.getInterface(padre.getLexeme()).getOffsetMetodo();
            }

//            for(Map.Entry<Integer,Method> entry: metodosPorOffsetCompleto.entrySet()){
            for(Map.Entry<String,Method> entry: metodosSinSobrecargaMap.entrySet()){
                mAux = entry.getValue();
                if(!mAux.isStatic()){
                    if(mAux.getClassDeclaredMethod().equals(getNombre())){
                        mAux.setOffsetMetodo(st.getMaxOffsetInterface());
                        mapeoDeMetodosFinal.put(mAux.getOffsetMetodo(), mAux);
                    }
//                    else{
//                        mapeoDeMetodosFinal.put(mAux.getOffsetMetodo(), mAux);
//                    }
                }
            }
            offsetChequeados = true;
        }
    }

    public Method metodoPertenece(Method m){
        for(Map.Entry<Integer,Method> entry: mapeoDeMetodosFinal.entrySet()){
            if(entry.getValue().getMethodName().equals(m.getMethodName())){
                return entry.getValue();
            }
        }
        for(Method met: methods){
            if(met.getMethodName().equals(m.getMethodName()))
                return met;
        }
        return null;
    }
}
