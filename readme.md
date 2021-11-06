## 河海大学自动健康打卡

​	tips1：本项目使用http请求模拟网页打卡。

​	tips2：本项目仅供学习交流。

### 项目功能

​	1、自动健康打卡。

​	2、打卡成功后发送邮件提醒。

​	3、如果遇到学校服务器卡顿导致打卡失败会自动进行重连(默认重连次数为30次，可自行设置)，直到打卡成功。

### 项目结构

![pic.jpg](/hr/pic.png)

### 具体配置

#### Main

​		`Date date = sdf.parse("2021-11-07 00:01:00");`

​		Main方法只需要修改定时器生效时间即可，如果想每天过零点就生效，就可以将时间改成明天，如果想立即打卡查看效果，就将时间改成过去时间，这样定时器就会立即执行，并且每天在这时候执行。

#### StudentInfo

​		河海大学健康打卡网页上的参数是命名没有实际意义，这里给出参数注释表。

| DATETIME_CYCLE |                           填报时间                           |
| :------------: | :----------------------------------------------------------: |
|   XGH_336526   |                             学号                             |
|    XM_1474     |                             姓名                             |
|  SFZJH_859173  |                           身份证号                           |
| SELECT_941320  |                             学院                             |
| SELECT_459666  |                             年级                             |
| SELECT_814855  |                             专业                             |
| SELECT_525884  |                             班级                             |
| SELECT_125597  |                            宿舍楼                            |
|  TEXT_950231   |                            宿舍号                            |
|  TEXT_937296   |                           手机号码                           |
|   RADIO_6555   |                        您的体温情况？                        |
|  RADIO_535015  |                       您今天是否在校？                       |
|  RADIO_891359  |                        本人健康情况？                        |
|  RADIO_372002  |                       同住人健康情况？                       |
|  RADIO_618691  | 本人及同住人14天内是否有中高风险地区旅居史或接触过中高风险地区人员 |

​	tips：建议去历史填报页面查看一下各项的格式要求，一切以官网为准。

### EmailUtil

​		邮件类中需要配置发送邮件账号的账密，其他的不需要改变。

### HealthReporterUtil

​		`public static final int TRY_MAX=20; //默认最大重连次数`

​		这个类中只需要关注initInfo()方法，在这个方法中需要填写需要打卡学生的打卡信息，只需要创建StudentInfo实体类，然后将相关信息填充完毕即可，接下来的打卡发送邮件操作程序会自动完成。

