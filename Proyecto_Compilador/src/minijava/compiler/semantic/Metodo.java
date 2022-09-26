package minijava.compiler.semantic;

import minijava.compiler.exception.SemanticExceptionDuplicatedParameter;

import java.util.ArrayList;
import java.util.Iterator;

public class Metodo {

    private boolean isStatic;
    private Tipo tipo;
    private String nombre;
    private ArrayList<Parametro> parametros;
    private int lineaDeclaracion;
    private String claseDefinido;

    public Metodo(){
        parametros = new ArrayList<>();
    }

    public void setStatic(boolean isStatic) { this.isStatic = isStatic; }

    public void setTipo(Tipo tipo){ this.tipo = tipo; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setClaseDefinido(String claseDefinido){ this.claseDefinido = claseDefinido; }

    public void setLineaDeclaracion(int lineaDeclaracion){ this.lineaDeclaracion = lineaDeclaracion; }

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

    public String getNombre() {
        return nombre;
    }

    public int getLineaDeclaracion() { return lineaDeclaracion; }

    public String getClaseDefinido() { return claseDefinido; }

    public ArrayList<Parametro> getParametros() {
        return parametros;
    }

    public boolean equals(Metodo met2compare){
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
