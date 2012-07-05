/* LockDListNode.java */

package list;

class LockDListNode extends DListNode {

	protected boolean Lock;

	LockDListNode(Object i, DListNode prev, DListNode next) {
		super(i, prev, next);
	
  }
}
