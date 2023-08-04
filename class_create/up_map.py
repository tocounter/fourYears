import pandas as pd
from pyecharts.charts import Map
from pyecharts import options as opts
from pyecharts.globals import ThemeType

data={'黑龙江省': 16, '北京市': 142, '上海市': 326, '福建省': 29, '广西壮族自治区': 21, '辽宁省': 33, '山东省': 54, '广东省': 192, '江苏省': 85, '浙江省': 122, '河北省': 32, '河南省': 17, '美国': 15, '韩国': 7, '陕西省': 20, '四川省': 61, '湖南省': 40, '湖北省': 23, '云南省': 15, '重庆市': 27, '天津市': 29, '卡塔尔': 2, '山西省': 4, '海南省': 10, '内蒙古自治区': 11, '贵州省': 11, '吉林省': 15, '安徽省': 18, '西班牙': 2, '意大利': 1, '加拿大': 5, '甘肃省': 3, '新西兰': 1, '江西省': 9, '德国': 1, '日本': 10, '新疆维吾尔自治区': 11, '""': 4, '中国香港特别行政区': 3, '未知': 2, '新加坡': 1, '法国': 3, '英国': 3, '西藏自治区': 1, '芬兰': 2, '几内亚': 1, '澳大利亚': 1, '台湾省': 3, '宁夏回族自治区': 1}
#data={'黑龙江省': 16, '北京市': 142, '上海市': 326, '福建省': 29, '广西省': 21, '辽宁省': 33, '山东省': 54, '广东省': 192, '江苏省': 85, '浙江省': 122, '河北省': 32, '河南省': 17, '美国': 15, '韩国': 7, '陕西省': 20, '四川省': 61, '湖南省': 40, '湖北省': 23, '云南省': 15, '重庆省': 27, '天津省': 29, '卡塔尔': 2, '山西省': 4, '海南省': 10, '内蒙古自治区': 11, '贵州省': 11, '吉林省': 15, '安徽省': 18, '西班牙': 2, '意大利': 1, '加拿大': 5, '甘肃省': 3, '新西兰': 1, '江西省': 9, '德国': 1, '日本': 10, '新疆维吾尔自治区': 11, '""省': 4, '香港特别行政区': 3, '未知': 2, '新加坡': 1, '法国': 3, '英国': 3, '西藏自治区': 1, '芬兰': 2, '几内亚': 1, '澳大利亚': 1, '台湾省': 3, '宁夏回族自治区': 1}
# df = pd.read_csv('2023_ip.csv', encoding='utf-8')
# data = df.value_counts()

# 删除数据中非中国省份的up主数量
for province in list(data.keys()):
    if province not in ['黑龙江省', '北京市', '上海市', '福建省', '广西壮族自治区', '辽宁省', '山东省', '广东省', '江苏省', '浙江省', '河北省', '河南省', '陕西省', '四川省', '湖南省', '湖北省', '云南省', '重庆市', '天津市', '山西省', '海南省', '内蒙古自治区', '贵州省', '吉林省', '安徽省', '甘肃省', '江西省', '新疆维吾尔自治区', '宁夏回族自治区', '西藏自治区','香港特别行政区','澳门特别行政区','台湾省']:
        del data[province]

# 将数据转换为DataFrame格式
data_df = pd.DataFrame(list(data.items()), columns=['province', 'num_ups'])
data_df = data_df.sort_values('num_ups', ascending=False)
#datas = [(i, int(j)) for i, j in zip(data.index, data.values)]
# 绘制地图
print(type(data_df))
map_data = [(province, num_ups) for province, num_ups in zip(data_df['province'], data_df['num_ups'])]
print(type(map_data))
print(map_data)
map_china = Map(init_opts=opts.InitOpts(theme=ThemeType.PURPLE_PASSION))
#map_china.set_series_opts(label_opts=opts.LabelOpts(is_show=False))   # 不显示label
map_china.set_global_opts(
    legend_opts=opts.LegendOpts(is_show=True),  # 是否显示
    title_opts=opts.TitleOpts(title='2023年至今B站每周热门视频中国各省份up主的占比',
                               pos_left='200',
                               pos_top='50'),
    visualmap_opts=opts.VisualMapOpts(
        #max_=max(data_df['num_ups']),
        is_show=True,  # 是否显示
        is_piecewise=True,  # 是否分段
        pieces=[
            {"min": 0, "max": 9, "lable": "0~49人", "color": "#CCFFFF"},
            {"min": 10, "max":19, "lable": "50~99人", "color": "#FFFF99"},
            {"min": 20, "max": 49, "lable": "100~149人", "color": "#FF9966"},
            {"min": 50, "max": 99, "lable": "150~199人", "color": "#FF6666"},
            {"min": 100, "max": 199, "lable": "200~249人", "color": "#CC3333"},
            {"min": 200, "lable": "200+", "color": "#990033"},
            ]
    )
)
map_china.add('各省份人数', map_data, maptype='china')
map_china.render('china_ups_map.html')
#map_china.render_notebook()
