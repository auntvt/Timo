<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:mo="https://gitee.com/aun/Timo">
<head th:replace="/common/template :: header(~{::title},~{::link},~{::style})">
    <style>
        body {
            background-color: #FFFFFF;
        }

        .chat-panel {
            width: 640px;
            height: 500px;
            position: absolute;
            top: 50%;
            left: 50%;
            background-color: #f5f5f5;
            transform: translate(-50%, -50%);
            -webkit-transform: translate(-50%, -50%);
            box-shadow: 0 1px 7px 2px rgba(0, 0, 0, 0.14);
            -webkit-box-shadow: 0 1px 7px 2px rgba(0, 0, 0, 0.14);
        }

        .chat-panel .chat-head {
            height: 40px;
            color: #FFFFFF;
            font-size: 18px;
            line-height: 40px;
            text-align: center;
            background-color: #7d7d7d;
        }

        .chat-panel .chat-head .layui-icon {
            line-height: 40px;
            float: right;
            margin-right: 12px;
        }

        .chat-panel .chat-record {
            overflow: auto;
            padding: 0 16px;
        }

        .chat-panel .chat-record .item {
            overflow: hidden;
            margin: 14px 0;
        }

        .chat-panel .chat-record .item.left div {
            float: left;
        }

        .chat-panel .chat-record .item.left .sign {
            border-color: transparent #ffc664 transparent transparent;
        }

        .chat-panel .chat-record .item.left .msg {
            color: #834105;
            background-color: #ffc664;
        }

        .chat-panel .chat-record .item.right div {
            float: right;
            border-color: transparent transparent transparent #9eea6a;
        }

        .chat-panel .chat-record .item.right .msg {
            background-color: #9eea6a;
        }

        .chat-panel .chat-record .picture {
            width: 40px;
            height: 40px;
            background-size: cover;
        }

        .chat-panel .chat-record .sign {
            width: 0;
            height: 0;
            margin-top: 6px;
            border: 5px solid;
        }

        .chat-panel .chat-record .msg {
            font-size: 16px;
            display: inline-block;
            padding: 7px 12px;
            border-radius: 2px;
            line-height: 22px;
        }

        .chat-panel .chat-record .msg a {
            color: #095eab;
            text-decoration: underline;
        }

        .chat-panel .chat-record .img img {
            width: 80px;
            margin: 0 10px;
        }

        .picture-left {
            background-image: url([[@{/images/chat-left-picture.jpg}]]);
        }

        .picture-right {
            background-image: url([[@{/images/chat-right-picture.jpg}]]);
        }
    </style>
</head>
<body>
    <div class="chat-panel">
        <div class="chat-head">小懒虫<i class="layui-icon layui-icon-close"></i></div>
        <div class="chat-record"></div>
    </div>
<script th:replace="/common/template :: script"></script>
<script type="text/javascript">
    var record = [{
        type: 1,
        img: true,
        msg: "<img src='[[@{/images/chat-single-dog.png}]]'>"
    }, {
        type: 2,
        msg: "欢迎来到单身狗俱乐部！哦不，是<span style='color:red'>TIMO</span>后台管理系统！"
    }, {
        type: 1,
        msg: "(◕ᴗ◕)是免费的吗？"
    }, {
        type: 2,
        msg: "本项目是采用了Apache Licence 2.0开源协议，\n是开源免费的哦！\n" +
        "开源地址：<a href='https://gitee.com/aun/Timo'>https://gitee.com/aun/Timo</a>"
    }, {
        type: 1,
        img: true,
        msg: "<img src='[[@{/images/char-ccmiao.png}]]'>"
    }];

    layui.use(['jquery'], function () {
        var $ = layui.jquery;
        var chatRecord = $(".chat-record");
        record.forEach(function (item) {
            chatRecord.append(addMsg(item));
        });

        function addMsg(item) {
            var type = item.type === 1 ? "left" : "right";
            var picture = item.type === 1 ? "picture-left" : "picture-right";
            if (item.img !== true) {
                return "<div class='item " + type + "'><div class='picture " + picture + "'></div>"
                    + "<div class='sign'></div><div class='msg'><pre>" + item.msg + "</pre></div></div>";
            } else {
                return "<div class='item " + type + "'><div class='picture " + picture + "'></div>"
                    + "<div class='img'>" + item.msg + "</div></div>";
            }
        }
    });
</script>
</body>
</html>
