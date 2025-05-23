package lab8.app.commands;

import java.io.File;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.auth.User;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;

/** Интерфейс для реализации пользовательских команд в виде классов.
 * 
 */
public abstract class Command implements Serializable {

    protected boolean requireLabWork = false;
    protected boolean requireId = false;
    protected boolean requireFile = false;
    protected boolean requirePersonName = false;

    protected User user = null;

    protected LabWork labWorkNew = null;
    protected String personName = null;
    protected String id = null;
    protected File file = null;

    protected String description;

    public String getDescription() {
        return description;
    }

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

    public abstract ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue, ResourceBundle bundle);
    

    public boolean isRequireLabWork() {
        return requireLabWork;
    }

    public void setRequireLabWork(boolean requireLabWork) {
        this.requireLabWork = requireLabWork;
    }

    public LabWork getLabWorkNew() {
        return labWorkNew;
    }

    public void setLabWorkNew(LabWork labWork) {
        this.labWorkNew = labWork;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
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
        return requirePersonName;
    }
}
