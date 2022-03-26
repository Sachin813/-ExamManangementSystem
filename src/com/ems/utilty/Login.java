package com.ems.utilty;
import com.db.jdbc.JDBCCONNECT;

import java.util.*;

public class Login {
    public static void main(String[] args) {
        System.out.println("Select 1 for teacher login\nSelect 2 for Student login");
        Scanner sc=new Scanner(System.in);
         int choice=Integer.parseInt(sc.nextLine());
         Login l=new Login();
         if (choice==1){
             System.out.println("Enter Userid and Password");
             l.teacherlogin(sc.nextLine(),sc.nextLine());

         }
         else if(choice==2){
             System.out.println("Enter Userid and Password");
             l.studentlogin(sc.nextLine(),sc.nextLine());

         }

    }

         void teacherlogin(String userid,String password){
            boolean isValiduser=new JDBCCONNECT().teacherlogin(userid, password);
            if (isValiduser){
                System.out.println("Welcome to ems system , please select below to proceede further");
                System.out.println("Select 1 for creating test\n2 for checking score of previous tests ");
                Scanner sc=new Scanner(System.in);
                int selection=Integer.parseInt(sc.nextLine());
                if (selection==1){
                    new Test().createtest();

                }
            }
            else{
                System.err.println("Wrong ID or Password! Please input correct credentials ");
            }
         }


    void studentlogin(String userid,String password){
        boolean isValiduser=new JDBCCONNECT().studentlogin(userid, password);
        if (isValiduser){
            System.out.println("Welcome to ems system , please select below to proceede further");
            System.out.println("Select 1 for taking a new test\n2 for checking score of previous tests ");
            Scanner sc=new Scanner(System.in);
            int selection=Integer.parseInt(sc.nextLine());
            if (selection==1){
                System.out.println("Enter the test id of the test that you want to give");
                new Test().taketest(userid,sc.nextLine());

            }
        }
        else{
            System.err.println("Wrong ID or Password! Please input correct credentials ");
        }
    }



}
