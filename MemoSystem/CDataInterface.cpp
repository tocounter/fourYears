#include "pch.h"
#include "CDataInterface.h"

bool CDataInterface::Open(CString FilePath)
{
	ifstream in(FilePath, ios::in);
	if (in.is_open()) {
		int num;//记录多少条数据
		in >> num;
		for (int i = 0; i < num; i++) {
			Cinfo minfo;
			minfo.load(in);
			info.push_back(minfo);
		}
		return true;
	}
	return false;
}

void CDataInterface::Add(Cinfo minfo)
{
	info.push_back(minfo);
}

void CDataInterface::Del(int index)
{
	info.erase(info.begin() + index);
}
void CDataInterface::Amend(int index, Cinfo minfo)
{
	info[index] = minfo;
}

bool CDataInterface::save(CString FilePath)
{
	ofstream out(FilePath,ios::out);
	if (out.is_open()) {
		out << info.size() << endl;
		for (int i = 0; i < info.size(); i++) {
			info[i].save(out);
		}
		out.close();
		return true;
	}
	return false;
}

Cinfo CDataInterface::find(int id)
{
	for (int i = 0; i < info.size(); i++) {
		if( info[i].m_id == id){
			return info[i];
		}
		
	}
	return Cinfo(-1,TEXT(""), TEXT(""), TEXT(""), TEXT(""));
}
