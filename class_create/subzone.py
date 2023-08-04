#制作主题分区饼状图
import pandas as pd
import matplotlib.pyplot as plt

# 读取表格文件
df = pd.read_csv('2023_hotDATA.csv')

# 统计每个种类出现的次数
count = df['主题'].value_counts()

# 将统计结果转换为DataFrame格式，并添加列名
count_df = pd.DataFrame(count).reset_index()
count_df.columns = ['主题', '数量']
count_df = count_df[:10]
# 绘制饼状图

#饼状图避免乱码
from pylab import mpl
mpl.rcParams['font.sans-serif'] = ['FangSong'] # 指定默认字体
mpl.rcParams['axes.unicode_minus'] = False # 解决保存图像是负号'-'显示为方块的问题

plt.figure(figsize=(18,10))
plt.pie(count_df['数量'], labels=count_df['主题'], autopct='%1.1f%%',  startangle=90,pctdistance=0.9)
plt.axis('equal')
plt.title('2023年初至今B站每周必看不同主题出现次数占比')
plt.legend()
plt.savefig('2023_主题占比饼状图.jpg')