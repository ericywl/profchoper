$(document).ready(function () {
    // Cancel slot modal popup on notibtn
    $("#notifications").off("click").on("click", ".notibtn", function () {
        var inputText = $(this).parent().find(".notitext").text();
        var border = $(this).parent().parent().css('border').split(" ");
        var textColor = border[2] + " " + border[3] + " " + border[4];

        cancelModalPopup(inputText, textColor, $(this));
    });

    // Listener to get caller that invoked cancel modal
    $("#cancelBtn").off("click").on("click", cancelModalBtnOnClick);

    // Book modal or cancel modal popup on table cell
    var calendar = $("#calendar");
    calendar.on("click", "table td", tableCellOnClick);

    // Listener to get caller that invoker book modal
    $("#bookBtn").off("click").on("click", bookModalBtnOnClick);

    // Hover on table cell changes its color and background if there's text in it
    calendar.on("mouseover", "table td", function () {
        var cell = $(this);
        var text = cell.text().toUpperCase();
        if (text.indexOf("PENDING") >= 0 || text.indexOf("BOOKED") >= 0) {
            // Red bg
            cell.css({cursor: 'pointer', background: '#ed3b3b', color: 'white'});
        } else if (text !== "") {
            // Green bg
            cell.css({cursor: 'pointer', background: '#1eaf47', color: 'white'});
        }
    });

    calendar.on("mouseout", "table td", function () {
        $(this).css({cursor: 'default', background: 'white', color: '#111111'});
    });

    // Dropdown menu click text
    $("#course-dropdown-menu").off("click").on("click", ".course-dropdown-menu-text", courseTextOnClick);
    $("#prof-dropdown-menu").off("click").on("click", ".prof-dropdown-menu-text", profTextOnClick);
});


// When a table cell is clicked, modal shows up if there's text
function tableCellOnClick() {
    var cellId = $(this).attr('id');
    var row = parseInt(cellId.split("x")[0]);
    var col = parseInt(cellId.split("x")[1]);
    var weekCalHeaderDate = $("#week-cal-header-date");
    var startDate = weekCalHeaderDate.text().substr(0, 11).replace(/ /g, "-");

    var date = modalDateFormat(startDate, col);
    var timeRange = $("#time" + row).text().replace("-", "to");
    var dateTimeRange = date + " - " + timeRange;

    var text = $(this).text().toUpperCase();
    if (text !== "" && ((text.indexOf("PENDING") >= 0 || text.indexOf("BOOKED") >= 0))) {
        var profAlias = text.split("(")[1].split(")")[0];
        const profApi = "/api/professors/" + profAlias.toLowerCase();
        $.getJSON(profApi, function (json) {
            if (json.name !== 0) {
                var profName = json.name;
                var inputText = dateTimeRange + " with Prof. " + profName + " (" + profAlias + ")";
                var textColor = (text.indexOf("BOOKED") >= 0) ? "#1eaf47" : "#4286f4";
                cancelModalPopup(inputText, textColor);
            } else {
                console.log("ERROR: Can't get prof name.")
            }
        });

    } else if (text !== "") {
        var textArr = ($(this).text().split(', '));
        var radioHome = $("#radio-home");
        $(radioHome).empty();
        if (textArr.length > 1) {
            for (var i = 0; i < textArr.length; i++) {
                const profApi = "/api/professors/" + textArr[i].toLowerCase();
                $.getJSON(profApi, function (json) {
                    if (json.name !== 0) {
                        var profName = json.name;
                        var profAlias = json.alias;
                        var profRadioBtn = makeRadioButton("profBtn", profAlias, profName);
                        radioHome.append(profRadioBtn);
                        radioHome.append("<br/>");
                    } else {
                        console.log("ERROR: Can't get prof alias.")
                    }
                });
            }

        } else {
            const profApi = "/api/professors/" + textArr[0].toLowerCase();
            $.getJSON(profApi, function (json) {
                if (json.name !== 0) {
                    var profName = json.name;
                    var profAlias = json.alias;
                    radioHome.append("<span id='" + profAlias + "'>" + profName + " (" +
                        profAlias.toUpperCase() + ")" + "</span>");
                }
            });
        }

        $("#book-date").empty().append(dateTimeRange);
        $("#book-alert").hide();
        $("#book-modal").modal('toggle');
    }
}


function bookModalBtnOnClick() {
    var profBtn = $("input:radio[name='profBtn']");
    var dateTime = $("#book-date").text().split(" to ")[0];

    var profAlias;
    var bookSlot;

    if (profBtn.is(":checked")) {
        profAlias = $("input:radio[name='profBtn']:checked").val();
        console.log("profbtn");
        console.log(dateTime + ", " + profAlias);

        bookSlot = {
            profAlias: profAlias,
            time: dateTime
        };

        return ajaxCheck(bookSlot, "null");

    } else if (!profBtn.length) {
        profAlias = $("#radio-home").find("span").attr('id');
        console.log(dateTime + ", " + profAlias);

        bookSlot = {
            profAlias: profAlias,
            time: dateTime
        };

        var chooseProfAlias = ($("#prof-choice-text").text() === "Display all Prof.") ? "null" : profAlias;
        return ajaxCheck(bookSlot, chooseProfAlias);
    }

    var bookAlert = $("#book-alert");
    bookAlert.empty().append("Please select a prof. before submitting.");
    bookAlert.show();
    setTimeout(function () {
        bookAlert.fadeOut();
    }, 5000);
}


// Create radio button in modal
function makeRadioButton(name, value, text) {
    var label = document.createElement("label");
    var radio = document.createElement("input");
    radio.type = "radio";
    radio.name = name;
    radio.value = value;

    label.append(radio);
    label.append(" " + text);

    return label;
}


// Cancel modal popup
function cancelModalPopup(inputText, textColor, caller) {
    var cancelModalText = $("#cancel-modal-text");
    cancelModalText.empty().append(inputText);
    cancelModalText.css({color: textColor});
    $("#cancel-modal").modal('toggle');
}


// Confirm cancel slot booking in modal
function cancelModalBtnOnClick() {
    var text = $(this).parent().parent().find($("#cancel-modal-text")).text();
    var profAlias = text.split("(")[1].split(")")[0].toLowerCase();
    var dateTime = text.split(" to ")[0];
    console.log(dateTime + ", " + profAlias);

    var bookSlot = {
        profAlias: profAlias,
        time: dateTime
    };

    var chooseProfAlias = ($("#prof-choice-text").text() === "Display all Prof.") ? "null" : profAlias;
    return ajaxCancel(bookSlot, chooseProfAlias);
}


// Send ajax request to book slot
function ajaxCheck(bookSlot, chooseProfAlias) {
    var headerDate = $("#week-cal-header-date").text();
    var appendedDate = headerDate.substr(0, 11).replace(/ /g, "-");
    var courseString = $("#course-choice-text").text();
    var courseId = courseString.substr(0, 2) + courseString.substr(3, 3);

    $.ajax({
        url: "/student/bookings?date=" + appendedDate + "&course=" + courseId,
        contentType: "application/json",
        type: "GET",
        success: function (result) {
            console.log(result.courseBookingsNo);

            if (result.courseBookingsNo >= 2) {
                var bookAlert = $("#book-alert");
                bookAlert.empty().append("You can only book 2 slots per week per course.");
                bookAlert.show();
                setTimeout(function () {
                    bookAlert.fadeOut();
                }, 5000);

                return null;

            } else {
                return ajaxBook(bookSlot, chooseProfAlias);
            }
        },
        error: function (e) {
            console.log("ERROR: " + e.toString());
        }
    });

    return null;
}


function ajaxBook(bookSlot, chooseProfAlias) {
    var headerDate = $("#week-cal-header-date").text();
    var appendedDate = headerDate.substr(0, 11).replace(/ /g, "-");
    var courseString = $("#course-choice-text").text();
    var courseId = courseString.substr(0, 2) + courseString.substr(3, 3);

    $.ajax({
        url: "/student?action=book",
        contentType: "application/json",
        type: "PUT",
        data: JSON.stringify(bookSlot),
        success: function (result) {
            if (result.status === "BOOK_DONE") {
                $("#book-modal").modal('toggle');
                showSnackbar("Request sent.<br/>Awaiting Prof's response.");

            } else if (result.status === "BOOK_FAIL") {
                showSnackbar("ERROR: Book request not processed.<br/>" +
                    "Please try again.")
            }

            const studentCalUrl = "/student/calendar?date=" + appendedDate
                + "&course=" + courseId + "&prof=" + chooseProfAlias;
            $("#week-cal-table").load(studentCalUrl, function () {
                console.log("Refreshed calendar.")
            });

            const studentNotiUrl = "/student/noti";
            $("#notifications").load(studentNotiUrl, function () {
                console.log("Refreshed notifications.")
            });

            console.log(result.status);
            return result.data;
        },
        error: function (e) {
            console.log("ERROR: " + e.toString())
        }
    });
}


// Send ajax request to cancel slot
function ajaxCancel(bookSlot, chooseProfAlias) {
    var headerDate = $("#week-cal-header-date").text();
    var appendedDate = headerDate.substr(0, 11).replace(/ /g, "-");
    var courseString = $("#course-choice-text").text();
    var courseId = courseString.substr(0, 2) + courseString.substr(3, 3);

    $.ajax({
        url: "/student?action=cancel",
        contentType: "application/json",
        type: "PUT",
        data: JSON.stringify(bookSlot),
        success: function (result) {
            if (result.status === "CANCEL_DONE") {
                showSnackbar("Slot booking cancelled.");

            } else if (result.status === "CANCEL_FAIL") {
                showSnackbar("ERROR: Slot is not cancelled.<br/>" +
                    "Please try again.")
            }

            const studentCalUrl = "/student/calendar?date=" + appendedDate
                + "&course=" + courseId + "&prof=" + chooseProfAlias;
            $("#week-cal-table").load(studentCalUrl);

            const studentNotiUrl = "/student/noti";
            $("#notifications").load(studentNotiUrl, function () {
                console.log("Refreshed notifications.")
            });

            console.log(result.status);
            return result.data;
        },
        error: function (e) {
            console.log("ERROR: " + e)
        }
    })
}


// When course dropdown text is clicked, replace student calendar with the course
function courseTextOnClick() {
    var headerDate = $("#week-cal-header-date").text();
    var appendedDate = headerDate.substr(0, 11).replace(/ /g, "-");

    var courseString = $(this).text();
    var courseId = courseString.substr(0, 2) + courseString.substr(3, 3);
    $("#course-choice-text").text(courseString);

    // Calendar refresh
    const studentCalUrl = "/student/calendar?date=" + appendedDate + "&course=" + courseId + "&prof=null";
    $("#week-cal-table").load(studentCalUrl, function () {
        console.log("Refreshed course calendar.")
    });

    const studentNotiUrl = "/student/noti";
    $("#notifications").load(studentNotiUrl, function () {
        console.log("Refreshed notifications.")
    });

    const profApi = "/api/professors?course=" + courseId;
    var profListHtml = "";
    $.getJSON(profApi, function (json) {
        if (json.length !== 0) {
            for (var i = 0; i < json.length; i++) {
                profListHtml = profListHtml +
                    "<li class='prof-dropdown-menu-text'>" + json[i].name + "</li>";
            }

            // Replacing prof choice text and dropdown menu list
            $("#prof-choice-text").text("Display all Prof.");
            $("#prof-dropdown-menu").empty().append(profListHtml);
            // Enable the prof button
            $(".prof").prop("disabled", false);

        } else {
            // Disabled the prof button
            $("#prof-choice-text").text("Display all Prof.");
            $(".prof").prop("disabled", true);
            console.log("ERROR: Can't get prof list.");
        }
    });

    return courseId;
}


// When prof dropdown text is clicked, replace student calendar with prof
function profTextOnClick() {
    var headerDate = $("#week-cal-header-date").text();
    var appendedDate = headerDate.substr(0, 11).replace(/ /g, "-");

    var profName = $(this).text();
    $("#prof-choice-text").text(profName);

    var courseString = $("#course-choice-text").text();
    var courseId = courseString.substr(0, 2) + courseString.substr(3, 3);

    if (profName === "Display all Prof.") {
        const studentCalUrl = "/student/calendar?date=" + appendedDate
            + "&course=" + courseId + "&prof=null";
        $("#week-cal-table").load(studentCalUrl, function () {
            console.log("Refreshed prof calendar.")
        });

        const studentNotiUrl = "/student/noti";
        $("#notifications").load(studentNotiUrl, function () {
            console.log("Refreshed notification.")
        });

        return;
    }

    // Calendar refresh
    const profApi = "/api/professors?name=" + profName;
    $.getJSON(profApi, function (json) {
        if (json.length !== 0) {
            var profAlias = json.alias;

            const studentCalUrl = "/student/calendar?date=" + appendedDate
                + "&course=" + courseId + "&prof=" + profAlias;
            $("#week-cal-table").load(studentCalUrl, function () {
                console.log("Refreshed prof calendar.")
            });

            const studentNotiUrl = "/student/noti";
            $("#notifications").load(studentNotiUrl, function () {
                console.log("Refreshed notification.")
            });

        } else {
            console.log("ERROR: Can't get prof alias.");
        }
    });

    return profName;
}
