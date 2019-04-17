<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <form class="form-inline col-sm-11" id="banner_search_form">
        <div class="input-group">
            <input class="form-control" placeholder="轮播图名称" name="bName">
        </div>
        <button type="button" id="banner_query" class="btn btn-sm btn-success">
            <i class="ace-icon fa fa-search"></i>
            查询
        </button>
        <button type="button" id="banner_clear" class="btn btn-sm btn-light">
            <i class="ace-icon fa fa-undo"></i>
            清空
        </button>
        <button type="button" id="banner_add" class="btn btn-sm btn-info">
            <i class="ace-icon fa fa-plus"></i>
            新建
        </button>
    </form>
</div>

<div class="row">
    <div class="panel-body">
        <table class="table table-striped table-bordered responsive table-hover"
               id="banner_table" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th width="10%" class="min-mobile-l">名称</th>
                <th width="3%" class="min-mobile-l">排序</th>
                <th width="6%" class="min-mobile-l">轮播图片</th>
                <th width="20%" class="min-mobile-l">跳转地址</th>
                <th width="10%" class="min-mobile-l">最后修改时间</th>
                <th width="20%" class="min-mobile-l">描述</th>
                <th width="6%" class="min-mobile-l">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div id="banner-detail-form" class="modal fade" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="blue bigger">轮播图信息</h4>
            </div>

            <form class="form-horizontal" id="bannerForm" name="bannerForm">
                <div class="modal-body">
                    <div class="row">
                        <input name="id" type="hidden"/>

                        <div class="form-group">
                            <label class="col-sm-2 control-label no-padding-right" for="bName"> 轮播图名称 </label>

                            <div class="col-sm-9">
                                <input type="text" id="bName" name="bName" placeholder="请填写轮播图名称" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label no-padding-right" for="sort"> 排序 </label>

                            <div class="col-sm-9">
                                <input type="text" id="sort" name="sort" class="form-control" onfocus="$(this).select();"/>
                            </div>
                        </div>

                        <div id="fileDiv" class="form-group">
                            <label class="col-sm-2 control-label no-padding-right" for="image"> 轮播图 </label>

                            <div class="col-sm-9">
                                <input type="file" id="image" name="image"/>
                                <input type="hidden" id="success" name="success"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label no-padding-right" for="jumpTo"> 跳转地址 </label>

                            <div class="col-sm-9">
                                <input type="text" id="jumpTo" name="jumpTo" placeholder="请填写跳转地址" class="form-control"/>
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

                <button class="btn btn-sm btn-primary" id="saveBanner">
                    <i class="ace-icon fa fa-check"></i>
                    保存
                </button>
            </div>
        </div>
    </div>
</div>
