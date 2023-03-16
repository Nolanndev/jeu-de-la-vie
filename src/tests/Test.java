package tests;

import java.io.IOException;
import main.exceptions.*;
import tests.utils.*;

public class Test {

    public static void main(String[] args) throws IOException, ProfileNameException {

        boolean ok = true;
        TestProfileManager profileManagerTester = new TestProfileManager();
        ok = ok && profileManagerTester.testLoad();
        ok = ok && profileManagerTester.testValidProfileName();
        ok = ok && profileManagerTester.testSave();

        System.out.println(ok ? "All test OK" : "At least one test KO");
    }

}
