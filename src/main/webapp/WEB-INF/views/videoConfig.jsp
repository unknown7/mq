<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="common.jsp"/>
</head>
<body class="no-skin" style="background:transparent;">
<div class="main-container">
    <div class="main-content">
        <div class="row col-sm-12">
            <ul class="nav nav-tabs" id="myTab">
                <li class="active">
                    <a data-toggle="tab" href="#classification">
                        <i class="blue ace-icon fa fa-cog bigger-120"></i>
                        视频分类设置
                    </a>
                </li>

                <li>
                    <a data-toggle="tab" href="#messages">
                        <i class="blue ace-icon fa fa-refresh bigger-120"></i>
                        首页轮播设置
                    </a>
                </li>

                <li>
                    <a data-toggle="tab" href="#dropdown">
                        <i class="blue ace-icon fa fa-share bigger-120"></i>
                        视频分享设置
                    </a>
                </li>
            </ul>

            <div class="tab-content">
                <div id="classification" class="tab-pane fade in active">
                    <div class="row">
                        <form class="form-inline col-sm-11" id="classification_search_form">
                            <div class="input-group">
                                <input class="form-control" placeholder="分类名称" name="vName">
                            </div>
                            <button type="button" id="classification_query" class="btn btn-sm btn-success">
                                <i class="ace-icon fa fa-search"></i>
                                查询
                            </button>
                            <button type="button" id="classification_clear" class="btn btn-sm btn-light">
                                <i class="ace-icon fa fa-undo"></i>
                                清空
                            </button>
                            <button type="button" id="classification_add" class="btn btn-sm btn-info">
                                <i class="ace-icon fa fa-plus"></i>
                                新建
                            </button>
                        </form>
                    </div>

                    <div class="row">
                        <div class="panel-body">
                            <table class="table table-striped table-bordered responsive table-hover"
                                   id="classification_table" cellspacing="0" width="100%">
                                <thead>
                                <tr>
                                    <th width="10%" class="min-mobile-l">名称</th>
                                    <th width="3%" class="min-mobile-l">排序</th>
                                    <th width="7%" class="min-mobile-l">分享提成</th>
                                    <th width="7%" class="min-mobile-l">最后修改时间</th>
                                    <th width="20%" class="min-mobile-l">描述</th>
                                    <th width="8%" class="min-mobile-l">操作</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>

                    <div id="classification-detail-form" class="modal fade" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="blue bigger">分类信息</h4>
                                </div>

                                <form class="form-horizontal" id="classificationForm" name="classificationForm">
                                    <div class="modal-body">
                                        <div class="row">
                                            <input name="id" type="hidden"/>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label no-padding-right" for="vName"> 分类名 </label>

                                                <div class="col-sm-9">
                                                    <input type="text" id="vName" name="vName" placeholder="请填写分类名" class="form-control"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label no-padding-right" for="sort"> 排序 </label>

                                                <div class="col-sm-9">
                                                    <input type="text" id="sort" name="sort" class="form-control" onfocus="$(this).select();"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label no-padding-right" for="defaultShareCommission"> 分享提成 </label>

                                                <div class="col-sm-9">
                                                    <input type="number" max="100" min="0" value="0"
                                                           id="defaultShareCommissionValue"
                                                           name="defaultShareCommission"
                                                           style="width: 10%;float: left;"
                                                           onfocus="$(this).select();"
                                                           onkeyup="$('#defaultShareCommission').slider({value: $(this).val()});">
                                                    <span style="width: 3%;float: left;"><font size="5">%</font></span>
                                                    <span id="defaultShareCommission" class="ui-slider-simple ui-slider-orange"></span>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label no-padding-right" for="desc"> 描述 </label>

                                                <div class="col-sm-9">
                                                    <textarea id="desc" name="desc" placeholder="请填写描述" class="form-control" style="height: 100px;"></textarea>
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

                                    <button class="btn btn-sm btn-primary" id="saveClassification">
                                        <i class="ace-icon fa fa-check"></i>
                                        保存
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="messages" class="tab-pane fade">
                    <p>Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid.</p>
                </div>

                <div id="dropdown" class="tab-pane fade">
                    <p>Etsy mixtape wayfarers, ethical wes anderson tofu before they sold out mcsweeney's organic lomo
                        retro fanny pack lo-fi farm-to-table readymade.</p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var ctx = "${ctx}";
    var cur = "${curEmp.id}"
</script>
<script src="${ctx }/static/js/videoConfig.js"></script>
</html>