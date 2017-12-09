$(document).ready(function () {
    // Select all links with hashes
    $('a[href*="#"]')
    // Remove links that don't actually link to anything
        .not('[href="#"]')
        .not('[href="#0"]')
        .click(function (event) {
            // On-page links
            if (
                location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '')
                &&
                location.hostname == this.hostname
            ) {
                // Figure out element to scroll to
                var target = $(this.hash);
                target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
                // Does a scroll target exist?
                if (target.length) {
                    // Only prevent default if animation is actually gonna happen
                    event.preventDefault();
                    $('html, body').animate({
                        scrollTop: target.offset().top
                    }, 1000, function () {
                        // Callback after animation
                        // Must change focus!
                        var $target = $(target);
                        $target.focus();
                    });
                }
            }
        });

    $("table td").click(function () {
        $("#myModal").modal();
    });

    $("#course-dropdown-menu").on("click", ".course-dropdown-menu-text", courseTextOnClick);
    $("#instructor-dropdown-menu").on("click", ".instructor-dropdown-menu-text", profTextOnClick);
});

function courseTextOnClick() {
    var courseString = $(this).text();
    $("#course-choice-text").text(courseString);

    var courseId = courseString.substr(0, 2) + courseString.substr(3, 3);
    console.log(courseId);

    // Replacing student calendar
    const studentCalUrl = "/student/calendar?course=" + courseId + "&prof=null";
    $("#week-cal-table").load(studentCalUrl);

    const profApi = "/api/professors?course=" + courseId;
    var profListHtml = "";
    $.getJSON(profApi, function (json) {
        if (json.length !== 0) {
            console.log(json);

            for (var i = 0; i < json.length; i++) {
                profListHtml = profListHtml + "<li class='instructor-dropdown-menu-text'>" + json[i].name + "</li>";
            }

            // Replacing instructor choice text and dropdown menu list
            $("#instructor-choice-text").text("Choose Instructor");
            $("#instructor-dropdown-menu").empty().append(profListHtml);

        } else {
            console.log("Error getting prof list.");
        }
    });

    return courseId;
}

function profTextOnClick() {
    var profName = $(this).text();
    $("#instructor-choice-text").text(profName);

    const profApi = "/api/professors?name=" + profName;
    var profAlias;
    $.getJSON(profApi, function (json) {
       if (json.length !== 0) {
           console.log(json);
           profAlias = json.alias;

       } else {
           console.log("Error getting prof alias.");
           profAlias = "oka";
       }
    });

    const studentCalUrl = "/student/calendar?prof=" + profAlias + "&course=null";
    $("#week-cal-table").load(studentCalUrl, function () {
        console.log("refresh")
    });
}