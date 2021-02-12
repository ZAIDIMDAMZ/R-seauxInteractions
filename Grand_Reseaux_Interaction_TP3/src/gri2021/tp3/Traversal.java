package gri2021.tp3;

import java.util.ArrayDeque;

//S'inspirer de cette calss cf correction TP1
public class Traversal {
	// Data-structure for searching a graph.
	    ArrayDeque<Integer> queue;
	    int[] dist;
	    public static final int infinity = Integer.MAX_VALUE; 
	    
	    Traversal(int n) {
	        queue = new ArrayDeque<>();
	        dist = new int [n];
	    }

	    void clear() {
	        queue.clear();
	        for (int i = 0; i < dist.length; ++i) { dist[i] = infinity; }
	    }

	    int distance(int v) { return dist[v]; }

	    // Bread first search, returns the number of visited nodes.
	    int bfs(Graph g, int src) {
	        assert(g.nbS <= dist.length);
	        clear();

	        dist[src] = 0;
	        queue.add(src);
	        int n = 0;
	        
	        while ( ! queue.isEmpty()) {
	            int u = queue.poll(); // FIFO
	            int d = dist[u];
	            ++n;
	           /* for (int v : g.neighbors(u)) {
	                if (dist[v] == infinity) { // first discovery of v
	                    dist[v] = d + 1;
	                    queue.add(v);
	                }
	            }*/
	        }

	        return n;
	    }
}
