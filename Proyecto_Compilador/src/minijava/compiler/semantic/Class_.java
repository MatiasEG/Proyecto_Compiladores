package minijava.compiler.semantic;

import java.util.ArrayList;

public class Class_ extends ClassInterface {

    private ArrayList<String> implement;

    public Class_(String name){
        extendsFrom = new ArrayList<>();
        implement = new ArrayList<>();
        this.name = name;
    }

    public void setImplement(ArrayList<String> implement){
        if(!implement.equals("")){
            this.implement = implement;
        }
    }

    public ArrayList<String> getClasesImplementadas(){ return implement; }
}
