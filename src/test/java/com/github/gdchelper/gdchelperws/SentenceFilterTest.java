package com.github.gdchelper.gdchelperws;

import com.github.gdchelper.jpa.Atendimento;
import org.junit.Test;
import static org.junit.Assert.*;

public class SentenceFilterTest {

    @Test
    public void testAccept() {
        SentenceFilter filter = new SentenceFilter();
        assertTrue(filter.test(new Atendimento("oi bom dia!")));
        assertFalse(filter.test(new Atendimento("Oi")));
        assertFalse(filter.test(new Atendimento("Comovai")));
        assertFalse(filter.test(new Atendimento("aiusheuhauiehae")));
        assertFalse(filter.test(new Atendimento("To: nicolaspohren@gmail.com")));
        assertFalse(filter.test(new Atendimento("Subject: nicolaspohren@gmail.com")));
        assertFalse(filter.test(new Atendimento("From: nicolaspohren@gmail.com")));
        assertFalse(filter.test(new Atendimento("enviada em: terca-feira, 21 de setembro de 2010 14:14")));
        assertFalse(filter.test(new Atendimento("enviada: terca-feira, 21 de setembro de 2010 14:14")));
        assertFalse(filter.test(new Atendimento("[problema]")));
        assertFalse(filter.test(new Atendimento("-------- mensagem encaminhada --------")));
        assertFalse(filter.test(new Atendimento("data: mon, 7 aug 2017 19:55:47 +0000")));
        assertFalse(filter.test(new Atendimento("de: matheus henrique klein - rech informatica <klein@rech.com.br")));
        assertFalse(filter.test(new Atendimento("para: lucas eduardo machado - rech informatica")));
        assertFalse(filter.test(new Atendimento("cc: 'diogo@laboratoriolebon.com.br'")));
        assertFalse(filter.test(new Atendimento("assunto: liberacao para usuario da daniela")));
//bom	0.493565	ruim (33,33%)	adicionais liberados: 40-gav, 48-nfe
//mbom	0.827747	ruim (33,33%)	modulos liberados: 1-ctb, 2-gpa, 3-fpa, 4-lfi, 10-aue

    }
    
}
