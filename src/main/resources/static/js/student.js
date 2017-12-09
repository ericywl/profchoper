$(document).ready(function () {
    smoothScrollTo();

    //
    var calendar = $(".calendar");
    calendar.on("click", "table td", function () {
        $("#myModal").modal();
    });


    // Hover on table cell changes its color and background
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
});

// When course dropdown text is clicked, replace student calendar with the course
function courseTextOnClick() {
    var headerDate = $("#week-cal-header-date").text();
    var appendedDate = headerDate.substr(0, 11).replace(/ /g, "-");
    console.log(appendedDate);

    var courseString = $(this).text();
    $("#course-choice-text").text(courseString);

    var courseId = courseString.substr(0, 2) + courseString.substr(3, 3);
    console.log(courseId);

    // Replacing student calendar
    const studentCalUrl
        = "/student/calendar?date=" + appendedDate + "&prof=null" + "&course=" + courseId;
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
    console.log(appendedDate);

    var profName = $(this).text();
    $("#instructor-choice-text").text(profName);

    const profApi = "/api/professors?name=" + profName;
    $.getJSON(profApi, function (json) {
        if (json.length !== 0) {
            console.log(json);
            var profAlias = json.alias;

            const studentCalUrl
                = "/student/calendar?date=" + appendedDate + "&prof=" + profAlias + "&course=null";
            $("#week-cal-table").load(studentCalUrl, function () {
                console.log("refresh")
            });

        } else {
            console.log("Error getting prof alias.");
        }
    });

    return profName;
}

// Always run smoothScrollTo
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
