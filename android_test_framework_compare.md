# 5个最佳的Android测试框架（带示例）[转]

文章地址 [http://ihongqiqu.com/2016/01/08/Android-Test-Framework-comparison/](http://ihongqiqu.com/2016/01/08/Android-Test-Framework-comparison/)

  谷歌的Android生态系统正在不断地迅速扩张。有证据表明，新的移动OEM正在攻陷世界的每一个角落，不同的屏幕尺寸、ROM /固件、芯片组以及等等等等，层出不穷。于是乎，对于Android开发人员而言，处理存储碎片变得越来越困窘。

  不过幸运的是，Android（还有iOS）开发人员可以无限制地访问一些先进的基于云的解决方案，如Testdroid Cloud，就可以在大规模的真实设备上执行自动化测试以确保质量，赞吧。此外，不同的Android测试框架的出现也大大减轻了Android开发人员 的负担。

  今天，我们就要说说5款最常用的Android测试框架，并且每个框架都给出了基本的代码示例。

## 1.Robotium

  不可否认，Robotium曾是Android世界之初使用最广泛的Android测试框架，风靡一时。由于它与Android有着相似的Selenium，所以它能够使得API的测试变得简单起来。

  Robotium是一个扩展于JUnit的开源库，运用多种有用的方法来支持Android UI测试。它提供的强大的自动化黑箱测试范例，可用于Android应用（原生的和混合的）和web测试。只要源代码允许，你就可以通过Robotium 写功能、系统和验收测试方案，以及测试应用。

  Robotium的代码示例：

```java
// Public void for the operation
public void testRecorded() throws Exception {
// Wait for the text 'Hello!' to be shown for newbie
if (solo.waitForText("Hello!")) {
// R class ID identifier for 'Sign in' - and click it
solo.clickOnView(solo.findViewById("com.twitter.android.R.id.sign_in"));
// R class ID identifier for entering username
solo.enterText((EditText) solo.findViewById("com.twitter.android.R.id.login_username"),"username");
// R class ID identifier for entering password
solo.enterText((EditText) solo.findViewById("com.twitter.android.R.id.login_password"),"password");
// R class ID identifier for clicking log in
solo.clickOnView(solo.findViewById("com.twitter.android.R.id.login_login"));
// Wait until log in is done
solo.waitForActivity("HomeTabActivity");
}
// Activate the text field to compose a tweet
solo.clickOnView(solo.findViewById("com.twitter.android.R.id.menu_compose_tweet"));
// Type the tweet
solo.enterText((EditText) solo.findViewById("com.twitter.android.R.id.edit"), "Testdroid");
// Tweeting!
solo.clickOnView(solo.findViewById("com.twitter.android.R.id.composer_post"));
}
```

  为了给大家提供便捷，还有一个用Robotium构建的用于测试脚本创建的一个非常棒的记录工具——Testdroid Recorder。当你在真实设备上执行实际行动时，它可以记录你的每一个步骤和每一个行为，并转换成JavaScript，以便于你进一步的修改。

  并且，你还可以全权下载和使用它的扩展库——ExtSolo，它里面包含了多种还没有被纳入到Robotium中的实用方法，例如：

 * 支持任意分辨率的x、Y点击自动缩放
 * 多路径拖动
 * 测试故障时自动截图
 * 模拟地点
 * 更改设备语言
 * 控制WiFi连接

官方网站：https://code.google.com/p/robotium/

## 2.uiautomator

  虽然Robotium是一个很好的测试框架，但是uiautomator能让你在测试Android应用和Android游戏时做得更多。谷歌的测 试框架允许你在一个或多个设备上测试原生Android应用的用户界面（UI）。Uiautomator的另一个优点是，它运行的JUnit测试用例是有 特殊权限的，这意味着测试用例可以跨越不同的进程。它还提供了五种不同的类给开发人员使用：

```java
com.android.uiautomator.core.UiCollection;
com.android.uiautomator.core.UiDevice;
com.android.uiautomator.core.UiObject;
com.android.uiautomator.core.UiScrollable;
com.android.uiautomator.core.UiSelector
```

  遗憾的是，uiautomator只能工作于API16或更高级别的Android设备上。它的另一个缺点是不支持web视图，也没有办法直接访问Android对象。

  uiautomator的代码示例：

```java
// Public void for the operation
public void testSignInAndTweet() throws Exception {
// Starting application:
getUiDevice().wakeUp(); // Press Home button to ensure we're on homescreen
getUiDevice().pressHome(); // Select 'Apps' and click button
new UiObject(new UiSelector().description("Apps")).click(); // Select 'Twitter' and click
new UiObject(new UiSelector().text("Twitter")).click(); // Locate and select 'Sign in'
UiSelector signIn = new UiSelector().text("Sign In"); // If button is available, click
UiObject signInButton = new UiObject(signIn);
if (signInButton.exists()) {
signInButton.click(); // Set the username
new UiObject(new
UiSelector().className("android.widget.EditText").instance(0)).setText("username");
new UiObject(new
UiSelector().className("android.widget.EditText").instance(1)).setText("password");
new UiObject(new UiSelector().className("android.widget.Button").
text("Sign In").instance(0)).click(); // Wait Sign in progress window
getUiDevice().waitForWindowUpdate(null, 2000); // Wait for main window
getUiDevice().waitForWindowUpdate(null, 30000);
}
new UiObject(new UiSelector().description("New tweet")).click(); // Typing text for a tweet
new UiObject(new UiSelector().className("android.widget.LinearLayout").instance(8)).
setText("Awesome #Testdroid!"); // Tweeting!
new UiObject(new UiSelector().text("Tweet")).click();
```

  官方网站：http://developer.android.com/tools/help/uiautomator/index.html

## 3.Espresso

  Espresso是由Google开源的一款最新的Android自动化测试框架，有助于于开发人员和测试人员锤炼出中意的用户界面。 Espresso的API体积小、可预见、简单易学，构建在Android仪表框架的基础上。使用它，能让你快速编写出简洁可靠的Android UI测试。它支持API level 8级（Froyo）、10（Gingerbread），和15（Ice Cream Sandwich）及后续。

  一方面它相当可靠，因为和UI线程是同步的，另一方面又非常之快，因为没有任何睡眠的必要（当某个毫秒，应用程序空转时，运行测试）。不过它同样不支持web视图。

  Espresso的代码示例：

```java
public void testEspresso() {
// Check if view with the text 'Hello.' is shown
onView(withText("Hello.")).check(matches(isDisplayed()));
// R class ID identifier for 'Sign in' - and click it
onView(withId(getInstrumentation().getTargetContext().getResources()
.getIdentifier("com.twitter.android:id/sign_in", null, null))).perform(click());
// R class ID identifier for entering username
onView(withId(getInstrumentation().getTargetContext().getResources()
.getIdentifier("com.twitter.android:id/login_username", null, null))).perform((typeText("username")));
// R class ID identifier for entering password
onView(withId(getInstrumentation().getTargetContext().getResources()
.getIdentifier("com.twitter.android:id/login_password", null, null))).perform((typeText("password")));
// R class ID identifier for clicking log in
onView(withId(getInstrumentation().getTargetContext().getResources()
.getIdentifier("com.twitter.android:id/login_login", null, null))).perform(click());
// Activate the text field to compose a tweet
onView(withId(getInstrumentation().getTargetContext().getResources()
.getIdentifier("com.twitter.android:id/menu_compose_tweet", null, null))).perform(click());
// Type the tweet
onView(withId(getInstrumentation().getTargetContext().getResources()
.getIdentifier("com.twitter.android:id/edit", null, null))).perform((typeText(”#Testdroid")));
// Tweeting!
onView(withId(getInstrumentation().getTargetContext().getResources()
.getIdentifier("com.twitter.android:id/composer_post", null, null))).perform(click());
}
```

  官方网站：https://code.google.com/p/android-test-kit/wiki/Espresso

## 4.Calabash

  Calabash是一款跨平台的自动化测试框架，支持Android和iOS原生和混合的应用程序。Calabash易于理解的语法，使得即使是非 技术人员也可以在这两个移动平台上为app创建和执行自动化验收测试。Calabash的测试描述于Cucumber，然后在运行时转化为 Robotium或Frank。它支持约80种不同的自然语言指令（控制器），并且可以使用Ruby和Java实现新的控制器。

  Calabash的代码示例：

```java
Feature: Login feature
Scenario: As a valid user I can log into my app
I wait for text "Hello"
Then I press view with id "Sign in"
Then I enter text "username" into "login_username"
Then I enter text "password" into "login_password"
Then I wait for activity "HomeTabActivity"
Then I press view with id "menu_compose_tweet"
Then I enter text "Testdroid" into field with id "edit"
Then I press view with id "composer_post"
```

  官方网站：http://calaba.sh/

## 5.Appium

  Appium是一款移动的自动化测试框架（和工具），支持iOS和Android原生和混合的移动Web应用程序。它内部使用的 JSONWireProtocol通过Selenium的WebDriver，来与iOS和Android应用进行交互。它通过 uiautomator（API level 16或更高）和Seledroid（API level 低于16）支持Android，通过UI Automation支持iOS，还有Android和iOS都支持的移动web如Selenium driver。

  Appium的最大优点在于你几乎可以用任意一种编程语言（例如，Java、Objective-C、JavaScript、PHP、Ruby、 Python和C＃等）来编写Appium脚本而不必选择工具，兼容最重要的平台（Android和iOS）而不必安装和配置设备适应测试等等。并且，如 果你熟悉Selenium的话，那么使用Appium用于移动app测试对你而言将是轻而易举的一件事。因为它们使用相同的WebDriver，并且以同 样的方式使用DesiredCapabilities。所以Appium与Selenium在配置应用程序运行时有诸多相似之处。

  Appium的代码示例：

```java
# wait for hello
sleep(3)
textFields = driver.find_elements_by_tag_name('textField')
assertEqual(textFields[0].get_attribute("value"), "Hello")
# click sign-in button
driver.find_elements_by_name('Sign in')[0].click()
# find the text fields again, and enter username and password
textFields = driver.find_elements_by_tag_name('textField')
textFields[0].send_keys("twitter_username")
textFields[1].send_keys("passw0rd")
# click the Login button (the first button in the view)
driver.find_elements_by_tag_name('button')[0].click()
# sleep
sleep(3)
# click the first button with name "Compose"
driver.find_elements_by_name('Compose')[0].click()
# type in the tweet message
driver.find_elements_by_tag_name('textField')[0].send_keys(”#Testdroid is awesome!")
# press the Send button
driver.find_elements_by_name('Send')[0].click()
# exit
driver.quit()
```

  官方网站：http://appium.io/

![android_test_compare_table](http://ihongqiqu.com/imgs/post/android_test_compare_table.png)

## 总结

  以上就是我们列出的5款最棒的测试框架，可用于日常的Android构建，创立和修改。当然，每一种框架都有其优势和缺陷。Appium可以同时测 试你的Android和iOS版本。但如果你是一个忠实的Android开发人员只开发安卓版本的app，那么，使用Robotium就很不错的。 Testdroid Recorder还可为我们在生成测试脚本节省大量的时间和金钱（这是免费的哦！）。因此，好好思考下你的测试需求——功能测试、兼容性测试、UI测试等 等——然后为自己选取最适合和最佳的Android测试框架。

译文链接： http://www.codeceo.com/article/5-android-test-framework.html 

英文原文： [Top 5 Android Testing Frameworks (with Examples)](http://testdroid.com/tech/top-5-android-testing-frameworks-with-examples) 

翻译作者： 码农网 – 小峰



