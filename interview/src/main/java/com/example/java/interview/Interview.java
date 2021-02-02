package com.example.java.interview;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 
 */
public final class Interview {
    public Interview() {
    }

    public static void main(String[] args) {
    }

    /**
     * Demonstrate inner classes in Java:
     * - static inner class can be instantiated without an instance of enclosing class
     * - non-static inner class can only instantiated with an instance of an enclosing class
     * - private inner class can only be used internally
     */
    public void demoInnerClass() {
        Outer.StaticInner staticInner = new Outer.StaticInner(); 
        staticInner.doSomething();

        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.doSomething();
    }   

    /**
     * Reverse a string using recursion
     * @param s
     * @return the reversed string
     */
	public String reverse(String s) {
        if (s == null) return null;
        if (s.length() == 1) return s;
        return reverse(s.substring(1)).concat(String.valueOf(s.charAt(0)));
	}

    /**
     * Find intersection of two arrays
     * @param arr1 : first input array
     * @param arr2 : second input array
     * @return array of element in intersection
     */
	public int[] intersect(int[] arr1, int[] arr2) {
        Set<Integer> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();

        for (int i : arr1) {
            set.add(i);            
        }

        for (int i : arr2) {
            if (!set.add(i)) {
                list.add(i);
            }
        }
        
        list.sort(Comparator.naturalOrder());
        int[] result = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * Find first non-repeated character in string
     * @param s : input string
     * @return : a Character that is first non-repeated or null if not found
     */
    public Character firstNonRepeated(String s) {
        return  
        s.chars()
            .mapToObj(i -> Character.valueOf((char)i))
            .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
            .entrySet()
            .stream()
            .filter(e -> e.getValue() == 1)
            .map(Entry::getKey)
            .findFirst()
            .orElseGet(()->null);
	}
}
