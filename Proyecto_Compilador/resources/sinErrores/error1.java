//ERROR 1 (valido) SOLUCIONADO -------------
class A {
    public A v1;
    void m1() {
        v1 = new C();
    }

    A m1(int p1) { return new B(); }
}
class B extends A { }
class C extends B { }

//------------- MAIN -------------
class MainModule{
    static void main(){}
}