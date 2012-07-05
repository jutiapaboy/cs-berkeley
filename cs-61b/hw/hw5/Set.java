/* Set.java */

import list.*;

/**
 *  A Set is a collection of Comparable elements stored in sorted order.
 *  Duplicate elements are not permitted in a Set.
 **/
public class Set {
  /* Fill in the data fields here. */
	
	protected List mySet;

  /**
   * Set ADT invariants:
   *  1)  The Set's elements must be precisely the elements of the List.
   *  2)  The List must always contain Comparable elements, and those elements 
   *      must always be sorted in ascending order.
   *  3)  No two elements in the List may be equals().
   **/

  /**
   *  Constructs an empty Set. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public Set() { 
    // Your solution here.
	  mySet = new DList();
  }

  /**
   *  cardinality() returns the number of elements in this Set.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int cardinality() {
    // Replace the following line with your solution.
    return mySet.length();
  }

  /**
   *  insert() inserts a Comparable element into this Set.
   *
   *  Sets are maintained in sorted order.  The ordering is specified by the
   *  compareTo() method of the java.lang.Comparable interface.
   *
   *  Performance:  runs in O(this.cardinality()) time.
   **/
  public void insert(Comparable c) {
    // Your solution here.
	  if (cardinality() == 0) {
		  mySet.insertFront(c);
	  } else {
		  try {
			  ListNode Front = mySet.front();
			  if (c.compareTo(Front.item()) < 0) {
				  Front.insertBefore(c); }
			  if (c.compareTo(Front.item()) == 0) { }

			  while (c.compareTo(Front.item()) > 0) {
				  Front = Front.next(); }
				  if (c.compareTo(Front.item()) == 0) {}
				  if (c.compareTo(Front.item()) > 0) {
					  Front.insertBefore(c); 
					   } }
			catch (InvalidNodeException e) {
			mySet.insertBack(c);
		} }
  }

  /**
   *  union() modifies this Set so that it contains all the elements it
   *  started with, plus all the elements of s.  The Set s is NOT modified.
   *  Make sure that duplicate elements are not created.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Your implementation should NOT copy elements of s or "this", though it
   *  will copy _references_ to the elements of s.  Your implementation will
   *  create new _nodes_ for the elements of s that are added to "this", but
   *  you should reuse the nodes that are already part of "this".
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
   **/
  public void union(Set s) {
    // Your solution here.
	  ListNode sBack = s.mySet.back();
	  ListNode sFront = s.mySet.front();
	  ListNode myFront = mySet.front();
	  ListNode myBack = mySet.back();
	  
	  try {
	  while (true) {
		  try {
			while (((Comparable) myFront.item()).compareTo(sFront.item()) < 0) {
				  myFront = myFront.next();
			  }
		  if (((Comparable) myFront.item()).compareTo(sFront.item()) > 0)  {
			  myFront.insertBefore(sFront.item());
		  }
		  sFront = sFront.next();
		} catch (InvalidNodeException e) {
			while (true) {
					mySet.insertBack(sFront.item());
					sFront = sFront.next();
			} } } 
	  } catch (InvalidNodeException e) {
	  }
  }

  /**
   *  intersect() modifies this Set so that it contains the intersection of
   *  its own elements and the elements of s.  The Set s is NOT modified.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Do not construct any new ListNodes during the execution of intersect.
   *  Reuse the nodes of "this" that will be in the intersection.
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT CONSTRUCT ANY NEW NODES.
   *  DO NOT ATTEMPT TO COPY ELEMENTS.
   **/
  public void intersect(Set s) {
    // Your solution here.

	  ListNode sBack = s.mySet.back();
	  ListNode myBack = mySet.back();
	  ListNode sFront = s.mySet.front();
	  ListNode myFront = mySet.front();

	  try {
		  while (true) {
			  try {
				while (((Comparable) myFront.item()).compareTo(sFront.item()) < 0) {
					myFront = myFront.next();
					myFront.prev().remove();
				  }
			  if (((Comparable) myFront.item()).compareTo(sFront.item()) == 0)  {
				  myFront = myFront.next();
			  }
			  sFront = sFront.next();
			} catch (InvalidNodeException e) {
				while (true) {
						myFront = myFront.next();
						myFront.prev().remove();
				} } } 
		  } catch (InvalidNodeException e) {
		  }
	  }

  /**
   *  toString() returns a String representation of this Set.  The String must
   *  have the following format:
   *    {  } for an empty Set.  No spaces before "{" or after "}"; two spaces
   *            between them.
   *    {  1  2  3  } for a Set of three Integer elements.  No spaces before
   *            "{" or after "}"; two spaces before and after each element.
   *            Elements are printed with their own toString method, whatever
   *            that may be.  The elements must appear in sorted order, from
   *            lowest to highest according to the compareTo() method.
   *
   *  WARNING:  THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
   *            FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS.  ANY
   *            DEVIATIONS WILL LOSE POINTS.
   **/
  public String toString() {
    // Replace the following line with your solution.
    String result = "{ ";
    ListNode current;
	try {
		current = mySet.front();
    while (current.isValidNode()) {
      result = result + " " + current.item() + " ";
      current = current.next();
    }
	} catch (InvalidNodeException e) {
		e.printStackTrace();
	}
    return result + " }";
  }

  public static void main(String[] argv) {
    Set s = new Set();
    s.insert(new Integer(3));
    s.insert(new Integer(4));
    s.insert(new Integer(3));
    System.out.println("Set s = " + s);

    Set s2 = new Set();
    s2.insert(new Integer(4));
    s2.insert(new Integer(5));
    s2.insert(new Integer(5));
    System.out.println("Set s2 = " + s2);

    Set s3 = new Set();
    s3.insert(new Integer(5));
    s3.insert(new Integer(3));
    s3.insert(new Integer(8));
    System.out.println("Set s3 = " + s3);
    
    Set s4 = new Set();
    s4.insert(new Integer(7));
    s4.insert(new Integer(11));
    s4.insert(new Integer(12));
    System.out.println("Set s4 = " + s4);

    s.union(s2);
    System.out.println("After s.union(s2), s = " + s);
    
    s2.union(s3);
    System.out.println("After s2.union(s3), s2 = " + s2);
    
    s2.union(s4);
    System.out.println("After s2.union(s4), s2 = " + s2);

    s.intersect(s3);
    System.out.println("After s.intersect(s3), s = " + s);

    System.out.println("s.cardinality() = " + s.cardinality());
    // You may want to add more (ungraded) test code here.
  }
}
