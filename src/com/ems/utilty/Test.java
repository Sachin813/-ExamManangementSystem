package com.ems.utilty;
import com.db.jdbc.JDBCCONNECT;

import java.util.Scanner;

import java.util.Scanner;

public class Test {
    public void createtest(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter number of questions");

        int noofQuestion=Integer.parseInt(sc.nextLine());
        String testData[][]=new String[noofQuestion][4];
        for (int i=0;i<noofQuestion;i++){
            System.out.println("Enter Question Number"+ (i+1));
            testData[i][0]=sc.nextLine();
            System.out.println("Enter 1 for MCQ and 2 for Numerical question");
            int TypeofQuestion=Integer.parseInt(sc.nextLine());
            if(TypeofQuestion==1){
                testData[i][1]="Mcq";

            }
            else {
                testData[i][1]="Numerical";

            }
            //if user selects mcq option
            if(TypeofQuestion==1){
                System.out.println("Enter no. of options");
                int noofoptions=Integer.parseInt(sc.nextLine());
                System.out.println("Enter options for mcq type question");
                String ComaSepratedOptions="";
                while (noofoptions--!=0){
                    if (ComaSepratedOptions.equals("")){
                        ComaSepratedOptions=ComaSepratedOptions+sc.nextLine();
                    }
                    else {
                        ComaSepratedOptions=ComaSepratedOptions+","+sc.nextLine();
                    }
                    testData[i][2]=ComaSepratedOptions;
                }
                //enter correct answer
                System.out.println("Enter correct answer");
                String correctanswer=sc.nextLine();
                testData[i][3]=correctanswer;
            }

        }

            new JDBCCONNECT().createTest(testData);


    }
    public void taketest(String studentId ,String testid){
        Scanner sc=new Scanner(System.in);
        try {
            String testArr[][] = new JDBCCONNECT().taketest(testid);
            String answers[] = new String[testArr.length];

            System.out.println("---------------Instructions----------------");
            System.out.println("1.For mcq type question - type option number and press enter to provide answer");
            System.out.println("2.For numerical question , write answer and then press enter");
            System.out.println("3.To nevigate to other question, Type \"nav\" and press enter");
            System.out.println("4.To submit test type submit and press enter");
            int quesno = 0;
            while (quesno != testArr.length) {
                System.out.println("Q" + (quesno + 1) + ". " + testArr[quesno][0]);
                if (testArr[quesno][1].equals("Mcq")) {
                    String[] options = testArr[quesno][2].split(",");
                    int optionno = 0;
                    while (optionno != options.length) {
                        System.out.println((optionno + 1) + ". " + options[optionno]);
                        optionno++;
                    }
                }
                    String input = sc.nextLine();
                    if (input.equals("nav")) {
                        int jumptoquesno = Integer.parseInt(sc.nextLine());
                        quesno = jumptoquesno - 1;
                        continue;
                    } else if (input.equalsIgnoreCase("submit")) {
                        System.out.println("Are you sure you want to submit?? Press y for yes and n for continue..");
                        if (input.equals("y")) {
                            break;//write code
                        } else {
                            continue;
                        }
                    } else {
                        answers[quesno] = input;

                    }
                    quesno++;


                }
                new JDBCCONNECT().saveTestAnswers(studentId, testid, answers);




        } catch (Exception ex){

        }


    }

}
