/////////////////////////////////////////////////////////////////////////
// LU Globals
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.PATIENT;

import com.k2view.cdbms.usercode.common.SharedGlobals;
import com.k2view.cdbms.shared.utils.UserCodeDescribe.*;

public class Globals extends SharedGlobals {

	@desc("Invoice Final Date")
	@category("Invoices")
	public static final String GLOBAL_BARRIER_DATE = "2009-09-30";

	@desc("Y Means Run for whole population else dont")
	@category("FeatureToggle")
	public static final String RUN_POPULATION = "Y";

	


}
