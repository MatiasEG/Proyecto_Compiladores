package minijava.compiler.semantic;

import java.util.ArrayList;

public class Interface_ extends ClaseInterface {

    public Interface_(String nombre, int lineaDeclaracion){
        extendsFrom = new ArrayList<>();
        metodos = new ArrayList<>();
        this.nombre = nombre;
        this.lineaDeclaracion = lineaDeclaracion;
    }

}
