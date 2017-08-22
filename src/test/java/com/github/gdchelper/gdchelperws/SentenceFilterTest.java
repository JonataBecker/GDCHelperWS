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
//        neutro	0.0	ruim (33,33%)	-------- mensagem encaminhada --------
//        erro: data: mon, 7 aug 2017 19:55:47 +0000
//erro: de: matheus henrique klein - rech informatica <klein@rech.com.br
//bom	0.480962	ruim (48,81%)	para: lucas eduardo machado - rech informatica
//bom	0.587043	ruim (48,07%)	cc: 'diogo@laboratoriolebon.com.br'
//bom	0.700242	ruim (56,08%)	assunto: liberacao para usuario da daniela
//bom	0.60198	ruim (44,61%)	para: 'knewitz, deise'
//bom	0.739874	ruim (63,10%)	cc: 'lojanh@reginatometais.com.br' <lojanh@reginatometais.com.br
//bom	0.493565	ruim (33,33%)	adicionais liberados: 40-gav, 48-nfe
//mbom	0.827747	ruim (33,33%)	modulos liberados: 1-ctb, 2-gpa, 3-fpa, 4-lfi, 10-aue

    }
    
}
