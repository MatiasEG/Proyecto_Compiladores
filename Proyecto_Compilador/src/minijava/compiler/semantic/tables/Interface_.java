package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;

import java.util.ArrayList;
import java.util.HashMap;

public class Interface_ extends ClassOrInterface {

    public Interface_(Token interfaceToken){
        extendsFrom = new ArrayList<>();
        methods = new ArrayList<>();
        metodosSinSobrecargaMap = new HashMap<>();
        metodoHashMap = new HashMap<>();
        this.claseOrinterfaceToken = interfaceToken;
    }

}
