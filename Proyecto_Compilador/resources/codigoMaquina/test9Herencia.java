class MainModule{
    static void main(){
        var a = new A();
        System.printIln(a.enteroX());
    }
}

class A extends X{

    int enteroA(){
        return 99;
    }
}

class X{

    int enteroX(){
        return 101;
    }
}