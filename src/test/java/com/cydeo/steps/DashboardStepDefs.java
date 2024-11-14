package com.cydeo.steps;

import com.cydeo.pages.DashBoardPage;
import com.cydeo.pages.LoginPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import com.sun.source.tree.AssertTree;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class DashboardStepDefs {

    LoginPage loginPage=new LoginPage();
    DashBoardPage dashBoardPage=new DashBoardPage();
    String actualUsersCount;
    String actualBooksCount;
    String actualBorrowedBooksCount;

    @Given("the user logged in as {string}")
    public void the_user_logged_in_as(String user) {
        loginPage.login(user);
        BrowserUtil.waitFor(4);

    }


    @When("user gets all information from modules")
    public void user_gets_all_information_from_modules() {
        actualUsersCount = dashBoardPage.getModuleCount("Users");
        System.out.println("actualUsersCount = " + actualUsersCount);

        actualBooksCount = dashBoardPage.getModuleCount("Books");
        System.out.println("actualBooksCount = " + actualBooksCount);

        actualBorrowedBooksCount = dashBoardPage.getModuleCount("Borrowed Books");
        System.out.println("actualBorrowedBooksCount = " + actualBorrowedBooksCount);

    }


    @Then("the informations should be same with database")
    public void the_informations_should_be_same_with_database() {

        // GET DATA FROM DATABASE AND COMPARE AGAINST UI

        /*
        Which one is actual / expected ?
        - Since UI getting data from DB and displaying UI will be actual in that scenario
        - Database will be expected Data

         */

        // CREATE CONN
        // DB_Util.createConnection();
        // Before("@db") --> will open connection

        // USERS

            // RUN QUERY USERS
            DB_Util.runQuery("select count(*) from users");
            // GET USERS COUNT
            String expectedUserCount = DB_Util.getFirstRowFirstColumn();
            System.out.println("expectedUserCount = " + expectedUserCount);
            // COMPARE
            Assert.assertEquals(expectedUserCount,actualUsersCount);

        // BOOKS
            // RUN QUERY
            DB_Util.runQuery("select count(*) from books");
            // GET BOOKS COUNT
            String expectedBooksCount = DB_Util.getCellValue(1, 1);
            System.out.println("expectedBooksCount = " + expectedBooksCount);
            // COMPARE
            Assert.assertEquals(expectedBooksCount,actualBooksCount);

        // BORROWED BOOKS
            // RUN QUERY
            DB_Util.runQuery("select count(*) from book_borrow where is_returned=0");
            // GET BORROWED BOOKS COUNT
            String expectedBorrowedBooksCount = DB_Util.getFirstRowFirstColumn();
            System.out.println("expectedBorrowedBooksCount = " + expectedBorrowedBooksCount);
            // COMPARE
            Assert.assertEquals(expectedBorrowedBooksCount,actualBorrowedBooksCount);


        // CLOSE CONN
        // DB_Util.destroy();
        // After("@db") --> will close connection

    }
}
