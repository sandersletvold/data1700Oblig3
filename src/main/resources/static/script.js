function loggInn() {
    const kunde = {
        brukernavn : $("#brukernavn").val(),
        passord : $("#passord").val()
    };
    const url = "/loggInn";

    $.get(url, kunde, function (OK) {
        if (OK) {
            window.location.href = 'liste.html';
        } else {
            $("#feilInnlogging").html("Feil brukernavn eller passord");
        }
    });
}

function signUp() {
    const nyKunde = {
        nyttBrukernavn : $("#nyttBrukernavn").val(),
        nyttPassord : $("#nyttPassord").val()
    };
    console.log(nyKunde);
    const url = "/signUp";
    $.post(url, nyKunde, function () {
        window.location.href = 'index.html';
        console.log(nyKunde);
    });
}