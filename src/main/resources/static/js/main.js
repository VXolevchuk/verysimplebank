$(document).ready(function(){
    $.getJSON('/account', function(data) {
        $('#login').text(data.email);
        $("#avatar").attr("src", data.pictureUrl);
    });

    assignButtons();
    loadData(0);
    loadData1();
    loadData2();
});

function assignButtons() {
    $("#submitButton").click(function (e) {
        if ($("#sender").val() == "")
            $("#senderSpan").text("Enter sender");
        else
            $("#senderSpan").text("");

        if ($("#receiver").val() == "")
            $("#receiverSpan").text("Enter receiver");
        else
            $("#receiverSpan").text("");

        if ($("#value").val() == "")
            $("#valueSpan").text("Enter value");
        else
            $("#valueSpan").text("");

        if (($("#sender").val() != "") && ($("#receiver").val() != "") && ($("#value").val() != "")) {
            var transaction = {
                sender: $("#sender").val(),
                receiver: $("#receiver").val(),
                value: $("#value").val()
            }

            $.ajax({
                type: "POST",
                url: "/transaction",
                contentType: "application/json",
                data: JSON.stringify(transaction),
                dataType: "json",
                success: function (result, status, xhr) {
                    if (result.description == "OK") {
                        $("#messageSpan").text("Added successfully");
                        setTimeout(function () {
                            window.location = "/";
                        }, 2000);
                    } else {
                        $("#messageSpan").text("Error occured!");
                    }
                },
                error: function (xhr, status, error) {
                    var jsonError = jQuery.parseJSON( xhr.responseText );
                    var desc = (jsonError != "") ? jsonError.description : "no details";

                    $("#messageSpan").text("Result: " + status + " " + error + " " +
                        xhr.status + " " + xhr.statusText + ": " + desc);
                }
            });
        }
    });

    $('#deleteButton').click(function() {
        var idList = { 'toDelete' : []};

        $(":checked").each(function() {
            idList['toDelete'].push($(this).val());
        });

        $.post("/delete", idList, function(data, status) {
            window.location = "/";
        });
    });
}


function loadData() {
    $("#data > tbody").empty();

    $.getJSON('/accounts', function(data) {
        var i;

        for (i = 0; i < data.length; i++) {
            $('#data > tbody:last-child').append(
                $('<tr>')
                    .append(
                        $('<td>').append(
                            $('<input>').attr('type', 'checkbox').attr('value', data[i].id)
                        )
                    )
                    .append($('<td>').append(data[i].accountNumber))
                    .append($('<td>').append(data[i].money))
                    .append($('<td>').append(data[i].currency))
            );
        }
    });
}

function loadData1() {
    $("#data1 > tbody").empty();

    $.getJSON('/exchange', function (data) {
        var i;

        for (i = 0; i < data.length; i++) {
            $('#data1 > tbody:last-child').append(
                $('<tr>')
                    .append(
                        $('<td>').append(
                            $('<input>').attr('type', 'checkbox').attr('value', data[i].id)
                        )
                    )
                    .append($('<td>').append(data[i].ccy))
                    .append($('<td>').append(data[i].base_ccy))
                    .append($('<td>').append(data[i].buy))
                    .append($('<td>').append(data[i].sale))
            );
        }
    });
}

function loadData2() {
    $("#data2 > tbody").empty();

    $.getJSON('/transactions', function(data) {
        var i;

        for (i = 0; i < data.length; i++) {
            $('#data2 > tbody:last-child').append(
                $('<tr>')
                    .append(
                        $('<td>').append(
                            $('<input>').attr('type', 'checkbox').attr('value', data[i].id)
                        )
                    )
                    .append($('<td>').append(data[i].date))
                    .append($('<td>').append(data[i].senderLogin))
                    .append($('<td>').append(data[i].receiverCardNumber))
                    .append($('<td>').append(data[i].value))
                    .append($('<td>').append(data[i].currency))
            );
        }
    });
}
