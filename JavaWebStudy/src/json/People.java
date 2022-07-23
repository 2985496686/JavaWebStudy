package json;

public class People{
    String name;
    int age;
    public People(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public People(){}

    @Override
    public String toString() {
        return "people{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}