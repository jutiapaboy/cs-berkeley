/* WUGraph.java */

package graph;


import java.util.Hashtable;
import list.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
	
	private int numEdges;    
	private DList verticesList;		// list of Vertex objects
	private Hashtable verticesHash;		// key = a single name of a vertex
													// value = a NodeListPair object (see NodeListPair.java)
	private Hashtable edgesHash;			// key = a pair of names of vertices
													// value = an Edge object (see Edge.java)

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph() {
	  numEdges = 0;
	  verticesList = new DList();
	  verticesHash = new Hashtable();
	  edgesHash = new Hashtable();
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount() {
	  return verticesList.length();
  }

  /**
   * edgeCount() returns the number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount() {
	  return numEdges;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
	  int length = vertexCount();
	  Object[] verticesArray = new Object[length];
	  DListNode current = (DListNode) verticesList.front();
	  int i = 0;
	  try {
		  while (current.isValidNode()) {
			  Vertex v = (Vertex) current.item();
			  verticesArray[i] = v.name();
			  i++;
			  current = (DListNode) current.next();
		  }
	  } catch (InvalidNodeException e) {
		  // finished looping through list of vertices
	  }
	  return verticesArray;
  }
	  

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.  The
   * vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex) {
	  if (verticesHash.get(vertex) != null) {
		  // already a vertex (has been inserted into verticesHash)
		  return;
	  }
	  Vertex v = new Vertex(vertex);
	  DListNode node = verticesList.insertReturn(v);
	  NodeListPair nlp = new NodeListPair(node, v.adjList());
	  verticesHash.put(vertex, nlp);
  }
	  
  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
	  if (verticesHash.get(vertex) == null) {
		  // not a vertex (not in verticesHash)
		  return;
	  }
	  NodeListPair nlp = (NodeListPair) verticesHash.remove(vertex);
	  DListNode node = nlp.me();
	  DList adj = nlp.adjList();
	  int d = adj.length();
	  DListNode current = (DListNode) adj.front();
	  try {
		  while (current.isValidNode()) {
			  Object u = ((Vertex) current.item()).name();
			  current = (DListNode) current.next();
			  VertexPair vu = new VertexPair(vertex, u);
			  Edge e = (Edge) edgesHash.remove(vu);
			  DListNode n1 = e.node1;
			  DListNode n2 = e.node2;
			  n1.remove();
			  if (!(n1.equals(n2))) {
				  n2.remove();
			  }
		  }
	  } catch (InvalidNodeException e) {
		  // this should never happen
	  }
	  try {
		  node.remove();
		  // remove the vertex from the list of vertices
	  } catch (InvalidNodeException e) {
	  }
	  numEdges -= d;
  }
		  
  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex) {
	  return verticesHash.containsKey(vertex);
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex) {
	  NodeListPair nlp = (NodeListPair) verticesHash.get(vertex);
	  if (nlp == null) {
		  return 0;
	  } else {
		  DList adj = nlp.adjList();
		  return adj.length();
	  }
  }
	  

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex) {
	  NodeListPair nlp = (NodeListPair) verticesHash.get(vertex);
	  if (nlp == null || degree(vertex) == 0) {
		  return null;
	  } else {
		  int len = degree(vertex);
		  Neighbors neighbors = new Neighbors();
		  Object[] nList = new Object[len];
		  int[] wList = new int[len];
		  int i = 0;
		  DList adj = nlp.adjList();
		  DListNode current = (DListNode) adj.front();
		  try {
			  while (current.isValidNode()) {
				  Object u = ((Vertex) current.item()).name();
				  nList[i] = u;
				  VertexPair vu = new VertexPair(vertex, u);
				  Edge e = (Edge) edgesHash.get(vu);
				  wList[i] = e.weight;
				  current = (DListNode) current.next();
				  i++;
			  }
		  } catch (InvalidNodeException e) {
			  // this should never happen
		  }
		  neighbors.neighborList = nList;
		  neighbors.weightList = wList;
		  return neighbors;
	  }
  }
	  
  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the edge is already
   * contained in the graph, the weight is updated to reflect the new value.
   * Self-edges (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight) {
	  NodeListPair nlpU, nlpV;
	  nlpU = (NodeListPair) verticesHash.get(u);
	  nlpV = (NodeListPair) verticesHash.get(v);
	  Vertex U;
	  Vertex V;
	  VertexPair uv = new VertexPair(u,v);
	  try {
		  U = (Vertex) nlpU.me().item();
		  V = (Vertex) nlpV.me().item();
		  Edge e = (Edge) edgesHash.get(uv);
		  if (e == null) {
			  DListNode node1 = U.addEdge(V);
			  DListNode node2 = node1;		// in the case that U == V
			  if (!u.equals(v)) {
				  node2 = V.addEdge(U);
			  }
			  e = new Edge(weight, node1, node2);
			  numEdges++;
		  } else {
			  e.weight = weight;
			  // keep the previous nodes
		  }
		  edgesHash.put(uv, e);
	  } catch (InvalidNodeException e) {
		  // this should never happen
	  }
  }
	  
  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v) {
	  VertexPair uv = new VertexPair(u,v);
	  Edge e = (Edge) edgesHash.remove(uv);
	  if (e != null) {
		  try {
			  e.node1.remove();
		  } catch (InvalidNodeException e1) {
		  }
		  try {
			  e.node2.remove();
		  } catch (InvalidNodeException e2) {
			  // removed a self-edge
		  }
		  numEdges--;
	  }
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) {
	  VertexPair uv = new VertexPair(u,v);
	  return edgesHash.containsKey(uv);
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but
   * also more annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
	  VertexPair uv = new VertexPair(u, v);
	  Edge e = (Edge) edgesHash.get(uv);
	  if (e == null) {
		  return 0;
	  } else {
		  return e.weight;
	  }
  }

}