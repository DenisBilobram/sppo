package lab5;

public class Coordinates {

    private int x; //Максимальное значение поля: 689
    private Double y; //Максимальное значение поля: 475, Поле не может быть null
    
    public Coordinates(int x, Double y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public Double getY() {
        return y;
    }
    public void setY(Double y) {
        this.y = y;
    }

    public String toString() {
        return String.format("%f %f", x, y);
    }
}