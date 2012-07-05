/* Kruskal.java */

import java.util.Hashtable;
import graph.*;
import set.*;

import list.*;

import java.util.Arrays;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {
	
  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   */
	public static WUGraph minSpanTree(WUGraph g) {
		WUGraph minTree = new WUGraph(); // creates new WUGraph reference t to empty WUGrap
		Hashtable edgesHash = new Hashtable();
		Hashtable verticesHash = new Hashtable();
		
		Object[] vertices = g.getVertices();
		int numEdges = g.edgeCount();
		int numVertices = vertices.length;
		for (int i = 0; i < numVertices; i++) {
			Object v = vertices[i];
			minTree.addVertex(v);
			verticesHash.put(v, i);
		}

		Object[] edgesArray = new Object[numEdges];
		int count = 0;
		for(int i = 0; i < numVertices; i++) {
			Object v = vertices[i];
			Neighbors n = g.getNeighbors(v);
			Object[] neighbors = n.neighborList;
			int[] weights = n.weightList;
			for (int j = 0; j < neighbors.length; j++) {
				Object u = neighbors[j];
				KruskalEdge vu = new KruskalEdge(weights[j], v, u);
				if (!edgesHash.containsKey(vu)) {
					edgesArray[count] = vu;
					count++;
					edgesHash.put(vu, true);
				}
			}
		}

		Arrays.sort(edgesArray);
		
		DisjointSets edgesSet = new DisjointSets(numVertices);
		for (int i = 0; i < numEdges-1; i++) {
			KruskalEdge e = (KruskalEdge) edgesArray[i];
			Object v1 = e.object1;
			Object v2 = e.object2;
			int num1 = (Integer) verticesHash.get(v1);
			int num2 = (Integer) verticesHash.get(v2);
			if (edgesSet.find(num1) != edgesSet.find(num2)) {
				edgesSet.union(num1, num2);
				minTree.addEdge(v1, v2, e.weight);
			}
		}
		return minTree;
	}

}

















