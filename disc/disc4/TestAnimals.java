public class TestAnimals {
    public static void main(String[] args) {
        Animal a = new Animal("Pluto", 10);
        Cat c = new Cat("Garfield", 6);
        Dog d = new Dog("Fido", 4);
        a.greet();
        c.greet();
        d.greet();
        a = c;
        ((Cat) a).greet();
        a.greet();

        // Compiler error because the static type of d is Dog and the static type of a is Animal. We can fix this by casting: d = (Dog) a;
        // a = new Dog("Spot", 10);
        // d = a;
    }
}
