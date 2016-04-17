/**
 *
 */
package com.cybozu.labs.langdetect.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Nakatani Shuyo
 */
public class NGramTest {

    /**
     * Test method for constants
     */
    @Test
    public final void testConstants() {
        assertEquals(3, NGram.N_GRAM);
    }

    /**
     * Test method for {@link NGram#normalize(char)} with Latin characters
     */
    @Test
    public final void testNormalizeWithLatin() {
        assertEquals(' ', NGram.normalize('\u0000'));
        assertEquals(' ', NGram.normalize('\u0009'));
        assertEquals(' ', NGram.normalize('\u0020'));
        assertEquals(' ', NGram.normalize('\u0030'));
        assertEquals(' ', NGram.normalize('\u0040'));
        assertEquals('\u0041', NGram.normalize('\u0041'));
        assertEquals('\u005a', NGram.normalize('\u005a'));
        assertEquals(' ', NGram.normalize('\u005b'));
        assertEquals(' ', NGram.normalize('\u0060'));
        assertEquals('\u0061', NGram.normalize('\u0061'));
        assertEquals('\u007a', NGram.normalize('\u007a'));
        assertEquals(' ', NGram.normalize('\u007b'));
        assertEquals(' ', NGram.normalize('\u007f'));
        assertEquals('\u0080', NGram.normalize('\u0080'));
        assertEquals(' ', NGram.normalize('\u00a0'));
        assertEquals('\u00a1', NGram.normalize('\u00a1'));
    }

    /**
     * Test method for {@link NGram#normalize(char)} with CJK Kanji characters
     */
    @Test
    public final void testNormalizeWithCJKKanji() {
        assertEquals('\u4E00', NGram.normalize('\u4E00'));
        assertEquals('\u4E01', NGram.normalize('\u4E01'));
        assertEquals('\u4E02', NGram.normalize('\u4E02'));
        assertEquals('\u4E01', NGram.normalize('\u4E03'));
        assertEquals('\u4E04', NGram.normalize('\u4E04'));
        assertEquals('\u4E05', NGram.normalize('\u4E05'));
        assertEquals('\u4E06', NGram.normalize('\u4E06'));
        assertEquals('\u4E07', NGram.normalize('\u4E07'));
        assertEquals('\u4E08', NGram.normalize('\u4E08'));
        assertEquals('\u4E09', NGram.normalize('\u4E09'));
        assertEquals('\u4E10', NGram.normalize('\u4E10'));
        assertEquals('\u4E11', NGram.normalize('\u4E11'));
        assertEquals('\u4E12', NGram.normalize('\u4E12'));
        assertEquals('\u4E13', NGram.normalize('\u4E13'));
        assertEquals('\u4E14', NGram.normalize('\u4E14'));
        assertEquals('\u4E15', NGram.normalize('\u4E15'));
        assertEquals('\u4E1e', NGram.normalize('\u4E1e'));
        assertEquals('\u4E1f', NGram.normalize('\u4E1f'));
        assertEquals('\u4E20', NGram.normalize('\u4E20'));
        assertEquals('\u4E21', NGram.normalize('\u4E21'));
        assertEquals('\u4E22', NGram.normalize('\u4E22'));
        assertEquals('\u4E23', NGram.normalize('\u4E23'));
        assertEquals('\u4E13', NGram.normalize('\u4E24'));
        assertEquals('\u4E13', NGram.normalize('\u4E25'));
        assertEquals('\u4E30', NGram.normalize('\u4E30'));
    }

    /**
     * Test method for {@link NGram#normalize(char)} for Romanian characters
     */
    @Test
    public final void testNormalizeForRomanian() {
        assertEquals('\u015f', NGram.normalize('\u015f'));
        assertEquals('\u0163', NGram.normalize('\u0163'));
        assertEquals('\u015f', NGram.normalize('\u0219'));
        assertEquals('\u0163', NGram.normalize('\u021b'));
    }

    /**
     * Test method for {@link NGram#get(int)} and {@link NGram#addChar(char)}
     */
    @Test
    public final void testNGram() {
        NGram ngram = new NGram();
        assertEquals(null, ngram.get(0));
        assertEquals(null, ngram.get(1));
        assertEquals(null, ngram.get(2));
        assertEquals(null, ngram.get(3));
        assertEquals(null, ngram.get(4));
        ngram.addChar(' ');
        assertEquals(null, ngram.get(1));
        assertEquals(null, ngram.get(2));
        assertEquals(null, ngram.get(3));
        ngram.addChar('A');
        assertEquals("A", ngram.get(1));
        assertEquals(" A", ngram.get(2));
        assertEquals(null, ngram.get(3));
        ngram.addChar('\u06cc');
        assertEquals("\u064a", ngram.get(1));
        assertEquals("A\u064a", ngram.get(2));
        assertEquals(" A\u064a", ngram.get(3));
        ngram.addChar('\u1ea0');
        assertEquals("\u1ec3", ngram.get(1));
        assertEquals("\u064a\u1ec3", ngram.get(2));
        assertEquals("A\u064a\u1ec3", ngram.get(3));
        ngram.addChar('\u3044');
        assertEquals("\u3042", ngram.get(1));
        assertEquals("\u1ec3\u3042", ngram.get(2));
        assertEquals("\u064a\u1ec3\u3042", ngram.get(3));

        ngram.addChar('\u30a4');
        assertEquals("\u30a2", ngram.get(1));
        assertEquals("\u3042\u30a2", ngram.get(2));
        assertEquals("\u1ec3\u3042\u30a2", ngram.get(3));
        ngram.addChar('\u3106');
        assertEquals("\u3105", ngram.get(1));
        assertEquals("\u30a2\u3105", ngram.get(2));
        assertEquals("\u3042\u30a2\u3105", ngram.get(3));
        ngram.addChar('\uac01');
        assertEquals("\uac00", ngram.get(1));
        assertEquals("\u3105\uac00", ngram.get(2));
        assertEquals("\u30a2\u3105\uac00", ngram.get(3));
        ngram.addChar('\u2010');
        assertEquals(null, ngram.get(1));
        assertEquals("\uac00 ", ngram.get(2));
        assertEquals("\u3105\uac00 ", ngram.get(3));

        ngram.addChar('a');
        assertEquals("a", ngram.get(1));
        assertEquals(" a", ngram.get(2));
        assertEquals(null, ngram.get(3));

    }

    /**
     * Test method for {@link NGram#get(int)} and {@link NGram#addChar(char)}
     */
    @Test
    public final void testNGram3() {
        NGram ngram = new NGram();

        ngram.addChar('A');
        assertEquals("A", ngram.get(1));
        assertEquals(" A", ngram.get(2));
        assertEquals(null, ngram.get(3));

        ngram.addChar('1');
        assertEquals(null, ngram.get(1));
        assertEquals("A ", ngram.get(2));
        assertEquals(" A ", ngram.get(3));

        ngram.addChar('B');
        assertEquals("B", ngram.get(1));
        assertEquals(" B", ngram.get(2));
        assertEquals(null, ngram.get(3));

    }

    /**
     * Test method for {@link NGram#get(int)} and {@link NGram#addChar(char)}
     */
    @Test
    public final void testNormalizeVietnamese() {
        assertEquals("", NGram.normalizeVi(""));
        assertEquals("ABC", NGram.normalizeVi("ABC"));
        assertEquals("012", NGram.normalizeVi("012"));
        assertEquals("\u00c0", NGram.normalizeVi("\u00c0"));

        assertEquals("\u00C0", NGram.normalizeVi("\u0041\u0300"));
        assertEquals("\u00C8", NGram.normalizeVi("\u0045\u0300"));
        assertEquals("\u00CC", NGram.normalizeVi("\u0049\u0300"));
        assertEquals("\u00D2", NGram.normalizeVi("\u004F\u0300"));
        assertEquals("\u00D9", NGram.normalizeVi("\u0055\u0300"));
        assertEquals("\u1EF2", NGram.normalizeVi("\u0059\u0300"));
        assertEquals("\u00E0", NGram.normalizeVi("\u0061\u0300"));
        assertEquals("\u00E8", NGram.normalizeVi("\u0065\u0300"));
        assertEquals("\u00EC", NGram.normalizeVi("\u0069\u0300"));
        assertEquals("\u00F2", NGram.normalizeVi("\u006F\u0300"));
        assertEquals("\u00F9", NGram.normalizeVi("\u0075\u0300"));
        assertEquals("\u1EF3", NGram.normalizeVi("\u0079\u0300"));
        assertEquals("\u1EA6", NGram.normalizeVi("\u00C2\u0300"));
        assertEquals("\u1EC0", NGram.normalizeVi("\u00CA\u0300"));
        assertEquals("\u1ED2", NGram.normalizeVi("\u00D4\u0300"));
        assertEquals("\u1EA7", NGram.normalizeVi("\u00E2\u0300"));
        assertEquals("\u1EC1", NGram.normalizeVi("\u00EA\u0300"));
        assertEquals("\u1ED3", NGram.normalizeVi("\u00F4\u0300"));
        assertEquals("\u1EB0", NGram.normalizeVi("\u0102\u0300"));
        assertEquals("\u1EB1", NGram.normalizeVi("\u0103\u0300"));
        assertEquals("\u1EDC", NGram.normalizeVi("\u01A0\u0300"));
        assertEquals("\u1EDD", NGram.normalizeVi("\u01A1\u0300"));
        assertEquals("\u1EEA", NGram.normalizeVi("\u01AF\u0300"));
        assertEquals("\u1EEB", NGram.normalizeVi("\u01B0\u0300"));

        assertEquals("\u00C1", NGram.normalizeVi("\u0041\u0301"));
        assertEquals("\u00C9", NGram.normalizeVi("\u0045\u0301"));
        assertEquals("\u00CD", NGram.normalizeVi("\u0049\u0301"));
        assertEquals("\u00D3", NGram.normalizeVi("\u004F\u0301"));
        assertEquals("\u00DA", NGram.normalizeVi("\u0055\u0301"));
        assertEquals("\u00DD", NGram.normalizeVi("\u0059\u0301"));
        assertEquals("\u00E1", NGram.normalizeVi("\u0061\u0301"));
        assertEquals("\u00E9", NGram.normalizeVi("\u0065\u0301"));
        assertEquals("\u00ED", NGram.normalizeVi("\u0069\u0301"));
        assertEquals("\u00F3", NGram.normalizeVi("\u006F\u0301"));
        assertEquals("\u00FA", NGram.normalizeVi("\u0075\u0301"));
        assertEquals("\u00FD", NGram.normalizeVi("\u0079\u0301"));
        assertEquals("\u1EA4", NGram.normalizeVi("\u00C2\u0301"));
        assertEquals("\u1EBE", NGram.normalizeVi("\u00CA\u0301"));
        assertEquals("\u1ED0", NGram.normalizeVi("\u00D4\u0301"));
        assertEquals("\u1EA5", NGram.normalizeVi("\u00E2\u0301"));
        assertEquals("\u1EBF", NGram.normalizeVi("\u00EA\u0301"));
        assertEquals("\u1ED1", NGram.normalizeVi("\u00F4\u0301"));
        assertEquals("\u1EAE", NGram.normalizeVi("\u0102\u0301"));
        assertEquals("\u1EAF", NGram.normalizeVi("\u0103\u0301"));
        assertEquals("\u1EDA", NGram.normalizeVi("\u01A0\u0301"));
        assertEquals("\u1EDB", NGram.normalizeVi("\u01A1\u0301"));
        assertEquals("\u1EE8", NGram.normalizeVi("\u01AF\u0301"));
        assertEquals("\u1EE9", NGram.normalizeVi("\u01B0\u0301"));

        assertEquals("\u00C3", NGram.normalizeVi("\u0041\u0303"));
        assertEquals("\u1EBC", NGram.normalizeVi("\u0045\u0303"));
        assertEquals("\u0128", NGram.normalizeVi("\u0049\u0303"));
        assertEquals("\u00D5", NGram.normalizeVi("\u004F\u0303"));
        assertEquals("\u0168", NGram.normalizeVi("\u0055\u0303"));
        assertEquals("\u1EF8", NGram.normalizeVi("\u0059\u0303"));
        assertEquals("\u00E3", NGram.normalizeVi("\u0061\u0303"));
        assertEquals("\u1EBD", NGram.normalizeVi("\u0065\u0303"));
        assertEquals("\u0129", NGram.normalizeVi("\u0069\u0303"));
        assertEquals("\u00F5", NGram.normalizeVi("\u006F\u0303"));
        assertEquals("\u0169", NGram.normalizeVi("\u0075\u0303"));
        assertEquals("\u1EF9", NGram.normalizeVi("\u0079\u0303"));
        assertEquals("\u1EAA", NGram.normalizeVi("\u00C2\u0303"));
        assertEquals("\u1EC4", NGram.normalizeVi("\u00CA\u0303"));
        assertEquals("\u1ED6", NGram.normalizeVi("\u00D4\u0303"));
        assertEquals("\u1EAB", NGram.normalizeVi("\u00E2\u0303"));
        assertEquals("\u1EC5", NGram.normalizeVi("\u00EA\u0303"));
        assertEquals("\u1ED7", NGram.normalizeVi("\u00F4\u0303"));
        assertEquals("\u1EB4", NGram.normalizeVi("\u0102\u0303"));
        assertEquals("\u1EB5", NGram.normalizeVi("\u0103\u0303"));
        assertEquals("\u1EE0", NGram.normalizeVi("\u01A0\u0303"));
        assertEquals("\u1EE1", NGram.normalizeVi("\u01A1\u0303"));
        assertEquals("\u1EEE", NGram.normalizeVi("\u01AF\u0303"));
        assertEquals("\u1EEF", NGram.normalizeVi("\u01B0\u0303"));

        assertEquals("\u1EA2", NGram.normalizeVi("\u0041\u0309"));
        assertEquals("\u1EBA", NGram.normalizeVi("\u0045\u0309"));
        assertEquals("\u1EC8", NGram.normalizeVi("\u0049\u0309"));
        assertEquals("\u1ECE", NGram.normalizeVi("\u004F\u0309"));
        assertEquals("\u1EE6", NGram.normalizeVi("\u0055\u0309"));
        assertEquals("\u1EF6", NGram.normalizeVi("\u0059\u0309"));
        assertEquals("\u1EA3", NGram.normalizeVi("\u0061\u0309"));
        assertEquals("\u1EBB", NGram.normalizeVi("\u0065\u0309"));
        assertEquals("\u1EC9", NGram.normalizeVi("\u0069\u0309"));
        assertEquals("\u1ECF", NGram.normalizeVi("\u006F\u0309"));
        assertEquals("\u1EE7", NGram.normalizeVi("\u0075\u0309"));
        assertEquals("\u1EF7", NGram.normalizeVi("\u0079\u0309"));
        assertEquals("\u1EA8", NGram.normalizeVi("\u00C2\u0309"));
        assertEquals("\u1EC2", NGram.normalizeVi("\u00CA\u0309"));
        assertEquals("\u1ED4", NGram.normalizeVi("\u00D4\u0309"));
        assertEquals("\u1EA9", NGram.normalizeVi("\u00E2\u0309"));
        assertEquals("\u1EC3", NGram.normalizeVi("\u00EA\u0309"));
        assertEquals("\u1ED5", NGram.normalizeVi("\u00F4\u0309"));
        assertEquals("\u1EB2", NGram.normalizeVi("\u0102\u0309"));
        assertEquals("\u1EB3", NGram.normalizeVi("\u0103\u0309"));
        assertEquals("\u1EDE", NGram.normalizeVi("\u01A0\u0309"));
        assertEquals("\u1EDF", NGram.normalizeVi("\u01A1\u0309"));
        assertEquals("\u1EEC", NGram.normalizeVi("\u01AF\u0309"));
        assertEquals("\u1EED", NGram.normalizeVi("\u01B0\u0309"));

        assertEquals("\u1EA0", NGram.normalizeVi("\u0041\u0323"));
        assertEquals("\u1EB8", NGram.normalizeVi("\u0045\u0323"));
        assertEquals("\u1ECA", NGram.normalizeVi("\u0049\u0323"));
        assertEquals("\u1ECC", NGram.normalizeVi("\u004F\u0323"));
        assertEquals("\u1EE4", NGram.normalizeVi("\u0055\u0323"));
        assertEquals("\u1EF4", NGram.normalizeVi("\u0059\u0323"));
        assertEquals("\u1EA1", NGram.normalizeVi("\u0061\u0323"));
        assertEquals("\u1EB9", NGram.normalizeVi("\u0065\u0323"));
        assertEquals("\u1ECB", NGram.normalizeVi("\u0069\u0323"));
        assertEquals("\u1ECD", NGram.normalizeVi("\u006F\u0323"));
        assertEquals("\u1EE5", NGram.normalizeVi("\u0075\u0323"));
        assertEquals("\u1EF5", NGram.normalizeVi("\u0079\u0323"));
        assertEquals("\u1EAC", NGram.normalizeVi("\u00C2\u0323"));
        assertEquals("\u1EC6", NGram.normalizeVi("\u00CA\u0323"));
        assertEquals("\u1ED8", NGram.normalizeVi("\u00D4\u0323"));
        assertEquals("\u1EAD", NGram.normalizeVi("\u00E2\u0323"));
        assertEquals("\u1EC7", NGram.normalizeVi("\u00EA\u0323"));
        assertEquals("\u1ED9", NGram.normalizeVi("\u00F4\u0323"));
        assertEquals("\u1EB6", NGram.normalizeVi("\u0102\u0323"));
        assertEquals("\u1EB7", NGram.normalizeVi("\u0103\u0323"));
        assertEquals("\u1EE2", NGram.normalizeVi("\u01A0\u0323"));
        assertEquals("\u1EE3", NGram.normalizeVi("\u01A1\u0323"));
        assertEquals("\u1EF0", NGram.normalizeVi("\u01AF\u0323"));
        assertEquals("\u1EF1", NGram.normalizeVi("\u01B0\u0323"));

    }
}