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

    private void generarMapeoMetodosPorOffset(){
        for(Map.Entry<Integer,Method> mHeredados: metodosHeredadosPorOffset.entrySet()){
            metodosPorOffsetCompleto.put(mHeredados.getKey(), mHeredados.getValue());
        }
        for(Map.Entry<Integer,Method> mPropios: metodosPorOffset.entrySet()){
            if(!metodosPorOffsetCompleto.containsKey(mPropios.getKey()))
                metodosPorOffsetCompleto.put(mPropios.getKey(), mPropios.getValue());
        }
    }

    public void generarCodigoData(SymbolTable st) throws IOException {
        this.generarMapeoMetodosPorOffset();
        st.writeLabel(".DATA\n\n");
        st.writeLabel(vtLabel+": ");
        int cantMetodosDinamicos = metodosDinamicos.size();
        if(cantMetodosDinamicos>0){
            st.writeLabel("DW ");
            int comasNecesarias = cantMetodosDinamicos-1;
//            for(Map.Entry<Integer, Method> entry: metodosPorOffset.entrySet()){
            // TODO intentar que arranque de 0
            for(int i = 0; i < offsetMetodo; i++){
                st.writeLabel(metodosPorOffsetCompleto.get(i).getLabel());
                if(comasNecesarias>0){
                    comasNecesarias--;
                    st.writeLabel(", ");
                }else{
                    st.writeLabel("\n\n");
                }
            }
        }else{
            st.writeLabel("NOP\n\n");
        }

        st.writeLabel(".CODE\n\n");
        for(Map.Entry<String, Method> entry: metodosSinSobrecargaMap.entrySet()){
            st.setActualMethod(entry.getValue());
            if(entry.getValue().getClassDeclaredMethod().equals(getNombre())){
                entry.getValue().generarCodigoBloque(st);
            }
        }

        //TODO seguir con esta generacion de codigo, generar bloque
    }
}
