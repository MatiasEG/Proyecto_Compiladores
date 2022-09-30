///[Error:m1|12]

class A extends B implements X{
    static void main(){}
}

class B{
    boolean m1(int i){}
}

interface X{
    int m1(int i);
}