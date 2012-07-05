public class Counter {
  private boolean[] digits;
  private int balance;

  public Counter(int size) {
    digits = new boolean[size];
    balance = 0;
  }

  // At any time, the balance given a Counter is the number of one's there are in the counter.
  // This is because we are given $3 to spend on an increment operation:
  // 000 ---> 001     $3 - $2 = + $1
  // 001 ---> 010     $3 - $3 = + $0
  
  // However, if we add a decrement() operation, the amortized cost of the counter operations
  // no longer run in O(1). For example: 
  // 0001000                                   BALANCE = $1
  // 0001000 ---> 0000111            But we changed 4 bits, so that costs $4, so now our BALANCE is NEGATIVE
  // Means out amortized cost has to run in linear time.
  public void increment() {
	balance += 3; // AMORTIZED COST
    int i = 0;
    balance--;
    while (digits[i]) {
      digits[i] = false;
      i++;
      balance--;
    }
    digits[i] = true;
    balance--;
    System.out.println("BALANCE IS $" + balance);
  }

  public String toString() {
    String ret = "";
    for (int i = digits.length - 1; i >= 0; i--) {
      ret = ret + (digits[i] ? "1" : "0");
    }
    return ret;
  }

  public static void main(String[] args) {
    Counter c = new Counter(20);

    for (int i = 0; i < 20; i++) {
      c.increment();
      System.out.println(c.toString());
    }
  }
}
