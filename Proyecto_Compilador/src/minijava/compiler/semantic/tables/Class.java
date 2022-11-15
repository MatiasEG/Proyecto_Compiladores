package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.variable.Attribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Class extends ClassOrInterface {

    private ArrayList<Token> implement;
    private ArrayList<Attribute> attributes;
    private HashMap<String, Attribute> atributosHashMap;
    private int offsetAtributo;
    private String vtLabel;
    private Map<Integer,Method> mapeoMetodosInterface;

    public Class(Token claseToken){
        extendsFrom = new ArrayList<>();
        implement = new ArrayList<>();
        attributes = new ArrayList<>();
        atributosHashMap = new HashMap<>();
        methods = new ArrayList<>();
        metodosSinSobrecargaMap = new HashMap<>();
        metodoHashMap = new HashMap<>();
        this.claseOrinterfaceToken= claseToken;
        metodosDinamicos = new HashMap<>();
        offsetAtributo = 1;
        vtLabel = "VT_"+getNombre();
        metodosPorOffset = new HashMap<>();
        metodosHeredadosPorOffset = new HashMap<>();
        metodosPorOffsetCompleto = new HashMap<>();
        offsetMetodo = 0;
        mapeoDeMetodosFinal = new HashMap<>();
        mapeoMetodosInterface = new HashMap<>();
    }

    public void setListOfImplements(ArrayList<Token> implement){
        if(!implement.equals("")){
            this.implement = implement;
        }
    }

    public ArrayList<Token> getImplementedClasses(){ return implement; }

    public ArrayList<Attribute> getAttributes(){ return attributes; }

    public HashMap<String, Attribute> getHashMapAtributes(){ return atributosHashMap; }

    public void addAttribute(Attribute attribute){
        attributes.add(attribute);
        attribute.setOffset(offsetAtributo++);
        atributosHashMap.put(attribute.getVarName(), attribute);
    }

    public String subtipo(SymbolTable st, ClassOrInterface claseInterfazSubtipo){
        String nombre = null;
        for(Token padre: extendsFrom){
            if(padre.getLexeme().equals(claseInterfazSubtipo.getNombre()))
                return padre.getLexeme();
            nombre = st.alreadyExist(padre.getLexeme()).subtipo(st, claseInterfazSubtipo);
            if(nombre != null)
                return nombre;
        }
        if(nombre == null){
            for(Token interfazPadre: implement){
                if(interfazPadre.getLexeme().equals(claseInterfazSubtipo.getNombre()))
                    return interfazPadre.getLexeme();
                nombre = st.alreadyExist(interfazPadre.getLexeme()).subtipo(st, claseInterfazSubtipo);
                if(nombre != null)
                    return nombre;
            }
        }
        return null;
    }

    public boolean alreadyHaveAttribute(Attribute attribute){
        for(Attribute a: attributes){
            if(a.getVarName().equals(attribute.getVarName())) return true;
        }
        return false;
    }

    public String getVtLabel(){ return vtLabel; }

    public void generarCodigoData(SymbolTable st) throws IOException {
        st.writeLabel(".DATA\n\n");
        st.writeLabel(vtLabel+": ");
        int cantMetodosDinamicos = metodosDinamicos.size();
        if(cantMetodosDinamicos>0){
            st.writeLabel("DW ");
            int comasNecesarias = cantMetodosDinamicos-1;
//            for(Map.Entry<Integer, Method> entry: metodosPorOffset.entrySet()){
            // TODO intentar que arranque de 0
//            for(int i = 0; i < metodosDinamicos.size(); i++){
            int i = 0;
            while(true){
                if(mapeoDeMetodosFinal.get(i) == null){
                    st.writeLabel("0, ");
                }else{
                    st.writeLabel(mapeoDeMetodosFinal.get(i).getLabel());
                    if(comasNecesarias>0){
                        comasNecesarias--;
                        st.writeLabel(", ");
                    }else{
                        st.writeLabel("\n\n");
                        break;
                    }
                }
                i++;
            }
        }else{
            st.writeLabel("NOP\n\n");
        }

        st.writeLabel(".CODE\n\n");
        for(Map.Entry<String, Method> entry: metodosSinSobrecargaMap.entrySet()){
            st.setActualMethod(entry.getValue());
            if(entry.getValue().metodoEsRedefinido){
                st.writeLabel(entry.getValue().getLabel()+": NOP\n\n");
            }else{
                if(entry.getValue().getClassDeclaredMethod().equals(getNombre())){
                    entry.getValue().generarCodigoBloque(st);
                }
            }
        }

        //TODO seguir con esta generacion de codigo, generar bloque
    }

    public void ordenarMetodosFinal(SymbolTable st){
        Method mAux;
        offsetMetodo = 0;
        if(extendsFrom.size()>1){
            st.getClass(extendsFrom.get(1).getLexeme()).ordenarMetodosFinal(st);
        }else{
//            for(Map.Entry<Integer,Method> entry: metodosPorOffsetCompleto.entrySet()){
            for(Map.Entry<String,Method> entry: metodosSinSobrecargaMap.entrySet()){
                mAux = entry.getValue();
                if(!mAux.esDeInterface && !mAux.isStatic()){
                    mAux.setOffsetMetodo(offsetMetodo++);
                    mapeoDeMetodosFinal.put(mAux.getOffsetMetodo(), mAux);
                }
            }
        }

    }

    public void definirOffsetMetodosInterfaces(SymbolTable st){
        this.generarMapeoMetodosPorOffset();
        Method mAux;
        Method mInterface;
//        for(Map.Entry<Integer,Method> entry: metodosPorOffsetCompleto.entrySet()){
        for(Map.Entry<String,Method> entry: metodosSinSobrecargaMap.entrySet()){
            mAux = entry.getValue();
            if(mAux.esDeInterface && !mAux.isStatic()){

                for(Map.Entry<String,Interface_> interface_entry: st.getInterfaces().entrySet()){
//                for(Token tInterface: implement){
//                    for(Map.Entry<String,Method> metInterface: interface_entry.getValue().getHashMapMethods().entrySet()){
                    mInterface = interface_entry.getValue().metodoPertenece(mAux);
//                        mInterface = st.getInterface(tInterface.getLexeme()).metodoPertenece(mAux);
                        if (mInterface != null) {
                            mAux.setOffsetMetodo(mInterface.getOffsetMetodo());
                            mapeoDeMetodosFinal.put(mAux.getOffsetMetodo(), mAux);
                            break;
                        }
//                    }
                }
            }
        }
        System.out.println("-----------------------------------------");
        System.out.println("Clase "+claseOrinterfaceToken.getLexeme());
        System.out.println("FinalSize: "+mapeoDeMetodosFinal.size());
        System.out.println("offset: "+offsetMetodo);
        System.out.println("-----------------------------------------\n\n");
    }
}
