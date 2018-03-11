/**
 * Copyright 2011 FuseSource
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.fusesource.customerwscamelcxfpojo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fusesource.demo.customer.Customer;
import com.fusesource.demo.wsdl.customerservice.CreateCustomerResponse;
import com.fusesource.demo.wsdl.customerservice.SearchCustomersByName;
import com.fusesource.demo.wsdl.customerservice.SearchCustomersByNameResponse;

public class SearchCustomerByNameProcessor implements Processor {

    public static final Logger log = LoggerFactory.getLogger(SearchCustomerByNameProcessor.class);
    private DataSource ds;
    
    
    public void process(Exchange exchng) throws Exception {
   
        Object[] args = exchng.getIn().getBody(Object[].class);        
        String name = (String) args[0];
        ArrayList<Customer> results = query(name);
        exchng.getOut().setBody(new Object[] {results});
        
    }

	private ArrayList<Customer> query(String name) throws SQLException {
		ArrayList<Customer> results = new ArrayList<Customer>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection conn = ds.getConnection();
			pstmt = conn.prepareStatement("select id, firstname, lastname, phoneno from customer where firstname like ? and isdeleted = 0");
			pstmt.setString(1, "%" + name + "%");
			rs = pstmt.executeQuery();
			while(rs.next()){
					Customer c = new Customer();
					c.setFirstName(rs.getString("firstname"));
					c.setLastName(rs.getString("lastname"));
					c.setPhoneNumber(rs.getString("phoneno"));
					c.setPhoneNumber(rs.getString("id"));
					results.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != rs){
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(null != pstmt){
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return results;
	}


	public DataSource getDs() {
		return ds;
	}


	public void setDs(DataSource ds) {
		this.ds = ds;
	}
}
