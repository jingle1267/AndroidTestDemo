  上文学习了Android的monkey测试，这次我学习一下monkeyrunner。大家别看这俩兄弟名字相像，但其实是完完全全不同的两个工具，应用在不同的测试领域。

  monkeyrunner工具提供了一个API，使用此API写出的程序可以在Android代码之外控制Android设备和模拟器。通过monkeyrunner，您可以写出一个Python程序去安装一个Android应用程序或测试包，运行它，向它发送模拟击键，截取它的用户界面图片，并将截图存储于工作站上。monkeyrunner工具的主要设计目的是用于测试功能/框架水平上的应用程序和设备，或用于运行单元测试套件，但您当然也可以将其用于其它目的。

## monkeyrunner和monkey的差别

  总的来说，monkey主要应用在压力和可靠性测试上，运行该命令可以随机地向目标程序发送各种模拟键盘事件流，并且可以自己定义发送的次数，以此观察被测应用程序的稳定性和可靠性，应用起来也比较简单，记住那几个命令就行了。而monkeyrunner呢，相比之下会强大一些，它主要可应用于功能测试，回归测试，并且可以自定义测试扩展，灵活性较强，并且测试人员可以完全控制。

## 测试类型

  monkeyrunner的测试类型主要有：

 1. 多设备控制：monkeyrunner API可以跨多个设备或模拟器实施测试套件。您可以在同一时间接上所有的设备或一次启动全部模拟器（或统统一起），依据程序依次连接到每一个，然后运行一个或多个测试。您也可以用程序启动一个配置好的模拟器，运行一个或多个测试，然后关闭模拟器。
 2. 功能测试： monkeyrunner可以为一个应用自动贯彻一次功能测试。您提供按键或触摸事件的输入数值，然后观察输出结果的截屏。
 3. 回归测试：monkeyrunner可以运行某个应用，并将其结果截屏与既定已知正确的结果截屏相比较，以此测试应用的稳定性。
 4. 可扩展的自动化：由于monkeyrunner是一个API工具包，您可以基于Python模块和程序开发一整套系统，以此来控制Android设备。除了使用monkeyrunner API之外，您还可以使用标准的Python os和subprocess模块来调用Android Debug Bridge这样的Android工具。

   monkeyrunner工具使用Jython（使用Java编程语言的一种Python实现）。Jython允许monkeyrunnerAPI与Android框架轻松的进行交互。使用Jython，您可以使用Python语法来获取API中的常量、类以及方法。

 ## 简单例子

   以下为一个简单的monkeyrunner程序，它将会连接到一个设备，创建一个monkeyDevice对象。使用device打开浏览器，并向其发送按键事件。程序接下来会将结果截图，创建一个monkeyImage对象，并使用这个对象截图将保存至.png文件。monkeyrunner01.py代码如下：

```python
#文件保存为utf8编码 且需要添加下一行代码
#coding=utf-8
#导入我们需要用到的包和类并且起别名
import sys
from com.android.monkeyrunner import monkeyRunner as mr
from com.android.monkeyrunner import monkeyDevice as md
from com.android.monkeyrunner import monkeyImage as mi
 
#connect device 连接设备
#第一个参数为等待连接设备时间
#第二个参数为具体连接的设备
#device = mr.waitForConnection(1.0,'216e9294')
device = mr.waitForConnection();
if not device:
    print >> sys.stderr,"fail"
    sys.exit(1)
#在emulator上会弹出消息提示
#mr.alert("hello")
#定义要启动的Activity
componentName='com.android.browser/.BrowserActivity'
#componentName='com.ihongqiqu.Identify/.activity.MainActivity'
#启动特定的Activity
device.startActivity(component=componentName)
mr.sleep(1.0)
#do someting 进行我们的操作
#输入 helloworld
device.type('10086')
#输入回车
device.press('KEYCODE_ENTER')
device.press('KEYCODE_MENU')

#return keyboard
#device.press('KEYCODE_BACK')
#takeSnapshot截图
mr.sleep(1.0)
result = device.takeSnapshot()
 
#save to file 保存到文件
result.writeToFile('./shot1.png','png');
```

  运行上面代码的命令是：

```
monkeyrunner monkeyrunner01.py
```

## monkeyrunner API

 * [monkeyRunner](http://developer.android.com/guide/developing/tools/monkeyRunner.html) ：一个为monkeyrunner程序提供工具方法的类。这个类提供了用于连接monkeyrunner至设备或模拟器的方法。它还提供了用于创建一个monkeyrunner程序的用户界面以及显示内置帮助的方法。        
    
 * [monkeyDevice](http://developer.android.com/guide/developing/tools/monkeyDevice.html) ：表示一个设备或模拟器。这个类提供了安装和卸载程序包、启动一个活动以及发送键盘或触摸事件到应用程序的方法。您也可以用这个类来运行测试包。        
    
 * [monkeyImage](http://developer.android.com/guide/developing/tools/monkeyImage.html) ：表示一个截图对象。这个类提供了截图、将位图转换成各种格式、比较两个monkeyImage对象以及写图像到文件的方法。  

   在python程序中，您将以Python模块的形式使用这些类。monkeyrunner工具不会自动导入这些模块。您必须使用类似如下的from语句：

```python
from com.android.monkeyrunner import XXX
```

  其中，XXX为您想要导入的类名。您可以在一个from语句中导入超过一个模块，其间以逗号分隔。    

