///[Error:InterfaceAux2|20]

interface InterfaceAux1{
    A m2(int b);
}

interface InterfaceAux2{}

interface Interface1 extends InterfaceAux1, InterfaceAux2{

    A m2(char c);

    int m2(char f, boolean x);

    void m1(A a1);

    int m3();
}

interface InterfaceAux2{
    int mx();
}