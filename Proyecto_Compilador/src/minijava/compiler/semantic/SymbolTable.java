package minijava.compiler.semantic;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {

    private ClassInterface claseActual;
    private HashMap<String, Class_> clases;
    private HashMap<String, Interface_> interfaces;

    public SymbolTable(){
        clases = new HashMap<>();
        interfaces = new HashMap<>();
    }

    public void setClaseActual(Class_ class_){
        claseActual = class_;
    }

    public void setActualInterface(Interface_ interface_){
        claseActual = interface_;
    }

    public void actualClassInterfaceExtendsFrom(ArrayList<String> extendsFrom){
        claseActual.setExtendsFrom(extendsFrom);
    }

    public void actualClassImplements(ArrayList<String> implement){
        ((Class_) claseActual).setImplement(implement);
    }

    public void insertarClase(){
        clases.put(claseActual.getName(), (Class_) claseActual);
    }

    public void insertarInterface(){
        interfaces.put(claseActual.getName(), (Interface_) claseActual);
    }

    public void imprimirTablas(){
        System.out.println("\n--------------------------\n\n");
        System.out.println("Clases");
        clases.forEach((nombre,tablaClase) -> {
            System.out.println("Nombre: "+nombre);
            ArrayList<String> aux = tablaClase.getClasesHerencia();
            for(String s: aux){
                System.out.println(nombre+" hereda de: "+s);
            }
            aux = tablaClase.getClasesImplementadas();
            for(String s: aux){
                System.out.println(nombre+" implementa la clase: "+s);
            }
        });

        System.out.println("\n--------------------------\n\n");
        System.out.println("Interfaces");
        interfaces.forEach((nombre,tablaInterface) -> {
            System.out.println("Nombre: "+nombre);
            ArrayList<String> aux = tablaInterface.getClasesHerencia();
            for(String s: aux){
                System.out.println(nombre+" hereda de: "+s);
            }
        });
    }
}
