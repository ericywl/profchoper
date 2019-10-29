$(document).ready(function () {
    // Pending notification cancel and reject button on click listener
    var notif = $("#notifications");
    notif.on("click", ".notibtnbook", pendingNotiBtnOnClick);
    notif.on("click", ".notibtn", pendingNotiBtnOnClick);

    // Hover on table cell changes its color and background if there's text in it
    var calendar = $("#calendar");
    calendar.on("mouseover", "table td", function () {
        var cell = $(this);
        var text = cell.text().toUpperCase();
        if (text.indexOf("AVAILABLE") >= 0 || text.indexOf("BOOKED") >= 0) {
            // Red bg to signify cancelling
            cell.css({cursor: 'pointer', background: '#ed3b3b', color: 'white'});
        } else if (text.indexOf("PENDING") >= 0) {
            // Blue bg for response ie. pending
            cell.css({cursor: 'pointer', background: '#4286f4', color: 'white'});
        } else {
            // Light grey bg for blank
            cell.css({cursor: 'pointer', background: '#bbbbbb', color: 'white'});
        }
    });

    // Restore td CSS on mouseout
    calendar.on("mouseout", "table td", function () {
        var cell = $(this);
        var text = cell.text().toUpperCase();
        if (text.indexOf("BOOKED") >= 0) {
            // Green bg for confirmed slots
            cell.css({cursor: 'default', background: '#1eaf47', color: 'white'});
        } else {
            cell.css({cursor: 'default', background: 'white', color: '#111111'});
        }
    });

    // On click listener for all table cells
    calendar.on("click", "table td", tableCellOnClick);

    // On click listener for each modal button
    $("#booked-btn").off("click").on("click", bookedModalBtnOnClick);
    $("#available-btn").off("click").on("click", availModalBtnOnClick);
    $("#confirm-btn").off("click").on("click", pendingModalBtnOnClick);
    $("#reject-btn").off("click").on("click", pendingModalBtnOnClick);
    $("#blank-btn").off("click").on("click", blankModalBtnOnClick);

    // On click listener for rejecting in pending notification
    $("#noti-reject-btn").off("click").on("click", function () {
        var dateTime = $("#noti-reject-modal-text").text().split(" to ")[0];

        var bookSlot = {
            profAlias: "dummy",
            time: dateTime
        };

        return ajaxReject(bookSlot);
    })
});


// Confirm and reject buttons in the pending notification
function pendingNotiBtnOnClick() {
    var notiText = $(this).parent().find(".notitext").text();
    var dateTime = notiText.split(" to ")[0];

    var bookSlot = {
        profAlias: "dummy",
        time: dateTime
    };

    if ($(this).hasClass("notibtnbook")) {
        return ajaxConfirm(bookSlot);

    } else if ($(this).hasClass("notibtn")) {
        $("#noti-reject-modal-text").empty().append(notiText);
        $("#noti-reject-modal").modal('toggle');
        return null;

    } else {
        console.log("ERROR: Clicking pending buttons should not reach here.");
        return null;
    }
}


// Table cell on click has 4 cases: AVAILABLE, PENDING, BOOKED or BLANK
function tableCellOnClick() {
    var cellId = $(this).attr('id');
    var row = parseInt(cellId.split("x")[0]);
    var col = parseInt(cellId.split("x")[1]);

    console.log(row + ", " + col);

    var weekCalHeaderDate = $("#week-cal-header-date");
    var startDate = weekCalHeaderDate.text().substr(0, 11).replace(/ /g, "-");

    var date = modalDateFormat(startDate, col);
    var timeRange = $("#time" + row).text().replace("-", "to");
    var dateTimeRange = date + " - " + timeRange;

    var text = $(this).text();
    if (text.indexOf("BOOKED") >= 0) {
        $.ajax({
            url: "/prof/bookings?datetime=" + dateTimeRange.split(" to ")[0],
            contentType: "application/json",
            type: "GET",
            success: function (result) {
                $("#booked-modal-text").empty().append(result.studentString);
                $("#booked-datetime").empty().append(dateTimeRange);
                $("#booked-modal").modal('toggle');

            },
            error: function (e) {
                console.log("ERROR: " + e.toString());
            }
        })

    } else if (text.indexOf("PENDING") >= 0) {
        // Pending modal popup
        $.ajax({
            url: "/prof/bookings?datetime=" + dateTimeRange.split(" to ")[0],
            contentType: "application/json",
            type: "GET",
            success: function (result) {
                $("#pending-modal-text").empty().append(result.studentString);
                $("#pending-datetime").empty().append(dateTimeRange);
                $("#pending-modal").modal('toggle');

            },
            error: function (e) {
                console.log("ERROR: " + e.toString());
            }
        })

    } else if (text.indexOf("AVAILABLE") >= 0) {
        // Available modal popup
        $("#avail-datetime").empty().append(dateTimeRange);
        $("#available-modal").modal('toggle');

    } else {
        // Blank modal popup
        $("#blank-datetime").empty().append(dateTimeRange);
        $("#blank-modal").modal('toggle');
    }
}


// 2 buttons in pending modal, confirm & reject booking
// Rejecting would prompt the Prof to delete the slot
function pendingModalBtnOnClick() {
    var dateTime = $("#pending-datetime").text();
    console.log(dateTime);

    var bookSlot = {
        profAlias: "dummy",
        time: dateTime.split(" to ")[0]
    };

    console.log(bookSlot);

    if ($(this).attr('id') === "confirm-btn") {
        return ajaxConfirm(bookSlot);

    } else if ($(this).attr('id') === "reject-btn") {
        return ajaxReject(bookSlot);

    } else {
        console.log("ERROR: Clicking pending buttons should not reach here.");
        return null;
    }
}


// Cancelling a booked slot
// Assumes that the Prof is busy on that time, hence deletes the slot as well
function bookedModalBtnOnClick() {
    var dateTime = $("#booked-datetime").text();
    console.log(dateTime);

    var bookSlot = {
        profAlias: "dummy",
        time: dateTime.split(" to ")[0]
    };

    return ajaxReject(bookSlot);
}

// Deletes slot from the clicked cell
function availModalBtnOnClick() {
    var dateTime = $("#avail-datetime").text();
    console.log(dateTime);

    var bookSlot = {
        profAlias: "dummy",
        time: dateTime.split(" to ")[0]
    };

    return ajaxDelete(bookSlot);
}

// Adds slot to the clicked cell
function blankModalBtnOnClick() {
    var dateTime = $("#blank-datetime").text();
    console.log(dateTime);

    var bookSlot = {
        profAlias: "dummy",
        time: dateTime.split(" to ")[0]
    };

    return ajaxAdd(bookSlot);
}


// Send ajax request to add slot
function ajaxAdd(bookSlot) {
    var headerDate = $("#week-cal-header-date").text();
    var appendedDate = headerDate.substr(0, 11).replace(/ /g, "-");

    $.ajax({
        url: "/prof",
        contentType: "application/json",
        type: "POST",
        data: JSON.stringify(bookSlot),
        success: function (result) {
            if (result.status === "ADD_DONE") {
                showSnackbar("Slot added.<br/>Students can book the slot now.")

            } else if (result.status === "ADD_FAIL") {
                showSnackbar("ERROR: Add request cannot be processed.<br/>" +
                    "Please try again.")
            }

            const profCalUrl = "/prof/calendar?date=" + appendedDate;
            $("#week-cal-table").load(profCalUrl);

            const profNotiUrl = "/prof/noti";
            $("#notifications").load(profNotiUrl);

            console.log(result.status);
            return result.data;

        },
        error: function (e) {
            console.log("ERROR: " + e.toString());
        }
    });

    return null;
}


// Send ajax request to delete slot
function ajaxDelete(bookSlot) {
    var headerDate = $("#week-cal-header-date").text();
    var appendedDate = headerDate.substr(0, 11).replace(/ /g, "-");

    $.ajax({
        url: "/prof",
        contentType: "application/json",
        type: "DELETE",
        data: JSON.stringify(bookSlot),
        success: function (result) {
            if (result.status === "DELETE_DONE") {
                showSnackbar("Slot deleted.<br/>Students will not be able to book that slot now.")

            } else if (result.status === "ADD_FAIL") {
                showSnackbar("ERROR: Delete request can't be processed.<br/>" +
                    "Please try again.")
            }

            const profCalUrl = "/prof/calendar?date=" + appendedDate;
            $("#week-cal-table").load(profCalUrl, function () {
                console.log("Refreshed calendar.")
            });

            const profNotiUrl = "/prof/noti";
            $("#notifications").load(profNotiUrl, function () {
                console.log("Refreshed notifications.")
            });

            console.log(result.status);
            return result.data;

        },
        error: function (e) {
            console.log("ERROR: " + e.toString());
        }
    });

    return null;
}


// Send ajax request to confirm slot
function ajaxConfirm(bookSlot) {
    var headerDate = $("#week-cal-header-date").text();
    var appendedDate = headerDate.substr(0, 11).replace(/ /g, "-");

    $.ajax({
        url: "/prof?action=confirm",
        contentType: "application/json",
        type: "PUT",
        data: JSON.stringify(bookSlot),
        success: function (result) {
            if (result.status === "CONFIRM_DONE") {
                showSnackbar("Slot booking confirmed.<br/>Please be there on time.")

            } else if (result.status === "CONFIRM_FAIL") {
                showSnackbar("ERROR: Confirm request can't be processed.<br/>" +
                    "Please try again.")
            }

            const profCalUrl = "/prof/calendar?date=" + appendedDate;
            $("#week-cal-table").load(profCalUrl, function () {
                console.log("Refreshed calendar.")
            });

            const profNotiUrl = "/prof/noti";
            $("#notifications").load(profNotiUrl, function () {
                console.log("Refreshed notifications.")
            });

            console.log(result.status);
            return result.data;

        },
        error: function (e) {
            console.log("ERROR: " + e.toString());
        }
    });

    return null;
}


// Send ajax request to reject slot
function ajaxReject(bookSlot) {
    var headerDate = $("#week-cal-header-date").text();
    var appendedDate = headerDate.substr(0, 11).replace(/ /g, "-");

    $.ajax({
        url: "/prof?action=reject",
        contentType: "application/json",
        type: "PUT",
        data: JSON.stringify(bookSlot),
        success: function (result) {
            if (result.status === "REJECT_DONE") {
                showSnackbar("Slot booking rejected and slot deleted.<br/>" +
                    "Students will not be able to book that slot now.")

            } else if (result.status === "CONFIRM_FAIL") {
                showSnackbar("ERROR: Reject request can't be processed.<br/>" +
                    "Please try again.")
            }

            const profCalUrl = "/prof/calendar?date=" + appendedDate;
            $("#week-cal-table").load(profCalUrl, function () {
                console.log("Refreshed calendar.")
            });

            const profNotiUrl = "/prof/noti";
            $("#notifications").load(profNotiUrl, function () {
                console.log("Refreshed notifications.")
            });

            console.log(result.status);
            return result.data;

        },
        error: function (e) {
            console.log("ERROR: " + e.toString());
        }
    });

    return null;
}
