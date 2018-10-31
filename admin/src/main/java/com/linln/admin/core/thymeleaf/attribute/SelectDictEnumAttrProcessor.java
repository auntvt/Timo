package com.linln.admin.core.thymeleaf.attribute;

import com.linln.admin.core.thymeleaf.utility.DictUtil;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.postprocessor.IPostProcessor;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.*;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 根据字典枚举生成下拉列表
 * @author 小懒虫
 * @date 2018/8/14
 */
public class SelectDictEnumAttrProcessor extends AbstractAttributeTagProcessor {

    public static final int PRECEDENCE = 1400;
    public static final String ATTR_NAME = "dictEnum";
    public static final String SELECTED_ATTR_NAME= "mo-selected";
    public static final String EMPTY_ATTR_NAME= "mo-empty";


    public SelectDictEnumAttrProcessor(final TemplateMode templateMode, final String dialectPrefix) {
        super(templateMode, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
    }

    public SelectDictEnumAttrProcessor(final TemplateMode templateMode, final String dialectPrefix, String attr_name, int precedence) {
        super(templateMode, dialectPrefix, null, false, attr_name, true, precedence, true);
    }


    @Override
    protected void doProcess(
            final ITemplateContext context,
            final IProcessableElementTag tag,
            final AttributeName attributeName,
            final String attributeValue,
            final IElementTagStructureHandler structureHandler) {

        // 如果属性值为空或者标签不为select。则不处理
        String elementName = tag.getElementCompleteName();
        if(attributeValue.isEmpty() || !elementName.equals("select")) return;

        // 获取列表对象，空则不处理
        Map<Long, String> valueList = DictUtil.enumValueList(attributeValue);
        if(valueList != null && valueList.size() > 0) {
            doProcess(context, tag, attributeName, attributeValue, structureHandler, valueList);
        };
    }

    protected void doProcess(
            final ITemplateContext context,
            final IProcessableElementTag tag,
            final AttributeName attributeName,
            final String attributeValue,
            final IElementTagStructureHandler structureHandler,
            Map<Long, String> valueList) {

        // 获取默认选中值
        String attributeSelectedValue = tag.getAttributeValue(SELECTED_ATTR_NAME);
        Object selectedValue = null;
        final IEngineConfiguration configuration = context.getConfiguration();
        if (attributeSelectedValue != null && !attributeSelectedValue.isEmpty()){
            final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);
            final IStandardExpression expression = expressionParser.parseExpression(context, attributeSelectedValue);
            final Object expressionResult;
            if (expression != null && expression instanceof FragmentExpression) {
                final FragmentExpression.ExecutedFragmentExpression executedFragmentExpression =
                        FragmentExpression.createExecutedFragmentExpression(context, (FragmentExpression) expression);
                expressionResult =
                        FragmentExpression.resolveExecutedFragmentExpression(context, executedFragmentExpression, true);
            } else {
                expressionResult = expression.execute(context, StandardExpressionExecutionContext.NORMAL);
            }
            if (expressionResult == NoOpToken.VALUE) {
                return;
            }
            selectedValue = (expressionResult == null ? "" : expressionResult);
        }

        // 拼装下拉列表选项
        List<String> keyList = new ArrayList<>();
        if (selectedValue instanceof List){
            ((List) selectedValue).forEach(item -> keyList.add(String.valueOf(item)));
        }else {
            keyList.add(selectedValue == null ? "" : selectedValue.toString());
        }
        StringBuilder optionContent = new StringBuilder();
        valueList.forEach((key, val) -> {
            optionContent.append("<option value = '").append(key);
            if(keyList.contains(String.valueOf(key))){
                optionContent.append("' ").append("selected='selected");
            }
            optionContent.append("'>").append(val).append("</option>");
        });

        // 判断是添加空值选项
        String attributeEmptyValue = tag.getAttributeValue(EMPTY_ATTR_NAME);
        if(attributeEmptyValue != null){
            String emptyStr = "<option value=''>" + attributeEmptyValue + "</option>";
            optionContent.insert(0, emptyStr);
        }

        // 返回新的HTML节点
        final Set<IPostProcessor> postProcessors = configuration.getPostProcessors(getTemplateMode());
        if (postProcessors.isEmpty()) {
            structureHandler.removeAttribute(SELECTED_ATTR_NAME);
            structureHandler.removeAttribute(EMPTY_ATTR_NAME);
            structureHandler.setBody(optionContent, false);
        }
    }

}
