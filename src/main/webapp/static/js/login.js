$(function () {
    var $loginForm = $('#loginForm');
    $loginForm.find('input[name=username]').focus();
    $('#btn-login-dark').on('click', function (e) {
        $('body').attr('class', 'login-layout');
        $('#id-text2').attr('class', 'white');
        $('#id-company-text').attr('class', 'blue');

        e.preventDefault();
    });
    $('#btn-login-light').on('click', function (e) {
        $('body').attr('class', 'login-layout light-login');
        $('#id-text2').attr('class', 'grey');
        $('#id-company-text').attr('class', 'blue');

        e.preventDefault();
    });
    $('#btn-login-blur').on('click', function (e) {
        $('body').attr('class', 'login-layout blur-login');
        $('#id-text2').attr('class', 'white');
        $('#id-company-text').attr('class', 'light-blue');

        e.preventDefault();
    });
    $('#loginForm').validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            username: {
                required: "请输入用户名"
            },
            password: {
                required: "请输入密码"
            }
        },
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
        invalidHandler: function (form) {
            var $username = $loginForm.find('input[name=username]');
            var $password = $loginForm.find('input[name=password]');
            if ($username.val() == '') {
                _shake($username.closest('.form-group'));
            } else {
                _shake($password.closest('.form-group'));
            }
        }
    });
    $("#login").on('click', function (e) {
        if ($('#loginForm').valid()) {
            $.ajax({
                type: "POST",
                dataType: "json",
                url: ctx + "/loginAuth",
                data: $('#loginForm').serialize(),
                success: function (result) {
                    if (result.success) {
                        $(window).attr('location', ctx + '/index');
                    } else {
                        // $('#err_info').removeClass('hide');
                        // $('#err_info').html("<font color='red'>" + result.msg + "</font>");
                        _alert('error', result.msg);
                        $('.form-group').each(function () {
                            _shake($(this));
                        })
                    }
                }
            });
        }
    });
    $('.form-control').on('keydown', function (e) {
        // $(this).closest('.form-group').removeClass('has-error');
        $('#err_info').addClass('hide');
    });
    $(document).keyup(function (event) {
        if (event.keyCode == 13) {
            $("#login").focus().trigger("click");
        }
    });
});