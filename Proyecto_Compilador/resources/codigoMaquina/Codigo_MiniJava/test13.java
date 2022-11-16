//m1 en A
//m2 en A
//m3 en B
//
//m1 en B
//m2 en B
//m3 en B

class MainModule{

    static void main() {

        var a = new A();
        a.m1();
        a.m2();
        a.m3();

        System.println();

        var bAux = new B();
        bAux.m1();
        bAux.m2();
        bAux.m3();
    }
}

class A extends B {

    void m1(){
        System.printSln("m1 en A");
    }

    void m2(){
        System.printSln("m2 en A");
    }
}

class B implements X{

    void m1(){
        System.printSln("m1 en B");
    }

    void m2(){
        System.printSln("m2 en B");
    }

    void m3(){
        System.printSln("m3 en B");
    }
}

interface X extends Y,W{
    void m1();
}

interface Y{
    void m2();
}

interface W {
    void m3();
}