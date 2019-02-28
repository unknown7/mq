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
                <%--<li class="grey dropdown-modal">--%>
                    <%--<a data-toggle="dropdown" class="dropdown-toggle" href="#">--%>
                        <%--<i class="ace-icon fa fa-tasks"></i>--%>
                        <%--<span class="badge badge-grey">4</span>--%>
                    <%--</a>--%>

                    <%--<ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">--%>
                        <%--<li class="dropdown-header">--%>
                            <%--<i class="ace-icon fa fa-check"></i>--%>
                            <%--4 Tasks to complete--%>
                        <%--</li>--%>

                        <%--<li class="dropdown-content">--%>
                            <%--<ul class="dropdown-menu dropdown-navbar">--%>
                                <%--<li>--%>
                                    <%--<a href="#">--%>
                                        <%--<div class="clearfix">--%>
                                            <%--<span class="pull-left">Software Update</span>--%>
                                            <%--<span class="pull-right">65%</span>--%>
                                        <%--</div>--%>

                                        <%--<div class="progress progress-mini">--%>
                                            <%--<div style="width:65%" class="progress-bar"></div>--%>
                                        <%--</div>--%>
                                    <%--</a>--%>
                                <%--</li>--%>

                                <%--<li>--%>
                                    <%--<a href="#">--%>
                                        <%--<div class="clearfix">--%>
                                            <%--<span class="pull-left">Hardware Upgrade</span>--%>
                                            <%--<span class="pull-right">35%</span>--%>
                                        <%--</div>--%>

                                        <%--<div class="progress progress-mini">--%>
                                            <%--<div style="width:35%" class="progress-bar progress-bar-danger"></div>--%>
                                        <%--</div>--%>
                                    <%--</a>--%>
                                <%--</li>--%>

                                <%--<li>--%>
                                    <%--<a href="#">--%>
                                        <%--<div class="clearfix">--%>
                                            <%--<span class="pull-left">Unit Testing</span>--%>
                                            <%--<span class="pull-right">15%</span>--%>
                                        <%--</div>--%>

                                        <%--<div class="progress progress-mini">--%>
                                            <%--<div style="width:15%" class="progress-bar progress-bar-warning"></div>--%>
                                        <%--</div>--%>
                                    <%--</a>--%>
                                <%--</li>--%>

                                <%--<li>--%>
                                    <%--<a href="#">--%>
                                        <%--<div class="clearfix">--%>
                                            <%--<span class="pull-left">Bug Fixes</span>--%>
                                            <%--<span class="pull-right">90%</span>--%>
                                        <%--</div>--%>

                                        <%--<div class="progress progress-mini progress-striped active">--%>
                                            <%--<div style="width:90%" class="progress-bar progress-bar-success"></div>--%>
                                        <%--</div>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        <%--</li>--%>

                        <%--<li class="dropdown-footer">--%>
                            <%--<a href="#">--%>
                                <%--See tasks with details--%>
                                <%--<i class="ace-icon fa fa-arrow-right"></i>--%>
                            <%--</a>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</li>--%>

                <%--<li class="purple dropdown-modal">--%>
                    <%--<a data-toggle="dropdown" class="dropdown-toggle" href="#">--%>
                        <%--<i class="ace-icon fa fa-bell icon-animated-bell"></i>--%>
                        <%--<span class="badge badge-important">8</span>--%>
                    <%--</a>--%>

                    <%--<ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">--%>
                        <%--<li class="dropdown-header">--%>
                            <%--<i class="ace-icon fa fa-exclamation-triangle"></i>--%>
                            <%--8 Notifications--%>
                        <%--</li>--%>

                        <%--<li class="dropdown-content">--%>
                            <%--<ul class="dropdown-menu dropdown-navbar navbar-pink">--%>
                                <%--<li>--%>
                                    <%--<a href="#">--%>
                                        <%--<div class="clearfix">--%>
													<%--<span class="pull-left">--%>
														<%--<i class="btn btn-xs no-hover btn-pink fa fa-comment"></i>--%>
														<%--New Comments--%>
													<%--</span>--%>
                                            <%--<span class="pull-right badge badge-info">+12</span>--%>
                                        <%--</div>--%>
                                    <%--</a>--%>
                                <%--</li>--%>

                                <%--<li>--%>
                                    <%--<a href="#">--%>
                                        <%--<i class="btn btn-xs btn-primary fa fa-user"></i>--%>
                                        <%--Bob just signed up as an editor ...--%>
                                    <%--</a>--%>
                                <%--</li>--%>

                                <%--<li>--%>
                                    <%--<a href="#">--%>
                                        <%--<div class="clearfix">--%>
													<%--<span class="pull-left">--%>
														<%--<i class="btn btn-xs no-hover btn-success fa fa-shopping-cart"></i>--%>
														<%--New Orders--%>
													<%--</span>--%>
                                            <%--<span class="pull-right badge badge-success">+8</span>--%>
                                        <%--</div>--%>
                                    <%--</a>--%>
                                <%--</li>--%>

                                <%--<li>--%>
                                    <%--<a href="#">--%>
                                        <%--<div class="clearfix">--%>
													<%--<span class="pull-left">--%>
														<%--<i class="btn btn-xs no-hover btn-info fa fa-twitter"></i>--%>
														<%--Followers--%>
													<%--</span>--%>
                                            <%--<span class="pull-right badge badge-info">+11</span>--%>
                                        <%--</div>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        <%--</li>--%>

                        <%--<li class="dropdown-footer">--%>
                            <%--<a href="#">--%>
                                <%--See all notifications--%>
                                <%--<i class="ace-icon fa fa-arrow-right"></i>--%>
                            <%--</a>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</li>--%>

                <%--<li class="green dropdown-modal">--%>
                    <%--<a data-toggle="dropdown" class="dropdown-toggle" href="#">--%>
                        <%--<i class="ace-icon fa fa-envelope icon-animated-vertical"></i>--%>
                        <%--<span class="badge badge-success">5</span>--%>
                    <%--</a>--%>

                    <%--<ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">--%>
                        <%--<li class="dropdown-header">--%>
                            <%--<i class="ace-icon fa fa-envelope-o"></i>--%>
                            <%--5 Messages--%>
                        <%--</li>--%>

                        <%--<li class="dropdown-content">--%>
                            <%--<ul class="dropdown-menu dropdown-navbar">--%>
                                <%--<li>--%>
                                    <%--<a href="#" class="clearfix">--%>
                                        <%--<img src="assets/images/avatars/avatar.png" class="msg-photo" alt="Alex's Avatar" />--%>
                                        <%--<span class="msg-body">--%>
													<%--<span class="msg-title">--%>
														<%--<span class="blue">Alex:</span>--%>
														<%--Ciao sociis natoque penatibus et auctor ...--%>
													<%--</span>--%>

													<%--<span class="msg-time">--%>
														<%--<i class="ace-icon fa fa-clock-o"></i>--%>
														<%--<span>a moment ago</span>--%>
													<%--</span>--%>
												<%--</span>--%>
                                    <%--</a>--%>
                                <%--</li>--%>

                                <%--<li>--%>
                                    <%--<a href="#" class="clearfix">--%>
                                        <%--<img src="assets/images/avatars/avatar3.png" class="msg-photo" alt="Susan's Avatar" />--%>
                                        <%--<span class="msg-body">--%>
													<%--<span class="msg-title">--%>
														<%--<span class="blue">Susan:</span>--%>
														<%--Vestibulum id ligula porta felis euismod ...--%>
													<%--</span>--%>

													<%--<span class="msg-time">--%>
														<%--<i class="ace-icon fa fa-clock-o"></i>--%>
														<%--<span>20 minutes ago</span>--%>
													<%--</span>--%>
												<%--</span>--%>
                                    <%--</a>--%>
                                <%--</li>--%>

                                <%--<li>--%>
                                    <%--<a href="#" class="clearfix">--%>
                                        <%--<img src="assets/images/avatars/avatar4.png" class="msg-photo" alt="Bob's Avatar" />--%>
                                        <%--<span class="msg-body">--%>
													<%--<span class="msg-title">--%>
														<%--<span class="blue">Bob:</span>--%>
														<%--Nullam quis risus eget urna mollis ornare ...--%>
													<%--</span>--%>

													<%--<span class="msg-time">--%>
														<%--<i class="ace-icon fa fa-clock-o"></i>--%>
														<%--<span>3:15 pm</span>--%>
													<%--</span>--%>
												<%--</span>--%>
                                    <%--</a>--%>
                                <%--</li>--%>

                                <%--<li>--%>
                                    <%--<a href="#" class="clearfix">--%>
                                        <%--<img src="assets/images/avatars/avatar2.png" class="msg-photo" alt="Kate's Avatar" />--%>
                                        <%--<span class="msg-body">--%>
													<%--<span class="msg-title">--%>
														<%--<span class="blue">Kate:</span>--%>
														<%--Ciao sociis natoque eget urna mollis ornare ...--%>
													<%--</span>--%>

													<%--<span class="msg-time">--%>
														<%--<i class="ace-icon fa fa-clock-o"></i>--%>
														<%--<span>1:33 pm</span>--%>
													<%--</span>--%>
												<%--</span>--%>
                                    <%--</a>--%>
                                <%--</li>--%>

                                <%--<li>--%>
                                    <%--<a href="#" class="clearfix">--%>
                                        <%--<img src="assets/images/avatars/avatar5.png" class="msg-photo" alt="Fred's Avatar" />--%>
                                        <%--<span class="msg-body">--%>
													<%--<span class="msg-title">--%>
														<%--<span class="blue">Fred:</span>--%>
														<%--Vestibulum id penatibus et auctor  ...--%>
													<%--</span>--%>

													<%--<span class="msg-time">--%>
														<%--<i class="ace-icon fa fa-clock-o"></i>--%>
														<%--<span>10:09 am</span>--%>
													<%--</span>--%>
												<%--</span>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        <%--</li>--%>

                        <%--<li class="dropdown-footer">--%>
                            <%--<a href="inbox.html">--%>
                                <%--See all messages--%>
                                <%--<i class="ace-icon fa fa-arrow-right"></i>--%>
                            <%--</a>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</li>--%>

                <li class="light-blue dropdown-modal">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="${image}${curEmp.avatarRealName}" />
                        <span class="user-info">
									<small>欢迎您,</small>
									${curEmp.eName }
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
                            <a href="profile.html">
                                <i class="ace-icon fa fa-user"></i>
                                Profile
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

                <%--<div class="nav-search" id="nav-search">--%>
                    <%--<form class="form-search">--%>
								<%--<span class="input-icon">--%>
									<%--<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />--%>
									<%--<i class="ace-icon fa fa-search nav-search-icon"></i>--%>
								<%--</span>--%>
                    <%--</form>--%>
                <%--</div><!-- /.nav-search -->--%>
            </div>

            <div class="page-content">
                <iframe id="iframe" align="top" scrolling="no" frameborder="0" width="100%" onload="$(this).height(document.documentElement.clientHeight - 120);">
                </iframe>
            </div>
        </div>
    </div><!-- /.main-content -->

    <%--<div class="footer">--%>
        <%--<div class="footer-inner">--%>
            <%--<div class="footer-content">--%>
						<%--<span class="bigger-120">--%>
							<%--<span class="blue bolder">Ace</span>--%>
							<%--Application &copy; 2013-2014--%>
						<%--</span>--%>

                <%--&nbsp; &nbsp;--%>
                <%--<span class="action-buttons">--%>
							<%--<a href="#">--%>
								<%--<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>--%>
							<%--</a>--%>

							<%--<a href="#">--%>
								<%--<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>--%>
							<%--</a>--%>

							<%--<a href="#">--%>
								<%--<i class="ace-icon fa fa-rss-square orange bigger-150"></i>--%>
							<%--</a>--%>
						<%--</span>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->
<script type="text/javascript">
    var ctx = "${ctx}";
</script>
<script src="${ctx }/static/js/index.js"></script>
</body>
</html>