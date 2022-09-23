package titanic.gestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import titanic.clases.BoteSalvavidas;
import titanic.clases.Pasajero;
import titanic.clases.Persona;
import titanic.clases.Tripulacion;
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
		
		meterNiniosFamiliar(listaPersonas);
		meterAncianos(listaPersonas);
		meterAdultos(listaPersonas);
		meterTripulacion(listaPersonas);
	}
	
	private void meterTripulacion(ArrayList<Persona> listaPersonas) {
		for (Persona persona : listaPersonas) {
			if (persona instanceof Tripulacion) {
				BoteSalvavidas bote = obtenerBoteLibre(persona.getZona());
				
				mapaBotes.get(bote).add(persona);
				listaPersonas.remove(persona);
				
				bote.setNumPlaza(bote.getNumPlaza() - 1);
			}
		}
	}

	private void meterAdultos(ArrayList<Persona> listaPersonas) {
		for (Persona persona : listaPersonas) {
			if (persona instanceof Pasajero && !persona.esAnciano() && persona.esMayorEdad()) {
				BoteSalvavidas bote = obtenerBoteLibre(persona.getZona());
				
				mapaBotes.get(bote).add(persona);
				listaPersonas.remove(persona);
				
				bote.setNumPlaza(bote.getNumPlaza() - 1);
			}
		}
	}

	private void meterAncianos(ArrayList<Persona> listaPersonas) {
		for (Persona persona : listaPersonas) {
			if (persona.esAnciano() && persona instanceof Pasajero) {
				BoteSalvavidas bote = obtenerBoteLibre(persona.getZona());
				
				mapaBotes.get(obtenerBoteLibre(persona.getZona())).add(persona);
				listaPersonas.remove(persona);
				
				bote.setNumPlaza(bote.getNumPlaza() - 1);
			}
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
	
	public BoteSalvavidas obtenerBoteLibreFamilia(TipoZona zona, Integer num) {
		BoteSalvavidas bote = null;
		
		for (BoteSalvavidas boteSalvavidas : listaBotes) {
			if (boteSalvavidas.getZona() == zona && boteSalvavidas.getNumPlaza() >= num) {
				bote = boteSalvavidas;
			}
		}
		
		return bote;
	}
	
	public void meterNiniosFamiliar(ArrayList<Persona> listaPersonas) {
		ArrayList<Persona> listaPadres = new ArrayList<Persona>();
		ArrayList<Persona> listaFamilia= new ArrayList<Persona>();
		
		for (Persona persona : listaFamilia) {
			if (!persona.esMayorEdad() && persona instanceof Pasajero) {
				if (mapaBotes.get(obtenerBoteLibre(persona.getZona())) == null) {
					mapaBotes.put(obtenerBoteLibre(persona.getZona()), new ArrayList<Persona>());
				}
				listaFamilia.add(persona);
				listaPersonas.remove(persona);
				
				for (Persona personaFamiliar : listaPersonas) {
					if (personaFamiliar instanceof Pasajero) {
						if (!persona.esMayorEdad()) {
							Pasajero pasajero = (Pasajero) persona;
							Pasajero pasajeroFamiliar = (Pasajero) personaFamiliar;
							
							if (pasajero.getNumHab() == pasajero.getNumHab()) {
								listaFamilia.add(personaFamiliar);
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
					listaFamilia.add(listaPadres.get(0));
					listaPersonas.remove(listaPadres.get(0));
					listaPadres.clear();
				}
				mapaBotes.get(obtenerBoteLibreFamilia(persona.getZona(), listaFamilia.size())).addAll(listaFamilia);
				listaFamilia.clear();
			}
		}
	}
}