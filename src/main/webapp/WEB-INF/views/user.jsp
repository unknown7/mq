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
                <input class="form-control" placeholder="昵称" name="nickName">
            </div>
            <div class="input-group">
                <select class="form-control" name="gender">
                    <option value="">性别</option>
                    <option value="1">男</option>
                    <option value="2">女</option>
                </select>
            </div>
            <div class="input-group">
                <input type="text"
                       class="datepicker form-control"
                       data-date-format="yyyy-mm-dd" name="createTimeBegin"
                       placeholder="注册时间从"/>
                <span class="input-group-addon">
                    <i class="fa fa-exchange"></i>
                </span>
                <input type="text"
                       class="datepicker form-control"
                       data-date-format="yyyy-mm-dd" name="createTimeEnd"
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
                    <th width="7%" class="min-mobile-l">昵称</th>
                    <th width="3%" class="min-mobile-l">头像</th>
                    <th width="3%" class="min-mobile-l">性别</th>
                    <th width="6%" class="min-mobile-l">国家</th>
                    <th width="6%" class="min-mobile-l">省份</th>
                    <th width="6%" class="min-mobile-l">城市</th>
                    <th width="7%" class="min-mobile-l">推荐人</th>
                    <th width="8%" class="min-mobile-l">注册时间</th>
                    <th width="3%" class="min-mobile-l">操作</th>
                </tr>
                </thead>
            </table>
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

                            <div id="fileDiv" class="col-xs-12 col-sm-4">
                                <div class="space"></div>

                                <input type="file" id="avatar" name="avatar"/>
                                <input type="hidden" id="success" name="success"/>
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
                                    <label for="createTime">注册时间</label>

                                    <div>
                                        <input type="text" id="createTime" name="createTime"/>
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
<script src="${ctx }/static/js/user.js"></script>
</html>