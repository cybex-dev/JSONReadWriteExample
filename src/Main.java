import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
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
        main.writeToJSON(jsonFile);
    }

    private Main readFromJSON(String jsonFile) {
        // Setup reader to read JSon file
        JSONParser jsonParser = new JSONParser();

        JSONObject obj = null;
        try (FileReader reader = new FileReader("employees.json"))
        {
            System.out.println("Reading JSON file");
            //Read JSON file
            obj = (JSONObject) jsonParser.parse(reader);

        } catch (IOException | ParseException e) {
            e.printStackTrace();

            // Lets exit to prevent any crashes
            System.exit(1);
        }

        // Reading number of students
        Integer totalStudents = (Integer) obj.get("totalStudents");

        // Read capacity state
        Boolean isAtCapacity = (Boolean) obj.get("atCapacity");

        // Read average
        Float average = (Float) obj.get("average");

        // Read students from JSON file
        


        return this;
    }

    private Main editJSONContent() {
        // Edit bill gates to be older

        // Add Student

        // Set class capacity full to true

        // Change class average

        return this;
    }

    private Main writeToJSON(String jsonFile) {
        // Setup writer to write to JSON file

        // Write students to file

        // Write new average

        // Write new capacity state

        return this;
    }

    public Main() {

    }
}
