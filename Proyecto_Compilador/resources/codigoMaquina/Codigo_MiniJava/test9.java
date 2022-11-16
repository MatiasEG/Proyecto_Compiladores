//101
//11
//33
//22
//1

class MainModule{

    private int mainInt;

    static void main(){
        var a = new A();
        a.init();
        System.printIln(a.enteroX());
        System.printIln(a.publicA1);
        System.printIln(a.publicX1);
        System.printIln(a.getPrivateA1());
        a.setPrivateX1(1);
        System.printIln(a.getPrivateX1());
    }
}

class A extends X{

    public int publicA1;
    private int privateA1;

    void init(){
        publicA1 = 11;
        privateA1 = 22;
        publicX1 = 33;
    }

    int enteroA(){
        return 99;
    }

    int getPrivateA1(){ return privateA1; }
}

class X{

    public int publicX1;
    private int privateX1;

    int enteroX(){
        return 101;
    }

    void setPrivateX1(int privateX1){ this.privateX1 = privateX1; }

    int getPrivateX1(){ return privateX1; }

}