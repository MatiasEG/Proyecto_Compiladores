class Main{
    static void main(){
        var a = new A();
        var b = new B();
        var ab = new B();

        a.redefinido();
        b.redefinido();
        ab.redefinido();
    }
}

class A{
    public int a1;

    int setA1(int a1){
        this.a1 = a1;
    }

    void showA1(){
        System.printIln(a1);
    }

    void redefinido(){
        System.printSln("Metodo def. en A");
    }
}

class B extends A{
    public int b1;

    int setB1(int b1){
        this.b1 = b1;
    }

    void showB1(){
        System.printIln(b1);
    }

    void redefinido(){
        System.printSln("Metodo redef. en B");
    }
}