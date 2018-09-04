package com.all4canid.link.domain.contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Textura Domain Object for Import New Contract
 * It contains multiple subcontracts data
 * 
 * @author spark
 *
 */
public class SubContracts implements Serializable {

	private static final long serialVersionUID = 7796953777391266301L;

	/** list of subcontracts */
	protected List<SubContract> SubContract;

	public List<SubContract> getSubContract() {
		if(null == SubContract) {
			SubContract = new ArrayList<>();
		}
		return SubContract;
	}
}
