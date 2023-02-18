package lab5;

public class Person {
    
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Float weight; //Поле не может быть null, Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null

    public Person(String name, Float weight, Color eyeColor, Color hairColor) {
        this.name = name;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Float getWeight() {
        return weight;
    }
    public void setWeight(Float weight) {
        this.weight = weight;
    }
    public Color getEyeColor() {
        return eyeColor;
    }
    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }
    public Color getHairColor() {
        return hairColor;
    }
    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }
}