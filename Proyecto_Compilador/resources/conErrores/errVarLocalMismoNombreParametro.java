///[Error:a|6]
class A{
    static void main(){}

    void met1(int a){
        char a; //ERROR --> varLocal tiene el mismo nombre que parametro
    }
}