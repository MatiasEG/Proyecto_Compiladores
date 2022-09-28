package minijava.compiler.semantic;

import minijava.compiler.exception.semantic.*;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.*;

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
        if(alreadyExist(claseActual.getNombre()) == null){
            clases.put(claseActual.getNombre(), (Clase) claseActual);
        }else{
            throw new SemanticExceptionClassInterfaceNameDuplicated("Nombre de clase repetido.", claseActual);
        }
    }

    public void insertarInterface() throws SemanticExceptionClassInterfaceNameDuplicated {
        if(alreadyExist(claseActual.getNombre()) == null){
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

    public ClaseInterface alreadyExist(String classORinterface){
        if(clases.containsKey(classORinterface)) return clases.get(classORinterface);
        if(interfaces.containsKey(classORinterface)) return interfaces.get(classORinterface);
        if(classORinterface.equals("Object")) return new Clase(null);

        return null;
    }

    public boolean check() throws SemanticException {
        Clase object = new Clase(new Token("Object", "Object", 0));
        object.setMetodos(new ArrayList<>());
        object.setAtributos(new ArrayList<>());
        object.setImplement(new ArrayList<>());
        clases.put(object.getNombre(), object);

        int main = 0;
        for(Map.Entry<String, Clase> entry: clases.entrySet()){
            for(String s: entry.getValue().getClasesHerencia()){
                //todo revisar la herencia me da problemas con el main
                checkSignaturaMetodosRedefinidosPorHerencia(entry.getValue(), alreadyExist(s));
                if(!clases.containsKey(s)) throw new SemanticExceptionExtendedClassDoesNotExist(entry.getValue(), s);
                if(herenciaCircular(entry.getValue(), s)) throw new SemanticExceptionCircleExtend(entry.getValue(), clases.get(s));
                main += checkMainYConstructor(main, entry.getValue(), entry.getValue().getMetodos());
            }

            for(String s: entry.getValue().getClasesImplementadas()){
                if(!interfaces.containsKey(s)) throw new SemanticExceptionImplementedClassDoesNotExist(entry.getValue(), s);

            }
        }

        if(main == 0) throw new SemanticExceptionMainDoesNotExist();

        for(Map.Entry<String, Interface_> entry: interfaces.entrySet()){
            for(String s: entry.getValue().getClasesHerencia()){
                if(!interfaces.containsKey(s)) throw new SemanticExceptionExtendedClassDoesNotExist(entry.getValue(), s);
            }
        }

        return true;
    }

    private int checkMainYConstructor(int main, Clase clase, ArrayList<Metodo> metodos) throws SemanticException {
        boolean constructorBasico = false;

        for(Metodo m: metodos){
            if(m.getNombre().equals("main") && main == 0 && m.getTipo().getLexemeType().equals("void") && m.isStatic()){
                main++;
            }else if(m.getNombre().equals("main") && main == 1 && m.getTipo().getLexemeType().equals("void") && m.isStatic()){
                throw new SemanticExceptionDuplicatedMain(m, clase);
            }else if(m.getNombre().equals("main")){
                throw new SemanticExceptionWrongDefinedMain(m);
            }else if(m.getNombre().equals(m.getTipo().getLexemeType()) && m.getParametros().size() == 0){
                constructorBasico = true;
            }
        }

        if(!constructorBasico){
            Metodo constructorBase = new Metodo();
            constructorBase.setMetodoToken(new Token("idClass", clase.getNombre(), 0));
            constructorBase.setClaseDefinido(clase.getNombre());
            Tipo tipoConstructor = new Tipo(new Token("idClass", clase.getNombre(), 0));
            constructorBase.setTipo(tipoConstructor);
            clase.addMetodo(constructorBase);
        }

        return main;
    }

    public void checkSignaturaMetodosRedefinidosPorHerencia(ClaseInterface descendiente, ClaseInterface padre) throws SemanticException {
        for(Metodo m: padre.getMetodos()){
            if(!descendiente.getMetodoHashMap().containsKey(m.getMapKey())) {
//                Metodo metDec = new Metodo();
//                metDec.setMetodoToken(new Token(m.getMetodoToken().getToken(), m.getMetodoToken().getLexeme(), 0));
//                metDec.setStatic(m.isStatic());
//                metDec.setTipo(m.getTipo());
//                metDec.setClaseDefinido(descendiente.getNombre());
                descendiente.addMetodo(padre.getMetodoHashMap().get(m.getMapKey()));
            }else if(descendiente.getMetodoHashMap().containsKey(m.getMapKey())){
                if(!m.equals(descendiente.getMetodoHashMap().get(m.getMapKey()))) throw new SemanticExceptionMethodNotRedefined(m, descendiente);
            }
        }
    }

    private boolean herenciaCircular(Clase clase, String nombreHerencia){
        return clases.get(nombreHerencia).getClasesHerencia().contains(clase.getNombre());
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
