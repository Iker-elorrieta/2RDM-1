package modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Centros implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String centro;
	private double latitud;
	private double longitud;

	public Centros(int id, String centro, double latitud, double longitud) {
		this.id = id;
		this.centro = centro;
		this.longitud = longitud;
	}

	public Centros(int id, String centro) {
		super();
		this.id = id;
		this.centro = centro;
	}

	public Centros() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCentro() {
		return centro;
	}

	public void setCentro(String centro) {
		this.centro = centro;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public ArrayList<Centros> leerJson() {

		String url = "src/centros.json";

		ArrayList<Centros> centros = new ArrayList<>();

		JsonParser parser = new JsonParser();

		try {

			FileReader fr = new FileReader(url);

			JsonElement datos = parser.parse(fr);

			JsonObject jsonObject = datos.getAsJsonObject();

			JsonArray array = jsonObject.getAsJsonArray("CENTROS");

			Iterator<JsonElement> iter = array.iterator();

			while (iter.hasNext()) {

				JsonElement entrada = iter.next();

				JsonObject objeto = entrada.getAsJsonObject();

				Centros centro = new Centros(objeto.get("CCEN").getAsInt(), objeto.get("NOM").getAsString(),
						objeto.get("LATITUD").getAsDouble(), objeto.get("LONGITUD").getAsDouble());

				centros.add(centro);

			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}

		return centros;

	}
}
