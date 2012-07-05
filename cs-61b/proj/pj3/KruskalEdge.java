/* EdgeStructure.java */

public class KruskalEdge implements Comparable {

	protected int weight;
	protected Object object1;
	protected Object object2;

	public KruskalEdge(int w, Object v1, Object v2) {
		weight = w;
		object1 = v1;
		object2 = v2;
	}
	
	public int hashCode() {
		if (object1.equals(object2)) {
			return object1.hashCode() + 1;
		} else {
			return object1.hashCode() + object2.hashCode();
		}
	}
	
	public boolean equals(Object o) {
		if (o instanceof KruskalEdge) {
			return ((object1.equals(((KruskalEdge) o).object1)) &&
					(object2.equals(((KruskalEdge) o).object2))) ||
					((object1.equals(((KruskalEdge) o).object2)) &&
							(object2.equals(((KruskalEdge) o).object1)));
		} else {
			return false;
		}
	}

	public int compareTo(Object obj) {
		KruskalEdge e = (KruskalEdge) obj;		// assume we only compare Edges with Edges
		if (weight < e.weight) {
			return -1;
		} else if (weight == e.weight) {
			return 0;
		} else {
			return 1;
		}
	}
	
}