<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:replace="/common/template :: header(~{::title},~{::link},~{::style})">
</head>
<body>
<div class="layui-form timo-compile">
    <form th:action="@{#{baseUrl}/save}">
        <input type="hidden" name="id" th:if="${#{var}}" th:value="${#{var}.id}"/>
        <div jsoup="field" class="layui-form-item">
            <label class="layui-form-label">#{field.title}</label>
            <div class="layui-input-inline">
                <input class="layui-input" type="text" name="#{field.name}"  placeholder="请输入#{field.title}" th:value="${#{var}?.#{field.name}}">
            </div>
        </div>
        <div jsoup="remark" class="layui-form-item layui-form-text">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea placeholder="请输入内容" class="layui-textarea" name="remark">[[${#{var}?.remark}]]</textarea>
            </div>
        </div>
        <div class="layui-form-item timo-finally">
            <button class="layui-btn ajax-submit"><i class="fa fa-check-circle"></i> 保存</button>
            <button class="layui-btn btn-secondary close-popup"><i class="fa fa-times-circle"></i> 关闭</button>
        </div>
    </form>
</div>
<script th:replace="/common/template :: script"></script>
</body>
</html>