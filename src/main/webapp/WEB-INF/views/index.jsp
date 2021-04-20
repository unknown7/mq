<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="image" value="${ctx}/images/"/>
<c:set var="video" value="${ctx}/videos/"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>木荃孕产管理平台</title>
    <meta name="description" content="首页" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <link rel="shortcut icon" href="#" />
    <jsp:include page="common.jsp"/>
</head>
<body class="no-skin">
<div id="navbar" class="navbar navbar-default ace-save-state">
    <div class="navbar-container ace-save-state" id="navbar-container">
        <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
            <span class="sr-only">Toggle sidebar</span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>
        </button>

        <div class="navbar-header pull-left">
            <a href="${ctx}/index" class="navbar-brand">
                <small>
                    <i class="fa fa-leaf"></i>
                    木荃孕产管理平台
                </small>
            </a>
        </div>

        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="light-blue dropdown-modal">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="${image}${curEmp.avatarRealName}" />
                        <span class="user-info">
									<small>欢迎您,</small>
									${curEmp.employeeName }
								</span>

                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>

                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="#">
                                <i class="ace-icon fa fa-cog"></i>
                                Settings
                            </a>
                        </li>

                        <li>
                            <a href="#" onclick="changePasswordOnClick();">
                                <i class="ace-icon fa fa-user"></i>
                                修改密码
                            </a>
                        </li>

                        <li class="divider"></li>

                        <li>
                            <a href="${ctx }/logout">
                                <i class="ace-icon fa fa-power-off"></i>
                                登出
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div><!-- /.navbar-container -->
</div>

<div class="main-container ace-save-state" id="main-container">
    <script type="text/javascript">
        try{ace.settings.loadState('main-container')}catch(e){}
    </script>

    <div id="sidebar" class="sidebar                  responsive                    ace-save-state">
        <script type="text/javascript">
            try{ace.settings.loadState('sidebar')}catch(e){}
        </script>

        <div class="sidebar-shortcuts" id="sidebar-shortcuts">
            <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                <button class="btn btn-success">
                    <i class="ace-icon fa fa-signal"></i>
                </button>

                <button class="btn btn-info">
                    <i class="ace-icon fa fa-pencil"></i>
                </button>

                <button class="btn btn-warning">
                    <i class="ace-icon fa fa-users"></i>
                </button>

                <button class="btn btn-danger">
                    <i class="ace-icon fa fa-cogs"></i>
                </button>
            </div>

            <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                <span class="btn btn-success"></span>

                <span class="btn btn-info"></span>

                <span class="btn btn-warning"></span>

                <span class="btn btn-danger"></span>
            </div>
        </div><!-- /.sidebar-shortcuts -->

        <ul id="menu" class="nav nav-list">

        </ul>

        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>
    </div>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul id="path" class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="${ctx }/index">Home</a>
                    </li>
                    <li class="active temp">仪表盘</li>
                </ul><!-- /.breadcrumb -->

            </div>

            <div class="page-content">
                <iframe id="iframe" align="top" scrolling="yes" frameborder="0" width="100%" onload="$(this).height(document.documentElement.clientHeight - 120);">
                </iframe>
            </div>
        </div>
    </div><!-- /.main-content -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>

    <div id="password-modification-form" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="blue bigger">修改密码</h4>
                </div>

                <form class="form-horizontal" id="passwordModificationForm" name="passwordModificationForm">
                    <div class="modal-body">

                        <input type="hidden" name="id" value="${curEmp.id }"/>

                        <div class="form-group">
                            <div class="col-xs-12 col-sm-11">
                                <input name="oldPassword" type="text" placeholder="原密码"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-xs-12 col-sm-11">
                                <input name="newPassword" type="text" placeholder="新密码"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-xs-12 col-sm-11">
                                <input name="newPasswordConfirm" type="text" placeholder="确认新密码"/>
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button class="btn btn-sm" data-dismiss="modal">
                            <i class="ace-icon fa fa-times"></i>
                            取消
                        </button>

                        <button class="btn btn-sm btn-primary" id="savePassword">
                            <i class="ace-icon fa fa-check"></i>
                            保存
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div><!-- /.main-container -->
<script type="text/javascript">
    var ctx = "${ctx}";
</script>
<script src="${ctx }/static/js/index.js"></script>
</body>
</html>