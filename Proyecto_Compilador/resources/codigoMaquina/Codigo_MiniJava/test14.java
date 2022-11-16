//m1 en B
//m2 en B
//m3 en B
//
//m2 en B
//
//m3 en B
//
//m2 en B

class MainModule{

    static void main() {

        var aux = new Aux();
        aux.init();

        aux.imprimirX();
        aux.imprimirY();
        aux.imprimirW();

        var yaux = aux.getYa();

        yaux.m2();
    }
}

class Aux{

    private Y ya;
    private X xb;
    private W wb;

    void init(){
        ya = new A();
        xb = new B();
        wb = new B();
    }

    void imprimirX(){
        xb.m1();
        xb.m2();
        xb.m3();
        System.println();
    }

    void imprimirY(){
        ya.m2();
        System.println();
    }

    void imprimirW(){
        wb.m3();
        System.println();
    }

    Y getYa(){
        return ya;
    }

}

class A extends B implements Y{

    void m1(){
        System.printSln("m1 en A");
    }

    void m3(){
        System.printSln("m3 en A");
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