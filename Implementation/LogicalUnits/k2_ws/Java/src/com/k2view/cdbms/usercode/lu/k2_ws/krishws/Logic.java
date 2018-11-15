/////////////////////////////////////////////////////////////////////////
// Project Web Services
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.k2_ws.krishws;

import java.util.*;
import java.sql.*;
import java.math.*;
import java.io.*;

import com.k2view.cdbms.shared.*;
import com.k2view.cdbms.shared.user.WebServiceUserCode;
import com.k2view.cdbms.sync.*;
import com.k2view.cdbms.lut.*;
import com.k2view.cdbms.shared.utils.UserCodeDescribe.*;
import com.k2view.cdbms.shared.logging.LogEntry.*;
import com.k2view.cdbms.func.oracle.OracleToDate;
import com.k2view.cdbms.func.oracle.OracleRownum;
import com.k2view.cdbms.usercode.lu.k2_ws.*;

import static com.k2view.cdbms.shared.utils.UserCodeDescribe.FunctionType.*;
import static com.k2view.cdbms.shared.user.ProductFunctions.*;
import static com.k2view.cdbms.usercode.common.SharedLogic.*;
import static com.k2view.cdbms.usercode.common.SharedGlobals.*;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public class Logic extends WebServiceUserCode {


	@out(name = "PATIENT_ID", type = String.class, desc = "")
	@out(name = "SSN", type = String.class, desc = "")
	@out(name = "FIRST_NAME", type = String.class, desc = "")
	@out(name = "LAST_NAME", type = String.class, desc = "")
	public static Object getfirstandlastname(String SSN) throws Exception {
		String sql = "SELECT PATIENT_ID, SSN, FIRST_NAME, LAST_NAME FROM PATIENT WHERE SSN='"+SSN+"'";
		DBExecute("fabric","use PATIENT.1166501472",null);
		ResultSetWrapper rs =  DBQuery("fabric",sql,null);
		
		//Db.Rows rows = ludb("PATIENT").fetch(sql);
		//return rows;
		return rs.getFirstRowAsMap();
	}


	@out(name = "SSN", type = String.class, desc = "")
	@out(name = "FIRST_NAME", type = String.class, desc = "")
	@out(name = "LAST_NAME", type = String.class, desc = "")
	@out(name = "CITY", type = String.class, desc = "")
	@out(name = "ZIP", type = String.class, desc = "")
	@out(name = "STATE", type = String.class, desc = "")
	@out(name = "COUNTRY", type = String.class, desc = "")
	public static Object getPatient(String SSN) throws Exception {
		String sql = "SELECT PATIENT_ID, SSN, FIRST_NAME, LAST_NAME,CITY,ZIP,STATE,COUNTRY FROM PATIENT WHERE SSN='"+SSN+"'";
		DBExecute("fabric","use PATIENT."+SSN,null);
		ResultSetWrapper rs =  DBQuery("fabric",sql,null);
		return rs.getFirstRowAsMap();
		//Db.Rows rows = ludb("PATIENT").fetch(sql);
		//return rows;
	}


	@out(name = "MEDICATION_ALLERGY", type = String.class, desc = "")
	@out(name = "NEW_VALUE", type = String.class, desc = "")
	@out(name = "SSN", type = String.class, desc = "")
	public static Object getallergy(String SSN) throws Exception {
		String sql = "SELECT PM.SSN,A.MEDICATION_ALLERGY,PM.NEW_VALUE FROM PRESCRIBED_MEDICATION PM,PATIENT P , ALLERGIES A where PM.SSN=P.SSN AND P.PATIENT_ID=A.PATIENT_ID";
		Db.Rows rows = ludb("PATIENT."+SSN).fetch(sql);
		return rows;
	}


	@out(name = "count", type = String.class, desc = "")
	public static String migrateRange(Integer min, Integer max) throws Exception {
		DBExecute("fabric","migrate PATIENT from HIS_DB using ('select SSN from patient where patient_id  between "+min+" AND "+max+" ')",null);
		DBExecute("fabric","use PATIENT",null);
		return (DBSelectValue("fabric","select count(*) from patient where patient_id  between "+min+" AND "+max,null)).toString();
	}

	
	

	
}
