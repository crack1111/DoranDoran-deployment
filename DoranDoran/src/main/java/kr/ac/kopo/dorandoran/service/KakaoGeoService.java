package kr.ac.kopo.dorandoran.service;

import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@Service
@Transactional
public class KakaoGeoService {

	private final RestTemplate restTemplate;

	@Value("${kakao.api.key}")
	private String kakaoApiKey;

	public KakaoGeoService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public double[] getCoordinates(String address) {

	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", "KakaoAK " + kakaoApiKey);
	    headers.set("Accept", "application/json");

	    HttpEntity<String> entity = new HttpEntity<>(headers);

	    URI uri = UriComponentsBuilder.newInstance()
	    	    .scheme("https")
	    	    .host("dapi.kakao.com")
	    	    .path("/v2/local/search/address.json")
	    	    .queryParam("query", address)
	    	    .build()
	    	    .encode(StandardCharsets.UTF_8)
	    	    .toUri();

	    ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
	    System.out.println("Response: " + response.getBody());

	    try {
	        String responseBody = response.getBody();
	        JSONObject json = new JSONObject(responseBody);
	        JSONArray documents = json.getJSONArray("documents");

	        if (documents.length() > 0) {
	            JSONObject first = documents.getJSONObject(0);

	            JSONObject coordinateObject = first.has("road_address")
	                ? first.getJSONObject("road_address")
	                : first.getJSONObject("address");

	            double lon = Double.parseDouble(coordinateObject.getString("x"));
	            double lat = Double.parseDouble(coordinateObject.getString("y"));
	            return new double[]{lat, lon};
	        } else {
	            throw new RuntimeException("No documents found for the address.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("주소를 좌표로 변환할 수 없습니다.");
	    }
	}


}
