<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <meta name="description" content="User login page"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="shortcut icon" href="#"/>
    <jsp:include page="common.jsp"/>
</head>

<body class="no-skin" style="background:transparent;width: 99%;">
<div id="page-wrapper">
    <div class="row">
        <form class="form-inline col-sm-11" id="search_form">
            <div class="input-group">
                <input class="form-control" placeholder="订单号" name="orderNo">
            </div>
            <div class="input-group">
                <input type="text"
                       class="datepicker form-control"
                       data-date-format="yyyy-mm-dd" name="createdTimeBegin"
                       placeholder="下单时间从"/>
                <span class="input-group-addon">
                    <i class="fa fa-exchange"></i>
                </span>
                <input type="text"
                       class="datepicker form-control"
                       data-date-format="yyyy-mm-dd" name="createdTimeEnd"
                       placeholder="至"/>
            </div>
            <button type="button" id="query" class="btn btn-sm btn-success">
                <i class="ace-icon fa fa-search"></i>
                查询
            </button>
            <button type="button" id="clear" class="btn btn-sm btn-light">
                <i class="ace-icon fa fa-undo"></i>
                清空
            </button>
        </form>
    </div>

    <div class="row">
        <div class="panel-body">
            <table class="table table-striped table-bordered responsive table-hover" id="table" cellspacing="0"
                   width="100%">
                <thead>
                <tr>
                    <th width="7%" class="min-mobile-l">单号</th>
                    <th width="3%" class="min-mobile-l">状态</th>
                    <th width="9%" class="min-mobile-l">商品名称</th>
                    <th width="4%" class="min-mobile-l">商品类型</th>
                    <th width="4%" class="min-mobile-l">商品价格</th>
                    <th width="4%" class="min-mobile-l">购买用户</th>
                    <th width="4%" class="min-mobile-l">订单价格</th>
                    <th width="4%" class="min-mobile-l">使用积分</th>
                    <th width="4%" class="min-mobile-l">实付价格</th>
                    <th width="7%" class="min-mobile-l">订单时间</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>

    <div id="video-detail-form" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="blue bigger">视频信息</h4>
                </div>

                <form class="form-horizontal" id="videoForm" name="videoForm">
                    <div class="modal-body">
                        <div class="row">
                            <input name="id" type="hidden"/>

                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right" for="title"> 标题 </label>

                                <div class="col-sm-9">
                                    <input type="text" id="title" name="title" placeholder="请填写标题" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right" for="subtitle"> 介绍 </label>

                                <div class="col-sm-9">
                                    <input type="text" id="subtitle" name="subtitle" placeholder="请填写介绍" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right" for="classification"> 分类 </label>

                                <div class="col-sm-9">
                                    <select class="form-control" name="classification" id="classification">
                                        <option value="">-</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right" for="price"> 价格 </label>

                                <div class="col-sm-9">
                                    <input type="text" id="price" name="price" class="form-control" onfocus="$(this).select();"/>
                                    <span style="width: 3%;padding-left: 5px;padding-top: 10px;font-size: 16px;">元</span>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right" for="profitShare"> 分享提成 </label>

                                <div class="col-sm-9">
                                    <input class="value-input" type="number" max="100" min="0" value="0"
                                           id="profitShareValue"
                                           name="profitShare"
                                           style="width: 10%;float: left;"
                                           onfocus="$(this).select();"
                                           onkeyup="$('#profitShare').slider({value: $(this).val()});">
                                    <span style="width: 3%;padding-left: 5px;padding-top: 10px;font-size: 16px;">%</span>
                                    <span id="profitShare" class="ui-slider-simple ui-slider-orange value-slider"></span>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right" for="profitSale"> 销售提成 </label>

                                <div class="col-sm-9">
                                    <input class="value-input" type="number" max="100" min="0" value="0"
                                           id="profitSaleValue"
                                           name="profitSale"
                                           style="width: 10%;float: left;"
                                           onfocus="$(this).select();"
                                           onkeyup="$('#profitSale').slider({value: $(this).val()});">
                                    <span style="width: 3%;padding-left: 5px;padding-top: 10px;font-size: 16px;">%</span>
                                    <span id="profitSale" class="ui-slider-simple ui-slider-orange value-slider"></span>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right" for="freeWatchTime"> 免费观看时长 </label>

                                <div class="col-sm-9">
                                    <input class="value-input" type="number" max="1000" min="0" value="0"
                                           id="freeWatchTimeValue"
                                           name="freeWatchTime"
                                           style="width: 10%;float: left;"
                                           onfocus="$(this).select();"
                                           onkeyup="$('#freeWatchTime').slider({value: $(this).val()});">
                                    <span style="width: 3%;padding-left: 5px;padding-top: 10px;font-size: 16px;">秒</span>
                                    <span id="freeWatchTime" class="ui-slider-simple ui-slider-orange value-slider"></span>
                                </div>
                            </div>

                            <div id="fileDiv" class="form-group">
                                <div id="cover-group" class="col-sm-5 col-sm-offset-1">
                                    <input type="file" id="cover" name="cover"/>
                                    <input type="hidden" id="cover_success" name="cover_success"/>
                                </div>

                                <div id="desc-group" class="col-sm-5">
                                    <input type="file" id="description" name="description"/>
                                    <input type="hidden" id="description_success" name="description_success"/>
                                </div>
                            </div>



                        </div>
                    </div>
                </form>

                <div class="modal-footer">
                    <button class="btn btn-sm" data-dismiss="modal">
                        <i class="ace-icon fa fa-times"></i>
                        取消
                    </button>

                    <button class="btn btn-sm btn-primary" id="save">
                        <i class="ace-icon fa fa-check"></i>
                        保存
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div id="video-upload-form" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="blue bigger">视频</h4>
                </div>

                <form class="form-horizontal" id="videoUploadForm" name="videoUploadForm">
                    <div class="modal-body">
                        <div class="row">

                            <div class="form-group">
                                <div id="videoDiv" class="col-sm-10 col-sm-offset-1">
                                    <input type="hidden" name="id"/>
                                    <input type="file" id="video" name="video"/>
                                    <input type="hidden" id="video_success" name="video_success"/>
                                </div>
                            </div>

                            <div id="progressForm" class="form-group hide">
                                <div class="col-sm-10 col-sm-offset-1">
                                    <div id="progressbar"><div class="progress-label"></div></div>
                                </div>
                            </div>

                        </div>
                    </div>
                </form>

                <div class="modal-footer">
                    <button class="btn btn-sm" data-dismiss="modal">
                        <i class="ace-icon fa fa-times"></i>
                        取消
                    </button>

                    <button class="btn btn-sm btn-primary" id="upload">
                        <i class="ace-icon fa fa-check"></i>
                        上传
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div id="user-detail-form" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="blue bigger">用户信息</h4>
                </div>

                <form class="form-horizontal" id="userForm" name="userForm" enctype="multipart/form-data">
                    <div class="modal-body">
                        <div class="row">
                            <input name="id" type="hidden"/>

                            <div id="avatarFileDiv" class="col-xs-12 col-sm-4">
                                <div class="space"></div>

                                <input type="file" id="avatar" name="avatar"/>
                                <input type="hidden" id="avatar_success" name="avatar_success"/>
                            </div>

                            <div class="col-xs-12 col-sm-4">
                                <div class="form-group">
                                    <label for="nickName">昵称</label>
                                    <div>
                                        <input type="text" id="nickName" name="nickName"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="gender">性别</label>

                                    <div>
                                        <select class="form-control" name="gender" id="gender" style="width: 86%">
                                            <option value="">-</option>
                                            <option value="1">男</option>
                                            <option value="2">女</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="createdTime">注册时间</label>

                                    <div>
                                        <input type="text" id="createdTime" name="createdTime"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="openId">微信唯一标识</label>
                                    <div>
                                        <input type="text" id="openId" name="openId"/>
                                    </div>
                                </div>
                            </div>

                            <div class="col-xs-12 col-sm-4">
                                <div class="form-group">
                                    <label for="country">国家</label>

                                    <div>
                                        <input type="text" id="country" name="country"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="province">省份</label>
                                    <div>
                                        <input type="text" id="province" name="province"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="city">城市</label>
                                    <div>
                                        <input type="text" id="city" name="city"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="language">语言</label>
                                    <div>
                                        <input type="text" id="language" name="language"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var ctx = "${ctx}";
    var image = "${image}";
    var video = "${video}";
    var cur = "${curEmp.id}";
</script>
<script src="${ctx }/static/js/order.js"></script>
</html>