

//class B{
//    public int n;
//}
//
//class A{
//    static void main(){}
//
//    B b;
//
//    void met1(int a){
//        b.n;
//
//    }
//}


class A {
    public int i1;
    public Init init;
    //public A a1;

    void m1(int p1)
    {

        A a1, a2 = null;

        int x = (3 + 4)* 4, x1;

        m2(p1+(i1-10), "hola!", new B());

        m2(p1+(v1-10), new B(), "hola!");

        new B() = 6;

        init.i2 = p1;

        init.i2 = 5;

        var x = 5;

        x = 6;

    }

    void m2(int p1, String x, B p2){}
}

class B{}

class Init{

    public int i2;
    //public A a2;
    //public char c1;

    //A getA(){ return 5; }

    static void main(){}
}