# Introduction
TwoCents is a financial tool which allow you to manage buy/sell orders of brazilian stocks and calculate all tax involved. With a set of reports the customer will have a overview about portfolio available. Besides that, tool generates all brokerage notes involved by operation completed. Limitation: It is only dedicated to Brazilian Stock Market due tax rules involved.

See the pictures below for more details. 

#Architecture

 1. Desktop app built using SWT(https://www.eclipse.org/swt/) and Nebula(https://eclipse.org/nebula/)
 2. Used Apache Lucene framework (https://lucene.apache.org/). For search stock in quick way. 
 3. Reports are built using Jasper Reports framework (http://community.jaspersoft.com/). 
 4. Database HSQLDB (http://hsqldb.org/). 
 5. Transaction/DAO layers handled by Spring and Hibernate. 
 6. Windows Installer used is NSIS. (http://nsis.sourceforge.net/Main_Page)

# Features

 1. Allow 3rd partners include advertisements pictures. 
 2. Generate brokerage notes for the transactions. 
 2. Update stock prices based on Yahoo finance. 
 3. License mechanism.
 4. Manage many customer/stockbroker accounts
 5. Export reports.
 6. Import/Export buy and sell orders into spreadsheets. 
 7. Backup data to Google Drive. 

# Contributors 
 1. Adriano Gonella
 2. Julio Correa
 3. Wagner Vernier
 4. Carlos Alexandre

# How execute?

 1. Import folder "TwoCents" as maven project.
 2. Execute class "com.twocents.main.Main" under folder TwoCents\com.twocents.main
 2. First time it will ask to inform user/broker information. You can add more after. 

## Application screenshot

 1. TwoCents Main Screen
![TwoCents Main Screen](https://github.com/gonella/twocents/blob/master/TwoCents/com.twocents.main/doc/TwoCents01.png "TwoCents Main Screen")
 2. TwoCents Report Custody + Reports available 
![TwoCents Report Screen](https://github.com/gonella/twocents/blob/master/TwoCents/com.twocents.main/doc/TwoCents02_Reports.png "TwoCents Report Screen")
 3. TwoCents Report Brokerage notes
![TwoCents Report Brokerage](https://github.com/gonella/twocents/blob/master/TwoCents/com.twocents.main/doc/TwoCents02_Reports_TaxInvolved.png "TwoCents Report Brokerage")

# Limitation
 1. Support ONLY Windows x64.

# Known issues

 1. After migrate ant to maven, some funcionalities stopped to work. E.g. generate install binary(NIS). For this reason we asked to import as maven project.
 


# TODO
 1. Fix issues with NSIS (Nullsoft Scriptable Install System), it is a professional open source system to create Windows installers.
 2. Remove some ant dependencies.
 2. Fix some layout issues. 
 3. Update some tax rules.
 4. Change some jasper class reused. (Due ant to maven conversion)
 5. Add more reports.
