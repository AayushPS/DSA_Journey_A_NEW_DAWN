package Graphs;

import java.util.List;
import java.util.PriorityQueue;

/*
 * To find MST from a given source node and return its cost
 */
public class Prims {
    /*
     * List<int[]>[] graph stores = {node,weight}
     * boolean[] visited to mark if a node is already in MST
     * PriorityQueue<int[]> pq contains{node, cost to reach} min {cost to reach} edge to reach the {node}
     */
    public static int prims(List<int[]>[] graph, int source) {
        int cost = 0;
        int size = graph.length;
        boolean[] visited = new boolean[size];
        visited[source] = true;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.add(new int[] {source, 0});
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int weight = current[1];
            cost += weight;
            visited[node] = true;
            for (int[] neighbor : graph[node]) {
                int neighborNode = neighbor[0];
                int neighborWeight = neighbor[1];
                if (!visited[neighborNode]) {
                    pq.add(new int[] {neighborNode, neighborWeight});
                }
            }
        }
        return cost;
    }
}