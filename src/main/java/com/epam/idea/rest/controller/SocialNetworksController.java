package com.epam.idea.rest.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.epam.idea.core.model.User;
import com.epam.idea.core.service.SocialNetworkService;
import com.epam.idea.core.service.UserSessionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Token;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import static com.epam.idea.rest.config.SocialConfig.clientId;
import static com.epam.idea.rest.config.SocialConfig.clientSecret;

/**
 * Created by Ihar_Niakhlebau on 03-Jun-15.
 */
@Controller
@RequestMapping()
public class SocialNetworksController {

	@Autowired
	private SocialNetworkService socialNetworkService;

	@Autowired
	private UserSessionService userSessionService;

	@RequestMapping(value = "/gglogin", method = RequestMethod.GET)
	public String saveSession() {


		GoogleConnectionFactory connectionFactory =
				new GoogleConnectionFactory(clientId, clientSecret);
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri("http://localhost:9090/Idea/auth/ggloginIn");
		params.setScope("https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email");
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
		return "redirect:" + authorizeUrl;
	}

	@RequestMapping(value = "/auth/ggloginIn", method = RequestMethod.GET)
	public String authorizeCallback(@RequestParam(value = "code", defaultValue = "") String code) throws IOException {

		if(code.equals("")) {
			return "redirect:/#/login";
		}
		String url = "https://accounts.google.com/o/oauth2/token";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		String urlParameters = "code="+code+"&client_id="+ clientId +"&client_secret="+ clientSecret +"&redirect_uri=http://localhost:9090/Idea/auth/ggloginIn&grant_type=authorization_code";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		JSONObject json = new JSONObject(response.toString());
		String accessToken = json.getString("access_token");
		Google google = new GoogleTemplate(accessToken);
		Person person =	google.plusOperations().getPerson("me");

		User user = socialNetworkService.findOrCreateUserBySocialIdGoogle(person);

		userSessionService.save(user);

		return "redirect:/#/home";
	}

}
