package app;

import java.util.PriorityQueue;

public class test {
    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(5);
        priorityQueue.add(10);
        priorityQueue = new PriorityQueue<>(priorityQueue.stream().filter(x -> x < 7).toList());
        for (Integer integer : priorityQueue) {
            System.out.println(integer);
        }
    }
}
