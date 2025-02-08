package pl.kaelmo.odolczykd.recruitment.api.external.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExternalUser {
    private String uuid;
    private String name;
    private String username;
    private String email;
    private String gender;
    private String phone;
    private String photoUrl;
}
