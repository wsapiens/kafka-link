package com.all4canid.link.domain.contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SubContract implements Serializable {

	private static final long serialVersionUID = -152688109923934259L;

	/** Internal project number */
	String MainJobNumber;
	/** SubContract number */
	String SubJobNumber;
	/** Vendor Id */
	String VendorId;
	/** Contract Number */
	String ContractNumber;
	/** Short description of contract's purpose */
	String ContractDescription;
	/** Date contract was created, format: MMddyyyy */
	String ContractDate;
	/** The default retention amount at subcontract level */
	BigDecimal DefaultRetentionPercent;
	/** The discount percentage */
	BigDecimal DiscountPercent;
	/** Total dollar value of the subcontract */
	BigDecimal SubcontractAmount;
	/** The number of days a discount percentage is effective */
	Integer DiscountWindow;
	/** additional field for tracking update subcontract */
	transient String RevisionNumber;
	/** Do not exceed **/
	BigDecimal DoNotExceed;
	/** List of components */
	List<SubContractComponent> Components = new ArrayList<>();
	/**default payment form*/
	String DefaultPaymentForm;
	
	public String getMainJobNumber() {
		return MainJobNumber;
	}
	public void setMainJobNumber(String mainJobNumber) {
		MainJobNumber = mainJobNumber;
	}
	public String getVendorId() {
		return VendorId;
	}
	public void setVendorId(String vendorId) {
		VendorId = vendorId;
	}
	public String getContractNumber() {
		return ContractNumber;
	}
	public void setContractNumber(String contractNumber) {
		ContractNumber = contractNumber;
	}
	public String getContractDescription() {
		return ContractDescription;
	}
	public void setContractDescription(String contractDescription) {
		ContractDescription = contractDescription;
	}
	public BigDecimal getDefaultRetentionPercent() {
		return DefaultRetentionPercent;
	}
	public void setDefaultRetentionPercent(BigDecimal defaultRetentionPercent) {
		DefaultRetentionPercent = defaultRetentionPercent;
	}
	public List<SubContractComponent> getComponents() {
		return Components;
	}
	public String getSubJobNumber() {
		return SubJobNumber;
	}
	public void setSubJobNumber(String subJobNumber) {
		SubJobNumber = subJobNumber;
	}
	public String getContractDate() {
		return ContractDate;
	}
	public void setContractDate(String contractDate) {
		ContractDate = contractDate;
	}
	public BigDecimal getDiscountPercent() {
		return DiscountPercent;
	}
	public void setDiscountPercent(BigDecimal discountPercent) {
		DiscountPercent = discountPercent;
	}
	public BigDecimal getSubcontractAmount() {
		return SubcontractAmount;
	}
	public void setSubcontractAmount(BigDecimal subcontractAmount) {
		SubcontractAmount = subcontractAmount;
	}
	public Integer getDiscountWindow() {
		return DiscountWindow;
	}
	public void setDiscountWindow(Integer discountWindow) {
		DiscountWindow = discountWindow;
	}
	public String getRevisionNumber() {
		return RevisionNumber;
	}
	public void setRevisionNumber(String revisionNumber) {
		RevisionNumber = revisionNumber;
	}
	public BigDecimal getDoNotExceed() {
		return DoNotExceed;
	}
	public void setDoNotExceed(BigDecimal doNotExceed) {
		DoNotExceed = doNotExceed;
	}
	public String getDefaultPaymentForm() {
		return DefaultPaymentForm;
	}
	public void setDefaultPaymentForm(String defaultPaymentForm) {
		DefaultPaymentForm = defaultPaymentForm;
	}
}
