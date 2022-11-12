class A{
    static void main(){
        var l1 = new List();
        l1.setSize(5);
        l1.setBoo(true);

        var l2 = new List();
        l2.setSize(1);
        l2.setBoo(false);

        l2.m1(6,l1);
    }
}

class List{
    private int size;
    private boolean boo;

    void m1(int i, List l){
        System.printIln(i);
        System.printIln(l.getSize());
        System.printIln(size);
        System.printBln(l.getBoo());
        System.printBln(getBoo());
        System.printIln(l.getSize()+size);
    }

    void setSize(int i){ size = i; }

    int getSize(){ return size; }

    void setBoo(boolean boo){ this.boo = boo; }

    boolean getBoo(){ return boo; }
}