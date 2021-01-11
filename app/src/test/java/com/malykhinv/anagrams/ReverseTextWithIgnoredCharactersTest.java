package com.malykhinv.anagrams;

import android.app.Activity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReverseTextWithIgnoredCharactersTest {

    MainActivity ma = new MainActivity();

    @Test
    public void testReverseText() {
        assertEquals("drowssaP: tnio1234pDRO_Wssap", ma.reverseText("Password: pass1234WORD_point"));
    }

    @Test
    public void addCharacterToIgnoreList() {
        assertEquals("\"\\!#$%&'()*+,-./0123456789:;<=>?@[]^_`{|}~Zzjm", ma.addCharactersToIgnoreList("Zzjjj8m"));
    }
}