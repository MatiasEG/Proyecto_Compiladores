class MainModule{
    static void main(){
        var x = new A();
        System.printIln(x.enteroX());
        System.printIln(x.enteroA());
        System.printIln(x.enteroW());
    }
}

class A extends X{
    int enteroA(){
        return 99;
    }
}

class X extends W{
    int enteroX(){
        return 101;
    }
}

class W{
    int enteroW(){
        return 1;
    }
}