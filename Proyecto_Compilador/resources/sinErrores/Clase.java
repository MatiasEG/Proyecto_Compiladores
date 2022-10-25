
//-------------------------------------------------
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

//-------------------------------------------------
//class A {
//    public int i1;
//    public Init init;
//    //public A a1;
//
//    void m1(int p1)
//    {
//
//        init.m2(p1+(i1-10), "hola!", true);
//
//        A a1, a2 = null;
//
//        int x = (3 + 4)* 4, x1;
//
////        m2(p1+(v1-10), new B(), "hola!");
//
////        new B() = 6;
//
//        init.i2 = p1;
//
//        init.i2 = 5;
//
//        var x = 5;
//
//        x = 6;
//
//    }
//
//
//}
//
//class B{}
//
//class Init{
//
//    public int i2;
//    //public A a2;
//    //public char c1;
//
//    //A getA(){ return 5; }
//    void m2(int p1, String x, boolean p2){}
//    static void main(){}
//}


//-------------------------------------------------
//class A {
//    public int a1;
//
//    void m1(int p1)
//    {
//        var x = 1;  //Aca esta la primer declaracion
//        {
////            {
////                var y = 2;
////            }
////            var y = 3;
//            {
//                var x = true;   //Err: var local repetida
//            }
//        }
//    }
//}
//
//
//class Init{
//    static void main(){}
//}


//-------------------------------------------------
//class A {
//    public int a1;
//    public int a3;
//    public D d1;
//
//    void m1(boolean x){
//
//        a1 = a3;
//
////        x = ((4+ 10 + 33) > 12) || ((99*15) == 23) && (97<=12) || !true && ((4/2) != 2);
//
////        x = !true;
//
////        x = d1 == false;
//
////        x = (99*15) == 23;
//    }
//
//    void m2(){}
//
//}
//
//class C extends D{}
//class D extends X,Y{}
//class X{}
//class Y{}
//class B extends Y{}
//
//class Init{
//    static void main(){}
//}


//-------------------------------------------------
//class A {
//    private int a1;
//
//    void m1(){
//        a1 = a1;
//    }
//}
//
//
//class B extends A{}
//
//
//class Init{
//    static void main(){}
//}


//-------------------------------------------------
//class A {
//    public B a1;
//    public int a2;
//}
//
//class B extends A{
//    public A a3;
//
//    void m1(B p1)
//    {
//        a1.a3.a2 = 4;
//    }
//}
//
//
//class Init{
//    static void main(){}
//}


class A {
    public int i1;
    public Init init;
    public A a1;

    void m1(int p1)
    {
        init.i2 = 5;
        init.getA() = a1;
        //get().m1;
    }
}

class Init{

    public int i2;
    public A a2;
    public char c1;

    A getA(){ return 5; }

    static void main(){}
}