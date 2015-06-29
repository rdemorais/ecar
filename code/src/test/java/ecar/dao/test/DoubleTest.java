package ecar.dao.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


public class DoubleTest {

	public void converteDouble(String valor) {
		Double valorDouble = 0.0;
		if (valor.contains(",")){
			valorDouble = Double.parseDouble(valor.replaceAll("\\.","").replace(",","."));
		} else {
			valorDouble = Double.parseDouble(valor);
		}
		System.out.println(valorDouble);  
	}
	
	public void converteDouble2(String valor){
		BigDecimal d = null;
		if (valor.contains(",")){
			valor = valor.replaceAll("\\.","").replace(",",".");
			d = new BigDecimal(valor);
		} else {
			d = new BigDecimal(valor);
		}
		System.out.println(d);
	}
	
	private boolean verificaFinalizado(String codIett, BigInteger finalizado){
		boolean isFinalizado = false;
		if(codIett.equals(finalizado)){
			isFinalizado = true;
		}
		return isFinalizado;
	}

	public static void main(String[] args) {
		DoubleTest d = new DoubleTest();
		d.converteDouble2("13,6");
	}

}
