package edu.uga.cs.rentaride.persistence.impl;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class Administrator
implements Iterator<Administrator> {
	private ResultSet   rs = null;
	private ObjectLayer objectLayer = null;
	private boolean     more = false;

	public Administrator(ResultSet rs, ObjectLayer objectLayer)
		throws RARException
		{
			this.rs = rs;
			this.objectLayer = objectLayer;
			try {
				more = rs.next();
			}
			catch(Exception e) {  // just in case...
				throw new RARException("Administrator: Cannot create an iterator; root cause: " + e);
			}
		}

	public boolean hasNext() {
		return more;
	}

	public Administrator next() {
		long id;
		String firstName;
		String lastName;
		String userName;
		String emailAddress;
		String password;
		Date createDate;
		Administrator administrator = null;


		if(more) {

			try {
				id = rs.getLong("id");
				firstName = rs.getString("firstName");
				lastName = rs.getString("lastName");
				userName = rs.getString("userName");
				emailAddress = rs.getString("emailAddress");
				password = rs.getString("password");
				createDate = rs.getDate("createdDate");

				more = rs.next();
			}
			catch(Exception e) {      // just in case...
				throw new NoSuchElementException("Administrator: No next Administrator object; root cause: " + e);
			}

			administrator = objectLayer.createAdministrator(firstName, lastName, userName, emailAddress, password, createDate);
			administrator.setId(id);

			return administrator;
		}
		else {
			throw new NoSuchElementException("Administrator: No next Administrator object");
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}

