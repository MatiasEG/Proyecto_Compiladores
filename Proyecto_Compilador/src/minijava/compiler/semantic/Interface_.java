package minijava.compiler.semantic;

import java.util.ArrayList;

public class Interface_ extends ClassInterface{

    private ArrayList<String> implement;

    public Interface_(String name){
        extendsFrom = new ArrayList<>();
        implement = new ArrayList<>();
        this.name = name;
    }

}
