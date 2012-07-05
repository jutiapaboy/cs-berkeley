/* Tree234.java */

package dict;

/**
 *  A Tree234 implements an ordered integer dictionary ADT using a 2-3-4 tree.
 *  Only int keys are stored; no object is associated with each key.  Duplicate
 *  keys are not stored in the tree.
 *
 *  @author Jonathan Shewchuk
 **/
public class Tree234 extends IntDictionary {

  /**
   *  (inherited)  size is the number of keys in the dictionary.
   *  root is the root of the 2-3-4 tree.
   *
   *  You may add fields if you wish, but don't change anything that
   *  would prevent toString() or find() from working correctly.
   **/
  Tree234Node root;

  public Tree234() {
    root = null;
    size = 0;
  }
 
  // I know, I know, ridiculously redundant and hard-to-read code. 
  // Apologies in advance! :'(
  // It is late, I am tired, and so I will now SLEEP
  
  
  public boolean isLeaf(Tree234Node node) {
	  Tree234Node c1 = node.child1;
	  Tree234Node c2 = node.child2;
	  Tree234Node c3 = node.child3;
	  Tree234Node c4= node.child4;
	  if (c1 == null && c2 == null && c3== null  && c4 == null) {
		  return true;
	  } else {
		  return false;
	  }
  }
  
  public Tree234Node getRoot(Tree234Node node) {
	  while (node.parent != null) {
		  node = node.parent;
	  }
	  return node;
  }
  
  public Tree234Node breakRoot(Tree234Node node, int key) {
	  int k1 = node.key1;
	  int k2 = node.key2;
	  int k3 = node.key3;
	  Tree234Node c1 = node.child1;
	  Tree234Node c2 = node.child2;
	  Tree234Node c3 = node.child3;
	  Tree234Node c4= node.child4;
	  Tree234Node breakNode = new Tree234Node(null, k2);
	  Tree234Node break1 = new Tree234Node(breakNode, k1);
	  Tree234Node break2 = new Tree234Node(breakNode, k3);
	  break1.child1 = c1;
	  break1.child2 = c2;
	  if (c1 != null && c2 != null) {
		  c1.parent = break1;
		  c2.parent = break1;
	  }
	  break2.child1 = c3;
	  break2.child2 = c4;
	  if (c3 != null && c4 != null) {
		  c3.parent = break2;
		  c4.parent = break2;
	  }
	  breakNode.child1 = break1;
	  breakNode.child2 = break2;
	  
	  if (key < k1) {
		  node = break1;
	  } else {
		  node = break2;
	  }
	  return node;
  }
  
  public Tree234Node breakNode(Tree234Node node, int key) {
	  int k1 = node.key1;
	  int k2 = node.key2;
	  int k3 = node.key3;
	  int p1 = node.parent.key1;
	  int p2 = node.parent.key2;
	  int p3 = node.parent.key3;
	  Tree234Node n1 = node.child1;
	  Tree234Node n2 = node.child2;
	  Tree234Node n3 = node.child3;
	  Tree234Node n4= node.child4;
	  Tree234Node c1 = node.parent.child1;
	  Tree234Node c2 = node.parent.child2;
	  Tree234Node c3 = node.parent.child3;
	  Tree234Node c4= node.parent.child4;
	  Tree234Node parent = node.parent;
	  Tree234Node break1 = new Tree234Node(node.parent, k1);
	  if (n1 != null && n1 != null) {
		  n1.parent = break1;
		  n2.parent = break1;
	  }
	  break1.child1 = n1;
	  break1.child2 = n2;
	  
	  Tree234Node break2 = new Tree234Node(node.parent, k3);
	  if (n3 != null && n4 != null ) {
		  n3.parent = break2;
		  n4.parent = break2;
	  }
	  break2.child1 = n3;
	  break2.child2 = n4;
	  
	  if (node == c1) {
		  node.parent.key1 = k2;
		  node.parent.key2 = p1;
		  node.parent.key3 = p2;
		  node.key2  = k3;
		  node.keys--;
		  node.parent.keys++;
		  node.parent.child1 = break1;
		  break1.child1 = n1;
		  break1.child2 = n2;
		  node.parent.child2 = break2;
		  node.parent.child3 = c2;
		  node.parent.child4 = c3;
		  if (key < k1) {
			  node = node.parent.child1;
			  return node;
		  } else if (n2 != null) {
			  if (key < n2.key1) {
				  node = n2;
				  return node;
			  }
		  } else {
			  node = node.parent.child2;
			  return node;
		  }
	  }
	  if (node == c2) {
		  node.parent.key2 = k2;
		  node.parent.key3 = p2;
		  node.key2  = k3;
		  node.keys--;
		  node.parent.keys++;
		  node.parent.child2 = break1;
		  node.parent.child3 = break2;
		  break2.child1 = n3;
		  break2.child2 = n4;
		  node.parent.child4 = c3;
		  if (key < k1) {
			  node = node.parent.child2;
			  return node;
		  } else if (n2 != null) {
			  if (key < n2.key1) {
				  node = n2;
				  return node;
			  }
		  } else {
			  node = node.parent.child3;
			  return node;
		  }
	  }
	  if (node == c3) {
		  node.parent.key3 = k2;
		  node.key2  = k3;
		  node.keys--;
		  node.parent.keys++;
		  node.parent.child3 = break1;
		  node.parent.child4 = break2;
		  if (key < k1) {
			  node = node.parent.child3;
			  return node;
		  } else if (n2 != null) {
			  if (key < n2.key1) {
				  node = n2;
				  return node;
			  }
		  } else {
			  node = node.parent.child4;
			  return node;
		  }
	  }
	return node;
  }

  public void insertRoot(Tree234Node node, int key) {
	  int k1 = node.key1;
	  int k2 = node.key2;
	  if (key < k1) {
		  node.key1 = key;
		  node.key2 = k1;
		  node.key3 = k2;
	  }
	  if (key < k2) {
		  node.key1 = k1;
		  node.key2 = key;
		  node.key3 = k2;
	  }
	  node.keys++;
	  size++;
	  root = node;
  }
  
  public void insertNode(Tree234Node node, int key) {
	  while (isLeaf(node) == false) {
		  if (node.keys == 3) {
			  if (node.parent == null) {
				  node = breakRoot(node, key);
			  } else {
			//	  System.out.println("BREAKING " + node);
				  node = breakNode(node, key);
			  }
		  }
	      if (key < node.key1) {
	          node = node.child1;
	        } else if ((node.keys == 1) || (key < node.key2)) {
	          node = node.child2;
	        } else if ((node.keys == 2) || (key < node.key3)) {
	          node = node.child3;
	        } else {
	          node = node.child4;
	        }
	  }
	  if (node.keys == 3) {
		  if (node.parent == null) {
			  node = breakRoot(node, key);
		  } else {
		//	  System.out.println("BREAKING " + node);
			  node = breakNode(node, key);
		  }
	  }
	//  System.out.println("NODE IS NOW " + node);
	  int k1 = node.key1;
	  int k2 = node.key2;
    // System.out.println("KEYS " + k1 + " " + k2);
	  if (key < k1) {
		//  System.out.println("INS 1");
		  node.key1 = key;
		  node.key2 = k1;
		  node.key3 = k2;
	  } else if (key < k2) {
	//	  System.out.println("INS 2");
		  node.key1 = k1;
		  node.key2 = key;
		  node.key3 = k2;
	  } else {
	//	  System.out.println("INS 3     " + key + "   " + node);
		  if (k2 == 0) {
			  node.key2 = key;
		  } else {
			  node.key3 = key;
		  }
	  }
	  node.keys++;
	  size++;
	 root = getRoot(node);
  }
	  

  /**
   *  toString() prints this Tree234 as a String.  Each node is printed
   *  in the form such as (for a 3-key node)
   *
   *      (child1)key1(child2)key2(child3)key3(child4)
   *
   *  where each child is a recursive call to toString, and null children
   *  are printed as a space with no parentheses.  Here's an example.
   *      ((1)7(11 16)22(23)28(37 49))50((60)84(86 95 100))
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of the 2-3-4 tree.
   **/
  public String toString() {
    if (root == null) {
      return "";
    } else {
      return root.toString();
    }
  }

  /**
   *  printTree() prints this Tree234 as a tree, albeit sideways.
   *
   *  You're welcome to change this method if you like.  It won't be tested.
   **/
  public void printTree() {
    if (root != null) {
      root.printSubtree(0);
    }
  }

  /**
   *  find() prints true if "key" is in this 2-3-4 tree; false otherwise.
   *
   *  @param key is the key sought.
   *  @return true if "key" is in the tree; false otherwise.
   **/
  public boolean find(int key) {
    Tree234Node node = root;
    while (node != null) {
      if (key < node.key1) {
        node = node.child1;
      } else if (key == node.key1) {
        return true;
      } else if ((node.keys == 1) || (key < node.key2)) {
        node = node.child2;
      } else if (key == node.key2) {
        return true;
      } else if ((node.keys == 2) || (key < node.key3)) {
        node = node.child3;
      } else if (key == node.key3) {
        return true;
      } else {
        node = node.child4;
      }
    }
    return false;
  }

  /**
   *  insert() inserts the key "key" into this 2-3-4 tree.  If "key" is
   *  already present, a duplicate copy is NOT inserted.
   *
   *  @param key is the key sought.
   **/
  public void insert(int key) {
	  Tree234Node node = root;
	  if (root == null) {
		  root = new Tree234Node(null, key);
		  size++;
		  return;
	  }

	  if (find(key) == true) {
		  return;
	  }
	  
	  int doneSize = size+1;
	  while (size != doneSize) {
			  if (node.keys == 3) {
			  if (node.parent == null) {
				  node = breakRoot(node, key);
			  } else {
			//	  System.out.println("BREAKING " + node);
				  node = breakNode(node, key);
			  }
		  }

		  if (key < node.key1) {
			  if (node.parent == null && (size <= 3)) {
				  insertRoot(node, key);
			  } else {
				  if (node.child1 != null) {
					  node = node.child1;
				  }
				  if (node.keys < 3) {
					  insertNode(node, key);
				  }
			  }
			  
		  } else if ((node.keys == 1) || (key < node.key2)) {
			  if (node.parent == null && (size <= 3)) {
				  insertRoot(node, key);
			  } else {
				  if (node.child2 != null) {
					  node = node.child2;
				  }
				  if (node.keys < 3) {
					  insertNode(node, key);
				  }
			  }
	
		  } else if ((node.keys == 2) || (key < node.key3)) {
			  node = node.child3;
		  } else {
			  node = node.child4;
		  }
	  }
	  return;
  }

  /**
   *  testHelper() prints the String representation of this tree, then
   *  compares it with the expected String, and prints an error message if
   *  the two are not equal.
   *
   *  @param correctString is what the tree should look like.
   **/
  public void testHelper(String correctString) {
    String treeString = toString();
    System.out.println(treeString);
    if (!treeString.equals(correctString)) {
      System.out.println("ERROR:  Should be " + correctString);
    }
  }

  /**
   *  main() is a bunch of test code.  Feel free to add test code of your own;
   *  this code won't be tested or graded.
   **/
  public static void main(String[] args) {
    Tree234 t = new Tree234();

    System.out.println("\nInserting 84.");
    t.insert(84);
    t.testHelper("84");

    System.out.println("\nInserting 7.");
    t.insert(7);
    t.testHelper("7 84");

    System.out.println("\nInserting 22.");
    t.insert(22);
    t.testHelper("7 22 84");

    System.out.println("\nInserting 95.");
    t.insert(95);
    t.testHelper("(7)22(84 95)");

    System.out.println("\nInserting 50.");
    t.insert(50);
    t.testHelper("(7)22(50 84 95)");

    System.out.println("\nInserting 11.");
    t.insert(11);
    t.testHelper("(7 11)22(50 84 95)");

    System.out.println("\nInserting 37.");
    t.insert(37);
    t.testHelper("(7 11)22(37 50)84(95)");

    System.out.println("\nInserting 60.");
    t.insert(60);
    t.testHelper("(7 11)22(37 50 60)84(95)");

    System.out.println("\nInserting 1.");
    t.insert(1);
    t.testHelper("(1 7 11)22(37 50 60)84(95)");

    System.out.println("\nInserting 23.");
    t.insert(23);
    t.testHelper("(1 7 11)22(23 37)50(60)84(95)");

    System.out.println("\nInserting 16.");
    t.insert(16);
    t.testHelper("((1)7(11 16)22(23 37))50((60)84(95))");

    System.out.println("\nInserting 100.");
    t.insert(100);
    t.testHelper("((1)7(11 16)22(23 37))50((60)84(95 100))");

    System.out.println("\nInserting 28.");
    t.insert(28);
    t.testHelper("((1)7(11 16)22(23 28 37))50((60)84(95 100))");

    System.out.println("\nInserting 86.");
    t.insert(86);
    t.testHelper("((1)7(11 16)22(23 28 37))50((60)84(86 95 100))");

    System.out.println("\nInserting 49.");
    t.insert(49);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((60)84(86 95 100))");

    System.out.println("\nInserting 81.");
    t.insert(81);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((60 81)84(86 95 100))");

    System.out.println("\nInserting 51.");
    t.insert(51);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51 60 81)84(86 95 100))");

    System.out.println("\nInserting 99.");
    t.insert(99);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51 60 81)84(86)95(99 100))");

    System.out.println("\nInserting 75.");
    t.insert(75);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51)60(75 81)84(86)95" +
                 "(99 100))");

    System.out.println("\nInserting 66.");
    t.insert(66);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51)60(66 75 81))84((86)95" +
                 "(99 100))");

    System.out.println("\nInserting 4.");
    t.insert(4);
    t.testHelper("((1 4)7(11 16))22((23)28(37 49))50((51)60(66 75 81))84" +
                 "((86)95(99 100))");

    System.out.println("\nInserting 80.");
    t.insert(80);
    t.testHelper("(((1 4)7(11 16))22((23)28(37 49)))50(((51)60(66)75" +
                 "(80 81))84((86)95(99 100)))");

    System.out.println("\nFinal tree:");
    t.printTree();
  }

}
