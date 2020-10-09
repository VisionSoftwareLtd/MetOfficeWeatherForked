package training.busboard;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jackson.JacksonFeature;

import training.busboard.model.Location;
import training.busboard.model.Root;

public class Main {
    public static void main(String args[]) throws URISyntaxException, IOException {
        URL apiKeyUrl = new Object(){}.getClass().getClassLoader().getResource("apikey.txt");
        File file = new File(apiKeyUrl.toURI());
        String apiKey = new String(Files.readAllBytes(file.toPath()));
        
        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
        WebTarget target = client.target("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json")
            .path("sitelist")
            .queryParam("key", apiKey);
        Root root = target.request(MediaType.APPLICATION_JSON)
            .get(Root.class);
        for (Location location : root.getLocations().getLocation()) {
            System.out.println(location.toString());
        }
    }
}	
