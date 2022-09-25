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
	
	public Map<BoteSalvavidas, ArrayList<Persona>> obtenerMapa() {
		return mapaBotes;
	}

	public void llenarMapaBotes() {
		ArrayList<Persona> listaPersonas = DAOPersonas.getInstance().getPersonas();
		ArrayList<Pasajero> listaPasajeros = new ArrayList<Pasajero>();
		ArrayList<Tripulacion> listaTripulantes = new ArrayList<Tripulacion>();

		for (int i = 0; i < listaPersonas.size(); i++) {
			if (listaPersonas.get(i) instanceof Pasajero) {
				listaPasajeros.add((Pasajero) listaPersonas.get(i));
			} else {
				listaTripulantes.add((Tripulacion) listaPersonas.get(i));
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
		for (int i = 0; i < listaTripulantes.size(); i++) {
			BoteSalvavidas bote = obtenerBoteLibre(listaTripulantes.get(i).getZona(), 1);

			if (bote != null) {
				if (mapaBotes.get(bote) == null) {
					mapaBotes.put(bote, new ArrayList<Persona>());
				}
				
				mapaBotes.get(bote).add(listaTripulantes.get(i));
				listaTripulantes.remove(listaTripulantes.get(i));
				
				i--;

				bote.setNumPlaza(bote.getNumPlaza() - 1);
			}
		}
	}

	private void meterAdultos(ArrayList<Pasajero> listaPasajeros) {
		for (int i = 0; i < listaPasajeros.size(); i++) {
			if (!listaPasajeros.get(i).esAnciano() && listaPasajeros.get(i).esMayorEdad()) {
				BoteSalvavidas bote = obtenerBoteLibre(listaPasajeros.get(i).getZona(), 1);

				if (bote != null) {
					if (mapaBotes.get(bote) == null) {
						mapaBotes.put(bote, new ArrayList<Persona>());
					}
					
					mapaBotes.get(bote).add(listaPasajeros.get(i));
					listaPasajeros.remove(listaPasajeros.get(i));
					
					i--;

					bote.setNumPlaza(bote.getNumPlaza() - 1);
				}
			}
		}
	}

	private void meterAncianos(ArrayList<Pasajero> listaPasajeros) {
		for (int i = 0; i < listaPasajeros.size(); i++) {
			if (listaPasajeros.get(i).esAnciano()) {
				BoteSalvavidas bote = obtenerBoteLibre(listaPasajeros.get(i).getZona(), 1);

				if (bote != null) {
					if (mapaBotes.get(bote) == null) {
						mapaBotes.put(bote, new ArrayList<Persona>());
					}
					
					mapaBotes.get(bote).add(listaPasajeros.get(i));
					listaPasajeros.remove(listaPasajeros.get(i));
					
					i--;

					bote.setNumPlaza(bote.getNumPlaza() - 1);
				}
			}
		}
	}

	public BoteSalvavidas obtenerBoteLibre(TipoZona zona, Integer num) {
		BoteSalvavidas bote = null;
		Integer siguienteZona = 0;

		for (int i = 0; i < listaBotes.size() && bote == null; i++) {
			if (listaBotes.get(i).getZona() == zona && listaBotes.get(i).getNumPlaza() >= num) {
				bote = listaBotes.get(i);
			}
		}

		if (bote == null) {
			final Integer ZONAPERSONA;

			for (int i = 0; i < TipoZona.values().length; i++) {
				if (TipoZona.values()[i] == zona) {
					if (i == TipoZona.values().length - 1) {
						siguienteZona = 0;
					} else {
						siguienteZona = ++i;
					}
				}
			}

			if (siguienteZona != 0) {
				ZONAPERSONA = siguienteZona - 1;
			} else {
				ZONAPERSONA = 0;
			}

			while (siguienteZona != ZONAPERSONA && bote == null) {
				for (int i = 0; i < listaBotes.size() && bote == null; i++) {
					if (listaBotes.get(i).getZona() == TipoZona.values()[siguienteZona]
							&& listaBotes.get(i).getNumPlaza() >= num) {
						bote = listaBotes.get(i);
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
		Pasajero ninio;

		for (int i = 0; i < listaPasajeros.size(); i++) {
			if (!listaPasajeros.get(i).esMayorEdad()) {
				listaFamilia.add(listaPasajeros.get(i));
				ninio = listaPasajeros.get(i);
				listaPasajeros.remove(listaPasajeros.get(i));

				for (int j = 0; j < listaPasajeros.size(); j++) {
					if (!listaPasajeros.get(j).esMayorEdad() && ninio.getNumHab() == listaPasajeros.get(j).getNumHab()) {
						listaFamilia.add(listaPasajeros.get(j));
						listaPasajeros.remove(listaPasajeros.get(j));
					} else {
						if (ninio.getNumHab() == listaPasajeros.get(j).getNumHab()) {
							listaPadres.add(listaPasajeros.get(j));
						}
					}
				}
				if (!listaPadres.isEmpty()) {
					Collections.shuffle(listaPadres);
					listaFamilia.add(listaPadres.get(0));
					listaPasajeros.remove(listaPadres.get(0));
					listaPadres.clear();
				}

				BoteSalvavidas bote = obtenerBoteLibre(ninio.getZona(), listaFamilia.size());

				if (bote != null) {
					if (mapaBotes.get(bote) == null) {
						mapaBotes.put(bote, new ArrayList<Persona>());
					}
					
					i--;

					mapaBotes.get(bote).addAll(listaFamilia);
					listaFamilia.clear();
				}
			}
		}
	}
}