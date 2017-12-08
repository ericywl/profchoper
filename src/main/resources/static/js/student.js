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

    $("#course-dropdown-menu").find("li").click(courseTextOnClick);

});

function courseTextOnClick() {
    var course = $(this).text();
    $("#course-choice-text").text(course);

    var courseId = course.substr(0, 2) + course.substr(3, 3);
    console.log(courseId);
    const profUrl = "https://sutd-profchoper.herokuapp.com/api/professors?course=" + courseId;

    var profsHTML;
    var data = $.getJSON(profUrl, function (json) {
        if (json.length !== 0) {
            var arr = $.parseJSON(json);
            for (var i = 0; i < arr.length; i++) {
                console.log(arr[i].name);
                profsHTML = profsHTML + "<li>" + arr[i].name + "</li>";
            }

            $("#instructor-dropdown-menu").empty().append(profsHTML);

        } else {
            console.log("error");
        }
    });


}