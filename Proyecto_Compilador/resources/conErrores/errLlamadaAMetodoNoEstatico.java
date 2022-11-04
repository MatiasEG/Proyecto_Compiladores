///[Error:dameUnaAEstatica|102]


class A0 implements X{
    public char c;
    public int i;
    public boolean boo;
    public A a;
    public B b;
    public X x;
    public String s;

    A getA(){}

    void metodo1(int wx){

        // -------- SUBTIPOS --------
        a = null;           // null es asignable para tipos referencia
        a = new A();
        x = a;              // A subtipo de X
        x = new A();        // A subtipo de X
        x = new C();        // C subtipo de X
        b = new C();        // C subtipo de B
        b = new B();
        a = new A();
        a = a;              // instancia propia
        b = b;              // instancia propia
        x = x;              // subtipo propio, pero no instancia propia ya que x debe estar instanciado
        // -----------------------------

        // -------- EXP. BINARIAS --------
        var variableBooleana = ((4+ 10 + 33) > 12) || ((99*15) == 23) && (97<=12) || !true && ((4/2) != 2);
        boo = x == a;       // true
        boo = a == x;       // true
        variableBooleana = true;
        // -----------------------------

        // -------- CONFORMIDAD --------
        var c = new C();    // si comento esta var, toma el artibuto tipo char y da error
        metodo2(i, c);
        metodo2(5, new C());
        metodo2(4*(5+1), x);
        metodo2(metodoInt() + 6, null);
        metodo2(metodoInt(), this);
        // -----------------------------

        // -------- EXP. UNARIAS --------
        i = +4;
        i = -(4 * metodoInt()) - -i;
        variableBooleana = !true;
        variableBooleana = !(4 >= 6);
        // -----------------------------

        // -------- PARENTESIS --------
        s = "string sin parentesis";
        s = ((("string con parentesis")));
        i = (((((((45)))))));
        // -----------------------------

        // -------- LLAMADA MET. Y CONS. --------
        b.testeandoArgs(a, a);
        b.testeandoArgs(x, a);
        b.testeandoArgs(c, a);
        // -----------------------------

        // -------- ENCADENADO --------
        boo = !(b.getC().getA().truefalse);
        boo = this.getA().truefalse;
        boo = getA().truefalse;
        // -----------------------------

        // -------- TIPOS ASIGNACION --------
        i += (4+3)*1;
        i -= -i;
        i = 5;
        // -----------------------------

        // -------- IF --------
        var y = true;
        if(true) {
            var w = 4*3;
            new A();
        }else{
            y = false;
            var w = new B();
        }
        // -----------------------------

        // -------- WHILE --------
        var ww = 1;
        while(!((1*6)>7)){
            var www = ww + 1;

            metodo1(www);
        }
        var www = true;
        // -----------------------------


        // -------- METODOS ESTATICOS --------
        Init.staticA().truefalse;
        boo = Init.dameUnaAEstatica().truefalse;
        var cAux = Init.staticB().getC();
        cAux.getA().entero;
        // -----------------------------
    }

    void metodo2(int entero, X xVar){}

    int metodoInt(){

        // -------- Return --------
        return 1;
        return 0;
        return (4-1)*(7/7);
        return i;
        return (((new C())).getB()).getA().entero;
        // -----------------------------

    }
}

interface X{
    A getA();
}

class A implements X{

    boolean truefalse;
    int entero;

    A getA(){}
}

class B{
    void testeandoArgs(X x, A a){}

    A getA(){}

    C getC(){}
}

class C extends B implements X{

    B getB(){}

    A getA(){}
}

class Init{
    static void main (){}

    static A staticA(){}

    static B staticB(){}

    C notStaticC(){}

    X notStaticX(){}
}