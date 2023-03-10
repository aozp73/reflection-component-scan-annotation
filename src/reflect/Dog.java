package reflect;

class Animal {
    void speak() {
    }
}

class Cat extends Animal {
    // 무효화가 상대적이라는 말은 Cat이 new될 땐 무효화가 안됨 (재정의 하지 않음)
    void ss() {

    }
}

public class Dog extends Animal {
    @Override
    void speak() {
    }
}
