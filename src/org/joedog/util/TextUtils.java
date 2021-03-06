package org.joedog.util;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TextUtils {

  /**
   * Tests two strings to see if they're the same.
   * <p>
   * @param  String   The first string argument
   * @param  String   The second string argument
   * @return boolean  True if they're the same, false if they're not
   */
  public static boolean equals(String a, String b) {
    return (a == null) ? b == null : a.equals(b);
  }

  /**
   * Tests a string object to determin if it's empty.
   * <p>
   * @param  String  The string to test
   * @return boolean True if empty, false if not
   */
  public boolean empty(String str) {
    return (str == null || str.trim().length() < 1);
  }

  /**
   * Parses a formated string and returns an array. The string must
   * be formatted in the following manner: {n,n} or (n,n) or [n,n,n]
   * The array's size will be determined automatically based on the 
   * number of comma separated tokens inside the brackets.
   * <p>
   * @param  String  A formated string, i.e., (23,12,12,3)
   * @return ArrayList<Integer>
   */
  public static ArrayList<Integer> toArrayList(String str) {
    int [] tmp = toArray(str);
    ArrayList<Integer> list = new ArrayList<Integer>();
    for (int i = 0; i < tmp.length; i++) {
      list.add(tmp[i]);
    }
    return list;
  }

  /**
   * Parses a formated string and returns an array. The string must
   * be formatted in the following manner: {n,n} or (n,n) or [n,n,n]
   * The array's size will be determined automatically based on the 
   * number of comma separated tokens inside the brackets.
   * <p>
   * @param  String  A formated string, i.e., (23,12,12,3)
   * @return int[]
   */
  public static int[] toArray(String str) {
    if (str == null && ! str.matches("\\(.*|\\{.*|\\[.*")) {
      System.err.println("ERROR: Format should match: {n,n} (n,n) or [n,n]");
      return null;
    }

    String[] tmp = str.split(",");
    int size     =  tmp.length;
    
    if ((str = str.replaceAll("\\s", "")).length()  < 2 * size + 1) {
      System.err.println("ERROR: Parsed length is too short: "+str.length());
      return null;
    }

    String[] tokens = (str = str.substring(1, str.length() - 1)).split(","); 
 
    int[] ret = new int[tokens.length];
    try {
      for (int i = 0; i < size; ++i) {
        ret[i] = Integer.parseInt(tokens[i]);
      }
    } catch (NumberFormatException ex) {
      return null;
    }
    return ret;
  }
}
