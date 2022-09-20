package titanic.daos;

import java.util.ArrayList;

import titanic.clases.BoteSalvavidas;
import titanic.enums.TipoZona;

public class DAOBotes {
	private static DAOBotes dao = null;
	private static ArrayList<BoteSalvavidas> lista = null;
	
	private DAOBotes() {
		
	}
	
	public static DAOBotes getInstance() {
		if (dao == null) {
			dao = new DAOBotes();
		}
		
		if (lista == null) {
			lista = new ArrayList<BoteSalvavidas>();
		}
		
		return dao;
	}
	
	public ArrayList<BoteSalvavidas> getBotes() {
		if (lista.isEmpty()) {
			BoteSalvavidas bote1 = new BoteSalvavidas(7, TipoZona.BABOR);
			lista.add(bote1);
			BoteSalvavidas bote2 = new BoteSalvavidas(10, TipoZona.ESTRIBOR);
			lista.add(bote2);
			BoteSalvavidas bote3 = new BoteSalvavidas(17, TipoZona.POPA);
			lista.add(bote3);
			BoteSalvavidas bote4 = new BoteSalvavidas(77, TipoZona.PROA);
			lista.add(bote4);
		}
		
		return lista;
	}
}