#pragma once
#include "afxdialogex.h"


// ChildDiaLog 对话框

class ChildDiaLog : public CDialogEx
{
	DECLARE_DYNAMIC(ChildDiaLog)

public:
	ChildDiaLog(CWnd* pParent = nullptr);   // 标准构造函数
	virtual ~ChildDiaLog();

// 对话框数据
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_ChildDlg };
#endif

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
public:
	CString m_id;
	CString m_lastname;
	CString m_firstname;
	CString m_date;
	CString m_content;
	afx_msg void OnBnClickedButton1();
	afx_msg void OnBnClickedButton2();
};
