$(document).ready(function () {
    smoothScrollTo();

    // Cancel slot modal popup
    $(".notibtn").click(function () {
        var text = $(this).parent().find(".notitext").text();
        var border = $(this).parent().parent().css('border').split(" ");
        var textColor = border[2] + " " + border[3] + " " + border[4];

        var cancelModalText = $("#cancel-modal-text");
        cancelModalText.empty().append(text);
        cancelModalText.css({color: textColor});
        $("#cancel-modal").modal('toggle', $(this));
    });

    var cancelModal = $("#cancel-modal");
    cancelModal.on('show.bs.modal', function (event) {
        var caller = event.relatedTarget;
        var cancelModalFooter = cancelModal.find(".modal-footer");
        cancelModalFooter.on("click", "#cancel", {caller: caller}, cancelModalBtnOnClick);
    });

    // Table cell modal popup
    var calendar = $(".calendar");
    calendar.on("click", "table td", tableCellOnClick);

    // Hover on table cell changes its color and background if there's text in it
    calendar.on("mouseover", "table td", function () {
        var cell = $(this);
        if (cell.text() !== "") {
            cell.css({cursor: 'pointer', background: 'green', color: 'white'});
        }
    });

    calendar.on("mouseout", "table td", function () {
        $(this).css({cursor: 'default', background: 'white', color: '#111111'});
    });

    // Dropdown menu click text
    $("#course-dropdown-menu").on("click", ".course-dropdown-menu-text", courseTextOnClick);
    $("#prof-dropdown-menu").on("click", ".prof-dropdown-menu-text", profTextOnClick);

    // Calendar header left & right buttons
    var weekCalHeaderContainer = $("#week-cal-header-container");
    weekCalHeaderContainer.find(".next").click(btnOnClick);
    weekCalHeaderContainer.find(".prev").click(btnOnClick);
});

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

// When a table cell is clicked, modal shows up if there's text
function tableCellOnClick() {
    var textArr = ($(this).html().split(', '));
    var radioHome = $("#radio-home");
    $(radioHome).empty();
    for (var i = 0; i < textArr.length; i++) {
        const profApi = "/api/professors/" + textArr[i].toLowerCase();
        $.getJSON(profApi, function (json) {
            if (json.name !== 0) {
                var fullName = json.name;
                var profRadioBtn = makeRadioButton("profbutton", fullName, fullName);
                radioHome.append(profRadioBtn);
                radioHome.append("<br/>");
            } else {
                console.log("ERROR: Can't get prof alias for " + fullName)
            }
        });
    }

    // Show modal if it has text
    if ($(this).text() !== "") {
        $("#myModal").modal();
    }
}

// Confirm cancel slot booking in modal
function cancelModalBtnOnClick(event) {
    var caller = event.data.caller;

    if (caller.hasClass('notibtn')) {
        var text = caller.parent().find($(".notitext")).text();
        var profName = text.split(". ")[1];
        var time = text.split(" to ")[0];

        const profApi = "/api/professors?name=" + profName;
        $.getJSON(profApi, function (json) {
            if (json.length !== 0) {
                var bookSlot = {
                    profAlias: json.alias,
                    time: time
                };

                $.ajax({
                    url: "/student?action=cancel",
                    contentType: "application/json",
                    type: "PUT",
                    data: JSON.stringify(bookSlot),
                    success: function () {
                        showSnackbar();
                        console.log("DONE")
                    },
                    error: function (e) {
                        console.log("ERROR: " + e)
                    }
                })

            } else {
                console.log("ERROR: Can't get prof alias.")
            }

        });
    }
}

// When course dropdown text is clicked, replace student calendar with the course
function courseTextOnClick() {
    var headerDate = $("#week-cal-header-date").text();
    var appendedDate = headerDate.substr(0, 11).replace(/ /g, "-");

    var courseString = $(this).text();
    $("#course-choice-text").text(courseString);

    var courseId = courseString.substr(0, 2) + courseString.substr(3, 3);
    console.log("Refreshed course calendar.");

    // Calendar refresh
    const studentCalUrl = "/student/calendar?date=" + appendedDate + "&course=" + courseId + "&prof=null";
    $("#week-cal-table").load(studentCalUrl);

    const profApi = "/api/professors?course=" + courseId;
    var profListHtml = "";
    $.getJSON(profApi, function (json) {
        if (json.length !== 0) {
            for (var i = 0; i < json.length; i++) {
                profListHtml = profListHtml +
                    "<li class='prof-dropdown-menu-text'>" + json[i].name + "</li>";
            }

            // Replacing prof choice text and dropdown menu list
            $("#prof-choice-text").text("Choose Prof");
            $("#prof-dropdown-menu").empty().append(profListHtml);
            // Enable the prof button
            $(".prof").prop("disabled", false);

        } else {
            // Disabled the prof button
            $("#prof-choice-text").text("Choose Prof");
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

        } else {
            console.log("ERROR: Can't get prof alias.");
        }
    });

    return profName;
}

// Next or previous calendar button
function btnOnClick() {
    var weekCalHeaderDate = $("#week-cal-header-date");
    var startDate = weekCalHeaderDate.text().substr(0, 11).replace(/ /g, "-");
    var endDate = weekCalHeaderDate.text().substr(14, 11).replace(/ /g, "-");

    var newStartDate;
    var newEndDate;

    var weekCalHeaderWeek = $("#week-cal-header-week");
    var newWeek;

    // If next, increment date
    // Else, decrement date
    if ($(this).is(".next")) {
        newStartDate = dateFormat(startDate, 7);
        newEndDate = dateFormat(endDate, 7);
        newWeek = checkTermDate(weekCalHeaderWeek.text(), newStartDate, 1);

    } else if ($(this).is(".prev")) {
        newStartDate = dateFormat(startDate, -7);
        newEndDate = dateFormat(endDate, -7);
        newWeek = checkTermDate(weekCalHeaderWeek.text(), newStartDate, -1);

    } else {
        console.log("ERROR: btnOnClick should not reach here.")
    }

    weekCalHeaderDate.empty().append(newStartDate + " - " + newEndDate);
    weekCalHeaderWeek.empty().append(newWeek);

    var appendedDate = newStartDate.replace(/ /g, "-");

    var profName = $("#prof-choice-text").text();

    var courseString = $("#course-choice-text").text();
    var courseId = courseString.substr(0, 2) + courseString.substr(3, 3);

    // Calendar refresh
    if (profName === "Choose Prof") {
        const studentCalUrl = "/student/calendar?date=" + appendedDate
            + "&prof=null" + "&course=" + courseId;

        $("#week-cal-table").load(studentCalUrl, function () {
            console.log("Refreshed course calendar.")
        })

    } else {
        const profApi = "/api/professors?name=" + profName;
        $.getJSON(profApi, function (json) {
            if (json.length !== 0) {
                var profAlias = json.alias;

                const studentCalUrl = "/student/calendar?date=" + appendedDate
                    + "&course=" + courseId + "&prof=" + profAlias;

                $("#week-cal-table").load(studentCalUrl, function () {
                    console.log("Refreshed prof calendar.")
                })

            } else {
                console.log("ERROR: Can't get prof alias.")
            }
        })
    }
}

// Checking for specific term dates
function checkTermDate(week, date, oneOrNegOne) {
    startTermDates = ["10 Sep 2018", "22 Jan 2018", "14 May 2018"];
    endTermDates = ["11 Dec 2017", "13 Aug 2018", "23 Apr 2018"];
    var output;

    if (week === "Vacation" && $.inArray(date, startTermDates) !== -1)
        return "Week 1";

    if (week === "Vacation" && $.inArray(date, endTermDates) !== -1)
        return "Week 14";

    if (week === "Vacation" && $.inArray(date, startTermDates) === -1
        && $.inArray(date.substr(0, 6), startTermDates) === -1) return "Vacation";

    var weekNo = parseInt(week.substr(5, 2)) + oneOrNegOne;
    if (weekNo > 14 || weekNo < 1) output = "Vacation";
    else output = "Week " + weekNo;

    return output;
}

// Format the date so that its dd-MMM-yyyy
function dateFormat(date, days) {
    var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

    var dateObj = new Date(date);
    dateObj.setDate(dateObj.getDate() + days);

    var newDate = dateObj.getDate().toString();
    var newMonth = months[dateObj.getMonth()];
    var newYear = dateObj.getFullYear().toString();

    return (newDate[1] ? newDate : "0" + newDate[0]) + " " + newMonth + " " + newYear;
}

// Smooth scroll to anchor href
function smoothScrollTo() {
    // Select all links with hashes and remove links that don't actually link to anything
    $('a[href*="#"]').not('[href="#"]').not('[href="#0"]')
        .click(function (event) {
            if (location.pathname.replace(/^\//, '') === this.pathname.replace(/^\//, '')
                && location.hostname === this.hostname) {

                // Figure out element to scroll to
                var target = $(this.hash);
                target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');

                if (target.length) {
                    // Only prevent default if animation is actually gonna happen
                    event.preventDefault();
                    $('html, body').animate({
                        scrollTop: target.offset().top
                    }, 1000, function () {
                        // Callback after animation and change focus
                        var $target = $(target);
                        $target.focus();
                    });
                }
            }
        });
}

// Show the snackbar for 3s
function showSnackbar() {
    var snackbar = $("#snackbar");
    console.log("In snackbar");
    snackbar.addClass("show");

    setTimeout(function () {
        snackbar.removeClass("show");
    }, 3000);
}
