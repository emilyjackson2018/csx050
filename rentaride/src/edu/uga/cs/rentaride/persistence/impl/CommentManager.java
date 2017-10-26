package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.impl.*;
import edu.uga.cs.rentaride.object.ObjectLayer;


class CommentManager {
    private ObjectLayer objectLayer = null;
    private Connection   conn = null;
    
    public CommentManager(Connection conn, ObjectLayer objectLayer) {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void save(Comment comment) 
            throws RARException
    {
        String               insertCommentSql = "insert into Comments (commentDate,rental,comment) values (?, ?, ?)";
        String               updateCommentSql = "update Comments set commentDate = ?, rental = ?, comment = ?, comemntID = ? where commentID = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 commentID;

        try {

            if(!comment.isPersistent())
                stmt = (PreparedStatement) conn.prepareStatement(insertCommentSql);
            else
                stmt = (PreparedStatement) conn.prepareStatement(updateCommentSql);

            if(comment.getDate() != null)
                stmt.setDate(1, (Date) comment.getDate());
            else 
                throw new RARException("CommentManager.save: can't save a Comment: date undefined");

            if(comment.getRental() != null)
                stmt.setDate(2, (Date) comment.getRental());
            else
                stmt.setNull(2, java.sql.Types.VARCHAR);

            if(comment.getText() != null) {
                java.util.Date jDate = comment.getDate();
                java.sql.Date sDate = new java.sql.Date(jDate.getTime());
                stmt.setDate(3,  sDate);
            }
            else
                stmt.setNull(3, java.sql.Types.DATE);

            if(comment.isPersistent())
                stmt.setLong(5, comment.getId());

            inscnt = stmt.executeUpdate();

            if(!comment.isPersistent()) {
                if(inscnt >= 1) {
                    String sql = "select last_insert_id()";
                    if(stmt.execute(sql)) {
                        ResultSet r = stmt.getResultSet();
                        while(r.next()) {
                            commentID = r.getLong(1);
                            if(commentID > 0)
                                comment.setId(commentID);
                        }
                    }
                }
                else
                    throw new RARException("CommentManager.save: failed to save a Comment");
            }
            else {
                if(inscnt < 1)
                    throw new RARException("CommentManager.save: failed to save a Comment");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RARException("CommentManager.save: failed to save a Comment: " + e);
        }
    }

       Customer restoreCustomerComment(Comment comment)
            throws RARException
    {
        String       selectCSql = "select c.commentID, c.commentDate, c.rental, c.comment " +
                                      " from Comments c, Customers cu where 1 = 1 ";
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        
        query.append(selectCSql);
        
        if(comment != null) {
            if(comment.getId() >= 0)
                query.append(" and c.commentID = " + comment.getId());
            else if(comment.getRental() != null)
                query.append(" and c.rental = '" + comment.getRental() + "'");
            else {

                if(comment.getDate() != null)
                    condition.append(" and c.commentDate = '" + comment.getDate() + "'");
                if(comment.getText() != null)
                    condition.append(" and c.comment = '" + comment.getText() + "'");
       
                if(condition.length() > 0) {
                    query.append(condition);
                }
                
            }
        }
        
        try {

            Statement stmt = conn.createStatement();

            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return (Customer) new CommentImpl();
            }
        }
        catch(Exception e) {
            throw new RARException("CommentManager.restore: Could not restore persistent Comment object; Root cause: " + e);
        }

        throw new RARException("CommentManager.restore: Could not restore persistent Comment object");
    }

    
    Rental restoreRentalComment(Comment comment)
            throws RARException
    {
        String       selectCSql = "select r.customer, r.pickupTime, r.returnTime, c.commentID, c.commentDate, c.rental, c.comment " +
                                      " from Comments c, Rentals r where r.rentalNo = c.commentID ";
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        
        query.append(selectCSql);
        
        if(comment != null) {
            if(comment.getId() >= 0)
                query.append(" and c.commentID = " + comment.getId());
            else if(comment.getRental() != null)
                query.append(" and c.rental = '" + comment.getRental() + "'");
            else {

                if(comment.getDate() != null)
                    condition.append(" and c.commentDate = '" + comment.getDate() + "'");
                if(comment.getText() != null)
                    condition.append(" and c.comment = '" + comment.getText() + "'");
                if(condition.length() > 0) {
                    query.append(condition);
                }
            }
        }
        
        try {

            Statement stmt = conn.createStatement();

            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return (Rental) new RentalList(r, objectLayer);
            }
        }
        catch(Exception e) {
            throw new RARException("CommentManager.restore: Could not restore persistent Rental object; Root cause: " + e);
        }

        throw new RARException("CommentManager.restore: Could not restore persistent Rental object");
    }

        public List<Comment> restore(Comment modelComment) 
            throws RARException
    {
        String       selectCSql = "select c.commentID, c.commentDate, c.rental, c.comment " +
                                      " from Comments c where 1 = 1 ";
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        
        query.append(selectCSql);
        
        if(modelComment != null) {
            if(modelComment.getId() >= 0)
                query.append(" and c.commentID = " + modelComment.getId());
            else if(modelComment.getRental() != null)
                query.append(" and c.rental = '" + modelComment.getRental() + "'");
            else {

                if(modelComment.getDate() != null)
                    condition.append(" and c.commentDate = '" + modelComment.getDate() + "'");
                if(modelComment.getText() != null)
                    condition.append(" and c.comment = '" + modelComment.getText() + "'");
                if(condition.length() > 0) {
                    query.append(condition);
                }
            }
        }
        
        try {

            Statement stmt = conn.createStatement();
            if(stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return (List<Comment>) new CommentImpl();
            }
        }
        catch(Exception e) {
            throw new RARException("CommentManager.restore: Could not restore persistent Comment object; Root cause: " + e);
        }

        throw new RARException("CommentManager.restore: Could not restore persistent Comment object");
    }
    
    
    public void delete(Comment comment) 
            throws RARException
    {
        String               deleteCommentSql = "delete from Comments where commentID = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
             
        if(!comment.isPersistent())
            return;
        
        try {
            stmt = (PreparedStatement) conn.prepareStatement(deleteCommentSql);
            stmt.setLong(1, comment.getId());
            inscnt = stmt.executeUpdate();          
            if(inscnt == 1) {
                return;
            }
            else
                throw new RARException("CommentManager.delete: failed to delete a Comment");
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RARException("CommentManager.delete: failed to delete a Comment: " + e);        }
    }
}

