/* Regler for validering */
const navnRegex = /^[A-Za-z]+$/;
const telefonnrRegex = /^[0-9]+$/;
const epostRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
/*  "epostRegex" er hentet fra:
    https://emaillistvalidation.com/blog/email-validation-in-javascript-using-regular-expressions-the-ultimate-guide/
*/

function kjop() {
    /* Deklarerer et array og definer verdiene til et objekt */
    const ordre = {
        film : $("#velgfilm").val(),
        antall : $("#antall").val(),
        fornavn : $("#fornavn").val(),
        etternavn : $("#etternavn").val(),
        telefonnr : $("#telefonnr").val(),
        epost : $("#epost").val()
    };

    /* Valideringen av alle feltene */
    let feilmld = "";
    if (ordre.film === "") {
        feilmld += "Velg film<br>";
    }
    if (ordre.antall <= 0) {
        feilmld += "Antall<br>";
    }
    if (!navnRegex.test(ordre.fornavn)) {
        feilmld += "Fornavn<br>";
    }
    if (!navnRegex.test(ordre.etternavn)) {
        feilmld += "Etternavn<br>";
    }
    if (!telefonnrRegex.test(ordre.telefonnr)) {
        feilmld += "Telefonnr<br>";
    }
    if (!epostRegex.test(ordre.epost)) {
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
        ut += "<tr><td>" +i.film+ "</td><td>" +i.antall+ "</td><td>" +i.fornavn+ "</td><td>" + i.etternavn+ "</td><td>" +i.telefonnr+ "</td><td>" +i.epost+ "</td><td><button class='btn btn-primary' onclick='oppdaterBillett(" +i.billettNr+ ")'>Endre</button></td><td><button class='btn btn-danger' onclick='slettEnBillett(" +i.billettNr+ ")'>Slett</button></td></tr>";
    }
    ut += "</table>";
    $("#billettfelt").html(ut);
    $("#feilmldfelt").html("");
}

function oppdaterBillett(billettNr) {
    $.get("hentEnBillett?billettNr=" + billettNr, function (data) {
        $("#billettNr").html(billettNr);
        $("#endreVelgfilm").val(data.film);
        $("#endreAntall").val(data.antall);
        $("#endreFornavn").val(data.fornavn);
        $("#endreEtternavn").val(data.etternavn);
        $("#endreTelefonnr").val(data.telefonnr);
        $("#endreEpost").val(data.epost);
    });
    $("#endreInputs").css("display", "block");
    $("#inputs").css("display", "none");
}

function slettEnBillett(billettNr) {
    $.ajax({
        url: "slettEnBillett?billettNr="+billettNr,
        type: "DELETE"
    });
    setTimeout(() => {hent();}, 10);
}

function oppdaterBillettiDB() {
    const billett = {
        billettNr : $("#billettNr").html(),
        film : $("#endreVelgfilm").val(),
        antall : $("#endreAntall").val(),
        fornavn : $("#endreFornavn").val(),
        etternavn : $("#endreEtternavn").val(),
        telefonnr : $("#endreTelefonnr").val(),
        epost : $("#endreEpost").val()
    }

    let feilmld = "";
    if (billett.film === "") {
        feilmld += "Velg film<br>";
    }
    if (billett.antall <= 0) {
        feilmld += "Antall<br>";
    }
    if (!navnRegex.test(billett.fornavn)) {
        feilmld += "Fornavn<br>";
    }
    if (!navnRegex.test(billett.etternavn)) {
        feilmld += "Etternavn<br>";
    }
    if (!telefonnrRegex.test(billett.telefonnr)) {
        feilmld += "Telefonnr<br>";
    }
    if (!epostRegex.test(billett.epost)) {
        feilmld += "Epost<br>";
    }

    if (feilmld === "") {
        $.post("/endreBillett", billett, function (){
            hent();
        });
        $("#endreInputs").css("display", "none");
        $("#inputs").css("display", "block");
    } else {
        $("#feilmldfelt").html("<h2>Feil vedrørende oppdatering av din ordre ved</h2><strong>" + feilmld);
    }
}