package com.linln.devtools.generate.utils.jAngel;

import com.linln.devtools.generate.utils.jAngel.nodes.ClassNode;
import com.linln.devtools.generate.utils.jAngel.nodes.Document;
import com.linln.devtools.generate.utils.jAngel.nodes.Node;
import com.linln.devtools.generate.utils.jAngel.parser.Expression;
import org.junit.Test;

/**
 * @author 小懒虫
 * @date 2019/3/28
 */
public class JAngelTest {

    @Test
    public void create() {
        long startTime = System.currentTimeMillis();    //获取开始时间



        String fileName = "ControllerTemplate.tpl";
        Document document = JAngel.parse("F:\\Project\\SpringBoot\\Timo-test\\devtools\\src\\main\\java\\com\\linln\\devtools\\generate\\template\\"+fileName,
                new Expression());

        assert document != null;
        ClassNode clazz = document.getClazz();
        clazz.insert(new Node("hsafjajsf"));
        clazz.insert(6, new Node("hsafjajsf"));
        clazz.getChildByName("!NULLService").remove();

        System.out.println(document);
        long endTime = System.currentTimeMillis();    //获取结束时间

        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间

    }
}