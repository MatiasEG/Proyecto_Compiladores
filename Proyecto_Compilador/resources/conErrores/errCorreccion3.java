///[Error:g|4]

interface Z {
    void g(int y);  // B implementa A, A extiende Z, pero B no implementa g de Z
}
interface A extends Z {
    void f(int x);

    void h();
}
class B implements A {
    void f(int x) {}
    void h() {}
}
class Init{
    static void main() { }
}