/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import assignment1.HashMap;
import java.net.HttpURLConnection;
import javax.swing.JOptionPane;

/**
 *
 * @author chrisrk192
 */
public class MainApp extends javax.swing.JFrame {
    public String baseURL;
    public String finalURL;
    //?datasetid=GHCND&locationid=ZIP:28801&startdate=2016-05-01&enddate=2016-05-05&limit=1000";
    private final String startDateKey;
    private final String endDateKey;
    private final String dataSetIDKey;
    private final String limitKey;
    private final String locationIDKey;
    //private final String dataTypeIDKey;
    
    public String startDateVal;
    public String endDateVal;
    public String dataSetVal;
    public String limitVal;
    //public String dataTypeID;
    public int locationVal;
    
    public HashMap theMap, theMapTo;
    public HashMap [] StatesMap;
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
        //this.dataTypeIDKey = "datatypeid=";
        
        //this.dataTypeID = "PRCP";
        this.startDateVal = "2016-11-01";
        this.endDateVal = "2016-11-01";
        this.dataSetVal = "GHCND";//"GSOM";
        this.limitVal = "1000";
        this.locationVal = 36;//new york
        
        
        finalURL = baseURL + dataSetIDKey+dataSetVal+
                //"&"+dataTypeIDKey+dataTypeID+
                "&"+startDateKey+startDateVal+
                "&"+endDateKey+endDateVal+
                "&"+locationIDKey+locationVal+
                "&"+limitKey+limitVal;
        theMap = new HashMap();
        theMapTo = new HashMap();
        
        initComponents();
        
    }

    /**
     * Creates the url with GUI set parameters
     */
    private void generateURL(){
        finalURL = baseURL + dataSetIDKey+dataSetVal+
                //"&"+dataTypeIDKey+dataTypeID+
                "&"+startDateKey+startDateVal+
                "&"+endDateKey+endDateVal+
                "&"+locationIDKey+locationVal+
                "&"+limitKey+limitVal;
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
    
    private boolean verifyRealType(String toVer){
        boolean isReal;
        
        switch(toVer){
            case "GHCND":
            case "GSOM":
                isReal = true;
                break;
            default:
                isReal = false;
                break;
        }
        return isReal;
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

        info.setText("Info");

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

        jLabel2.setText("Parameters");

        jLabel3.setText("Limit");

        jLabel4.setText("Start Date");

        jLabel5.setText("End Date");

        limitField.setText("1000");
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

        startField.setText("2016-11-01");
        startField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startFieldActionPerformed(evt);
            }
        });

        endField.setText("2016-11-01");
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

        typeField.setText("GHCND");
        typeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeFieldActionPerformed(evt);
            }
        });

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
            .addComponent(title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
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
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(find)
                                    .addComponent(toFind)
                                    .addComponent(closest)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(104, 104, 104))))
                    .addComponent(jLabel2)
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
                            .addComponent(typeField))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(limitField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(typeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                .addComponent(numResultsTo))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(toFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(find)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closest)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(simBttn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(similarity)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * Collects State data using the GUI set parameters and creates a new map 
     *      and it into the base map.
     * @param evt 
     */
    private void collectDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collectDataActionPerformed
        //collect data
        info.setText("Collecting...");
        theMap = new HashMap();
        long startTime = System.currentTimeMillis();
        locationVal = parseState(baseInput.getText());
        generateURL();
        assignment1.Assignment1.gather(theMap, finalURL,locationVal);
        log.setText(theMap.PrintMap());
        numResults.setText("Results: " + theMap.getCount());
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
        info.setText("Collected Data.");
        
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

    
    private void collectDataToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collectDataToActionPerformed
        // TODO add your handling code here:
        theMapTo = new HashMap();
        //collect data
        long startTime = System.currentTimeMillis();
        //loop through all 50 states and test getting some data
        //for(int i = 11; i < 42; i++){
            //locationVal = i;
            //System.out.println("STATE: "+i+"/////////////////////////////////////////////////////////////////////////////////////");
        locationVal = parseState(toInput.getText());
        generateURL();
        assignment1.Assignment1.gather(theMapTo, finalURL,locationVal);
        //try{
            //System.out.println("Sleep");
        //    Thread.sleep(100);
            //System.out.println("Woke");
        //} catch(InterruptedException e){
        //    System.err.println(e);
        //}
            
        //}
        //assignment1.Assignment1.gather(theMap, finalURL,locationVal);
        log2.setText(theMapTo.PrintMap());
        info.setText("Data Collected.");
        numResultsTo.setText("Results: " + theMapTo.getCount());
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total collection time: "+totalTime);
    }//GEN-LAST:event_collectDataToActionPerformed

    private void baseInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baseInputActionPerformed
        // NOT USED
    }//GEN-LAST:event_baseInputActionPerformed

    /**
     * Calculates the similarity between theMap and theMapTo then updates the
     *      GUI
     * @param evt 
     */
    private void simBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simBttnActionPerformed
        if(theMap != null && theMapTo != null)
            similarity.setText("Similarity: " + assignment1.Assignment1.LinRegres(theMap, theMapTo));
        else
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
        // TODO add your handling code here:///////////////////////////////////////////////////////////////////////////////////////
        int maxNumStates = 56;
        int baseNum = 0;
        double maxRelat = 0;
        StatesMap = new HashMap[maxNumStates];
        long startTime = System.currentTimeMillis();
        //get the base map to cmp to;
        locationVal = parseState(toFind.getText());
        baseNum = locationVal;
        generateURL();
        assignment1.Assignment1.gather(theMap, finalURL,locationVal);


        //loop through all 50 states and get data
        for(int i = 11; i < maxNumStates; i++){
            locationVal = i;
            StatesMap[i] = new HashMap();
            generateURL();
            assignment1.Assignment1.gather(StatesMap[i], finalURL,locationVal);
            if(StatesMap[i].getCount() > 0){//calculate the regression for each state
                double newLin = assignment1.Assignment1.LinRegres(theMap, StatesMap[i]);
                if(newLin > maxRelat && i != baseNum){ 
                    maxRelat = newLin;
                    closest.setText("Closest: " + parseIntToState(i) +" with R value" + maxRelat);
                }
            }
            //Sleep so NOAA doesnt drop the connection
            try{
                System.out.println("Sleep");
                Thread.sleep(100);
                System.out.println("Woke");
            } catch(InterruptedException e){
                System.err.println(e);
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
     * Verifies that the date type is valid and updates the url
     * @param evt 
     */
    private void typeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeFieldActionPerformed
        String testType = "";

        testType = typeField.getText();
        System.out.println(testType);
        if(!verifyRealType(testType)){
            typeField.setText("INVALID");
            testType = "GHCND";
        }
        dataSetVal = testType;
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
    private javax.swing.JLabel cmpState;
    private javax.swing.JButton collectData;
    private javax.swing.JButton collectDataTo;
    private javax.swing.JMenu end;
    private javax.swing.JTextField endField;
    private javax.swing.JMenu file;
    private javax.swing.JButton find;
    private javax.swing.JLabel info;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu limit;
    private javax.swing.JTextField limitField;
    private javax.swing.JTextArea log;
    private javax.swing.JTextArea log2;
    private javax.swing.JLabel numResults;
    private javax.swing.JLabel numResultsTo;
    private javax.swing.JMenu params;
    private javax.swing.JButton simBttn;
    private javax.swing.JLabel similarity;
    private javax.swing.JMenu start;
    private javax.swing.JTextField startField;
    private javax.swing.JLabel title;
    private javax.swing.JTextField toFind;
    private javax.swing.JTextField toInput;
    private javax.swing.JTextField typeField;
    // End of variables declaration//GEN-END:variables
}
