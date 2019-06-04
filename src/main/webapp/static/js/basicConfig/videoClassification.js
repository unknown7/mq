$(function () {
    var maxSort = 0;
    var $dt = $('#classification_table').on('preXhr.dt', function (e, settings, data) {
        //data.search.value = $searchForm.formGet();
    }).dataTable({
        columns: [
            {
                "data": "vName",
                "class": "text-center"
            },
            {
                "data": "sort",
                "class": "text-center",
                "render": function (data, type, row) {
                    if (maxSort < data) {
                        maxSort = data;
                    }
                    return data;
                }
            },
            {
                "data": "defaultProfitShare",
                "class": "text-center",
                "render": function (data, type, row) {
                    return (Math.format(data * 100, 2)) + "%";
                }
            },
            {
                "data": "defaultFreeWatchTime",
                "class": "text-center",
                "render": function (data, type, row) {
                    return data + "秒";
                }
            },
            {
                "data": "updateTime",
                "class": "text-center",
                "render": function (data, type, row) {
                    return new Date(data).Format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {
                "data": "desc",
                "class": "text-center",
                "render": function(data, type, row) {
                    var length = 0;
                    var result = data;
                    var i;
                    if (data.length > 50) {
                        for (i = 0; length < 100 && i < data.length; i++) {
                            if (data.charCodeAt(i) > 127 || data.charCodeAt(i) == 94) {
                                length += 2;
                            } else {
                                length += 1;
                            }
                        }
                        if (length >= 100) {
                            result = data.substring(0, i - 1) + "...";
                        }
                    }
                    return result;
                }
            },
            {
                "data": "id",
                "class": "text-center",
                "render": function (data, type, row) {
                    return '<span class="btn btn-primary btn-xs ml-5" data-id="' + data + '" onclick="editClassification(' + data + ')">编辑</span> ' +
                        '<span class="btn btn-danger btn-xs ml-5" data-id="' + data + '" onclick="removeClassification(' + data + ')">删除</span>';
                }
            }
        ],
        ajax: function (data, callback, settings) {
            var param = {};//因为服务端排序，可以新建一个参数对象
            param.start = data.start;//开始的序号
            param.length = data.length;//要取的数据的
            param.page = (data.start / data.length) + 1;//当前页码
            var formData = $("#classification_search_form").serializeArray();//把form里面的数据序列化成数组
            formData.forEach(function (e) {
                param[e.name] = e.value;
            });
            $.ajax({
                type: "POST",
                url: ctx + "/basicConfig/findClassification",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    maxSort = 0;
                    //封装返回数据
                    var returnData = {};
                    returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    returnData.recordsTotal = result.total;//返回数据全部记录
                    returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = result.list;//返回的数据列表
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

    $('.default-value-slider').css({width: '77%', 'float': 'right', margin: '15px'}).empty().slider({
        value: 0,
        range: "min",
        animate: true,
        slide: function (event, handle) {
            // $('#defaultProfitShareValue').val(handle.value);
            $(this).prev().prev().val(handle.value);
        },
        change: function (event, handle) {
            $(this).prev().prev().val(handle.value);
            // $('#defaultProfitShareValue').val(handle.value);
            // $('#defaultProfitShareValue').val(handle.value);
        }
    });

    $('#classificationForm input[name=sort]').ace_spinner({
        value: 0,
        min: 0,
        max: 100,
        step: 1,
        on_sides: true,
        icon_up: 'ace-icon fa fa-plus bigger-110',
        icon_down: 'ace-icon fa fa-minus bigger-110',
        btn_up_class: 'btn-success',
        btn_down_class: 'btn-danger'
    });

    $('#classification-detail-form').on('hide.bs.modal', function () {
        $(':input', '#classificationForm').not(':button,:submit,:reset').val('').removeAttr('checked');
        $('.default-value-slider').slider({
            value: 0
        });
        $('.default-value-input').val(0);
        $('#classificationForm').find('input, textarea').filter('.valid').removeClass('valid');
    });

    $('#classificationForm input, textarea').on('keydown', function () {
        $('#classificationForm').find('input, textarea').filter('.valid').removeClass('valid');
    });

    $('#classificationForm').validate({
        rules: {
            vName: {
                required: true
            },
            desc: {
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
            _shake($(shakes).not("#defaultProfitShareValue, #defaultFreeWatchTimeValue").closest('.form-group'));
        }
    });

    $('#saveClassification').on('click', function (e) {
        if ($('#classificationForm').valid()) {
            $('button').attr('disabled', 'disabled');
            var formData = new FormData($("#classificationForm")[0]);
            $.ajax({
                type: "POST",
                dataType: "json",
                url: ctx + "/basicConfig/saveClassification",
                data: formData,
                cache: false,
                contentType: false,
                processData: false,
                success: function (result) {
                    $('button').removeAttr('disabled');
                    if (result.success) {
                        _alert('success', result.msg);
                        $('#classification-detail-form').modal('hide');
                        $('#classification_table').DataTable().ajax.reload();
                    } else {
                        _alert('error', result.msg);
                    }
                }
            });
        }
    });

    $('#classification_query').on('click', function (e) {
        $('#classification_table').DataTable().ajax.reload();
    });
    $('#classification_clear').on('click', function (e) {
        $("#classification_search_form")[0].reset();
    });
    $('#classification_add').on('click', function (e) {
        $('#classificationForm input[name=sort]').val(maxSort + 1);
        $('#classification-detail-form').modal('show');
    });
});
var removeClassification = function (id) {
    bootbox.confirm({
        message: '您确定要删除这个分类吗？(删除后不可恢复)',
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
                    url: ctx + "/basicConfig/removeClassification",
                    data: {
                        id: id
                    },
                    cache: false,
                    success: function (result) {
                        $('button').removeAttr('disabled')
                        if (result.success) {
                            _alert('success', result.msg);
                            $('#classification_table').DataTable().ajax.reload();
                        } else {
                            _alert('error', result.msg);
                        }
                    }
                });
            }
        }
    });
}
var editClassification = function (id) {
    $.ajax({
        type: "POST",
        url: ctx + "/basicConfig/selectOneClassification",
        cache: false,  //禁用缓存
        data: {
            id: id
        },
        dataType: "json",
        success: function (result) {
            $('#classification-detail-form').find('input, textarea').each(function () {
                var that = $(this);
                var value = result[that.attr('name')];
                if (that.attr('type') == 'number') {
                    if (that.attr('name') == 'defaultProfitShare') {
                        value = Math.format(value * 100, 2);
                    }
                    that.val(value).keyup();
                }
                that.val(value);
            });
            $('#classification-detail-form').modal('show');
        }
    });
}