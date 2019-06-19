# Java使用Spring的DI功能改造WEB应用

1. 创建一个动态WEB工程 Dynamic Web Project 并命名为demo_spring03_init_web
![](53-Images/1.png)
![](53-Images/2.png)

2. 搭建环境（导入jar包commons-logging beans context core expression test web）
![](53-Images/3.png)

**添加到libraries路径下面**
![](53-Images/4.png)

成功后会成这样

![](53-Images/5.png)

3. 新建Servlet(controller/UserServlet)

![](53-Images/6.png)
![](53-Images/7.png)

**如图所示Servlet报错**
![](53-Images/8.png)

**解决Servlet报错:**
![](53-Images/9.png)
![](53-Images/10.png)
![](53-Images/11.png)
![](53-Images/12.png)
**如图所示已经解决咯报错**
![](53-Images/13.png)
**删除无用的方法**
![](53-Images/14.png)
**重写Service方法**
![](53-Images/15.png)
![](53-Images/16.png)

**测试是否能够请求到这个Servlet**
![](53-Images/17.png)
![](53-Images/18.png)

启动完成

![](53-Images/19.png)

输入http://localhost:9090/demo_spring03_init_web/UserServlet(备注我的端口是9090)

如图所示，测试成功

![](53-Images/20.png)

3. 新建Service(service/UserService)

![](53-Images/21.png)
```
package service;

public class UserService {

	public boolean login(String username,String password) {
		if("zhangsan".equals(username) && "123456".equals(password)) {
			return true;
		}else {
			return false;
		}
	}
}
```
4. 拷贝或新建配置文件
![](53-Images/22.png)
**beans.xml**
```
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" 
       xmlns:aop="http://www.springframework.org/schema/aop"      
       xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd
           http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.5.xsd">
	<bean id="userService" class="service.UserService"></bean>
</beans>
```
**UserServlet**
```
@Override
protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println("welcome!!!!");
    //假设：请求传递过来username:password
    //那么接收参数
    String username = request.getParameter("username");
    String password = request.getParameter("password");

//		UserService userService = new UserService();//之前的写法
    //使用userservice不需要servlet自己实例化
    //获取web片的spring容器
    WebApplicationContext act = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()); 
    UserService userService = (UserService) act.getBean("userService");
    //调用底层service
    boolean result = userService.login(username, password);
    //根据调用结果返回响应
    response.setContentType("text/html;charset=utf-8");
    if(result) {
        response.getWriter().write("<script>alert('login success');</script>");
    }else {
        response.getWriter().write("<script>alert('login fail');</script>");
    }
}
```
5. 配置web.xml让spring容器初始化
![](53-Images/23.png)
![](53-Images/24.png)

**web.xml中配置一个侦听器侦听已配置的jar包中的spring-web包中的web.context/ContextloaderListener.css**
![](53-Images/25.png)

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" version="3.0">
  <display-name>demo_spring03_init_web</display-name>
  
  <listener>
  	<listener-class>
  		org.springframework.web.context.ContextLoaderListener
  	</listener-class>
  </listener>
  
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file> 
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
</web-app>
```

**关联源码** ctrl+鼠标左键跳转

![](53-Images/26.png)
![](53-Images/27.png)

**如图所示关联成功**

![](53-Images/28.png)
![](53-Images/29.png)

**web.xml**
```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" version="3.0">
  <display-name>demo_spring03_init_web</display-name>
<!--
配置一个侦听器，侦听web应用启动事件，
一旦web应用启动,那么listener会读取contextConfigLocation参数值,
也就是读取spring的bean的配置文件然后把文件中注册的bean全部实例化
 -->
  <listener>
  	<listener-class>
  		org.springframework.web.context.ContextLoaderListener
  	</listener-class>
  </listener>
  <context-param>
  	<param-name>
  		contextConfigLocation
  	</param-name>
  	<param-value>/WEB-INF/beans.xml</param-value>
    <!--
    如果侦听多个的写法是
    <param-value>/WEB-INF/beans.xml,x,x,x</param-value>
    或者
    <param-value>/WEB-INF/*_beans.xml</param-value>
    或者
    <param-value>/WEB-INF/beans.xml,classpath:beans.xml</param-value>
    -->
  </context-param>
  
  
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file> 
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
</web-app>
```
**UserService**
```
package service;
public class UserService {
	public UserService() {
		System.out.println("userService 实例化了");
	}

	public boolean login(String username,String password) {
		if("zhangsan".equals(username) && "123456".equals(password)) {
			return true;
		}else {
			return false;
		}
	}
}
```
6. 启动项目测试是否配置成功
![](53-Images/30.png)
![](53-Images/31.png)

**如图所示,测试成功**

![](53-Images/32.png)

**拓展知识：** [Eclipse如何修改启动项目与项目名称不一致的情况](https://www.jianshu.com/p/c6961258679a)

7. 带参访问UserServlet

http://localhost:9090/demo_spring03_init_web/UserServlet?username=zhangsan&password=123456

![](53-Images/33.png)

##### 总结

第一步：导入jar包
```
beans context  core  expression  test web  commons-logging
```

第二步：编写service并配置  编写servlet调用service
```
//获取web版的spring容器
WebApplicationContext act=WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
UserService userService=(UserService) act.getBean("userService");
```

第三步：在web.xml中注册listener  

```
<!-- 
  配置一个侦听器，侦听web应用启动事件，
  一旦web应用启动，那么listener会读取contextConfigLocation参数值，也就是读取spring的bean的配置文件
  然后把文件中注册的bean全部实例化  
   -->
  
  <listener>
 <listener-class> org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  
  <context-param>
  <param-name>contextConfigLocation</param-name>
  <param-value>/WEB-INF/beans.xml</param-value>
  </context-param>
  ```

  以上就是我对于**Java使用spring的di功能对web应用进行改造** 总结的全部内容，附上[源代码](https://github.com/javaobjects/demo_spring03_init_web)

==================================================================
#### 分割线
==================================================================

**博主为咯学编程：父母不同意学编程，现已断绝关系;恋人不同意学编程，现已分手;亲戚不同意学编程，现已断绝来往;老板不同意学编程,现已失业三十年。。。。。。如果此博文有帮到你欢迎打赏，金额不限。。。**

![](https://upload-images.jianshu.io/upload_images/5227364-0824589594f944c7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)