package minijava.compiler.semantic.tables;

import minijava.compiler.exception.semantic.duplicated.SemanticExceptionDuplicatedParameter;
import minijava.compiler.lexical.analyzer.Token;

import java.util.ArrayList;
import java.util.Iterator;

public class Metodo {

    private boolean isStatic;
    protected Token token;
    protected String claseDefinido;
    protected ArrayList<Parametro> parametros;
    protected Tipo tipo;

    public void setToken(Token token) { this.token = token; }

    public Token getToken() { return token; }

    public int getLineNumber(){ return token.getLineNumber(); }

    public String getLexeme(){ return token.getLexeme(); };

    public void setTipo(Tipo tipo){ this.tipo = tipo; }

    public Tipo getTipo() {
        return tipo;
    }

    public void setClaseDefinido(String claseDefinido){ this.claseDefinido = claseDefinido; }

    public String getClaseDefinido() { return claseDefinido; }

    public Parametro addParametro(Parametro parametro){
        parametro.setPosicion(parametros.size()+1);
        for(Parametro p2compare: parametros){
            if(p2compare.getNombre().equals(parametro.getNombre())){
                return parametro;
            }
        }
        parametros.add(parametro);
        return null;
    }

    public ArrayList<Parametro> getParametros() {
        return parametros;
    }

    public String getMapKey(){
        String encabezadoMet = this.getLexeme();
        encabezadoMet = encabezadoMet.concat("(");

        Iterator<Parametro> itParametros = parametros.iterator();
        if(itParametros.hasNext()) encabezadoMet = encabezadoMet.concat(itParametros.next().getTipo().getLexemeType());
        while(itParametros.hasNext()) encabezadoMet = encabezadoMet.concat(","+itParametros.next().getTipo().getLexemeType());

        encabezadoMet = encabezadoMet.concat(")");
        return encabezadoMet;
    }

    public Metodo(){
        parametros = new ArrayList<>();
        isStatic = false;
    }

    public void setStatic(boolean isStatic) { this.isStatic = isStatic; }

    public boolean isStatic() {
        return isStatic;
    }


    public boolean equals(Metodo met2compare){
        if(this.isStatic == met2compare.isStatic && this.getTipo().equals(met2compare.getTipo())){
            return this.getMapKey().equals(met2compare.getMapKey());
        }else{
            return false;
        }
    }
}
