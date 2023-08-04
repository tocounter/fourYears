import csv
import re
import urllib.parse
import time

import pandas as pd
import requests
from bs4 import BeautifulSoup
from lxml import etree

def create_response(cid):
    url='https://comment.bilibili.com/'
    headers={
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36'
    }
    # data={
    #     'oid':cid
    # }
    #a=urllib.parse.urlencode(data)
    url=url+str(cid)+'.xml'
    html=requests.get(url=url,headers=headers).content
    return html
def get_content(html):
    # content=etree.HTML(response.text)
    # barrage=content.xpath('//d/text()')
    # return barrage
    html_data = str(html, 'utf-8')
    bs4 = BeautifulSoup(html_data, 'xml')
    results = bs4.find_all('d')
    comments = [comment.text for comment in results]
    comments_dict = {'comments': comments}
    return comments_dict


def save_barrage(year,lists):
    #with open(str(year)+'年弹幕.csv','a',encoding='utf-8-sig') as fp:
# # 1:创建writer对象
    # writer = csv.writer(fp)
    # # 3:遍历列表，将每一行的数据写入csv
    # for p in lists:
    #     writer.writerow(p)
    df = pd.DataFrame(lists)
    df.to_csv(str(year)+'年弹幕.csv', mode='a', index=False, encoding='utf-8-sig', header=None)

if __name__=='__main__':
    f = open("everyyear_cid.csv", "r", encoding="utf-8-sig")
    data_lines = f.readlines()
    f.close()
    data_lines.pop(0)

    for line in data_lines:
        year=int(line.split(',')[0])
        cid=int(line.split(',')[1])
        save_barrage(year,get_content(create_response(cid)))
