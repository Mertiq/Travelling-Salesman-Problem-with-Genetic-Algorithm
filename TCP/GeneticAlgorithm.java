package TCP;

import java.util.Random;

public class GeneticAlgorithm {

    public void run(){
        int populationSize = 100;
        int generationCount = 10000;
        int startCityIndex = 0;
        Random rand = new Random();

        Population p = new Population(startCityIndex,populationSize);

        for (int i = 0; i < generationCount; i++) {

            p.GetBestSolution();

            for (int j = 0; j < 100; j++) {
                p.Crossover(p.bestSolution, p.secondBestSolution);
                p.GetBestSolution();
            }

            for (int j = 0; j < 100; j++) {
                Solution s = p.solutions.get(rand.nextInt(p.solutions.size()));
                while(s == p.bestSolution || s == p.secondBestSolution)
                    s = p.solutions.get(rand.nextInt(p.solutions.size()));

                p.SwapMutation(s);
                p.GetBestSolution();
            }

            p.bestSolution.PrintPath();
        }

    }

}
