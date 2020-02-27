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
    <link href="../css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/moment-with-locales.js"></script>
    <script src="../js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/jquery.dataTables.min.css">
    <script src="../js/jquery.dataTables.js"></script>
    <script src="../js/layer.js"></script>
    <script type="text/javascript" src="../js/jquery-confirm.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/jquery-confirm.css"/>
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
            word-break: break-word;
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
    </div>
    <div class="modal fade" id="addBook" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">新增账户信息</h4>
                </div>
                <div id="addBookModal" class="modal-body">
                    <div class="form-horizontal">
                        <div class="form-group">
                            <label for="usercode" class="col-sm-2 control-label">用户名:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="name" name="name" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="Name" class="col-sm-2 control-label">密码:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="password" type="password">
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
                    <h4 class="modal-title">修改账户信息</h4>
                </div>
                <div id="editBookModal" class="modal-body">
                    <div class="form-horizontal">
                        <div class="form-group">
                            <label for="editusercode" class="col-sm-2 control-label">用户名:*</label>
                            <div class="col-sm-10">
                            	<input type='hidden' id="uuid"/>
                                <input class="form-control" id="editname" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editName" class="col-sm-2 control-label">密码:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="editpassword" type="password">
                            </div>
                        </div>
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

    <table id="books" class="table table-striped table-bordered row-border hover order-column" cellspacing="0"
           width="100%">
        <thead>
        <tr>

            <th>序号</th>
            <th>账户名</th>
            <th>密码</th>
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
        "ajax": "${pageContext.request.contextPath }/user/list.action",
        columns: [
            {data: 'id'},
            {data: 'name'},
            {data: 'password'}
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
        var name = $("#name").val();
        var password = $("#password").val();
        if (name == '') {
            $.alert('账户名不能为空！');
            return false;
        } else if (password == '') {
            $.alert('密码不能为空！');
            return false;
        }
        $.ajax({
            url: "${pageContext.request.contextPath }/user/insert.action",
            type: "post",
            data: {
                name: name,
                password: password
            },
            success: function (res) {
                $.alert('添加成功！');
                $("#addBookModal").find('input').val('');
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
            $(inputs[0]).val(rowData['id']);
            $(inputs[1]).val(rowData['name']);
            $(inputs[2]).val(rowData['password']);
        } else {
            $.alert('请选择一条信息');
        }
    });
    //行选中
    $('#books tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');//可多选
    });

    //取消修改
    $("#cancelEdit").click(function () {
        console.log('cancelAdd');
        $("#editBookModal").find('input').val('');
    });

    $("#saveEdit").click(function () {
        var editname = $("#editname").val();
        var editpassword = $("#editpassword").val();
        var uuid = $("#uuid").val();
        if (editname == '') {
            $.alert('账户名不能为空！');
            return false;
        } else if (editpassword == '') {
            $.alert('密码不能为空！');
            return false;
        }
        $.ajax({
            url: "${pageContext.request.contextPath }/user/update.action",
            type: "post",
            data: {
                uuid: uuid,
                name: editname,
                password:editpassword
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
        }
    });

    //确认删除
    $('#delete').click(function () {
        for (var i = 0; i < table.rows('.selected').data().length; i++) {
            var rowData = table.rows('.selected').data()[i];
            var uuid = rowData['id'];
            $.ajax({
                url: "${pageContext.request.contextPath }/user/delete.action",
                type: "post",
                data: {
                    uuid: uuid
                },
                success: function (res) {
                    $('#books').DataTable().ajax.reload();
                }
            });
        }

        $.alert('删除成功！');
    });

    layer.ready(function () {
        //使用相册
        layer.photos({
            photos: '#photos',
            shift: 5
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