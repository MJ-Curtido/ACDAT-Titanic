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
			BoteSalvavidas bote = obtenerBoteLibre(trip.getZona(), 1);

			if (bote != null) {
				if (mapaBotes.get(bote) == null) {
					mapaBotes.put(bote, new ArrayList<Persona>());
				}
				
				mapaBotes.get(bote).add(trip);
				listaTripulantes.remove(trip);

				bote.setNumPlaza(bote.getNumPlaza() - 1);
			}
		}
	}

	private void meterAdultos(ArrayList<Pasajero> listaPasajeros) {
		for (Pasajero pasajero : listaPasajeros) {
			if (!pasajero.esAnciano() && pasajero.esMayorEdad()) {
				BoteSalvavidas bote = obtenerBoteLibre(pasajero.getZona(), 1);

				if (bote != null) {
					if (mapaBotes.get(bote) == null) {
						mapaBotes.put(bote, new ArrayList<Persona>());
					}
					
					mapaBotes.get(bote).add(pasajero);
					listaPasajeros.remove(pasajero);

					bote.setNumPlaza(bote.getNumPlaza() - 1);
				}
			}
		}
	}

	private void meterAncianos(ArrayList<Pasajero> listaPasajeros) {
		for (Pasajero pasajero : listaPasajeros) {
			if (pasajero.esAnciano()) {
				BoteSalvavidas bote = obtenerBoteLibre(pasajero.getZona(), 1);

				if (bote != null) {
					if (mapaBotes.get(bote) == null) {
						mapaBotes.put(bote, new ArrayList<Persona>());
					}
					
					mapaBotes.get(bote).add(pasajero);
					listaPasajeros.remove(pasajero);

					bote.setNumPlaza(bote.getNumPlaza() - 1);
				}
			}
		}
	}

	public BoteSalvavidas obtenerBoteLibre(TipoZona zona, Integer num) {
		BoteSalvavidas bote = null;
		Integer siguienteZona = 0;

		for (BoteSalvavidas boteSalvavidas : listaBotes) {
			if (boteSalvavidas.getZona() == zona && boteSalvavidas.getNumPlaza() >= num) {
				bote = boteSalvavidas;
			}
		}

		if (bote == null) {
			final Integer ZONAPERSONA;

			for (int i = 0; i < TipoZona.values().length && TipoZona.values()[i] != zona; i++) {
				if (TipoZona.values()[i] == zona) {
					if (i == TipoZona.values().length - 1) {
						siguienteZona = 0;
					} else {
						siguienteZona = ++i;
					}
				}
			}

			if (siguienteZona != 0) {
				ZONAPERSONA = --siguienteZona;
			} else {
				ZONAPERSONA = 0;
			}

			while (siguienteZona != ZONAPERSONA) {
				for (BoteSalvavidas boteSalvavidas : listaBotes) {
					if (boteSalvavidas.getZona() == TipoZona.values()[siguienteZona]
							&& boteSalvavidas.getNumPlaza() >= num) {
						bote = boteSalvavidas;
					}
				}

				if (bote == null) {
					for (int i = 0; i < TipoZona.values().length
							&& TipoZona.values()[i] != TipoZona.values()[siguienteZona]; i++) {
						if (TipoZona.values()[i] == zona) {
							if (i == TipoZona.values().length - 1) {
								siguienteZona = 0;
							} else {
								siguienteZona = ++i;
							}
						}
					}
				}
			}
		}
		return bote;
	}

	public void meterNiniosFamiliar(ArrayList<Pasajero> listaPasajeros) {
		ArrayList<Pasajero> listaPadres = new ArrayList<Pasajero>();
		ArrayList<Pasajero> listaFamilia = new ArrayList<Pasajero>();

		for (Pasajero pasajero : listaPasajeros) {
			if (!pasajero.esMayorEdad()) {
				listaFamilia.add(pasajero);
				listaPasajeros.remove(pasajero);

				for (Pasajero pasajeroFamiliar : listaPasajeros) {
					if (!pasajero.esMayorEdad() && pasajero.getNumHab() == pasajero.getNumHab()) {
						listaFamilia.add(pasajeroFamiliar);
						listaPasajeros.remove(pasajeroFamiliar);
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

				BoteSalvavidas bote = obtenerBoteLibre(pasajero.getZona(), listaFamilia.size());

				if (bote != null) {
					if (mapaBotes.get(bote) == null) {
						mapaBotes.put(bote, new ArrayList<Persona>());
					}

					mapaBotes.get(bote).addAll(listaFamilia);
					listaFamilia.clear();
				}
			}
		}
	}
}