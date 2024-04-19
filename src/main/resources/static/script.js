function kjop() {
    /* Henter inputene fra HTML siden */
    const film = $("#velgfilm").val();
    const antall = $("#antall").val();
    const fornavn = $("#fornavn").val();
    const etternavn = $("#etternavn").val();
    const telefonnr = $("#telefonnr").val();
    const epost = $("#epost").val();

    /* Deklarerer et array og definer verdiene til et objekt */
    let billettNr;
    const ordre = {
        film : film,
        antall : antall,
        fornavn : fornavn,
        etternavn : etternavn,
        telefonnr : telefonnr,
        epost : epost,
    };

    /* Regler for validering */
    const navnRegex = /^[A-Åa-å]+$/;
    const telefonnrRegex = /^[0-9]+$/;
    const epostRegex = /^[a-åA-Å0-9._-]+@[a-åA-Å0-9.-]+\.[a-åA-Å]{2,4}$/;
    /*  "epostRegex" er hentet fra:
        https://emaillistvalidation.com/blog/email-validation-in-javascript-using-regular-expressions-the-ultimate-guide/
        Z er endret til Å for å tilpasse norsk
    */

    /* Valideringen av alle feltene */
    let feilmld = "";
    if (film === "") {
        feilmld += "Velg film<br>";
    }
    if (antall <= 0) {
        feilmld += "Antall<br>";
    }
    if (!navnRegex.test(fornavn)) {
        feilmld += "Fornavn<br>";
    }
    if (!navnRegex.test(etternavn)) {
        feilmld += "Etternavn<br>";
    }
    if (!telefonnrRegex.test(telefonnr)) {
        feilmld += "Telefonnr<br>";
    }
    if (!epostRegex.test(epost)) {
        feilmld += "Epost<br>";
    }

    /* Hvis feilmld er tom string, betyr dette at alle input feltene er godkjente og billetten kjøpes */
    if (feilmld === "") {
        $.post("/tilServer", ordre, function (){
            hent();
        });

        // Reset av input felt etter vellykket kjøp
        $("#velgfilm").val("");
        $("#antall").val("");
        $("#fornavn").val("");
        $("#etternavn").val("");
        $("#telefonnr").val("");
        $("#epost").val("");

        /* Hvis ikke feilmld er en tom string, betyr dette at det er en feil i en eller flere av input feltene */
    } else {
        $("#feilmldfelt").html("<h2>Feil vedrørende din ordre ved</h2><strong>" + feilmld);
    }
};

function slett() {
    $.get("/slettLagring", function (){
        hent();
    })
};

function hent() {
    $.get("/tilKlient", function (data){
        utskrift(data);
    })
}

function utskrift(ordre) {
    let ut = "<table class='table'><tr><th class='active'>Film</th><th class='active'>Antall</th><th class='active'>Fornavn</th><th class='active'>Etternavn</th><th class='active'>Telefonnr</th><th class='active'>Epost</th><th class='active'>Endre</th><th class='active'>Slett</th></tr>";
    for (let i of ordre) {
        ut += "<tr><td>" +i.film+ "</td><td>" +i.antall+ "</td><td>" +i.fornavn+ "</td><td>" + i.etternavn+ "</td><td>" +i.telefonnr+ "</td><td>" +i.epost+ "</td><td><button class='btn btn-primary' onclick='oppdaterBillett(" +i.billettNr+ ")'>Endre</button></td><td><button class='btn btn-danger' onclick='slettEnBillet(" +i.billettNr+ ")'>Slett</button></td></tr>";
    }
    ut += "</table>";
    $("#billettfelt").html(ut);
    $("#feilmldfelt").html("");
}

function oppdaterBillett(billettNr) {
    $.get("hentBilletterFraDB?billettNr=" + billettNr, function (data) {
        $("#billettNr").html(billettNr);
        $("#endreVelgfilm").val(data.film);
        $("#endreAntall").val(data.film);
        $("#endreFornavn").val(data.film);
        $("#endreEtternavn").val(data.film);
        $("#endreTelefonnr").val(data.film);
        $("#endreEpost").val(data.film);
    });
}

function slettEnBillet(billettNr) {
    $.ajax({
        url: "slettEnBillett?billettNr="+billettNr,
        type: "DELETE"
    });
}