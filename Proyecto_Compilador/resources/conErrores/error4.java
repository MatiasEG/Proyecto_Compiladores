///[Error:A|6]

//ERROR 4 (invalido) SOLUCIONADO -------------
class A {
    B m1() {
        return new A();
    }
}
class B extends A { }

//------------- MAIN -------------
class MainModule{
    static void main(){}
}