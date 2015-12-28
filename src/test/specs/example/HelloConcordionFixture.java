package example;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(ConcordionRunner.class)
public class HelloConcordionFixture {

	public Map greetingFor(String firstName) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/admin").path("users");

		Response response = target.request().get();
		List<Map> result = response.readEntity(List.class);

		assertThat(result.size(), is(2));

		Map user = result.get(0);

//		assertThat((String) user.get("uri"), is(basePath+"/"+firstUser.getId()));
//		assertThat((String) user.get("name"), is(firstUser.getName()));
		return user;
	}


	public List<Map> getUsers() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/admin").path("users");

		Response response = target.request().get();
		List<Map> result = response.readEntity(List.class);


		return result;
	}
}
