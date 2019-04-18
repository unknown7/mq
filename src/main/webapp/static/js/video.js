$(function () {
    var $dt = $('#table').on('preXhr.dt', function (e, settings, data) {
        //data.search.value = $searchForm.formGet();
    }).dataTable({
        columns: [
            {
                "data": "title",
                "class": "text-center"
            },
            {
                "data": "subtitle",
                "class": "text-center"
            },
            {
                "data": "price",
                "class": "text-center",
                "render": function (data, type, row) {
                    return "￥" + data;
                }
            },
            {
                "data": "coverRealName",
                "class": "text-center",
                "render": function (data, type, row) {
                    return '<img class="tooltipa" src="' + image + data + '" width="30" height="30">';
                }
            },
            {
                "data": "descriptionRealName",
                "class": "text-center",
                "render": function (data, type, row) {
                    return '<img class="tooltipa" src="' + image + data + '" width="30" height="30">';
                }
            },
            {
                "data": "classificationName",
                "class": "text-center"
            },
            {
                "data": "shareCommission",
                "class": "text-center",
                "render": function (data, type, row) {
                    return (data * 100) + "%";
                }
            },
            {
                "data": "accessed",
                "class": "text-center"
            },
            {
                "data": "watched",
                "class": "text-center"
            },
            {
                "data": "purchased",
                "class": "text-center"
            },
            {
                "data": "id",
                "class": "text-center",
                "render": function (data, type, row) {
                    var result = '<span class="btn btn-primary btn-xs ml-5" data-id="' + data + '" onclick="edit(' + data + ')">编辑</span> ';
                    /**
                     * 未上传
                     */
                    if (row.status == '0101') {
                        result +=
                            '<span class="btn btn-danger btn-xs ml-5" data-id="' + data + '" onclick="remove(' + data + ')">删除</span> ' +
                            '<span class="btn btn-purple btn-xs ml-5" data-id="' + data + '" onclick="upload(' + data + ')">上传</span> ';
                    }
                    /**
                     * 未发布
                     */
                    else if (row.status == '0102') {
                        result +=
                            '<span class="btn btn-danger btn-xs ml-5" data-id="' + data + '" onclick="remove(' + data + ')">删除</span> ' +
                            '<span class="btn btn-success btn-xs ml-5" data-id="' + data + '" onclick="release(' + data + ')">发布</span> ';
                    }
                    /**
                     * 已发布
                     */
                    else {
                        result = '<span class="btn btn-danger btn-xs ml-5" data-id="' + data + '" onclick="pulloff(' + data + ')">下架</span>';
                    }
                    return result;
                }
            }
        ],
        ajax: function (data, callback, settings) {
            var param = {};//因为服务端排序，可以新建一个参数对象
            param.start = data.start;//开始的序号
            param.length = data.length;//要取的数据的
            param.page = (data.start / data.length) + 1;//当前页码
            var formData = $("#search_form").serializeArray();//把form里面的数据序列化成数组
            formData.forEach(function (e) {
                param[e.name] = e.value;
            });
            $.ajax({
                type: "POST",
                url: ctx + "/video/find",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    //封装返回数据
                    var returnData = {};
                    returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    returnData.recordsTotal = result.recordsTotal;//返回数据全部记录
                    returnData.recordsFiltered = result.recordsTotal;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = result.data;//返回的数据列表
                    //console.log(returnData);
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);
                }
            });
        },
        //"ajax": $.fn.dataTable.pagerAjax({url: "/listData"}),
        destroy: true,
        lengthChange: false,
        serverSide: true,//分页，取数据等等的都放到服务端去
        searching: false,
        processing: true,//载入数据的时候是否显示“载入中”
        bDestroy: true,
        pageLength: 10,//首次加载的数据条数
        ordering: false,//排序操作在服务端进行，所以可以关了。
        language: {
            processing: "载入中",//处理页面数据的时候的显示
            paginate: {//分页的样式文本内容。
                previous: "上一页",
                next: "下一页",
                first: "第一页",
                last: "最后一页"
            },
            zeroRecords: "没有内容",//table tbody内容为空时，tbody的内容。
            //下面三者构成了总体的左下角的内容。
            info: "第 _PAGE_/_PAGES_页 共 _TOTAL_条记录",//左下角的信息显示，大写的词为关键字。
            infoEmpty: "第 _PAGE_/_PAGES_页 共 _TOTAL_条记录",//筛选为空时左下角的显示。
            infoFiltered: ""//筛选之后的左下角筛选提示(另一个是分页信息显示，在上面的info中已经设置，所以可以不显示)，
        },
        columnDefs: [{
            "defaultContent": "",
            "targets": "_all"
        }]
    }).on('click', 'a[row-index]', function () {
    });

    $.ajax({
        type: "POST",
        url: ctx + "/video/findAllClassification",
        cache: false,  //禁用缓存
        data: {},  //传入组装的参数
        dataType: "json",
        success: function (result) {
            $.each(result, function (i, item) {
                $('#search_form select[name=classification]').append('<option value="' + item.id + '">' + item.vName + '</option>');
                $('#videoForm select[name=classification]').append('<option value="' + item.id + '">' + item.vName + '</option>');
            });
        }
    });

    $('#vo').on('click', function (e) {
        $('#table').DataTable().ajax.reload();
    });
    $('#clear').on('click', function (e) {
        $("#search_form")[0].reset();
    });
    $('#add').on('click', function (e) {
        $('#video-detail-form').modal('show');
    });

    $('#shareCommission').css({width: '77%', 'float': 'right', margin: '15px'}).empty().slider({
        value: 0,
        range: "min",
        animate: true,
        slide: function (event, handle) {
            $('#shareCommissionValue').val(handle.value);
        },
        change: function (event, handle) {
            $('#shareCommissionValue').val(handle.value);
        }
    });

    $('#price').ace_spinner({
        value: 0,
        min: 0,
        max: 10000,
        step: 0.1,
        on_sides: true,
        icon_up: 'ace-icon fa fa-plus bigger-110',
        icon_down: 'ace-icon fa fa-minus bigger-110',
        btn_up_class: 'btn-success',
        btn_down_class: 'btn-danger'
    });

    var fileInputs = [
        {
            'id': 'cover',
            'title': '请上传视频封面',
            'msg': '请上传图片类型的文件作为封面'
        }, {
            'id': 'description',
            'title': '请上传视频详情',
            'msg': '请上传图片类型的文件作为详情'
        }
    ];
    $.each(fileInputs, function (i, item) {
        $('#' + item.id).ace_file_input({
            style: 'well',
            btn_choose: item.title,
            btn_change: null,
            no_icon: 'ace-icon fa fa-picture-o',
            droppable: true,
            thumbnail: 'large',
            allowExt: ["jpeg", "jpg", "png", "gif", "bmp"],
            allowMime: ["image/jpg", "image/jpeg", "image/png", "image/gif", "image/bmp"],
            maxFileSize: 10,
            before_change: function (files, dropped) {
                //选择文件 展示之前的事件
                //return true 保留当前文件; return false 不保留文件；return -1 重置文件框
                //需要同步等待返回结果
                $('#' + item.id + '_success').val('success');
                return true;
            }
        }).on('file.error.ace', function (e, info) {
            _alert('info', item.msg, '错误的文件格式');
            _shake($(this).parent());
            $(this).next().val('');
        });
    });

    $('#video').ace_file_input({
        style: 'well',
        btn_choose: '请上传视频',
        btn_change: null,
        no_icon: 'ace-icon fa fa-cloud-upload',
        droppable: true,
        thumbnail: 'large',
        allowExt: ["avi", "mov", "rm", "rmvb", "mp4", "flv", "3gp", "wmv"],
        allowMime: ["video/x-msvideo", "video/quicktime", "application/vnd.rn-realmedia", "application/vnd.rn-realmedia", "video/mp4", "video/x-flv", "video/3gpp", "video/x-ms-wmv"],
        maxFileSize: 10,
        before_change: function (files, dropped) {
            //选择文件 展示之前的事件
            //return true 保留当前文件; return false 不保留文件；return -1 重置文件框
            //需要同步等待返回结果
            $('#video_success').val('success');
            return true;
        }
    }).on('file.error.ace', function (e, info) {
        _alert('info', "请上传视频类型的文件", '错误的文件格式');
        _shake($(this).parent());
        $('#video_success').val('');
    });

    $('#save').on('click', function (e) {
        if ($('#videoForm').valid()) {
            var cover_success = $('#cover_success').val();
            var description_success = $('#description_success').val();
            if (cover_success == 'success' && description_success == 'success') {
                $('button').attr('disabled', 'disabled');
                var formData = new FormData($("#videoForm")[0]);
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: ctx + "/video/save",
                    data: formData,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (result) {
                        $('button').removeAttr('disabled');
                        if (result.success) {
                            _alert('success', result.msg);
                            $('#video-detail-form').modal('hide');
                            $('#table').DataTable().ajax.reload();
                        } else {
                            _alert('error', result.msg);
                        }
                    }
                });
            } else if (cover_success != 'success' && description_success != 'success') {
                _shake($('input[type=file]').parent());
            } else if (cover_success != 'success') {
                _shake($('#cover_success').parent());
            } else if (description_success != 'success') {
                _shake($('#description_success').parent());
            }
        }
    });

    $('#videoForm').validate({
        rules: {
            title: {
                required: true
            },
            subtitle: {
                required: true
            },
            classification: {
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
            _shake($(shakes).not('#cover_success, #description_success, #shareCommissionValue').closest('.form-group'));
            if ($('#cover_success').val() == '') {
                _shake($('#cover_success').parent());
            }
            if ($('#description_success').val() == '') {
                _shake($('#description_success').parent());
            }
        }
    });

    $('#fileDiv .remove').on('click', function () {
        $(this).parent().next().val('');
    });

    $('#video-detail-form').on('hide.bs.modal', function () {
        $(':input', '#videoForm').not(':button,:submit,:reset').val('').removeAttr('checked');
        $('#shareCommission').slider({
            value: 0
        });
        $('#shareCommissionValue').val(0);
        $('#price').val(0);
        $('#videoForm').find('input, select').filter('.valid').removeClass('valid');
        $('#fileDiv .remove').trigger('click');
    });

    $('#video-upload-form').on('hide.bs.modal', function () {
        $('#videoUploadForm input[name=id]').val('');
        $('#progressbar').progressbar("value", 0);
        $('#videoDiv .remove').trigger('click');
    });

    $('#videoForm input, textarea').on('keydown', function () {
        $('#videoForm').find('input, textarea').filter('.valid').removeClass('valid');
    });

    $("#progressbar").progressbar({
        value: 0,
        create: function (event, ui) {
            $(this).addClass('progress progress-striped active')
                .children(0).addClass('progress-bar progress-bar-success');
        },
        change: function () {
            $(".progress-label").text($("#progressbar").progressbar("value") + "%");
        },
        complete: function () {
            $(".progress-label").text("完成！");
        }
    });

    $('#upload').on('click', function () {
        var video_success = $('#video_success').val();
        if (video_success == 'success') {
            $('#videoDiv .remove').addClass('hide');
            $('#video-upload-form .close').addClass('hide');
            $("#progressForm").removeClass('hide');
            progress();
        } else {
            _shake($('#video_success').parent());
        }
    });
});
var edit = function (id) {
    $.ajax({
        type: "POST",
        url: ctx + "/video/selectOne",
        cache: false,  //禁用缓存
        data: {
            id: id
        },
        dataType: "json",
        success: function (result) {
            $('#videoForm').find('input, select').each(function () {
                var value = result[$(this).attr('name')];
                if ($(this).attr('type') == 'number') {
                    value = value * 100;
                    $(this).val(value).keyup();
                }
                $(this).val(value);
            });
            var data = [
                {
                    'id': 'cover',
                    'oldTile': "请上传视频封面",
                    'newTile': result.coverName,
                    'url': image + result.coverRealName
                },
                {
                    'id': 'description',
                    'oldTile': "请上传视频详情",
                    'newTile': result.descriptionName,
                    'url': image + result.descriptionRealName
                }
            ];
            $.each(data, function (i, item) {
                var span = $('span[data-title=' + item.oldTile + ']');
                $(span).addClass('hide-placeholder selected');
                $(span).children()
                    .attr('data-title', item.newTile)
                    .addClass('large')
                    .prepend("<img class='middle'" +
                        "style='backgroud-image: url(" + item.url + ");width: 150px;height: 112px;'" +
                        "src='" + item.url + "'>")
                    .children().attr('class', ' ace-icon fa fa-picture-o file-image');
                $('#' + item.id + '_success').val('success');
            });

            $('#video-detail-form').modal('show');
        }
    });
}
var remove = function (id) {
    bootbox.confirm({
        message: '您确定要删除这个视频吗？(删除后不可恢复)',
        buttons: {
            confirm: {
                label: '确认'
            },
            cancel: {
                label: '取消'
            }
        },
        callback: function (result) {
            if (result) {
                $('button').attr('disabled', 'disabled');
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: ctx + "/video/remove",
                    data: {
                        id: id
                    },
                    cache: false,
                    success: function (result) {
                        $('button').removeAttr('disabled')
                        if (result.success) {
                            _alert('success', result.msg);
                            $('#table').DataTable().ajax.reload();
                        } else {
                            _alert('error', result.msg);
                        }
                    }
                });
            }
        }
    });
}
var pulloff = function (id) {
    bootbox.confirm({
        message: '您确定要下架这个视频吗？',
        buttons: {
            confirm: {
                label: '确认'
            },
            cancel: {
                label: '取消'
            }
        },
        callback: function (result) {
            if (result) {
                $('button').attr('disabled', 'disabled');
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: ctx + "/video/pulloff",
                    data: {
                        id: id
                    },
                    cache: false,
                    success: function (result) {
                        $('button').removeAttr('disabled')
                        if (result.success) {
                            _alert('success', result.msg);
                            $('#table').DataTable().ajax.reload();
                        } else {
                            _alert('error', result.msg);
                        }
                    }
                });
            }
        }
    });
}
var release = function (id) {
    bootbox.confirm({
        message: '您确定要发布这个视频吗？',
        buttons: {
            confirm: {
                label: '确认'
            },
            cancel: {
                label: '取消'
            }
        },
        callback: function (result) {
            if (result) {
                $('button').attr('disabled', 'disabled');
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: ctx + "/video/release",
                    data: {
                        id: id
                    },
                    cache: false,
                    success: function (result) {
                        $('button').removeAttr('disabled')
                        if (result.success) {
                            _alert('success', result.msg);
                            $('#table').DataTable().ajax.reload();
                        } else {
                            _alert('error', result.msg);
                        }
                    }
                });
            }
        }
    });
}
var upload = function(id) {
    $('#videoUploadForm input[name=id]').val(id);
    $('#video-upload-form').modal('show');
}
var progress = function () {
    $('button').attr('disabled', 'disabled');
    var formData = new FormData($("#videoUploadForm")[0]);
    var complete = false;
    $.ajax({
        type: "POST",
        dataType: "json",
        url: ctx + "/video/upload",
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        success: function (result) {
            complete = true;
            $('button').removeAttr('disabled');
            if (result.success) {
                _alert('success', result.msg);
                $('#videoDiv .remove').removeClass('hide');
                $('#video-upload-form .close').removeClass('hide');
                $('#progressForm').addClass('hide');
                $('#video-upload-form').modal('hide');
                $('#table').DataTable().ajax.reload();
            } else {
                _alert('error', result.msg);
            }
        }
    });
    var video = $('#videoUploadForm input[name=video]').get(0).files[0];
    var kb = video.size / 1000;
    /**
     * 默认1024KB / 秒
     * @type {number}
     */
    var simulation = kb / 1024;
    var section = 100 / simulation;
    var denominator = 1;
    var step = 0.01;
    var result = 0;
    var $progressbar = $("#progressbar");
    var cal = 0;
    var interval = setInterval(function () {
        if (!complete) {
            cal = Math.format(section / denominator, 7);
            result += cal;
            $progressbar.progressbar("value", result);
            denominator = denominator + step;
        } else {
            $progressbar.progressbar("value", 100);
            clearInterval(interval);
        }
    }, 1000);
}