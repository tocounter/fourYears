
// MemoSystemDlg.cpp: 实现文件
//

#include "pch.h"
#include "framework.h"
#include "MemoSystem.h"
#include "MemoSystemDlg.h"
#include "afxdialogex.h"
#include "ChildDiaLog.h"
#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// 用于应用程序“关于”菜单项的 CAboutDlg 对话框

class CAboutDlg : public CDialogEx
{
public:
	CAboutDlg();

// 对话框数据
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_ABOUTBOX };
#endif

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

// 实现
protected:
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialogEx(IDD_ABOUTBOX)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialogEx)
END_MESSAGE_MAP()


// CMemoSystemDlg 对话框



CMemoSystemDlg::CMemoSystemDlg(CWnd* pParent /*=nullptr*/)
	: CDialogEx(IDD_MEMOSYSTEM_DIALOG, pParent)
	, m_find(_T(""))
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CMemoSystemDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_LIST1, m_list);
	DDX_Text(pDX, IDC_EDIT1, m_find);
}

BEGIN_MESSAGE_MAP(CMemoSystemDlg, CDialogEx)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDC_BUTTON1, &CMemoSystemDlg::OnBnClickedButton1)
	ON_BN_CLICKED(IDC_BUTTON3, &CMemoSystemDlg::OnBnClickedButton3)
	ON_BN_CLICKED(IDC_BUTTON4, &CMemoSystemDlg::OnBnClickedButton4)
	ON_BN_CLICKED(IDC_BUTTON5, &CMemoSystemDlg::OnBnClickedButton5)
	ON_BN_CLICKED(IDC_BUTTON6, &CMemoSystemDlg::OnBnClickedButton6)
	ON_BN_CLICKED(IDC_BUTTON2, &CMemoSystemDlg::OnBnClickedButton2)
	ON_BN_CLICKED(IDC_BUTTON7, &CMemoSystemDlg::OnBnClickedButton7)
END_MESSAGE_MAP()


// CMemoSystemDlg 消息处理程序

BOOL CMemoSystemDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// 将“关于...”菜单项添加到系统菜单中。

	// IDM_ABOUTBOX 必须在系统命令范围内。
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != nullptr)
	{
		BOOL bNameValid;
		CString strAboutMenu;
		bNameValid = strAboutMenu.LoadString(IDS_ABOUTBOX);
		ASSERT(bNameValid);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// 设置此对话框的图标。  当应用程序主窗口不是对话框时，框架将自动
	//  执行此操作
	SetIcon(m_hIcon, TRUE);			// 设置大图标
	SetIcon(m_hIcon, FALSE);		// 设置小图标
	//设置ListCtrl风格
	DWORD dwStyle = m_list.GetExtendedStyle();
	dwStyle |= LVS_EX_FULLROWSELECT| LVS_EX_GRIDLINES;	//选中某行使整行高亮（只适用与report风格的ListCtrl） 
		   //网格线（只适用与report风格的ListCtrl） 
	m_list.SetExtendedStyle(dwStyle); //设置扩展风格 
	// TODO: 在此添加额外的初始化代码
	m_list.InsertColumn(0, TEXT("编号"), LVCFMT_LEFT, 100);
	m_list.InsertColumn(1, TEXT("姓"), LVCFMT_LEFT, 100);
	m_list.InsertColumn(2, TEXT("名"), LVCFMT_LEFT, 100);
	m_list.InsertColumn(3, TEXT("日期"), LVCFMT_LEFT, 100);
	m_list.InsertColumn(4, TEXT("内容"), LVCFMT_LEFT, 150);
	IsOpen = false;
	return TRUE;  // 除非将焦点设置到控件，否则返回 TRUE
}

void CMemoSystemDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialogEx::OnSysCommand(nID, lParam);
	}
}

// 如果向对话框添加最小化按钮，则需要下面的代码
//  来绘制该图标。  对于使用文档/视图模型的 MFC 应用程序，
//  这将由框架自动完成。

void CMemoSystemDlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this); // 用于绘制的设备上下文

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// 使图标在工作区矩形中居中
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// 绘制图标
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialogEx::OnPaint();
	}
}

//当用户拖动最小化窗口时系统调用此函数取得光标
//显示。
HCURSOR CMemoSystemDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}






void CMemoSystemDlg::OnBnClickedButton1()
{
	// TODO: 在此添加控件通知处理程序代码 
	// 设置过滤器   
	TCHAR szFilter[] = _T("文本文件(*.txt)|*.txt|所有文件(*.*)|*.*||");
	// 构造打开文件对话框   
	CFileDialog fileDlg(TRUE, _T("txt"), NULL, 0, szFilter, this);
	
	// 显示打开文件对话框   
	if (IDOK == fileDlg.DoModal())
	{
		// 如果点击了文件对话框上的“打开”按钮，则将选择的文件路径显示到编辑框里   
		strFilePath = fileDlg.GetPathName();
		DataInterface.Open(strFilePath);
		IsOpen = true;
		updateList();
	}
}

void CMemoSystemDlg::updateList()
{
	m_list.DeleteAllItems();
	CString str;
	for (int i = 0; i < DataInterface.info.size(); i++) {
		str.Format(TEXT("%d"), DataInterface.info[i].m_id);
		m_list.InsertItem(i, str);
		str.Format(TEXT("%s"), DataInterface.info[i].m_lastname.c_str());
		m_list.SetItemText(i,1, str);
		str.Format(TEXT("%s"), DataInterface.info[i].m_firstname.c_str());
		m_list.SetItemText(i,2,str);
		str.Format(TEXT("%s"), DataInterface.info[i].m_date.c_str());
		m_list.SetItemText(i,3, str);
		str.Format(TEXT("%s"), DataInterface.info[i].m_content.c_str());
		m_list.SetItemText(i,4, str);
	}
}


void CMemoSystemDlg::OnBnClickedButton3()
{
	// TODO: 在此添加控件通知处理程序代码
	if (IsOpen) {
		ChildDiaLog dlg;
		if (IDOK == dlg.DoModal()) {
			Cinfo info(atoi(dlg.m_id), dlg.m_lastname.GetBuffer(), dlg.m_firstname.GetBuffer(), dlg.m_date.GetBuffer(), dlg.m_content.GetBuffer());
			DataInterface.Add(info);
			updateList();
		}
	}
	else {
		MessageBox(TEXT("还未打开文件！"), TEXT("提示"));
	}
	
}


void CMemoSystemDlg::OnBnClickedButton4()
{
	if (IsOpen) {
		int index = m_list.GetSelectionMark();
		if (index > -1) {
			UINT i;
			i = MessageBox(_T("确定要删除这条记录吗？"), _T("提示"), MB_YESNO | MB_ICONQUESTION);
			if (i == IDYES) {
				DataInterface.Del(index);
				MessageBox(TEXT("删除成功！"), TEXT("提示"));
				updateList();
			}
			else {
				return;
			}
		}
		else {
			MessageBox(TEXT("请先点击列表选择要删除的记录！"), TEXT("提示"));
		}
		// TODO: 在此添加控件通知处理程序代码
	}
	else {
		MessageBox(TEXT("还未打开文件！"), TEXT("提示"));
	}
}


void CMemoSystemDlg::OnBnClickedButton5()
{
	// TODO: 在此添加控件通知处理程序代码
	int index = m_list.GetSelectionMark();
	if (index > -1) {
		ChildDiaLog dlg;
		CString str;
		str.Format(TEXT("%d"), DataInterface.info[index].m_id);
		dlg.m_id = str;
		str.Format(TEXT("%s"), DataInterface.info[index].m_lastname.c_str());
		dlg.m_lastname = str;
		str.Format(TEXT("%s"), DataInterface.info[index].m_firstname.c_str());
		dlg.m_firstname = str;
		str.Format(TEXT("%s"), DataInterface.info[index].m_date.c_str());
		dlg.m_date = str;
		str.Format(TEXT("%s"), DataInterface.info[index].m_content.c_str());
		dlg.m_content = str;
		if (IDOK == dlg.DoModal()) {
			Cinfo info(atoi(dlg.m_id), dlg.m_lastname.GetBuffer(), dlg.m_firstname.GetBuffer(), dlg.m_date.GetBuffer(), dlg.m_content.GetBuffer());
			DataInterface.Amend(index,info);
			updateList();
		}
		
	}
	else {
		MessageBox(TEXT("请先点击列表选择要修改的记录！"), TEXT("提示"));
	}
}


void CMemoSystemDlg::OnBnClickedButton6()
{
	// TODO: 在此添加控件通知处理程序代码
	UpdateData(TRUE);
	Cinfo findInfo = DataInterface.find(atoi(m_find));
	if (findInfo.m_id == -1) {
		MessageBox(TEXT("查无记录！"), TEXT("提示"));
	}
	else {
		CString str;
		str.Format(TEXT("查找成功！\n\n编号：%d\n姓氏：%s\n名字：%s\n日期：%s\n内容：%s"),
			findInfo.m_id, findInfo.m_lastname.c_str(), findInfo.m_firstname.c_str(), findInfo.m_date.c_str(), findInfo.m_content.c_str());
		MessageBox(str, TEXT("提示"));
	}
}


void CMemoSystemDlg::OnBnClickedButton2()
{
	// TODO: 在此添加控件通知处理程序代码
	if (DataInterface.save(strFilePath)) {
		MessageBox(TEXT("已保存！"), TEXT("提示"));
	}
	else {
		MessageBox(TEXT("保存失败！"), TEXT("提示"));
	}
}


void CMemoSystemDlg::OnBnClickedButton7()
{
	// TODO: 在此添加控件通知处理程序代码
	UINT i;
	i = MessageBox(_T("确定要退出吗？"), _T("提示"), MB_YESNO | MB_ICONQUESTION);
	if (i == IDYES) {
		exit(0);
	}
	else {
		return;
	}
}
