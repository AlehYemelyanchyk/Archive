package by.epam.ayem.main.server.model;

/*Задание 3: создайте клиент-серверное приложение "Архив".
    Общие требования к заданию:
    1. В архиве хранятся Дела (например, студентов). Архив находится на сервере.
    2. Клиент, в зависимости от прав, может запросить дело на просмотр, внести в него изменения,
    или создать новое дело.
Требования к коду:
1. Для реализации сетевого соединения используйте сокеты.
2. Формат хранения данных на сервере - xml-файлы.*/

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchiveRepository {

    public void readStudentsFromFile(StudentsBase studentsBase, String fileName) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            // Определил анонимный класс, расширяющий класс DefaultHandler.
            DefaultHandler handler = new DefaultHandler() {

                String studentId;
                String studentSurname;
                String studentName;
                String studentGroup;
                // Поля, указывающие, что тэг начался.
                boolean id = false;
                boolean surname = false;
                boolean name = false;
                boolean group = false;
                boolean student = false;

                // Метод вызывается, когда SAXParser натыкается на начало тэга. Считывается только qName.
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.equals("id")) {
                        id = true;
                    } else if (qName.equals("surname")) {
                        surname = true;
                    } else if (qName.equals("name")) {
                        name = true;
                    } else if (qName.equals("group_number")) {
                        group = true;
                    }
                }

                // Метод вызывается, когда SAXParser считывет текст между тэгами.
                @Override
                public void characters(char[] ch, int start, int length) {
                    if (id) {
                        studentId = new String(ch, start, length);
                        id = false;
                    } else if (surname) {
                        studentSurname = new String(ch, start, length);
                        surname = false;
                    } else if (name) {
                        studentName = new String(ch, start, length);
                        name = false;
                    } else if (group) {
                        studentGroup = new String(ch, start, length);
                        studentsBase.getStudents().add(new Student(studentId, studentSurname, studentName, studentGroup));
                        group = false;
                    }
                }
            };

            saxParser.parse(fileName, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readUsersFromFile(UserBase userBase, String fileName) {
        List<User> users = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String c;
            String[] subStr;
            while ((c = bufferedReader.readLine()) != null) {
                subStr = c.split(";");
                users.add(createUserNoHash(subStr[0], subStr[1], subStr[2], subStr[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            userBase.setUsers(users);
        }
    }

    private User createUserNoHash(String name, String email, String password, String userRole) {
        UserRole user = null;
        for (UserRole item : UserRole.values()) {
            if (item.toString().equals(userRole)) {
                user = item;
            }
        }
        return new User(name, email, password, user);
    }

    public void writeUserToFile(UserBase userBase, String filename) {
        File file = new File(filename);
        file.delete();

        try (FileWriter fileWriter = new FileWriter(filename, true)) {

            for (User user : userBase.getUsers()) {
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter);

                printWriter.write(user.getName() + ";"
                        + user.getEmail() + ";"
                        + user.getPassword() + ";"
                        + user.getRole() + "\n");
                printWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeStudentToFile(StudentsBase studentsBase, String filePath) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.newDocument();

            Element rootElement = doc.createElement("archive");
            doc.appendChild(rootElement);

            for (Student student : studentsBase.getStudents()) {
                Element childElement = doc.createElement("student");
                rootElement.appendChild(childElement);

                Element id = doc.createElement("id");
                id.setTextContent(student.getId());
                childElement.appendChild(id);

                Element surname = doc.createElement("surname");
                surname.setTextContent(student.getSurname());
                childElement.appendChild(surname);

                Element name = doc.createElement("name");
                name.setTextContent(student.getName());
                childElement.appendChild(name);

                Element groupNumber = doc.createElement("group_number");
                groupNumber.setTextContent(student.getGroupNumber());
                childElement.appendChild(groupNumber);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult sr = new StreamResult(filePath);

            t.transform(source, sr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
