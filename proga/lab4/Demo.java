public class Demo {
    public static void main(String[] args) {
        Person v = new Person("Denis");
        Person.Account v1 = new Person.Account(null);
    }
}


class Person {

    private String name;

    Person(String name) {
        this.name = name;
    }

    public Account setAccount(String password) {
        return new Account(password);
    }

    static class Account {

        String password;

        Account(String password) {
            this.password = "123";
        }
    }
}
