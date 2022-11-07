class MainModule{
    static void main(){
        var a = new A();
        a.m1();
        System.printI(a.entero);
    }
}
class A{
    public int entero;

    void m1(){
        entero = 1;
        var b = new B();
        b.mB();
        System.printI(entero+b.intB);
    }
}

class B{
    public int intB;

    void mB(){
        intB = 7;
    }
}