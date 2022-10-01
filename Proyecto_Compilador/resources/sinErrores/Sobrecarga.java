

class A extends B implements X{
    static void main(){}

    void m1(String s, int i){}

    ClaseRef m1(int i, String s){}

    String m1(char c, int i){}

    String m1(int i, char c){}
}

class B{
    char m1(){}

    ClaseRef m1(int i){}
}

interface X{
    ClaseRef m1(int i, String s);
}