package TCP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ResourceManager {
    //cities on the tsp file
    public static ArrayList<City> cities;

    static {
        try {
            cities = readFile("src/AIHW2/Cities_Coordinates.tsp");
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist!");
        }
    }

    //file reader
    public static ArrayList<City> readFile(String fileName) throws FileNotFoundException{

        Scanner reader = new Scanner(new File(fileName));
        ArrayList<City> cities = new ArrayList<>();
        int lineCounter = 0;
        
        while (reader.hasNext()) {

            String nextLine = reader.nextLine(); // read next line

            if(nextLine.equals("EOF")) // file ends
                break;

            // skip first 3 row
            if(lineCounter < 3 ){
                lineCounter++;
                continue;
            }

            // split line
            String[] numbers = nextLine.replace("\n", "").split(" ");

            int index = (int) Float.parseFloat(numbers[0]); // get index
            float xCoordinate = Float.parseFloat(numbers[1]); // get X coordinate
            float yCoordinate = Float.parseFloat(numbers[2]); // get Y coordinate

            // Create the city based on the information read from the file.
            cities.add(new City(index, xCoordinate, yCoordinate)); // add city
        }
        return cities;
    }
}
