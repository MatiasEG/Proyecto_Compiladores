//ERROR 1 (valido) SOLUCIONADO -------------
//class A {
//    public A v1;
//    void m1() {
//        v1 = new C();
//    }
//
//    A m1(int p1) { return new B(); }
//}
//class B extends A { }
//class C extends B { }

//ERROR 2 (valido) SOLUCIONADO -------------
//class A {
//    A m1(int p1) {
//        return new B();
//    }
//}
//class B extends A { }

//ERROR 3 (valido) SOLUCIONADO -------------
//interface I {
//    void m1();
//}
//class A implements I {
//    void m1() { }
//}
//class B {
//    public A a;
//    I m2() {
//        return a;
//    }
//}

//ERROR 4 (invalido) SOLUCIONADO -------------
//class A {
//    B m1() {
//        return new A();
//    }
//}
//class B extends A { }

//ERROR 5 (valido) SOLUCIONADO -------------
//class A {
//    void m1() {
//        var x = new Object();
//    }
//}

//ERROR 6 (valido) SOLUCIONADO -------------
//class A {
//    void m1()
//    {
//        var s = new System();
//        s.printC('c');
//    }
//}

//ERROR 7 (valido) SOLUCIONADO ------------- TODO que hago si me viene un return vacio en un metodo que no es void?
//class A {
//    void m1() {
//        return;
//    }
//}

//ERROR 8 (invalido) SOLUCIONADO -------------
//class A {
//    void m1(A p1) {
//        p1 = "Hola!";
//    }
//}

//ERROR 9 (invalido) SOLUCIONADO -------------
//class A {
//    public B a1;
//    void m1() { a1.x = 5; }
//}
//class B {
//    private int x;
//}

//ERROR 10 (invalido) SOLUCIONADO -------------
//class A {
//    void m1(int p1){ this; }
//}

//ERROR 11 (invalido) SOLUCIONADO -------------
//class A {
//    public A a;
//    public B b;
//    void m1() {
//        var r = true;
//        r = a == b;
//    }
//}
//class B {}

//ERROR 12 (invalido) SOLUCIONADO -------------
//class A {
//    void m1() { var x = m2(); }
//    void m2() { }
//}

//------------------------------------------------
//ERROR 1 (logro-valido) SOLUCIONADO -------------
// Lo que hago es que, si ya esta el metodo declarado, no lo puedo sobrecargar mas,
// ya que no se guardara en la estructura que posee los metodos de la clase
//class A {
//    A(int x) { }
//    void m1() {
//        new A(1).m2();
//    }
//    void m2() { }
//}

//------------------------------------------------
//ERROR 2 (logro-valido) SOLUCIONADO -------------
//class A {
//    public A<A> a1;
//}

//------------- MAIN -------------
class MainModule{
    static void main(){}
}