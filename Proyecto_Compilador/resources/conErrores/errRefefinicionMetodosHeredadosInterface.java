///[Error:m1|12]

interface X{
    boolean m1(char c);

    boolean m1(String s);
}

interface Y extends X{
    boolean m1(int a);

    ClaseRef m1(char c);
}

class Main{
    static void main(){}
}