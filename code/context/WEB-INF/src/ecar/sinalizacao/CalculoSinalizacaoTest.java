package ecar.sinalizacao;

import java.util.List;

import ecar.dao.AcompRealFisicoDao;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.CorDao;
import ecar.dao.ItemEstrtIndResulDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.Cor;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.sinalizacao.dao.SinalizacaoDao;
import ecar.util.Dominios;

public class CalculoSinalizacaoTest {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(null);
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);
		CalculoSinalizacao calculoSinalizacao;
		try {
			AcompReferenciaItemAri ari = (AcompReferenciaItemAri) acompReferenciaItemDao.localizar(
					AcompReferenciaItemAri.class, new Long(21));
			
			List<AcompRealFisicoArf> indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, Dominios.TODOS, false);
			
			for (AcompRealFisicoArf acompRealFisicoArf : indResultados) {
				System.out.println("############ " + acompRealFisicoArf.getItemEstrtIndResulIettr().getNomeIettir() + " ############");
				calculoSinalizacao = new CalculoSinalizacao(ari, acompRealFisicoArf);
				
				calculoSinalizacao.execute();
				
				System.out.println(calculoSinalizacao.getFaixa());
				if(acompRealFisicoArf.getItemEstrtIndResulIettr().getConsiderarLinhaDeBase()) {
					System.out.println(calculoSinalizacao.getFaixaLinhaDeBase());					
				}
				
			}
		} catch (ECARException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void vincularSinalizacao() {
		ItemEstrtIndResulDao indDao = new ItemEstrtIndResulDao(null);
		SinalizacaoDao sinalizacaoDao = new SinalizacaoDao();
		
		try {
			ItemEstrtIndResulIettr indResulIettr = (ItemEstrtIndResulIettr) indDao.localizar(ItemEstrtIndResulIettr.class, new Long(9));
			
			Sinalizacao sinalizacao = (Sinalizacao) sinalizacaoDao.localizar(Sinalizacao.class, new Long(1));
			
			indResulIettr.setSinalizacao(sinalizacao);
			
			sinalizacao.addItemEstrtIndResulIettr(indResulIettr);
			
			indDao.alterar(indResulIettr);
			
		} catch (ECARException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveSinalizacao() {
		try {
			SinalizacaoDao dao = new SinalizacaoDao();
			CorDao corDao = new CorDao(null);
			
			Sinalizacao sinalizacao = new Sinalizacao();
			sinalizacao.setIdentificacao("Quanto menor, melhor");
			sinalizacao.setPolaridade(false);
			
			Faixa vermelho = new Faixa();
			
			vermelho.setMin(0.0);
			vermelho.setMax(25.0);
			vermelho.setSinalizacao(sinalizacao);
		
			vermelho.setCor((Cor)corDao.localizar(Cor.class, new Long(3)));
			
			sinalizacao.addFaixa(vermelho);
			
			Faixa amarelo = new Faixa();
			
			amarelo.setMin(25.1);
			amarelo.setMax(50.0);
			amarelo.setSinalizacao(sinalizacao);
		
			amarelo.setCor((Cor)corDao.localizar(Cor.class, new Long(2)));
			
			sinalizacao.addFaixa(amarelo);
			
			Faixa azul = new Faixa();
			
			azul.setMin(50.1);
			azul.setMax(75.0);
			azul.setSinalizacao(sinalizacao);
		
			azul.setCor((Cor)corDao.localizar(Cor.class, new Long(4)));
			
			sinalizacao.addFaixa(azul);
			
			Faixa verde = new Faixa();
			
			verde.setMin(75.1);
			verde.setMax(1000.0);
			verde.setSinalizacao(sinalizacao);
		
			verde.setCor((Cor)corDao.localizar(Cor.class, new Long(1)));
			
			sinalizacao.addFaixa(verde);
			
			dao.salvar(sinalizacao);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
