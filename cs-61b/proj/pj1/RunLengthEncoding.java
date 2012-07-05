/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes an
 *  Ocean object.  Descriptions of the methods you must implement appear below.
 *  They include constructors of the form
 *
 *      public RunLengthEncoding(int i, int j, int starveTime);
 *      public RunLengthEncoding(int i, int j, int starveTime,
 *                               int[] runTypes, int[] runLengths) {
 *      public RunLengthEncoding(Ocean ocean) {
 *
 *  that create a run-length encoding of an Ocean having width i and height j,
 *  in which sharks starve after starveTime timesteps.
 *
 *  The first constructor creates a run-length encoding of an Ocean in which
 *  every cell is empty.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts an Ocean object into a run-length encoding of that object.
 *
 *  See the README file accompanying this project for additional details.
 */

public class RunLengthEncoding {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
	
	private int RLHeight;
	private int RLWidth;
	private int RLStarveTime;
	private DList1 OceanList;
	private int RunTime;
	private int[] Hunger;

  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with three parameters) is a constructor that creates
   *  a run-length encoding of an empty ocean having width i and height j,
   *  in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public RunLengthEncoding(int i, int j, int starveTime) {
    // Your solution here.
	  RLWidth = i;
	  RLHeight = j;
	  RLStarveTime = starveTime;
	  OceanList = new DList1();
	  OceanList.insertEnd(Ocean.EMPTY, i*j);
	  RunTime = 0;
	  Hunger = new int[1];
  }

  /**
   *  RunLengthEncoding() (with five parameters) is a constructor that creates
   *  a run-length encoding of an ocean having width i and height j, in which
   *  sharks starve after starveTime timesteps.  The runs of the run-length
   *  encoding are taken from two input arrays.  Run i has length runLengths[i]
   *  and species runTypes[i].
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   *  @param runTypes is an array that represents the species represented by
   *         each run.  Each element of runTypes is Ocean.EMPTY, Ocean.FISH,
   *         or Ocean.SHARK.  Any run of sharks is treated as a run of newborn
   *         sharks (which are equivalent to sharks that have just eaten).
   *  @param runLengths is an array that represents the length of each run.
   *         The sum of all elements of the runLengths array should be i * j.
   */

  public RunLengthEncoding(int i, int j, int starveTime,
                           int[] runTypes, int[] runLengths) {
    // Your solution here.
	  RLWidth = i;
	  RLHeight = j;
	  RLStarveTime = starveTime;
	  OceanList = new DList1();
	  Hunger = new int[i*j];
	  for (int n = 0; n < runTypes.length; n++) {
	    // System.out.println("TYPE " + runTypes[n] + " RUNLENGTH " + runLengths[n]);
		  OceanList.insertEnd(runTypes[n], runLengths[n]);
	  }
	  for (int k = 0; k < runTypes.length; k++) {
		  if (runTypes[k] == Ocean.SHARK) {
			  for (int l = runLengths[k]; l > 0; l--) {
				  Hunger[l] = RLStarveTime + 2;
				  // System.out.println("Hunger @ l     "  + l + " " + Hunger[l]);
			  } } }
  }

  /**
   *  restartRuns() and nextRun() are two methods that work together to return
   *  all the runs in the run-length encoding, one by one.  Each time
   *  nextRun() is invoked, it returns a different run (represented as a
   *  TypeAndSize object), until every run has been returned.  The first time
   *  nextRun() is invoked, it returns the first run in the encoding, which
   *  contains cell (0, 0).  After every run has been returned, nextRun()
   *  returns null, which lets the calling program know that there are no more
   *  runs in the encoding.
   *
   *  The restartRuns() method resets the enumeration, so that nextRun() will
   *  once again enumerate all the runs as if nextRun() were being invoked for
   *  the first time.
   *
   *  (Note:  Don't worry about what might happen if nextRun() is interleaved
   *  with addFish() or addShark(); it won't happen.)
   */

  /**
   *  restartRuns() resets the enumeration as described above, so that
   *  nextRun() will enumerate all the runs from the beginning.
   */

  public void restartRuns() {
    // Your solution here.
	  RunTime = 0;
  }

  /**
   *  nextRun() returns the next run in the enumeration, as described above.
   *  If the runs have been exhausted, it returns null.  The return value is
   *  a TypeAndSize object, which is nothing more than a way to return two
   *  integers at once.
   *  @return the next run in the enumeration, represented by a TypeAndSize
   *          object.
   */

  public TypeAndSize nextRun() {
    // Replace the following line with your solution.
	  RunTime++;
	  if (RunTime > OceanList.size) {
		  return null;
	  } else {
    return new TypeAndSize(OceanList.nth1(RunTime), OceanList.nth2(RunTime));
	  }
  }

  /**
   *  toOcean() converts a run-length encoding of an ocean into an Ocean
   *  object.  You will need to implement the three-parameter addShark method
   *  in the Ocean class for this method's use.
   *  @return the Ocean represented by a run-length encoding.
   */

  public Ocean toOcean() {
    // Replace the following line with your solution.
	Ocean targetOcean = new Ocean(this.RLWidth, this.RLHeight, this.RLStarveTime);

	System.out.println(this.OceanList);
	int STEP = 0;
	
	for (int k = 1; k <= OceanList.size; k++) {
		int Type = OceanList.nth1(k);
		int Length = OceanList.nth2(k);
		if (Type == Ocean.EMPTY) {
			STEP += Length;
		}
		
		if (Type == Ocean.FISH) {
			for (int l = Length; l > 0; l--) {
				targetOcean.addFish(STEP%RLWidth, STEP/RLWidth);
				STEP++;
			} }
		
		if (Type == Ocean.SHARK) {
			for (int l = Length; l > 0; l--) {
				targetOcean.addShark(STEP%RLWidth, STEP/RLWidth, this.Hunger[STEP]);
				STEP++;
			} 
		}
	} 
    return targetOcean;
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of an input Ocean.  You will need to implement
   *  the sharkFeeding method in the Ocean class for this constructor's use.
   *  @param sea is the ocean to encode.
   */

  public RunLengthEncoding(Ocean sea) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
	  
	  OceanList = new DList1();
	  RLHeight = sea.height();
	  RLWidth = sea.width();
	  RLStarveTime = sea.starveTime();
	  Hunger = new int[RLHeight*RLWidth];
		
	for (int STEP = 0; STEP < RLHeight*RLWidth; STEP+=0) {
		  if (sea.cellContents(STEP%RLWidth, STEP/RLWidth) == Ocean.EMPTY) {
			  int Length = 0;
			  while (sea.cellContents(STEP%RLWidth, STEP/RLWidth) == Ocean.EMPTY) {
				  Length++;
				  STEP++; 
				  if (STEP == RLHeight*RLWidth) {
					  break;
					} 
				}
			  OceanList.insertEnd(Ocean.EMPTY, Length);
		  }
		  
		  if (sea.cellContents(STEP%RLWidth, STEP/RLWidth) == Ocean.FISH) {
			  int Length = 0;
			  while (sea.cellContents(STEP%RLWidth, STEP/RLWidth) == Ocean.FISH) {
				  Length++;
				  STEP++; 
				  if (STEP == RLHeight*RLWidth) {
					  break;
					} 
				}
			  OceanList.insertEnd(Ocean.FISH, Length);
		  }
		  
		  if (sea.cellContents(STEP%RLWidth, STEP/RLWidth) == Ocean.SHARK) {
			  int Length = 0;
			  int XCOR = STEP%RLWidth;
			  int YCOR = STEP/RLWidth;
			  int FEEDING = sea.sharkFeeding(XCOR, YCOR);
			  while (sea.cellContents(STEP%RLWidth, STEP/RLWidth) == Ocean.SHARK &&
					  sea.sharkFeeding(STEP%RLWidth, STEP/RLWidth) ==  FEEDING) {
				  Length++;
				  Hunger[STEP] = FEEDING-1;
				  STEP++;
				  if (STEP == RLHeight*RLWidth) {
					  break;} }
			  OceanList.insertEnd(Ocean.SHARK, Length);
		  }
	  }
	//System.out.println(OceanList);	  
    check();
  }

  /**
   *  The following methods are required for Part IV.
   */

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.  The final run-length
   *  encoding should be compressed as much as possible; there should not be
   *  two consecutive runs of sharks with the same degree of hunger.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {

    /**
    // Your solution here, but you should probably leave the following line
    //   at the end.
    check();
    **/
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  The final run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs of sharks with the same degree
   *  of hunger.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
    // check();
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same contents, or if the sum of all run
   *  lengths does not equal the number of cells in the ocean.
   */

  public void check() {
	  int sumLength = 0;
	  for (int k =1; k <= OceanList.size; k++) { 
	  if (OceanList.nth1(k) == OceanList.nth1(k+1) &&
			 Hunger[sumLength] == Hunger[sumLength + OceanList.nth2(k)]) {
		 System.out.println("Species in RUN " + k + "species in RUN " + (k+1) + "are the same!");
	 	}
	 	sumLength += OceanList.nth2(k);
	  }  
	  if (sumLength != RLHeight*RLWidth) {
		  System.out.println("Sum of run lengths " + sumLength + " does not equal " + RLHeight*RLWidth);
	  } 
  }

}
