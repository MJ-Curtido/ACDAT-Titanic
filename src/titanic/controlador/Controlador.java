package titanic.controlador;

import titanic.gestion.GestionTitanic;
import titanic.vista.Vista;

public class Controlador {
	public void menu() throws InterruptedException {
		GestionTitanic gestion = new GestionTitanic();
		Vista vista = new Vista();
		
		vista.mostrarMapa(gestion.obtenerMapa());
	}
}