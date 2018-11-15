/////////////////////////////////////////////////////////////////////////
// LU Functions
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.PATIENT.krish;

import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
import java.math.*;
import java.io.*;
import java.util.Date;

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


	@desc("All My Functions")
	@out(name = "output_string_1", type = String.class, desc = "")
	@out(name = "output_string_2", type = String.class, desc = "")
	public static Object krish_concat(String input_string_1, String input_string_2) throws Exception {
		String output_string_1=(input_string_1+input_string_2+"KRISH");
		String output_string_2=("KRISH"+input_string_1+input_string_2);
		return new Object[]{output_string_1,output_string_2};
	}


	public static void PATIENT_Enrich_Object() throws Exception {
			String sql = "SELECT PATIENT_ID, SSN, FIRST_NAME, LAST_NAME FROM PATIENT";
			Db.Rows rows = ludb().fetch(sql);
			for (Db.Row row:rows){
				String[] trnValues = {(String)row.get("SSN")};
				Map trn = getTranslationValues("SUPER_USER_TRANSLATION",trnValues);
				String new_product_code = (String)trn.get("IS_SUPER_USER");
				DBExecute("ludb",("update PATIENT set is_super_user_enrichment='"+new_product_code+ "' where SSN='"+row.get("SSN")+"' "),null);
			}
	}


	@out(name = "output_str", type = String.class, desc = "")
	public static String replaceStr(String replace_str, String find_str, String input_str) throws Exception {
		return input_str.replace(find_str,replace_str);
	}


	@out(name = "real_balance", type = Double.class, desc = "")
	public static Double compute_balance(Integer input_balance) throws Exception {
		return (Double.valueOf(input_balance)/100);
	}


	public static void PRESCRIBED_MEDICATION_EO() throws Exception {
		String sql = "SELECT PRESCRIPTION_ID,PRESCRIBED_MEDICATION FROM PRESCRIBED_MEDICATION";
		Db.Rows rows = ludb().fetch(sql);
		for (Db.Row row:rows){
			 /* String medication_name=(String)DBSelectValue("ludb","select medication_name from medication_reference 
			where medication_id='"+row.get("PRESCRIBED_MEDICATION")+"'",null);
		DBExecute("ludb","update PRESCRIBED_MEDICATION set medication_value_by_eo='"+medication_name+"' 
			where PRESCRIPTION_ID='"+row.get("PRESCRIPTION_ID")+"'",null); */
		}
	}


	public static void krish_invoices_eo() throws Exception {
		String sql = "SELECT PATIENT_ID, INVOICE_ID, ISSUED_DATE, DUE_DATE, STATUS, BALANCE, REAL_BALANCE FROM INVOICE";
		Db.Rows rows = ludb().fetch(sql);
		Date global_barrier_date_dt=new SimpleDateFormat("yyyy-MM-dd").parse(GLOBAL_BARRIER_DATE);
		Date invoice_date_dt = null;
		String delete_str;
		String can_be_deleted=null;
		for (Db.Row row:rows){
			invoice_date_dt = new SimpleDateFormat("yyyy-MM-dd").parse((String)row.get("ISSUED_DATE"));
			reportUserMessage("Start invoice_id:"+row.get("INVOICE_ID"));
			reportUserMessage("global_barrier_date_dt:"+global_barrier_date_dt);
			reportUserMessage("invoice_date from table:"+(String)row.get("ISSUED_DATE"));
			reportUserMessage("invoice_date_dt:"+invoice_date_dt);
			reportUserMessage("End invoice_id:"+row.get("INVOICE_ID"));
			if (invoice_date_dt.compareTo(global_barrier_date_dt) < 0)
			{
				can_be_deleted="Y";
			}
			else
			{
				can_be_deleted="N";
			}
			delete_str = "global_barrier_date_dt:"+ GLOBAL_BARRIER_DATE+" invoice_date_dt::"+(String)row.get("ISSUED_DATE")+" .Hence can be deleted:"+can_be_deleted;
			DBExecute("ludb","update invoice set can_be_deleted='"+delete_str+"' where invoice_id='"+(String)row.get("ISSUED_ID")+"'",null);
		}
	}



	
	
	
	
}
