<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="image" value="${ctx}/images/"/>
<c:set var="video" value="${ctx}/videos/"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="common.jsp"/>
</head>
<body class="no-skin" style="background:transparent;width: 99%;">
<div id="page-wrapper">
    <div class="row">
        <form class="form-inline col-sm-11" id="search_form">
            <div class="input-group">
                <input class="form-control" placeholder="视频标题" name="title">
            </div>
            <div class="input-group">
                <select class="form-control" name="classification" style="width: 200px;">
                    <option value="">分类</option>
                </select>
            </div>
            <button type="button" id="query" class="btn btn-sm btn-success">
                <i class="ace-icon fa fa-search"></i>
                查询
            </button>
            <button type="button" id="clear" class="btn btn-sm btn-light">
                <i class="ace-icon fa fa-undo"></i>
                清空
            </button>
            <button type="button" id="add" class="btn btn-sm btn-info" data-target="#detail-form" data-toggle="modal">
                <i class="ace-icon fa fa-plus"></i>
                新建
            </button>
        </form>
    </div>

    <div class="row">
        <div class="panel-body">
            <table class="table table-striped table-bordered responsive table-hover" id="table" cellspacing="0"
                   width="100%">
                <thead>
                <tr>
                    <th width="9%" class="min-mobile-l">标题</th>
                    <%--<th width="12%" class="min-mobile-l">介绍</th>--%>
                    <th width="3%" class="min-mobile-l">价格</th>
                    <th width="3%" class="min-mobile-l">封面</th>
                    <%--<th width="3%" class="min-mobile-l">详情</th>--%>
                    <th width="6%" class="min-mobile-l">分类</th>
                    <th width="4%" class="min-mobile-l">分享提成</th>
                    <th width="6%" class="min-mobile-l">免费观看时长</th>
                    <th width="7%" class="min-mobile-l">访问量</th>
                    <th width="7%" class="min-mobile-l">观看量</th>
                    <th width="7%" class="min-mobile-l">已购人数</th>
                    <th width="7%" class="min-mobile-l">操作</th>
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
</div>
</body>
<script type="text/javascript">
    var ctx = "${ctx}";
    var image = "${image}";
    var video = "${video}";
    var cur = "${curEmp.id}";
</script>
<script src="${ctx }/static/js/video.js"></script>
</html>