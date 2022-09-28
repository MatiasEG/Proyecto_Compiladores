
interface InterfaceAux1{}

interface InterfaceAux2{}

interface Interface1 extends InterfaceAux1, InterfaceAux2{

    A m2(char c);

    int m2(char f, boolean x);

    void m1(A a1);

    int m3();
}

class Clase1{

    static void main(){}
}

class Clase2 extends Clase1 implements Interface1{

    public boolean booleano1, booleano2;

    static void metodo1(A a1, boolean booleano1){}

    void metodo2(){}

    String metodo2(int e){}

    String metodo3(int entero1){}

    private A a1, a2, a3;

    int entero1;

    void reverso(int x, A a1){}

    void reverso(A a1, int x){}

}