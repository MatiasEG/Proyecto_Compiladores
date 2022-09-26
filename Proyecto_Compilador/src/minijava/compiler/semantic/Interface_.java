package minijava.compiler.semantic;

import java.util.ArrayList;

public class Interface_ extends ClaseInterface {

    private ArrayList<String> implement;

    public Interface_(String nombre, int lineaDeclaracion){
        extendsFrom = new ArrayList<>();
        implement = new ArrayList<>();
        metodos = new ArrayList<>();
        this.nombre = nombre;
        this.lineaDeclaracion = lineaDeclaracion;
    }

}
