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
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fusesource.demo.customer.Customer;
import com.fusesource.demo.wsdl.customerservice.CreateCustomerResponse;

public class CreateCustomerProcessor implements Processor {

    public static final Logger log = LoggerFactory.getLogger(CreateCustomerProcessor.class);
    private DataSource ds;
    
    
    public void process(Exchange exchng) throws Exception {
   
        Object[] args = exchng.getIn().getBody(Object[].class);
        Customer c = (Customer) args[0];
        
        insert(c);
                
        exchng.getOut().setBody(new Object[] {"OK"});
    }


	private void insert(Customer c) {
		PreparedStatement pstmt = null;
		try {
			Connection conn = ds.getConnection();
			pstmt = conn.prepareStatement("insert into customer (firstname, lastname, phoneno, isdeleted) values (?,?,?,?)");
			pstmt.setString(1, c.getFirstName());
			pstmt.setString(2, c.getLastName());
			pstmt.setString(3, c.getPhoneNumber());
			pstmt.setInt(4, 0);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != pstmt){
				try { 
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	public DataSource getDs() {
		return ds;
	}


	public void setDs(DataSource ds) {
		this.ds = ds;
	}
    
    
    
}
