$(function () {
    var $dt = $('#table').on('preXhr.dt', function (e, settings, data) {
        //data.search.value = $searchForm.formGet();
    }).dataTable({
        columns: [
            {
                "data": "eName",
                "class": "text-center"
            },
            {
                "data": "gender",
                "class": "text-center",
                "render": function (data, type, row) {
                    if (data == 'female') {
                        return "女";
                    } else if (data == 'male') {
                        return "男";
                    }
                    return "";
                }
            },
            {
                "data": "birth",
                "class": "text-center",
                "render": function (data, type, row) {
                    return new Date(data).Format("yyyy-MM-dd");
                }
            },
            {
                "data": "age",
                "class": "text-center"
            },
            // {
            //     "data": "avatar",
            //     "class": "text-center",
            //     "render": function (data, type, row) {
            //         return '<img src="' + ctx + data + '" width="20%" height="15%"/>';
            //     }
            // },
            {
                "data": "username",
                "class": "text-center"
            },
            {
                "data": "mobile",
                "class": "text-center"
            },
            {
                "data": "email",
                "class": "text-center"
            },
            {
                "data": "wechat",
                "class": "text-center"
            },
            {
                "data": "id",
                "class": "text-center",
                "render": function (data, type, row) {
                    if (data != 1 || cur == 1)
                        return '<span class="btn btn-primary btn-xs ml-5" data-id="' + data + '" onclick="edit(' + data + ')">编辑</span> ' +
                            '<span class="btn btn-warning btn-xs ml-5" data-id="' + data + '" onclick="empower(' + data + ')">赋权</span> ' +
                            '<span class="btn btn-danger btn-xs ml-5" data-id="' + data + '" onclick="remove(' + data + ')">删除</span>';
                    return "超级管理员";
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
                url: ctx + "/employee/find",
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

    $('#avatar').ace_file_input({
        style: 'well',
        btn_choose: '请上传员工头像',
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
            $('#success').val('success');
            return true;
        }
    }).on('file.error.ace', function (e, info) {
        _alert('info', '请上传图片类型的文件作为头像', '错误的文件格式');
        _shake($('#fileDiv'));
        $('#success').val('');
    });

    $('#employeeForm').validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            },
            eName: {
                required: true
            },
            birth: {
                required: true
            },
            gender: {
                required: true
            },
            success: {
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
            _shake($(shakes).not('#success').closest('.form-group'));
            if ($('#success').val() == '') {
                _shake($('#fileDiv'));
            }
        }
    });
    $('#save').on('click', function (e) {
        if ($('#employeeForm').valid()) {
            var uploaded = $('#success').val();
            if (uploaded == 'success') {
                $('button').attr('disabled', 'disabled');
                var formData = new FormData($("#employeeForm")[0]);
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: ctx + "/employee/save",
                    data: formData,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (result) {
                        $('button').removeAttr('disabled');
                        if (result.success) {
                            _alert('success', result.msg);
                            $('#detail-form').modal('hide');
                            $('#table').DataTable().ajax.reload();
                        } else {
                            _alert('error', result.msg);
                        }
                    }
                });
            } else {
                _shake($('#fileDiv'));
            }
        }
    });

    $('#detail-form').on('hide.bs.modal', function () {
        $(':input', '#employeeForm').not(':button,:submit,:reset').val('').removeAttr('checked').removeAttr('readonly');
        $('#fileDiv .remove').focus().trigger("click");
        $('#success').val('');
        $('#employeeForm').find('input, select').filter('.valid').removeClass('valid');
    });

    $('.datepicker').datepicker({
        language: 'zh-CN',
        autoclose: true
    }).on('hide', function (e) {
        e.stopPropagation();
    });

    $('#auth').on('click', function (e) {
        $('#table').DataTable().ajax.reload();
    });
    $('#clear').on('click', function (e) {
        $("#search_form")[0].reset();
    });

    $('#employeeForm input, select').on('keydown', function() {
        $('#employeeForm').find('input, select').filter('.valid').removeClass('valid');
    });

    $('#password').on('focus', function () {
       $(this).select();
    });

    $('#saveRight').on('click', function (e) {
        $('button').attr('disabled', 'disabled');
        var ids = [];
        var checked = $('#tree1').treeview('getChecked');
        $.each(checked, function (i, item) {
            ids.push(item.id);
        });
        $.ajax({
            type: "POST",
            url: ctx + "/employee/saveRight",
            cache: false,  //禁用缓存
            data: {
                ids: ids.join(","),
                employeeId: $('#tree-view-form').find('input[name=id]').val()
            },
            dataType: "json",
            success: function (result) {
                $('button').removeAttr('disabled')
                if (result.success) {
                    _alert('success', result.msg);
                    $('#tree-view-form').modal('hide');
                } else {
                    _alert('error', result.msg);
                }
            }
        });
    });

    $('#fileDiv .remove').on('click', function () {
        $('#success').val('');
    });
});
var edit = function (id) {
    $.ajax({
        type: "POST",
        url: ctx + "/employee/selectOne",
        cache: false,  //禁用缓存
        data: {
            id: id
        },
        dataType: "json",
        success: function (result) {
            $('#detail-form').find('input[type=text], input[type=password], input[type=hidden], select').each(function () {
                var $this = $(this);
                var value = result[$this.attr('name')];
                if ($this.hasClass('datepicker')) {
                    $this.val(new Date(value).Format("yyyy-MM-dd"));
                } else {
                    $this.val(value);
                }
                if ($this.attr('name') == 'username') {
                    $this.attr('readonly', 'readonly');
                }
            });
            var span = $('span[data-title=请上传员工头像]');
            $(span).addClass('hide-placeholder selected');
            $(span).children()
                .attr('data-title', result.avatarName)
                .addClass('large')
                .prepend("<img class='middle'" +
                    "style='backgroud-image: url(" + image + result.avatarRealName + ");width: 150px;height: 112px;'" +
                    "src='" + image + result.avatarRealName + "'>")
                .children().attr('class', ' ace-icon fa fa-picture-o file-image');
            $('#success').val('success');
            $('#detail-form').modal('show');
        }
    });
}
var empower = function (id) {
    reloadTree(id);
    $('#tree-view-form').find('input[name=id]').val(id);
    $('#tree-view-form').modal('show');
}
var remove = function (id) {
    bootbox.confirm({
        message: '您确定要删除这位员工吗？(删除后不可恢复)',
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
                    url: ctx + "/employee/remove",
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
var reloadTree = function (id) {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: ctx + "/employee/selectTreeView",
        cache: false,
        data: {
            id: id
        },
        success: function (result) {
            $('#tree1').treeview({
                data: result,
                multiSelect: true,
                showCheckbox: true,
                showBorder: true,
                highlightSelected: true,
                levels: 7,
                onNodeSelected: function (event, data) {
                    $(this).treeview('checkNode', [ data.nodeId, { silent: true } ]);
                },
                onNodeUnselected : function (event, data) {
                    $(this).treeview('uncheckNode', [ data.nodeId, { silent: true } ]);
                },
                onNodeChecked: function (event, data) {
                    $(this).treeview('selectNode', [ data.nodeId, { silent: true } ]);
                },
                onNodeUnchecked : function (event, data) {
                    $(this).treeview('unselectNode', [ data.nodeId, { silent: true } ]);
                }
            });
        }
    });
}