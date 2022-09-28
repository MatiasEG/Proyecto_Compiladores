package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;

import java.util.ArrayList;
import java.util.HashMap;

public class Interface_ extends ClaseInterface {

    public Interface_(Token interfaceToken){
        extendsFrom = new ArrayList<>();
        metodos = new ArrayList<>();
        metodoHashMap = new HashMap<>();
        this.claseOrinterfaceToken = interfaceToken;
    }

}
