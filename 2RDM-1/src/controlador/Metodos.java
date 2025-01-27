package controlador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class Metodos {
	final String url = "src/centros.json";

	public void conectarJSON() {
		JsonParser parser = new JsonParser();
		try {
			FileReader fr = new FileReader(url);
			JsonElement datos = parser.parse(fr);

			parser(datos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void parser(JsonElement datos) {
		if (datos.isJsonArray()) {

			JsonArray array = datos.getAsJsonArray();
			Iterator<JsonElement> iter = array.iterator();

			while (iter.hasNext()) {
				JsonElement entrada = iter.next();
				parser(entrada);
			}

		} else if (datos.isJsonObject()) {

			//TODO DESCOMENTAR PARA IMPRIMIRLO System.out.println("Objeto");
			JsonObject objeto = datos.getAsJsonObject();
			Iterator<Map.Entry<String, JsonElement>> iter2 = objeto.entrySet().iterator();
			while (iter2.hasNext()) {
				Map.Entry<String, JsonElement> ent = iter2.next();
				JsonElement valor = ent.getValue();
				//TODO DESCOMENTAR PARA IMPRIMIRLO System.out.println("Atributo: " + ent.getKey());
				parser(valor);
			}

		} else if (datos.isJsonPrimitive()) {

			JsonPrimitive primi = datos.getAsJsonPrimitive();

			if (primi.isString()) {
				//TODO DESCOMENTAR PARA IMPRIMIRLO System.out.println("\tTexto: " + primi.getAsString());
			} else if (primi.isNumber()) {
				//TODO DESCOMENTAR PARA IMPRIMIRLO System.out.println("\tNumero: " + primi.getAsNumber());
			} else if (primi.isBoolean()) {
				//TODO DESCOMENTAR PARA IMPRIMIRLO System.out.println("\tBoolean: " + primi.getAsBoolean());
			}

		} else if (datos.isJsonNull()) {
			//TODO DESCOMENTAR PARA IMPRIMIRLO System.out.println("Es nulo");
		}

	}

}