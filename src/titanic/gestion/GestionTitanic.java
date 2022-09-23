package titanic.gestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import titanic.clases.BoteSalvavidas;
import titanic.clases.Pasajero;
import titanic.clases.Persona;
import titanic.clases.Persona.ComparadorEdad;
import titanic.clases.Tripulacion;
import titanic.clases.Tripulacion.ComparadorResponsabilidad;
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
		ArrayList<Pasajero> listaPasajeros = new ArrayList<Pasajero>();
		ArrayList<Tripulacion> listaTripulantes = new ArrayList<Tripulacion>();

		for (Persona persona : listaPersonas) {
			if (persona instanceof Pasajero) {
				listaPasajeros.add((Pasajero) persona);
			} else {
				listaTripulantes.add((Tripulacion) persona);
			}
		}

		Collections.sort(listaPasajeros, new ComparadorEdad());
		Collections.sort(listaTripulantes, new ComparadorResponsabilidad());

		meterNiniosFamiliar(listaPasajeros);
		meterAncianos(listaPasajeros);
		meterAdultos(listaPasajeros);
		meterTripulacion(listaTripulantes);
	}

	private void meterTripulacion(ArrayList<Tripulacion> listaTripulantes) {
		for (Tripulacion trip : listaTripulantes) {
			if (mapaBotes.get(obtenerBoteLibre(trip.getZona())) == null) {
				mapaBotes.put(obtenerBoteLibre(trip.getZona()), new ArrayList<Persona>());
			}
			
			BoteSalvavidas bote = obtenerBoteLibre(trip.getZona());

			mapaBotes.get(bote).add(trip);
			listaTripulantes.remove(trip);

			bote.setNumPlaza(bote.getNumPlaza() - 1);
		}
	}

	private void meterAdultos(ArrayList<Pasajero> listaPasajeros) {
		for (Pasajero pasajero : listaPasajeros) {
			if (!pasajero.esAnciano() && pasajero.esMayorEdad()) {
				if (mapaBotes.get(obtenerBoteLibre(pasajero.getZona())) == null) {
					mapaBotes.put(obtenerBoteLibre(pasajero.getZona()), new ArrayList<Persona>());
				}
				
				BoteSalvavidas bote = obtenerBoteLibre(pasajero.getZona());

				mapaBotes.get(bote).add(pasajero);
				listaPasajeros.remove(pasajero);

				bote.setNumPlaza(bote.getNumPlaza() - 1);
			}
		}
	}

	private void meterAncianos(ArrayList<Pasajero> listaPasajeros) {
		for (Pasajero pasajero : listaPasajeros) {
			if (pasajero.esAnciano()) {
				if (mapaBotes.get(obtenerBoteLibre(pasajero.getZona())) == null) {
					mapaBotes.put(obtenerBoteLibre(pasajero.getZona()), new ArrayList<Persona>());
				}
				
				BoteSalvavidas bote = obtenerBoteLibre(pasajero.getZona());

				mapaBotes.get(bote).add(pasajero);
				listaPasajeros.remove(pasajero);

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

	public void meterNiniosFamiliar(ArrayList<Pasajero> listaPasajeros) {
		ArrayList<Pasajero> listaPadres = new ArrayList<Pasajero>();
		ArrayList<Pasajero> listaFamilia = new ArrayList<Pasajero>();

		for (Pasajero pasajero : listaFamilia) {
			if (!pasajero.esMayorEdad()) {
				if (mapaBotes.get(obtenerBoteLibre(pasajero.getZona())) == null) {
					mapaBotes.put(obtenerBoteLibre(pasajero.getZona()), new ArrayList<Persona>());
				}
				listaFamilia.add(pasajero);
				listaPasajeros.remove(pasajero);

				for (Pasajero pasajeroFamiliar : listaPasajeros) {
					if (!pasajero.esMayorEdad()) {
						if (pasajero.getNumHab() == pasajero.getNumHab()) {
							listaFamilia.add(pasajeroFamiliar);
							listaPasajeros.remove(pasajeroFamiliar);
						}
					} else {
						if (pasajero.getNumHab() == pasajero.getNumHab()) {
							listaPadres.add(pasajeroFamiliar);
						}
					}
				}
				if (!listaPadres.isEmpty()) {
					Collections.shuffle(listaPadres);
					listaFamilia.add(listaPadres.get(0));
					listaPasajeros.remove(listaPadres.get(0));
					listaPadres.clear();
				}
				mapaBotes.get(obtenerBoteLibreFamilia(pasajero.getZona(), listaFamilia.size())).addAll(listaFamilia);
				listaFamilia.clear();
			}
		}
	}
}