package pl.kaelmo.odolczykd.recruitment.api.internal.service;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import pl.kaelmo.odolczykd.recruitment.api.external.model.ExternalUser;

import java.util.ArrayList;
import java.util.List;

@Service
public class InternalApiService {
    private static final String API_URL = "https://randomuser.me/api/?results=25&nat=us&seed=kaelmo";
    private static final String RESULTS_FIELD_NAME = "results";
    private static final String LOGIN_FIELD_NAME = "login";
    private static final String UUID_FIELD_NAME = "uuid";
    private static final String USERNAME_FIELD_NAME = "username";
    private static final String NAME_FIELD_NAME = "name";
    private static final String TITLE_FIELD_NAME = "title";
    private static final String FIRST_FIELD_NAME = "first";
    private static final String LAST_FIELD_NAME = "last";
    private static final String GENDER_FIELD_NAME = "gender";
    private static final String EMAIL_FIELD_NAME = "email";
    private static final String PHONE_FIELD_NAME = "phone";
    private static final String PICTURE_FIELD_NAME = "picture";
    private static final String MEDIUM_FIELD_NAME = "medium";

    private final RestTemplate restTemplate = new RestTemplate();

    public List<ExternalUser> getDataFromExternalApi() {
        try {
            var response = restTemplate.getForObject(API_URL, String.class);
            var jsonResponse = new JSONObject(response);
            var results = jsonResponse.getJSONArray(RESULTS_FIELD_NAME);

            List<ExternalUser> users = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                var externalUserJson = results.getJSONObject(i);
                var externalUser = getExternalUserFromJson(externalUserJson);
                users.add(externalUser);
            }
            return users;
        } catch (Exception ignored) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during data fetching");
        }

    }

    private static ExternalUser getExternalUserFromJson(JSONObject jsonUser) {
        return ExternalUser.builder()
                .uuid(getUserUUID(jsonUser))
                .name(getUserFullName(jsonUser))
                .gender(jsonUser.getString(GENDER_FIELD_NAME))
                .email(jsonUser.getString(EMAIL_FIELD_NAME))
                .username(getUsername(jsonUser))
                .phone(jsonUser.getString(PHONE_FIELD_NAME))
                .photoUrl(getUserPhotoUrl(jsonUser))
                .build();
    }

    private static String getUserUUID(JSONObject jsonUser) {
        var loginFieldJson = jsonUser.getJSONObject(LOGIN_FIELD_NAME);
        return loginFieldJson.getString(UUID_FIELD_NAME);
    }

    private static String getUsername(JSONObject jsonUser) {
        var loginFieldJson = jsonUser.getJSONObject(LOGIN_FIELD_NAME);
        return loginFieldJson.getString(USERNAME_FIELD_NAME);
    }

    private static String getUserPhotoUrl(JSONObject jsonUser) {
        var picturesFieldJson = jsonUser.getJSONObject(PICTURE_FIELD_NAME);
        return picturesFieldJson.getString(MEDIUM_FIELD_NAME);
    }

    private static String getUserFullName(JSONObject jsonUser) {
        var nameFieldJson = jsonUser.getJSONObject(NAME_FIELD_NAME);
        return nameFieldJson.getString(TITLE_FIELD_NAME) + " " +
                nameFieldJson.getString(FIRST_FIELD_NAME) + " " +
                nameFieldJson.getString(LAST_FIELD_NAME);
    }
}
