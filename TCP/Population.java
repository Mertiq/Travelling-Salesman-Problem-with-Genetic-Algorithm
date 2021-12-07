package TCP;

import java.util.ArrayList;
import java.util.Random;

public class Population {

    ArrayList<Solution> solutions = new ArrayList<>();
    ArrayList<City> cities;

    public Solution bestSolution = null;
    public Solution secondBestSolution = null;

    int populationSize;

    public Population(int startCityIndex, int populationSize) {
        cities = ResourceManager.cities;
        this.populationSize = populationSize;
        InitializePopulation(startCityIndex);
    }

    public void InitializePopulation(int startCityIndex) {
        for (int i = 0; i < populationSize; i++){
            solutions.add(new Solution(startCityIndex));
        }
    }

    public void Crossover(Solution solution1, Solution solution2){

        int firstChild1SelectedCity,firstChild2SelectedCity,secondChild1SelectedCity=0,secondChild2SelectedCity=0;

        //create 2 empty solution
        Solution child1 = new Solution(0);
        Solution child2 = new Solution(0);

        //fill solution's path with solution1 and 2
        for (int i = 0; i < solution1.path.size(); i++) {
            child1.path.set(i, solution1.path.get(i));
            child2.path.set(i, solution2.path.get(i));
        }

        Random rand = new Random();

        //choose 2 index to decide the range of path which will change
        int startIndex = rand.nextInt(cities.size()-1);
        int endIndex = rand.nextInt(cities.size()-1);

        while(startIndex == endIndex)
            endIndex = rand.nextInt(cities.size()-1);

        //they can't be first city
        if(startIndex == 0) startIndex++;
        if(endIndex == 0) endIndex++;

        //start index must be lower than end index
        if(startIndex > endIndex){
            int tmp = startIndex;
            startIndex = endIndex;
            endIndex = tmp;
        }

        //System.out.println("start " + startIndex + " end " + endIndex);

        //loop path
        for(int i = 1;i < solution1.path.size()-1;i++){

            if(i > startIndex && i < endIndex){

                firstChild1SelectedCity = child1.path.get(i).index;
                firstChild2SelectedCity = child2.path.get(i).index;

                //swap i index in child1 and 2
                Swap(i,i,child1, child2);

                for (int j = 0; j < child1.path.size(); j++) {
                    if(child1.path.get(j).index == firstChild2SelectedCity){
                        secondChild1SelectedCity = j;
                        break;
                    }
                }
                for (int k = 0; k < child2.path.size(); k++) {
                    if(child2.path.get(k).index == firstChild1SelectedCity){
                        secondChild2SelectedCity = k;
                        break;
                    }
                }

                Swap(secondChild1SelectedCity,secondChild2SelectedCity,child1, child2);
                secondChild1SelectedCity = 0;
                secondChild2SelectedCity = 0;

            }
        }
            ReplaceWithWorst(child1);
            ReplaceWithWorst(child2);

    }

    //swap 2 given cities in different solutions
    public void Swap(int city1index, int city2index, Solution solution1, Solution solution2){
        City city = solution1.path.get(city1index);
        solution1.path.set(city1index, solution2.path.get(city2index));
        solution2.path.set(city2index, city);
    }

    //swap 2 given cities in same solutions
    public void Swap(int city1index, int city2index, Solution solution1){
        City city = solution1.path.get(city1index);
        solution1.path.set(city1index, solution1.path.get(city2index));
        solution1.path.set(city2index, city);
    }

    //print all paths
    public void PrintPopulation(){
        for (Solution solution : solutions) {
            solution.PrintPath();
        }
    }

    //returns the lowest total path length
    public int GetBestSolution(){
        int x=10000;
        for (Solution solution:solutions) {
            solution.CalculateTotalDistance();
            if(x > solution.totalPathLength){
                x = solution.totalPathLength;
                secondBestSolution = bestSolution;
                bestSolution = solution;
            }
        }
        return x;
    }

    //replace given solution with the worst solution in the population
    public void ReplaceWithWorst(Solution solution){

        int greatestLength = 0;
        int greatestLengthIndex = 0;

        for (int i = 0; i < solutions.size(); i++) {
            if(greatestLength <= solutions.get(i).totalPathLength){
                greatestLength = solutions.get(i).totalPathLength;
                greatestLengthIndex = i;
            }
        }

        if(greatestLength > solution.totalPathLength){
            solutions.set(greatestLengthIndex,solution);
            solutions.get(greatestLengthIndex).CalculateTotalDistance();
        }
    }

    //swap mutation
    public void SwapMutation(Solution solution1){
        Random rand = new Random();
        int startIndex = rand.nextInt(cities.size());
        int endIndex = rand.nextInt(cities.size());
        if(startIndex == 0) startIndex++;
        if(endIndex == 0) endIndex++;
        Swap(startIndex,endIndex,solution1);
        solution1.CalculateTotalDistance();
    }
}
