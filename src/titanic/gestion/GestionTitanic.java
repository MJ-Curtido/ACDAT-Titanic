package titanic.gestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import titanic.clases.BoteSalvavidas;
import titanic.clases.Persona;
import titanic.daos.DAOPersonas;

public class GestionTitanic {
	private Map<BoteSalvavidas, ArrayList<Persona>> mapaBotes;

	public GestionTitanic(Map<BoteSalvavidas, ArrayList<Persona>> mapaBotes) {
		this.mapaBotes = new HashMap<BoteSalvavidas, ArrayList<Persona>>();
		
		llenarMapaBotes();
	}
	
	public void llenarMapaBotes() {
		ArrayList<Persona> listaPersonas = DAOPersonas.getInstance().getPersonas();
		
		for (Persona persona : listaPersonas) {
			if (!persona.esMayorEdad()) {
				
				
				for (Persona personaFamiliar : listaPersonas) {
					
				}
			}
		}
	}
}