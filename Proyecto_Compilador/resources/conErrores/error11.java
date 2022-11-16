///[Error:==|9]

//ERROR 11 (invalido) SOLUCIONADO -------------
class A {
    public A a;
    public B b;
    void m1() {
        var r = true;
        r = a == b;
    }
}
class B {}

//------------- MAIN -------------
class MainModule{
    static void main(){}
}