package lab5;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LabWork implements Comparable{

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long minimalPoint; //Значение поля должно быть больше 0
    private Long tunedInWorks;
    private Difficulty difficulty; //Поле может быть null
    private Person author; //Поле не может быть null
    
    public LabWork(Long id, String name, Coordinates coordinates, Date creationDate, Long minimalPoint,
            Long tunedInWorks, Difficulty difficulty, Person author) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.minimalPoint = minimalPoint;
        this.tunedInWorks = tunedInWorks;
        this.difficulty = difficulty;
        this.author = author;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    public java.util.Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(java.util.Date creationDate) {
        this.creationDate = creationDate;
    }
    public long getMinimalPoint() {
        return minimalPoint;
    }
    public void setMinimalPoint(long minimalPoint) {
        this.minimalPoint = minimalPoint;
    }
    public long getTunedInWorks() {
        return tunedInWorks;
    }
    public void setTunedInWorks(long tunedInWorks) {
        this.tunedInWorks = tunedInWorks;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    public Person getAuthor() {
        return author;
    }
    public void setAuthor(Person author) {
        this.author = author;
    }
    @Override
    public int compareTo(Object arg0) {
        return this.id.compareTo(((LabWork)arg0).getId());
    }
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return String.format("LaBwork id %d ---\nName: %s\nCoordinates: %d, %f\nCreation date: %s\nMinimal point: %d\nTuned in works: %d\nDifficulty: %s\nAuthor name: %s\nAuthor weight: %f\nEye color: %s\nHair color: %s.",
                            this.id, this.name, this.coordinates.getX(), this.coordinates.getY(), dateFormat.format(this.creationDate), this.minimalPoint, this.tunedInWorks, this.difficulty.toString(), this.author.getName(), this.author.getWeight(),
                            this.author.getEyeColor().toString(), this.author.getHairColor().toString());
    }
}