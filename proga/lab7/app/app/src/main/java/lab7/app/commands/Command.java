package lab7.app.commands;

import java.io.File;
import java.io.Serializable;
import java.util.concurrent.PriorityBlockingQueue;

import lab7.app.auth.User;
import lab7.app.labwork.LabWork;
import lab7.app.labwork.Person;
import lab7.app.signals.Signal;


/** Интерфейс для реализации пользовательских команд в виде классов.
 * 
 */
public abstract class Command implements Serializable {

    protected boolean requireLabWork = false;
    protected boolean requireId = false;
    protected boolean requireFile = false;
    protected boolean requirePerson = false;

    protected User user = null;

    protected LabWork labWorkUpdate = null;
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

    public abstract Signal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue);

    public boolean isRequireLabWork() {
        return requireLabWork;
    }

    public void setRequireLabWork(boolean requireLabWork) {
        this.requireLabWork = requireLabWork;
    }

    public LabWork getLabWorkUpdate() {
        return labWorkUpdate;
    }

    public void setLabWorkUpdate(LabWork labWork) {
        this.labWorkUpdate = labWork;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isRequirePerson() {
        return requirePerson;
    }
}
