public class Experiment {
    private long[] bfsTimes;
    private long[] dfsTimes;
    private int[] sizes;

    public Experiment() {
        sizes = new int[]{10, 30, 100};
        bfsTimes = new long[3];
        dfsTimes = new long[3];
    }

    public void runTraversals(Graph g) {
        long start, end;

        start = System.nanoTime();
        g.bfs(0);
        end = System.nanoTime();
        System.out.println("  BFS time: " + (end - start) + " ns");

        start = System.nanoTime();
        g.dfs(0);
        end = System.nanoTime();
        System.out.println("  DFS time: " + (end - start) + " ns");
    }

    public void runMultipleTests() {
        System.out.println("\n========== PERFORMANCE EXPERIMENTS ==========");

        for (int i = 0; i < sizes.length; i++) {
            int n = sizes[i];
            System.out.println("\n--- Graph size: " + n + " vertices ---");

            Graph g = buildGraph(n);

            long start = System.nanoTime();
            redirectBfs(g, 0);
            long end = System.nanoTime();
            bfsTimes[i] = end - start;

            start = System.nanoTime();
            redirectDfs(g, 0);
            end = System.nanoTime();
            dfsTimes[i] = end - start;

            System.out.println("  BFS time: " + bfsTimes[i] + " ns");
            System.out.println("  DFS time: " + dfsTimes[i] + " ns");
        }

        printResults();
    }

    private Graph buildGraph(int n) {
        Graph g = new Graph();
        for (int i = 0; i < n; i++) {
            g.addVertex(new Vertex(i));
        }
        for (int i = 0; i < n - 1; i++) {
            g.addEdge(i, i + 1);
        }
        for (int i = 0; i < n - 3; i += 3) {
            g.addEdge(i, i + 2);
        }
        return g;
    }

    private void redirectBfs(Graph g, int start) {
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();
        boolean[] visited = new boolean[start + 200];
        visited[start] = true;
        queue.add(start);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int nb : g.getAdjList().getOrDefault(cur, new java.util.ArrayList<>())) {
                if (!visited[nb]) {
                    visited[nb] = true;
                    queue.add(nb);
                }
            }
        }
    }

    private void redirectDfs(Graph g, int start) {
        boolean[] visited = new boolean[start + 200];
        dfsHelper(g, start, visited);
    }

    private void dfsHelper(Graph g, int node, boolean[] visited) {
        visited[node] = true;
        for (int nb : g.getAdjList().getOrDefault(node, new java.util.ArrayList<>())) {
            if (!visited[nb]) dfsHelper(g, nb, visited);
        }
    }

    public void printResults() {
        System.out.println("\n========== RESULTS TABLE ==========");
        System.out.printf("%-15s %-20s %-20s%n", "Graph Size", "BFS Time (ns)", "DFS Time (ns)");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("%-15d %-20d %-20d%n", sizes[i], bfsTimes[i], dfsTimes[i]);
        }
    }
}
