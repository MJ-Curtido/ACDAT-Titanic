package titanic.vista;

import java.util.Iterator;
import titanic.clases.BoteSalvavidas;
import titanic.clases.Persona;
import java.util.ArrayList;
import java.util.Map;

public class Vista {
	public void mostrarMapa(Map<BoteSalvavidas, ArrayList<Persona>> mapa) throws InterruptedException {
		Iterator it = mapa.keySet().iterator();
		while (it.hasNext()) {
			BoteSalvavidas bote = (BoteSalvavidas) it.next();
			ArrayList<Persona> lista = mapa.get(bote);
			
			System.out.println(bote + ": " + lista.toString());
			
			Thread.sleep(1000);
		}
	}
}