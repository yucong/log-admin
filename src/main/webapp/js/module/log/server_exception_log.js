define(function (require) {
    require('bootstrapValidator');
    require('messager');
    require('table');
    require('doctor-data');
    require('typeahead');
    require('fileinput');
    require('baidueditor');
    window['ZeroClipboard'] = require('zeroclipboard');
    require('multiselect');
    require('json-viewer');
    require('daterangepicker');

    $.module("Log.serverException", function () {
        var search_aggregate_status = -1;
        var current_show_data = [];
        //加载到下拉框中
        if(typeof(Storage) != "undefined"){
        	if(localStorage.deviceType){
        		$("#search_deviceType").val(localStorage.deviceType);
            }
        	if(localStorage.errLevel){
            	$("#search_errLevel").val(localStorage.errLevel);
            } 
        	if(localStorage.status){
        		$("#search_status").val(localStorage.status);
            }
        }
        return {
            init: function () {
                this.loadData();
                this.loadEvent();
            },
            /*初始化列表数据*/
            loadData: function (pageNumber, pageSize) {
                var that = this;
                var requestUrl = $("#s_request").val();
                var clientIP = $("#s_ip").val();
                var code = $("#search_state").val();
                var method = $("#search_method").val();
                var userId = +$("#search_userId").val();
                var deviceType = $("#search_deviceType").val();
                var errLevel = $("#search_errLevel").val();
                var platform = $("#search_platform").val();
                var env = $("#search_env").val();
                var search_time_begin = $("#search_time_begin").val();
                var search_time_end = $("#search_time_end").val();
                var status = $("#search_status").val();

                //保存到webStorage中
                if(typeof(Storage) != "undefined"){
                	
                	localStorage.errLevel = errLevel;
                	localStorage.deviceType = deviceType;
                	localStorage.status = status;
                
                } else {
                   console.log("对不起，您的浏览器不支持 web 存储。");
                }

                SYS.Core.ajaxGet({
                    url: "log/listServerExceptionLog",
                    data: {
                        page: pageNumber ? pageNumber : 1,
                        size: pageSize ? pageSize : 10,
                        clientIP: clientIP,
                        requestUrl: requestUrl,
                        code: code,
                        method: method,
                        platform: platform,
                        userId: userId ? userId : null,
                        deviceType: deviceType,
                        errLevel: errLevel,
                        env: env,
                        beginTime : search_time_begin,
                        endTime : search_time_end,
                        status: status
                    },
                    success: function (data) {
                        var obj = {
                            'pageNumber': data.data.page.current_page,
                            'pageSize': data.data.page.page_size,
                            'totalRows': data.data.page.all_count,
                            'data': data.data.list,
                        };
                        current_show_data = obj.data;
                        $("#myTable").bootstrapTable('destroy');    //销毁table
                        $('#myTable').bootstrapTable({               //重新生成table
                            striped: true,      //使表格带有条纹
                            singleSelect: true,
                            showColumns: true,
                            showToggle: true,
                            pagination: true,
                            pageNumber: obj.pageNumber,
                            pageSize: obj.pageSize,
                            totalRows: obj.totalRows,
                            pageList: [8, 15, 20, 50, 100, 200],
                            sidePagination: 'server',
                            clickToSelect: true,
                            idField: '_id',
                            data: obj.data,
                            columns: [
                                { field: 'autoId', title: 'ID', align: 'center' },
                                
                                { field: 'requestUrl', title: '请求URL', align: 'left',
                                    formatter(requestUrl, row, index) {
                                        return "<span style='color:blue;font-weight:bold;'>" + row.method + "</span>" + " " + requestUrl ; 
                                    }
                                },
                                { field: 'env', title: '环境', align: 'center'  },
                                { field: 'clientIP', title: '请求IP', align: 'center'  },
                                { field: 'userId', title: '用户ID', align: 'center' },
                                { field: 'deviceType', title: '设备类型', align: 'center'  },
                                { field: 'errLevel', title: '级别', align: 'center',
                                    formatter(errLevel, row, index) {
                                		if(errLevel == 'info') {
                                			return "<span class='label label-success'>info</span>" ;
                                		} else if(errLevel == 'warn') {
                                			return "<span class='label label-primary'>warn</span>" ;
                                		} else if(errLevel == 'error') {
                                			return "<span class='label label-error'>error</span>" ;
                                		}
                                		return row.errLevel ;
                                	
                                         
                                    }
                                },
                                {
                                    field: 'errMsg', title: '错误信息', align: 'left',
                                    formatter : function(value, row, index, field){
                                        return "<span id='errMsg_h' style='color:red;display: block;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;' title='" + value + "'>" + value + "</span>";
                                    },
                                    cellStyle : function(value, row, index, field){
                                        return {
                                            css: {
                                                "white-space": "nowrap",
                                                "text-overflow": "ellipsis",
                                                "overflow": "hidden",
                                                "max-width":"150px"
                                            }
                                        };
                                    }
                                },

                                {
                                    field: 'createTime', title: '发生时间', align: 'center',
                                    formatter: function (createTime) {
                                        return SYS.Tool.formatterByDaterule(createTime, "yyyy-mm-dd hh:mm:ss");
                                    }
                                },
                                
                                // status :1打开，2关闭
                                { field: 'status', title: '状态', align: 'center',
                                    formatter(status, row, index) {
                                		if(status == '2') {
                                			return "<span class='label label-success'>已解决</span>" ;
                                		} else  {
                                			return "<span class='label label-error'>未解决</span>" ;
                                		}
                                		return row.errLevel ;
                                	
                                         
                                    }
                                },
                                
                                {
                                    field: 'do', title: '操作', align: 'center',
                                    formatter: function (value, row, index) {
                                    	var html = "<a href='javascript:void(0)' onclick='Log.serverException.toEdit(" + index + ")' class='text-do-edit'>查看</a>";
                                		if(row.status == '2') {
                                            html += "<a href='javascript:void(0)' onclick='Log.serverException.toUpdate(\"" + row.id + "\",\"" + row.status + "\")' class='text-do-remove'> 开启</a>";
                                        } else {
                                        	html += "<a href='javascript:void(0)' onclick='Log.serverException.toUpdate(\"" + row.id + "\",\"" + row.status + "\")' class='text-do-remove'> 关闭</a>";
                                        }
                                    	return html;
                                    }
                                }
                            ],
                            onPageChange: function (number, size) {
                                that.loadData(number, size);
                            },
                            onRefreshTable: function () {   //表格右侧刷新按钮
                                that.loadData(obj.pageNumber, obj.pageSize);
                            }
                        });
                    }
                });
            },
            loadEvent: function () {
                var that = this;
                $("#search-toolbar").on('click', "#btn_search", function () {
                    that.loadData();
                }).on("keydown ", "input", function (e) {
                    if (e.keyCode == 13) {
                        that.loadData();
                    }
                });

                //初始化时间控件
                SYS.Tool.initDaterangSearch('reportrange', 'search_time_begin', 'search_time_end');

                $("body").on("click", "#errMsg_h", function () {
                    var data = $(this).html();
                    $("#data").val(data);
                    $('#myModal').modal('show');
                })
            },
            toEdit: function (index) {
                var data = current_show_data[index];
                $("#data").val(data.stackTrace);
                $('#myModal').modal('show');
            },
            toUpdate: function (id,status) {
                var that = this;
                var msg = "";
                var url = "";
                if(status == '1') {
                    msg = "确定要关闭吗?";
                    url = "serverExceptionLog/close";
                } else {
                    msg = "确定要打开吗";
                    url = "serverExceptionLog/open";
                }
                $.messager.confirm( msg, function () {
                    SYS.Core.ajaxPost({
                        url: url,
                        data: {
                            id: id
                        },
                        success: function (data) {
                            if (data.code == 1) {
                                $.messager.popup(data.message, "success");
                                that.loadData();
                            } else {
                                $.messager.popup(data.message, 'error');
                            }
                        }
                    })
                });
            },
        }
    });
    Log.serverException.init();
})