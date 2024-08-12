package Savvion.Savvion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@Service
public class EmployeeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private static final RowMapper<Long> EMPLOYEE_ROW_MAPPER = new RowMapper<>() {
        @Override
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            
        	return rs.getLong("process_instance_id");
        }
    };

    public List<Map<String, Object>> findEmployeesByIds(List<String> ids) {
    	if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("List of names cannot be null or empty");
        }
    	
    	String sql = "SELECT process_instance_id FROM techdesk WHERE ticketno IN (" +
    			ids.stream().map(name -> "?").collect(Collectors.joining(",")) +
                ")";
    	List<Long> data = jdbcTemplate.query(sql, EMPLOYEE_ROW_MAPPER, ids.toArray());
    	
    	
        String sql1 = String.format("select ticketno,AppSupportStatus,AppSupportCloseDtString,AppSupportRemark,callstatus,appsupportdocumentname,appsupportperformer from techdesk where process_instance_id IN (%s)",
        		data.stream().map(String::valueOf).collect(Collectors.joining(",")));
        
        List<Map<String, Object>> employee = jdbcTemplate.queryForList(sql1);
        
        employee.forEach(t -> {
			try {
				if(t.get("APPSUPPORTSTATUS").equals("Open")) {}else {
				soapcall(t);}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
                
        return employee;
    }
    
    public Map<String, Object> soapcall(Map<String, Object> inputData)  throws IOException {
        String responseString = "";
        String outputString = "";
        String wsEndPoint = "http://calldesk.reliancegeneral.co.in:8081/Service.asmx";
        URL url = new URL(wsEndPoint);
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) connection;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">"
                + "   <soapenv:Header/>"
                + "   <soapenv:Body>"
                + "      <tem:SetAppSupportStatus>"
                + "         <!--Optional:-->"
                + "         <tem:TicketNumber>"+inputData.get("TICKETNO")+"</tem:TicketNumber>"
                + "         <!--Optional:-->"
                + "         <tem:AppSupportStatus>"+inputData.get("APPSUPPORTSTATUS")+"</tem:AppSupportStatus>"
                + "         <!--Optional:-->"
                + "         <tem:AppSupportCallClosedDate>"+inputData.get("APPSUPPORTCLOSEDTSTRING")+"</tem:AppSupportCallClosedDate>"
                + "         <!--Optional:-->"
                + "         <tem:AppSupportRemark>"+inputData.get("APPSUPPORTREMARK")+"</tem:AppSupportRemark>"
                + "         <!--Optional:-->"
                + "         <tem:Category>"+inputData.get("CALLSTATUS")+"</tem:Category>"
                + "         <!--Optional:-->"
                + "         <tem:OtherRemark></tem:OtherRemark>"
                + "         <!--Optional:-->"
                + "         <tem:strFileName>"+inputData.get("APPSUPPORTDOCUMENTNAME")+"</tem:strFileName>"
                + "         <!--Optional:-->"
                + "         <tem:strAppSupportPerformer>"+inputData.get("APPSUPPORTPERFORMER")+"</tem:strAppSupportPerformer>"
                + "         <!--Optional:-->"
                + "         <tem:strAppSupportPhoneRemark></tem:strAppSupportPhoneRemark>"
                + "         <!--Optional:-->"
                + "         <tem:SlipReceivedDate></tem:SlipReceivedDate>"
                + "         <!--Optional:-->"
                + "         <tem:ClientName></tem:ClientName>"
                + "         <!--Optional:-->"
                + "         <tem:LocationOfClient></tem:LocationOfClient>"
                + "         <!--Optional:-->"
                + "         <tem:Proposalreceivefrom></tem:Proposalreceivefrom>"
                + "         <!--Optional:-->"
                + "         <tem:Proposalreceivename></tem:Proposalreceivename>"
                + "         <!--Optional:-->"
                + "         <tem:PolicyInceptionDate></tem:PolicyInceptionDate>"
                + "         <!--Optional:-->"
                + "         <tem:ExpiryDate></tem:ExpiryDate>"
                + "         <!--Optional:-->"
                + "         <tem:ExpiryTPA></tem:ExpiryTPA>"
                + "         <!--Optional:-->"
                + "         <tem:InsuranceCompany></tem:InsuranceCompany>"
                + "         <!--Optional:-->"
                + "         <tem:ExpiringBroker></tem:ExpiringBroker>"
                + "         <!--Optional:-->"
                + "         <tem:EE_Relationship></tem:EE_Relationship>"
                + "         <!--Optional:-->"
                + "         <tem:IndustryType></tem:IndustryType>"
                + "         <!--Optional:-->"
                + "         <tem:ExpiringPremium></tem:ExpiringPremium>"
                + "         <!--Optional:-->"
                + "         <tem:PolicyClassificationType></tem:PolicyClassificationType>"
                + "         <!--Optional:-->"
                + "         <tem:Queue_Name></tem:Queue_Name>"
                + "         <!--Optional:-->"
                + "         <tem:TypeOfIssue></tem:TypeOfIssue>"
                + "         <!--Optional:-->"
                + "         <tem:Text_TOI></tem:Text_TOI>"
                + "         <!--Optional:-->"
                + "         <tem:FixType></tem:FixType>"
                + "         <!--Optional:-->"
                + "         <tem:Active_TicketNo></tem:Active_TicketNo>"
                + "         <!--Optional:-->"
                + "         <tem:Issue_Request_type></tem:Issue_Request_type>"
                + "         <!--Optional:-->"
                + "         <tem:Issue_Request_Subtype></tem:Issue_Request_Subtype>"
                + "      </tem:SetAppSupportStatus>"
                + "   </soapenv:Body>"
                + "</soapenv:Envelope>";
        byte[] buffer = new byte[xmlInput.length()];
        buffer = xmlInput.getBytes();
        bout.write(buffer);
        byte[] b = bout.toByteArray();
        String SOAPAction = "http://xyz.org/abc/SMS";
        httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        OutputStream out = httpConn.getOutputStream();
        out.write(b);
        out.close();
        InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), Charset.forName("UTF-8"));
        BufferedReader in = new BufferedReader(isr);
        while ((responseString = in.readLine()) != null) {
            outputString = outputString + responseString;
        }
        String formattedSOAPResponse = formatXML(outputString);
        System.out.println("Response: " + formattedSOAPResponse);
    	return inputData;
    }

    private static String formatXML(String unformattedXml) {
        try {
            Document document = parseXmlFile(unformattedXml);
            NodeList nodeList = document.getElementsByTagName("SetAppSupportStatusResult");
            return nodeList.item(0).getTextContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

  }
