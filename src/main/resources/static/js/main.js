$(document).ready(function () {
    smoothScrollTo();

    // Calendar header left & right buttons
    var weekCalHeaderContainer = $("#week-cal-header-container");
    weekCalHeaderContainer.find(".next").click(nextPrevBtnOnClick);
    weekCalHeaderContainer.find(".prev").click(nextPrevBtnOnClick);
});


// Next or previous calendar button
function nextPrevBtnOnClick() {
    var weekCalHeaderDate = $("#week-cal-header-date");
    var startDate = weekCalHeaderDate.text().substr(0, 11).replace(/ /g, "-");
    var endDate = weekCalHeaderDate.text().substr(14, 11).replace(/ /g, "-");

    var newStartDate;
    var newEndDate;

    var weekCalHeaderWeek = $("#week-cal-header-week");
    var newWeek;

    // If next, increment date and week, else decrement
    // Check for term dates as well
    if ($(this).is(".next")) {
        newStartDate = headerDateFormat(startDate, 7);
        newEndDate = headerDateFormat(endDate, 7);
        newWeek = checkTermDate(weekCalHeaderWeek.text(), newStartDate, 1);

    } else if ($(this).is(".prev")) {
        newStartDate = headerDateFormat(startDate, -7);
        newEndDate = headerDateFormat(endDate, -7);
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
        });

        const studentNotiUrl = "/student/noti?date=" + appendedDate;
        $("#notifications").load(studentNotiUrl, function () {
            console.log("Refreshed notification.")
        });

    } else {
        const profApi = "/api/professors?name=" + profName;
        $.getJSON(profApi, function (json) {
            if (json.length !== 0) {
                var profAlias = json.alias;

                const studentCalUrl = "/student/calendar?date=" + appendedDate
                    + "&course=" + courseId + "&prof=" + profAlias;
                $("#week-cal-table").load(studentCalUrl, function () {
                    console.log("Refreshed prof calendar.")
                });

                const studentNotiUrl = "/student/noti?date=" + appendedDate;
                $("#notifications").load(studentNotiUrl, function () {
                    console.log("Refreshed notification.")
                });

            } else {
                console.log("ERROR: Can't get prof alias.")
            }
        })
    }
}


// Checking for specific term dates
function checkTermDate(week, date, oneOrNegOne) {
    const startTermDates = ["10 Sep 2018", "22 Jan 2018", "14 May 2018"];
    const endTermDates = ["11 Dec 2017", "13 Aug 2018", "23 Apr 2018"];
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
function headerDateFormat(date, days) {
    const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

    var dateObj = new Date(date);
    dateObj.setDate(dateObj.getDate() + days);

    var newDate = dateObj.getDate().toString();
    var newMonth = months[dateObj.getMonth()];
    var newYear = dateObj.getFullYear().toString();

    return (newDate[1] ? newDate : "0" + newDate[0]) + " " + newMonth + " " + newYear;
}


// Format the date so that its dd/MM/yyyy
function modalDateFormat(date, days) {
    var dateObj = new Date(date);
    dateObj.setDate(dateObj.getDate() + days);

    var newDate = dateObj.getDate().toString();
    var newMonth = (dateObj.getMonth() + 1).toString();
    var newYear = dateObj.getFullYear().toString().substr(2, 2);

    return (newDate[1] ? newDate : "0" + newDate[0]) + "/" + newMonth + "/" + newYear;
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
                        scrollTop: target.offset().top - 20
                    }, 1000, function () {
                        // Callback after animation and change focus
                        var $target = $(target);
                        $target.focus();
                    });
                }
            }
        });
}


// Show the snackbar for 8s
function showSnackbar(text) {
    var snackbar = $("#snackbar");
    snackbar.empty().append(text);
    snackbar.addClass("show");

    setTimeout(function () {
        snackbar.removeClass("show");
    }, 8000);
}