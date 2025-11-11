package Graphs;

/*
 * To check Connectivity of nodes in Graph optimally
 */
public class DSU {
    int[] parent;
    int[] rank;

    public DSU(int n){
        this.parent = new int[n];
        for(int i = 0; i<n; i++){
            this.parent[i] = i;
        }
        this.rank = new int[n];;
    }
    public int find(int x){
        if(parent[x]!=x) parent[x] = find(parent[x]);
        return parent[x];
    }
    public void union(int x, int y){
        int px = find(x), py = find(y);
        if(rank[px]<rank[py]){
            parent[px] = py;
        }else if(rank[px]>rank[py]){
            parent[py] = px;
        }else{
            parent[py] = px;
            rank[px]++;
        }
    }
    public boolean isConnected(int x, int y){
        return find(x)==find(y);
    }
}
