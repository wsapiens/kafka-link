package com.all4canid.link.domain.contract;

import java.io.Serializable;
import java.math.BigDecimal;

public class SubContractComponent implements Serializable {

	private static final long serialVersionUID = -3841208943346455764L;

	/** This contract component's parent project (job) */
	String MainJobNumber;
	/** Component Item Number */
	String SubcontractItemNumber;
	/** Component Description */
	String ComponentDescription;
	/** Account Code */
	String AccountCode;
	/** Category Code */
	String CategoryCode;
	/** Subcontract Number */
	String SubJobNumber;
	/** Tax Code */
	String TaxCode;
	/** Vendor Id */
	String VendorId;
	/** Contract Number */
	String ContractNumber;
	/** Component Unit of Measure */
	String UnitOfMeasure;
	/** Sequence number for this component */
	Integer TaskSequence;
	/** Component Unit Price */
	BigDecimal UnitPrice;
	/** Component Amount */
	BigDecimal ComponentAmount;
	/** Line level retention for this component */
	BigDecimal ComponentRetentionPercent;
	/** Component Quantity */
	BigDecimal UnitQuantity;
	/** Component Type */
	String ComponentType;
	/** Phase Code */
	String PhaseCode;
	
	public String getPhaseCode() {
		return PhaseCode;
	}
	public void setPhaseCode(String phaseCode) {
		PhaseCode = phaseCode;
	}
	public String getComponentType() {
		return ComponentType;
	}
	public void setComponentType(String componentType) {
		ComponentType = componentType;
	}
	public String getSubcontractItemNumber() {
		return SubcontractItemNumber;
	}
	public void setSubcontractItemNumber(String subcontractItemNumber) {
		SubcontractItemNumber = subcontractItemNumber;
	}
	public String getComponentDescription() {
		return ComponentDescription;
	}
	public void setComponentDescription(String componentDescription) {
		ComponentDescription = componentDescription;
	}
	public String getAccountCode() {
		return AccountCode;
	}
	public void setAccountCode(String accountCode) {
		AccountCode = accountCode;
	}
	public String getCategoryCode() {
		return CategoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		CategoryCode = categoryCode;
	}
	public String getSubJobNumber() {
		return SubJobNumber;
	}
	public void setSubJobNumber(String subJobNumber) {
		SubJobNumber = subJobNumber;
	}
	public String getTaxCode() {
		return TaxCode;
	}
	public void setTaxCode(String taxCode) {
		TaxCode = taxCode;
	}
	public BigDecimal getComponentRetentionPercent() {
		return ComponentRetentionPercent;
	}
	public void setComponentRetentionPercent(BigDecimal componentRetentionPercent) {
		ComponentRetentionPercent = componentRetentionPercent;
	}
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
	public String getUnitOfMeasure() {
		return UnitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		UnitOfMeasure = unitOfMeasure;
	}
	public Integer getTaskSequence() {
		return TaskSequence;
	}
	public void setTaskSequence(Integer taskSequence) {
		TaskSequence = taskSequence;
	}
	public BigDecimal getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		UnitPrice = unitPrice;
	}
	public BigDecimal getComponentAmount() {
		return ComponentAmount;
	}
	public void setComponentAmount(BigDecimal componentAmount) {
		ComponentAmount = componentAmount;
	}
	public BigDecimal getUnitQuantity() {
		return UnitQuantity;
	}
	public void setUnitQuantity(BigDecimal unitQuantity) {
		UnitQuantity = unitQuantity;
	}
	
}
