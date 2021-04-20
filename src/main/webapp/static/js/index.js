$(function () {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: ctx + "/selectMenuTree",
        success: function (result) {
            var ul = $('#menu');
            loadMenu(result, ul);
            $(ul).find('li[class=active]').find('a').click();
        }
    });

    $('#passwordModificationForm').validate({
        rules: {
            oldPassword: {
                required: true
            },
            newPassword: {
                required: true
            },
            newPasswordConfirm: {
                required: true
            }
        },
        messages: {},
        highlight: function (e) {
        },
        success: function (e) {
            // $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
            // $(e).remove();
        },
        errorPlacement: function (error, element) {
        },
        submitHandler: function (form) {
        },
        invalidHandler: function (form, e) {
            var shakes = $(e.currentElements).not('.valid');
            _shake($(shakes).closest('.form-group'));
        }
    });

    $('#savePassword').on('click', function (e) {
        if ($('#passwordModificationForm').valid()) {
            var newPassword = $('#passwordModificationForm input[name=newPassword]').val();
            var newPasswordConfirm = $('#passwordModificationForm input[name=newPasswordConfirm]').val();
            if (newPassword != newPasswordConfirm) {
                _shake($('#passwordModificationForm input[name=newPasswordConfirm]').closest('.form-group'));
            } else {
                $('button').attr('disabled', 'disabled');
                var formData = new FormData($("#passwordModificationForm")[0]);
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: ctx + "/employee/password/modification",
                    data: formData,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (result) {
                        $('button').removeAttr('disabled');
                        if (result.success) {
                            _alert('success', result.msg);
                            $('#detail-form').modal('hide');
                            setTimeout(function() {
                                window.location = ctx + "/logout";
                            }, 1200);
                        } else {
                            _alert('error', result.msg);
                        }
                    }
                });
            }
        }
    });

    $('#password-modification-form').on('hide.bs.modal', function () {
        $(':input', '#passwordModificationForm').not(':button,:submit,:reset,[name=id]').val('');
        $('#passwordModificationForm').find('input').filter('.valid').removeClass('valid');
    });
});
var loadMenu = function (menu, ul) {
    $.each(menu, function (i, item) {
        var li = $('<li></li>');
        if (item.id == 1) {
            li.attr('class', 'active');
        }
        var a = $('<a></a>');
        a.attr('href', 'javascript:void(0)').attr('onclick', 'menuOnClick("' + item.url + '", "' + item.mName + '")').attr('mname', item.mName);
        var i = $('<i></i>');
        i.attr('class', item.icon);
        var span = $('<span></span>');
        span.attr('class', 'menu-text');
        $(span).append(item.mName);
        var b = $('<b></b>');
        b.attr('class', 'arrow');
        $(a).append(i).append(span);
        $(li).append(a).append(b);
        if (item.child.length > 0) {
            var ib = $('<b></b>');
            ib.attr('class', 'arrow fa fa-angle-down');
            $(a).append(ib);
            a.attr('class', 'dropdown-toggle');
            var sul = $('<ul></ul>');
            sul.attr('class', 'submenu');
            loadMenu(item.child, sul);
            $(li).append(sul);
        }
        $(ul).append(li);
    });
};
var menuOnClick = function (url, mName) {
    var arr = new Array();
    arr.push(mName);
    var cur = $('#menu').find('a[mName=' + mName + ']');
    while (true) {
        var ul = $(cur).parent().parent();
        if ($(ul).attr('id') != 'menu') {
            cur = $(ul).prev().prev();
            var name = $(cur).attr('mname');
            arr.push(name);
        } else {
            break;
        }
    }
    if (url != undefined && url != 'undefined' && url != '') {
        $('#path').find('.temp').remove();
        for (var i = arr.length - 1; i >= 0; i--) {
            $('#path').append('<li class="active temp">' + arr[i] + '</li>');
        }
        $('#menu').find('li[class=active]').removeAttr('class');
        var selected = $('#menu').find('a[mName=' + mName + ']');
        $(selected).parent().attr("class", "active");
        $('#iframe').attr('src', ctx + url);
    }
}
var changePasswordOnClick = function() {
    $('#password-modification-form').modal('show');
}