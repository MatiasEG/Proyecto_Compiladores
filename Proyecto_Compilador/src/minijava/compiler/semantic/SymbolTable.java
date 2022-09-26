package minijava.compiler.semantic;

import minijava.compiler.exception.semantic.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private ClaseInterface claseActual;
    private HashMap<String, Clase> clases;
    private HashMap<String, Interface_> interfaces;

    public SymbolTable(){
        clases = new HashMap<>();
        interfaces = new HashMap<>();
    }

    public void setClaseActual(Clase clase_){
        claseActual = clase_;
    }

    public void setActualInterface(Interface_ interface_){
        claseActual = interface_;
    }

    public void actualClassInterfaceExtendsFrom(ArrayList<String> extendsFrom){
        claseActual.setExtendsFrom(extendsFrom);
    }

    public String getActualClassInterfaceName(){ return claseActual.getNombre(); }

    public void actualClassImplements(ArrayList<String> implement){
        ((Clase) claseActual).setImplement(implement);
    }

    public void insertarClase() throws SemanticExceptionClassInterfaceNameDuplicated {
        if(!alreadyExist(claseActual.getNombre())){
            clases.put(claseActual.getNombre(), (Clase) claseActual);
        }else{
            throw new SemanticExceptionClassInterfaceNameDuplicated("Nombre de clase repetido.", claseActual);
        }
    }

    public void insertarInterface() throws SemanticExceptionClassInterfaceNameDuplicated {
        if(!alreadyExist(claseActual.getNombre())){
            interfaces.put(claseActual.getNombre(), (Interface_) claseActual);
        }else{
            throw new SemanticExceptionClassInterfaceNameDuplicated("Nombre de interfaz repetido.", claseActual);
        }

    }

    public void actualClassAddAtribute(Atributo atributo) throws SemanticException {
        Clase clase = (Clase) claseActual;

        if(!clase.alreadyHaveAtribute(atributo)){
            clase.addAtribute(atributo);
        }else{
            throw new SemanticExceptionDuplicatedAtribute(atributo);
        }
    }

    public void actualClassInterfaceAddMethod(Metodo metodo) throws SemanticExceptionDuplicatedMethod {
        if(claseActual.methodRedefinition(metodo)){
            claseActual.addMetodo(metodo);
        }else{
            throw new SemanticExceptionDuplicatedMethod(metodo);
        }
    }

    public boolean alreadyExist(String classORinterface){
        if(clases.containsKey(classORinterface)) return true;
        if(interfaces.containsKey(classORinterface)) return true;
        if(classORinterface.equals("Object")) return true;

        return false;
    }

    public boolean check() throws SemanticExceptionExtendedClassDoesNotExist, SemanticExceptionImplementedClassDoesNotExist {
        for(Map.Entry<String, Clase> entry: clases.entrySet()){
            for(String s: entry.getValue().getClasesHerencia()){
                if(!alreadyExist(s)) throw new SemanticExceptionExtendedClassDoesNotExist(entry.getValue(), s);
            }
            for(String s: entry.getValue().getClasesImplementadas()){
                if(!alreadyExist(s)) throw new SemanticExceptionImplementedClassDoesNotExist(entry.getValue(), s);
            }
        }

        for(Map.Entry<String, Interface_> entry: interfaces.entrySet()){
            for(String s: entry.getValue().getClasesHerencia()){
                if(!alreadyExist(s)) throw new SemanticExceptionExtendedClassDoesNotExist(entry.getValue(), s);
            }
        }

        return true;
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

            System.out.println("Atributos de "+nombre);
            ArrayList<Atributo> atributos = tablaClase.getAtributos();
            for(Atributo a: atributos){
                System.out.println("Atributo: "+a.getNombre()+"---------------------------------");
                System.out.println(" > "+a.getNombre()+" visibilidad: "+a.isVisible());
                System.out.println(" > "+a.getNombre()+" tipo: "+a.getTipo().getLexemeType());
            }

            System.out.println("Metodos de: "+nombre);
            ArrayList<Metodo> metodos = tablaClase.getMetodos();
            ArrayList<Parametro> parametros;
            for(Metodo m: metodos){
                System.out.println("Nombre metodo: "+m.getNombre());
                System.out.println(" > "+m.getNombre()+" es estatico: "+m.isStatic());
                System.out.println(" > "+m.getNombre()+" tipo: "+m.getTipo().getLexemeType());

                parametros = m.getParametros();
                for(Parametro p: parametros){
                    System.out.println("      Parametro nombre: "+p.getNombre());
                    System.out.println("      Parametro tipo: "+p.getTipo().getLexemeType());
                    System.out.println("      Parametro pertenece a: "+p.getMetodo().getNombre());
                }
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

            System.out.println("Metodos de: "+nombre);
            ArrayList<Metodo> metodos = tablaInterface.getMetodos();
            ArrayList<Parametro> parametros;
            for(Metodo m: metodos){
                System.out.println("Nombre metodo: "+m.getNombre());
                System.out.println(" > "+m.getNombre()+" es estatico: "+m.isStatic());
                System.out.println(" > "+m.getNombre()+" tipo: "+m.getTipo().getLexemeType());

                parametros = m.getParametros();
                for(Parametro p: parametros){
                    System.out.println("      Parametro nombre: "+p.getNombre());
                    System.out.println("      Parametro tipo: "+p.getTipo().getLexemeType());
                    System.out.println("      Parametro pertenece a: "+p.getMetodo().getNombre());
                }
            }
        });
    }
}
