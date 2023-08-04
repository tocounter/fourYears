import re
import jieba
import numpy as np
from PIL import Image
from matplotlib import pyplot as plt
from wordcloud import WordCloud

for i in range(2019,2024):
    text = open(str(i)+'年弹幕.csv', encoding='utf-8-sig').read()
    text = text.replace("\n", "").replace("\u3000", "").replace("\u0020", "")
    text_cut = jieba.lcut(text)
    text_cut = "  ".join(text_cut)
    # text_cut = re.sub(r'[^\u4e00-\u9fa5a-zA-Z]+', '', text_cut)
    # text_cut = re.sub(r'\d+', '', text_cut)
    stop_words = open("stopwords_cn.txt", encoding="utf-8").read().split("\n")

    mask = np.array(Image.open("D:\\学习资料\\新建文件夹\\bilibili3.jpeg"))
    wc1 = WordCloud(
        background_color="white",  # 背景为白色
        font_path='F:\\simfang.ttf',  # 使用的字体库:当前字体支持中文
        max_words=200,  # 最大显示的关键词数量
        width=1000,  # 生成词云的宽
        height=860,  # 生成词云的高
        collocations=False,  # 解决关键词重复：是否包括两个词的搭配
        mask=mask,
        stopwords=stop_words, #屏蔽的内容
    )
    wc2 = wc1.generate(text_cut)

    plt.imshow(wc2)
    plt.axis("off")
    plt.savefig(str(i)+'年弹幕词云图.jpg', dpi=600, bbox_inches='tight')
    #plt.show()


