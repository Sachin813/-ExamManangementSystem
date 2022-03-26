package com.db.jdbc;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.Date;
public class JDBCCONNECT {
    public boolean teacherlogin(String userid,String password){
        boolean isValiduser=false;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "7959");
            Statement st = con.createStatement();
            String query="Select * from teachercreds where userid='"+userid+"' and password='"+password+"';";
            System.out.println(query);
            ResultSet rs=st.executeQuery(query);
            if (rs.next()){
                isValiduser=true;
            }
            st.close();
            con.close();


        }catch (Exception ex){
            System.out.println("Some error occured while logging in");
        }

        return isValiduser;


    }
    public boolean studentlogin(String userid,String password){
        boolean isValiduser=false;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "7959");
            Statement st = con.createStatement();
            String query="Select * from studentcreds where username='"+userid+"' and password='"+password+"';";
            System.out.println(query);
            ResultSet rs=st.executeQuery(query);
            if (rs.next()){
                isValiduser=true;
            }
            st.close();
            con.close();


        }catch (Exception ex){
            System.out.println("Some error occured while logging in");
        }

        return isValiduser;


    }

    public boolean createTest(String[][]testdata) {
        try {
            Date dt = new Date();
            long timeOfTableCreation =  dt.getTime();
            String questionTableName = "test" +  timeOfTableCreation;
            String answersTableName =  "testScore" + timeOfTableCreation;
            System.out.println(questionTableName +"   "+ answersTableName);

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "7959");
            Statement st = con.createStatement();
            String createtablequery="create table "+questionTableName+"(question varchar(20) ,typeofquestion varchar(10), options varchar(100), correctanswer varchar(100))";
            String answerTableQuery = "create table "+answersTableName+"(studentId varchar(50), ";
            int noOfQuestions = testdata.length;
            for(int i = 1; i <= noOfQuestions; i++)
                answerTableQuery += "Answer"+i + " varchar(20),";

            answerTableQuery =  answerTableQuery.substring(0, answerTableQuery.length()-1) +");";

            System.out.println(answerTableQuery);


            System.out.print(createtablequery);

            int updated = st.executeUpdate(createtablequery);
            updated = st.executeUpdate(answerTableQuery);

            if (updated==0){
                int i=0;
                while(i<testdata.length){
                    String insertquery="insert into "+ questionTableName+" values('"+testdata[i][0]+"','"+testdata[i][1]+"','"+testdata[i][2]+"','"+testdata[i][3]+"')";
                    System.out.println(insertquery);
                    int rowsupdatewd=st.executeUpdate(insertquery);
                    i++;
                }

            }
            st.close();
            con.close();




        }catch (Exception ex){

        }
        return true;
    }
    public String[][] taketest(String testid) throws Exception{



              Class.forName("com.mysql.cj.jdbc.Driver");
              Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "7959");
              String Selectquery = "select question , typeofquestion , options from " +testid;
              System.out.println(Selectquery);
              PreparedStatement st=con.prepareStatement(Selectquery,
                      ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
              ResultSet rs = st.executeQuery(Selectquery);
              int size = 0;
              if (rs != null) {
                  rs.last();
                  size = rs.getRow();
              }
              String[][] testData = new String[size][3];
              rs.beforeFirst();
              int i = 0;
              while (rs.next()) {
                  testData[i][0] = rs.getString(1);
                  testData[i][1] = rs.getString(2);
                  testData[i][2] = rs.getString(3);
                  i++;
              }
              return testData;


    }
    public boolean saveTestAnswers(String studentId, String testId, String []answersOfTest) {


        boolean testSavedSuccess = true;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "password");

           Statement st = con.createStatement();

            String scoreTableName = "testScore"+ testId.substring(4);

            String insertAnswersQuery = "insert into "+ scoreTableName+" values('"+studentId +"',";

            for(int i =0; i< answersOfTest.length; i++)
                insertAnswersQuery += "'"+answersOfTest[i]+"',";

            insertAnswersQuery = insertAnswersQuery.substring(0, insertAnswersQuery.length() - 1)+")";

            System.out.println(insertAnswersQuery);

            st.executeUpdate(insertAnswersQuery);
            st.close();
            con.close();




        }catch(Exception ex) {
            testSavedSuccess = false;

            ex.printStackTrace();

        }
        return testSavedSuccess;




    }




}
