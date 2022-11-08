package minijava.compiler.semantic;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.semanticP1.classinterface.*;
import minijava.compiler.exception.semanticP1.extend.SemanticExceptionCircleExtend;
import minijava.compiler.exception.semanticP1.extend.SemanticExceptionClassExtendInterface;
import minijava.compiler.exception.semanticP1.extend.SemanticExceptionRepeatedExtend;
import minijava.compiler.exception.semanticP1.method.*;
import minijava.compiler.exception.semanticP1.duplicated.SemanticExceptionDuplicatedAtribute;
import minijava.compiler.exception.semanticP1.duplicated.SemanticExceptionDuplicatedMain;
import minijava.compiler.exception.semanticP1.duplicated.SemanticExceptionDuplicatedMethod;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.*;
import minijava.compiler.semantic.tables.Class;
import minijava.compiler.semantic.tables.variable.Attribute;
import minijava.compiler.semantic.tables.variable.Parameter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private ClassOrInterface actualClassOrInterface;
    private HashMap<String, Class> classes;
    private HashMap<String, Interface_> interfaces;
    private Method actualMethod;
    private Class actualClass;
    private FileWriter writer;
    private String identacionParaCodigo;
    private String mainClass;

    public SymbolTable(){
        classes = new HashMap<>();
        interfaces = new HashMap<>();
        identacionParaCodigo = "";
    }

    public void createConcreteClasses() throws SemanticException {
        Class object = PredefinedClasses.createObjectClass();
        classes.put(object.getNombre(), object);

        Class string = PredefinedClasses.createStringClass();
        classes.put(string.getNombre(), string);

        Class system = PredefinedClasses.createSystemClass();
        classes.put(system.getNombre(), system);
    }

    public void setActualClassOrInterface(ClassOrInterface classOrInterface){
        actualClassOrInterface = classOrInterface;
    }

    public void setActualClassInterfaceListOfExtends(ArrayList<Token> extendsFrom){ actualClassOrInterface.setListOfExtends(extendsFrom); }

    public void setActualClassListOfImplements(ArrayList<Token> implement){ ((Class) actualClassOrInterface).setListOfImplements(implement); }

    public String getActualClassInterfaceName(){ return actualClassOrInterface.getNombre(); }

    public Token getObjectClassToken(){ return classes.get("Object").getClassOrinterfaceToken(); }

    public void insertarClase() throws SemanticException{
        if(alreadyExist(actualClassOrInterface.getNombre()) == null){
            classes.put(actualClassOrInterface.getNombre(), (Class) actualClassOrInterface);
        }else{
            throw new SemanticExceptionClassInterfaceNameDuplicated("Nombre de clase repetido.", actualClassOrInterface);
        }
    }

    public void insertarInterface() throws SemanticException{
        if(alreadyExist(actualClassOrInterface.getNombre()) == null){
            interfaces.put(actualClassOrInterface.getNombre(), (Interface_) actualClassOrInterface);
        }else{
            throw new SemanticExceptionClassInterfaceNameDuplicated("Nombre de interfaz repetido.", actualClassOrInterface);
        }

    }

    public void actualClassAddAtribute(Attribute attribute) throws SemanticException{
        Class clase = (Class) actualClassOrInterface;

        if(!clase.alreadyHaveAttribute(attribute)){
            clase.addAttribute(attribute);
        }else{
            throw new SemanticExceptionDuplicatedAtribute(attribute);
        }
    }

    public void actualClassInterfaceAddMethod(Method method) throws SemanticException{
        if(!actualClassOrInterface.sameMethodOverloaded(method)){
            actualClassOrInterface.addMetodo(method);
        }else{
            throw new SemanticExceptionDuplicatedMethod(method);
        }
    }

    public void actualClassInterfaceAddConstructor(Method method) throws SemanticException{
        if(!actualClassOrInterface.sameMethodOverloaded(method)){
            if(!actualClassOrInterface.getNombre().equals(method.getMethodName())) throw new SemanticExceptionWrongDefinedConstructor(method);
            actualClassOrInterface.addMetodo(method);
        }else{
            throw new SemanticExceptionDuplicatedMethod(method);
        }
    }

    public ClassOrInterface alreadyExist(String classORinterface){
        if(classes.containsKey(classORinterface)) return classes.get(classORinterface);
        if(interfaces.containsKey(classORinterface)) return interfaces.get(classORinterface);

        return null;
    }

    public String bSubtipoA(String a, String b){
        if(classes.containsKey(a) && classes.containsKey(b)){
            String subAclaseBclase = classes.get(a).subtipo(this, classes.get(b));
            if(subAclaseBclase != null)
                return subAclaseBclase;
        }else if(interfaces.containsKey(a) && interfaces.containsKey(b)) {
            String subAinterfaceBinterface = interfaces.get(a).subtipo(this, interfaces.get(b));
            if (subAinterfaceBinterface != null)
                return subAinterfaceBinterface;
        }else if(classes.containsKey(a) && interfaces.containsKey(b)) {
            String subAclaseBinterface = classes.get(a).subtipo(this, interfaces.get(b));
            if (subAclaseBinterface != null) {
                return subAclaseBinterface;
            }
        }
        return null;
    }

    public void check() throws SemanticException{
        int main = 0;
        String s;
        for(Map.Entry<String, Class> entry: classes.entrySet()){

            checkExtendsRepetidos(entry.getValue().getExtendedClasses());

            main += checkMetodos(main, entry.getValue(), entry.getValue().getMethods());

            for(Token t: entry.getValue().getExtendedClasses()){
                s = t.getLexeme();
                if(interfaces.containsKey(s)) throw new SemanticExceptionClassExtendInterface(t);
                if(!classes.containsKey(s)) throw new SemanticExceptionExtendedClassDoesNotExist(entry.getValue(), t);
//                main += checkMetodos(main, entry.getValue(), entry.getValue().getMethods());
                checkSignaturaMetodosRedefinidosPorHerencia(entry.getValue(), alreadyExist(s));
            }

            for(Token t: entry.getValue().getImplementedClasses()){
                s = t.getLexeme();
                if(classes.containsKey(s)) throw new SemanticExceptionClassImplementClass(t);
                if(!interfaces.containsKey(s)) throw new SemanticExceptionImplementedClassDoesNotExist(entry.getValue(), t);
            }

            for(Attribute a: entry.getValue().getAttributes()){
                if(a.getVarType().isClassRef() && alreadyExist(a.getVarType().getLexemeType()) == null) throw new SemanticExceptionClassRefNotExist(a.getVarType().getTokenType());
            }
        }

        if(main == 0) throw new SemanticExceptionMethodMainDoesNotExist();


        for(Map.Entry<String, Interface_> entry: interfaces.entrySet()){
            checkExtendsRepetidos(entry.getValue().getExtendedClasses());
            for(Token t: entry.getValue().getExtendedClasses()){
                s = t.getLexeme();
                if (classes.containsKey(s)) throw new SemanticExceptionInterfaceExtendsClase(classes.get(s));
                if(!interfaces.containsKey(s)) throw new SemanticExceptionExtendedInterfaceDoesNotExist(entry.getValue(), t);
                checkSignaturaMetodosRedefinidosPorHerencia(entry.getValue(), alreadyExist(s));
            }

            for(Method m: entry.getValue().getMethods()){
                if(m.isStatic()) throw new SemanticExceptionStaticMethodOnInterface(m);
            }
        }

    }

    private void checkExtendsRepetidos(ArrayList<Token> clasesHerencia) throws SemanticException{
        HashMap<String, Token> clasesHerenciaHashMap = new HashMap<>();
        for(Token token: clasesHerencia){
            if(!clasesHerenciaHashMap.containsKey(token.getLexeme())){
                clasesHerenciaHashMap.put(token.getLexeme(), token);
            }else{
                throw new SemanticExceptionRepeatedExtend(token);
            }
        }
    }

    private int checkMetodos(int main, Class clase, ArrayList<Method> methods) throws SemanticException{
        boolean constructorBasico = false;

        for(Method m: methods){
            if(m.getMethodName().equals("main") && main == 0 && m.getMethodType().getLexemeType().equals("void") && m.isStatic() && m.getParameters().size()==0){
                mainClass = clase.getNombre();
                main++;
            }else if(m.getMethodName().equals("main") && main == 1 && m.getMethodType().getLexemeType().equals("void") && m.isStatic()){
                throw new SemanticExceptionDuplicatedMain(m, clase);
            }else if(m.getMethodName().equals("main")){
                throw new SemanticExceptionMethodMainWrongDefined(m);
            }else if(m.getMethodName().equals(m.getMethodType().getLexemeType()) && m.getParameters().size() == 0){
                constructorBasico = true;
            }

            for(Parameter p: m.getParameters()){
                if(p.getVarType().isClassRef() && alreadyExist(p.getVarType().getLexemeType()) == null) throw new SemanticExceptionClassRefNotExist(p.getVarType().getTokenType());
            }

            if(m.getMethodType().isClassRef() && alreadyExist(m.getMethodType().getLexemeType()) == null) throw new SemanticExceptionClassRefNotExist(m.getMethodType().getTokenType());
        }

        if(!constructorBasico){
            Method constructorBase = new Method();
            constructorBase.setMethodToken(new Token("idClass", clase.getNombre(), 0));
            constructorBase.setClassDeclaredMethod(clase.getNombre());
            Type typeConstructor = new Type(new Token("idClass", clase.getNombre(), 0));
            constructorBase.setMethodType(typeConstructor);
            constructorBase.setMainBlock(new Block(constructorBase));
            constructorBase.setStatic(true);
            clase.addMetodo(constructorBase);
        }

        return main;
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

    public void consolidacion() throws SemanticException{
        for(Map.Entry<String, Class> entry: classes.entrySet()){
            for(Token t: entry.getValue().getExtendedClasses()){
                consolidacionAtributosHeredados(entry.getValue(), classes.get(t.getLexeme()));
                if(consolidacionHerenciaCircular(entry.getValue(), classes.get(t.getLexeme()))) throw new SemanticExceptionCircleExtend(t, entry.getValue());
            }

            for(Token t: entry.getValue().getImplementedClasses()){
                checkMetodosImplementados(entry.getValue(), interfaces.get(t.getLexeme()));
            }
        }


        for(Map.Entry<String, Interface_> entry: interfaces.entrySet()){
            for(Token t: entry.getValue().getExtendedClasses()){
                if(consolidacionHerenciaCircular(entry.getValue(), interfaces.get(t.getLexeme()))) throw new SemanticExceptionCircleExtend(t, entry.getValue());
            }
        }
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

    private boolean consolidacionHerenciaCircular(ClassOrInterface classOrInterfaceDesc, ClassOrInterface classOrInterfacePadre) {
        boolean resultado = false;
        for(Token herenciaDelPadre: classOrInterfacePadre.getExtendedClasses()){
            if(herenciaDelPadre.getLexeme().equals(classOrInterfaceDesc.getNombre())) resultado = true;
            if(!resultado) resultado = consolidacionHerenciaCircular(classOrInterfaceDesc, classes.get(alreadyExist(herenciaDelPadre.getLexeme()).getNombre()));
        }
        return resultado;
    }

    private void consolidacionAtributosHeredados(Class descendiente, Class padre){
        Attribute atDec;
        for(Attribute at: padre.getAttributes()){
            if(at.isPublic()){
                atDec = Attribute.clone(at);
                atDec.setAttributeIsFromFather(true);
                if(descendiente.getHashMapAtributes().containsKey(at.getVarName())){
                    atDec.setPisado(true);
                    descendiente.addAttribute(atDec);
                }else {
                    descendiente.addAttribute(atDec);
                }
            }
        }
    }


    public void checkSentences() throws SemanticException {
        for(Map.Entry<String, Class> entry: classes.entrySet()){
            setActualClass(entry.getValue());
            for(Method m: entry.getValue().getMethods()){
                setActualMethod(m);
                if(m.getClassDeclaredMethod().equals(entry.getKey())) m.checkBlock(this);
            }
        }
    }

    private void setActualClass(Class class_){ this.actualClass = class_; }

    public Class getActualClass(){ return actualClass; }

    public Class getClass(String className){
        if (classes.containsKey(className)) return classes.get(className);
        else return null;
    }

    public void setActualMethod(Method m){ this.actualMethod = m; }

    public Method getActualMethod(){ return actualMethod; }








    public void generarCodigo() throws IOException {
        write(".CODE\n" +
                "   PUSH main"+mainClass+"\n" +
                "   CALL\n" +
                "   HALT\n\n");

        generarCodigoDeRutinasHeap();
        PredefinedClasses.generarCodigoClasesXDefecto(this);

        for(Map.Entry<String, Class> entry: classes.entrySet()){
            if(entry.getValue().getNombre()!="Object" &&
                    entry.getValue().getNombre()!="String" &&
                    entry.getValue().getNombre()!="System") {

                entry.getValue().generarCodigoData(this);
                setActualClass(entry.getValue());
            }
        }
    }

    public void setWriter(FileWriter writer){
        this.writer = writer;
    }

    public void writeLabel(String txt2write) throws IOException {
        writer.write(txt2write);

    }

    public void write(String txt2write) throws IOException {
        writer.write(identacionParaCodigo+txt2write);
    }

    public void setIdentacionParaCodigo(String espacios){ identacionParaCodigo = espacios; }

    private void generarCodigoDeRutinasHeap() throws IOException {
        writeLabel("# ---------------- simple_heap_init ---------------- \n");
        writeLabel("simple_heap_init:RET 0 # Inicializacion simple del .heap\n\n");


        writeLabel("# ---------------- simple_malloc ---------------- \n");
        String spaces = String.format("%"+("simple_malloc".length()+1)+"s", "");
        writeLabel("simple_malloc:LOADFP # Inicializacion unidad\n" +
                spaces+"LOADSP\n" +
                spaces+"STOREFP # Finaliza inicializacion del RA\n" +
                spaces+"LOADHL # hl\n" +
                spaces+"DUP # hl\n" +
                spaces+"PUSH 1\n" +
                spaces+"ADD # hl + 1\n" +
                spaces+"STORE 4 # Guarda resultado (puntero a base del bloque)\n" +
                spaces+"LOAD 3 # Carga cantidad de celdas a alojar (parametro)\n" +
                spaces+"ADD\n" +
                spaces+"STOREHL # Mueve el heap limit (hl)\n" +
                spaces+"STOREFP\n" +
                spaces+"RET 1 # Retorna eliminando el parametro\n\n");
    }












    public void imprimirTablas(){
        System.out.println("\n--------------------------\n\n");
        System.out.println("Clases");
        classes.forEach((nombre, tablaClase) -> {
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
            ArrayList<Attribute> attributes = tablaClase.getAttributes();
            for(Attribute a: attributes){
                System.out.println("Atributo: "+a.getVarName()+"---------------------------------");
                System.out.println(" > "+a.getVarName()+" visibilidad: "+a.isPublic());
                System.out.println(" > "+a.getVarName()+" tipo: "+a.getVarType().getLexemeType());
                System.out.println(" > "+a.getVarName()+" clase pertenece: "+a.getClass_());
                System.out.println(" > "+a.getVarName()+" atributo del padre: "+a.attributeIsFromFather());
                System.out.println(" > "+a.getVarName()+" esta pisado: "+a.getPisado());
            }

            System.out.println("Metodos de: "+nombre);
            ArrayList<Method> methods = tablaClase.getMethods();
            ArrayList<Parameter> parameters;
            for(Method m: methods){
                System.out.println("Nombre metodo: "+m.getMethodName());
                System.out.println(" > "+m.getMethodName()+" es estatico: "+m.isStatic());
                System.out.println(" > "+m.getMethodName()+" tipo: "+m.getMethodType().getLexemeType());
                System.out.println(" > "+m.getMethodName()+" clase pertenece: "+m.getClassDeclaredMethod());

                parameters = m.getParameters();
                for(Parameter p: parameters){
                    System.out.println("      Parametro nombre: "+p.getVarName());
                    System.out.println("      Parametro tipo: "+p.getVarType().getLexemeType());
                    System.out.println("      Parametro pertenece a: "+p.getMethodOfDefinedParameter().getMethodName());
                }
            }

            System.out.println("---------------------------------------------------------------------------\n");
            System.out.println("---------------------------------------------------------------------------");
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
                System.out.println(" > "+m.getMethodName()+" clase pertenece: "+m.getClassDeclaredMethod());

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
