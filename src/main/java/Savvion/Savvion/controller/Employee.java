package Savvion.Savvion.controller;

public class Employee {
    private String TicketNumber;
    private String AppSupportStatus;
    private String AppSupportCallClosedDate;
    private String AppSupportRemark;
    private String Category;
    private String strFileName;
    private String strAppSupportPerformer;
    // Additional field for SOAP response data
	public String getTicketNumber() {
		return TicketNumber;
	}
	@Override
	public String toString() {
		return "Employee [TicketNumber=" + TicketNumber + ", AppSupportStatus=" + AppSupportStatus
				+ ", AppSupportCallClosedDate=" + AppSupportCallClosedDate + ", AppSupportRemark=" + AppSupportRemark
				+ ", Category=" + Category + ", strFileName=" + strFileName + ", strAppSupportPerformer="
				+ strAppSupportPerformer + "]";
	}
	public void setTicketNumber(String ticketNumber) {
		TicketNumber = ticketNumber;
	}
	public String getAppSupportStatus() {
		return AppSupportStatus;
	}
	public void setAppSupportStatus(String appSupportStatus) {
		AppSupportStatus = appSupportStatus;
	}
	public String getAppSupportCallClosedDate() {
		return AppSupportCallClosedDate;
	}
	public void setAppSupportCallClosedDate(String appSupportCallClosedDate) {
		AppSupportCallClosedDate = appSupportCallClosedDate;
	}
	public String getAppSupportRemark() {
		return AppSupportRemark;
	}
	public void setAppSupportRemark(String appSupportRemark) {
		AppSupportRemark = appSupportRemark;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public String getStrFileName() {
		return strFileName;
	}
	public void setStrFileName(String strFileName) {
		this.strFileName = strFileName;
	}
	public String getStrAppSupportPerformer() {
		return strAppSupportPerformer;
	}
	public void setStrAppSupportPerformer(String strAppSupportPerformer) {
		this.strAppSupportPerformer = strAppSupportPerformer;
	}

    }

