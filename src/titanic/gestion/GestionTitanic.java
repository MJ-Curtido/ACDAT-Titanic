package titanic.gestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import titanic.clases.BoteSalvavidas;
import titanic.clases.Pasajero;
import titanic.clases.Persona;
import titanic.daos.DAOBotes;
import titanic.daos.DAOPersonas;
import titanic.enums.TipoZona;

public class GestionTitanic {
	private Map<BoteSalvavidas, ArrayList<Persona>> mapaBotes;

	public GestionTitanic(Map<BoteSalvavidas, ArrayList<Persona>> mapaBotes) {
		this.mapaBotes = new HashMap<BoteSalvavidas, ArrayList<Persona>>();
		
		llenarMapaBotes();
	}
	
	public void llenarMapaBotes() {
		ArrayList<Persona> listaPersonas = DAOPersonas.getInstance().getPersonas();
		
		for (Persona persona : listaPersonas) {
			meterNiniosFamiliar(persona, listaPersonas);
		}
	}
	
	public BoteSalvavidas obtenerBote(TipoZona zona) {
		ArrayList<BoteSalvavidas> listaBotes = DAOBotes.getInstance().getBotes();
		
		BoteSalvavidas bote = null;
		
		for (BoteSalvavidas boteSalvavidas : listaBotes) {
			if (boteSalvavidas.getZona() == zona) {
				bote = boteSalvavidas;
			}
		}
		
		return bote;
	}
	
	public void meterNiniosFamiliar(Persona persona, ArrayList<Persona> listaPersonas) {
		ArrayList<Persona> listaPadres = new ArrayList<Persona>();
		
		if (!persona.esMayorEdad()) {
			if (mapaBotes.get(obtenerBote(persona.getZona())) == null) {
				mapaBotes.put(obtenerBote(persona.getZona()), new ArrayList<Persona>());
			}
			
			mapaBotes.get(obtenerBote(persona.getZona())).add(persona);
			listaPersonas.remove(persona);
			
			for (Persona personaFamiliar : listaPersonas) {
				if (personaFamiliar instanceof Pasajero) {
					if (!persona.esMayorEdad()) {
						Pasajero pasajero = (Pasajero) persona;
						Pasajero pasajeroFamiliar = (Pasajero) personaFamiliar;
						
						if (pasajero.getNumHab() == pasajero.getNumHab()) {
							mapaBotes.get(obtenerBote(persona.getZona())).add(personaFamiliar);
							listaPersonas.remove(personaFamiliar);
						}
					}
					else {
						if (personaFamiliar instanceof Pasajero) {
							Pasajero pasajero = (Pasajero) persona;
							Pasajero pasajeroFamiliar = (Pasajero) personaFamiliar;
							
							if (pasajero.getNumHab() == pasajero.getNumHab()) {
								listaPadres.add(pasajeroFamiliar);
							}
						}
					}
				}
			}
			if (!listaPadres.isEmpty()) {
				Collections.shuffle(listaPadres);
				mapaBotes.get(obtenerBote(persona.getZona())).add(listaPadres.get(0));
				listaPersonas.remove(listaPadres.get(0));
				listaPadres.clear();
			}
		}
	}
}