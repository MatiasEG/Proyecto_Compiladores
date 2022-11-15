//m1 en B
//m1 en C

class A{
    static void main(){
        getB().m1();
        getC().m1();

        System.printSln("----------");
        var b = new B();
        b.m1();
        b.x1();
        b.y1();

        System.printSln("----------");
        getBX().x1();
        getBX().y1();
    }

    void metA1(){ System.printIln(1); }
    void metA2(){ System.printIln(2); }
    void metA3(){ System.printIln(3); }
    void metA4(){ System.printIln(4); }

    static X getBX(){
        return new B();
    }

    static I getB(){
        return new B();
    }

    static I getC(){
        return new C();
    }
}

interface X extends Y{
    void x1();
}

interface Y{
    void y1();
}

interface I{
    void m1();
}

class B implements I, X{
    void metB1(){ System.printIln(1); }

    void m1(){
        System.printSln("m1 en B");
    }

    void x1() { System.printSln("x1 en B"); }

    void y1() { System.printSln("y1 en B"); }
}

class C implements I, Y{
    void m1(){
        System.printSln("m1 en C");
    }

    void y1() { System.printSln("y1 en C"); }
}