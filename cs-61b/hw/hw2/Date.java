/* Date.java */

import java.io.*;

class Date {

  private int Month;
  private int Day;
  private int Year;

  /** Constructs a date with the given month, day and year.   If the date is
   *  not valid, the entire program will halt with an error message.
   *  @param month is a month, numbered in the range 1...12.
   *  @param day is between 1 and the number of days in the given month.
   *  @param year is the year in question, with no digits omitted.
   */
  public Date(int month, int day, int year) {
    if (isValidDate(month, day, year) == false) {
      System.out.println("Fatal error:  Invalid date.");
      System.exit(0);
    }
      Month = month;
      Day = day;
      Year = year;
  }

  /** Constructs a Date object corresponding to the given string.
   *  @param s should be a string of the form "month/day/year" where month must
   *  be one or two digits, day must be one or two digits, and year must be
   *  between 1 and 4 digits.  If s does not match these requirements or is not
   *  a valid date, the program halts with an error message.
   */

  private static int charCheck(char[]  charArray) {
    int sum = 0;
    for (int i =0; i < charArray.length; i++) {
      if (Character.isDigit(charArray[i]) == false) {
	sum += 1;
      }
    }
    return sum;
  } 

  public Date(String s) {

    String[] d = s.split("/");
      
      if (d.length < 3) {
      	System.out.println("Fatal error:  Invalid date.");
	System.exit(0);
      }
      
      String month = d[0];
      String day = d[1];
      String year = d[2];
      
      char[] monthChar = month.toCharArray();
      char[] dayChar = day.toCharArray();
      char[] yearChar= year.toCharArray();
      
      if (month.length() <= 2 &&
	  month.length() > 0 &&
	  day.length() <= 2 &&
	  day.length() > 0 &&
	  year.length() <= 4 &&
	  year.length() > 0) {
	
	if (charCheck(monthChar) == 00 &&
	    charCheck(dayChar) == 0 &&  
	    charCheck(yearChar) == 0) {
	  
	  int monthValue = Integer.parseInt(month);
	  int dayValue = Integer.parseInt(day);
	  int yearValue = Integer.parseInt(year);
	  
	  if (isValidDate(monthValue, dayValue, yearValue) == true) {
	    
	    Month = monthValue;
	    Day = dayValue;
	    Year = yearValue;
	    
	  } else {
	    
	    System.out.println("Fatal error:  Invalid date.");
	    System.exit(0);
	  }
	  
	} else {
	  
	  System.out.println("Fatal error:  Invalid date.");
	  System.exit(0);
	}
      }
    }
    
  /** Checks whether the given year is a leap year.
   *  @return true if and only if the input year is a leap year.
   */
  public static boolean isLeapYear(int year) {
    if (!(year%100 == 0) && (year%4 == 0) ||
	 (year%400 == 0)) {
      return true;
    } else {
      return false;
    } 
  }
 
  /** Returns the number of days in a given month.
   *  @param month is a month, numbered in the range 1...12.
   *  @param year is the year in question, with no digits omitted.
   *  @return the number of days in the given month.
   */
  public static int daysInMonth(int month, int year) {
    switch (month) {
    case 2:
      if (isLeapYear(year) == true) {
	return 29;
	  } else {
	return 28;
      }
    case 4:
    case 6:
    case 9:
    case 11:
      return 30;
    default:
      return 31;  }
  }

  /** Checks whether the given date is valid.
   *  @return true if and only if month/day/year constitute a valid date.
   *
   *  Years prior to A.D. 1 are NOT valid.
   */
  public static boolean isValidDate(int month, int day, int year) {
    if (month<=12 && 
	month>=1 &&
	day <= daysInMonth(month, year) &&
	day >= 1 &&
	year >= 1) {
      return true;
    } else {
      return false;
    }
  }

  /** Returns a string representation of this date in the form month/day/year.
   *  The month, day, and year are printed in full as integers; for example,
   *  12/7/2006 or 3/21/407.
   *  @return a String representation of this date.
   */
  public String toString() {
    String dateString = new String(this.Month + "/" + this.Day + "/" + this.Year);
    return dateString;
  }

  /** Determines whether this Date is before the Date d.
   *  @return true if and only if this Date is before d. 
   */
  public boolean isBefore(Date d) {
    if (this.Year < d.Year) {
      return true;
    } else {
    if (this.Year == d.Year &&
	this.Month < d.Month) {
      return true;
    } else {
    if (this.Year == d.Year &&
	this.Month == d.Month &&
	this.Day < d.Day) {
      return true;
    } else {
      return false;
    }
  }
    }
  }

  /** Determines whether this Date is after the Date d.
   *  @return true if and only if this Date is after d. 
   */
  public boolean isAfter(Date d) {
   if (this.isBefore(d) == false &&
       d.isBefore(this) == false) {
      return false;
   } else {
   if (this.isBefore(d) == true) {
     return false;
   }
   return true;
   }
  }

  /** Returns the number of this Date in the year.
   *  @return a number n in the range 1...366, inclusive, such that this Date
   *  is the nth day of its year.  (366 is only used for December 31 in a leap
   *  year.)
   */
  public int dayInYear() {
    int sum = 0;
    for (int month=1; month < this.Month; month++) {
      sum =sum + daysInMonth(month, this.Year);
    }
    return sum + this.Day;
  }

  /** Determines the difference in days between d and this Date.  For example,
   *  if this Date is 12/15/1997 and d is 12/14/1997, the difference is 1.
   *  If this Date occurs before d, the result is negative.
   *  @return the difference in days between d and this date.
   */

  private int dayTotal() {
    int sum = 0;
    for (int year=1; year < this.Year; year++) {
      if (isLeapYear(year) == true) {
	sum += 366;
      } else {
	sum += 365;
      }
    }
    return sum + this.dayInYear();
  }
  
  public int difference(Date d) {
    if (this.isBefore(d) == true) {
      return -(d.dayTotal() - this.dayTotal());
    } else {
      if (this.isBefore(d) == false) {
	return (this.dayTotal() - d.dayTotal());
      } else {
	return 0;
      }
    }
  }
    
  public static void main(String[] argv) {
    System.out.println("\nTesting constructors.");
    Date d1 = new Date(1, 1, 1);
    System.out.println("Date should be 1/1/1: " + d1);
    d1 = new Date("2/4/2");
    System.out.println("Date should be 2/4/2: " + d1);
    d1 = new Date("2/29/2000");
    System.out.println("Date should be 2/29/2000: " + d1);
    d1 = new Date("2/29/1904");
    System.out.println("Date should be 2/29/1904: " + d1);

    d1 = new Date(12, 31, 1975);
    System.out.println("Date should be 12/31/1975: " + d1);
    Date d2 = new Date("1/1/1976");
    System.out.println("Date should be 1/1/1976: " + d2);
    Date d3 = new Date("1/2/1976");
    System.out.println("Date should be 1/2/1976: " + d3);

    Date d4 = new Date("2/27/1977");
    Date d5 = new Date("8/31/2110");

    /* I recommend you write code to test the isLeapYear function! */

    System.out.println("\nTesting before and after.");
    System.out.println(d2 + " after " + d1 + " should be true: " + 
                       d2.isAfter(d1));
    System.out.println(d3 + " after " + d2 + " should be true: " + 
                       d3.isAfter(d2));
    System.out.println(d1 + " after " + d1 + " should be false: " + 
                       d1.isAfter(d1));
    System.out.println(d1 + " after " + d2 + " should be false: " + 
                       d1.isAfter(d2));
    System.out.println(d2 + " after " + d3 + " should be false: " + 
                       d2.isAfter(d3));

    System.out.println(d1 + " before " + d2 + " should be true: " + 
                       d1.isBefore(d2));
    System.out.println(d2 + " before " + d3 + " should be true: " + 
                       d2.isBefore(d3));
    System.out.println(d1 + " before " + d1 + " should be false: " + 
                       d1.isBefore(d1));
    System.out.println(d2 + " before " + d1 + " should be false: " + 
                       d2.isBefore(d1));
    System.out.println(d3 + " before " + d2 + " should be false: " + 
                       d3.isBefore(d2));

    System.out.println("\nTesting difference.");
    System.out.println(d1 + " - " + d1  + " should be 0: " + 
                       d1.difference(d1));
    System.out.println(d1.dayInYear());
    System.out.println(d2.dayInYear());
    System.out.println(d3.dayInYear());
    System.out.println(d4.dayInYear());
    
    System.out.println(d2 + " - " + d1  + " should be 1: " + 
                       d2.difference(d1));
    System.out.println(d3 + " - " + d1  + " should be 2: " + 
                       d3.difference(d1));
    System.out.println(d3 + " - " + d4  + " should be -422: " + 
                       d3.difference(d4));
    System.out.println(d5 + " - " + d4  + " should be 48762: " + 
                       d5.difference(d4));

    System.out.println(isLeapYear(1800));
    System.out.println(isLeapYear(1900));
    System.out.println(daysInMonth(2, 2000));
    System.out.println(daysInMonth(2, 2001));
    System.out.println(daysInMonth(8,2001));
    System.out.println(isValidDate(1, 30, 2000));
    System.out.println(isValidDate(2, 30, 2000));
    System.out.println(isValidDate(8, 49, 0000));
    System.out.println(d1.toString());
    System.out.println(isValidDate(1, 1, 1));
    Date d10 = new Date(12, 31, 2000);
    System.out.println(d10);
    System.out.println(d1.dayInYear());
    System.out.println(d2.dayInYear());
    System.out.println(d10.dayInYear());
    String tester = "2/29/2a00";
    String[] what= tester.split("/");
    System.out.println(what[0]);
    System.out.println(what[1]);
    System.out.println(what[2]);
    String a = what[0];
    String c = what[1];
    String d = what[2];
    int b = Integer.parseInt(a);
    int e = Integer.parseInt(c);
    System.out.println(a);
    System.out.println(b);
    System.out.println(a.length());
    System.out.println(c.length());
    System.out.println(d.length());
    char[] aChar = a.toCharArray();
    char[] cChar = c.toCharArray();
    char[] dChar = d.toCharArray();
    
    System.out.println(charCheck(aChar));
    System.out.println(charCheck(cChar));
    System.out.println(charCheck(dChar));
    
    Date d101 = new Date(8, 31, 2110);
    Date d102 = new Date(2, 21, 2145);
    Date d103 = new Date(1, 1, 1921);
    Date d104= new Date(1, 1, 1);
    Date d105 = new Date(12, 31, 2);
    
    System.out.println(d101.difference(d102));
    
    System.out.println(d101.dayTotal());
    System.out.println(d102.dayTotal());
    System.out.println(d103.dayTotal());
    System.out.println(d104.dayTotal());
    System.out.println(d105.dayTotal());
    
    System.out.println(isLeapYear(2110));
    System.out.println(d104.dayInYear());
    Date d123 = new Date("Hello");
    Date d234 = new Date("2/13");
    Date d166 = new Date("2/78/2a42");
    System.out.println(d166);
   
  }
}

