jQuery.fn.shake = function (intShakes /*Amount of shakes*/, intDistance /*Shake distance*/, intDuration /*Time duration*/) {
    this.each(function () {
        var jqNode = $(this);
        jqNode.css({position: 'relative'});
        for (var x = 1; x <= intShakes; x++) {
            jqNode.animate({left: (intDistance * -1)}, (((intDuration / intShakes) / 4)))
                .animate({left: intDistance}, ((intDuration / intShakes) / 2))
                .animate({left: 0}, (((intDuration / intShakes) / 4)));
        }
    });
    return this;
}

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

Math.format = function (f, digit) {
    var m = Math.pow(10, digit);
    return parseInt(f * m, 10) / m;
}

var _shake = function (that) {
    $(that).shake(2, 10, 400).focus();
}

var _alert = function (type, msg, title) {
    var class_name;
    switch (type) {
        case 'info':
            title = '<i class="ace-icon fa fa-bolt"></i> ' + title;
            class_name = 'gritter-right gritter-dark gritter-info';
            break;
        case 'success':
            title = '<i class="ace-icon fa fa-check-square-o"></i> 操作成功';
            class_name = 'gritter-success gritter-right gritter-dark';
            break;
        case 'error':
            title = '<i class="ace-icon fa fa-ban"></i> 操作失败';
            class_name = 'gritter-error gritter-right gritter-dark';
            break;
    }
    $.gritter.add({
        title: title,
        text: msg,
        class_name: class_name,
        time: 3000
    });
}