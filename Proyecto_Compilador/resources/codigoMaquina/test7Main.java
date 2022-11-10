class MainModule{
    static void main(){
        var a = new A();
        System.printI(a.bMet().aMet().bMet().entero());

    }
}
class A{

    B bMet(){
        return new B();
    }
}

class B{

    public int varInstancia;

    A aMet(){
        return new A();
    }

    int entero(){
        return 5+4;
    }
}