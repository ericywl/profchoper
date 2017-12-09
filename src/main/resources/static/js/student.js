$(document).ready(function () {
    smoothScrollTo();

    // INSERT MODAL HERE
    var calendar = $(".calendar");
    calendar.on("click", "table td", function () {
        $("#myModal").modal();
        alert($(this).text())
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
    $("#instructor-dropdown-menu").on("click", ".instructor-dropdown-menu-text", profTextOnClick);

    var weekCalHeaderContainer = $("#week-cal-header-container");
    weekCalHeaderContainer.find(".next").click(btnOnClick);
    weekCalHeaderContainer.find(".prev").click(btnOnClick);
});

// When course dropdown text is clicked, replace student calendar with the course
function courseTextOnClick() {
    var headerDate = $("#week-cal-header-date").text();
    var appendedDate = headerDate.substr(0, 11).replace(/ /g, "-");

    var courseString = $(this).text();
    $("#course-choice-text").text(courseString);

    var courseId = courseString.substr(0, 2) + courseString.substr(3, 3);
    console.log("Refreshed calendar.");

    // Replacing student calendar
    const studentCalUrl = "/student/calendar?date=" + appendedDate + "&course=" + courseId + "&prof=null";
    $("#week-cal-table").load(studentCalUrl);

    const profApi = "/api/professors?course=" + courseId;
    var profListHtml = "";
    $.getJSON(profApi, function (json) {
        if (json.length !== 0) {
            console.log(json);

            for (var i = 0; i < json.length; i++) {
                profListHtml = profListHtml +
                    "<li class='instructor-dropdown-menu-text'>" + json[i].name + "</li>";
            }

            // Replacing instructor choice text and dropdown menu list
            $("#instructor-choice-text").text("Choose Instructor");
            $("#instructor-dropdown-menu").empty().append(profListHtml);
            // Enable the instructor button
            $(".instructor").prop("disabled", false);

        } else {
            // Disabled the instructor button
            $("#instructor-choice-text").text("Choose Instructor");
            $(".instructor").prop("disabled", true);
            console.log("Error getting prof list.");
        }
    });

    return courseId;
}

// When instructor dropdown text is clicked, replace student calendar with prof
function profTextOnClick() {
    var headerDate = $("#week-cal-header-date").text();
    var appendedDate = headerDate.substr(0, 11).replace(/ /g, "-");

    var profName = $(this).text();
    $("#instructor-choice-text").text(profName);

    var courseString = $("#course-choice-text").text();
    var courseId = courseString.substr(0, 2) + courseString.substr(3, 3);

    const profApi = "/api/professors?name=" + profName;
    $.getJSON(profApi, function (json) {
        if (json.length !== 0) {
            console.log(json);
            var profAlias = json.alias;

            const studentCalUrl = "/student/calendar?date=" + appendedDate
                + "&course=" + courseId + "&prof=" + profAlias;

            $("#week-cal-table").load(studentCalUrl, function () {
                console.log("Refreshed calendar.")
            });

        } else {
            console.log("Error getting prof alias.");
        }
    });

    return profName;
}

function btnOnClick() {
    var profName = $("#instructor-choice-text").text();

    var courseString = $("#course-choice-text").text();
    var courseId = courseString.substr(0, 2) + courseString.substr(3, 3);

    var weekCalHeaderDate = $("#week-cal-header-date");
    var startDate = weekCalHeaderDate.text().substr(0, 11).replace(/ /g, "-");
    var endDate = weekCalHeaderDate.text().substr(14, 11).replace(/ /g, "-");

    var now = new Date().if()

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


    if (profName === "Choose Instructor") {
        const studentCalUrl = "/student/calendar?date=" + startDate
            + "&prof=null" + "&course=" + courseId;

        $("#week-cal-table").load(studentCalUrl, function () {
            console.log("Refreshed calendar.")
        })

    } else {
        const profApi = "/api/professors?name=" + profName;
        $.getJSON(profApi, function (json) {
            if (json.length !== 0) {
                var profAlias = json.alias;

                const studentCalUrl = "/student/calendar?date=" + startDate
                    + "&course=" + courseId + "&prof=" + profAlias;

                $("#week-cal-table").load(studentCalUrl, function () {
                    console.log("Refreshed calendar.")
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
