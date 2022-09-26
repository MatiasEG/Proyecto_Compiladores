
interface Interface1 extends InterfaceAux1, InterfaceAux2{

    A m2(char c);

    int m2(char f, boolean x);

    void m1(A a1);

    int m3();
}

class Clase1 extends Clase2 implements Interface1{

    public boolean booleano1, booleano2;

    static void metodo1(A a1, boolean booleano1){}

    void metodo2(){}

    String metodo2(int e){}

    String metodo3(int entero1){}

    private A a1, a2, a3;

    int entero1;

}

class Clase2{}