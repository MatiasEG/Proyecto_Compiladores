
//--------------------------------- LISTO
//class Init{ static void main(){} }
//
//class A extends System{
//    static void printIln(int p1) { }
//    void debugPrint(int x) { }
//}


//--------------------------------- LISTO
//class Init{ static void main(){} }
//
//class A extends C, C{}
//
//class C{}


//---------------------------------
//interface Z {  //el mensaje muestra el error aca
//    void g(int y);
//}
//interface A extends Z {
//    void f(int x);
//
//    void h();
//}
//class B implements A {
//    void f(int x) {}
//    void h() {}
//}
//class Init{
//    static void main() { }
//}


//--------------------------------- LISTO
//class A { }
//class B implements A { }
//class Init {
//    static void main() {}
//}


//---------------------------------
class B<Pepe>{}

class A{
    static void main(){}

    B<Pepe4> b1, b2 = null;

    B<Pepe1> m1(B<Pepe3> b){

        A<B> a1 =
                new A<>();    //TODO solo este puede ser opcional los demas tienen que contener algo


    }

}