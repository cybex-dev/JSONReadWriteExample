import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * See for more details: https://howtodoinjava.com/library/json-simple-read-write-json-examples/
 */
public class Main {
    private String clazz;
    private Long totalStudents;
    private boolean isAtCapacity;
    private double average;
    private List<Student> students;
    private Long year;

    class Student {
        private String name, surname;
        private boolean isMale;
        private Long age;
        private List<Long> marks;

        public Student() {
        }

        public Student(String name, String surname, boolean isMale, Long age, List<Long> marks) {
            this.name = name;
            this.surname = surname;
            this.isMale = isMale;
            this.age = age;
            this.marks = marks;
        }

        public List<Long> getMarks() {
            return marks;
        }

        public void setMarks(List<Long> marks) {
            this.marks = marks;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public boolean getIsMale() {
            return isMale;
        }

        public void setIsMale(boolean male) {
            isMale = male;
        }

        public Long getAge() {
            return age;
        }

        public void setAge(Long age) {
            this.age = age;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();

        // Get the JSON file from resources
        String jsonFile = Main.class.getResource("class.json").getFile();

        // Assign to a File instance
        File file = new File(jsonFile);

        // Check if the file exists
        if (!file.exists()) {
            System.out.printf("File [%s] not found\n", jsonFile);
            return;
        }

        // Read JSON file
        main.readFromJSON(jsonFile);

        // Edit JSON file
        main.editJSONContent();

        // Write out to JSON file
        main.writeToJSON("output.json");
    }

    private Main readFromJSON(String jsonFile) {
        // Setup reader to read JSon file
        JSONParser jsonParser = new JSONParser();
        students = new ArrayList<>();

        JSONObject obj = null;
        try (FileReader reader = new FileReader(jsonFile))
        {
            //Read JSON file
            obj = (JSONObject) jsonParser.parse(reader);

        } catch (IOException | ParseException e) {
            e.printStackTrace();

            // Lets exit to prevent any crashes
            System.exit(1);
        }

        // Reading number of students
        totalStudents = (Long) obj.get("totalStudents");

        // Read capacity state
        isAtCapacity = (Boolean) obj.get("atCapacity");

        // Read average
        average = (Double) obj.get("average");

        // Read Class
        clazz = (String) obj.get("class");

        // Read Year
        year = (Long) obj.get("year");

        // Read students from JSON file
        JSONArray studentArray = (JSONArray) obj.get("students");
        for (Object o : studentArray) {

            // Create student object
            JSONObject jsonObject = (JSONObject) o;
            Student student = new Student();
            student.setName((String) jsonObject.getOrDefault("name", "n/a"));
            student.setSurname((String) jsonObject.getOrDefault("surname", "n/a"));
            student.setAge((Long) jsonObject.getOrDefault("age", 0));
            student.setIsMale((Boolean) jsonObject.getOrDefault("isMale", true));

            // Process marks array
            JSONArray studentMarks = (JSONArray) jsonObject.get("marks");
            List<Long> marks = new ArrayList<>();
            // map all objects to integers and add to list
            for (Object studentMark : studentMarks) {
                marks.add((Long) studentMark);
            }
            student.setMarks(marks);

            // add student
            students.add(student);
        }

        return this;
    }

    private Double getAverageFromList(List<Long> marks) {
        return marks.stream().mapToInt(Long::intValue).average().orElse(0);
    }

    private Main editJSONContent() {
        // Edit bill gates to be older
        Student billGates = students.stream().filter(student -> student.getName().equals("bill") && student.getSurname().equals("gates")).findFirst().orElse(null);
        if (billGates != null) {
            billGates.setAge(1234L);
        }

        // Add Student
        students.add(new Student("Jane", "Doe", false, 25L, Arrays.asList(27L, 77L, 99L)));

        // Set class capacity full to true
        isAtCapacity = true;

        // Change class average
        average = students.stream().mapToDouble(value -> getAverageFromList(value.getMarks())).average().orElse(0);

        return this;
    }

    private Main writeToJSON(String jsonFile) {
        JSONObject rootObject = new JSONObject();

        // Write class
        rootObject.put("class", clazz);

        // Write totalStudents
        rootObject.put("totalStudents", totalStudents);

        // Write new capacity state
        rootObject.put("atCapacity", isAtCapacity);

        // Write students to file
        JSONArray studentArray = new JSONArray();
        students.forEach(student -> {
            // Creat containing JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", student.getName());
            jsonObject.put("surname", student.getSurname());
            jsonObject.put("age", student.getAge());
            jsonObject.put("isMale", student.getIsMale());

            // Get Student's marks
            jsonObject.put("marks", student.getMarks());

            // Add Student JSON object to Students JSON Array
            studentArray.add(jsonObject);
        });
        rootObject.put("students", studentArray);

        // Write year
        rootObject.put("year", year);

        // Write new average
        rootObject.put("average", average);

        // Setup writer to write to JSON file
        try (FileWriter writer = new FileWriter(jsonFile))
        {
            //Read JSON file
            writer.write(rootObject.toJSONString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Main() {

    }
}
