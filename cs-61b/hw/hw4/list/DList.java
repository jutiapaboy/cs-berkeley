/* DList.java */

package list;

/**
 *  A DList is a mutable doubly-linked list ADT.  Its implementation is
 *  circularly-linked and employs a sentinel (dummy) node at the head
 *  of the list.
 *
 *  DO NOT CHANGE ANY METHOD PROTOTYPES IN THIS FILE.
 */

public class DList {

  /**
   *  head references the sentinel node.
   *  size is the number of items in the list.  (The sentinel node does not
   *       store an item.)
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  protected DListNode head;
  protected int size;

  /* DList invariants:
   *  1)  head != null.
   *  2)  For any DListNode x in a DList, x.next != null.
   *  3)  For any DListNode x in a DList, x.prev != null.
   *  4)  For any DListNode x in a DList, if x.next == y, then y.prev == x.
   *  5)  For any DListNode x in a DList, if x.prev == y, then y.next == x.
   *  6)  size is the number of DListNodes, NOT COUNTING the sentinel,
   *      that can be accessed from the sentinel (head) by a sequence of
   *      "next" references.
   */

  /**
   *  newNode() calls the DListNode constructor.  Use this class to allocate
   *  new DListNodes rather than calling the DListNode constructor directly.
   *  That way, only this method needs to be overridden if a subclass of DList
   *  wants to use a different kind of node.
   *  @param item the item to store in the node.
   *  @param prev the node previous to this node.
   *  @param next the node following this node.
   */
  protected DListNode newNode(Object item, DListNode prev, DListNode next) {
    return new DListNode(item, prev, next);
  }

  /**
   *  DList() constructor for an empty DList.
   */
  public DList() {
    //  Your solution here.
	  head = newNode(Integer.MIN_VALUE, null, null);
	  head.next = head;
	  head.prev = head;
	  size = 0;
  }

  /**
   *  isEmpty() returns true if this DList is empty, false otherwise.
   *  @return true if this DList is empty, false otherwise. 
   *  Performance:  runs in O(1) time.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /** 
   *  length() returns the length of this DList. 
   *  @return the length of this DList.
   *  Performance:  runs in O(1) time.
   */
  public int length() {
    return size;
  }

  /**
   *  insertFront() inserts an item at the front of this DList.
   *  @param item is the item to be inserted.
   *  Performance:  runs in O(1) time.
   */
  public void insertFront(Object item) {
    // Your solution here.
      DListNode node = newNode(item, null, null);
      DListNode old = head.next;
      node.prev = head;
      head.next = node;
      node.next = old;
      old.prev = node;
      size ++;
  }

  /**
   *  insertBack() inserts an item at the back of this DList.
   *  @param item is the item to be inserted.
   *  Performance:  runs in O(1) time.
   */
  public void insertBack(Object item) {
    // Your solution here.
	  DListNode node = newNode(item, null, null);
	  DListNode old = head.prev;
	  node.prev = old;
	  node.next = head;
	  head.prev = node;
	  node.prev = old;
	  old.next = node;
	  size++;
  }

  /**
   *  front() returns the node at the front of this DList.  If the DList is
   *  empty, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @return the node at the front of this DList.
   *  Performance:  runs in O(1) time.
   */
  public DListNode front() {
    // Your solution here.
	  if (size == 0) {
		  return null;
	  } else {
		  return head.next;
	  }
  }

  /**
   *  back() returns the node at the back of this DList.  If the DList is
   *  empty, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @return the node at the back of this DList.
   *  Performance:  runs in O(1) time.
   */
  public DListNode back() {
    // Your solution here.
	  if (size == 0) {
		  return null;
	  } else {
		  return head.prev;
	  }
  }

  /**
   *  next() returns the node following "node" in this DList.  If "node" is
   *  null, or "node" is the last node in this DList, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @param node the node whose successor is sought.
   *  @return the node following "node".
   *  Performance:  runs in O(1) time.
   */
  public DListNode next(DListNode node) {
    // Your solution here.
	  if (node.next == head || node.item == null) {
		  return null;
	  } else {
		  return node.next;
	  }
  }

  /**
   *  prev() returns the node prior to "node" in this DList.  If "node" is
   *  null, or "node" is the first node in this DList, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @param node the node whose predecessor is sought.
   *  @return the node prior to "node".
   *  Performance:  runs in O(1) time.
   */
  public DListNode prev(DListNode node) {
    // Your solution here.
	  if (node.prev == head || node.item == null) {
		  return null;
	  } else {
		  return node.prev;
	  }
  }

  /**
   *  insertAfter() inserts an item in this DList immediately following "node".
   *  If "node" is null, do nothing.
   *  @param item the item to be inserted.
   *  @param node the node to insert the item after.
   *  Performance:  runs in O(1) time.
   */
  public void insertAfter(Object item, DListNode node) {
    // Your solution here.
	  DListNode after = newNode(item, null, null);
	  if (node == null) {
		  return;
	  } else {
		  DListNode old = node.next;
		  node.next = after;
		  after.prev = node;
		  after.next = old;
		  old.prev = after;
		  size++;
	  }
  }

  /**
   *  insertBefore() inserts an item in this DList immediately before "node".
   *  If "node" is null, do nothing.
   *  @param item the item to be inserted.
   *  @param node the node to insert the item before.
   *  Performance:  runs in O(1) time.
   */
  public void insertBefore(Object item, DListNode node) {
    // Your solution here.
	  DListNode after = newNode(item, null, null);
	  if (node == null) {
		  return;
	  } else {
		  DListNode old = node.prev;
		  node.prev = after;
		  after.next = node;
		  after.prev = old;
		  old.next = after;
		  size++;
	  }
  }

  /**
   *  remove() removes "node" from this DList.  If "node" is null, do nothing.
   *  Performance:  runs in O(1) time.
   */
  public void remove(DListNode node) {
    // Your solution here.
	  if (node == null) {
		  return;
	  } else {
		  if (size == 0 || size == 1) {
			  head.next = head;
			  head.prev = head;
			  size--;
		  } else {
			  DListNode before = node.prev;
			  DListNode after = node.next;
			  before.next = after;
			  after.prev = before;
			  size--;
		  }
	  }	  
  }

  /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
   *  Performance:  runs in O(n) time, where n is the length of the list.
   */
  public String toString() {
    String result = "[  ";
    DListNode current = head.next;
    while (current != head) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }

	public static void main(String[] args) {
	    // DO NOT CHANGE THE FOLLOWING CODE.
		
	      DList l = new DList();
	      System.out.println("### TESTING insertFront ###\nEmpty list is " + l);

	      System.out.println("\nEmpty is true: " + l.isEmpty());
	      System.out.println("length is 0: " + l.length());
	      System.out.println("front of empty list is null: " + l.front());
	      System.out.println("back of empty list is null: " + l.back());

	      l.insertFront("9");
	      System.out.println("\nInserting 9 at front.\nList with 9 is " + l);
	      System.out.println("length is : " + l.length());    

	      l.insertFront("8");
	      System.out.println("\nInserting 8 at front.\nList with 8 and 9 is " + l);
	      System.out.println("length is : " + l.length());   

	      l.insertBack("7");
	      System.out.println("\nInserting 7 at end.\nList with 8 9 7 is " + l);
	      System.out.println("length  is : " + l.length());  

	      System.out.println("front of [8 9 7] is " + l.front().item);
	      System.out.println("back of [8 9 7] is " + l.back().item);

	      DListNode second = l.next(l.front());
	      System.out.println("\nsecond element of [8 9 7] is " + second.item);

	      l.insertBefore("hello",second);
	      System.out.println("\nInserting hello before 9.\n[8 hello 9 7] = " + l);
	      System.out.println("length is : " + l.length());   

	      l.insertAfter("bye",second);
	      System.out.println("\nInserting bye after 9.\n[8 hello 9 bye 7] = " + l);
	      System.out.println("length is : " + l.length());   

	      System.out.println("\nEmpty is false: " + l.isEmpty());
	      System.out.println("list is " + l);
	      System.out.println("length is 4: " + l.length());      
	      
	    }
	}
