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
	private ArrayList<BoteSalvavidas> listaBotes;

	public GestionTitanic() {
		this.listaBotes = DAOBotes.getInstance().getBotes();
		this.mapaBotes = new HashMap<BoteSalvavidas, ArrayList<Persona>>();
		
		llenarMapaBotes();
	}
	
	public void llenarMapaBotes() {
		ArrayList<Persona> listaPersonas = DAOPersonas.getInstance().getPersonas();
		
		for (Persona persona : listaPersonas) {
			meterNiniosFamiliar(persona, listaPersonas);
			meterAncianos(persona, listaPersonas);
			meterAdultos(persona, listaPersonas);
			meterTripulacion(persona, listaPersonas);
		}
	}
	
	private void meterTripulacion(Persona persona, ArrayList<Persona> listaPersonas) {
		//hacerlo
	}

	private void meterAdultos(Persona persona, ArrayList<Persona> listaPersonas) {
		if (persona instanceof Pasajero && !persona.esAnciano() && persona.esMayorEdad()) {
			BoteSalvavidas bote = obtenerBoteLibre(persona.getZona());
			
			mapaBotes.get(bote).add(persona);
			listaPersonas.remove(persona);
			
			bote.setNumPlaza(bote.getNumPlaza() - 1);
		}
	}

	private void meterAncianos(Persona persona, ArrayList<Persona> listaPersonas) {
		if (persona.esAnciano() && persona instanceof Pasajero) {
			BoteSalvavidas bote = obtenerBoteLibre(persona.getZona());
			
			mapaBotes.get(obtenerBoteLibre(persona.getZona())).add(persona);
			listaPersonas.remove(persona);
			
			bote.setNumPlaza(bote.getNumPlaza() - 1);
		}
	}

	public BoteSalvavidas obtenerBoteLibre(TipoZona zona) {
		BoteSalvavidas bote = null;
		
		for (BoteSalvavidas boteSalvavidas : listaBotes) {
			if (boteSalvavidas.getZona() == zona && boteSalvavidas.getNumPlaza() > 0) {
				bote = boteSalvavidas;
			}
		}
		
		return bote;
	}
	
	public void meterNiniosFamiliar(Persona persona, ArrayList<Persona> listaPersonas) {
		ArrayList<Persona> listaPadres = new ArrayList<Persona>();
		
		if (!persona.esMayorEdad() && persona instanceof Pasajero) {
			if (mapaBotes.get(obtenerBoteLibre(persona.getZona())) == null) {
				mapaBotes.put(obtenerBoteLibre(persona.getZona()), new ArrayList<Persona>());
			}
			
			mapaBotes.get(obtenerBoteLibre(persona.getZona())).add(persona);
			listaPersonas.remove(persona);
			
			for (Persona personaFamiliar : listaPersonas) {
				if (personaFamiliar instanceof Pasajero) {
					if (!persona.esMayorEdad()) {
						Pasajero pasajero = (Pasajero) persona;
						Pasajero pasajeroFamiliar = (Pasajero) personaFamiliar;
						
						if (pasajero.getNumHab() == pasajero.getNumHab()) {
							mapaBotes.get(obtenerBoteLibre(persona.getZona())).add(personaFamiliar);
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
				mapaBotes.get(obtenerBoteLibre(persona.getZona())).add(listaPadres.get(0));
				listaPersonas.remove(listaPadres.get(0));
				listaPadres.clear();
			}
		}
	}
}