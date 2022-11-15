class A{
    public String a1A;
    void m1A(){
        System.printSln("metodo 1 clase A");
    }
}

class B extends A{
    public String a1B;
    void m1B(){
        System.printSln("metodo 1 clase B");
    }
}

class C extends B{
    public String a1C;
    void m1A(){
        System.printSln("Redefinicion de metodo 1 en clase C");
    }
}

class Principal{
    static void main(){
        var c = new C();
        c.m1A();
    }
}