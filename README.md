# Assignment 4: Graph Traversal and Representation System

## A. Project Overview

This project implements a graph data structure and two classic traversal algorithms: BFS and DFS. A graph is made up of **vertices** (nodes) and **edges** (connections between nodes). In this project edges are directed, meaning they go one way from source to destination.

**BFS (Breadth-First Search)** visits nodes level by level using a queue. It explores all neighbors of a node before going deeper.

**DFS (Depth-First Search)** goes as deep as possible down one path before backtracking. It uses recursion (which internally uses the call stack).

---

## B. Class Descriptions

### Vertex
Represents a single node in the graph. Has a unique integer `id`. Includes constructor, getter, and `toString()`.

### Edge
Represents a directed connection between two vertices: `source -> destination`. Includes constructor, getters, and `toString()`.

### Graph
Stores the graph using an **adjacency list** — a `HashMap` where each vertex id maps to a list of its neighbor ids. This is memory-efficient for sparse graphs. Contains `addVertex`, `addEdge`, `printGraph`, `bfs`, and `dfs` methods.

**Why adjacency list?** For sparse graphs it uses O(V + E) space which is better than adjacency matrix O(V²).

### Experiment
Builds graphs of different sizes, runs BFS and DFS on them using `System.nanoTime()`, and prints a comparison table.

---

## C. Algorithm Descriptions

### BFS (Breadth-First Search)

**Step-by-step:**
1. Mark start node as visited, add to queue
2. While queue is not empty: dequeue front node, print it
3. For each unvisited neighbor: mark visited, enqueue
4. Repeat until queue empty

**Use cases:** Shortest path in unweighted graphs, level-order traversal, finding nearest neighbor

**Time complexity:** O(V + E) — visits every vertex and edge once

---

### DFS (Depth-First Search)

**Step-by-step:**
1. Mark current node as visited, print it
2. For each unvisited neighbor: recursively call DFS on it
3. Backtrack when no unvisited neighbors remain

**Use cases:** Cycle detection, topological sort, maze solving, finding connected components

**Time complexity:** O(V + E) — same as BFS theoretically

---

## D. Experimental Results

### Traversal Order (Small Graph — 10 vertices)

```
Graph structure:
Vertex 0 -> [1, 2]
Vertex 1 -> [3, 4]
Vertex 2 -> [5]
Vertex 3 -> [6]
Vertex 4 -> [7]
Vertex 5 -> [8]
Vertex 6 -> [9]

BFS from 0: 0 1 2 3 4 5 6 7 8 9
DFS from 0: 0 1 3 6 9 4 7 2 5 8
```

BFS visits level by level (0, then 1&2, then their children...). DFS dives deep into one branch first before exploring others.

### Execution Time Comparison

| Graph Size | BFS Time (ns) | DFS Time (ns) |
|------------|---------------|---------------|
| 10         | 39,691        | 7,949         |
| 30         | 29,649        | 15,596        |
| 100        | 1,399,985     | 248,820       |

### Observations

- DFS is consistently faster in practice due to lower overhead (no queue operations)
- Both scale linearly as expected for O(V + E)
- BFS uses more memory because it stores all nodes at current level in queue

---

## E. Screenshots

### Graph Structure Output
```
====== SMALL GRAPH (10 vertices) ======
=== Graph Adjacency List ===
Vertex 0 -> [1, 2]
Vertex 1 -> [3, 4]
Vertex 2 -> [5]
...
```

### BFS Traversal Output
```
BFS from 0: 0 1 2 3 4 5 6 7 8 9
BFS time: 2700391 ns
```

### DFS Traversal Output
```
DFS from 0: 0 1 3 6 9 4 7 2 5 8
DFS time: 2666408 ns
```

### Performance Results
```
Graph Size      BFS Time (ns)        DFS Time (ns)
------------------------------------------------------------
10              39691                7949
30              29649                15596
100             1399985              248820
```

---

## F. Reflection

Working on this assignment helped me understand how BFS and DFS differ not just in theory but in actual behavior. When I ran BFS on the small graph, it visited nodes level by level which made sense visually. DFS on the other hand immediately dove into one branch and only came back when it hit a dead end. This made it easier to understand why BFS is used for shortest path (it finds the closest nodes first) while DFS is better for exhaustive searching.

The biggest challenge was understanding why DFS was actually faster in my tests even though both are O(V + E). The reason is that BFS uses a queue which has overhead from constant enqueue and dequeue operations, while DFS just uses the call stack through recursion. This showed me that Big-O tells you the shape of growth but not the exact constant factors. Another challenge was handling the `visited` array size correctly to avoid ArrayIndexOutOfBounds errors.

---

## Analysis Questions

**How does graph size affect performance?**  
Both grow roughly linearly. BFS time jumped from ~40K ns to ~1.4M ns going from 10 to 100 vertices. DFS from ~8K to ~250K ns.

**Which traversal is faster?**  
DFS was faster in all tests, roughly 5x faster at 100 vertices.

**Do results match O(V + E)?**  
Yes, roughly. Both grow linearly with graph size which matches O(V + E) expectations.

**How does graph structure affect traversal order?**  
In a tree-like graph BFS gives level order, DFS gives pre-order path. In a dense graph with many connections the orders diverge more.

**When is BFS preferred?**  
When you need the shortest path, or when you expect the answer to be close to the starting node.

**Limitations of DFS?**  
Can cause stack overflow on very deep graphs due to recursion. Does not guarantee shortest path. Can get stuck exploring a very long wrong path before backtracking.
---

## G. Bonus Task: Dijkstra's Algorithm (Shortest Path)

### Overview

Dijkstra's algorithm finds the shortest path from a given starting vertex to **all other vertices** in a weighted directed graph. Unlike BFS (which works on unweighted graphs), Dijkstra considers edge weights and always picks the closest unvisited vertex next.

---

### Changes Made

#### `Edge.java`
Added a `weight` field with a new constructor `Edge(source, destination, weight)`. The original no-arg constructor is kept for backwards compatibility and defaults weight to 1.

#### `Graph.java`
- Added a second adjacency list: `Map<Integer, List<int[]>> weightedAdjList` where each `int[]` stores `{neighbor, weight}`.
- Added `addWeightedEdge(int from, int to, int weight)` to populate the weighted list.
- Added `dijkstra(int start)` — see algorithm below.

#### `Main.java`
Added a demo weighted graph with 6 vertices demonstrating Dijkstra from vertex 0.

---

### Algorithm: `void dijkstra(int start)`

**Step-by-step:**
1. Initialize `dist[]` array with `Integer.MAX_VALUE` (infinity) for all vertices; set `dist[start] = 0`
2. Use a `visited[]` boolean array to track finalized vertices
3. Repeat V times:
   - Find the unvisited vertex `u` with the smallest `dist[u]` (simple loop, no priority queue)
   - Mark `u` as visited
   - For each neighbor `v` of `u` with edge weight `w`: if `dist[u] + w < dist[v]`, update `dist[v]`
4. Print distances to all vertices

**Time complexity:** O(V²) — uses a simple minimum scan instead of a priority queue (as allowed by task requirements)

**Space complexity:** O(V) for the `dist[]` and `visited[]` arrays

---

### Example Output

Graph edges: `0->1 (4), 0->2 (1), 2->1 (2), 1->3 (1), 2->3 (5), 3->4 (3), 4->5 (2)`

```
=== Dijkstra's Shortest Paths from Vertex 0 ===
  Vertex 0: distance = 0
  Vertex 1: distance = 3    (0->2->1, cost 1+2=3, shorter than direct 0->1 cost 4)
  Vertex 2: distance = 1    (0->2)
  Vertex 3: distance = 4    (0->2->1->3, cost 1+2+1=4)
  Vertex 4: distance = 7    (0->2->1->3->4)
  Vertex 5: distance = 9    (0->2->1->3->4->5)
```

### Key Insight

Dijkstra's greedy strategy works because once a vertex is finalized (visited), we are guaranteed that no shorter path exists — provided all edge weights are non-negative.

# ADS
