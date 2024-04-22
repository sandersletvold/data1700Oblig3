package oslomet.data1700.oblig3;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Billett {
    private String film;
    private String antall;
    private String fornavn;
    private String etternavn;
    private String telefonnr;
    private String epost;
    private Long billettNr;

    public Billett(String film, String antall, String fornavn, String etternavn, String telefonnr, String epost){
        this.film = film;
        this.antall = antall;
        this.fornavn = fornavn;
        this.etternavn =etternavn;
        this.telefonnr = telefonnr;
        this.epost = epost;
    }
}