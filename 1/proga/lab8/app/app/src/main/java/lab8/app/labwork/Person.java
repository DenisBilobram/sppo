package lab8.app.labwork;

import java.io.Serializable;

/** Класс реализующий человека, используюется в lab8.server.LabWork.
 * 
 */
public class Person implements Serializable {
    
    private long id;
    
    private String name; //Поле не может быть null, Строка не может быть пустой
    private float height; //Поле не может быть null, Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null

    public Person(String name, float height, Color eyeColor) {
        this.name = name;
        this.height = height;
        this.eyeColor = eyeColor;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Float getHeigth() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
    }
    public Color getEyeColor() {
        return eyeColor;
    }
    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }
}