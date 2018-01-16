package GUI;

import Loader.Cashe;
import assignment1.HashMap;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author chrisrk192
 */

public class MainApp extends javax.swing.JFrame {
    public static final int URLSIZE = 150;
    public String baseURL;
    public String finalURL;
    //?datasetid=GHCND&locationid=ZIP:28801&startdate=2016-05-01&enddate=2016-05-05&limit=1000";
    private final String startDateKey;
    private final String endDateKey;
    private final String dataSetIDKey;
    private final String limitKey;
    private final String locationIDKey;
    private final String dataTypeIDKey;
    private final String dataCategoryIDKey;
    
    public String dataCategoryIDVal;
    public String startDateVal;
    public String endDateVal;
    public String dataSetVal;
    public String limitVal;
    public static String dataTypeID;
    public int locationVal;
    
    public HashMap theMap, theMapTo;
    public HashMap [] StatesMap;
    public Cashe theCashe;
    /**
     * Creates new form MainApp
     */
    public MainApp() {
        baseURL = "https://www.ncdc.noaa.gov/cdo-web/api/v2/data?";
        this.startDateKey = "startdate=";
        this.dataSetIDKey = "datasetid=";
        this.endDateKey = "enddate=";
        this.limitKey = "limit=";
        this.locationIDKey = "locationid=FIPS:";
        this.dataTypeIDKey = "datatypeid=";
        this.dataCategoryIDKey = "datacategoryid=";
        
        this.dataCategoryIDVal = "PRCP";
        this.dataTypeID = "PRCP";
        this.startDateVal = "2016-12-01";
        this.endDateVal = "2016-12-01";
        this.dataSetVal = "GHCND";//Daily summaries //"GSOM";
        this.limitVal = "100";
        this.locationVal = 36;//new york
        
        
        finalURL = baseURL + dataSetIDKey+dataSetVal+
                "&"+dataTypeIDKey+dataTypeID+
                //"&"+dataCategoryIDKey+dataCategoryIDVal+//changed this
                "&"+startDateKey+startDateVal+
                "&"+endDateKey+endDateVal+
                "&"+locationIDKey+locationVal+
                "&"+limitKey+limitVal;
        theMap = new HashMap();
        theMapTo = new HashMap();
        theCashe = new Cashe();
        initComponents();
        
    }

    /**
     * Creates the url with GUI set parameters
     */
    private String generateURL(){
        finalURL = baseURL + dataSetIDKey+dataSetVal+
                "&"+dataTypeIDKey+dataTypeID+
                //"&"+dataCategoryIDKey+dataCategoryIDVal+//changed this
                "&"+startDateKey+startDateVal+
                "&"+endDateKey+endDateVal+
                "&"+locationIDKey+locationVal+
                "&"+limitKey+limitVal;
        return finalURL;
    }
    
    /**
     * Parses State string and returns the NOAA integer for that state 
     * @param state the state name, Capitalized with spaces as needed
     * @return NOAA identifier
     */
    private int parseState(String state){
        int num = 0;
        switch(state){
            case "Delaware":
                num = 10;
                break;
            case "District of Columbia":
                num = 11;
                break;
            case "Florida":
                num = 12;
                break;
            case "Georgia":
                num = 13;
                break;
            case "Hawaii":
                num = 15;
                break;
            case "Idaho":
                num = 16;
                break;
            case "Illinois":
                num = 17;
                break;
            case "Indiana":
                num = 18;
                break;
            case "Iowa":
                num = 19;
                break;
            case "Kansas":
                num = 20;
                break;
            case "Kentucky":
                num = 21;
                break;
            case "Louisiana":
                num = 22;
                break;
            case "Maine":
                num = 23;
                break;
            case "Maryland":
                num = 24;
                break;
            case "Massachusetts":
                num = 25;
                break;
            case "Michigan":
                num = 26;
                break;
            case "Minnesota":
                num = 27;
                break;
            case "Mississippi":
                num = 28;
                break;
            case "Missouri":
                num = 29;
                break;
            case "Montana":
                num = 30;
                break;
            case "Nebraska":
                num = 31;
                break;
            case "Nevada":
                num = 32;
                break;
            case "New Hampshire":
                num = 33;
                break;
            case "New Jersey":
                num = 34;
                break;
            case "New Mexico":
                num = 35;
                break;
            case "New York":
                num = 36;
                break;
            case "North Carolina":
                num = 37;
                break;
            case "North Dakota":
                num = 38;
                break;
            case "Ohio":
                num = 39;
                break;
            case "Oklahoma":
                num = 40;
                break;
            case "Oregon":
                num = 41;
                break;
            case "Pennsylvania":
                num = 42;
                break;
            case "Rhode Island":
                num = 44;
                break;
            case "South Carolina":
                num = 45;
                break;
            case "South Dakota":
                num = 46;
                break;
            case "Tennessee":
                num = 47;
                break;  
            case "Texas":
                num = 48;
                break;
            case "Utah":
                num = 49;
                break;
            case "Vermont":
                num = 50;
                break;
            case "Virginia":
                num = 51;
                break;
            case "Washington":
                num = 53;
                break;
            case "West Virginia":
                num = 54;
                break;
            case "Wisconsin":
                num = 55;
                break;
            case "Wyoming":
                num = 56;
                break;   
            default:
                System.err.println("INVALID STATE");
                num = 0;
                break;
        }
        return num;
    }
    
    /**
     * Parses NOAA State number and returns name of that state 
     * @param stt NOAA state identifier
     * @return State name
     */
    private String parseIntToState(int stt){
        String num;
        switch(stt){
            case 10:
                num = "Delaware";
                break;
            case 11:
                num = "District of Columbia";
                break;
            case 12:
                num = "Florida";
                break;
            case 13:
                num = "Georgia";
                break;
            case 15:
                num = "Hawaii";
                break;
            case 16:
                num = "Idaho";
                break;
            case 17:
                num = "Illinois";
                break;
            case 18:
                num = "Indiana";
                break;
            case 19:
                num = "Iowa";
                break;
            case 20:
                num = "Kansas";
                break;
            case 21:
                num = "Kentucky";
                break;
            case 22:
                num = "Louisiana";
                break;
            case 23:
                num = "Maine";
                break;
            case 24:
                num = "Maryland";
                break;
            case 25:
                num = "Massachusetts";
                break;
            case 26:
                num = "Michigan";
                break;
            case 27:
                num = "Minnesota";
                break;
            case 28:
                num = "Mississippi";
                break;
            case 29:
                num = "Missouri";
                break;
            case 30:
                num = "Montana";
                break;
            case 31:
                num = "Nebraska";
                break;
            case 32:
                num = "Nevada";
                break;
            case 33:
                num = "New Hampshire";
                break;
            case 34:
                num = "New Jersey";
                break;
            case 35:
                num = "New Mexico";
                break;
            case 36:
                num = "New York";
                break;
            case 37:
                num = "North Carolina";
                break;
            case 38:
                num = "North Dakota";
                break;
            case 39:
                num = "Ohio";
                break;
            case 40:
                num = "Oklahoma";
                break;
            case 41:
                num = "Oregon";
                break;
            case 42:
                num = "Pennsylvania";
                break;
            case 44:
                num = "Rhode Island";
                break;
            case 45:
                num = "South Carolina";
                break;
            case 46:
                num = "South Dakota";
                break;
            case 47:
                num = "Tennessee";
                break;  
            case 48:
                num = "Texas";
                break;
            case 49:
                num = "Utah";
                break;
            case 50:
                num = "Vermont";
                break;
            case 51:
                num = "Virginia";
                break;
            case 53:
                num = "Washington";
                break;
            case 54:
                num = "West Virginia";
                break;
            case 55:
                num = "Wisconsin";
                break;
            case 56:
                num = "Wyoming";
                break;   
            default:
                System.err.println("INVALID STATE");
                num = "NaN";
                break;
        }
        return num;
    }
    
    /**
     * Verifies that the string is a valid state name.
     * @param toVer The state name to verify
     * @return true if toVer is a state name
     */
    private boolean verifyRealType(String toVer){
        boolean isReal;
        
        switch(toVer){
            case "TOBS":
            case "PRCP":
            case "TEMP":
            //case "GHCND":
            //case "GSOM":
            //case "GSOY":
                isReal = true;
                break;
            default:
                isReal = false;
                break;
        }
        return isReal;
    }
    
    /**
     * Loads in a Data set from disk if it is saved, or downloads it and saves it otherwise
     * @param url
     * @param locationVal
     * @return 
     */
    private HashMap getHashMapData(String url, int locationVal){
        HashMap aMap = new HashMap();
        generateURL();
        if(theCashe.HasURL(url)){
            System.out.println("Loading from File/Mem");
            aMap = theCashe.LoadItem(url); 
        }else{
            System.out.println("Loading from DL");
            aMap.setURL(finalURL);
            aMap.setTimeDownloaded(System.currentTimeMillis());
            aMap.FromDate = endDateVal;
            aMap.ToDate  =  startDateVal;
            assignment1.Assignment1.gather(aMap, finalURL,locationVal, dataTypeID);
            
            theCashe.Add(aMap);
            theCashe.Save();
            //Sleep so NOAA doesnt drop the connection
            //try{
            //    System.out.println("Sleep");
            //    Thread.sleep(10);//was 100
            //    System.out.println("Woke");
            //} catch(InterruptedException e){
            //    System.err.println(e);
            //}
        }
        return aMap;        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        log2 = new javax.swing.JTextArea();
        collectData = new javax.swing.JButton();
        info = new javax.swing.JLabel();
        numResults = new javax.swing.JLabel();
        similarity = new javax.swing.JLabel();
        baseState = new javax.swing.JLabel();
        cmpState = new javax.swing.JLabel();
        collectDataTo = new javax.swing.JButton();
        numResultsTo = new javax.swing.JLabel();
        baseInput = new javax.swing.JTextField();
        toInput = new javax.swing.JTextField();
        simBttn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        log = new javax.swing.JTextArea();
        title = new javax.swing.JLabel();
        find = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        toFind = new javax.swing.JTextField();
        closest = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        limitField = new javax.swing.JTextField();
        startField = new javax.swing.JTextField();
        endField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        typeField = new javax.swing.JTextField();
        preLoadButton = new javax.swing.JButton();
        clusterData = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        clusterTextArea = new javax.swing.JTextArea();
        testURL = new javax.swing.JTextField();
        testURLButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        xText = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        yText = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        closestClusterButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        pointCloseText = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        params = new javax.swing.JMenu();
        limit = new javax.swing.JMenu();
        start = new javax.swing.JMenu();
        end = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        log2.setEditable(false);
        log2.setColumns(20);
        log2.setRows(5);
        log2.setText("Log");
        jScrollPane1.setViewportView(log2);

        collectData.setText("Collect Data");
        collectData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collectDataActionPerformed(evt);
            }
        });

        info.setText("TestURL");

        numResults.setText("Results: 0");

        similarity.setText("Similarity:?");

        baseState.setText("Base State");

        cmpState.setText("Compare State");

        collectDataTo.setText("Collect Data");
        collectDataTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collectDataToActionPerformed(evt);
            }
        });

        numResultsTo.setText("Results: 0");

        baseInput.setText("New York");
        baseInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                baseInputFocusLost(evt);
            }
        });
        baseInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baseInputActionPerformed(evt);
            }
        });

        toInput.setText("Vermont");
        toInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                toInputFocusLost(evt);
            }
        });

        simBttn.setText("Calculate ");
        simBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simBttnActionPerformed(evt);
            }
        });

        log.setColumns(20);
        log.setRows(5);
        log.setText("Log");
        jScrollPane2.setViewportView(log);

        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("Daily State Summary Values");

        find.setText("Find Closest");
        find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findActionPerformed(evt);
            }
        });

        jLabel1.setText("Find State Most Closely Related to:");

        toFind.setText("New York");
        toFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toFindActionPerformed(evt);
            }
        });

        closest.setText("Closest:");

        jLabel2.setText("Parameters(Hit Enter To Verify)");

        jLabel3.setText("Limit");

        jLabel4.setText("Start Date");

        jLabel5.setText("End Date");

        limitField.setText("100");
        limitField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                limitFieldFocusLost(evt);
            }
        });
        limitField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limitFieldActionPerformed(evt);
            }
        });

        startField.setText("2016-12-01");
        startField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startFieldActionPerformed(evt);
            }
        });

        endField.setText("2016-12-01");
        endField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endFieldActionPerformed(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Compare 2 States");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Find Most Similar To");

        jLabel8.setText("Type");

        typeField.setText("PRCP");
        typeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeFieldActionPerformed(evt);
            }
        });

        preLoadButton.setText("PreLoad");
        preLoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preLoadButtonActionPerformed(evt);
            }
        });

        clusterData.setText("Cluster");
        clusterData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clusterDataActionPerformed(evt);
            }
        });

        clusterTextArea.setColumns(20);
        clusterTextArea.setRows(5);
        jScrollPane3.setViewportView(clusterTextArea);

        testURL.setText("https://www.ncdc.noaa.gov/cdo-web/api/v2/datasets/GHCND");

        testURLButton.setText("TEST URL");
        testURLButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testURLButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Defrag");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel9.setText("Find Closest to Point");

        xText.setText("0.0");

        jLabel10.setText("X");

        yText.setText("0.0");

        jLabel11.setText("Y");

        closestClusterButton.setText("Find");
        closestClusterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closestClusterButtonActionPerformed(evt);
            }
        });

        pointCloseText.setColumns(20);
        pointCloseText.setRows(5);
        jScrollPane4.setViewportView(pointCloseText);

        file.setText("File");
        jMenuBar1.add(file);

        params.setText("Parameters");

        limit.setText("Limit");
        limit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                limitMouseClicked(evt);
            }
        });
        limit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limitActionPerformed(evt);
            }
        });
        params.add(limit);

        start.setText("Start");
        start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startMouseClicked(evt);
            }
        });
        params.add(start);

        end.setText("End");
        end.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                endMouseClicked(evt);
            }
        });
        params.add(end);

        jMenuBar1.add(params);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(startField)
                                    .addComponent(endField)
                                    .addComponent(limitField, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                    .addComponent(typeField))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(find)
                                    .addComponent(closest)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(toFind)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(testURL, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(testURLButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(37, 37, 37)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(simBttn)
                                                    .addComponent(similarity)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(collectData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addGap(10, 10, 10)
                                                            .addComponent(numResults))
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addGap(16, 16, 16)
                                                            .addComponent(baseState))
                                                        .addComponent(baseInput, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGap(53, 53, 53)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(collectDataTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(numResultsTo))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(cmpState))
                                            .addComponent(toInput, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(39, 39, 39))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(93, 93, 93)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(closestClusterButton)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel10)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(xText))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel11)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(yText, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 43, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(clusterData)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9)))))
                .addGap(116, 116, 116)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(preLoadButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(title)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(preLoadButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(limitField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(startField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(endField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(typeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(toFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(find)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closest)))
                        .addGap(1, 1, 1)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmpState)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(baseState)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(baseInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(collectData)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numResults))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(toInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(collectDataTo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numResultsTo)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(simBttn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(similarity)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(testURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(testURLButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clusterData)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(xText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(yText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closestClusterButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * Collects State data using the GUI set parameters and creates a new map 
     *      and loads it into the base map.
     * @param evt 
     */
    private void collectDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collectDataActionPerformed
        //collect data
        info.setText("Collecting...");
        theMap = new HashMap();
        long startTime = System.currentTimeMillis();
        locationVal = parseState(baseInput.getText());
        generateURL();
        theMap = getHashMapData(finalURL, locationVal);
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
        log.setText(theMap.PrintMap());
        info.setText("Data Collected.");
        numResults.setText("Results: " + theMap.getCount());
        
    }//GEN-LAST:event_collectDataActionPerformed

    private void limitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limitActionPerformed
        //NOT USED
    }//GEN-LAST:event_limitActionPerformed
    
    /**
     * Updates the max number of readings taken from NOAA
     * @param evt 
     */
    private void limitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_limitMouseClicked
        
        int testLimit = 0;
        String limitString;
        while(testLimit < 1){
            limitString = (String)JOptionPane.showInputDialog(
                    "Limit\n"
                            + "Current Value = " + limitVal + "\n"
                            + "The number of readings to gather from the data base");
            //testLimit = Integer.getInteger(limitString);
            try{
            System.out.println(limitString);
            testLimit = Integer.parseInt(limitString);
            } catch(NumberFormatException e){
                testLimit = 0;
            }
            
        } 
        limitVal = testLimit + "";
        generateURL();
    }//GEN-LAST:event_limitMouseClicked

    /**
     * Verifies that the date provided is valid and updates the url
     * @param evt 
     */
    private void startMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startMouseClicked
       
        boolean accepted = false;
        String testStart = "";
        while(!accepted){
            testStart = (String)JOptionPane.showInputDialog(
                    "Start Date\n"
                        + "Current Value = " + startDateVal + "\n"
                        + "YYYY-MM-DD");
            System.out.println(testStart);
            if(testStart.charAt(4) == '-' && testStart.charAt(7) == '-')
                accepted = true;
        } 
        startDateVal = testStart;
        generateURL();
    }//GEN-LAST:event_startMouseClicked

    /**
     * Verifies that the date provided is valid and updates the url
     * @param evt 
     */
    private void endMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_endMouseClicked
       
        boolean accepted = false;
        String testEnd = "";
        while(!accepted){
            testEnd = (String)JOptionPane.showInputDialog(
                    "End Date\n"
                        + "Current Value = " + endDateVal + "\n"
                        + "YYYY-MM-DD");
            System.out.println(testEnd);
            if(testEnd.charAt(4) == '-' && testEnd.charAt(7) == '-')
                accepted = true;
        } 
        endDateVal = testEnd;
        generateURL();
    }//GEN-LAST:event_endMouseClicked

    /**
     * Collects State data using the GUI set parameters and creates a new map 
     *      and loads it into the comparison map.
     * @param evt 
     */
    private void collectDataToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collectDataToActionPerformed
        //collect data
        info.setText("Collecting...");
        theMapTo = new HashMap();
        long startTime = System.currentTimeMillis();
        locationVal = parseState(toInput.getText());
        numResultsTo.setText("Results: " + theMapTo.getCount());
        generateURL();
        theMapTo = getHashMapData(finalURL, locationVal);  
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
        log2.setText(theMapTo.PrintMap());
        info.setText("Data Collected.");
        numResultsTo.setText("Results: " + theMapTo.getCount());
    }//GEN-LAST:event_collectDataToActionPerformed

    private void baseInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baseInputActionPerformed
        // NOT USED
    }//GEN-LAST:event_baseInputActionPerformed

    /**
     * Calculates the similarity between theMap and theMapTo then updates the
     *      GUI.
     * @param evt 
     */
    private void simBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simBttnActionPerformed
        if(theMap != null && theMapTo != null){
            double sim = assignment1.Assignment1.LinRegres(theMap, theMapTo);
            if(Double.isNaN(sim))
                similarity.setText("Similarity: " + 0.0);
            else 
                similarity.setText("Similarity: " + sim);
        }else
            similarity.setText("Null map");
    }//GEN-LAST:event_simBttnActionPerformed

    /**
     * Verifies that the state provided is valid 
     * @param evt 
     */
    private void toInputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_toInputFocusLost
        int s = parseState(toInput.getText());
        if(s == 0)
            toInput.setText("INVALID");
            
    }//GEN-LAST:event_toInputFocusLost

    /**
     * Verifies that the state provided is valid 
     * @param evt 
     */
    private void toFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toFindActionPerformed
        int s = parseState(toInput.getText());
        if(s == 0)
            toInput.setText("INVALID");
    }//GEN-LAST:event_toFindActionPerformed

    /**
     * Finds the state most closely related to the input state using a 
     *      linear regression algorithm applied to each state
     * @param evt 
     */
    private void findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findActionPerformed
        
        int maxNumStates = 56;
        int baseNum = 0;
        double maxRelat = 0;
        StatesMap = new HashMap[maxNumStates];
        long startTime = System.currentTimeMillis();
        //get the base map to cmp to;
        locationVal = parseState(toFind.getText());
        baseNum = locationVal;
        generateURL();
        
        theMap = getHashMapData(finalURL, locationVal);

        //loop through all 50 states and get data//i started at 11 before
        for(int i = 0; i < maxNumStates; i++){
            locationVal = i;
            StatesMap[i] = new HashMap();
            generateURL();
            
            StatesMap[i] = getHashMapData(finalURL, locationVal);
            if(StatesMap[i].getCount() > 0){//calculate the regression for each state
                double newLin = assignment1.Assignment1.LinRegres(theMap, StatesMap[i]);
                if(newLin > maxRelat && i != baseNum){ 
                    maxRelat = newLin;
                    closest.setText("Closest: " + parseIntToState(i) +" with R value" + maxRelat);
                }
            }
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total Find time: "+totalTime);
    }//GEN-LAST:event_findActionPerformed

    /**
     * Verifies the limit value and updates the url
     * @param evt 
     */
    private void limitFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limitFieldActionPerformed
        int testLimit = 0;
        String limitString;
        limitString = limitField.getText();
        System.out.println(limitString);
        try{
            testLimit = Integer.parseInt(limitString);
        } catch(NumberFormatException e){
            testLimit = 0;
            limitField.setText(""+testLimit);
        }
        limitVal = testLimit + "";
        generateURL();
    }//GEN-LAST:event_limitFieldActionPerformed

    /**
     * Verifies that the upper date range is valid and updates the url
     * @param evt 
     */
    private void endFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endFieldActionPerformed
        String testEnd = "";
        
        testEnd = endField.getText();
        System.out.println(testEnd);
        if(testEnd.charAt(4) == '-' && testEnd.charAt(7) == '-'){
        } else {
            endField.setText("YYYY-MM-DD");
        }
        endDateVal = testEnd;
        generateURL();
    }//GEN-LAST:event_endFieldActionPerformed

    /**
     * Verifies that the lower date range is valid and updates the url
     * @param evt 
     */
    private void startFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startFieldActionPerformed
        String testStart = "";
        
        testStart = startField.getText();
        System.out.println(testStart);
        if(testStart.charAt(4) == '-' && testStart.charAt(7) == '-'){
            ;
        } else {
            startField.setText("YYYY-MM-DD");
        }
        startDateVal = testStart;
        generateURL();
    }//GEN-LAST:event_startFieldActionPerformed

    /**
     * Verifies that the date type is valid
     * @param evt 
     */
    private void typeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeFieldActionPerformed
        String testType = "";

        testType = typeField.getText();
        System.out.println(testType);
        if(!verifyRealType(testType)){
            typeField.setText("INVALID");
            testType = "PRCP";
        }
        dataTypeID = testType;
        dataCategoryIDVal = testType;
        generateURL();

    }//GEN-LAST:event_typeFieldActionPerformed

    /**
     * Verifies the state is valid
     * @param evt 
     */
    private void baseInputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_baseInputFocusLost
        int s = parseState(baseInput.getText());
        if(s == 0)
            baseInput.setText("INVALID");
    }//GEN-LAST:event_baseInputFocusLost

    /**
     * Verifies the limit and updates the url
     * @param evt 
     */
    private void limitFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_limitFieldFocusLost
        int testLimit = 0;
        String limitString;
        limitString = limitField.getText();
        System.out.println(limitString);
        try{
            testLimit = Integer.parseInt(limitString);
        } catch(NumberFormatException e){
            testLimit = 0;
            limitField.setText(""+testLimit);
        }
        limitVal = testLimit + "";
        generateURL();
    }//GEN-LAST:event_limitFieldFocusLost
        
    /**
     * populate the cashe with 20 days of data for each 50 states eash being 2 different urls so 2000
     * @param evt 
     */
    private void preLoadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preLoadButtonActionPerformed
        int maxNumStates = 56;
        String preLoadDate = "2016-12-";
        String useDate;
        dataTypeID = "PRCP";
        for(int dayCount = 1;dayCount < 20; dayCount++){
            if (dayCount < 10) {
                useDate = preLoadDate + "0" + dayCount;
            }else{
                useDate = preLoadDate + dayCount;
            }
            startDateVal = useDate;
            endDateVal = useDate;
            generateURL();
            //get for all 50 on this date
            for(int i = 0; i < maxNumStates; i++){
                locationVal = i;
                generateURL();
                getHashMapData(finalURL, locationVal);
                //Sleep so NOAA doesnt drop the connection
                
            }
        }
        dataTypeID = "TOBS";
        for(int dayCount = 1;dayCount < 20; dayCount++){
            if (dayCount < 10) {
                useDate = preLoadDate + "0" + dayCount;
            }else{
                useDate = preLoadDate + dayCount;
            }
            startDateVal = useDate;
            endDateVal = useDate;
            generateURL();
            //get for all 50 on this date
            for(int i = 0; i < maxNumStates; i++){
                locationVal = i;
                generateURL();
                getHashMapData(finalURL, locationVal);
                //Sleep so NOAA doesnt drop the connection
                try{
                    System.out.println("Sleep");
                    Thread.sleep(10);//was 100
                    System.out.println("Woke");
                } catch(InterruptedException e){
                    System.err.println(e);
                }
            }
        }   
    }//GEN-LAST:event_preLoadButtonActionPerformed

    /**
     * Displays a category and set of similar keys (using cluster membership)
     * Categories are: Warm Dry,Warm Wet, Cold Dry, Cold Wet.
     * Categories are balanced 50% on temperature 50% on precipitation
     * @param evt 
     */
    private void clusterDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clusterDataActionPerformed
        int K = 4;
        double convrgncLim = 1.0;
        ArrayList<Point> allPoints;
        ArrayList<Point>[] subLists = new ArrayList[K];
        Point[] means = new Point[K];
        Point[] newMeans = new Point[K];
        Point[] closests = new Point[K];
        Double[] closestDoubles = new Double[K];
        int closestIndex;
        double closestDist;
        boolean doAgainFlag;
        int meanAttempLimit = 100;
        int meanAttemps = 0;
        int maxNumStates = 56;
        //populate the linked list of points 
        String preLoadDate = "2016-12-";
        String useDate;
        allPoints = new ArrayList<>();
        Point avg;
        HashMap prcp, temp;
        
        
        for(int dayCount = 1;dayCount < 20; dayCount++){
            if (dayCount < 10) {
                useDate = preLoadDate + "0" + dayCount;
            }else{
                useDate = preLoadDate + dayCount;
            }
            
            startDateVal = useDate;
            endDateVal = useDate;
            for(int i = 0; i < maxNumStates; i++){
                locationVal = i;
                dataTypeID = "PRCP";
                generateURL();
                prcp = getHashMapData(finalURL, locationVal);
                dataTypeID = "TOBS";
                generateURL();
                temp = getHashMapData(finalURL, locationVal);
                avg = new Point();
                avg.key = finalURL;//all use tobs
                avg.x = assignment1.Assignment1.AverageHashMapVal(temp);
                avg.y = assignment1.Assignment1.AverageHashMapVal(prcp);
                if(!Double.isNaN(avg.y) && !Double.isNaN(avg.x))//handle empty data sets
                    allPoints.add(avg);
                
            }
        }  
            
        //find for 20 days(50 some states, 2 urls per = 1000 items)
        //break list into subs lists based on the mean they are closest to
            //while the true means have not been found
        for(int i = 0; i < K; i++){
                subLists[i] = new ArrayList<>();
                means[i] = new Point(0,0);
                closestDoubles[i] = Double.MAX_VALUE;
        }
        //inital Set of centroids
        means[0] = new Point(60,0);
        means[1] = new Point(60,100);
        means[2] = new Point(-60,0);
        means[3] = new Point(-60,100);
        
        do{
            //remake the sublists
            for(int i = 0; i < K; i++){
                subLists[i].clear();
            }
            //for each item       
            for(int itemIndex = 0; itemIndex < allPoints.size(); itemIndex++){
                //for each mean
               
                closestIndex = 0;
                closestDist = Double.MAX_VALUE;
                double testDist;
                //compare to each existing center
                for(int j = 0; j< K; j++){
                    testDist = Point.EuclideanDist(allPoints.get(itemIndex), means[j]);
                    if(Double.isInfinite(testDist))
                        System.out.println("break here");
                    if(testDist < closestDist && testDist != -1){
                        closestIndex = j;
                        closestDist = testDist;
                    }
                }
   //done for every point             
                if(closestDist != -1){//they were not null
                    subLists[closestIndex].add(allPoints.get(itemIndex));//adds the point to the closest mean's set
                    //checks to see if the point is the closest to the center, if so, saves it as the closest point
                    if(closestDoubles[closestIndex] > closestDist){//////////////////////////////////////////////////////////////
                        closests[closestIndex] = allPoints.get(itemIndex);
                        closestDoubles[closestIndex] = closestDist;
                    }
                }
            }       
            //find the new mean of the sub lists, that is the new mean
            double meanX,meanY;
            Point current;
            for(int j = 0; j< K; j++){
                //calc average
                meanX = 0;
                meanY = 0;
                for(int n = 0; n < subLists[j].size(); n++){
                    current = subLists[j].get(n);
                    meanX += current.x;
                    meanY += current.y;
                }
                meanX = meanX / subLists[j].size();
                meanY = meanY / subLists[j].size();
                if(!Double.isNaN(meanX) && !Double.isNaN(meanY))
                    newMeans[j]  = new Point(meanX ,meanY);
                else if(Double.isNaN(meanX) && Double.isNaN(meanY))
                    newMeans[j] = new Point(0,0);
                else if (!Double.isNaN(meanX)) 
                    newMeans[j] = new Point(meanX,0);
                else
                    newMeans[j] = new Point(0,meanY);
                    
            }
            //if the new means are far way from the old redo( means = newMeans; newMeans = new )
            doAgainFlag = false;
            for(int j = 0; j < K; j++){//check each new mean to each old mean
                double delta = Point.EuclideanDist(means[j],newMeans[j]);
                if (delta > convrgncLim) {
                    doAgainFlag = true;
                }
            }
            if(doAgainFlag){
                for(int i = 0; i < K; i++){
                    means[i] = newMeans[i];
                }
                //means = newMeans//;//error here, need a deep copy, not shalow
                newMeans = new Point[K];
                System.out.println("another one");
            }
        } while(doAgainFlag && meanAttemps < meanAttempLimit);
        //at this point we have the true set of means, and the true subsets
            //we can now display
            String out = "";
        for (int i = 0; i < K; i++) {
            switch(i){
                case 0:
                    out += "Warm Dry\n";
                    break;
                case 1:
                    out += "Warm Wet\n";
                    break;
                case 2:
                    out += "Cold Dry\n";
                    break;
                case 3:
                    out += "Cold Wet\n";
                    break;
            }
            out += "Cluser at :" + means[i].x + ", "+ means[i].y + "\n";
            out += "Closest point: " + closests[i].key  + "\n";
            out += "With distance: " + closestDoubles[i]  + "\n";
            for(int j = 0; j < subLists[i].size();j++){
                out += "     " + subLists[i].get(j).key + "\n";
            }
        }
        clusterTextArea.setText(out);
    }//GEN-LAST:event_clusterDataActionPerformed
    
    /**
     * Intended to aid in debugging
     * Prints to System.out the json downloaded from the link
     * @param evt 
     */
    private void testURLButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testURLButtonActionPerformed
        
        System.out.println(assignment1.Assignment1.GetJSON(assignment1.Assignment1.GetConnection(testURL.getText())));
    }//GEN-LAST:event_testURLButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        theCashe.Defragment();
    }//GEN-LAST:event_jButton1ActionPerformed

    
    private void closestClusterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closestClusterButtonActionPerformed
        double x = Double.parseDouble(xText.getText());
        double y = Double.parseDouble(yText.getText());
        Point referencePoint = new Point(x, y);
        Point theClosest = new Point();
        int K = 4;
        double convrgncLim = 1.0;
        ArrayList<Point> allPoints;
        ArrayList<Point>[] subLists = new ArrayList[K];
        Point[] means = new Point[K];
        Point[] newMeans = new Point[K];
        Point[] closests = new Point[K];
        Double[] closestDoubles = new Double[K];
        int closestIndex;
        double closestDist;
        boolean doAgainFlag;
        int meanAttempLimit = 100;
        int meanAttemps = 0;
        int maxNumStates = 56;
        //populate the linked list of points 
        String preLoadDate = "2016-12-";
        String useDate;
        allPoints = new ArrayList<>();
        Point avg;
        HashMap prcp, temp;
        
        
        for(int dayCount = 1;dayCount < 20; dayCount++){
            if (dayCount < 10) {
                useDate = preLoadDate + "0" + dayCount;
            }else{
                useDate = preLoadDate + dayCount;
            }
            
            startDateVal = useDate;
            endDateVal = useDate;
            for(int i = 0; i < maxNumStates; i++){
                locationVal = i;
                dataTypeID = "PRCP";
                generateURL();
                prcp = getHashMapData(finalURL, locationVal);
                dataTypeID = "TOBS";
                generateURL();
                temp = getHashMapData(finalURL, locationVal);
                avg = new Point();
                avg.key = finalURL;//all use tobs
                avg.x = assignment1.Assignment1.AverageHashMapVal(temp);
                avg.y = assignment1.Assignment1.AverageHashMapVal(prcp);
                if(!Double.isNaN(avg.y) && !Double.isNaN(avg.x))//handle empty data sets
                    allPoints.add(avg);
                
            }
        }  
            
        //find for 20 days(50 some states, 2 urls per = 1000 items)
        //break list into subs lists based on the mean they are closest to
            //while the true means have not been found
        for(int i = 0; i < K; i++){
                subLists[i] = new ArrayList<>();
                means[i] = new Point(0,0);
                closestDoubles[i] = Double.MAX_VALUE;
                
        }
        theClosest = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
        //inital Set of centroids
        means[0] = new Point(60,0);
        means[1] = new Point(60,100);
        means[2] = new Point(-60,0);
        means[3] = new Point(-60,100);
        
        do{
            //remake the sublists
            for(int i = 0; i < K; i++){
                subLists[i].clear();
            }
            //for each item       
            for(int itemIndex = 0; itemIndex < allPoints.size(); itemIndex++){
                //for each mean
               
                closestIndex = 0;
                closestDist = Double.MAX_VALUE;
                double testDist;
                //compare to each existing center
                for(int j = 0; j< K; j++){
                    testDist = Point.EuclideanDist(allPoints.get(itemIndex), means[j]);
                    if(Double.isInfinite(testDist))
                        System.out.println("break here");
                    if(testDist < closestDist && testDist != -1){
                        closestIndex = j;
                        closestDist = testDist;
                    }
                }
   //done for every point             
                if(closestDist != -1){//they were not null
                    subLists[closestIndex].add(allPoints.get(itemIndex));//adds the point to the closest mean's set
                    //the point is the closest to the reference
                    if(Point.EuclideanDist(referencePoint, theClosest) > Point.EuclideanDist(referencePoint, allPoints.get(itemIndex))){
                        theClosest = allPoints.get(itemIndex);
                    }
                    
                    //checks to see if the point is the closest to the center, if so, saves it as the closest point
                    if(closestDoubles[closestIndex] > closestDist){//////////////////////////////////////////////////////////////
                        closests[closestIndex] = allPoints.get(itemIndex);
                        closestDoubles[closestIndex] = closestDist;
                    }
                }
            }       
            //find the new mean of the sub lists, that is the new mean
            double meanX,meanY;
            Point current;
            for(int j = 0; j< K; j++){
                //calc average
                meanX = 0;
                meanY = 0;
                for(int n = 0; n < subLists[j].size(); n++){
                    current = subLists[j].get(n);
                    meanX += current.x;
                    meanY += current.y;
                }
                meanX = meanX / subLists[j].size();
                meanY = meanY / subLists[j].size();
                if(!Double.isNaN(meanX) && !Double.isNaN(meanY))
                    newMeans[j]  = new Point(meanX ,meanY);
                else if(Double.isNaN(meanX) && Double.isNaN(meanY))
                    newMeans[j] = new Point(0,0);
                else if (!Double.isNaN(meanX)) 
                    newMeans[j] = new Point(meanX,0);
                else
                    newMeans[j] = new Point(0,meanY);
                    
            }
            //if the new means are far way from the old redo( means = newMeans; newMeans = new )
            doAgainFlag = false;
            for(int j = 0; j < K; j++){//check each new mean to each old mean
                double delta = Point.EuclideanDist(means[j],newMeans[j]);
                if (delta > convrgncLim) {
                    doAgainFlag = true;
                }
            }
            if(doAgainFlag){
                for(int i = 0; i < K; i++){
                    means[i] = newMeans[i];
                }
                //means = newMeans//;//error here, need a deep copy, not shalow
                newMeans = new Point[K];
                System.out.println("another one");
            }
        } while(doAgainFlag && meanAttemps < meanAttempLimit);
        //at this point we have the true set of means, and the true subsets
            //we can now display
            String out = "Closest: ";
            int index = 0; 
            double close = Double.MAX_VALUE;
            out += theClosest.key+ "\n";
            out += "x= " + theClosest.x + "\n";
            out += "y= " + theClosest.y + "\n";
            
            for (int i = 0; i < K; i++) {
                if(Point.EuclideanDist(referencePoint, means[i]) < close){
                    close = Point.EuclideanDist(referencePoint, means[i]);
                    
                }
            }
          
            switch(index){
                case 0:
                    out += "In cluster: Warm Dry\n";
                    break;
                case 1:
                    out += "In cluster: Warm Wet\n";
                    break;
                case 2:
                    out += "In cluster: Cold Dry\n";
                    break;
                case 3:
                    out += "In cluster: Cold Wet\n";
                    break;
            }
            
        
        pointCloseText.setText(out);
    }//GEN-LAST:event_closestClusterButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainApp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField baseInput;
    private javax.swing.JLabel baseState;
    private javax.swing.JLabel closest;
    private javax.swing.JButton closestClusterButton;
    private javax.swing.JButton clusterData;
    private javax.swing.JTextArea clusterTextArea;
    private javax.swing.JLabel cmpState;
    private javax.swing.JButton collectData;
    private javax.swing.JButton collectDataTo;
    private javax.swing.JMenu end;
    private javax.swing.JTextField endField;
    private javax.swing.JMenu file;
    private javax.swing.JButton find;
    private javax.swing.JLabel info;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JMenu limit;
    private javax.swing.JTextField limitField;
    private javax.swing.JTextArea log;
    private javax.swing.JTextArea log2;
    private javax.swing.JLabel numResults;
    private javax.swing.JLabel numResultsTo;
    private javax.swing.JMenu params;
    private javax.swing.JTextArea pointCloseText;
    private javax.swing.JButton preLoadButton;
    private javax.swing.JButton simBttn;
    private javax.swing.JLabel similarity;
    private javax.swing.JMenu start;
    private javax.swing.JTextField startField;
    private javax.swing.JTextField testURL;
    private javax.swing.JButton testURLButton;
    private javax.swing.JLabel title;
    private javax.swing.JTextField toFind;
    private javax.swing.JTextField toInput;
    private javax.swing.JTextField typeField;
    private javax.swing.JTextField xText;
    private javax.swing.JTextField yText;
    // End of variables declaration//GEN-END:variables
}
