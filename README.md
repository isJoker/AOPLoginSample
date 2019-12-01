
# AOP与AspectJ详解之项目实战

## AOP
AOP是 Aspect Oriented Programming 的缩写，即“面向切面编程”，通过使用AOP可以在编译期间对代码进行动态管理，一达到统一维护的目的。AOP其实是OOP编程思想的一种延续，利用AOP，我们可以对业务逻辑的各个模块进行隔离，降低模块间耦合度，提高程序的可重用性，进而提高开发效率。利用AOP，我们还可以在无侵入的状态下在宿主中插入一些代码逻辑，从而实现诸如日志埋点、性能监控、动态权限控制等。


### AOP相关术语
* Advice : 增强
* JoinPoint : 连接点
* PointCut : 切点
* Aspect : 切面
* Weaving : 织入
* Target : 目标对象

#### Advice
增强，又叫“通知”，是指织入到目标类连接点上的一段层序代码

#### JoinPoint
连接点，即程序执行的某个特定位置，如类开始初始化前、类初始化后、类中的某个方法调用前后、方法抛出异常后等。一个类或一段程序代码拥有一些具有边界性质的特定点就是连接点，如果一个类有两个方法，那么这两个方法都是连接点，即连接点是程序中客观存在的事物。

### PointCut
切点，也叫“切入点”，每个程序类都拥有多个连接点，AOP通过“切点”来定位特定的连接点。连接点相当于数据库中的记录，而切点相当于查询条件，一个切点可以对应多个连接点。

### Aspect
切面，由切点和增强组成，包括连接点和横切逻辑的定义

### Weaving
织入，织入是将增强添加到目标类具体连接点上的过程，根据不同的实现技术，AOP有三种不同的织入方式：
1. 编译器织入，需要使用特殊的Java编译器
2. 类装载期织入，需要使用特殊的类装载器
3. 动态代理织入，在运行期为目标类添加增强生成子类的方式

### Target 
目标对象，增强逻辑的织入目标类

了解完以上相关术语后大家可能还不太明白是怎么一回事，我们用白话来总结下：
1. 我们通过定义一个表达式（PointCat）来告诉程序，我们需要对哪个地方进行额外的操作，通过这个表达式（PointCat），我们得到了那些需要通知的方法（JoinPoint）
2. 我们还需要告诉程序，这些方法（JoinPoint）需要做怎样的增强（Advice）
3. 什么时候进行额外的操作（执行前/执行后/执行前后/返回之前），额外操作具体要做什么，我们把以上两步定义到一个地方（Aspect）
4. 进行额外的操作所涉及到被修改的对象称为目标对象（Target）
5. 完成上面所有的操作动作，总成为织入（Weaving）

## AspectJ
上面我们已经了解了AOP的概念，AspectJ实际上是对AOP编程思想的实现，可以和java配合起来使用。

AspectJ最核心的模块就是它提供的ajc编译器，它将AspectJ的代码在编译器插入到目标程序当中，ajc会构建目标程序与AspectJ代码的联系，在编译期将AspectJ代码插入到被切出的PointCut中，从而达到AOP的目的。

如果想对AspectJ有更多的了解，可以看其官网：[http://www.eclipse.org/aspectj/](http://www.eclipse.org/aspectj/)

### AspectJ相关的注解类

#### @Aspect
用来描述一个切面类，编译期会被AspectJ的ajc编译器识别
```
@Aspect
public class ClickBehaviorAspect {
    
}
```

#### @PointCut
用来定义切点，标记方法
```
    @Pointcut("execution(@com.jokerwan.aoploginsample.annotation.ClickBehavior * *(..))")
    public void methodPointcut() {

    }
```
上面标记了com.jokerwan.aoploginsample.annotation.ClickBehavior类的所有方法，“* *(..)”表示所有方法

#### @Before
前置增强，在某连接点之前执行的增强
```
    @Before("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
    public void onViewClickAOP(final JoinPoint joinPoint){
        View view = (View) joinPoint.getArgs()[0];
        TrackApi.getInstance().trackViewOnClick(view);
    }
```

#### @After
后置增强，在某连接点之后执行增强
```
    @After("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
    public void onViewClickAOP(final JoinPoint joinPoint){
        View view = (View) joinPoint.getArgs()[0];
        TrackApi.getInstance().trackViewOnClick(view);
    }
```

#### @Around
环绕增强，在切点前后执行
```
    @Around("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
    public Object onViewClickAOP(final ProceedingJoinPoint joinPoint) throws Throwable {
        // before: do something
        Log.d(TAG, "onViewClickAOP: 点击执行前");
        Object result = joinPoint.proceed();
        // after: do something
        Log.d(TAG, "onViewClickAOP: 点击执行后");
        return result;
    }
```

#### @AfterReturning
返回增强，在切入点方法返回结果之后执行
```
    @AfterReturning("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
    public void onViewClickAOP(final JoinPoint joinPoint){
        // do something
    }
```

#### @AfterThrowing
异常增强，切点抛出异常时执行
```
    @AfterThrowing("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
    public void onViewClickAOP(final JoinPoint joinPoint){
        Log.d(TAG, "onViewClickAOP: 出现错误");
    }
```

### 切点表达式
```
execution(* android.view.View.OnClickListener.onClick(android.view.View))
```
上面就是一个切点表达式，一个完整的切点表达式包含如下几个部分：
`execution (<修饰符模式>？<返回类型模式><方法名模式> (<参数模式>) <异常模式>？)
`
其中带?表示可选的；
修饰符模式是指：public、private、protected等；
异常模式是指：ClassNotFoundException异常等。

下面列几个切点表达式的例子方便大家理解：
#### Sample 1
```
    @After("execution(public * *(..))")
    public void testAOP(JoinPoint joinPoint){
        // do something
    }
```
该切入点将会匹配所有修饰符模式为public的方法
#### Sample 2
```
    @Around("execution(* on*(..))")
    public void testAOP(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "onViewClickAOP: 点击执行前");
        Object result = joinPoint.proceed();
        Log.d(TAG, "onViewClickAOP: 点击执行后");
    }
```
该切入点将会匹配所有方法名以“on”开头的方法
#### Sample 3
```
    @Before("execution(* com.jokerwan.track.android..*View(..))")
    public void testAOP(JoinPoint joinPoint) {
        // do something
    }
```
该切入点将会匹配 com.jokerwan.track.android包及其子包中所有方法名以“View”结尾的方法
#### Sample 4
```
    @AfterReturning("execution(String com.jokerwan.track.android.*(..))")
    public void testAOP(JoinPoint joinPoint, Object returnValue) {
        Log.d(TAG, "testAOP: 返回值=" + returnValue);
    }
```
该切入点将会匹配 com.jokerwan.track.android 包中所有返回类型是 String 的方法，并在方法返回结果之后打印当前方法的返回值
**注意：该切入点不包括子包里面的方法，这就是包名后面“.”与“..”表达式的区别**
#### Sample 5
```
    @After("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
    public void testAOP(JoinPoint joinPoint) {
        // do something
    }
```
该切入点将会匹配所有的android.view.View.OnClickListener.onClick(android.view.View)方法，并在方法执行之后执行相关代码
#### Sample 6
```
    @After("execution(@butterknife.onClick * *(..))")
    public void testAOP(JoinPoint joinPoint) {
        // do something
    }
```
该切入点将会匹配所有被ButterKnife的 @Click 注解声明的方法


### JoinPoint
上面的实例可以看到，在被注解的方法中有一个JoinPoint类型的参数，这个参数包含了切点（方法）的所有详细信息

我们通过一个例子来讲解下JoinPoint类型的参数，该实例实现了点击行为统计功能

## 通过Gradle配置使用AspectJ
1.项目根目录的build.gradle文件
```
buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.4.0'
        // aspectj
        classpath 'org.aspectj:aspectjtools:1.8.9'
        classpath 'org.aspectj:aspectjweaver:1.8.9'

    }
}

allprojects {
    repositories {
        google()
        jcenter()
        
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```
2.app模块下的build.gradle
```
apply plugin: 'com.android.application'

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'org.aspectj:aspectjtools:1.8.9'
        classpath 'org.aspectj:aspectjweaver:1.8.9'
    }
}

android {
    compileSdkVersion 29
    buildToolsVersion "29.0.2"
    defaultConfig {
        applicationId "com.jokerwan.aoploginsample"
        minSdkVersion 21
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.0.2'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test:runner:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.1.1'

    implementation 'org.aspectj:aspectjrt:1.8.13'
}


// 编译时用Aspect专门的编译器，不再使用传统的javac
// 添加如下配置脚本，使Android Studio使用ajc作为编译器编译代码
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main

final def log = project.logger
final def variants = project.android.applicationVariants

variants.all { variant ->
    if (!variant.buildType.isDebuggable()) {
        log.debug("Skipping non-debuggable build type '${variant.buildType.name}'.")
        return
    }

    JavaCompile javaCompile = variant.javaCompile
    javaCompile.doLast {
        String[] args = ["-showWeaveInfo",
                         "-1.8",
                         "-inpath", javaCompile.destinationDir.toString(),
                         "-aspectpath", javaCompile.classpath.asPath,
                         "-d", javaCompile.destinationDir.toString(),
                         "-classpath", javaCompile.classpath.asPath,
                         "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
        log.debug "ajc args: " + Arrays.toString(args)

        MessageHandler handler = new MessageHandler(true)
        new Main().run(args, handler)
        for (IMessage message : handler.getMessages(null, true)) {
            switch (message.getKind()) {
                case IMessage.ABORT:
                case IMessage.ERROR:
                case IMessage.FAIL:
                    log.error message.message, message.thrown
                    break
                case IMessage.WARNING:
                    log.warn message.message, message.thrown
                    break
                case IMessage.INFO:
                    log.info message.message, message.thrown
                    break
                case IMessage.DEBUG:
                    log.debug message.message, message.thrown
                    break
            }
        }
    }
}

```

3.定义一个ClickBehavior注解,用来注解需要统计的点击事件的方法
```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClickBehavior {
    String value();
}
```
4.接着定义一个切面ClickBehaviorAspect，用来匹配被ClickBehavior注解修饰的方法
```
@Aspect
public class ClickBehaviorAspect {

    private static final String TAG = "JokerWan";

    /**
     * 1、找到需要处理的切入点（找到被ClickBehavior修饰的方法，放到当前的切入点进行处理）
     *  “* *(..))” 表示可以处理ClickBehavior这个类所有的方法
     */
    @Pointcut("execution(@com.jokerwan.aoploginsample.annotation.ClickBehavior * *(..))")
    public void methodPointcut() {

    }

    /**
     * 2、对切入点如何处理
     */
    @Around("methodPointcut()")
    public Object joinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取签名方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // 获取方法所属的类名
        String className = methodSignature.getDeclaringType().getSimpleName();

        // 获取方法名
        String methodName = methodSignature.getName();

        // 返回值类型
        Class returnType = methodSignature.getReturnType();

        // 参数类型
        Class[] parameterTypes = methodSignature.getParameterTypes();

        // 参数名
        String[] parameterNames = methodSignature.getParameterNames();

        // 获取方法的注解值(需要统计的用户行为)
        String funName = methodSignature.getMethod().getAnnotation(ClickBehavior.class).value();

        // 统计方法的执行时间、统计用户点击某功能行为。（存储到本地，每过x天上传到服务器）
        long begin = System.currentTimeMillis();
        Log.e(TAG, "ClickBehavior Method Start >>> ");
        // MainActivity中切面的方法
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - begin;
        Log.e(TAG, "ClickBehavior Method End >>> ");
        Log.e(TAG, String.format("统计了：%s功能，在%s类的%s方法，用时%d ms",
                funName, className, methodName, duration));

        return result;
    }

}

```
5.在MainActivity对需要统计的方法进行注解
```
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "JokerWan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnShoppingCart = findViewById(R.id.btn_shopping_cart);
        Button btnOrderDetail = findViewById(R.id.btn_order_detail);
        Button btnUserCenter = findViewById(R.id.btn_user_center);

        btnLogin.setOnClickListener(this);
        btnShoppingCart.setOnClickListener(this);
        btnOrderDetail.setOnClickListener(this);
        btnUserCenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_shopping_cart:
                goShoppingCart();
                break;
            case R.id.btn_order_detail:
                goOrderDetail();
                break;
            case R.id.btn_user_center:
                goUserCenter();
                break;
        }
    }

    @ClickBehavior("跳转用户中心")
    private void goUserCenter() {
        Log.e(TAG, "开始跳转到 -> 用户中心 Activity");
        startActivity(new Intent(this, OtherActivity.class));
    }

    @ClickBehavior("跳转订单详情")
    private void goOrderDetail() {
        Log.e(TAG, "开始跳转到 -> 订单详情 Activity");
        startActivity(new Intent(this, OtherActivity.class));
    }

    @ClickBehavior("跳转购物车")
    private void goShoppingCart() {
        Log.e(TAG, "开始跳转到 -> 购物车 Activity");
        startActivity(new Intent(this, OtherActivity.class));
    }

    @ClickBehavior("去登录")
    private void login() {
        Log.e(TAG, "开始跳转到 -> 登录 Activity");
        startActivity(new Intent(this, LoginActivity.class));
    }
}
```
6.运行项目，点击按钮跳转，打开调试信息，输出如下：
```
2019-12-01 21:05:05.185 6647-6647/com.jokerwan.aoploginsample E/JokerWan: ClickBehavior Method Start >>> 
2019-12-01 21:05:05.185 6647-6647/com.jokerwan.aoploginsample E/JokerWan: 开始跳转到 -> 登录 Activity
2019-12-01 21:05:05.197 6647-6647/com.jokerwan.aoploginsample E/JokerWan: ClickBehavior Method End >>> 
2019-12-01 21:05:05.199 6647-6647/com.jokerwan.aoploginsample E/JokerWan: 统计了：去登录功能，在MainActivity类的login方法，用时12 ms
2019-12-01 21:05:11.813 6647-6647/com.jokerwan.aoploginsample E/JokerWan: ClickBehavior Method Start >>> 
2019-12-01 21:05:11.813 6647-6647/com.jokerwan.aoploginsample E/JokerWan: 开始跳转到 -> 用户中心 Activity
2019-12-01 21:05:11.821 6647-6647/com.jokerwan.aoploginsample E/JokerWan: ClickBehavior Method End >>> 
2019-12-01 21:05:11.821 6647-6647/com.jokerwan.aoploginsample E/JokerWan: 统计了：跳转用户中心功能，在MainActivity类的goUserCenter方法，用时8 ms
2019-12-01 21:05:16.373 6647-6647/com.jokerwan.aoploginsample E/JokerWan: ClickBehavior Method Start >>> 
2019-12-01 21:05:16.373 6647-6647/com.jokerwan.aoploginsample E/JokerWan: 开始跳转到 -> 订单详情 Activity
2019-12-01 21:05:16.379 6647-6647/com.jokerwan.aoploginsample E/JokerWan: ClickBehavior Method End >>> 
2019-12-01 21:05:16.380 6647-6647/com.jokerwan.aoploginsample E/JokerWan: 统计了：跳转订单详情功能，在MainActivity类的goOrderDetail方法，用时6 ms

```

