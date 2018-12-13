/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dance;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;



public class CSVFile {

    static HashMap<String, String[]> danceShowData_dancemap = new HashMap<String, String[]>();
    static final ArrayList<String[]> danceShowData_dance = new ArrayList<String[]>();
    private String[] OneRow;
    static final ArrayList<String[]> danceShowData_danceGroups = new ArrayList<String[]>();
    private String[] OneRow1;
    static final ArrayList<String[]> danceShowData_runningOrder = new ArrayList<String[]>();
    private String[] OneRow2;

    public CSVFile() {
    }

    void callFunction() {
        //Read CSV files.......
        ReadCSVfileDances();
        ReadCSVfileDancesGroups();
        ReadCSVfileRunningOrder();
    }

    void ReadCSVfileDances() {
        try {
// Get the path of the CSV file..........
            File DataFile = new File(System.getProperty("user.dir") + "\\danceShowData_dances.csv");
            BufferedReader brd = new BufferedReader(new FileReader(DataFile));
            while (brd.ready()) {
                String st = brd.readLine();

                st = st.replace("  ", "").trim();
                st = st.replace("\t", " ").trim();

                OneRow = st.split(",");
                danceShowData_dance.add(OneRow);
                for (String[] dance : danceShowData_dance) {
                    danceShowData_dancemap.put(dance[0], dance);
                }
//                System.out.println(Arrays.toString(OneRow));
            } // end of while
        } // end of try
        catch (Exception e) {
            String errmsg = e.getMessage();
            System.out.println("File not found:" + errmsg);
        } // end of Catch

    }// end of ReadFile method

    public void ReadCSVfileDancesGroups() {
        try {

            File DataFile = new File(System.getProperty("user.dir") + "\\danceShowData_danceGroups.csv");
            BufferedReader brd = new BufferedReader(new FileReader(DataFile));
            while (brd.ready()) {
                String st = brd.readLine();
                st = st.replace("  ", "").trim();
                st = st.replace("\t", "").trim();
                OneRow1 = st.split(",");
                danceShowData_danceGroups.add(OneRow1);
                for (String[] dance : danceShowData_danceGroups) {
                    danceShowData_dancemap.put(dance[0], dance);
                }
//                System.out.println(Arrays.toString(OneRow1));
            } // end of while
        } // end of try
        catch (Exception e) {
            String errmsg = e.getMessage();
            System.out.println("File not found:" + errmsg);
        } // end of Catch
        
    }// end of ReadFile method

    public void ReadCSVfileRunningOrder() {
        try {

            File DataFile = new File(System.getProperty("user.dir") + "\\danceShowData_runningOrder.csv");
            BufferedReader brd = new BufferedReader(new FileReader(DataFile));
            while (brd.ready()) {
                String st = brd.readLine();

                st = st.replace("  ", "");
                st = st.replace("\t", "").trim();
                OneRow2 = st.split(",");
                danceShowData_runningOrder.add(OneRow2);
                for (String[] dance : danceShowData_runningOrder) {
                    danceShowData_dancemap.put(dance[0], dance);
                }
//                System.out.println(Arrays.toString(OneRow2));
            } // end of while
        } // end of try
        catch (Exception e) {
            String errmsg = e.getMessage();
            System.out.println("File not found:" + errmsg);
        } // end of Catch
      
    }// end of ReadFile method

}




// end of C
