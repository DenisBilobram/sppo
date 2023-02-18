package lab5.database;

import lab5.LabWork;

import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.PriorityQueue;

public class DataWriter {
    Database database;

    public DataWriter(Database db) {
        this.database = db;
    }

    public void write(PriorityQueue<LabWork> collecStack) {
        try {
            FileWriter writer = new FileWriter(this.database.storage);
            String coll = new String();
            coll += "<stack>\n";
            for (LabWork labWork : collecStack) {
                coll += "\t<labwork>\n";
                coll += String.format("\t\t<id>%d</id>\n", labWork.getId());
                coll += String.format("\t\t<name>%s</name>\n", labWork.getName());
                coll += "\t\t<coordinates>\n";
                coll += String.format("\t\t\t<x>%d</x>\n", labWork.getCoordinates().getX());
                coll += String.format("\t\t\t<y>%.2f</y>\n", labWork.getCoordinates().getY());
                coll += "\t\t</coordinates>\n";
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String strDate = dateFormat.format(labWork.getCreationDate());
                coll += String.format("\t\t<creationdate>%s</creationdate>\n", strDate);
                coll += String.format("\t\t<minimalpoint>%d</minimalpoint>\n", labWork.getMinimalPoint());
                coll += String.format("\t\t<tunedinworks>%d</tunedinworks>\n", labWork.getTunedInWorks());
                coll += String.format("\t\t<difficulty>%s</difficulty>\n", labWork.getDifficulty().toString());
                coll += "\t\t<author>\n";
                coll += String.format("\t\t\t<name>%s</name>\n", labWork.getAuthor().getName());
                coll += String.format("\t\t\t<weight>%.2f</weight>\n", labWork.getAuthor().getWeight());
                coll += String.format("\t\t\t<eyecolor>%s</eyecolor>\n", labWork.getAuthor().getEyeColor().toString());
                coll += String.format("\t\t\t<haircolor>%s</haircolor>\n", labWork.getAuthor().getHairColor().toString());
                coll += "\t\t</author>\n";
                coll += "\t</labwork>\n";
            }
            coll += "</stack>\n";
            
            writer.write(coll);
            writer.flush();
            writer.close();

        } catch (java.io.IOException e) {
            System.out.println("Ошибка записи в файл.");
        }
    }
}
