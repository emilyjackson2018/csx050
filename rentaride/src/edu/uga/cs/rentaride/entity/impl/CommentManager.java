package edu.uga.cs.rentaride.persistence.impl;

import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.impl.CommentImpl;
import edu.uga.cs.rentaride.entity.impl.CustomerImpl;
import edu.uga.cs.rentaride.entity.impl.RentalImlp;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class CommentManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;

    public CommentManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public void store( Comment comment )
    {
        String               insertCommentSql = "insert into comment ( text, date, rental, customer) values ( ?, ?, ?, ?)";
        String               updateCommentSql = "update comment set text = ?, set date = ?, set rental = ?, set customer = ?";
        PreparedStatement    stmt;
        int                  inscnt;
        long                 commentId;

        try {

            if( !comment.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertCommentSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateCommentSql );

            if( comment.getCustomer() == null || comment.getRental() == null) {
              throw new RARException("CommentManager.save: can't save a Comment: no defined customer and/or rental")
            }
            if( !comment.getCustomer().isPersistent() || !comment.getRental().isPersistent()) {
              throw new RARException("CommentManager.save: can't save a Comment: related customer and/or rental not persistent")
            }

            if( comment.getText() != null ) // clubsuser is unique, so it is sufficient to get a Comment
                stmt.setString( 1, comment.getText() );
            else
                throw new RARException( "CommentManager.save: can't save a Comment: text undefined" );

            if( comment.getDate() != null )
                stmt.setString( 2, comment.getDate() );
            else
                throw new RARException( "CommentManager.save: can't save a Comment: date undefined" );

            if( comment.getRental() != null )
                stmt.setString( 3,  comment.getRental() );
            else
                throw new RARException( "CommentManager.save: can't save a Comment: rental undefined" );

            if( comment.getCustomer() != null )
                stmt.setString( 4, comment.getCustomer() );
            else
                throw new RARException( "CommentManager.save: can't save a Comment: customer undefined" );

            inscnt = stmt.executeUpdate();

            if( !comment.isPersistent() ) {
                // in case this this object is stored for the first time,
                // we need to establish its persistent identifier (primary key)
                if( inscnt == 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result
                        // retrieve the result
                        ResultSet r = stmt.getResultSet();
                        // we will use only the first row!
                        while( r.next() ) {
                            // retrieve the last insert auto_increment value
                            commentId = r.getLong( 1 );
                            if( commentId > 0 )
                                comment.setId( commentId ); // set this Comment's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    //throw new RARException( "CommentManager.save: failed to save a Comment" );
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            //throw new RARException( "CommentManager.save: failed to save a Comment: " + e );
        }
    }

    public List<Comment> restore( Comment modelcomment )
            throws RARException
    {
        String       selectCommentSql = "select id, text, date, rental, customer from comment";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Comment> comments = new ArrayList<Comment>();

        condition.setLength( 0 );

        // form the query based on the given Comment object instance
        query.append( selectCommentSql );

        if( modelcomment != null ) {
            if( modelcomment.getId() >= 0 ) // id is unique, so it is sufficient to get a Comment
                query.append( " where id = " + modelcomment.getId() );
            else if( modelcomment.getUserName() != null ) // userName is unique, so it is sufficient to get a Comment
                query.append( " where username = '" + modelcomment.getUserName() + "'" );
            else {
                if( modelcomment.getPassword() != null )
                    condition.append( " password = '" + modelcomment.getPassword() + "'" );

                if( modelcomment.getEmail() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " email = '" + modelcomment.getEmail() + "'" );
                }

                if( modelcomment.getFirstName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " firstName = '" + modelcomment.getFirstName() + "'" );
                }

                if( modelcomment.getLastName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " lastName = '" + modelcomment.getLastName() + "'" );
                }

                if( modelcomment.getAddress() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " address = '" + modelcomment.getAddress() + "'" );
                }

                if( modelcomment.getPhone() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " phone = '" + modelcomment.getPhone() + "'" );
                }

                if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
            }
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent Comment objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   id;
                String userName;
                String password;
                String email;
                String firstName;
                String lastName;
                String address;
                String phone;

                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    userName = rs.getString( 2 );
                    password = rs.getString( 3 );
                    email = rs.getString( 4 );
                    firstName = rs.getString( 5 );
                    lastName = rs.getString( 6 );
                    address = rs.getString( 7 );
                    phone = rs.getString( 8 );

                    Comment Comment = objectLayer.createComment( userName, password, email, firstName, lastName, address, phone );
                    comment.setId( id );

                    Comments.add( Comment );

                }

                return Comments;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new RARException( "CommentManager.restore: Could not restore persistent Comment object; Root cause: " + e );
        }

        // if we get to this point, it's an error
        throw new RARException( "CommentManager.restore: Could not restore persistent Comment objects" );
    }

    public List<Club> restoreClubEstablishedByComment( Comment Comment )
            throws RARException
    {
        String selectCommentSql = "select c.id, c.name, c.address, c.established, p.id, " +
                                 "p.username, p.userpass, p.email, p.firstname, p.lastname, p.address, " +
                                 "p.phone from club c, Comment p where c.founderid = p.id";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Club>   clubs = new ArrayList<Club>();

        condition.setLength( 0 );

        // form the query based on the given Comment object instance
        query.append( selectCommentSql );

        if( Comment != null ) {
            if( comment.getId() >= 0 ) // id is unique, so it is sufficient to get a Comment
                query.append( " and p.id = " + comment.getId() );
            else if( comment.getUserName() != null ) // userName is unique, so it is sufficient to get a Comment
                query.append( " and p.username = '" + comment.getUserName() + "'" );
            else {
                if( comment.getPassword() != null )
                    condition.append( " p.password = '" + comment.getPassword() + "'" );

                if( comment.getEmail() != null && condition.length() == 0 )
                    condition.append( " p.email = '" + comment.getEmail() + "'" );
                else
                    condition.append( " AND p.email = '" + comment.getEmail() + "'" );

                if( comment.getFirstName() != null && condition.length() == 0 )
                    condition.append( " p.firstname = '" + comment.getFirstName() + "'" );
                else
                    condition.append( " AND p.firstname = '" + comment.getFirstName() + "'" );

                if( comment.getLastName() != null && condition.length() == 0 )
                    condition.append( " p.lastname = '" + comment.getLastName() + "'" );
                else
                    condition.append( " AND p.lastname = '" + comment.getLastName() + "'" );

                if( comment.getAddress() != null && condition.length() == 0 )
                    condition.append( " p.address = '" + comment.getAddress() + "'" );
                else
                    condition.append( " AND p.address = '" + comment.getAddress() + "'" );

                if( comment.getPhone() != null && condition.length() == 0 )
                    condition.append( " p.phone = '" + comment.getPhone() + "'" );
                else
                    condition.append( " AND p.phone = '" + comment.getPhone() + "'" );

                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent Club objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result

                long   id;
                String name;
                String address;
                Date   establishedOn;
                Club   nextClub = null;

                ResultSet rs = stmt.getResultSet();

                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    name = rs.getString( 2 );
                    address = rs.getString( 3 );
                    establishedOn = rs.getDate( 4 );

                    nextClub = objectLayer.createClub(); // create a proxy club object
                    // and now set its retrieved attributes
                    nextClub.setId( id );
                    nextClub.setName( name );
                    nextClub.setAddress( address );
                    nextClub.setEstablishedOn( establishedOn );
                    // set this to null for the "lazy" association traversal
                    nextClub.setCommentFounder( null );

                    clubs.add( nextClub );
                }

                return clubs;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new RARException( "CommentManager.restoreEstablishedBy: Could not restore persistent Club objects; Root cause: " + e );
        }

        throw new RARException( "CommentManager.restoreEstablishedBy: Could not restore persistent Club objects" );
    }

    public void delete( Comment comment )
            //throws RARException
    {
        String               deleteCommentSql = "delete from comment where id = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;

        // form the query based on the given Comment object instance
        if( !comment.isPersistent() ) // is the Comment object persistent?  If not, nothing to actually delete
            return;

        try {

            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteCommentSql );

            stmt.setLong( 1, comment.getId() );

            inscnt = stmt.executeUpdate();

            if( inscnt == 0 ) {
                throw new RARException( "CommentManager.delete: failed to delete this Comment" );
            }
        }
        catch( SQLException e ) {
            throw new RARException( "CommentManager.delete: failed to delete this Comment: " + e.getMessage() );
        }
    }
}
