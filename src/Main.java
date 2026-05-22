public class Main {
    public static void main(String[] args) {

        System.out.println("====== SMALL GRAPH (10 vertices) ======");
        Graph small = new Graph();
        for (int i = 0; i < 10; i++) {
            small.addVertex(new Vertex(i));
        }
        small.addEdge(0, 1);
        small.addEdge(0, 2);
        small.addEdge(1, 3);
        small.addEdge(1, 4);
        small.addEdge(2, 5);
        small.addEdge(3, 6);
        small.addEdge(4, 7);
        small.addEdge(5, 8);
        small.addEdge(6, 9);

        small.printGraph();
        System.out.println();

        Experiment exp = new Experiment();

        System.out.println("-- Traversals on small graph --");
        exp.runTraversals(small);

        System.out.println("\n====== MEDIUM GRAPH (30 vertices) ======");
        Graph medium = new Graph();
        for (int i = 0; i < 30; i++) {
            medium.addVertex(new Vertex(i));
        }
        for (int i = 0; i < 29; i++) {
            medium.addEdge(i, i + 1);
        }
        for (int i = 0; i < 27; i += 3) {
            medium.addEdge(i, i + 3);
        }
        System.out.println("-- Traversals on medium graph --");
        exp.runTraversals(medium);

        System.out.println("\n====== LARGE GRAPH (100 vertices) ======");
        Graph large = new Graph();
        for (int i = 0; i < 100; i++) {
            large.addVertex(new Vertex(i));
        }
        for (int i = 0; i < 99; i++) {
            large.addEdge(i, i + 1);
        }
        for (int i = 0; i < 95; i += 5) {
            large.addEdge(i, i + 5);
        }
        System.out.println("-- Traversals on large graph (no node output) --");

        long s, e;
        s = System.nanoTime();
        large.bfs(0);
        e = System.nanoTime();
        System.out.println("  BFS time: " + (e - s) + " ns");

        s = System.nanoTime();
        large.dfs(0);
        e = System.nanoTime();
        System.out.println("  DFS time: " + (e - s) + " ns");

        exp.runMultipleTests();

        System.out.println("\n====== BONUS: Dijkstra's Shortest Path ======");
        Graph weighted = new Graph();
        for (int i = 0; i < 6; i++) weighted.addVertex(new Vertex(i));

        weighted.addWeightedEdge(0, 1, 4);
        weighted.addWeightedEdge(0, 2, 1);
        weighted.addWeightedEdge(2, 1, 2);
        weighted.addWeightedEdge(1, 3, 1);
        weighted.addWeightedEdge(2, 3, 5);
        weighted.addWeightedEdge(3, 4, 3);
        weighted.addWeightedEdge(4, 5, 2);

        System.out.println("Weighted graph edges:");
        System.out.println("  0->1 (4), 0->2 (1), 2->1 (2), 1->3 (1), 2->3 (5), 3->4 (3), 4->5 (2)");
        System.out.println();

        weighted.dijkstra(0);
    }
}
