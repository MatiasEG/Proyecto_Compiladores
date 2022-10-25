
class C{
    private int i;

    B x(int i){}

    void metodoTest(){
        x(5).a.y.z3(false);        //z3 --> corresponde a Resolviendo los Nombres de Metodos (1)

        x(i).bMethod();                 //bMethod --> corresponde a Resolviendo los Nombres de Metodos (2)
    }


    void chequeandoAccesoVariables(int i, boolean b, A aRef){
        aRef.y;                         //aRef --> corresponde a Resolviendo los Nombres de Metodos (3)
        aRef;                           //aRef --> corresponde a Resolviendo los Nombres de Metodos (3)
        b = true;
        x(i).a                          //a --> corresponde a Resolviendo los Nombres de Metodos (4)
                = new A();              //A --> corresponde a Resolviendo los Nombres de Metodos (5)
    }

}

class B{
    public A a;

    void bMethod(){}
}

class A{
    public Y y;
}

class Y{
    void z3(boolean b){
        w("test");                  //w --> corresponde a Resolviendo los Nombres de Metodos (1)
    }

    void w(String s){}
}

class Init{
    static void main(){}
}