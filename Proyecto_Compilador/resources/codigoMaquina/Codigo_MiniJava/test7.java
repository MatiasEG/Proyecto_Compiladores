//Obtengo objeto B
//Obtengo objeto A
//Obtengo objeto B
//9

class MainModule{
    static void main(){
        var a = new A();
        System.printIln(a.bMet().aMet().bMet().entero());

    }
}
class A{

    B bMet(){
        System.printSln("Obtengo objeto B");
        return new B();
    }
}

class B{

    public int varInstancia;

    A aMet(){
        System.printSln("Obtengo objeto A");
        return new A();
    }

    int entero(){
        return 5+4;
    }
}