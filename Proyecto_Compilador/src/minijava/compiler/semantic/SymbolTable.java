package minijava.compiler.semantic;

import minijava.compiler.exception.semantic.*;
import minijava.compiler.exception.semantic.duplicated.SemanticExceptionDuplicatedParameter;
import minijava.compiler.exception.semantic.extend.SemanticExceptionCircleExtend;
import minijava.compiler.exception.semantic.classinterface.SemanticExceptionExtendedClassDoesNotExist;
import minijava.compiler.exception.semantic.classinterface.SemanticExceptionClassImplementClass;
import minijava.compiler.exception.semantic.classinterface.SemanticExceptionClassInterfaceNameDuplicated;
import minijava.compiler.exception.semantic.classinterface.SemanticExceptionImplementedClassDoesNotExist;
import minijava.compiler.exception.semantic.classinterface.SemanticExceptionInterfaceExtendsClase;
import minijava.compiler.exception.semantic.method.*;
import minijava.compiler.exception.semantic.duplicated.SemanticExceptionDuplicatedAtribute;
import minijava.compiler.exception.semantic.duplicated.SemanticExceptionDuplicatedMain;
import minijava.compiler.exception.semantic.duplicated.SemanticExceptionDuplicatedMethod;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private ClaseInterface claseActual;
    private HashMap<String, Clase> clases;
    private HashMap<String, Interface_> interfaces;
    private ArrayList<SemanticException> exceptions;

    public SymbolTable(){
        clases = new HashMap<>();
        interfaces = new HashMap<>();
        exceptions = new ArrayList<>();
        createConcreteClasses();
    }

    public void setClaseActual(Clase clase_){
        claseActual = clase_;
    }

    public void setActualInterface(Interface_ interface_){
        claseActual = interface_;
    }

    public void actualClassInterfaceExtendsFrom(ArrayList<Token> extendsFrom){
        claseActual.setExtendsFrom(extendsFrom);
    }

    public String getActualClassInterfaceName(){ return claseActual.getNombre(); }

    public void actualClassImplements(ArrayList<Token> implement){
        ((Clase) claseActual).setImplement(implement);
    }

    public ArrayList<SemanticException> getExceptions(){ return exceptions; }

    public void insertarClase() throws SemanticException{
        if(alreadyExist(claseActual.getNombre()) == null){
            clases.put(claseActual.getNombre(), (Clase) claseActual);
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
        Clase clase = (Clase) claseActual;

        if(!clase.alreadyHaveAtribute(atributo)){
            clase.addAtribute(atributo);
        }else{
            throw new SemanticExceptionDuplicatedAtribute(atributo);
        }
    }

    public void actualClassInterfaceAddMethod(Metodo metodo) throws SemanticException{
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

    private void createConcreteClasses() {
        createObjectClass();
        createStringClass();
        createSystemClass();
    }

    private void createObjectClass() {
        Parametro parametroIf;
        Clase object = new Clase(new Token("idClass", "Object", 0));

        Metodo debugPrint = new Metodo();
        debugPrint.setStatic(true);
        debugPrint.setClaseDefinido("Object");
        debugPrint.setTipo(new Tipo(new Token("idKeyWord_void", "void", 0)));
        debugPrint.setToken(new Token("idMetVar", "debugPrint", 0));
            Parametro i = new Parametro();
            i.setMetodo(debugPrint);
            i.setPosicion(0);
            i.setVarToken(new Token("idMetVar", "i", 0));
            i.setTipo(new Tipo(new Token("idKeyWord_int", "int", 0)));
        if((parametroIf = debugPrint.addParametro(i)) != null) throw new SemanticExceptionDuplicatedParameter(parametroIf);
        object.addMetodo(debugPrint);

        clases.put(object.getNombre(), object);
    }

    public Token getObjectClassToken(){ return clases.get("Object").getClaseOrinterfaceToken(); }

    private void createStringClass(){
        Clase string = new Clase(new Token("idClass", "String", 0));
        clases.put(string.getNombre(), string);
    }

    private void createSystemClass() throws SemanticException{
        Parametro parametroIf;
        Clase system = new Clase(new Token("idClass", "System", 0));

        Metodo read = new Metodo();
        read.setStatic(true);
        read.setClaseDefinido("System");
        read.setTipo(new Tipo(new Token("idKeyWord_int", "int", 0)));
        read.setToken(new Token("idMetVar", "read", 0));
        system.addMetodo(read);

        Metodo printB = new Metodo();
        printB.setStatic(true);
        printB.setClaseDefinido("System");
        printB.setTipo(new Tipo(new Token("idKeyWord_void", "void", 0)));
        printB.setToken(new Token("idMetVar", "printB", 0));
            Parametro b = new Parametro();
            b.setMetodo(printB);
            b.setPosicion(0);
            b.setVarToken(new Token("idMetVar", "b", 0));
            b.setTipo(new Tipo(new Token("idKeyWord_boolean", "boolean", 0)));
        if((parametroIf = printB.addParametro(b)) != null) throw new SemanticExceptionDuplicatedParameter(parametroIf);
        system.addMetodo(printB);

        Metodo printC = new Metodo();
        printC.setStatic(true);
        printC.setClaseDefinido("System");
        printC.setTipo(new Tipo(new Token("idKeyWord_void", "void", 0)));
        printC.setToken(new Token("idMetVar", "printC", 0));
            Parametro c = new Parametro();
            c.setMetodo(printC);
            c.setPosicion(0);
            c.setVarToken(new Token("idMetVar", "c", 0));
            c.setTipo(new Tipo(new Token("literalCharacter", "char", 0)));
        if((parametroIf = printC.addParametro(c)) != null) throw new SemanticExceptionDuplicatedParameter(parametroIf);
        system.addMetodo(printC);

        Metodo printI = new Metodo();
        printI.setStatic(true);
        printI.setClaseDefinido("System");
        printI.setTipo(new Tipo(new Token("idKeyWord_void", "void", 0)));
        printI.setToken(new Token("idMetVar", "printI", 0));
            Parametro i = new Parametro();
            i.setMetodo(printI);
            i.setPosicion(0);
            i.setVarToken(new Token("idMetVar", "i", 0));
            i.setTipo(new Tipo(new Token("idKeyWord_int", "int", 0)));
        if((parametroIf = printI.addParametro(i)) != null) throw new SemanticExceptionDuplicatedParameter(parametroIf);
        system.addMetodo(printI);

        Metodo printS = new Metodo();
        printS.setStatic(true);
        printS.setClaseDefinido("System");
        printS.setTipo(new Tipo(new Token("idKeyWord_void", "void", 0)));
        printS.setToken(new Token("idMetVar", "printS", 0));
            Parametro s = new Parametro();
            s.setMetodo(printS);
            s.setPosicion(0);
            s.setVarToken(new Token("idMetVar", "s", 0));
            s.setTipo(new Tipo(new Token("idClass", "String", 0)));
        if((parametroIf = printS.addParametro(s)) != null) throw new SemanticExceptionDuplicatedParameter(parametroIf);
        system.addMetodo(printS);

        Metodo println = new Metodo();
        println.setStatic(true);
        println.setClaseDefinido("System");
        println.setTipo(new Tipo(new Token("idKeyWord_void", "void", 0)));
        println.setToken(new Token("idMetVar", "println", 0));
        system.addMetodo(println);

        Metodo printBln = new Metodo();
        printBln.setStatic(true);
        printBln.setClaseDefinido("System");
        printBln.setTipo(new Tipo(new Token("idKeyWord_void", "void", 0)));
        printBln.setToken(new Token("idMetVar", "printBln", 0));
            Parametro bln = new Parametro();
            bln.setMetodo(printBln);
            bln.setPosicion(0);
            bln.setVarToken(new Token("idMetVar", "b", 0));
            bln.setTipo(new Tipo(new Token("idKeyWord_boolean", "boolean", 0)));
        if((parametroIf = printBln.addParametro(bln)) != null) throw new SemanticExceptionDuplicatedParameter(parametroIf);
        system.addMetodo(printBln);

        Metodo printCln = new Metodo();
        printCln.setStatic(true);
        printCln.setClaseDefinido("System");
        printCln.setTipo(new Tipo(new Token("idKeyWord_void", "void", 0)));
        printCln.setToken(new Token("idMetVar", "printCln", 0));
            Parametro cln = new Parametro();
            cln.setMetodo(printCln);
            cln.setPosicion(0);
            cln.setVarToken(new Token("idMetVar", "c", 0));
            cln.setTipo(new Tipo(new Token("literalCharacter", "char", 0)));
        if((parametroIf = printCln.addParametro(cln)) != null) throw new SemanticExceptionDuplicatedParameter(parametroIf);
        system.addMetodo(printCln);

        Metodo printIln = new Metodo();
        printIln.setStatic(true);
        printIln.setClaseDefinido("System");
        printIln.setTipo(new Tipo(new Token("idKeyWord_void", "void", 0)));
        printIln.setToken(new Token("idMetVar", "printIln", 0));
            Parametro iln = new Parametro();
            iln.setMetodo(printIln);
            iln.setPosicion(0);
            iln.setVarToken(new Token("idMetVar", "i", 0));
            iln.setTipo(new Tipo(new Token("idKeyWord_int", "int", 0)));
        if((parametroIf = printIln.addParametro(iln)) != null) throw new SemanticExceptionDuplicatedParameter(parametroIf);
        system.addMetodo(printIln);

        Metodo printSln = new Metodo();
        printSln.setStatic(true);
        printSln.setClaseDefinido("System");
        printSln.setTipo(new Tipo(new Token("idKeyWord_void", "void", 0)));
        printSln.setToken(new Token("idMetVar", "printSln", 0));
            Parametro sln = new Parametro();
            sln.setMetodo(printSln);
            sln.setPosicion(0);
            sln.setVarToken(new Token("idMetVar", "s", 0));
            sln.setTipo(new Tipo(new Token("idClass", "String", 0)));
        if((parametroIf = printSln.addParametro(sln)) != null) throw new SemanticExceptionDuplicatedParameter(parametroIf);
        system.addMetodo(printSln);
    }

    public boolean check() throws SemanticException{
        int main = 0;
        String s;
        for(Map.Entry<String, Clase> entry: clases.entrySet()){
            for(Token t: entry.getValue().getClasesHerencia()){
                s = t.getLexeme();
                //todo arreglar el then, porque entro si no existe s y despues se lo pido igual
                if(!clases.containsKey(s)) throw new SemanticExceptionExtendedClassDoesNotExist(entry.getValue(), t);
                main += checkMainYConstructor(main, entry.getValue(), entry.getValue().getMetodos());
                if(herenciaCircular(entry.getValue(), s)) throw new SemanticExceptionCircleExtend(entry.getValue(), clases.get(s));

            }

            for(Token t: entry.getValue().getClasesImplementadas()){
                s = t.getLexeme();
                if(!interfaces.containsKey(s)) throw new SemanticExceptionImplementedClassDoesNotExist(entry.getValue(), s);
                checkMetodosImplementados(entry.getValue(), interfaces.get(s));
            }
        }

        if(main == 0) throw new SemanticExceptionMethodMainDoesNotExist();


        for(Map.Entry<String, Interface_> entry: interfaces.entrySet()){
            for(Token t: entry.getValue().getClasesHerencia()){
                s = t.getLexeme();
                if(!interfaces.containsKey(s) && !s.equals("Object")) throw new SemanticExceptionExtendedClassDoesNotExist(entry.getValue(), t);
            }
        }

        return true;
    }

    private int checkMainYConstructor(int main, Clase clase, ArrayList<Metodo> metodos) throws SemanticException{
        boolean constructorBasico = false;

        for(Metodo m: metodos){
            if(m.getLexeme().equals("main") && main == 0 && m.getTipo().getLexemeType().equals("void") && m.isStatic()){
                main++;
            }else if(m.getLexeme().equals("main") && main == 1 && m.getTipo().getLexemeType().equals("void") && m.isStatic()){
                throw new SemanticExceptionDuplicatedMain(m, clase);
            }else if(m.getLexeme().equals("main")){
                throw new SemanticExceptionMethodMainWrongDefined(m);
            }else if(m.getLexeme().equals(m.getTipo().getLexemeType()) && m.getParametros().size() == 0){
                constructorBasico = true;
            }
        }

        if(!constructorBasico){
            Metodo constructorBase = new Metodo();
            constructorBase.setToken(new Token("idClass", clase.getNombre(), 0));
            constructorBase.setClaseDefinido(clase.getNombre());
            Tipo tipoConstructor = new Tipo(new Token("idClass", clase.getNombre(), 0));
            constructorBase.setTipo(tipoConstructor);
            clase.addMetodo(constructorBase);
        }

        return main;
    }

    private boolean herenciaCircular(Clase clase, String nombreHerencia) {
        return clases.get(nombreHerencia).getClasesHerencia().contains(clase.getNombre());
    }

    private void checkMetodosImplementados(Clase claseImplementa, Interface_ interface_) throws SemanticException{
        for(Metodo m: interface_.getMetodos()){
            if(claseImplementa.getMetodoHashMap().containsKey(m.getMapKey())){
                if(!m.equals(claseImplementa.getMetodoHashMap().get(m.getMapKey()))) throw new SemanticExceptionMethodWrongImplemented(claseImplementa, m);
            }else{
                throw new SemanticExceptionMethodNotImplemented(claseImplementa, m);
            }
        }
    }










    public void consolidacion() throws SemanticException{
        String s;
        for(Map.Entry<String, Clase> entry: clases.entrySet()){
            for(Token t: entry.getValue().getClasesHerencia()){
                s = t.getLexeme();
                checkSignaturaMetodosRedefinidosPorHerencia(entry.getValue(), alreadyExist(s));
                checkAtributosHeredados(entry.getValue(), clases.get(s));
            }

            for(Token t: entry.getValue().getClasesImplementadas()){
                s = t.getLexeme();
                if(clases.containsKey(s)) throw new SemanticExceptionClassImplementClass(entry.getValue());
            }
        }


        for(Map.Entry<String, Interface_> entry: interfaces.entrySet()){
            for(Token t: entry.getValue().getClasesHerencia()){
                s = t.getLexeme();
                checkSignaturaMetodosRedefinidosPorHerencia(entry.getValue(), alreadyExist(s));
                if(clases.containsKey(s) && !s.equals("Object")) throw new SemanticExceptionInterfaceExtendsClase(interfaces.get(entry.getValue()));
            }
        }
    }

    private void checkSignaturaMetodosRedefinidosPorHerencia(ClaseInterface descendiente, ClaseInterface padre) throws SemanticException{
        for(Metodo m: padre.getMetodos()){
            if(!descendiente.getMetodoHashMap().containsKey(m.getMapKey())) {
                descendiente.addMetodo(padre.getMetodoHashMap().get(m.getMapKey()));
            }else if(descendiente.getMetodoHashMap().containsKey(m.getMapKey())){
                if(!m.equals(descendiente.getMetodoHashMap().get(m.getMapKey()))) throw new SemanticExceptionMethodNotRedefined(descendiente.getMetodoHashMap().get(m.getMapKey()), descendiente);
            }
        }
    }

    private void checkAtributosHeredados(Clase descendiente, Clase padre){
        for(Atributo at: padre.getAtributos()){
            if(descendiente.getAtributosHashMap().containsKey(at.getNombre())){
                at.setVisibilidadHerencia(false);
                descendiente.addAtribute(at);
            }else{
                descendiente.addAtribute(at);
            }
        }
    }












    public void imprimirTablas(){
        System.out.println("\n--------------------------\n\n");
        System.out.println("Clases");
        clases.forEach((nombre,tablaClase) -> {
            String s;
            System.out.println("Nombre: "+nombre);
            ArrayList<Token> aux = tablaClase.getClasesHerencia();
            for(Token t: aux){
                s = t.getLexeme();
                System.out.println(nombre+" hereda de: "+s);
            }
            aux = tablaClase.getClasesImplementadas();
            for(Token t: aux){
                s = t.getLexeme();
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
                System.out.println("Nombre metodo: "+m.getLexeme());
                System.out.println(" > "+m.getLexeme()+" es estatico: "+m.isStatic());
                System.out.println(" > "+m.getLexeme()+" tipo: "+m.getTipo().getLexemeType());

                parametros = m.getParametros();
                for(Parametro p: parametros){
                    System.out.println("      Parametro nombre: "+p.getNombre());
                    System.out.println("      Parametro tipo: "+p.getTipo().getLexemeType());
                    System.out.println("      Parametro pertenece a: "+p.getMetodo().getLexeme());
                }
            }
        });

        System.out.println("\n--------------------------\n\n");
        System.out.println("Interfaces");
        interfaces.forEach((nombre,tablaInterface) -> {
            String s;
            System.out.println("Nombre: "+nombre);
            ArrayList<Token> aux = tablaInterface.getClasesHerencia();
            for(Token t: aux){
                s = t.getLexeme();
                System.out.println(nombre+" hereda de: "+s);
            }

            System.out.println("Metodos de: "+nombre);
            ArrayList<Metodo> metodos = tablaInterface.getMetodos();
            ArrayList<Parametro> parametros;
            for(Metodo m: metodos){
                System.out.println("Nombre metodo: "+m.getLexeme());
                System.out.println(" > "+m.getLexeme()+" es estatico: "+m.isStatic());
                System.out.println(" > "+m.getLexeme()+" tipo: "+m.getTipo().getLexemeType());

                parametros = m.getParametros();
                for(Parametro p: parametros){
                    System.out.println("      Parametro nombre: "+p.getNombre());
                    System.out.println("      Parametro tipo: "+p.getTipo().getLexemeType());
                    System.out.println("      Parametro pertenece a: "+p.getMetodo().getLexeme());
                }
            }
        });
    }
}
