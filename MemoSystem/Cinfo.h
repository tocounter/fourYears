#pragma once
class Cinfo
{
public:
	Cinfo();
	Cinfo(int id, string lastname, string firstname, string date, string content);
	void load(ifstream& in);
	void save(ofstream& out);
	int m_id;
	string m_lastname;
	string m_firstname;
	string m_date;
	string m_content;
};

