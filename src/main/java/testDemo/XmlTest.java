package testDemo;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/23 20:59
 */
public class XmlTest {
    public static void main(String[] args) throws Exception {
        ArrayList<User> users = readXml("/user.xml");
        for (User user : users) {
            System.out.println(user + "---------");
        }

    }

    private static ArrayList<User> readXml(String xmlPath) throws DocumentException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //创建一个容器，保存所有的用户
        ArrayList<User> userArrayList = new ArrayList<User>();
        // 创建
        SAXReader saxReader = new SAXReader();
        // 读取xml文件
        Document document = saxReader.read(XmlTest.class.getResourceAsStream(xmlPath));
        // 读取元素
        Element rootElement = document.getRootElement();
        //
        List<Element> list = rootElement.elements();
        for (Element element : list) {
            // 获取子节点标签
            // System.out.println(element.getName());
            User user = new User();
            // 提取子节点标签的各个属性值
            Attribute attribute = element.attribute("id");
            String id = attribute.getValue();
            //System.out.println(id);
            user.setId(id);
            //获取内部所有属性值
            List<Element> userInfoElements = element.elements();
            for (Element userInfoElement : userInfoElements) {
                String tagName = userInfoElement.getName();
                String tagValue = userInfoElement.getStringValue();
                /*if ("name".equals(tagName)) {
                    user.setName(tagValue);
                } else if ("age".equals(tagName)) {
                    user.setAge(tagValue);
                } else if ("sex".equals(tagName)) {
                    user.setSex(tagValue);
                }*/
                //反射，如果name是XXX，就调用setXXX方法
                //1，获得字节码对象
                Class<User> clazz = User.class;
                //获得setXXX方法名字
                String setMethod = "set" + (tagName.charAt(0) + "").toUpperCase() + tagName.substring(1);
                //通过字节码对象获得setXXX方法
                Method method = clazz.getMethod(setMethod, String.class);
                //反射调用，user对象，调用这个method，设置了一个value的值
                method.invoke(user, tagValue);
            }
            //System.out.println(user);
            userArrayList.add(user);
        }
        return userArrayList;
    }
}
