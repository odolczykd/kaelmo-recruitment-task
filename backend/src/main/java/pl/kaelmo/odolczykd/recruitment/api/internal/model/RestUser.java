package pl.kaelmo.odolczykd.recruitment.api.internal.model;

public record RestUser(
        String name,
        String username,
        String email,
        String gender,
        String phone,
        String photoUrl
) {
}
