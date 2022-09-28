package minijava.compiler.semantic.tables;

import minijava.compiler.exception.semantic.SemanticExceptionDuplicatedParameter;
import minijava.compiler.lexical.analyzer.Token;

import java.util.ArrayList;
import java.util.Iterator;

public class Metodo {

    private boolean isStatic;
    private Tipo tipo;
    private ArrayList<Parametro> parametros;
    private String claseDefinido;
    private Token metodoToken;

    public Metodo(){
        parametros = new ArrayList<>();
        isStatic = false;
    }

    public void setStatic(boolean isStatic) { this.isStatic = isStatic; }

    public void setTipo(Tipo tipo){ this.tipo = tipo; }

    public void setClaseDefinido(String claseDefinido){ this.claseDefinido = claseDefinido; }

    public void addParametro(Parametro parametro) throws SemanticExceptionDuplicatedParameter {
        parametro.setPosicion(parametros.size()+1);
        for(Parametro p2compare: parametros){
            if(p2compare.getNombre().equals(parametro.getNombre())){
                throw new SemanticExceptionDuplicatedParameter(parametro);
            }
        }
        parametros.add(parametro);
    }

    public boolean isStatic() {
        return isStatic;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setMetodoToken(Token metodoToken) { this.metodoToken = metodoToken; }

    public Token getMetodoToken() { return metodoToken; }

    public String getNombre() {
        return metodoToken.getLexeme();
    }

    public int getLineaDeclaracion() { return metodoToken.getLineNumber(); }

    public String getClaseDefinido() { return claseDefinido; }

    public ArrayList<Parametro> getParametros() {
        return parametros;
    }

    public String getMapKey(){
        String encabezadoMet = this.getNombre();
        encabezadoMet = encabezadoMet.concat("(");

        Iterator<Parametro> itParametros = parametros.iterator();
        if(itParametros.hasNext()) encabezadoMet = encabezadoMet.concat(itParametros.next().getVarToken().getLexeme());
        while(itParametros.hasNext()) encabezadoMet = encabezadoMet.concat(","+itParametros.next().getVarToken().getLexeme());

        encabezadoMet = encabezadoMet.concat(")");
        return encabezadoMet;
    }

    public boolean equals(Metodo met2compare){
        if(this.isStatic == met2compare.isStatic && this.getTipo().equals(met2compare.getTipo())){
            return this.equalsParametros(met2compare);
        }else{
            return false;
        }
    }

    public boolean equalsParametros(Metodo met2compare){
        boolean equals = true;
        if(this.getNombre().equals(met2compare.getNombre())){
            ArrayList<Parametro> parametrosMet1 = this.getParametros();
            ArrayList<Parametro> parametrosMet2 = met2compare.getParametros();

            if(parametrosMet1.size() == parametrosMet2.size()){
                Iterator<Parametro> itParametrosMet1 = parametrosMet1.iterator();
                Iterator<Parametro> itParametrosMet2 = parametrosMet2.iterator();

                Parametro pMet1;
                Parametro pMet2;
                while(itParametrosMet1.hasNext()){
                    pMet1 = itParametrosMet1.next();
                    pMet2 = itParametrosMet2.next();

                    if(!pMet1.equals(pMet2)) return false;
                }
            }else{
                equals = false;
            }
        }else{
            equals = false;
        }
        return equals;
    }
}
