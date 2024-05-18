package oslomet.data1700.oblig3;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kunde {
    private Integer id;
    private String brukernavn;
    private String passord;

    public Kunde(String brukernavn, String passord) {
        this.brukernavn = brukernavn;
        this.passord = passord;
    }
}