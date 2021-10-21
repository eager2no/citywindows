package com.chen.webservice;

import com.chen.webservice.Utils.RuleHandler;
import com.chen.webservice.model.Order;
import com.chen.ga.Chromosome;
import com.chen.ga.Fitness;
import com.chen.ga.GeneticAlgorithm;
import com.chen.ga.IterartionListener;
import com.chen.ga.Population;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.chen.webservice.DemoApplication.initOrders;
import static com.chen.webservice.Utils.RuleChain.ruleHandleMap;

public class Demo2 {

    public static void main(String[] args) {
        Population<MyOrder> population = createInitialPopulation(5);

        Fitness<MyOrder, Double> fitness = new MyVectorFitness();

        List<Integer> rules = Arrays.asList(1);

        GeneticAlgorithm<MyOrder, Double> ga = new GeneticAlgorithm<MyOrder, Double>(population, fitness, rules);

        addListener(ga);

        ga.evolve(50000);

        System.out.println("finished!");

        sout(ga.getPopulation().getChromosomeByIndex(0).orders);
    }

    /**
     * @description: 打印最终排片
     * @params: [orders]
     * @return: void
     * @author: chenzhiwen
     * @dateTime: 2021/10/21 下午7:13
     */
    private static void sout(List<Order> orders) {
        long nowDay = getTodayZeroPointTimestamps();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startTime =nowDay + 43200000;
        System.out.println("节目单：\n");

        for (Order order:orders){
            long orderId = order.getOrderId();
            String sd = sdf.format(new Date(startTime)); // 时间戳转换日期
            System.out.print("时间："+sd);
            System.out.print(", 节目id："+orderId);
            System.out.println(", 节目分类："+order.getCategory());
            startTime += order.getPlayTime();

        }
//String sd = sdf.format(new Date(Long.parseLong(beginDate))); // 时间戳转换日期


    }

    public static Long getTodayZeroPointTimestamps() {
        Long currentTimestamps = System.currentTimeMillis();
        Long oneDayTimestamps = Long.valueOf(60 * 60 * 24 * 1000);
        return currentTimestamps - (currentTimestamps + 60 * 60 * 8 * 1000) % oneDayTimestamps;
    }


    /**
     * @description: 初始化初代个体
     * @params: [populationSize]
     * @return: com.chen.ga.Population<com.chen.webservice.Demo2.MyOrder>
     * @author: chenzhiwen
     * @dateTime: 2021/10/21 下午7:13
     */
    private static Population<MyOrder> createInitialPopulation(int populationSize) {
        Population<MyOrder> population = new Population<MyOrder>();
        MyOrder base = new MyOrder();
        processFreq(base.orders);
        for (int i = 0; i < populationSize; i++) {
            // each member of initial population
            // is mutated clone of base chromosome
            MyOrder chr = base.mutate();
            population.addChromosome(chr);
        }
        return population;
    }

    private static void processFreq(List<Order> orders) {
        List<Order> freqOrders = new ArrayList<>();
        for(Order order : orders){
            if(order.getPlayFrequency()<=1){
                continue;
            }
            for(int i = 0; i<order.getPlayFrequency();i++){
                Order freqOrder = order.deepCopy();
                freqOrders.add(freqOrder);
            }
        }
        orders.addAll(freqOrders);
        Collections.shuffle(orders);
    }

    /**
     * @description: 监听迭代
     * @params: [ga]
     * @return: void
     * @author: chenzhiwen
     * @dateTime: 2021/10/21 下午7:13
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
                List<MyOrder> myOrders = ga.getAll();
                //输出当前代个体适应度值
                String fi = getFit(ga, myOrders);
                double bestFit = ga.fitness(best);
                int iteration = ga.getIteration();

                // Listener prints best achieved solution
                if (iteration % 10 == 0) {
                    System.out.println(String.format("%s\t%s\t%s", iteration, bestFit, fi));
                }

                // If fitness is satisfying - we can stop Genetic algorithm
                if (bestFit < this.threshold) {
                    ga.terminate();
                }
            }
        });
    }

    private static String getFit(GeneticAlgorithm<MyOrder, Double> ga, List<MyOrder> myOrders) {
        StringBuilder desc = new StringBuilder();
        myOrders.forEach(myOrder -> {
            String x = ga.fitness(myOrder) + " ";
            desc.append(x);

        });
        return desc.toString();
    }


    /**
     * @description: 排片 bean
     * @params:
     * @return:
     * @author: chenzhiwen
     * @dateTime: 2021/10/21 下午7:14
     */
    public static class MyOrder implements Chromosome<MyOrder>, Cloneable {

        private static final Random random = new Random();

        private List<Order> orders = initOrders(10);

        /**
         * @description: 变异
         * @params: []
         * @return: com.chen.webservice.Demo2.MyOrder
         * @author: chenzhiwen
         * @dateTime: 2021/10/21 下午7:15
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
            result.orders.remove(index1);
            result.orders.add(index1, order2);
            result.orders.remove(index2);
            result.orders.add(index2, order1);

            return result;
        }

        /**
         * @description: 交叉
         * @params: [other]
         * @return: java.util.List<com.chen.webservice.Demo2.MyOrder>
         * @author: chenzhiwen
         * @dateTime: 2021/10/21 下午7:15
         */
        @Override
        public List<MyOrder> crossover(MyOrder other) {
            MyOrder thisClone = this.clone();
            MyOrder otherClone = other.clone();

            return Arrays.asList(thisClone, otherClone);

        }

        @Override
        protected MyOrder clone() {
            MyOrder clone = new MyOrder();
            clone.orders.clear();
            clone.orders.addAll(this.orders);
            return clone;
        }

    }

    /**
     * @description: 适应度函数，具体适应度规则在ruleChain指定
     * @params:
     * @return:
     * @author: chenzhiwen
     * @dateTime: 2021/10/21 下午7:15
     */
    public static class MyVectorFitness implements Fitness<MyOrder, Double> {

        @Override
        public Double calculate(MyOrder chromosome, List<Integer> rules) {
            double delta = 0;

            int ruleRate = 0;

            for(Map.Entry<Integer, RuleHandler> entry: ruleHandleMap.entrySet()){
                if(rules.contains(entry.getKey())){
                    ruleRate += entry.getValue().checkResult(chromosome.orders, ruleRate);
                }
            }

            return delta + ruleRate;
        }

    }
}
