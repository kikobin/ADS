import java.util.*;

public class Graph {
    private Map<Integer, List<Integer>> adjList;
    private Map<Integer, List<int[]>> weightedAdjList;
    private Map<Integer, Vertex> vertices;

    public Graph() {
        adjList = new HashMap<>();
        weightedAdjList = new HashMap<>();
        vertices = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        vertices.put(v.getId(), v);
        adjList.putIfAbsent(v.getId(), new ArrayList<>());
        weightedAdjList.putIfAbsent(v.getId(), new ArrayList<>());
    }

    public void addEdge(int from, int to) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            System.out.println("Vertex not found, skipping edge " + from + "->" + to);
            return;
        }
        adjList.get(from).add(to);
    }

    public void addWeightedEdge(int from, int to, int weight) {
        if (!weightedAdjList.containsKey(from) || !weightedAdjList.containsKey(to)) {
            System.out.println("Vertex not found, skipping edge " + from + "->" + to);
            return;
        }
        weightedAdjList.get(from).add(new int[]{to, weight});
    }

    public void printGraph() {
        System.out.println("=== Graph Adjacency List ===");
        for (int id : adjList.keySet()) {
            System.out.print("Vertex " + id + " -> ");
            System.out.println(adjList.get(id));
        }
    }

    public void bfs(int start) {
        boolean[] visited = new boolean[Collections.max(adjList.keySet()) + 1];
        Queue<Integer> queue = new LinkedList<>();

        System.out.print("BFS from " + start + ": ");
        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print(current + " ");
            for (int neighbor : adjList.getOrDefault(current, new ArrayList<>())) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    public void dfs(int start) {
        boolean[] visited = new boolean[Collections.max(adjList.keySet()) + 1];
        System.out.print("DFS from " + start + ": ");
        dfsHelper(start, visited);
        System.out.println();
    }

    private void dfsHelper(int node, boolean[] visited) {
        visited[node] = true;
        System.out.print(node + " ");
        for (int neighbor : adjList.getOrDefault(node, new ArrayList<>())) {
            if (!visited[neighbor]) {
                dfsHelper(neighbor, visited);
            }
        }
    }

    public void dijkstra(int start) {
        int size = Collections.max(vertices.keySet()) + 1;
        int[] dist = new int[size];
        boolean[] visited = new boolean[size];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        for (int i = 0; i < vertices.size(); i++) {
            int u = -1;
            for (int v : vertices.keySet()) {
                if (!visited[v] && (u == -1 || dist[v] < dist[u])) {
                    u = v;
                }
            }

            if (u == -1 || dist[u] == Integer.MAX_VALUE) break;
            visited[u] = true;

            for (int[] edge : weightedAdjList.getOrDefault(u, new ArrayList<>())) {
                int neighbor = edge[0];
                int weight = edge[1];
                if (dist[u] + weight < dist[neighbor]) {
                    dist[neighbor] = dist[u] + weight;
                }
            }
        }

        System.out.println("=== Dijkstra's Shortest Paths from Vertex " + start + " ===");
        List<Integer> sorted = new ArrayList<>(vertices.keySet());
        Collections.sort(sorted);
        for (int v : sorted) {
            if (dist[v] == Integer.MAX_VALUE) {
                System.out.println("  Vertex " + v + ": unreachable");
            } else {
                System.out.println("  Vertex " + v + ": distance = " + dist[v]);
            }
        }
    }

    public Map<Integer, List<Integer>> getAdjList() {
        return adjList;
    }
}
