package com.github.gdchelper.gdchelperws;

import com.github.gdchelper.jpa.Atendimento;
import org.junit.Test;
import static org.junit.Assert.*;

public class SentenceFilterTest {

    @Test
    public void testTest() {
        SentenceFilter filter = new SentenceFilter();
        assertTrue(filter.test(new Atendimento("oi bom dia!")));
        assertFalse(filter.test(new Atendimento("Oi")));
        assertFalse(filter.test(new Atendimento("To: nicolaspohren@gmail.com")));
        assertFalse(filter.test(new Atendimento("Subject: nicolaspohren@gmail.com")));
        assertFalse(filter.test(new Atendimento("From: nicolaspohren@gmail.com")));
        assertFalse(filter.test(new Atendimento("enviada em: terca-feira, 21 de setembro de 2010 14:14")));
        assertFalse(filter.test(new Atendimento("enviada: terca-feira, 21 de setembro de 2010 14:14")));
    }
    
}
