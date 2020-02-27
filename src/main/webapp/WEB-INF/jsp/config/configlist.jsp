<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../utils/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="../js/jquery-2.2.4.min.js"></script>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../css/jquery.dataTables.min.css">
    <link href="../css/jquery-confirm.css" rel="stylesheet" media="screen">
    <link href="../css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/moment-with-locales.js"></script>
    <script src="../js/bootstrap-datetimepicker.min.js"></script>
    <script src="../js/jquery.dataTables.js"></script>
    <script src="../js/layer.js"></script>
    <script src="../js/jquery-confirm.js" type="text/javascript"></script>
    <style>

        .operation {
            margin: 10px;
        }

        .operation > button {
            margin: 10px;
        }

        #books_length {
            float: left;
            margin-left: 20px;
        }

        #books_filter {
            float: right;
            margin-right: 20px;
        }

        #books {
            margin: 5px;
        }

        .center-block {
            display: block;
            width: 21%;
            margin: auto;
        }

        .input-group[class*=col-] {
        }

        ul, li {
            list-style: none;
        }

        div.img {
            margin: 3px;
            border: 1px solid #bebebe;
            height: auto;
            width: auto;
            float: left;
            text-align: center;
        }

        div.img img {
            display: inline;
            margin: 3px;
            border: 1px solid #bebebe;
        }

        div.img:hover img {
            border: 1px solid #333333;
        }

        div.desc {
            text-align: center;
            font-weight: normal;
            width: 150px;
            font-size: 12px;
            margin: 10px 5px 10px 5px;
        }

        .photos {
            margin: 10px;
        }
        #books th,td{
        	text-align: center;
        }
    </style>
</head>

<body>
<section class="content">
    <div class="btn-group operation">
        <button id="btn_add" type="button" class="btn bg-primary">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
        </button>
        <button id="btn_edit" type="button" class="btn bg-info">
            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
        </button>
        <button id="btn_delete" type="button" class="btn btn-success">
            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
        </button>
<!--         <button id="btn_clear" type="button" class="btn btn-success">
            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>数据清空
        </button> -->
    </div>

    <div class="modal fade" id="addBook" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">新增配置信息</h4>
                </div>
                <div id="addBookModal" class="modal-body">
                    <div class="form-horizontal">
                        <div class="form-group">
                            <label for="remork" class="col-sm-2 control-label">项说明:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="remork" type="text" style="width: 100%; float: left;">
                                <!-- <button id="choice" type="button" class="btn bg-primary">
                                      选择
                                </button> -->
                            </div>

                        </div>
                        <div class="form-group">
                            <label for="Name" class="col-sm-2 control-label">配置项:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="Name" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="path" class="col-sm-2 control-label">路径:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="path" type="text" style="width: 100%; float: left;">
                            </div>

                        </div>
                        <div class="form-group">
                            <label for="beizhu" class="col-sm-2 control-label">备注:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="beizhu" type="text" style="width: 100%; float: left;">
                            </div>

                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <div class="center-block">
                        <button id="cancelAdd" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button id="addInfo" type="button" class="btn btn-success" data-dismiss="modal">保存</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="editBookInfo" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">修改配置信息</h4>
                </div>
                <div id="editBookModal" class="modal-body">
                    <div class="form-horizontal">
                        <div class="form-group">
                            <label for="editremork" class="col-sm-2 control-label">项说明:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="editremork" type="text"
                                       style="width: 100%; float: left;">
                                <!-- <button id="choice" type="button" class="btn bg-primary">
                                      选择
                                </button> -->
                            </div>

                        </div>
                        <div class="form-group">
                            <label for="editName" class="col-sm-2 control-label">配置项:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="editName" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editpath" class="col-sm-2 control-label">路径:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="editpath" type="text" style="width: 100%; float: left;">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editbeizhu" class="col-sm-2 control-label">备注:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="editbeizhu" type="text"
                                       style="width: 100%; float: left;">
                            </div>
                        </div>
                        <input type="hidden" id="uuid"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="center-block">
                        <button id="cancelEdit" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button id="saveEdit" type="button" class="btn btn-success" data-dismiss="modal">保存</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="deleteBook" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">确认要删除吗？</h4>
                    <input type="hidden" id="deluuid"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="delete" type="button" class="btn btn-danger" data-dismiss="modal">删除</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="cleardata" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">确认要清空数据吗？</h4>
                    <input type="hidden" id="deluuid"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="clear" type="button" class="btn btn-danger" data-dismiss="modal">删除</button>
                </div>
            </div>
        </div>
    </div>

    <table id="books" class="table table-striped table-bordered row-border hover order-column" cellspacing="0"
           width="100%">
        <thead>
        <tr>
            <th>序号</th>
            <th>项说明</th>
            <th>配置项</th>
            <th>路径</th>
            <th>备注</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</section>
</body>

<script type="text/javascript">

    $(function () {

        var table = $('#books').DataTable({
            //data: data,
            "ajax": "${pageContext.request.contextPath }/config/configlist.action",
            columns: [
                {data: 'uuid'},
                {data: 'remork'},
                {data: 'name'},
                {data: 'path'},
                {data: 'beizhu'}

            ],

            "pagingType": "full_numbers",
            "bSort": true,
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            "columnDefs": [{
                "searchable": false,
                "orderable": true,
                "targets": 0
            }],
            "order": [[1, 'asc']]
        });

        //点击新增按钮
        $("#btn_add").click(function () {
            console.log('add');
            $("#addBook").modal({
                backdrop: "static",//点击空白处不关闭对话框
                show: true
            });
        });
        //取消新增
        $("#cancelAdd").on('click', function () {
            console.log('cancelAdd');
            $("#addBookModal").find('input').val('');
        });
        //点击确定
        $("#addInfo").click(function () {
            var Name = $("#Name").val();
            var path = $("#path").val();
            var remork = $("#remork").val();
            var beizhu = $("#beizhu").val();

            if (Name == '') {
                $.alert('配置项不能为空！');
                return false;
            } else if (path == '') {
                $.alert('路径不能为空！');
                return false;
            } else if (remork == '') {
                $.alert('项说明不能为空！');
                return false;
            }
            $.ajax({
                url: "${pageContext.request.contextPath }/config/insert.action",
                type: "post",
                data: {
                    name: Name,
                    path: path,
                    remork: remork,
                    beizhu: beizhu
                },
                success: function (res) {
                    $.alert('添加成功！');
                    $('#books').DataTable().ajax.reload();
                }
            });
        });
        //点击修改按钮
        $('#btn_edit').click(function () {
            console.log('edit');
            if (table.rows('.selected').data().length) {
                $("#editBookInfo").modal();
                var rowData = table.rows('.selected').data()[0];
                var inputs = $("#editBookModal").find('input');
                $(inputs[0]).val(rowData['remork']);
                $(inputs[1]).val(rowData['name']);
                $(inputs[2]).val(rowData['path']);
                $(inputs[3]).val(rowData['beizhu']);
                $(inputs[4]).val(rowData['uuid']);
            } else {
                $.alert('请选择项目');
            }
        });

        $('#choice').click(function () {
            console.log('choice');
            $.ajax({
                url: "${pageContext.request.contextPath }/config/selectFile3.action",
                type: "get",
                success: function (result) {
                    $("#path").val(result.path);
                }
            });
        });
        $('#choice1').click(function () {
            console.log('choice');
            $.ajax({
                url: "${pageContext.request.contextPath }/config/selectFile3.action",
                type: "get",
                success: function (result) {
                    $("#editpath").val(result.path);
                }
            });
        });
        //行选中
        $('#books tbody').on('click', 'tr', function () {
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
            } else {
                table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }
        });

        //取消修改
        $("#cancelEdit").click(function () {
            console.log('cancelAdd');
            $("#editBookModal").find('input').val('');
        });

        $("#saveEdit").click(function () {
            var editName = $("#editName").val();
            var editpath = $("#editpath").val();
            var editremork = $("#editremork").val();
            var editbeizhu = $("#editbeizhu").val();
            var uuid = $("#uuid").val();
            $.ajax({
                url: "${pageContext.request.contextPath }/config/update.action",
                type: "post",
                data: {
                    uuid: uuid,
                    name: editName,
                    path: editpath,
                    remork: editremork,
                    beizhu: editbeizhu
                },
                success: function (res) {
                    $.alert('修改成功！');
                    $('#books').DataTable().ajax.reload();
                }
            });
        });
        //序号
        table.on('order.dt search.dt', function () {
            table.column(0, {
                search: 'applied',
                order: 'applied'
            }).nodes().each(function (cell, i) {
                cell.innerHTML = i + 1;
            });
        }).draw();

        //点击删除按钮
        $('#btn_delete').click(function () {
            if (table.rows('.selected').data().length) {
                $("#deleteBook").modal();
                var rowData = table.rows('.selected').data()[0];
                //alert(rowData['uuid']);
                $("#deluuid").val(rowData['uuid']);
            } else {
                $.alert('请选择项目');
            }
        });
        //确认删除
        $('#delete').click(function () {
            var uuid = $("#deluuid").val();
            $.ajax({
                url: "${pageContext.request.contextPath }/config/delete.action",
                type: "post",
                data: {
                    uuid: uuid
                },
                success: function (res) {
                    $.alert('删除成功！');
                    $('#books').DataTable().ajax.reload();
                }
            });
        });
        //点击删除按钮
        $('#btn_clear').click(function () {
            $("#cleardata").modal();
        });
        //确认删除
        $('#clear').click(function () {
            $.ajax({
                url: "${pageContext.request.contextPath }/diku/cleares.action",
                type: "post",
                success: function (res) {
                    $.alert('数据清除成功！');
                }
            });
        });
        layer.ready(function () {
            //使用相册
            layer.photos({
                photos: '#photos'
                , shift: 5
            });
        });
        document.onkeydown = grabEvent;
        function grabEvent(e) {
            switch (e.which) {
                case 27:
                    $(".close").click();
                    return 0;
                    break;
            }
        }
    });
</script>

</html>