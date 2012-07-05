package list;

public class LockDList extends DList {
	
	protected DListNode newNode(Object item, DListNode prev, DListNode next) {
		return new LockDListNode(item, prev, next);
		  }		
		
	public void lockNode(DListNode node) {
		((LockDListNode)node).Lock = true;
	}
		  
	 public void remove(DListNode node) {
		    // Your solution here.
			  if (((LockDListNode)node).Lock == false) {
				  super.remove(node);
		  }
	 }

	 public static void main(String[] args) {
		    // DO NOT CHANGE THE FOLLOWING CODE.
		     
		      LockDList l = new LockDList();

		      l.insertFront("9");
		      l.insertFront("8");
		      l.insertBack("7");

		      l.lockNode( l.next(l.front()) );
		      System.out.println("\nsecond element of [8 9 7] is " + "now locked");

		      System.out.println("\nTrying to remove locked node:");
		      l.remove( l.next(l.front()));
		      System.out.println("\nThe list is still [8 9 7] " + l);

		      System.out.println("\nNow trying to remove 8 from the list");
		      l.remove(l.front());
		      System.out.println("\nThe list is now [9 7] " + l);
		      
		    }
	 
}

