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
        console.log(caller);

        var cancelModalFooter = cancelModal.find(".modal-footer");
        cancelModalFooter.on("click", "#cancel", {caller: caller}, cancelModalBtnOnClick);
    });




    // INSERT CALENDAR MODAL HERE
    var calendar = $(".calendar");
    calendar.on("click", "table td", function () {
        $("#myModal").modal();
    });


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


    $("#course-dropdown-menu").on("click", ".course-dropdown-menu-text", courseTextOnClick);
    $("#prof-dropdown-menu").on("click", ".prof-dropdown-menu-text", profTextOnClick);

    var weekCalHeaderContainer = $("#week-cal-header-container");
    weekCalHeaderContainer.find(".next").click(btnOnClick);
    weekCalHeaderContainer.find(".prev").click(btnOnClick);
});

function cancelModalBtnOnClick() {
    // var bookSlot;
    // console.log();

    /*
    $.ajax({
        url: "/student/cancel",
        contentType: "application/json",
        method: "PUT",
        data: {},
        success: function () {

        }
    })
    */
}

// When course dropdown text is clicked, replace student calendar with the course
function courseTextOnClick() {
    var headerDate = $("#week-cal-header-date").text();
    var appendedDate = headerDate.substr(0, 11).replace(/ /g, "-");

    var courseString = $(this).text();
    $("#course-choice-text").text(courseString);

    var courseId = courseString.substr(0, 2) + courseString.substr(3, 3);
    console.log("Refreshed course calendar.");

    // Replacing student calendar
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
            console.log("Error getting prof list.");
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
            console.log("Error getting prof alias.");
        }
    });

    return profName;
}

function btnOnClick() {
    var weekCalHeaderDate = $("#week-cal-header-date");
    var startDate = weekCalHeaderDate.text().substr(0, 11).replace(/ /g, "-");
    var endDate = weekCalHeaderDate.text().substr(14, 11).replace(/ /g, "-");

    var newStartDate;
    var newEndDate;

    var weekCalHeaderWeek = $("#week-cal-header-week");
    var newWeek;

    if ($(this).is(".next")) {
        newStartDate = endDateFormat(startDate, 7);
        newEndDate = endDateFormat(endDate, 7);
        newWeek = checkTermDate(weekCalHeaderWeek.text(), newStartDate, 1);

    } else if ($(this).is(".prev")) {
        newStartDate = endDateFormat(startDate, -7);
        newEndDate = endDateFormat(endDate, -7);
        newWeek = checkTermDate(weekCalHeaderWeek.text(), newStartDate, -1);

    } else {
        console.log("Error btnOnClick should not each here.")
    }

    weekCalHeaderDate.empty().append(newStartDate + " - " + newEndDate);
    weekCalHeaderWeek.empty().append(newWeek);

    var appendedDate = newStartDate.replace(/ /g, "-");

    var profName = $("#prof-choice-text").text();

    var courseString = $("#course-choice-text").text();
    var courseId = courseString.substr(0, 2) + courseString.substr(3, 3);

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
                console.log("Error getting prof alias.")
            }
        })
    }
}

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

function endDateFormat(date, days) {
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

            // On-page links
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
