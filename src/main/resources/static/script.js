function loggInn() {
    const kunde = {
        brukernavn: $("#brukernavn").val(),
        passord: $("#passord").val()
    };
    $.get("/loggInn", kunde, function (OK) {
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
    $.ajax({
        url: "/signUp",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(nyKunde)
    });
}