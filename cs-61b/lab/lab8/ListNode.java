/* ListNode.java */

/**
 *  ListNode is a very simple headless list class, akin to cons cells in
 *  Scheme.  Each ListNode contains an item and a reference to the next node.
 **/
class ListNode {

  public Object item;
  public ListNode next;

  /**
   *  Constructs a ListNode with item i and next node n.
   *  @param i the item to store in the ListNode.
   *  @param n the next ListNode following this ListNode.
   **/
  ListNode(Object i, ListNode n) {
    item = i;
    next = n;
  }
  
  public String toString() {
	    String result = "[ ";
	    ListNode cur = this;
	    while (cur != null) {
	      result = result + cur.item + " ";
	      cur = cur.next;
	    }
	    return result + "]";

	  }
  
  //  public static void main (String[] args) {
  //	  ListNode l = new ListNode(1, new ListNode(3, new ListNode(4, null)));
  //	  System.out.println(l);
  //}
  
}
