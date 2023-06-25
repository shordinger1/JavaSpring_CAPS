$(function () {
    var currentPage = 1
    var totalPage = 1
    searchCourses(1, true); //default dispalying the first page
    // Search form submit event
    $('#searchForm').submit(function (event) {
        event.preventDefault(); // Prevent form submission
        searchCourses(1, true);
    });

    // Reset button click event
    $('#resetButton').click(function () {
        // Reset the search form and update the table with all courses
        $('#searchForm')[0].reset();
        searchCourses(1, true)
    });

    $(document).on('click', ".page-item.number", function () {
        currentPage = $(this).children().attr('value') // get current number from class = "page-item number"
        console.log($(this).children().attr('value'))
        searchCourses(currentPage, true)
    })

    $(document).on('click', ".page-link.pageStart", function () {
        var page = currentPage - 1 <= 1 ? 1 : currentPage - 1
        searchCourses(page, true)

    })

    $(document).on('click', ".page-link.pageEnd", function () {
        var page = currentPage + 1 >= totalPage ? totalPage : currentPage + 1
        searchCourses(page, true)
    })
})


function searchCourses(pageNumber, refreshPageNumber) {
    var fields = $('#searchForm').serializeArray();
    console.log(fields)
    var params = {};
    $.each(fields, function (index, field) {
        params[field.name] = field.value;
    })
    params['pageNumber'] = pageNumber;
    console.log(params)
    var jsonParam = JSON.stringify(params);
    console.log(jsonParam);
    $.ajax({
        url: 'http://localhost:8080/searchEnrollment', //替换路径
        type: 'POST',
        contentType: 'application/json',
        data: jsonParam,
        success: function (response) {
            updateTable(response.pageData);
            if (refreshPageNumber) {
                totalPage = response.totalPage
                updatePageNumber(response.totalPage);
            }
            activePageNumber(pageNumber)
        },
        error: function (xhr, status, error) {
            console.log(xhr.responseText);
        }
    });
}

// use loop to check all page items,
// if the value of current page item is equal to current page number(the param passed),
// set as active
function activePageNumber(pageNumber) {
    $('.page-item').removeClass('active');
    $.each($('.page-item').children(), function (index, el) {
        if ($(el).attr('value') == pageNumber) {
            $(el).parents().addClass('active');
            return
        }
    })
}

// display total page number, from 1 - totalPages
function updatePageNumber(totalPages) {
    $('.page-link.number').remove();
    for (var i = 1; i <= totalPages; i++) {
        $('#pageEnd').before('<li class="page-item number"><span class="page-link number" value=' + i + '>' + i + '</span></li>')
    }
}

function updateTable(results) {
    var tableBody = $('#tbodyData');
    tableBody.empty();

    if (results.length === 0) {
        tableBody.append('<tr><td colspan="9" class="text-center">No courses found.</td></tr>');
    } else {
        $.each(results, function (index, course) {
            var row = '<tr>' +
                '<td>' + course.id + '</td>' +
                //'<td>' + course.courseId + '</td>' +
                '<td>' + course.courseName + '</td>' +
                //'<td>' + course.courseCredits + '</td>' +
                //'<td>' + course.courseCapacity + '</td>' +
                //'<td>' + course.courseVacancy + '</td>' +
                '<td>' + course.faculty + '</td>' +
                '<td>' + course.courseStartDate + '</td>' +
                '<td>' + course.courseEndDate + '</td>' +
                '<td><span class="btn btn-warning" id="enrolCourses" onclick="enrolCourses(' + course.id + ')">Enrol</span></td>' +
                '</tr>';
            tableBody.append(row);
        });
    }
}

// 点击enrol 之后，传输courseId给后端，如果选课成功，需要courseId+studentId保存当前课程
function enrolCourses(courseId) {
    console.log(courseId)
    $.ajax({
        url: 'http://localhost:8080/course-students/save/' + courseId, //这里路径需要替换
        type: 'POST',
        success: function (response) { // 返回true 选课成功, false选课失败
            alert("Enrolled successfuly.");
            searchCourses(1, true)
        },
        error: function (xhr, status, error) {
            alert("Erolled failed.");
        }
    });
}

