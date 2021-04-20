$(function () {
    var $dt = $('#table').on('preXhr.dt', function (e, settings, data) {
        //data.search.value = $searchForm.formGet();
    }).dataTable({
        columns: [
            {
                "data": "orderNo",
                "class": "text-center"
            },
            {
                "data": "orderStatus",
                "class": "text-center"
            },
            {
                "data": "title",
                "class": "text-center"
            },
            {
                "data": "goodsType",
                "class": "text-center"
            },
            {
                "data": "goodsPrice",
                "class": "text-center"
            },
            {
                "data": "nickName",
                "class": "text-center",
                "render": function (data, type, row) {
                    if (data && row.userId) {
                        return '<a href="javascript:void(0)" data-id="' + data + '" onclick="view(' + row.userId + ')">' + data + '</a>';
                    }
                    return '';
                }
            },
            {
                "data": "totalAmount",
                "class": "text-center"
            },
            {
                "data": "points",
                "class": "text-center"
            },
            {
                "data": "payAmount",
                "class": "text-center"
            },
            {
                "data": "createdTime",
                "class": "text-center",
                "render": function (data, type, row) {
                    return new Date(data).Format("yyyy-MM-dd hh:mm:ss");
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
                url: ctx + "/order/find",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
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

    $('#query').on('click', function (e) {
        $('#table').DataTable().ajax.reload();
    });
    $('#clear').on('click', function (e) {
        $("#search_form")[0].reset();
    });

    $('.datepicker').datepicker({
        language: 'zh-CN',
        autoclose: true
    }).on('hide', function (e) {
        e.stopPropagation();
    });
});

var view = function(id) {
    $.ajax({
        type: "POST",
        url: ctx + "/user/selectOne",
        cache: false,  //禁用缓存
        data: {
            id: id
        },
        dataType: "json",
        success: function (result) {
            $('#userForm').find('input, select').each(function () {
                var that = $(this);
                var value = result[that.attr('name')];
                if (that.attr('name') == 'createdTime') {
                    value = new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }
                that.val(value);
            });
            var data = [
                {
                    'id': 'cover',
                    'oldTile': "请上传用户头像",
                    'newTile': "用户头像",
                    'url': result.avatarUrl
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
            });
            viewStatus();
            $('#user-detail-form').modal('show');
        }
    });
}
var viewStatus = function() {
    $('#userForm').find("input[type=text], input[type=number]").attr("readonly", "readonly");
    $('#userForm').find("input[type=file], select").attr("disabled", "disabled");
    $("#avatar").next().next().css("display", "none");
}