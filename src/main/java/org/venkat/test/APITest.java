package org.venkat.test;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.venkat.api.core.RestClient;
import org.venkat.api.core.Utility;

public class APITest {
	private Utility util = Utility.newInstance();
	private RestClient client = util.getClient();

	@Test(groups= {"all", "apiTest", "listOfBreeds"})
	public void verifyListOfBreeds() {
		try {
			HttpResponse response = client.get("listOfBreeds");
			String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			JSONObject responseJson = new JSONObject(responseStr);

			// A). Verify status code
			Assert.assertEquals(response.getStatusLine().getStatusCode(), 200, "Status Code not maching with 200");
			// B). Verify JSON response status text
			Assert.assertEquals(responseJson.get("status"), "success", "Status Text not maching 'success'");
			// C). display JSON String
			System.out.println("response JSON String: \n" + responseJson);
		} catch (ParseException | JSONException | IOException e) {
			Assert.assertEquals(true, false, e.toString());
		}
	}

	@Test(groups= {"all", "apiTest", "retrieverIsInList"})
	public void verifyRetrieverIsInList() {
		try {
			HttpResponse response = client.get("listOfBreeds");
			String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			JSONObject responseJson = new JSONObject(responseStr);

			// A). Verify status code
			Assert.assertEquals(responseJson.getJSONObject("message").has("retriever"), true,
					"'retriever' not in message list");
		} catch (ParseException | JSONException | IOException e) {
			Assert.assertEquals(true, false, e.toString());
		}
	}

	@Test(groups= {"all", "apiTest", "retrieverSubBreed"})
	public void verifyRetrieverSubBreed() {
		try {
			HttpResponse response = client.get("subList", "retriever");
			String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			JSONObject responseJson = new JSONObject(responseStr);

			// A). Verify status code
			Assert.assertEquals(response.getStatusLine().getStatusCode(), 200, "Status Code not maching with 200");
			// B). Verify JSON response status text
			Assert.assertEquals(responseJson.get("status"), "success", "Status Text not maching 'success'");
			// C). Display Retriever SubBreed list
			System.out.println("Retriever SubBreed list:: \n" + responseJson);
		} catch (ParseException | JSONException | IOException e) {
			Assert.assertEquals(true, false, e.toString());
		}
	}
	
	@Test(groups= {"all", "apiTest", "subBreedRandomImage"})
	public void verifySubBreedRandomImage() {
		try {
			HttpResponse response = client.get("subBreedRandomImage", "retriever", "golden");
			String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			JSONObject responseJson = new JSONObject(responseStr);

			// A). Verify status code
			Assert.assertEquals(response.getStatusLine().getStatusCode(), 200, "Status Code not maching with 200");
			// B). Verify JSON response status text
			Assert.assertEquals(responseJson.get("status"), "success", "Status Text not maching 'success'");
			// C). Display Retriever SubBreed list
			System.out.println("Retriever SubBreed list:: \n" + responseJson);
		} catch (ParseException | JSONException | IOException e) {
			Assert.assertEquals(true, false, e.toString());
		}
	}
}
