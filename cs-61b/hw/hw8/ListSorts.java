/* ListSorts.java */

import list.*;

public class ListSorts {

  private final static int SORTSIZE = 100000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
	  LinkedQueue qQueue = new LinkedQueue();
	  while (!q.isEmpty()) {
		  Comparable pop;
		  try {
			  pop = (Comparable) q.dequeue();
			  LinkedQueue popQueue = new LinkedQueue();
			  popQueue.enqueue(pop);
			  qQueue.enqueue(popQueue);
		  } catch (QueueEmptyException e) {
			  e.printStackTrace();
		  }
	  }
	  return qQueue;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
	  LinkedQueue mQueue = new LinkedQueue();
	  try {
		  while (!q1.isEmpty() && !q2.isEmpty()) { 
			  Comparable pop1 = (Comparable) q1.front();
			  Comparable pop2 = (Comparable) q2.front();
			  if (pop1.compareTo(pop2) <= 0) {
				  mQueue.enqueue(pop1);
				  q1.dequeue();
			  } else {
				  mQueue.enqueue(pop2);
				  q2.dequeue();
			  }
		  }
	  } catch (QueueEmptyException e) {
		  e.printStackTrace();
	  }
	  if (q1.isEmpty()) {
		  mQueue.append(q2);
	  } else {
		  mQueue.append(q1);
	  }
	  return mQueue;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
		  LinkedQueue qSmall, LinkedQueue qEquals, LinkedQueue qLarge) {
	  while (!qIn.isEmpty()){
		  Comparable pop;
		  try {
			  pop = (Comparable) qIn.dequeue();
			  if (pop.compareTo(pivot) < 0) {
				  qSmall.enqueue(pop);
			  } else if (pop.compareTo(pivot) == 0) {
				  qEquals.enqueue(pop);
			  } else {
				  qLarge.enqueue(pop);
			  }
		  } catch (QueueEmptyException e) {
			  e.printStackTrace();
		  }
	  }
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
	  if (q.size() <= 1) {
		  return;
	  }
	  LinkedQueue qQueue = makeQueueOfQueues(q);
	  try {
		  while(qQueue.size() != 1) {
			  LinkedQueue q1 = (LinkedQueue) qQueue.dequeue();
			  LinkedQueue q2 = (LinkedQueue) qQueue.dequeue();
			  LinkedQueue mQ = mergeSortedQueues(q1, q2);
			  qQueue.enqueue(mQ);
		  }
	  } catch (QueueEmptyException e) {
		  e.printStackTrace();
	  }
	  try {
		  q.append((LinkedQueue) qQueue.dequeue());
	  } catch (QueueEmptyException e) {
		  e.printStackTrace();
	  }
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
	  if (q.size() <= 1) {
		  return;
	  }
	  int pIndex = (int) (q.size() * Math.random() + 1);
	  Comparable pivot = (Comparable) q.nth(pIndex);
	  LinkedQueue qSmall = new LinkedQueue();
	  LinkedQueue qEqual = new LinkedQueue();
	  LinkedQueue qLarge = new LinkedQueue();
	  partition(q, pivot, qSmall, qEqual, qLarge);
	  quickSort(qSmall);
	  quickSort(qLarge);
	  q.append(qSmall);
	  q.append(qEqual);
	  q.append(qLarge); 
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {

	LinkedQueue test1 = makeRandom(5);
	System.out.println("TEST QUEUE " + test1);
	LinkedQueue test1qq = makeQueueOfQueues(test1);
	System.out.println("QUEUE OF QUEUES " + test1qq);
	System.out.println(" ");
	
	LinkedQueue test21 = new LinkedQueue();
	test21.enqueue(1);
	test21.enqueue(4);
	test21.enqueue(4);
	test21.enqueue(8);
	test21.enqueue(9);

	System.out.println("Q1 = " + test21);
	
	LinkedQueue test22 = new LinkedQueue();
	test22.enqueue(1);
	test22.enqueue(2);
	test22.enqueue(7);
	test22.enqueue(8);

	System.out.println("Q2 = " + test22);
	
	LinkedQueue mQ = mergeSortedQueues(test21, test22);
	System.out.println("MERGED Q1 AND Q2 =" + mQ);
	System.out.println(" ");

	LinkedQueue test3 = makeRandom(17);
	System.out.println("BEFORE PARTITION " + test3);
	LinkedQueue test3s = new LinkedQueue();
	LinkedQueue test3e = new LinkedQueue();
	LinkedQueue test3l = new LinkedQueue();
	Comparable p = (Comparable) test3.nth(1);
	partition(test3, p, test3s, test3e, test3l);
	System.out.println("AFTER PARTITION " + test3 + "    PIVOT WAS " + p);
	System.out.println("SMALL " + test3s);
	System.out.println("EQUAL " + test3e);
	System.out.println("LARGE " + test3l);
	System.out.println(" ");

    LinkedQueue q = makeRandom(10);
    System.out.println("BEFORE MERGESORT " + q.toString());
    mergeSort(q);
    System.out.println("AFTER MERGESORT " + q.toString());
    System.out.println(" ");
    
    q = makeRandom(10);
    System.out.println("BEFORE QUICKSORT " + q.toString());
    quickSort(q);
    System.out.println("AFTER QUICKSORT " + q.toString());
    System.out.println(" ");

    /* Remove these comments for Part III. */
    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
  }

}
