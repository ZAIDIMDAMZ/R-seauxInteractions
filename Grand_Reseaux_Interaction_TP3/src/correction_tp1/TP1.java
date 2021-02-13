package correction_tp1;

import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class TP1 {
	public static void mem() {
        Runtime rt = Runtime.getRuntime();
        rt.gc();
        System.err.println("Allocated memory : "
                           + (rt.totalMemory() - rt.freeMemory()) / 1000000
                           + " Mb");
        System.err.flush();
    }

    public static void main(String[] args) throws IOException {
        
        mem();

        // Some tests about array/vector size :

        // Size of an int array: 4 bytes per int.
        int[] arr = new int[1000000];
        
        mem();

        // Size of a vector of integers: 16 bytes per int.
        Vector<Integer> vec = new Vector<>();
        for (int u = 0; u < 1000000; ++u) { vec.add(u); }
        System.err.println("vector " + vec.size() + " " + vec.capacity());
        
        mem();

        // Read edges of a graph.
        String fname = args[0];
        int m_max = Integer.parseInt(args[1]);
        Edges edg = new Edges();
        edg.add(new FileReader(fname), m_max);        // 15s for socpock
        System.err.println("n = " + edg.n + " m = " + edg.m);

        mem();

        // Construct adjacency lists.
        Graph g = new Graph(edg, true);
        System.out.println("n=" + g.n);
        System.out.println("m=" + g.m); 
        
        mem();

        // Check the number of arcs.
        int n = 0;
        int m = 0;
        for (int u : g) {
            ++n;
            for (int v : g.neighbors(u)) ++m;
        }
        System.err.println("n = " + n + " m = " + m + " m/2 = " + (m/2));

        // Maximum degree:
        int degmax = 0, n_degodd = 0;
        for (int u : g) {
            int du = g.degree(u);
            if (du > degmax) degmax = du;
            if (du % 2 == 1) ++n_degodd;
        }
        System.out.println("degmax=" + degmax);
        System.err.println("n_degodd=" + n_degodd);

        // Distance from [src] to [dst].
        int src = Integer.parseInt(args[2]);
        int dst = Integer.parseInt(args[3]);
        Traversal trav = new Traversal(g.n);
        int nb_bfs = 1;
        for (int i = src; i < src + nb_bfs; i += 1) { // 2.4s per BFS on soc-pok
            trav.bfs(g, src);
            System.out.println("dist="+trav.distance(dst));
        }

        mem();
    }
}
