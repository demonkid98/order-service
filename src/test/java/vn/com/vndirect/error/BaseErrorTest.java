package vn.com.vndirect.error;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class BaseErrorTest {

    private BaseError error;
    private String testKey;

    @Before
    public void setUp() {
        error = new BaseError();
    }

    @Test
    public void testNewErrorShouldBeEmpty() {
        assertTrue(error.isEmpty());
    }

    @Test
    public void testErrorWithFieldsAndMessagesShouldNotBeEmpty() {
        error.add(testKey, "an error");
        assertFalse(error.isEmpty());
    }

    @Test
    public void testAddAndGetErrorField() {
        ArrayList<String> messages;

        messages = error.get(testKey);
        assertEquals(0, messages.size());

        String first = "first error";
        error.add(testKey, first);
        messages = error.get(testKey);
        assertEquals(1, messages.size());
        assertEquals(first, messages.get(0));

        String second = "second error";
        error.add(testKey, second);
        messages = error.get(testKey);
        assertEquals(2, messages.size());
        assertEquals(second, messages.get(1));
    }

    @Test
    public void testGetFirstError() {
        String first = "first error";
        error.add(testKey, first);
        error.add(testKey, "second error");
        assertEquals(first, error.getFirst());
    }

}
