package com.chen.webservice;

import com.chen.webservice.model.Order;
import com.chen.ga.Chromosome;
import com.chen.ga.Fitness;
import com.chen.ga.GeneticAlgorithm;
import com.chen.ga.IterartionListener;
import com.chen.ga.Population;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.chen.webservice.DemoApplication.initOrders;

public class Demo2 {

    public static void main(String[] args) {
        Population<MyOrder> population = createInitialPopulation(5);

        Fitness<MyOrder, Double> fitness = new MyVectorFitness();

        GeneticAlgorithm<MyOrder, Double> ga = new GeneticAlgorithm<MyOrder, Double>(population, fitness);

        addListener(ga);

        ga.evolve(50);

        System.out.println("finished!");
    }

    /**
     * The simplest strategy for creating initial population <br/>
     * in real life it could be more complex
     */
    private static Population<MyOrder> createInitialPopulation(int populationSize) {
        Population<MyOrder> population = new Population<MyOrder>();
        MyOrder base = new MyOrder();
        for (int i = 0; i < populationSize; i++) {
            // each member of initial population
            // is mutated clone of base chromosome
            MyOrder chr = base.mutate();
            population.addChromosome(chr);
        }
        return population;
    }

    /**
     * After each iteration Genetic algorithm notifies listener
     */
    private static void addListener(GeneticAlgorithm<MyOrder, Double> ga) {
        // just for pretty print
        System.out.println(String.format("%s\t%s\t%s", "iter", "fit", "chromosome"));

        // Lets add listener, which prints best chromosome after each iteration
        ga.addIterationListener(new IterartionListener<MyOrder, Double>() {

            private final double threshold = 1e-5;

            @Override
            public void update(GeneticAlgorithm<MyOrder, Double> ga) {

                MyOrder best = ga.getBest();
                double bestFit = ga.fitness(best);
                int iteration = ga.getIteration();

                // Listener prints best achieved solution
                System.out.println(String.format("%s\t%s\t%s", iteration, bestFit, best));

                // If fitness is satisfying - we can stop Genetic algorithm
                if (bestFit < this.threshold) {
                    ga.terminate();
                }
            }
        });
    }

    /**
     * Chromosome, which represents vector of five integers
     */
    public static class MyOrder implements Chromosome<MyOrder>, Cloneable {

        private static final Random random = new Random();

        private List<Order> orders = initOrders(10);

        /**
         * Returns clone of current chromosome, which is mutated a bit
         */
        @Override
        public MyOrder mutate() {
            MyOrder result = this.clone();

            // just select random element of vector
            // and increase or decrease it on small value
            int index1 = random.nextInt(this.orders.size());
            int index2 = random.nextInt(this.orders.size());
            Order order1 = result.orders.get(index1);
            Order order2 = result.orders.get(index2);
            result.orders.add(index2, order2);
            result.orders.add(index1, order1);

            return result;
        }

        /**
         * Returns list of siblings <br/>
         * Siblings are actually new chromosomes, <br/>
         * created using any of crossover strategy
         */
        @Override
        public List<MyOrder> crossover(MyOrder other) {
            MyOrder thisClone = this.clone();
            MyOrder otherClone = other.clone();

            // one point crossover
            int index1 = random.nextInt(thisClone.orders.size() - 1);
            int index2 = random.nextInt(other.orders.size() - 1);
            List<Order> thisOrders1 = thisClone.orders.subList(0, index1);
            List<Order> thisOrders2 = thisClone.orders.subList(index1, thisClone.orders.size() - 1);
            thisOrders2.addAll(thisOrders1);
            thisClone.orders = thisOrders2;
            List<Order> otherOrders1 = otherClone.orders.subList(0, index2);
            List<Order> otherOrders2 = otherClone.orders.subList(index2, other.orders.size() - 1);
            otherOrders2.addAll(otherOrders1);
            otherClone.orders = otherOrders2;

            return Arrays.asList(thisClone, otherClone);

        }

        @Override
        protected MyOrder clone() {
            MyOrder clone = new MyOrder();
            clone.orders.addAll(this.orders);
            return clone;
        }

    }

    /**
     * Fitness function, which calculates difference between chromosomes vector
     * and target vector
     */
    public static class MyVectorFitness implements Fitness<MyOrder, Double> {

//		private final int[] target = { 10, 20, 30, 40, 1000 };

        @Override
        public Double calculate(MyOrder chromosome) {
            double delta = 0;
            delta = this.sqr(chromosome);
            return delta;
        }

        private double sqr(MyOrder x) {
            List<Order> orders = x.orders;
            double rate = 1;
            if (orders.get(0).getPlayTime() < 15001 &&orders.get(1).getPlayTime() > 15001&&orders.get(2).getPlayTime() > 15001&&orders.get(3).getPlayTime() == 15000) {
                return rate;
            } else {
                return rate + 1;
            }
        }
    }
}
