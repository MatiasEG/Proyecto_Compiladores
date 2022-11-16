class A{
    public String at1;

    static void main(){
        var a = 5;
        var b = 9;

        reverse(a);
        reverse(b);
    }

    static int reverse(int i){
        System.printIln(i);
        return reverseAux(i).reversedNumber;
    }

    static Pair reverseAux(int i){
        var x = new Pair();
        x.reversedNumber = 10;
        x.multiplier = 20;
        return x;
    }
}

class Pair {

    public int reversedNumber;
    public int multiplier;

}