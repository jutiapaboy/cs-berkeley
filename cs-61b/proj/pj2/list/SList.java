package list;
import player.Pair;

/* SList.java */

/**
 *  The SList class is a singly-linked implementation of the linked list
 *  abstraction.  SLists are mutable data structures, which can grow at either
 *  end.
 *
 *  @author Kathy Yelick and Jonathan Shewchuk
 **/

public class SList {

	private SListNode head;
	private int size;

	/**
	 *  SList() constructs an empty list.
	 **/
	public SList() {
		size = 0;
		head = null;
	}
	
	public SList(Object obj) {
		size = 1;
		head = new SListNode(obj);
	}

	/**
	 *  isEmpty() indicates whether the list is empty.
	 *  @return true if the list is empty, false otherwise.
	 **/
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 *  length() returns the length of this list.
	 *  @return the length of this list.
	 **/
	public int length() {
		return size;
	}

	/**
	 *  insertFront() inserts item "obj" at the beginning of this list.
	 *  @param obj the item to be inserted.
	 **/
	public void insertFront(Object obj) {
		head = new SListNode(obj, head);
		size++;
	}

	/**
	 *  insertEnd() inserts item "obj" at the end of this list.
	 *  @param obj the item to be inserted.
	 **/
	public void insertEnd(Object obj) {
		if (head == null) {
			head = new SListNode(obj);
		} else {
			SListNode node = head;
			while (node.next != null) {
				node = node.next;
			}
			node.next = new SListNode(obj);
		}
		size++;
	}

	/**
	 *  nth() returns the item at the specified position.  If position < 1 or
	 *  position > this.length(), null is returned.  Otherwise, the item at
	 *  position "position" is returned.  The list does not change.
	 *  @param position the desired position, from 1 to length(), in the list.
	 *  @return the item at the given position in the list.
	 **/
	public Object nth(int position) {
		SListNode currentNode;

		if ((position < 1) || (head == null)) {
			return null;
		} else {
			currentNode = head;
			while (position > 1) {
				currentNode = currentNode.next;
				if (currentNode == null) {
					return null;
				}
				position--;
			}
			return currentNode.item;
		}
	}

	/**
	 *  remove() removes the item at the specified position.  If position < 1 or
	 *  position > this.length(), nothing is done.  Otherwise, the item at
	 *  position "position" is removed, and the list size is reduced by 1.
	 *  @param position the desired position, from 1 to length(), in the list.
	 **/
	public void remove(int position) {
		SListNode currentNode;
		if (position < 0 || position > this.length()) {
			return;
		}
		if (position == 1) {
			head = head.next;
		} else {
			currentNode = head;
			while (position > 2) {
				currentNode = currentNode.next;
				if (currentNode == null) {
					return;
				}
				position--;
			}
			currentNode.next = currentNode.next.next;
		}
		size--;
	}

	/**
	 * has() checks if the given object is in the SList
	 * @param obj the item to be found
	 */
	public boolean has(Object obj) {
		SListNode currentNode = head;
		while (currentNode != null) {
			if (currentNode.item.equals(obj)) {
				return true;
			}
			currentNode = currentNode.next;
		}
		return false;
	}

	/**
	 * pop() pops the first item off the list
	 * @return the popped item
	 */
	public Object pop() {
		SListNode old = head;
		head = head.next;
		size--;
		return old.item;
	}
	
	public SList copy() {
		SList list = new SList();
		for (int k = length(); k > 0; k--) {
			Object n = nth(k);
			list.insertFront(n);
		}
		return list;
	}

	/**
	 *  toString() converts the list to a String.
	 *  @return a String representation of the list.
	 **/
	public String toString() {
		//int i;
		Object obj;
		String result = "[ ";

		SListNode cur = head;

		while (cur != null) {
			obj = cur.item;
			result = result + obj.toString() + " ";
			cur = cur.next;
		}
		result = result + "]";
		return result;
	}


	/**
	 *  main() runs test cases on the SList class.  Prints summary
	 *  information on basic operations and halts with an error (and a stack
	 *  trace) if any of the tests fail.
	 **/
	public static void main (String[] args) {
		/*
	  int[][] emp = new int[0][0];
	  System.out.println("empty array has " + emp[0][0]);
		 */
		
		SList ls = new SList();
		Pair p1 = new Pair(1, 3, 1);
		Pair p2 = new Pair(2, 3, 1);
		Pair p3 = new Pair(4, 5, 2);
		Pair p4 = new Pair(1, 3, 1);
		Pair p5 = new Pair(3, 4, 1);
		Pair p6 = new Pair(2, 3, 1);
		
		ls.insertFront(p1);
		ls.insertFront(p2);
		ls.insertFront(p3);
		System.out.println(ls + " has " + p4 + ": " + ls.has(p4));
		System.out.println(ls + " has " + p5 + ": " + ls.has(p5));
		System.out.println(ls + " has " + p6 + ": " + ls.has(p6));
		
		//System.out.println(ls.copy());
		
		testEmpty();
		testAfterInsertFront();
		testAfterInsertEnd();
	}


	/**
	 *  testEmpty() tests toString(), isEmpty(), length(), insertFront(), and
	 *  insertEnd() on an empty list.  Prints summary information of the tests
	 *  and halts the program if errors are detected.
	 **/
	private static void testEmpty() {
		SList lst1 = new SList();
		SList lst2 = new SList();
		System.out.println();
		System.out.println("Here is a list after construction: "
				+ lst1.toString());

		System.out.println("isEmpty() should be true. It is: " +
				lst1.isEmpty());

		System.out.println("length() should be 0. It is: " +
				lst1.length());
		lst1.insertFront(new Integer(3));
		System.out.println("Here is a list after insertFront(3) to an empty list: "
				+ lst1.toString());
		lst2.insertEnd(new Integer(5));
		System.out.println("Here is a list after insertEnd(5) on an empty list: "
				+ lst2.toString());
	}

	/**
	 *  testAfterInsertFront() tests toString(), isEmpty(), length(),
	 *  insertFront(), and insertEnd() after insertFront().  Prints summary
	 *  information of the tests and halts the program if errors are detected.
	 **/
	private static void testAfterInsertFront() {
		SList lst1 = new SList();
		lst1.insertFront(new Integer(3));
		lst1.insertFront(new Integer(2));
		lst1.insertFront(new Integer(1));
		System.out.println();
		System.out.println("Here is a list after insertFront 3, 2, 1: "
				+ lst1.toString());
		System.out.println("isEmpty() should be false. It is: " +
				lst1.isEmpty());
		System.out.println("length() should be 3. It is: " +
				lst1.length());
		lst1.insertEnd(new Integer(4));
		System.out.println("Here is the same list after insertEnd(4): "
				+ lst1.toString());
	}

	/**
	 *  testAfterInsertEnd() tests toString(), isEmpty(), length(),
	 *  insertFront(), and insertEnd() after insertEnd().  Prints summary
	 *  information of the tests and halts the program if errors are detected.
	 **/
	private static void testAfterInsertEnd() {
		SList lst1 = new SList();
		lst1.insertEnd(new Integer(6));
		lst1.insertEnd(new Integer(7));
		System.out.println();
		System.out.println("Here is a list after insertEnd 6, 7: "
				+ lst1.toString());
		System.out.println("isEmpty() should be false. It is: " +
				lst1.isEmpty());
		System.out.println("length() should be 2. It is: " +
				lst1.length());
		lst1.insertFront(new Integer(5));
		System.out.println("Here is the same list after insertFront(5): "
				+ lst1.toString());
	}
}
