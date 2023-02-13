public class Main {
    public static void main(String[] args) {
        Box<String> b1 = new Box();
        String s1 = b1.pack("123");
    }
}

class Box<T> {
    public T pack(T obj) {
        return obj;
    }
}