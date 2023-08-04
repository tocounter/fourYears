from pyecharts.charts import Bar, Timeline
from pyecharts.options import *
from pyecharts.globals import ThemeType

#文件和数据的初始处理
f = open("all_hotDATA.csv", "r", encoding="utf-8-sig")
data_lines = f.readlines()
f.close()
data_lines.pop(0)

Bpart={'番剧':['连载动画','新番时间表', '完结动画', '番剧索引', '番剧资讯', '官方延伸'],
       '国创':['国产动画', '国创资讯', '国产原创相关', '新番时间表', '布袋戏', '国产动画索引', '动态漫·广播剧'],
       '动画':['MAD·AMV',  '特摄',  'MMD·3D', '动漫杂谈',  '短片·手书·配音', '动画综合','手办·模玩','动画d'],
       '游戏':['单机游戏', '桌游棋魂', '游戏赛事', '电子竞技', 'GMV', '手机游戏', '音游', '网络游戏', 'Mugen'],
       '鬼畜':['鬼畜调教', '教程演示', '音MAD', '人力VOCALOID', '鬼畜剧场'],
       '音乐':['原创音乐', '音乐现场', '音乐综合', '翻唱',  'MV', '说唱', '演奏', '乐评盘点', 'VOCALOID UTAU', '音乐教学'],
       '舞蹈':['宅舞', '手势·网红舞', '街舞', '舞蹈综合', '明星舞蹈', '舞蹈教程', '国风舞蹈'],
       '影视':['影视杂谈', '影视剪辑', '小剧场', '预告·资讯'],
       '娱乐':['综艺', '娱乐杂谈', '粉丝创作', '明星综合'],
       '知识':['科普学习', '校园学习', '社科·法律·心理', '职业职场', '人文历史', '设计·创意', '财经商业','野生技能社会'],
       '科技':['数码',  '极客DIY',  '软件应用',  '计算机技术',  '科工机械'],
       '资讯':['热点',  '环球',  '社会',  '综合'],
       '美食':['美食制作',  '美食记录',  '美食侦探',  '美食评测',  '田园美食'],
       '生活':['搞笑','亲子',  '出行',  '三农',  '家居房产',  '手工',  '绘画',  '日常'],
       '动物圈':['喵星人',  '汪星人',  '小宠异宠',  '野生动物',  '动物二创',  '动物综合'],
       '汽车':['赛车',  '改装玩车',  '新能源车',  '房车', '摩托车', '购车攻略', '汽车生活'],
       '时尚':['美妆护肤',  '仿妆cos',  '穿搭',  '时尚潮流'],
       '公开课':['学科课程', '硬核技能',  '考试考证'],
       '运动':['篮球',  '足球',  '健身',  '竞技体育',  '运动文化',  '运动综合'],
       }


data_dict = {}
for line in data_lines:
    year = int(line.split(",")[0])  # 年份
    type = line.split(",")[1]  # 各个主题
    #主题转分区
    for key,value in Bpart.items():
        if type in value:
            type=key

    playback = int(line.split(",")[2])# 播放量
    if year not in data_dict:
        data_dict[year] = [[type, playback]]
    else:
        flag = False
        for item in data_dict[year]:
            if item[0] == type:
                item[1] += playback
                flag = True
                break
        if not flag:
            data_dict[year].append([type, playback])
    #异常处理
    try:
        data_dict[year].append([type, playback])
    except KeyError:
        data_dict[year] = []
        data_dict[year].append([type, playback])

# 排序年份
sorted_year_list = sorted(data_dict.keys())
#创建时间线对象
timeline = Timeline({"theme": ThemeType.INFOGRAPHIC})
#自定义xy轴数据
for year in sorted_year_list:
    data_dict[year].sort(key=lambda element: element[1], reverse=True)
    # 取出本年份前八名的分区
    year_data = data_dict[year][:8]
    x_data = []
    y_data = []
    for type_proportion in year_data:
        x_data.append(type_proportion[0])  # x轴添加分区
        y_data.append(type_proportion[1] / 1000000)  # y轴添加播放量数据,单位为百万元

    # 构建柱状图
    bar = Bar()
    x_data.reverse()
    y_data.reverse()
    bar.add_xaxis(x_data)
    bar.add_yaxis("播放量/百万", y_data, label_opts=LabelOpts(position="right"))
    # 反转x轴，y轴
    bar.reversal_axis()
    # 设置每一年的图表标题
    bar.set_global_opts(
        title_opts=TitleOpts(title=f"{year}年B站每周必看分区前八播放量")
    )
    # 创建时间线对象
    timeline.add(bar, str(year))

# 设置时间为自动播放
timeline.add_schema(
    play_interval=1000,  # 时间间隔
    is_timeline_show=True,  # 是否显示时间
    is_loop_play=True,  # 是否循环
    is_auto_play=True  # 是否自动播放
)
# 绘图
timeline.render("2019-2023年B站每周必看分区播放量排行榜.html")