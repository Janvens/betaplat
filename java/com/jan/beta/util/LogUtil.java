package com.jan.beta.util;

public class LogUtil {
	
	public static String logXml(Object obj){
		String xmlStr = null;
		if(obj != null){
			xmlStr = (String)obj;
			xmlStr = "\r\n" + xmlStr;
			xmlStr = xmlStr.replaceAll("></", ">|</");
			xmlStr = xmlStr.replace("><", ">\n<");
			xmlStr = xmlStr.replace("|", "");
//			xmlStr = xmlStr.replace("></", ">\n<");
		}
		return xmlStr;
	}
	
	public static void main(String[] args){
		String req = "<?xml version='1.0' encoding='utf-8'?><MESSAGE><MCODE>101430</MCODE><MID></MID><DATE>20091201</DATE><TIME>000000</TIME><MOBILEID>213</MOBILEID><ALIAS>1111111111111</ALIAS><MERID>888001900010004</MERID><ORDERID>000000000000106</ORDERID><AMOUT>1</AMOUT><ALLOWNOTE>0</ALLOWNOTE><AUTHORIZEMODE>WEB</AUTHORIZEMODE><CURRENCY>CNY</CURRENCY><ORDERDATE>20091201</ORDERDATE><PERIOD>1</PERIOD><PERIODUNIT>0</PERIODUNIT><PRODUCTDESC>0</PRODUCTDESC><PRODUCTID>00000000</PRODUCTID><PRODUCTNAME>0</PRODUCTNAME><TXNTYP>L</TXNTYP><SIGN>kIs0y0ib5bvYwGD3tJeor88pgGY3z44YUTiKLgQ8fo1iQ87wwauVeSZikR40Pv9X570r7yi7CLgUeUbgMNnXyB+nLosAaFzzYNYiwgywbQyhiCRiyDilcm6ZtLgE6XydEVfpNsSBUYLRO0uVLF1g/aw48xlLZiZrjequDgsq9V4=</SIGN></MESSAGE>";
		req = LogUtil.logXml(req);
		System.out.println(req);
		String res = "<MESSAGE><MCODE>101430</MCODE><MID>W000000000000000</MID><DATE>20091201</DATE><TIME>000000</TIME><PAYDAY>20250602</PAYDAY><RCODE>PM4001</RCODE><DESC>����˻������ڣ���ȷ���Ƿ񿪻�������</DESC><OMID>20100415014587</OMID><ORDERID>000000000000106</ORDERID><SIGN>FL33ntDoq8wFFKBF07s5NmJ7T/m8nKedsAPawimjo7gNL32NwsVUAzPfZk6JXlKqKjxQvK+mPCj7Kur7gySUIooU1p0vHJtzozYt3C+fRskbYBl7g4deWH1CIVEbpblLTgDjAZO/TRod/noViHopvLge2iVRSPcXHmXh+AvI9zQ=</SIGN></MESSAGE>";
		req = LogUtil.logXml(res);
		System.out.println(req);
	}
}
