package se.ltucoders.bothniabladetdam.service;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class GpsLocationService {
    private final String userAgent = "user-agent";
    private final String headerValue = "\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36\"";

    // Gets city, village or county form GPS coordinates
    public String getLocation(String lat, String lon) {
        // nominatim.org blocks programmatic http requests. The request must be done by a web browser.
        //TODO: make the method shorter
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add(userAgent, headerValue);
        HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    String.format("https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=%s&lon=%s", lat, lon),
                    HttpMethod.GET, httpEntity, String.class);
            return parseResponseEntity(response);
        } catch (Exception ex) {
            System.out.println(ex);
            return "";
        }
    }


    // Extracts city, village or county values form http response
    private String parseResponseEntity(ResponseEntity<String> response) {
        JSONObject responseObject = new JSONObject(response.getBody());
        JSONObject addressObject = responseObject.getJSONObject("address");
        Iterator<String> addressIterator = addressObject.keys();
        List<String> addressList = new ArrayList<>();
        addressIterator.forEachRemaining(addressList :: add);
        for (String element : addressList) {
            if (element.equals("city")) {
                return addressObject.getString("city");
            }

            if (element.equals("village")) {
                return addressObject.getString("village");
            }

            if (element.equals("county")) {
                return addressObject.getString("county");
            }
        }
        return "Not defined";
    }

}
