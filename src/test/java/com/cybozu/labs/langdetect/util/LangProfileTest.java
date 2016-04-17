/**
 *
 */
package com.cybozu.labs.langdetect.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Nakatani Shuyo
 */
public class LangProfileTest {

    /**
     * Test method for {@link com.cybozu.labs.langdetect.util.LangProfile#LangProfile()}.
     */
    @Test
    public final void testLangProfile() {
        LangProfile profile = new LangProfile();
        assertEquals(null, profile.getName());
    }

    /**
     * Test method for {@link com.cybozu.labs.langdetect.util.LangProfile#LangProfile(java.lang.String)}.
     */
    @Test
    public final void testLangProfileStringInt() {
        LangProfile profile = new LangProfile("en");
        assertEquals("en", profile.getName());
    }

    /**
     * Test method for {@link com.cybozu.labs.langdetect.util.LangProfile#add(java.lang.String)}.
     */
    @Test
    public final void testAdd() {
        LangProfile profile = new LangProfile("en");
        profile.add("a");
        assertEquals(Integer.valueOf(1), profile.getFreq().get("a"));
        profile.add("a");
        assertEquals(Integer.valueOf(2), profile.getFreq().get("a"));
        profile.omitLessFreq();
    }


    /**
     * Illegal call test for {@link LangProfile#add(String)}
     */
    @Test
    public final void testAddIllegally1() {
        LangProfile profile = new LangProfile(); // Illegal ( available for only JSONIC ) but ignore
        profile.add("a"); // ignore
        assertEquals(null, profile.getFreq().get("a")); // ignored
    }

    /**
     * Illegal call test for {@link LangProfile#add(String)}
     */
    @Test
    public final void testAddIllegally2() {
        LangProfile profile = new LangProfile("en");
        profile.add("a");
        profile.add("");  // Illegal (string's length of parameter must be between 1 and 3) but ignore
        profile.add("abcd");  // as well
        assertEquals(Integer.valueOf(1), profile.getFreq().get("a"));
        assertEquals(null, profile.getFreq().get(""));     // ignored
        assertEquals(null, profile.getFreq().get("abcd")); // ignored
    }

    /**
     * Test method for {@link com.cybozu.labs.langdetect.util.LangProfile#omitLessFreq()}.
     */
    @Test
    public final void testOmitLessFreq() {
        LangProfile profile = new LangProfile("en");
        String[] grams = "a b c \u3042 \u3044 \u3046 \u3048 \u304a \u304b \u304c \u304d \u304e \u304f".split(" ");
        for (int i = 0; i < 5; ++i) {
            for (String g : grams) {
                profile.add(g);
            }
        }
        profile.add("\u3050");

        assertEquals(Integer.valueOf(5), profile.getFreq().get("a"));
        assertEquals(Integer.valueOf(5), profile.getFreq().get("\u3042"));
        assertEquals(Integer.valueOf(1), profile.getFreq().get("\u3050"));
        profile.omitLessFreq();
        assertEquals(null, profile.getFreq().get("a")); // omitted
        assertEquals(Integer.valueOf(5), profile.getFreq().get("\u3042"));
        assertEquals(null, profile.getFreq().get("\u3050")); // omitted
    }

    /**
     * Illegal call test for {@link com.cybozu.labs.langdetect.util.LangProfile#omitLessFreq()}.
     */
    @Test
    public final void testOmitLessFreqIllegally() {
        LangProfile profile = new LangProfile();
        profile.omitLessFreq();  // ignore
    }

}
