///[Error:return|13]

class A {
    public int i1;
    public Init init;
    public A a1;

    void m1(int p1)
    {
        init.i2 = 5;
        init.getA() = a1;
        //get().m1;
        return 7;
    }
}

class Init{

    public int i2;
    public A a2;
    public char c1;

    A getA(){ return 5; }

    static void main(){}
}