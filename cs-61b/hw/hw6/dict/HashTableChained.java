/* HashTableChained.java */

package dict;
import list.SList;


/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/

	SList[] myHashTable;
	int mySize;


  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public static boolean isPrime(int n) {
	  if (n < 0) {
		  n = -1*n;
	  }
	  for (int i = 2; i < n; i++) {
		  if ((double) n%i == 0) {
			  return false;
		  }
	  }
	  return true;
  }
  
  public HashTableChained(int sizeEstimate) {
	  while (! (isPrime(sizeEstimate))) {
		  sizeEstimate++;
	  }
	  myHashTable = new SList[sizeEstimate];
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    myHashTable = new SList[101];
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
    return ((520*code + 42)% 2147483647)%myHashTable.length;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
	return mySize;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    if (this.size() == 0) {
    	return true;
    } else {
    	return false;
    }
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    Entry e = new Entry();
    e.key = key;
    e.value = value;
    int keyHashCode = key.hashCode();
    int bucketNum = compFunction(keyHashCode);
    if (myHashTable[bucketNum] == null) {
    	myHashTable[bucketNum] = new SList();
    }
    myHashTable[bucketNum].insertFront(e);
    mySize++;
    return e;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    int keyHashCode = key.hashCode();
    int bucketNum = this.compFunction(keyHashCode);
    for (int i = 1; i <= myHashTable[bucketNum].length(); i++) {
    	if (((Entry) myHashTable[bucketNum].nth(i)).key().equals(key)) {
    		return (Entry) myHashTable[bucketNum].nth(i);
    	}
    }	
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
	    int keyHashCode = key.hashCode();
	    int bucketNum = this.compFunction(keyHashCode);
	    for (int i = 1; i <= myHashTable[bucketNum].length(); i++) {
	    	if (((Entry) myHashTable[bucketNum].nth(i)).key().equals(key)) {
	    		myHashTable[bucketNum].remove(i-1);
	    		mySize--;
	    		return (Entry) myHashTable[bucketNum].nth(i);
	    	}
	    }	
	    return null;
	  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
	mySize = 0;
    myHashTable = new SList[myHashTable.length];
  }
  
  public static void paint(HashTableChained hashT) {
	    if (hashT != null) {
	      int width = hashT.myHashTable.length;

	      /* Draw the ocean. */
	      for (int x = 0; x < width + 2; x++) {
	        System.out.print("-");
	      }
	      System.out.println();
	        for (int x = 0; x < width; x++) {
	          SList contents = hashT.myHashTable[x];
	          if (contents != null) {
	            System.out.print(contents.length());
	          } else {
	            System.out.print(' ');
	          }
	        System.out.println("|");
	      }
	      for (int x = 0; x < width + 2; x++) {
	        System.out.print("-");
	      }
	      System.out.println();
	    }
	  }
  
  public static void printTable(HashTableChained t){
	  for (int i = 0; i < t.myHashTable.length; i++){
		  System.out.println(t.myHashTable[i]);
	  }
  }

  	
	public static void main(String[] args) {
		System.out.println(97 + " is prime? " + isPrime(97));
		HashTableChained hashT = new HashTableChained(98);
		System.out.println("\n");
		hashT.insert("Cat", "Hello");
		hashT.insert(1, 1);
		hashT.insert(2, 2);
		hashT.insert(3, 3);
		hashT.insert(50, 50);
		hashT.insert("WTF", "WTF");
		Entry a = hashT.find("Cat");
		System.out.println(a.key() + "  " + a.value());
		Entry b = hashT.find(3);
		System.out.println(b.key() + " " + b.value());
		hashT.remove("Cat");
		hashT.remove(3);
		System.out.println("\nhashT size = " + hashT.size());
		System.out.println("compFunction 97 = " + hashT.compFunction(97));
		printTable(hashT);
	//	paint(hashT);
	}

 
}
