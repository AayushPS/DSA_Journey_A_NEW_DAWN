package Graphs;

import java.util.Comparator;
import java.util.List;

/*
 * To find the optimal MST of the graph and return its cost
 */
public class Krushkal {
    /*
     * List<int[]> edges stores {node1, node2, edge's Weight}
     */
    public static int krushkal(List<int[]> edges,int nodes){
        int cost = 0;
        DSU uf = new DSU(nodes);
        edges.sort(Comparator.comparingInt(i->i[2]));
        for(int[] e: edges){
            int u = e[0] , v = e[1] , weight = e[2];
            if(!uf.isConnected(u,v)){
                uf.union(u,v);
                cost += weight;
            }
        }
        return cost;
    }
}
