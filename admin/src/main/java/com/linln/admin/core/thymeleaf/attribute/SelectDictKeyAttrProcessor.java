package com.linln.admin.core.thymeleaf.attribute;

import com.linln.admin.core.thymeleaf.utility.DictUtil;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Map;

/**
 * 根据字典键值对生成下拉列表
 * @author 小懒虫
 * @date 2018/8/14
 */
public class SelectDictKeyAttrProcessor extends SelectDictEnumAttrProcessor {

    public static final int PRECEDENCE = 1400;
    public static final String ATTR_NAME = "dictKey";

    public SelectDictKeyAttrProcessor(final TemplateMode templateMode, final String dialectPrefix) {
        super(templateMode, dialectPrefix, ATTR_NAME, PRECEDENCE);
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
        Map<Long, String> valueList = DictUtil.keyValueList(attributeValue);
        if(valueList != null && valueList.size() > 0) {
            doProcess(context, tag, attributeName, attributeValue, structureHandler, valueList);
        };
    }

}
