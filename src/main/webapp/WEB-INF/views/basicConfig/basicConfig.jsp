<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="image" value="${ctx}/images/"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../common.jsp"/>
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
                    <a data-toggle="tab" href="#banner">
                        <i class="blue ace-icon fa fa-refresh bigger-120"></i>
                        首页轮播设置
                    </a>
                </li>
            </ul>

            <div class="tab-content">
                <div id="classification" class="tab-pane fade in active">
                    <jsp:include page="videoClassification.jsp"/>
                </div>

                <div id="banner" class="tab-pane fade">
                    <jsp:include page="banner.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var ctx = "${ctx}";
    var image = "${image}";
    var cur = "${curEmp.id}";
</script>
<script src="${ctx }/static/js/basicConfig/videoClassification.js"></script>
<script src="${ctx }/static/js/basicConfig/banner.js"></script>
</html>