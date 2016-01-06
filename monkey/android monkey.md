
  作为一个Android开发者，不懂的自动化测试是十分可耻的。此文主要介绍Android平台下的Monkey测试。

## Monkey测试介绍

  Monkey测试是Android平台自动化测试的一种手段，通过Monkey程序模拟用户触摸屏幕、滑动Trackball、按键等操作来对设备上的程序进行压力测试，检测程序多久的时间会发生异常。

## Monkey测试特点

Monkey测试的特点主要有以下几点：
 * 可对MonkeyTest的对象，事件数量，类型，频率等进行设置。
 * Monky测试使用的事件流数据流是随机的，不能进行自定义。
 * 测试的对象仅为应用程序包，有一定的局限性。

## Monkey命令

### 常规

** -help ** 

作用：列出简单的用法
例：adb shell monkey -help
 
** -v **

作用：命令行上的每一个-v都将增加反馈信息的详细级别。
Level0（默认），除了启动、测试完成和最终结果外只提供较少的信息。
Level1，提供了较为详细的测试信息，如逐个发送到Activity的事件信息。
Level2，提供了更多的设置信息，如测试中选中或未选中的Activity信息。
例： adb shell monkey -v 10
    adb shell monkey -v -v 10
    adb shell monkey -v -v -v 10

### 事件

** -s <seed> **

作用：伪随机数生成器的seed值。如果用相同的seed值再次运行monkey，将生成相同的事件序列。
例：adb shell monkey -s 12345 -v 10

** --throttle <milliseconds> **

作用：在事件之间插入固定的时间（毫秒）延迟，你可以使用这个设置来减缓Monkey的运行速度，如果你不指定这个参数，则事件之间将没有延迟，事件将以最快的速度生成。
例：adb shell monkey --throttle 300 -v 10

注：常用参数，一般设置为300毫秒，原因是实际用户操作的最快300毫秒左右一个动作事件，所以此处一般设置为300毫秒。

** --pct-touch <percent> **

作用：调整触摸事件的百分比。（触摸事件是指在屏幕中的一个down-up事件，即在屏幕某处按下并抬起的操作）
例：adb shell monkey --pct-touch 100 -v 10

注：常用参数，此参数设置要适应当前被测应用程序的操作，比如一个应用80%的操作都是触摸，那就可以将此参数的百分比设置成相应较高的百分比。

** --pct-motion <percent> **

作用：调整motion事件百分比。（motion事件是由屏幕上某处一个down事件、一系列伪随机的移动事件和一个up事件组成）
例：adb shell monkey --pct-motion 100 -v 10

注：常用参数，需注意的是移动事件是直线滑动，下面的trackball移动包含曲线移动。

** --pct-trackball <percent> **

作用：调整滚动球事件百分比。（滚动球事件由一个或多个随机的移动事件组成，有时会伴随着点击事件）
例：adb shell monkey --pct-trackball 100 -v 10

注：不常使用参数，现在手机几乎没有滚动球，但滚动球事件中包含曲线滑动事件，在被测程序需要曲线滑动时可以选用此参数。

** --pct-nav <percent> **

作用：调整基本的导航事件百分比。（导航事件由方向输入设备的上下左右按键所触发的事件组成）
例：adb shell monkey --pct-nav 100 -v 10
注：不常用操作。

** --pct-majornav <percent> **

作用：调整主要导航事件的百分比。（这些导航事件通常会导致UI界面中的动作事件，如5-way键盘的中间键，回退按键、菜单按键）
例：adb shell monkey --pct-majornav 100 -v 10

** --pct-syskeys <percent> **

作用：调整系统事件百分比。（这些按键通常由系统保留使用，如Home、Back、Start Call、End Call、音量调节）
例：adb shell monkey --pct-syskeys 100 -v 10

** --pct-appswitch <percent> **

作用：调整Activity启动的百分比。（在随机的时间间隔中，Monkey将执行一个startActivity()调用，作为最大程度覆盖被测包中全部Activity的一种方法）
例：adb shell monkey --pct-appswitch 100 -v 5

** --pct-anyevent **

作用：调整其他事件的百分比。（这包含所有其他事件，如按键、其他在设备上不常用的按钮等）
例：adb shell monkey --pct-anyevent 100 -v 5

### 约束条件

** -p <allowed-package-name> **

作用：如果你指定一个或多个包，Monkey将只允许访问这些包中的Activity。如果你的应用程序需要访问这些包(如选择联系人)以外的Activity，你需要指定这些包。如果你不指定任何包，Monkey将允许系统启动所有包的Activity。指定多个包，使用多个-p，一个-p后面接一个包名。
例：adb shell monkey -p com.android.browser -v 10

** -c <main-category> **

作用：如果你指定一个或多个类别，Monkey将只允许系统启动这些指定类别中列出的Activity。如果你不指定任何类别，Monkey将选择谢列类别中列出的Activity，Intent.CATEGORY_LAUNCHER和Intent.CATEGORY_MONKEY。指定多个类别使用多个-c，每个-c指定一个类别。
例：adb shell monkey -p com.paipai.ershou -v 10 -c

** --dbg-no-events **

作用：设置此选项，Monkey将执行初始启动，进入一个测试Activity，并不会在进一步生成事件。为了得到最佳结果，结合参数-v，一个或多个包的约束，以及一个保持Monkey运行30秒或更长时间的非零值，从而提供了一个可以监视应用程序所调用的包之间转换的环境。

** --hprof **

作用：设置此选项，将在Monkey生成事件序列前后生成profilling报告。在data/misc路径下生成大文件（~5Mb），所以要小心使用。

** --ignore-crashes **

作用：通常，应用发生崩溃或异常时Monkey会停止运行。如果设置此项，Monkey将继续发送事件给系统，直到事件计数完成。

** --ignore-security-exception **

作用：通常，当程序发生许可错误（例如启动一些需要许可的Activity）导致的异常时，Monkey将停止运行。设置此项，Monkey将继续发送事件给系统，直到事件计数完成。

** --kill-process-after-error **

作用：通常，当Monkey由于一个错误而停止时，出错的应用程序将继续处于运行状态。设置此项，将会通知系统停止发生错误的进程。注意，正常（成功）的结束，并没有停止启动的进程，设备只是在结束事件之后简单的保持在最后的状态。

** --monitor-native-crashes **

作用：监视并报告Andorid系统中本地代码的崩溃事件。如果设置--kill-process-after-error，系统将停止运行。

** --wait-dbg **

作用：停止执行中的Monkey，直到有调试器和它相连接。


