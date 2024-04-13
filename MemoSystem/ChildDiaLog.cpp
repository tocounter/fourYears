// ChildDiaLog.cpp: 实现文件
//

#include "pch.h"
#include "MemoSystem.h"
#include "afxdialogex.h"
#include "ChildDiaLog.h"


// ChildDiaLog 对话框

IMPLEMENT_DYNAMIC(ChildDiaLog, CDialogEx)

ChildDiaLog::ChildDiaLog(CWnd* pParent /*=nullptr*/)
	: CDialogEx(IDD_ChildDlg, pParent)
	, m_id(_T(""))
	, m_lastname(_T(""))
	, m_firstname(_T(""))
	, m_date(_T(""))
	, m_content(_T(""))
{

}

ChildDiaLog::~ChildDiaLog()
{
}

void ChildDiaLog::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Text(pDX, IDC_EDIT1, m_id);
	DDX_Text(pDX, IDC_EDIT2, m_lastname);
	DDX_Text(pDX, IDC_EDIT3, m_firstname);
	DDX_Text(pDX, IDC_EDIT4, m_date);
	DDX_Text(pDX, IDC_EDIT5, m_content);
}


BEGIN_MESSAGE_MAP(ChildDiaLog, CDialogEx)
	ON_BN_CLICKED(IDC_BUTTON1, &ChildDiaLog::OnBnClickedButton1)
	ON_BN_CLICKED(IDC_BUTTON2, &ChildDiaLog::OnBnClickedButton2)
END_MESSAGE_MAP()


// ChildDiaLog 消息处理程序


void ChildDiaLog::OnBnClickedButton1()
{
	UpdateData(TRUE);
	if (m_id == TEXT("") || m_lastname == TEXT("") || m_firstname == TEXT("") || m_date == TEXT("") || m_content == TEXT("")) {
		MessageBox(TEXT("大胆，没输入完全，滚回去重新输入！"), TEXT("提示"));
	}
	else {
		OnOK();
		return;
	}
	// TODO: 在此添加控件通知处理程序代码
}


void ChildDiaLog::OnBnClickedButton2()
{
	// TODO: 在此添加控件通知处理程序代码
	EndDialog(0);
}
