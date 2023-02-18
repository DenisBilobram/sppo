package lab5.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import lab5.LabWork;
import lab5.commands.Reciever;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;



public class DataReader {
    Database database;

    public DataReader(Database db) {
        this.database = db;
    }

    public PriorityQueue<LabWork> read() {
        PriorityQueue<LabWork> stack = new PriorityQueue<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(this.database.storage);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("labwork");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                String tx = node.getTextContent();
                String[] li = tx.split("\n");
                List<String> attrsLi = new ArrayList<>();

                for (int j = 0; j < li.length; j++) {
                    if (!li[j].replace("\t", "").equals("")) {
                        attrsLi.add(li[j].replace("\t", ""));
                    }
                }

                LabWork lab = Reciever.labWorkConstruct(attrsLi, false);

                stack.add(lab);
            };
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Ошибка чтения из файла.");
            System.out.println(e.getMessage());
        }
        return stack;
    } 
}
