public class Demo {
    public static void main(String[] args) {
        Thing th1 = new Thing("100");
        Thing th2 = new Thing("100");

        System.out.println(th1.equals(th2));
    }
}

class Thing {

    public String description;

    Thing(String desc) {
        this.description = desc;
    }

    public String toString() {
        return this.description;
    }

    // @Override
    // public boolean equals(Object thing) {
    //     if (thing instanceof Thing) {
    //         return this.description == ((Thing)thing).description;
    //     }
    //     return super.equals(thing);
    // }
}
