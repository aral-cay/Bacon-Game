/**
 * @author Aral Cay
 * @author Mehmet Can Yilmaz
 * Purpose: Handles the graph methods for the bacon game
 */

import java.util.*;

public class GraphLibrary {

    public static <V,E> Graph<V,E> bfs(Graph<V,E> g, V source){
        //creates a bfs tree
        Graph<V, E> bfsTree = new AdjacencyMapGraph<V, E>();
        bfsTree.insertVertex(source);

        //set "visited" and queue "queue" is defined
        Set<V> visited = new HashSet<V>();
        Queue<V> queue = new LinkedList<V>();

        queue.add(source);
        visited.add(source);

        //while the queue is not empty
        while(!queue.isEmpty()){

            //for each generic type in neighbors the bfs is performed

            V u = queue.remove();
            for (V v: g.outNeighbors(u)){
                if (!visited.contains(v)) {
                    visited.add(v);
                    queue.add(v);
                    bfsTree.insertVertex(v);
                    bfsTree.insertDirected(v, u, g.getLabel(u, v));
                }
            }
        }

        return bfsTree;
    }

    public static <V,E> List<V> getPath(Graph<V,E> tree, V v){
        //creates array list and generic type vertex
        ArrayList<V> shortestPath = new ArrayList<V>();
        V currentVertex = v;
        shortestPath.add(currentVertex);

        //while out degree is bigger than zero for ech u in out neighbors, path is tracked to find the shortest
        while (tree.outDegree(currentVertex) > 0){
            for (V u: tree.outNeighbors(currentVertex)){
                shortestPath.add(u);
                currentVertex = u;
            }
        }

        //returns the shortest path
        return shortestPath;
    }


    public static <V,E> Set<V> missingVertices(Graph<V,E> graph, Graph<V,E> subgraph){

        //creates set missing vertices
        Set<V> missingVertices = new HashSet<V>();

        //for each generic type v in vertices missing vertices are added
        for (V v: graph.vertices()){
            if (!subgraph.hasVertex(v)){
                missingVertices.add(v);
            }
        }

        // returns the set
        return missingVertices;
    }

    public static <V,E> double averageSeparation(Graph<V,E> tree, V root){

        // array
        int sum[] = {0};

        //returns the average separation using sum helper
        sumHelper(tree, root, 0, sum);

        return ((double)(sum[0]))/((double)tree.numVertices());

    }

    public static <V,E> void sumHelper(Graph<V,E> tree, V currentVertex, int pathDistance, int[] sum){

        sum[0] += pathDistance;

        //sum helper method updates the sum according to the vertex's separation
        if (tree.inDegree(currentVertex) > 0){
            for (V u: tree.inNeighbors(currentVertex)){
                sumHelper(tree, u, pathDistance+1, sum);
            }
        }
    }

    //tester
    public static void testFunctions(){
        Graph<String, Set<String>> testGraph = new AdjacencyMapGraph<String, Set<String>>();

        String kb = "Kevin Bacon";
        String a = "Alice";
        String c = "Charlie";
        String b = "Bob";
        String d = "Dartmouth";
        String n = "Nobody";
        String nf = "Nobody's Friend";

        Set<String> kba = new HashSet<String>();
        kba.add("A Movie");
        kba.add("E Movie");

        Set<String> kbb = new HashSet<String>();
        kbb.add("A Movie");

        Set<String> ab = new HashSet<String>();
        ab.add("A Movie");

        Set<String> ac = new HashSet<String>();
        ac.add("D Movie");

        Set<String> bc = new HashSet<String>();
        bc.add("C Movie");

        Set<String> cd = new HashSet<String>();
        cd.add("B Movie");

        Set<String> nnf = new HashSet<String>();
        nnf.add("F Movie");

        testGraph.insertVertex(kb);
        testGraph.insertVertex(a);
        testGraph.insertVertex(c);
        testGraph.insertVertex(b);
        testGraph.insertVertex(d);
        testGraph.insertVertex(n);
        testGraph.insertVertex(nf);

        testGraph.insertUndirected(kb, a, kba);
        testGraph.insertUndirected(kb, b, kbb);
        testGraph.insertUndirected(a, b, ab);
        testGraph.insertUndirected(a, c, ac);
        testGraph.insertUndirected(b, c, bc);
        testGraph.insertUndirected(c, d, cd);
        testGraph.insertUndirected(n, nf, nnf);

        System.out.println((testGraph));
        System.out.println(bfs(testGraph, kb));
        System.out.println(getPath(bfs(testGraph, kb), c));
        System.out.println(missingVertices(testGraph, bfs(testGraph, kb)));
        System.out.println(averageSeparation(bfs(testGraph, kb), kb));

    }

    //main function
    public static void main(String[] args){
        testFunctions();
    }

}