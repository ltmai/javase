package com.example.java.interview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InterviewTest {
    
    private Interview app;

    @BeforeEach
    void init() {
        app = new Interview();
    }

    @Test
    public void firstNonRepeated() {
        String s = "abc";
        Character expected = 'a';
        assertEquals(expected, app.firstNonRepeated(s));
    }

    @Test
    public void firstNonRepeated_notFound() {
        String s = "abcabc";
        assertNull(app.firstNonRepeated(s));
    }


    @Test
    public void arrayIntersection_nonEmptyInput() {
        int[] arr1 = {1, 4 ,7 ,9 ,2};
        int[] arr2 = {1, 7, 3, 4, 5};
        int[] expected = {1, 4, 7};
        assertTrue(Arrays.equals(expected, app.intersect(arr1, arr2)));
    }

    @Test
    public void arrayIntersection_emptyInput() {
        int[] arr1 = {1, 4 ,7 ,9 ,2};
        int[] arr2 = {};
        int[] expected = {};
        assertTrue(Arrays.equals(expected, app.intersect(arr1, arr2)));
    }

    @Test
    void reverseString() {
        assertNull(app.reverse(null));
        assertEquals("a", app.reverse("a"));
        assertEquals("cba", app.reverse("abc"));
        assertEquals("abba", app.reverse("abba"));
    }
}
