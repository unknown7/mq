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
                <input class="form-control" placeholder="姓名或用户名" name="searchName">
            </div>
            <div class="input-group">
                <select class="form-control" name="gender">
                    <option value="">性别</option>
                    <option value="male">男</option>
                    <option value="female">女</option>
                </select>
            </div>
            <div class="input-group">
                <input type="text"
                   class="datepicker form-control"
                   data-date-format="yyyy-mm-dd" name="birthBegin"
                   placeholder="生日从"/>
                <span class="input-group-addon">
                    <i class="fa fa-exchange"></i>
                </span>
                <input type="text"
                 class="datepicker form-control"
                 data-date-format="yyyy-mm-dd" name="birthEnd"
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
            <button type="button" id="add" class="btn btn-sm btn-info" data-target="#detail-form" data-toggle="modal">
                <i class="ace-icon fa fa-plus"></i>
                新建
            </button>
        </form>
    </div>

    <div class="row">
        <div class="panel-body">
            <table class="table table-striped table-bordered responsive table-hover" id="table" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th width="7%" class="min-mobile-l">姓名</th>
                    <th width="3%" class="min-mobile-l">性别</th>
                    <th width="7%" class="min-mobile-l">生日</th>
                    <th width="3%" class="min-mobile-l">年龄</th>
                    <th width="3%" class="min-mobile-l">头像</th>
                    <th width="8%" class="min-mobile-l">用户名</th>
                    <th width="5%" class="min-mobile-l">电话</th>
                    <th width="10%" class="min-mobile-l">邮箱</th>
                    <th width="10%" class="min-mobile-l">微信</th>
                    <th width="10%" class="min-mobile-l">操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>

    <div id="detail-form" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="blue bigger">员工信息</h4>
                </div>

                <form class="form-horizontal" id="employeeForm" name="employeeForm" enctype="multipart/form-data">
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
                                    <label for="username"><font color="red">*</font>用户名</label>
                                    <div>
                                        <input type="text" id="username" name="username"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="password"><font color="red">*</font>密码</label>

                                    <div>
                                        <input type="password" id="password" name="password"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="eName"><font color="red">*</font>姓名</label>
                                    <div>
                                        <input type="text" id="eName" name="eName"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="birth"><font color="red">*</font>生日</label>
                                    <div>
                                        <input type="text" id="birth" name="birth" class="datepicker form-control" style="width: 86%"/>
                                    </div>
                                </div>
                            </div>

                            <div class="col-xs-12 col-sm-4">
                                <div class="form-group">
                                    <label for="gender"><font color="red">*</font>性别</label>

                                    <div>
                                        <select class="form-control" name="gender" id="gender" style="width: 86%">
                                            <option value="">-</option>
                                            <option value="male">男</option>
                                            <option value="female">女</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="mobile">电话</label>
                                    <div>
                                        <input type="text" id="mobile" name="mobile"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="email">邮箱</label>
                                    <div>
                                        <input type="text" id="email" name="email"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="wechat">微信</label>
                                    <div>
                                        <input type="text" id="wechat" name="wechat"/>
                                    </div>
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

    <div id="tree-view-form" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="blue bigger">赋权</h4>
                </div>

                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-12 col-sm-11">
                            <input name="id" type="hidden"/>
                            <ul id="tree1"></ul>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button class="btn btn-sm" data-dismiss="modal">
                        <i class="ace-icon fa fa-times"></i>
                        取消
                    </button>

                    <button class="btn btn-sm btn-primary" id="saveRight">
                        <i class="ace-icon fa fa-check"></i>
                        保存
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
    var cur = "${curEmp.id}"
</script>
<script src="${ctx }/static/js/employee.js"></script>
</html>