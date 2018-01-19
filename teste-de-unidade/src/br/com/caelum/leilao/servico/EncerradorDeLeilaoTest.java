package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import static org.mockito.Mockito.*;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.infra.dao.LeilaoDao;

public class EncerradorDeLeilaoTest {

	@Test
	public void deveEncerrarLeiloesQueComecaramUmaSemanaAntes(){
		Calendar  antiga = Calendar.getInstance();
		antiga.set(1999, 1, 20);
		
		Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();
		Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();
		
		LeilaoDao daoFalso = mock(LeilaoDao.class);
		
		List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);
		when(daoFalso.correntes()).thenReturn(leiloesAntigos);
		
		daoFalso.salva(leilao1);
		daoFalso.salva(leilao2);
		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso);
		
		encerrador.encerra();
		
		assertEquals(2, encerrador.getTotalEncerrados());
		assertTrue(leilao1.isEncerrado());
		assertTrue(leilao1.isEncerrado());
		
	}
	
}
