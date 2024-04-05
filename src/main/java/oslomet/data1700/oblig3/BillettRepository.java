package oslomet.data1700.oblig3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BillettRepository {
    @Autowired
    private JdbcTemplate db;

    public void lagreBillett(Billett billett) {
        String sql = "INSERT INTO billett (film, antall, fornavn, etternavn, telefonnr, epost) VALUES(?,?,?,?,?,?);";
        db.update(sql, billett.getFilm(), billett.getAntall(), billett.getFornavn(), billett.getEtternavn(), billett.getTelefonnr(), billett.getEpost());
    }

    public List<Billett> hentBilletter() {
        String sql = "SELECT * FROM billett;";
        List<Billett> alleBilletter = db.query(sql, new BeanPropertyRowMapper(Billett.class));
        return alleBilletter;
    }

    public void slettBilletter() {
        String sql = "DELETE FROM billett;";
        db.update(sql);
    }
}
