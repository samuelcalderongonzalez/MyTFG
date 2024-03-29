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
import com.squareup.okhttp.ResponseBody;

import serielizable.entity.Film;
import serielizable.entity.Serie;

public class APILibrary {
	private static List<Genre> filmGenres = searchAllFilmGenres();
	private static List<Genre> serieGenres = searchAllSerieGenres();

	public List<Film> searchFilmByTitle(String title) {

		List<Film> films = new ArrayList<>();

		try {
			title = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
					.url("https://api.themoviedb.org/3/search/movie?query=" + title
							+ "&include_adult=false&language=es&page=1")
					.get().addHeader("accept", "application/json").addHeader("Authorization", Constants.API_TOKEN)
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
				JsonArray jsonGenres = element.getAsJsonObject().getAsJsonArray("genre_ids");
				Film currFilm = new Film();
				for (JsonElement el : jsonGenres) {
					for (Genre g : filmGenres) {
						if (g.getId() == el.getAsInt()) {
							currFilm.addGenre(g.getName());
						}
					}
				}

				currFilm.setId(element.getAsJsonObject().get("id").getAsInt());
				currFilm.setTitle(element.getAsJsonObject().get("title").getAsString());
				currFilm.setReleaseDate(
						DateUtils.mapStringToDate(element.getAsJsonObject().get("release_date").getAsString()));
				currFilm.setScore(element.getAsJsonObject().get("vote_average").getAsDouble());
				currFilm.setSynopsis(element.getAsJsonObject().get("overview").getAsString());
				currFilm.setDuration(searchFilmRuntime(currFilm.getId()));
				System.out.println(element.getAsJsonObject().get("vote_average").getAsDouble());
				films.add(currFilm);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(films);
		return films;

	}

	public List<Serie> searchSerieByTitle(String title) {

		List<Serie> series = new ArrayList<>();

		try {
			title = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
					.url("https://api.themoviedb.org/3/search/tv?query=" + title
							+ "&include_adult=false&language=es&page=1")
					.get().addHeader("accept", "application/json").addHeader("Authorization", Constants.API_TOKEN)
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
				JsonArray jsonGenres = element.getAsJsonObject().getAsJsonArray("genre_ids");
				Serie currSerie = new Serie();
				for (JsonElement el : jsonGenres) {
					for (Genre g : serieGenres) {
						if (g.getId() == el.getAsInt()) {
							currSerie.addGenre(g.getName());
						}
					}
				}
				currSerie.setId(element.getAsJsonObject().get("id").getAsInt());
				currSerie.setTitle(element.getAsJsonObject().get("name").getAsString());
				currSerie.setReleaseDate(
						DateUtils.mapStringToDate(element.getAsJsonObject().get("first_air_date").getAsString()));
				currSerie.setScore(element.getAsJsonObject().get("vote_average").getAsDouble());
				currSerie.setSynopsis(element.getAsJsonObject().get("overview").getAsString());
				series.add(currSerie);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return series;

	}

	private static List<Genre> searchAllFilmGenres() {
		List<Genre> genre = new ArrayList<Genre>();
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url("https://api.themoviedb.org/3/genre/movie/list?language=es").get()
				.addHeader("accept", "application/json").addHeader("Authorization", Constants.API_TOKEN).build();

		try {
			ResponseBody response = client.newCall(request).execute().body();
			Gson gson = new Gson();
			String jsonResponse = response.string();
			JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
			JsonArray results = jsonObject.getAsJsonArray("genres");
			for (JsonElement element : results) {
				genre.add(new Genre(element.getAsJsonObject().get("id").getAsInt(),
						element.getAsJsonObject().get("name").getAsString()));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return genre;
	}

	private static List<Genre> searchAllSerieGenres() {
		List<Genre> genre = new ArrayList<Genre>();
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url("https://api.themoviedb.org/3/genre/tv/list?language=es").get()
				.addHeader("accept", "application/json").addHeader("Authorization", Constants.API_TOKEN).build();

		try {
			ResponseBody response = client.newCall(request).execute().body();
			Gson gson = new Gson();
			String jsonResponse = response.string();
			JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
			JsonArray results = jsonObject.getAsJsonArray("genres");
			for (JsonElement element : results) {
				genre.add(new Genre(element.getAsJsonObject().get("id").getAsInt(),
						element.getAsJsonObject().get("name").getAsString()));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return genre;
	}

	public Integer searchFilmRuntime(int filmId) {
		Integer runtime = 0;
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url("https://api.themoviedb.org/3/movie/" + filmId + "?api_key=" + Constants.API_KEY).get()
				.addHeader("accept", "application/json").addHeader("Authorization", Constants.API_TOKEN).build();

		try {
			ResponseBody response = client.newCall(request).execute().body();
			Gson gson = new Gson();
			String jsonResponse = response.string();
			JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
			runtime = jsonObject.get("runtime").getAsInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return runtime;
	}

	public Integer searchSerieRuntime(int serieId) {
		Integer runtime = 0;
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url("https://api.themoviedb.org/3/tv/" + serieId + "?api_key=" + Constants.API_KEY).get()
				.addHeader("accept", "application/json").addHeader("Authorization", Constants.API_TOKEN).build();

		try {
			ResponseBody response = client.newCall(request).execute().body();
			Gson gson = new Gson();
			String jsonResponse = response.string();
			JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
			runtime = jsonObject.get("runtime").getAsInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return runtime;
	}

}
