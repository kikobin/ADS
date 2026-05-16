import java.util.*;

public class Graph {
    // adjacency list: each vertex id maps to list of neighbor ids
    private Map<Integer, List<Integer>> adjList;
    private Map<Integer, Vertex> vertices;

    public Graph() {
        adjList = new HashMap<>();
        vertices = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        vertices.put(v.getId(), v);
        adjList.putIfAbsent(v.getId(), new ArrayList<>());
    }

    public void addEdge(int from, int to) {
        // make sure both vertices exist before adding edge
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            System.out.println("Vertex not found, skipping edge " + from + "->" + to);
            return;
        }
        adjList.get(from).add(to);
        // uncomment below for undirected graph
        // adjList.get(to).add(from);
    }

    public void printGraph() {
        System.out.println("=== Graph Adjacency List ===");
        for (int id : adjList.keySet()) {
            System.out.print("Vertex " + id + " -> ");
            System.out.println(adjList.get(id));
        }
    }

    // BFS - uses a queue, visits level by level
    public void bfs(int start) {
        boolean[] visited = new boolean[Collections.max(adjList.keySet()) + 1];
        Queue<Integer> queue = new LinkedList<>();

        System.out.print("BFS from " + start + ": ");

        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print(current + " ");

            // visit all neighbors of current node
            for (int neighbor : adjList.getOrDefault(current, new ArrayList<>())) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    // DFS - uses recursion/stack, goes deep before backtracking
    public void dfs(int start) {
        boolean[] visited = new boolean[Collections.max(adjList.keySet()) + 1];
        System.out.print("DFS from " + start + ": ");
        dfsHelper(start, visited);
        System.out.println();
    }

    private void dfsHelper(int node, boolean[] visited) {
        visited[node] = true;
        System.out.print(node + " ");

        // recursively visit all unvisited neighbors
        for (int neighbor : adjList.getOrDefault(node, new ArrayList<>())) {
            if (!visited[neighbor]) {
                dfsHelper(neighbor, visited);
            }
        }
    }

    public Map<Integer, List<Integer>> getAdjList() {
        return adjList;
    }
}
