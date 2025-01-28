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

	public Centros(int i, String string) {

	}

	public Centros() {
		// TODO Auto-generated constructor stub
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
				Centros centro = new Centros(objeto.get("CCEN").getAsInt(), objeto.get("NOM").getAsString());
				centros.add(centro);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return centros;

	}
}
