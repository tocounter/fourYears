#pragma once
#include "Cinfo.h"
class CDataInterface
{
public:
	bool Open(CString FilePath);
	void Add(Cinfo minfo);
	void Del(int index);
	void Amend(int index, Cinfo minfo);
	bool save(CString FilePath);
	Cinfo find(int id);
	vector<Cinfo> info;
};

