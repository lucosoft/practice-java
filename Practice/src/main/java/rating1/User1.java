package rating1;

public class User1 {

    private final String name;
    private final int age;
    private final Rating1 rating1 = new Rating1();

    public User1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Rating1 getRating() {
        return rating1;
    }

    @Override
    public String toString() {
        return "User{" + "name=" + name + ", age=" + age + '}';
    }
}
