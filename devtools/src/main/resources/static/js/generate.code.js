layui.use(['element', 'form', 'layer', 'formSelects'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var formSelects = layui.formSelects;

    // 定义Basic基本信息类
    var basic = {
        projectPath: '',
        packagePath: '',
        author: '',
        genTitle: '',
        genModule: '',
        genPMenu: '',
        tablePrefix: '',
        tableName: '',
        tableEntity: '',
        requestMapping: '',
        moduleType: ''
    };
    // 监听表单数据变动
    $('#basic').on('input propertychange', function(e) {
        var $this = $(e.target);
        var name = $this.attr("name");
        basic[name] = $this.val();
        if(name === "tableName"){
            if($this.val().length > 0){
                $(".panel-header .entity").css("display", "block");
            }else{
                $(".panel-header .entity").css("display", "none");
            }
        }
    });

    // 数据监听Basic数据变动
    var defaults = {};
    Object.defineProperty(basic, "packagePath", {
        get: function () { return defaults.packagePath},
        set: function(value) {
            $(".bindPackagePath").text(value);
            defaults.packagePath = value;
        }
    });
    Object.defineProperty(basic, "genModule", {
        get: function () { return defaults.genModule},
        set: function(value) {
            $(".bindGenModule").text(value);
            defaults.genModule = value;
            updateRequestMapping();
        }
    });
    Object.defineProperty(basic, "tablePrefix", {
        get: function () { return defaults.tablePrefix},
        set: function(value) {
            $(".bindTablePrefix").text(value);
            defaults.tablePrefix = value;
        }
    });
    Object.defineProperty(basic, "tableName", {
        get: function () { return defaults.tableName},
        set: function(value) {
            $(".bindTableName").text(value);
            defaults.tableName = value;

            var split = value.split("_"), newValue = '';
            split.forEach(function (val) {
                newValue += val.substring(0,1).toUpperCase()+val.substring(1);
            });
            basic.tableEntity = newValue;
            var $tableEntity = $(".tableEntity");
            var $bindTableEntity = $(".bindTableEntity");
            $tableEntity.val(newValue);
            $bindTableEntity.text(newValue);
        }
    });
    Object.defineProperty(basic, "tableEntity", {
        get: function () { return defaults.tableEntity},
        set: function(value) {
            $(".bindTableEntity").text(value);
            defaults.tableEntity = value;
            updateRequestMapping();
        }
    });

    // 初始化basic数据
    $('#basic input').each(function (key, val) {
        var name = $(val).attr("name");
        if(name !== undefined){
            basic[name] = $(val).val();
        }
    });

    // 更多按钮
    $('.more-btn').on("click", function (e) {
        e.preventDefault();
        $(".more-field").slideToggle();
        $(this).children(".fa").toggleClass("active");
    });

    // 访问地址变动
    function updateRequestMapping() {
        var module = "/" + basic.genModule.replace(new RegExp('\:', "g"), '/');
        var entity = (basic.tableEntity + "");
        entity = entity.substring(0,1).toLowerCase()+entity.substring(1);
        $("input[name='requestMapping']").val(module + "/" + entity);
    }

    /* 实体模型操作 */
    var entity = $("#entity");
    var field = null;
    // 选中字段
    entity.on("click", ".entity-number", function () {
        if(field !== null){
            $(field).css("background-color", "#FFFFFF");
            $(field).css("color", "#666666");
        }
        if(field !== this){
            $(this).css("background-color", "#5FB878");
            $(this).css("color", "#FFFFFF");
            field = this;
        }else{
            field = null;
        }
    });
    // 添加字段
    $(".field-add").on("click", function () {
        var element = entity.children("tr:last-child").clone();
        element.find("input, select").val("");
        element.find("[name='type']").val("1");
        var random = Math.random()*10000;
        element.find("[xm-select]").attr("xm-select", random);
        if(field == null){
            entity.append(element);
        }else {
            $(field).parent().after(element);
        }
        element.children(".entity-number").click();
        form.render();
        formSelects.render(random);
        resetNumber();
    });
    // 删除字段
    $(".field-del").on("click", function () {
        if(field != null){
            $(field).parent().remove();
            resetNumber();
        }
    });
    // 上移字段
    $(".field-up").on("click", function () {
        if(field != null){
            var parent = $(field).parent();
            if(parent.prev().length === 1){
                parent.insertBefore(parent.prev());
                resetNumber();
            }
        }
    });
    // 下移字段
    $(".field-down").on("click", function () {
        if(field != null){
            var parent = $(field).parent();
            if(parent.next().length === 1){
                parent.insertAfter(parent.next());
                resetNumber();
            }
        }
    });

    // 重置字段编号
    var resetNumber = function(){
        entity.children().each(function (key, val) {
            $(val).children(".entity-number").text(key + 1);
        });
    };

    // 模块悬浮标签
    $(".float-label").on("click", function(){
        $(this).toggleClass("active");
    });

    // 提取实体类数据
    var getEntity = function(){
        var fieldList = [];
        entity.children().each(function (key, trNode) {
            var field = {};
            field.name = $(trNode).find("[name='name']").val();
            field.title = $(trNode).find("[name='title']").val();
            field.type = $(trNode).find("[name='type']").val();
            field.query = $(trNode).find("[name='query']").val();
            field.show = $(trNode).find("[name='show']").is(':checked');
            var xmId = $(trNode).find(".entity-verify select").attr("xm-select");
            field.verify = formSelects.value(xmId, 'val');
            fieldList[key] = field;
        });
        return fieldList;
    };

    // 认证基本信息
    var verifyBasic = function(){
        var result = JSON.parse(JSON.stringify(basic));
        $('#basic').find("input[name]").each(function (key, item) {
            if($(item).val().replace(/(^\s*)|(\s*$)/g, "") === ""){
                var title = $(item).parent().prev().text();
                layer.msg(title + "不能为空！", {offset: '15px', time: 3000, icon: 2});
                return result = false;
            }
            result[$(item).attr('name')] = $(item).val();
        });
        // 处理模板结构选项
        result.moduleType = basic.moduleType = $("input[name='moduleType']:checked").val();
        return result;
    };

    // 提取模板数据
    var getTemplate = function(){
        var template = {};
        $("#float").children(".float-label").each(function (key, item) {
            var name = $(item).data("name");
            template[name] = $(item).hasClass("active");
        });
        return template;
    };

    // 保存按钮
    $(".entity-save").on("click", function () {
        // 检验处理基本数据
        var basic = verifyBasic();
        if(basic === false){
            return false;
        }

        // 封装数据
        var generate = {
            basic: basic,
            fields: getEntity(),
            template: getTemplate()
        };

        // 处理模块分组
        var split = basic.genModule.split(":");
        if (split.length > 1 && split[1].length > 0) {
            var newBasic = JSON.parse(JSON.stringify(basic));
            newBasic.genModule = split[0];
            newBasic.genGroup = split[1];
            generate.basic = newBasic;
        }

        var url = $(this).data("url");
        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(generate),
            dataType: "json",
            success: function (result) {
                if(result.code === 200){
                    genDetail(result.data);
                    basicHistory(basic);
                }else{
                    layer.msg(result.msg, {offset: '15px', time: 3000, icon: 2});
                }
            },
            error: function (message) {
                layer.msg("保存失败！", {offset: '15px', time: 3000, icon: 2});
            }
        });
    });

    //生成文件信息详细页面
    function genDetail(list){
        var panel = '';
        for(var p in list){
            var path = list[p];
            panel += "<div class='item'><span class='title'>" + p + "</span>：";
            if(path.indexOf("exist:") === 0){
                panel += "<span class='info-e'><i class='fa fa-times-circle-o'></i> 生成失败，文件已存在</span>";
                path = path.substring("exist:".length, path.length);
            }else {
                panel += "<span class='info-s'><i class='fa fa-check-circle-o'></i> 生成成功</span>";
            }
            panel += "<div class='path'>" + path + "</div>";
            panel += "</div>";
        }
        layer.open({
            type: 1,
            title: '生成文件信息',
            shadeClose: true,
            skin: 'layui-layer-rim',
            area: ['800px', '540px'],
            content: "<div class='save-detail'>" + panel + "</div>"
        });
    }

    //记录部分基本参数
    var local = window.localStorage;
    function basicHistory(basic){
        if(window.localStorage){
            local.setItem("packagePath", basic.packagePath);
            local.setItem("author", basic.author);
            local.setItem("genModule", basic.genModule);
            local.setItem("tablePrefix", basic.tablePrefix);
            local.setItem("moduleType", basic.moduleType);
        }
    }

    //还原基本参数
    if(local.getItem("packagePath")){
        var packagePath = local.getItem("packagePath");
        basic.packagePath = packagePath;
        $("[name='packagePath']").val(packagePath);
    }
    if(local.getItem("author")){
        var author = local.getItem("author");
        basic.author = author;
        $("[name='author']").val(author);

    }
    if(local.getItem("genModule")){
        var genModule = local.getItem("genModule");
        basic.genModule = genModule;
        $("[name='genModule']").val(genModule);
    }
    if(local.getItem("tablePrefix")){
        var tablePrefix = local.getItem("tablePrefix");
        basic.tablePrefix = tablePrefix;
        $("[name='tablePrefix']").val(tablePrefix);
    }
    if(local.getItem("moduleType")){
        var moduleType = local.getItem("moduleType");
        basic.moduleType = moduleType;
        var index = parseInt(moduleType) - 1;
        $("input[name='moduleType']:eq("+ index +")").attr('checked', 'true');
        form.render('radio');
    }
});