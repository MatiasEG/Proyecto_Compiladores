package minijava.compiler.semantic;

import minijava.compiler.exception.semantic.*;
import minijava.compiler.exception.semantic.classinterface.*;
import minijava.compiler.exception.semantic.extend.SemanticExceptionCircleExtend;
import minijava.compiler.exception.semantic.method.*;
import minijava.compiler.exception.semantic.duplicated.SemanticExceptionDuplicatedAtribute;
import minijava.compiler.exception.semantic.duplicated.SemanticExceptionDuplicatedMain;
import minijava.compiler.exception.semantic.duplicated.SemanticExceptionDuplicatedMethod;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.*;
import minijava.compiler.semantic.tables.Class;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private ClassOrInterface claseActual;
    private HashMap<String, Class> clases;
    private HashMap<String, Interface_> interfaces;

    public SymbolTable(){
        clases = new HashMap<>();
        interfaces = new HashMap<>();
    }

    public void createConcreteClasses() throws SemanticException {
        Class object = DefaultClasesManager.createObjectClass();
        clases.put(object.getNombre(), object);

        Class string = DefaultClasesManager.createStringClass();
        clases.put(string.getNombre(), string);

        Class system = DefaultClasesManager.createSystemClass();
        clases.put(system.getNombre(), system);
    }

    public void setActualClassOrInterface(ClassOrInterface classOrInterface){
        claseActual = classOrInterface;
    }

    public void setActualClassInterfaceListOfExtends(ArrayList<Token> extendsFrom){ claseActual.setListOfExtends(extendsFrom); }

    public void setActualClassListOfImplements(ArrayList<Token> implement){ ((Class) claseActual).setListOfImplements(implement); }

    public String getActualClassInterfaceName(){ return claseActual.getNombre(); }

    public Token getObjectClassToken(){ return clases.get("Object").getClassOrinterfaceToken(); }

    public void insertarClase() throws SemanticException{
        if(alreadyExist(claseActual.getNombre()) == null){
            clases.put(claseActual.getNombre(), (Class) claseActual);
        }else{
            throw new SemanticExceptionClassInterfaceNameDuplicated("Nombre de clase repetido.", claseActual);
        }
    }

    public void insertarInterface() throws SemanticException{
        if(alreadyExist(claseActual.getNombre()) == null){
            interfaces.put(claseActual.getNombre(), (Interface_) claseActual);
        }else{
            throw new SemanticExceptionClassInterfaceNameDuplicated("Nombre de interfaz repetido.", claseActual);
        }

    }

    public void actualClassAddAtribute(Atributo atributo) throws SemanticException{
        Class clase = (Class) claseActual;

        if(!clase.alreadyHaveAttribute(atributo)){
            clase.addAttribute(atributo);
        }else{
            throw new SemanticExceptionDuplicatedAtribute(atributo);
        }
    }

    public void actualClassInterfaceAddMethod(Method method) throws SemanticException{
        if(!claseActual.sameMethodOverloaded(method)){
            claseActual.addMetodo(method);
        }else{
            throw new SemanticExceptionDuplicatedMethod(method);
        }
    }

    public void actualClassInterfaceAddConstructor(Method method) throws SemanticException{
        if(!claseActual.sameMethodOverloaded(method)){
            if(!claseActual.getNombre().equals(method.getMethodName())) throw new SemanticExceptionWrongDefinedConstructor(method);
            claseActual.addMetodo(method);
        }else{
            throw new SemanticExceptionDuplicatedMethod(method);
        }
    }

    public ClassOrInterface alreadyExist(String classORinterface){
        if(clases.containsKey(classORinterface)) return clases.get(classORinterface);
        if(interfaces.containsKey(classORinterface)) return interfaces.get(classORinterface);

        return null;
    }






    public void check() throws SemanticException{
        int main = 0;
        String s;
        for(Map.Entry<String, Class> entry: clases.entrySet()){

            for(Token t: entry.getValue().getExtendedClasses()){
                s = t.getLexeme();
                if(!clases.containsKey(s)) throw new SemanticExceptionExtendedClassDoesNotExist(entry.getValue(), t);
                main += checkMainYConstructor(main, entry.getValue(), entry.getValue().getMethods());
                checkSignaturaMetodosRedefinidosPorHerencia(entry.getValue(), alreadyExist(s));
            }

            for(Token t: entry.getValue().getImplementedClasses()){
                s = t.getLexeme();
                if(clases.containsKey(s)) throw new SemanticExceptionInterfaceExtendsClase(interfaces.get(entry.getValue()));
                if(!interfaces.containsKey(s)) throw new SemanticExceptionImplementedClassDoesNotExist(entry.getValue(), t);
                checkMetodosImplementados(entry.getValue(), interfaces.get(s));
            }

        }

        if(main == 0) throw new SemanticExceptionMethodMainDoesNotExist();


        for(Map.Entry<String, Interface_> entry: interfaces.entrySet()){
            for(Token t: entry.getValue().getExtendedClasses()){
                s = t.getLexeme();
                if(!interfaces.containsKey(s)) throw new SemanticExceptionExtendedInterfaceDoesNotExist(entry.getValue(), t);
                checkSignaturaMetodosRedefinidosPorHerencia(entry.getValue(), alreadyExist(s));
            }
        }

    }

    private int checkMainYConstructor(int main, Class clase, ArrayList<Method> methods) throws SemanticException{
        boolean constructorBasico = false;

        for(Method m: methods){
            if(m.getMethodName().equals("main") && main == 0 && m.getMethodType().getLexemeType().equals("void") && m.isStatic() && m.getParameters().size()==0){
                main++;
            }else if(m.getMethodName().equals("main") && main == 1 && m.getMethodType().getLexemeType().equals("void") && m.isStatic()){
                throw new SemanticExceptionDuplicatedMain(m, clase);
            }else if(m.getMethodName().equals("main")){
                throw new SemanticExceptionMethodMainWrongDefined(m);
            }else if(m.getMethodName().equals(m.getMethodType().getLexemeType()) && m.getParameters().size() == 0){
                constructorBasico = true;
            }
        }

        if(!constructorBasico){
            Method constructorBase = new Method();
            constructorBase.setMethodToken(new Token("idClass", clase.getNombre(), 0));
            constructorBase.setClassDeclaredMethod(clase.getNombre());
            Type typeConstructor = new Type(new Token("idClass", clase.getNombre(), 0));
            constructorBase.setMethodType(typeConstructor);
            clase.addMetodo(constructorBase);
        }

        return main;
    }

    private boolean herenciaCircular(ClassOrInterface classOrInterfaceDesc, ClassOrInterface classOrInterfacePadre) {
        boolean resultado = false;
        for(Token herenciaDelPadre: classOrInterfacePadre.getExtendedClasses()){
            if(herenciaDelPadre.getLexeme().equals(classOrInterfaceDesc.getNombre())) resultado = true;
            if(!resultado) resultado = herenciaCircular(classOrInterfaceDesc, clases.get(alreadyExist(herenciaDelPadre.getLexeme()).getNombre()));
        }
        return resultado;
    }

    private void checkMetodosImplementados(Class claseImplementa, Interface_ interface_) throws SemanticException{
        for(Method m: interface_.getMethods()){
            if(claseImplementa.getHashMapMethods().containsKey(m.getMapKey())){
                if(!m.equals(claseImplementa.getHashMapMethods().get(m.getMapKey()))) throw new SemanticExceptionMethodWrongImplemented(claseImplementa, m);
            }else{
                throw new SemanticExceptionMethodNotImplemented(claseImplementa, m);
            }
        }
    }










    public void consolidacion() throws SemanticException{
        String s;
        for(Map.Entry<String, Class> entry: clases.entrySet()){
            for(Token t: entry.getValue().getExtendedClasses()){
                s = t.getLexeme();
                checkAtributosHeredados(entry.getValue(), clases.get(s));
                if(herenciaCircular(entry.getValue(), clases.get(t.getLexeme()))) throw new SemanticExceptionCircleExtend(t, entry.getValue());
            }

            for(Token t: entry.getValue().getImplementedClasses()){
                s = t.getLexeme();
                if(clases.containsKey(s)) throw new SemanticExceptionClassImplementClass(entry.getValue());
            }
        }


        for(Map.Entry<String, Interface_> entry: interfaces.entrySet()){
            for(Token t: entry.getValue().getExtendedClasses()){
                s = t.getLexeme();
                if(herenciaCircular(entry.getValue(), interfaces.get(t.getLexeme()))) throw new SemanticExceptionCircleExtend(t, entry.getValue());
            }
        }
    }

    private void checkSignaturaMetodosRedefinidosPorHerencia(ClassOrInterface descendiente, ClassOrInterface padre) throws SemanticException{
        for(Method m: padre.getMethods()){
            if(!descendiente.getHashMapMethods().containsKey(m.getMapKey()) && !m.getMethodType().getLexemeType().equals(m.getMethodName())) {
                descendiente.addMetodo(padre.getHashMapMethods().get(m.getMapKey()));
            }else if(descendiente.getHashMapMethods().containsKey(m.getMapKey())){
                if(!m.equals(descendiente.getHashMapMethods().get(m.getMapKey()))) throw new SemanticExceptionMethodNotRedefined(descendiente.getHashMapMethods().get(m.getMapKey()), descendiente);
            }
        }
    }

    private void checkAtributosHeredados(Class descendiente, Class padre){
        for(Atributo at: padre.getAttributes()){
            if(descendiente.getHashMapAtributes().containsKey(at.getVarName())){
                at.setHeredityVisibility(false);
                descendiente.addAttribute(at);
            }else{
                descendiente.addAttribute(at);
            }
        }
    }












    public void imprimirTablas(){
        System.out.println("\n--------------------------\n\n");
        System.out.println("Clases");
        clases.forEach((nombre,tablaClase) -> {
            String s;
            System.out.println("Nombre: "+nombre);
            ArrayList<Token> aux = tablaClase.getExtendedClasses();
            for(Token t: aux){
                s = t.getLexeme();
                System.out.println(nombre+" hereda de: "+s);
            }
            aux = tablaClase.getImplementedClasses();
            for(Token t: aux){
                s = t.getLexeme();
                System.out.println(nombre+" implementa la clase: "+s);
            }

            System.out.println("Atributos de "+nombre);
            ArrayList<Atributo> atributos = tablaClase.getAttributes();
            for(Atributo a: atributos){
                System.out.println("Atributo: "+a.getVarName()+"---------------------------------");
                System.out.println(" > "+a.getVarName()+" visibilidad: "+a.isVisible());
                System.out.println(" > "+a.getVarName()+" tipo: "+a.getVarType().getLexemeType());
            }

            System.out.println("Metodos de: "+nombre);
            ArrayList<Method> methods = tablaClase.getMethods();
            ArrayList<Parameter> parameters;
            for(Method m: methods){
                System.out.println("Nombre metodo: "+m.getMethodName());
                System.out.println(" > "+m.getMethodName()+" es estatico: "+m.isStatic());
                System.out.println(" > "+m.getMethodName()+" tipo: "+m.getMethodType().getLexemeType());

                parameters = m.getParameters();
                for(Parameter p: parameters){
                    System.out.println("      Parametro nombre: "+p.getVarName());
                    System.out.println("      Parametro tipo: "+p.getVarType().getLexemeType());
                    System.out.println("      Parametro pertenece a: "+p.getMethodOfDefinedParameter().getMethodName());
                }
            }
        });

        System.out.println("\n--------------------------\n\n");
        System.out.println("Interfaces");
        interfaces.forEach((nombre,tablaInterface) -> {
            String s;
            System.out.println("Nombre: "+nombre);
            ArrayList<Token> aux = tablaInterface.getExtendedClasses();
            for(Token t: aux){
                s = t.getLexeme();
                System.out.println(nombre+" hereda de: "+s);
            }

            System.out.println("Metodos de: "+nombre);
            ArrayList<Method> methods = tablaInterface.getMethods();
            ArrayList<Parameter> parameters;
            for(Method m: methods){
                System.out.println("Nombre metodo: "+m.getMethodName());
                System.out.println(" > "+m.getMethodName()+" es estatico: "+m.isStatic());
                System.out.println(" > "+m.getMethodName()+" tipo: "+m.getMethodType().getLexemeType());

                parameters = m.getParameters();
                for(Parameter p: parameters){
                    System.out.println("      Parametro nombre: "+p.getVarName());
                    System.out.println("      Parametro tipo: "+p.getVarType().getLexemeType());
                    System.out.println("      Parametro pertenece a: "+p.getMethodOfDefinedParameter().getMethodName());
                }
            }
        });
    }
}
