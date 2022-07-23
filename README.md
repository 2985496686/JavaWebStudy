# javaweb-study
# 学习笔记
## 编码转换
    post方式下设置编码：
        req.setCharacterEncoding("UTF-8");(这行代码要放在所有获取参数动作之前)
    get方式下请求发送中文数据：
            Tomcat8之前需要进行如下转换，Tomcat8之后不需要进行转换
            String fname = req.getParameter("fname");
            byte[] bytes = fname.getBytes("ISO-8859-1");
            fname = new String(bytes,"UTF-8");

## Servlet及其子类
1. 继承关系
javax.servlet(接口) ---> javax.servlet. GenericServlet(抽象类)  ---->  javax.servlet.http.HttpServlet(抽象类)
2. 常用方法
   void init(ServletConfig var1) throws ServletException;
   void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;
   void destroy();
   ServletConfig getServletConfig();
   String getServletInfo();
3. service方法源码分析
   该方法是在HttpServlet中实现的
4. 生命周期
    从出生到死亡就是一个生命周期
    Servlet第一次被请求时会执行 构造方法，init()和service()方法，之后的请求只会执行service()方法，当容器被关闭时会调用destroy()方法
    注意：第一次请求时系统会通过反射机制来调用无参构造从而来实例化一个对象
    Servlet的实例化和初始化的时机
    (1)默认情况下在第一次响应时会进行实例化和初始化。
    (2)我们可以在web.xml中进行设置：<load-on-startup>1</load-on-startup>，数字越小，时机越靠前，最小为0.
5. 容器中的Servlet是单例的(所有请求都是一个实例来响应的)，线程不安全的。
    服务器启动只会生成一个实例对象，不同用户的不同请求都是访问同一个servlet对象，所以Servlet是单例并且是非线程安全的，所以为保证安全性不建议在Servlet中定义静态变量，或者将静态变量设置为只读
6. Http协议
    (1)http称之为超文本标记语言
    (2)http是无状态的：服务器无法区分两次请求是否是同一个客户端发送过来的。
       解决办法：会话跟踪技术
    (3)Http请求响应包含两个部分：请求和响应
      ---请求
        请求包含三个部分：请求行，请求消息头，请求主题
            请求行：包含三个信息：1.请求的方式 ； 2.请求的url ； 3请求的协议
            请求消息头：包含了很多客户端要告诉服务器的信息(比如说浏览器的版本型号，能够接受响应的类型等)
            请求体：
               get方式没有请求体，但是有queryString
               post方式，有请求体，form data
               json格式，有请求体，request payload

      ---响应
        响应包含三个部分：1.响应行； 2.响应头； 3响应体
            1)响应行：1.协议；2响应状态码；3响应体
            2)响应头：包含了服务器的信息
            3)响应的实际内容(比如请求html页面时，响应就时该html文件中的具体内容)

7. 会话
    会话跟踪技术
        1)客户端第一次发请求给服务器，服务器获取session，获取不到，则创建新的，然后响应给客户端
        2)下次客户端发送请求时会将已有的session发送给服务器。
    常用api
    request.getSession() 获取当前会话，没有创建一个新的
    request.getSession(true) 获取当前会话，没有获取一个新的，和无参的一样
    request.getSession(false) 获取当前会话，没有返回null，不会创建新的

    session.isNew(); 判断当前会话是否是新创建的
    session.getMaxInactiveInterval();获取会话的最大非激活间隔时常(一定时间不发送请求，会话就会失效)，默认为1800s
    session.invalidate();强制让会话失效

    session保存作用域
    每一个会话都有一个保存作用域，在作用域内以键值对的方式保存信息
    -常用API
    void setAttribute(String key,Object value)
    Object getAttribute(String key)
    void removeAttribute(String key)

8. 服务器端内部转发和客户端重定向
    1)服务器内部转发
    request.getRequestDispatcher("test05").forward(request,response);
    改行为是服务器端内部的行为，并不会导致浏览器地址栏的改变
    2)客户端重定向
    客户端发送请求，服务器让其请求另外一个组件，浏览器的地址栏发生了改变

9. get()和post()区别
   (1)客户端直接请求servlet时默认请求方式为get.
   (2)提交html表单时，使用get方式会将参数数据放在url的后边发给服务器，不安全。
   (3)提交html表单时，使用post方式会将参数数据放在http的请求体里面发给服务器。
   (4)get传送的数据是添加在url后边的，浏览器对url都有自己的长度限制，所以通过get方式发送的数据量较小。
   (5）post发送数据的是添加在http请求体里的，发送数据量较大，但是一般服务器会对post发送的数据量进行限制。

10. ServletRequest中的常用方法：getParameter()，可以获取请求的信息。
(11)ServletResponse中的常用方法：通过getWriter方法获取PrintWriter对象，向客户端发送数据。

## cookie
1. cookie的创建，以键值对的形式
 Cookie cookie = new Cookie("cookie1","1122");
 此时Cookie只是在服务端创建，客户端此时还不知道，服务端需要告诉客户端加入这个Cookie
 response.addCookie(cookie);
 客户端首先会根据key值判断有没有该cookie，若没有，创建新的cookie，若有就用value覆盖该cookie的value

2. 服务端获取Cookie
   客户端在发送请求时会将cookie一并发给服务器
   服务器可以通过
   request.getCookies();获取由请求发送过来的全部请求。

3. 重写cookie
    方案一：用相同的key值创建cookie，用新cookie覆盖原先的cookie，达到重写的效果。
    方案二：获取要修改的cookie，然后调用cookie.setValue(newValue)方法修改value，要注意的是这里对value进行修改，修改的只是服务端获取的cookie，想让客户端同样需要响应给客户端
           即：resp.addCookie(cookie);所以说这种方法本质也是方案一。

 4. cookie的生命周期控制
 Cookie的生命周期控制指的是管理Cookie的销毁(删除)时机。
 可以通过cookie.setMaxAge(int expiry)来进行控制，默认情况下expiry为-1
 expiry > 0 表示cookie在expiry秒后删除,在到达指定时间前，是一直存在的。
 expiry = 0 表示cookie立刻删除
 expiry < 0 表示cookie在浏览器被关闭的时候立马会被删除，该cookie是会话级别的

 5. cookie的path属性:定义了Web站点上可以访问该Cookie的目录。在客户端可以有效的过滤掉访问某一服务器不需要的cookie

    cookie的path默认为: 工程路径/,


 ## Web项目中"/"的含义
 1. “/”是绝对路径
 2. 服务器解析“/”为：http://ip:port/
 3. 客户端解析：“/”为：http://ip:port/工程路径/
 注意：服务器重定向中的 ：resp.sentRedirect("/");这里面的斜杠是发送给浏览器解析，这里如果不加“/”,默认发送的是http://ip:port/工程路径/
       配置文件中的路径是由客户端来解析，表单action里的路径也由客户端来解析
       工程路径映射到web项目就是web目录下


 ## Filter ----- JavaWeb三大组件之一
1. Filter作用：拦截请求，过滤响应、
2. Filter的生命周期
   (1)项目启动时实例化filter对象
   (2)在Web工程启动的时候执行Init()方法
   (3)doFilter()过滤方法，每次拦截到请求都会执行该方法。
   (4)destroy()方法，停止Web项目时就会执行该方法，销毁Filter过滤器。
3. filterConfig
在初始化时，系统会给init方法传入filterConfig对象
通过调用FilterConfig对象方法可以得到以下信息：
getFilterName() ----- 配置Filter时起的名字
getServletConfig ------ 获取ServletConfig对象
getServletParameter ----- 获取给Filter配置的参数
getInitParameter   -------获取初始化参数
4. FilterChain
FilterChain是过滤器链
他只有一个方法：doFilter()-----如果没有其它Filter了，会去执行目标资源；如果还有其它过滤器，回去执行其它过滤器。
多个Filter过滤器联合使用的特点：
(1)它们会按照web.xml文件中的配置顺序，依次执行每一个过滤器。
(2)从客户端发送请求，然后经过第1个过滤器，第2个过滤器(假设这里只有两个过滤器)，直到访问到资源，然后在依次经过第二个过滤器，第一个过滤器，最终将资源响应给客户端，该过程是一个请求和响应的过程。
(3)执行过程中，中间任意一个过滤器未执行FileChain.doFilter()方法，那么后面的过滤器也都不会执行，资源也访问不到。
(4)拦截路径的三种配置方式
 第一种---精确匹配：<url-pattern>/Admin/p.jpg</url-pattern> 拦截http://ip:post/项目路径/Admin/p.gpj，拦截具体某一个资源
 第二种---目录匹配：<url-pattern>/Admin/</url-pattern> 拦截http://ip:post/项目路径/Admin/目录下的所有资源
 第三种---后缀名匹配：<url-pattern>*.html</url-pattern>拦截以.html为后缀名的所有资源，注意这里不要加"/"。

 Filter过滤器只关心请求的地址，不关心这个资源是否真的存在。


 ## Ajax
 Ajax是一种浏览器通过js异步发送请求，局部更新页面技术

 Ajax请求的局部页面更新，不会舍弃页面原来有的内容
