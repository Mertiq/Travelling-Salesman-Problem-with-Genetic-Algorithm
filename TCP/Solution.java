package TCP;

import java.util.ArrayList;
import java.util.Random;

public class Solution {

    public int totalPathLength;
    public ArrayList<City> path = new ArrayList<>();
    public ArrayList<City> cities = ResourceManager.cities;

    public Solution(int startCityIndex){
        InitializePath(startCityIndex);
    }

    //initialize path
    public void InitializePath(int startCityIndex){

        path.add(cities.get(startCityIndex));

        for (int i = 1; i < cities.size(); i++) {
            Random rand = new Random();
            float random = rand.nextInt(cities.size())+1;
            while (PathControl(random)){
                random = rand.nextInt(cities.size())+1;
            }

            path.add(cities.get((int)random-1));
        }
        path.add(path.get(0));
    }

    //path control if this city selected or not
    public boolean PathControl(float a){
        for (City city : path) {
            if (city.index == a)
                return true;
        }
        return false;
    }

    //Calculate Total Distance
    public void CalculateTotalDistance(){
        int total = 0;
        for (int i = 0; i < path.size()-1; i++) {
            total += CalculateDistanceBetween2Cities(path.get(i),path.get(i+1));
        }
        totalPathLength = total;
    }

    //Calculate Distance Between 2 Cities
    public int CalculateDistanceBetween2Cities(City city1, City city2){
        int a = (int) Math.pow(city1.x_coordinate - city2.x_coordinate,2);
        int b = (int) Math.pow(city1.y_coordinate - city2.y_coordinate,2);
        return (int) Math.sqrt(a+b);
    }

    public void PrintPath(){
        System.out.print("total " + totalPathLength);
        System.out.print(" ==> ");
        for (City city : path) {
            if (city.index < 10)
                System.out.print(city.index + "   ");
            else if (city.index < 100)
                System.out.print(city.index + "  ");
            else
                System.out.print(city.index + " ");
        }
        System.out.println();
    }

}
