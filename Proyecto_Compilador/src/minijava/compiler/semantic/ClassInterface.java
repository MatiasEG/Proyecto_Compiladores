package minijava.compiler.semantic;

import java.util.ArrayList;

public abstract class ClassInterface {

    protected String name;
    protected ArrayList<String> extendsFrom;

    public void setExtendsFrom(ArrayList<String> extendsFrom){
        this.extendsFrom =  extendsFrom;
    }

    public String getName(){ return name; }

    public ArrayList<String> getClasesHerencia(){ return extendsFrom; }
}
