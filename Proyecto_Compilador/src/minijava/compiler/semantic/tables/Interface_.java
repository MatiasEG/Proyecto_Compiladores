package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class Interface_ extends ClassOrInterface {

    public Interface_(Token interfaceToken){
        extendsFrom = new ArrayList<>();
        methods = new ArrayList<>();
        metodosSinSobrecargaMap = new HashMap<>();
        metodoHashMap = new HashMap<>();
        this.claseOrinterfaceToken = interfaceToken;
        metodosDinamicos = new HashMap<>();
        metodosPorOffset = new HashMap<>();
    }


    public String subtipo(SymbolTable st, ClassOrInterface claseInterfazSubtipo){
        String nombre = null;
        for(Token padre: extendsFrom){
            if(padre.getLexeme().equals(claseInterfazSubtipo.getNombre())) return padre.getLexeme();
            nombre = st.alreadyExist(padre.getLexeme()).subtipo(st, claseInterfazSubtipo);
        }
        return nombre;
    }
}
