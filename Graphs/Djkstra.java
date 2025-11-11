package Graphs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/*
 * To find minimum cost to reach all or certain nodes from a given source node
 */
public class Djkstra {
    /*
     * List<int[]>[] graph stores {node,weight} edges for i'th node as a list
     * int[] dist stores the shortest cost discovered to reach i'th node from source node
     * PriorityQueue<int[]> contains {node discovered, current cost to reach the node}
     */
    public static int[] djkstra(List<int[]>[] graph, int source){
        int[] dist = new int[graph.length];
        Arrays.fill(dist,Integer.MAX_VALUE);
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(i-> i[1]));
        dist[source] = 0;
        pq.add(new int[]{source,0});
        while(!pq.isEmpty()){
            int[] pair = pq.poll();
            int el = pair[0], distSoFar = pair[1];
            if(distSoFar>dist[el]) continue;
            for(int[] edge: graph[el]){
                int nextNode = edge[0] , weight = edge[1];
                if(dist[el]+weight<dist[nextNode]){
                    dist[nextNode] = dist[el] + weight;
                    pq.add(new int[]{nextNode,dist[nextNode]});
                }
            }
        }
        return dist;
    }
}
