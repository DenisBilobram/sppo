package client.labwork;


/** Класс реализующий координаты которые используются в классе lab5.LabWork.
 * 
 */
public class Coordinates {

    private long x; //Максимальное значение поля: 689
    private long y; //Максимальное значение поля: 475, Поле не может быть null
    
    public Coordinates(long x, long y) {
        this.x = x;
        this.y = y;
    }
    public long getX() {
        return x;
    }
    public void setX(long x) {
        this.x = x;
    }
    public long getY() {
        return y;
    }
    public void setY(long y) {
        this.y = y;
    }

    public String toString() {
        return String.format("%d %d", x, y);
    }
}