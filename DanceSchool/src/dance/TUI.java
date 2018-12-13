/**
 *
 */
package dance;

import static dance.CSVFile.danceShowData_dance;
import static dance.CSVFile.danceShowData_danceGroups;
import static dance.CSVFile.danceShowData_dancemap;
import static dance.CSVFile.danceShowData_runningOrder;
import java.awt.SystemColor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;
import java.io.Serializable;

/**
 * A simple text-based user interface for the dance show programme generator.
 *
 * @author S H S Wong
 * @version 08/11/2018
 */
public class TUI {

    private Controller controller = this.controller;
    private static Scanner stdIn;
    static int gapbtweentoshow;
    static String feasibiltyresult = "";

    public TUI(Controller controller) {

        this.controller = controller;

        // Creates a Scanner object for obtaining user input
        stdIn = new Scanner(System.in);

        while (true) {
            displayMenu();
            getAndProcessUserOption();
        }
    }

    private TUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Displays the header of this application and a summary of menu options.
     */
    private void displayMenu() {
        display(header());
        display(menu());
    }

    /**
     * Obtains an user option and processes it.
     */
    private void getAndProcessUserOption() {
        String command = stdIn.nextLine().trim();
        switch (command) {
            case "1": // Lists all dancers in a dance
                display("Lists all dancers in a dance...");
                display("Enter the name of the required dance:");
                String choice = stdIn.nextLine().trim();
                display(controller.listAllDancersIn(choice));

                break;
            case "2": // Lists all dances and respective dancers
                display("Lists all dance numb1ers and the respective dancers...");
                display(controller.listAllDancesAndPerformers());

                break;
            case "3": // Checks the feasibility of a given running order
                display("Checks the feasibility of a given running order...");
//                display("Enter the name of the CSV file with the proposed running order:");
//                String dataFile = stdIn.nextLine().trim();
//                display("Enter the required number of gaps between dances:");
//                String gap = stdIn.nextLine().trim();

                try {
                    display(controller.checkFeasibilityOfRunningOrder());
//                  display(controller.checkFeasibilityOfRunningOrder(dataFile, (new Integer(gap)).intValue()));
                } catch (NumberFormatException e) {
                    display("You have not entered the number of gaps as an integer. Sorry, no checking can be done.");
                }
                break;
            case "4": // Generates a running order of all dance numbers
                display("Generates a running order...");
                display("Enter the required number of gaps between dances:");

                try {
                    //gapbtweentoshow = new Integer(stdIn.nextLine());
                    display(controller.generateRunningOrder(gapbtweentoshow));

                } catch (NumberFormatException e) {
                    display("You have not entered the number of gaps as an integer. Sorry, no checking can be done.");
                }
                break;
            case "5": // Exits the application
                display("Goodbye!");
                System.exit(0);
                break;
            default: // Not a known command option
                display(unrecogniseCommandErrorMsg(command));
        }
    }

    /*
     * Returns a string representation of a brief title for this application as the header.
     * @return	a header
     */
    private static String header() {

        return "\nDance Show Programme Generator\n";
    }

    /*
     * Returns a string representation of the user menu.
     * @return	the user menu
     */
    private static String menu() {

        return "Enter the number associated with your chosen menu option.\n"
                + "1: List all dancers in a dance\n"
                + "2: List all dance numbers and the respective dancers\n"
                + "3: Check the feasibility of a given running order\n"
                + "4: Generate a running order\n"
                + "5: Exit this application\n";
    }

    /*
     * Displays the specified info for the user to view.
     * @param info	info to be displayed on the screen
     */
    private void display(String info) {

        System.out.println(info);
    }

    /*
     * Returns an error message for an unrecognised command.
     * 
     * @param error the unrecognised command
     * @return      an error message
     */
    private static String unrecogniseCommandErrorMsg(String error) {
        return String.format("Cannot recognise the given command: %s.%n", error);
    }

    public static void main(String[] args) throws IOException {
        CSVFile obj = new CSVFile();
        obj.callFunction();
        TUI obj1 = new TUI(new Controller() {
            @Override
            public String listAllDancersIn(String dance) {
                String returnresult = "";

                Set<String> keys = danceShowData_dancemap.keySet();
                for (String key : keys) {

                    if (key.equals(dance)) {
                        String[] result = danceShowData_dancemap.get(key);
                        if (result.length <= 1) {
                            System.out.println("No Dancers Avaliable");

                        } else {

                            for (int i = 1; i < result.length - 1; i++) {
                                // System.out.println("Searched Dancers:::" + result[i]);
                                returnresult = returnresult + result[i] + ",";

                            }
                        }
                    }
                }

                //System.out.println(s);
                return returnresult;
            }

            @Override
            public String listAllDancesAndPerformers() {
                String str1 = "";
                try {
                    Collections.sort(danceShowData_dance, new Comparator<String[]>() {
                        public int compare(String[] strings, String[] otherStrings) {
                            return strings[0].compareTo(otherStrings[0]);
                        }
                    });

                } catch (Exception e) {

                }
                for (int i = 0; i < danceShowData_dance.size(); i++) {

                    String str = "";
                    String result[] = danceShowData_dance.get(i);
                    try {
                        for (int j = 0; j < result.length; j++) {

                            str = str + result[j];
                            if (j == 0) {
                                System.out.print(result[j] + ":  ");
                            }
                            System.out.print(result[j + 1]);
                            System.out.print(",");
                        }

                    } catch (Exception e) {
                        System.out.println("No record Found ....");
                    }
                    System.out.println("");

                }

                return str1;
            }

            @Override
            public String checkFeasibilityOfRunningOrder() {//String filename, int gaps
                String ReturnResult = "";
                String str = "";
                String str1 = "";
                String tempDancer[] = null;
                String tempDancer1[] = null;
                int count = 1;
                try {
                    for (int i = 0; i < danceShowData_runningOrder.size(); i++) {
                        for (int j = i; j < count; j++) {
                            tempDancer = danceShowData_runningOrder.get(i);
                            tempDancer1 = danceShowData_runningOrder.get(count);
                            for (int k = 0; k < tempDancer.length; k++) {
                                for (int l = 0; l < tempDancer1.length; l++) {
                                    str = tempDancer[k];
                                    str1 = tempDancer1[l];
                                    if (str.equals(str1)) {
                                        ReturnResult = ReturnResult + "\n" + tempDancer[0] + " And  " + tempDancer1[0];
//                                        System.out.println("Dancer need a gap between performance" + tempDancer[0]);
                                    } else {
//                                        ReturnResult=tempDancer[0]+tempDancer1[0];
//                                        System.out.println("Running Order is fine...");
                                    }

                                }

                            }

                        }
                        count++;
                    }

                } catch (Exception e) {

                }
                feasibiltyresult = ReturnResult;
                return ReturnResult;
            }

            @Override
            public String generateRunningOrder(int gaps) {
                String str = "";
                String str1 = "";
                String returnresult = "";
                String ReturnResult="";

                String str11 = "";
                String tempDancer[] = null;
                String tempDancer1[] = null;
                int count = 1;
                try {
                    for (int i = 0; i < danceShowData_runningOrder.size(); i++) {
                        for (int j = i; j < count; j++) {
                            tempDancer = danceShowData_runningOrder.get(i);
                            tempDancer1 = danceShowData_runningOrder.get(count);
                            for (int k = 0; k < tempDancer.length; k++) {
                                for (int l = 0; l < tempDancer1.length; l++) {
                                    str = tempDancer[k];
                                    str11 = tempDancer1[l];
                                    if (str.equals(str11)) {
                                        
                                        
                                        ReturnResult = ReturnResult + "\n" + tempDancer[0] + " And  " + tempDancer1[0];
//                                        System.out.println("Dancer need a gap between performance" + tempDancer[0]);
                                    } else {
                                        //System.out.println(str);
//                                        ReturnResult=tempDancer[0]+tempDancer1[0];
//                                        System.out.println("Running Order is fine...");
                                    }

                                }

                            }

                        }
                        count++;
                    }

                } catch (Exception e) {

                }
                feasibiltyresult=ReturnResult;
                //System.out.println(ReturnResult);

                for (int i = 0; i < danceShowData_runningOrder.size(); i++) {
                    String result[] = danceShowData_runningOrder.get(i);

                    try {
                        for (int j = 0; j < result.length; j++) {
                            if (j == 0) {
                                str = str + result[j];
                            }
                            str = str + result[j + 1];
                            str = str + ",";
                        }
                    } catch (Exception e) {
//                        System.out.println("Please Insert The Integer Value For The Gap");
                    }
                    str = str + '\n';
                }
                System.out.println(str);
                if (!feasibiltyresult.equals("")) {
                    try {
                        String[] result = feasibiltyresult.split("\n");

                        for (int i = 1; i < result.length; i++) {

                            if (!result[i].equals("")) //                    System.out.println(result[i]+": Requaried gap for two Performance:"+gaps+" min"); 
                            {
                                System.out.println("Enter the required number of gaps for dances::"+result[i]);
                                gapbtweentoshow = new Integer(stdIn.nextLine());
                                returnresult = returnresult + result[i] + ": Requaried gap for two Performance:" + gapbtweentoshow + " min\n";
                            }

                        }
                    } catch (Exception e) {
                    }
                }

                return returnresult;

            }

        });

    }
}
