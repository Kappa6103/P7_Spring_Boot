package com.nnk.springboot.constant;

import com.nnk.springboot.models.*;
import com.nnk.springboot.models.config.Role;
import org.springframework.dao.DataRetrievalFailureException;

import java.time.LocalDateTime;

public class Const {
    final public static String ACCOUNT = "Account Test";
    final public static String ACCOUNT_UPDATED = "Account Test Updated";
    final public static String TYPE = "Type Test";
    final public static Double BID_QUANTITY = 10d;
    final public static Double BID_QUANTITY_UPDATED = 20d;

    final public static String NAME = "Rule Name";
    final public static String NAME_UPDATED = "Rule Name Updated";
    final public static String DESCRIPTION = "Description";
    final public static String JSON = "Json";
    final public static String TEMPLATE ="Template";
    final public static String SQL_STR = "SQL";
    final public static String SQL_PART = "SQL Part";

    final public static String MOODYS_RATING = "Moodys Rating";
    final public static String S_AND_P_RATING = "S and P Rating";
    final public static String FITCH_RATING = "Fitch Rating";
    final public static int ORDER_NUMBER = 10;
    final public static int ORDER_NUMBER_UPDATED = 20;

    final public static int CURVE_ID = 10;
    final public static int CURVE_ID_UPDATED = 20;
    final public static Double TERM = 10d;
    final public static Double TERM_UPDATED = 323d;
    final public static Double VALUE = 30d;

    final public static int PWD_HASHED_SIZE = 60;

    final public static String USERNAME = "TestUsername";
    final public static String USERNAME_UPDATED = "TestUsernameUpdated";
    final public static String PWD = "TestPwdfafdsdfsfsd234@#$";
    final public static String FULLNAME = "Test Fullname";
    final public static String ROLE_USER = "USER";
    final public static String ROLE_ADMIN = "ADMIN";
    final public static String EMPTY_FIELD = "";

    final public static Double ASKQUANTITY = 11d;
    final public static Double BID = 12d;
    final public static Double ASK = 13d;
    final public static String BENCHMARK = "Benchmark Test";
    final public static LocalDateTime BIDLISTDATE = LocalDateTime.of(2025, 10, 9, 15, 25);
    final public static String COMMENTARY = "Commentary Test";
    final public static String SECURITY = "Security Test";
    final public static String STATUS = "Status";
    final public static String TRADER = "Trader Test";
    final public static String BOOK = "Book Test";
    final public static String CREATION_NAME = "Creation Name Test";
    final public static LocalDateTime CREATION_DATE = LocalDateTime.of(2025, 10, 10, 15, 25);
    final public static String REVISION_NAME = "Revision name Test";
    final public static LocalDateTime REVISION_DATE = LocalDateTime.of(2025, 10, 11, 15, 25);
    final public static String DEAL_NAME = "Deal Name Test";
    final public static String DEAL_TYPE = "Deal Type Test";
    final public static String SOURCELIST_ID = "Source List Id";
    final public static String SIDE = "Side Test";

    final public static LocalDateTime AS_OF_DATE = LocalDateTime.of(2025, 10, 6, 15, 25);

    final public static Double BUY_QUANTITY = 123d;

    final public static String AUTH_USERNAME = "AUTH_USERNAM";
    final public static String AUTH_PWD = "AUTH_PWD";
    final public static String AUTH_ROLE_USER = Role.USER.toString();

    final public static BidList BIDLIST = new BidList(ACCOUNT, TYPE);
    final public static DataRetrievalFailureException DATA_ACCESS_EXCEPTION = new DataRetrievalFailureException("DB Access Failure");
    final public static CurvePoint CURVEPOINT = new CurvePoint();
    final public static Rating RATING = new Rating();
    final public static RuleName RULENAME = new RuleName();
    final public static Trade TRADE = new Trade();
}
