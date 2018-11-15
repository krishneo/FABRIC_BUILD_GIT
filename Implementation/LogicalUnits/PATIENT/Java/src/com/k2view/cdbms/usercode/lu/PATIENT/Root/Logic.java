/////////////////////////////////////////////////////////////////////////
// LU Functions
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.PATIENT.Root;

import java.util.*;
import java.sql.*;
import java.math.*;
import java.io.*;

import com.k2view.cdbms.shared.*;
import com.k2view.cdbms.shared.Globals;
import com.k2view.cdbms.shared.user.UserCode;
import com.k2view.cdbms.sync.*;
import com.k2view.cdbms.lut.*;
import com.k2view.cdbms.shared.utils.UserCodeDescribe.*;
import com.k2view.cdbms.shared.logging.LogEntry.*;
import com.k2view.cdbms.func.oracle.OracleToDate;
import com.k2view.cdbms.func.oracle.OracleRownum;
import com.k2view.cdbms.usercode.lu.PATIENT.*;

import static com.k2view.cdbms.shared.utils.UserCodeDescribe.FunctionType.*;
import static com.k2view.cdbms.shared.user.ProductFunctions.*;
import static com.k2view.cdbms.usercode.common.SharedLogic.*;
import static com.k2view.cdbms.usercode.lu.PATIENT.Globals.*;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public class Logic extends UserCode {


	@type(RootFunction)
	@out(name = "pharmacy_id", type = String.class, desc = "")
	@out(name = "ssn", type = String.class, desc = "")
	@out(name = "pharmacy_name", type = String.class, desc = "")
	@out(name = "address", type = String.class, desc = "")
	@out(name = "city", type = String.class, desc = "")
	@out(name = "zip", type = String.class, desc = "")
	@out(name = "state", type = String.class, desc = "")
	@out(name = "country", type = String.class, desc = "")
	public static void fnPop_pharmacy_information(String input) throws Exception {
						String sql = "SELECT pharmacy_id, ssn, pharmacy_name, address, city, zip, state, country FROM public.pharmacy_information";
						Db.Rows rows = db("PIS_DB").fetch(sql);
						for (Db.Row row:rows){
							yield(row.cells());
						}
					
			
	}


	@type(RootFunction)
	@out(name = "SSN", type = String.class, desc = "")
	@out(name = "FIRST_NAME", type = String.class, desc = "")
	@out(name = "LAST_NAME", type = String.class, desc = "")
	public static void fnPop_SUB_PATIENT(String SSN) throws Exception {
		String sql = "Select PATIENT.SSN,"
				+ "   PATIENT.FIRST_NAME,"
				+ "   PATIENT.LAST_NAME"
				+ " From PATIENT where SSN='"
		        + SSN+"'";
		Db.Rows rows = db("HIS_DB").fetch(sql);
		for (Db.Row row:rows){
			yield(row.cells());
		}
	}

	
	
	
	
}
