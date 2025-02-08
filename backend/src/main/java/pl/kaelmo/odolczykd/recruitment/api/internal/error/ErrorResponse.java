package pl.kaelmo.odolczykd.recruitment.api.internal.error;

public record ErrorResponse(
        int status,
        String reason
) {
}
