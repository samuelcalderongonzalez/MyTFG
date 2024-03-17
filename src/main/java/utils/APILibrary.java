package utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import serielizable.entity.Film;

public class APILibrary {

	public List<Film> searchFilmByTitle(String title) {

		List<Film> films = new ArrayList<>();

		try {
			title = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
					.url("https://api.themoviedb.org/3/search/movie?query="
							+ title + "&include_adult=false&language=es&page=1")
					.get().addHeader("accept", "application/json")
					.addHeader("Authorization", Constants.API_TOKEN)
					.build();
			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code " + response);
			}

			Gson gson = new Gson();
			String jsonResponse = response.body().string();
			JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
			JsonArray results = jsonObject.getAsJsonArray("results");

			for (JsonElement element : results) {
				System.out.println(element);
				System.out.println(element.getAsJsonObject().get("id"));
				System.out.println(element.getAsJsonObject().get("title"));
				System.out.println(element.getAsJsonObject().get("release_date"));
				System.out.println(element.getAsJsonObject().get("vote_average"));
				Film currFilm = new Film();
				currFilm.setId(element.getAsJsonObject().get("id").getAsInt());
				currFilm.setTitle(element.getAsJsonObject().get("title").getAsString());
				currFilm.setReleaseDate(DateUtils.mapStringToDate(element.getAsJsonObject().get("release_date").getAsString()));
				currFilm.setScore(element.getAsJsonObject().get("vote_average").getAsDouble());
				films.add(currFilm);
				System.out.println("-------------------");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return films;

	}
	
	public List<Film> searchSerieByTitle(String title) {

		List<Film> serie = new ArrayList<>();

		try {
			title = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
					.url("https://api.themoviedb.org/3/search/tv?query="
							+ title + "&include_adult=false&language=es&page=1")
					.get().addHeader("accept", "application/json")
					.addHeader("Authorization", Constants.API_TOKEN)
					.build();
			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code " + response);
			}

			Gson gson = new Gson();
			String jsonResponse = response.body().string();
			JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
			JsonArray results = jsonObject.getAsJsonArray("results");

			for (JsonElement element : results) {
				System.out.println(element);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return serie;

	}

}
