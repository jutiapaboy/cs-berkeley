/* DList1.java */

/**
 *  A DList1 is a mutable doubly-linked list.  (No sentinel, not
 *  circularly linked.)
 */

public class DList1 {

  /**
   *  head references the first node.
   *  tail references the last node.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  protected DListNode1 head;
  protected DListNode1 tail;
  protected int size;

  /* DList1 invariants:
   *  1)  head.prev == null.
   *  2)  tail.next == null.
   *  3)  For any DListNode1 x in a DList, if x.next == y and x.next != null,
   *      then y.prev == x.
   *  4)  For any DListNode1 x in a DList, if x.prev == y and x.prev != null,
   *      then y.next == x.
   *  5)  The tail can be accessed from the head by a sequence of "next"
   *      references.
   *  6)  size is the number of DListNode1s that can be accessed from the
   *      head by a sequence of "next" references.
   */

  /**
   *  DList1() constructor for an empty DList1.
   */
  public DList1() {
    head = null;
    tail = null;
    size = 0;
  }

  /**
   *  DList1() constructor for a one-node DList1.
   */
  public DList1(int a, int b) {
    head = new DListNode1(a, b);
    tail = head;
    head.item1 = a;
    head.item2 = b;
    size = 1;
  }

  /**
   *  DList1() constructor for a two-node DList1.
   */
  public DList1(int a, int b, int c, int d) {
    head = new DListNode1();
    head.item1 = a;
    head.item2 = b;
    tail = new DListNode1();
    tail.item1 = c;
    tail.item2 = d;
    head.next = tail;
    tail.prev = head;
    size = 2;
  }

  /**
   *  insertFront() inserts an item at the front of a DList1.
   */
  public void insertFront(int i, int j) {
    // Your solution here.
	  DListNode1 node = new DListNode1(i, j);
	  if (size == 0) {
		  head = node;
		  tail = node;
	  } else {
		  if (size == 1) {
			 head = node;
			 head.next = tail;
			 tail.prev = node;
		  } else {
			  DListNode1 old = head;
			  head = node;
			  head.next = old;
			  old.prev = head;
		  }
	  }
	  size++;
  }
  
  public void insertEnd(int i, int j) {
	  DListNode1 node = new DListNode1(i, j);
	  if (size == 0) {
		  head = node;
		  tail = node;
		  } else {
			  if (size == 1) {
				  tail = node;
				  tail.prev = head;
				  head.next = tail;
		    } else {
		    	DListNode1 old = tail;
		    	tail = node;
		    	tail.prev = old;
		    	old.next = tail;
		    }
		  }
	  size++;
	  }

  /**
   *  removeFront() removes the first item (and node) from a DList1.  If the
   *  list is empty, do nothing.
   */
  public void removeFront() {
    // Your solution here.
	  if (size != 0 && size != 1) {
		  DListNode1 node = head.next;
		  head = node;
		  head.prev = null;
		  size--;
	  } else {
		  head = null;
		  tail = null;
		  size = 0;
	  }
  }
  
  
  public void MushWith(DListNode1 node, int species) {
	  //This method is supposed to take two nodes and mush them into one.
		}

  public void BreakAndInsertAt(int position, int species) {
	  //This method is supposed to break the DList and magically insert things correctly.
	    }
  
  public DListNode1 NTHnode(int position) {
	    DListNode1 currentNode;
	    if ((position < 1) || (head == null)) {
	      return new DListNode1();
	    } else {
	      currentNode = head;
	      while (position > 1) {
	        currentNode = currentNode.next;
	        if (currentNode == null) {
	          return new DListNode1();
	        }
	        position--;
	      }
	      return currentNode;
	    }
	  } 
  
  public int nth1(int position) {
	    DListNode1 currentNode;
	    if ((position < 1) || (head == null)) {
	      return 0;
	    } else {
	      currentNode = head;
	      while (position > 1) {
	        currentNode = currentNode.next;
	        if (currentNode == null) {
	          return 0;
	        }
	        position--;
	      }
	      return currentNode.item1;
	    }
	  } 

  public int nth2(int position) {
	    DListNode1 currentNode;
	    if ((position < 1) || (head == null)) {
	      return 0;
	    } else {
	      currentNode = head;
	      while (position > 1) {
	        currentNode = currentNode.next;
	        if (currentNode == null) {
	          return 0;
	        }
	        position--;
	      }
	      return currentNode.item2;
	    }
	  } 
  
  /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
   */
  public String toString() {
    String result = "[  ";
    DListNode1 current = head;
    while (current != null) {
      result = result + "( " + current.item1 + " " + current.item2 + " ) ";
      current = current.next;
    }
    return result + "]";
  }

  }
  


 

