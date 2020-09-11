$.module('DOCTOR.Data', function () {
    return {
        common: {
            sex:{
                "-1":"全部",
                "1":"男",
                "2":"女"
            },
            status:{
                "-1":"全部",
                "1":"上线",
                "0":"下线"
            },
            orderType:{
                "-1":"全部",
                "0":"免费",
                "1":"付费"
            },
            plantform:{
                "-1":"全部",
                "11":"Android",
                "12":"IOS"
            },
            commissionRole:{
                "0" : "全部",
                "1" : "公司",
                "2" : "BD",
                "3" : "机构",
                "4" : "机构员工"
            }
        },
        mns: {
            messageQueue:{
                "":"全部",
                "yes":"已处理",
                "no":"未处理"
            },
            noticeStatus:{
                "-1":"全部",
                "1":"已通知",
                "2":"未通知"
            }
        },
        inquiry: {
            relation:{
                "-1":"全部",
                "93":"自己",
                "94":"配偶",
                "95":"父母",
                "96":"亲戚",
                "97":"亲戚",
                "98":"朋友"

            },
            req_pay_status:{
                "1":"付费",
                "0":"免费"
            },
            cost_type:{
                "-1":"全部",
                "1":"付费",
                "2":"免费"
            },
            //付费问诊进度
            schedule_cost_type_1:{
                "0":"挂号单生成 (用户点击付费问诊按钮即生成挂号单)",
                "1":"填写完成 (挂号单填写完成点击提交)",
                "2":"分配医生开始抢单",
                "3":"分配医生时取消问诊",
                "4":"有医生抢单",
                "5":"无医生抢单",
                "6":"选择医生时取消问诊",
                "7":"选择医生后付款",
                "8":"付款后开启咨询",
                "9":"医生开具问诊小结",
                "10":"用户购药"
            },
            //免费问诊进度
            schedule_cost_type_2:{
                "0":"挂号单生成 (用户点击免费问诊按钮即生成挂号单",
                "1":"填写完成 (挂号单填写完成点击提交)",
                "2":"排队等待医生接诊",
                "3":"排队时取消问诊",
                "4":"医生接诊",
                "5":"接诊过程中退出",
                "6":"医生开具问诊小结",
                "7":"用户购药"
            },
            //派单排队进度
            req_type:{
                "10":"排队",
                "20":"抢单",
                "30":"选医生",
                "40":"付诊费",
                "50":"问诊",
                "60":"评价",
                "70":"关闭",
            },
            //医生缓存状态
            doctor_cache_type:{
                "1":"开诊等待中",
                "2":"派单中",
                "3":"抢单中",
                "4":"抢单成功等待付款",
                "5":"问诊中",
                "6":"关闭诊室"
            },
            //排队状态
            lineup_status:{
                "0":"关闭排队",
                "1":"接收排队"
            },
            req_child_type:{
                "11":"排队中",

                "21":"派单中",
                "22":"派单未响应",
                "23":"医生抢单",
                "24":"医生忽略抢单",
                "25":"抢单未响应",
                "26":"抢单名额已满",
                "27":"抢单未被选中",
                "28":"抢单已失效",
                "281":"派单被用户取消",
                "282":"选中被用户取消",
                "283":"付款被用户取消",
                "284":"选择医生超时",
                "29":"抢单被选中",

                "31":"选择医生",
                "32":"未选择医生",

                "41":"付诊费",

                "51":"问诊开始",

                "611":"用户取消排队",
                "621":"派单阶段-用户取消问诊",
                "631":"抢单阶段-用户取消问诊",
                "641":"选择医生阶段-用户取消问诊",
                "651":"付诊费阶段-用户取消问诊",
                "652":"付诊费阶段-用户没支付成功",
                "661":"问诊阶段-用户取消问诊",
                "662":"问诊阶段-用户确认问诊结束",

                "671":"问诊阶段-问诊超时自动关闭"
            }
        },
        hospital: {
            nature:{
                "-1":"全部",
                "1":"公立",
                "2":"民营",
                "3":"私立",
                "0":"未知"
            },
            level:{
                "-1":"全部",
                "30":"三级特等",
                "31":"三级甲等",
                "32":"三级乙等",
                "33":"三级丙等",
                "21":"二级甲等",
                "22":"二级乙等",
                "23":"二级丙等",
                "11":"一级甲等",
                "12":"一级乙等",
                "13":"一级丙等",
                "0":"未知"
            },
            type:{
                "-1":"全部",
                "1":"专科医院",
                "2":"综合医院",
                "3":"社区医院",
                "4":"诊所"
            }
        },
        user: {
            coupon_type:{
                "-1":"全部",
                "1":"代金券",
                "2":"兑换券",
                "3":"折扣券"
            },
            coupon_scope:{
                "-1":"全部",
                "10":"付费问诊",
                "20":"商品购买"
            },
            account_type:{
                "11":"用户余额",
                "12":"用户积分",
                "21":"医生余额",
                "22":"医生积分",
                "31":"平台积分"
            },
            doctor_audit_status:{
                "-1":"全部",
                "1":"待审核",
                "2":"审核不通过",
                "3":"审核通过"
            }
        },
        product: {
            auth_tags:{
                "7天退换":"7天退换",
                "正品保障":"正品保障"
            },
            feature_tags:{
                "半永久纹眉":"半永久纹眉",
                "半永久纹身":"半永久纹身",
                "半永久":"半永久"
            },
            tagNameSearch: {
                "":"全部",
                "一口价" : "一口价",
                "美+精选" : "美+精选",
                "实物商品" : "实物商品"
            },
            freeTrialSearch: {
                "0":"全部",
                "1" : "免费体验"
            },
            tagName: {
                "一口价" : "一口价",
                "美+精选" : "美+精选",
                "实物商品" : "实物商品"
            },
            shopConf: {
                "1" : "Banner",
                "2" : "商品"
            },
            shopSearchConf: {
                "-1" : "全部",
                "1" : "Banner",
                "2" : "商品"
            },
            projectConf: {
                "1" : "商城",
                "2" : "新娘"
            },
            projectSearchConf: {
                "-1" : "全部",
                "1" : "商城",
                "2" : "新娘"
            }
        },
        information: {
            type_id:{
                "-1":"全部",
                "1":"内容资讯",
                "2":"活动",
                "3":"问诊单",
                "4":"评论",
                "5":"商品",
                "6":"订单"
            },
            position_type:{
                "-1":"全部",
                "1":"C端首页",
                "2":"C端资讯页",
                "3":"D端首页",
                "4":"D端资讯页",
                "5":"C端启动页",
                "6":"D端启动页",
                "7":"H5商城"
            },
            ref_type:{
                "-1":"全部",
                "1":"资讯详细",
                "2":"商品详细",
                "3":"自定义内容"
            }
        },
        order:{
            //订单类型
            order_type:{
                "10":"免费咨询",
                "20":"付费问诊",
                "30":"名医预约",
                "40":"线下挂号",
                "50":"商品销售订单",
                "51":"商品退货订单",
                "60":"问诊处方单"
            },
             // 预约订单状态
            trade_cosmetic_status:{
                "1":"待支付",
                "2":"待预约",
                "3":"退款申请中",
                "4":"已退款",
                "5":"订单已取消",
                "6":"订单已关闭",
                "7":"待服务",
                "8":"服务完成"
            },
            //订单状态
            trade_status:{
                "1":"待支付",
                "2":"支付成功",
                "3":"退款申请中",
                "4":"已退款",
                "5":"订单已取消",
                "6":"订单已关闭",
                "7":"待服务",
                "8":"服务完成"
            },
            //取消状态
            cancel_status:{
                "0":"没取消",
                "1":"系统取消",
                "2":"用户取消"
            },
            process_status:{
                "-1":"全部",
                "0":"未处理",
                "1":"已处理"
            },
            //审核状态
            approve_status:{
                "1": "不需要审核",
                "2": "等待审核",
                "3": "批准",
                "4":"否决"
            },
            //支付方式
            pay_source:{
                "1":"余额",
                "2":"积分",
                "3":"优惠券",
                "4":"活动抵扣",
                "5":"微信客户端",
                "6":"微信公众号",
                "7":"支付宝客户端",
                "8":"支付宝Wap"
            },
            //第三方交易类型
            third_trade_type:{
                "1":"付款",
                "2":"退款"
            },
            //订单类型
            trans_type:{
                "20":"付费问诊",
                "50":"商品销售订单",
                "60":"问诊处方单"
            },
        }
    }
});