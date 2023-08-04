import json
import urllib.request
import pandas as pd
import os

#获取request
def create_resquest(period):
    url='https://api.bilibili.com/x/web-interface/popular/series/one?'
    headers={
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36'
    }
    data={
        'number':period
    }
    a=urllib.parse.urlencode(data)
    url=url+a
    request=urllib.request.Request(url=url,headers=headers)
    return request

#获取表单内容
def get_content(request):
    response=urllib.request.urlopen(request)
    content=response.read().decode('utf-8')
    return content


def analysis_data(content):
    lists = []
    con = json.loads(content)
    data = con.get('data')
    config=data.get('config')
    list = data.get('list')
    for i in list:
        message = []

        stat = i.get('stat')  # 视频数据列表
        #owner = i.get('owner')# 视频拥有者信息
        #name=owner.get('name').replace('_','').strip()#视频作者
        year=config.get('name')
        #title=i.get('title').replace(' ','').strip()#视频标题词云图提取处理

        message.append(year[0:4])
        #message.append(i.get('title'))#视频题目
        #message.append(name)#视频作者
        message.append(i.get('tname'))#视频主题
        message.append(stat.get('view'))#视频播放量
        # message.append(stat.get('like'))#视频点赞量
        # message.append(stat.get('coin'))#视频硬币量
        # message.append(stat.get('favorite'))#视频收藏数

        lists.append(message)
    return lists

def save_data(lists):
    exist=os.path.getsize('all_hotDATA.csv')
    headLine = ['年份','主题', '播放量']
    if exist:
        df = pd.DataFrame(lists)
        df.to_csv('all_hotDATA.csv', mode='a', index=False, encoding='utf-8-sig', header=None)
    else:
        df=pd.DataFrame(lists,columns=headLine)
        df.to_csv('all_hotDATA.csv', mode='a', index=False, encoding='utf-8-sig', errors='ignore')

if __name__=='__main__':
    with open("all_hotDATA.csv", "w"):
        pass
    start_period=int(input("请输入起始周期数\n"))
    end_period = int(input("请输入末尾周期数\n"))
    for i in range(start_period,end_period+1):
        request=create_resquest(i)
        content=get_content(request)
        lists=analysis_data(content)
        save_data(lists)