//------------------------------------------------
//ERROR 1 (logro-valido) SOLUCIONADO -------------
// Lo que hago es que, si ya esta el metodo declarado, no lo puedo sobrecargar mas,
// ya que no se guardara en la estructura que posee los metodos de la clase
class A {
    A(int x) { }
    void m1() {
        new A(1).m2();
    }
    void m2() { }
}

//------------- MAIN -------------
class MainModule{
    static void main(){}
}