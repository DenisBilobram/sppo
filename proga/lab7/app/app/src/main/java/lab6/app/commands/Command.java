package lab7.app.commands;

import java.io.File;
import java.io.Serializable;
import java.util.PriorityQueue;

import lab7.app.labwork.LabWork;
import lab7.app.labwork.Person;
import lab7.app.signals.Signal;


/** Интерфейс для реализации пользовательских команд в виде классов.
 * 
 */
public abstract class Command implements Serializable {

    protected boolean requireLabWork = false;
    protected boolean requirePerson = false;
    protected boolean requireId = false;
    protected boolean requireFile = false;

    protected LabWork labWork = null;
    protected Person person = null;
    protected String id = null;
    protected File file = null;

    public boolean isRequireFile() {
        return this.requireFile;
    }

    public boolean isRequireId() {
        return this.requireId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public abstract Signal execute(PriorityQueue<LabWork> PriorityQueue);

    public boolean isRequireLabWork() {
        return requireLabWork;
    }

    public void setRequireLabWork(boolean requireLabWork) {
        this.requireLabWork = requireLabWork;
    }

    public boolean isRequirePerson() {
        return requirePerson;
    }

    public void setRequirePerson(boolean requirePerson) {
        this.requirePerson = requirePerson;
    }

    public LabWork getLabWork() {
        return labWork;
    }

    public void setLabWork(LabWork labWork) {
        this.labWork = labWork;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
