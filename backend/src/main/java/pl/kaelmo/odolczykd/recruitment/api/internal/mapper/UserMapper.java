package pl.kaelmo.odolczykd.recruitment.api.internal.mapper;

import org.springframework.stereotype.Component;
import pl.kaelmo.odolczykd.recruitment.api.external.model.ExternalUser;
import pl.kaelmo.odolczykd.recruitment.api.internal.model.RestUser;

@Component
public class UserMapper {
    public RestUser toRestUser(ExternalUser user) {
        return new RestUser(
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getGender(),
                user.getPhone(),
                user.getPhotoUrl()
        );
    }
}
