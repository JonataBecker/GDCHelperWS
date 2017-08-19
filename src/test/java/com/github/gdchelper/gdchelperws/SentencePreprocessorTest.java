/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gdchelper.gdchelperws;

import com.github.gdchelper.db.SentencePreprocessor;
import static org.junit.Assert.*;

/**
 *
 * @author Pichau
 */
public class SentencePreprocessorTest {
    
    @org.junit.Test
    public void testSomeMethod() {
        SentencePreprocessor processor = new SentencePreprocessor();
        assertEquals("oi bom dia!", processor.process("Oi bom dia!"));
        assertEquals("maca", processor.process("Maça"));
        assertEquals("houve erro ao enviar", processor.process("*** Houve erro ao enviar"));
        assertEquals("claro, antes da aprovacao mandamos o orcamento", processor.process("- Rodolfo: Claro, antes da aprovação mandamos o orçamento"));
        assertEquals("realizei acesso remoto", processor.process("- Alex: *** Realizei acesso remoto"));
        assertEquals("e escreva o frase", processor.process("[10:23:41] Rozane de Siqueira Veiga: e escreva o frase"));
        assertEquals("ok", processor.process("- fábio: ok"));
        assertEquals("deve informar sim", processor.process("[05/10/2010 11:19:19] franciele reichert: deve informar sim"));
        assertEquals("item 1", processor.process("- item 1"));
        assertEquals("com tab", processor.process("com\ttab"));
        assertEquals("espacos repetidos ou nao tanto", processor.process("espaços    repetidos ou não  tanto"));
        assertEquals("se ao gravar o registro 510", processor.process("---> se ao gravar o registro 510"));
        assertEquals("acessar o fat 1.2-a", processor.process(">>> acessar o fat 1.2-a"));
        assertEquals("sim", processor.process("<piv2600-1-sab>: sim"));
    }
    
}
