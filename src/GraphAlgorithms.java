import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.List;

/**
 * Your implementation of Prim's algorithm.
 */
public class GraphAlgorithms {

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * You may assume that there will only be one valid MST that can be formed.
     * You should NOT allow self-loops or parallel edges in the MST.
     * You may import/use java.util.PriorityQueue, java.util.Set, and any
     * class that implements the aforementioned interface.
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     * The only instance of java.util.Map that you may use is the adjacency
     * list from graph. DO NOT create new instances of Map for this method
     * (storing the adjacency list in a variable is fine).
     * You may assume that the passed in start vertex and graph will not be null.
     * You may assume that the start vertex exists in the graph.
     *
     * @param <T>   The generic typing of the data.
     * @param start The vertex to begin Prims on.
     * @param graph The graph we are applying Prims to.
     * @return The MST of the graph or null if there is no valid MST.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        Set<Vertex<T>> visitedNodes = new HashSet<>(); // Store visited nodes
        Set<Edge<T>> result = new HashSet<>(); // Store the edges of our tree
        PriorityQueue<Edge<T>> queue = new PriorityQueue<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjacency = graph.getAdjList();
        for (VertexDistance<T> neighbor : adjacency.get(start)) {
            if (start != neighbor.getVertex()) { // Check that the edge doesn't point to itself
                queue.add(new Edge<>(start, neighbor.getVertex(), neighbor.getDistance()));
            }
        }
        visitedNodes.add(start);
        Edge<T> currentEdge;
        while (!queue.isEmpty() && visitedNodes.size() != graph.getVertices().size()) {
            currentEdge = queue.poll();
            if (!visitedNodes.contains(currentEdge.getV())) {
                visitedNodes.add(currentEdge.getV()); // Mark nodes off as we visit them
                result.add(currentEdge); // Save the edges for both directions
                result.add(new Edge<>(currentEdge.getV(), currentEdge.getU(), currentEdge.getWeight()));
                for (VertexDistance<T> neighbor : adjacency.get(currentEdge.getV())) { // With the new vertex added we have probably cut other edges
                    if (!visitedNodes.contains(neighbor.getVertex())) { // If these cut edges are between non-visited nodes, enqueue them
                        if (currentEdge.getV() != neighbor.getVertex()) { // Check that the edge doesn't point to itself
                            queue.add(new Edge<>(currentEdge.getV(), neighbor.getVertex(), neighbor.getDistance()));
                        }
                    }
                }
            }
        }
        return result;
    }
}