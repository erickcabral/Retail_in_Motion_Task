========= Retail in Motion Task by Erick Cabral =======

===== PRESONAL NOTES ====

1 - MVVM Architecture
2 - Android TDD methodology
3 - Android Jetpack libs -> Databinding, Navigation
4 - 3rd Libs:
 	Retrofit,
	Tikaroo XML Converter Factory,
	Mockito,
5 - Fixed Screen Orientation:
	There is no need for the app to work in Landscape orientation


======================= TASK DESCRIPTION ======================= 
RIM Employee LUAS App

A lot of Retail inMotion employees commute from city center to the office using LUAS every day. 
To avoid waiting too much at the stops, people would like to have an app where it is possible to see trams forecast. 
So, to help our employee, write a simple app considering only the following requests:

Given I am a LUAS passenger
When I open the app from 00:00 – 12:00
Then I should see trams forecast from Marlborough LUAS stop towards Outbound

Given I am a LUAS passenger
When I open the app from 12:01 – 23:59
Then I should see trams forecast from Stillorgan LUAS stop towards Inbound

Given I am on the stop forecast info screen
When I tap on the refresh button
Then the forecast data should be updated

To get real time information, use LUAS Forecasting API
https://data.gov.ie/dataset/luas-forecasting-api

To retrieve information from a specific stop, consider the below URLs:

Marlborough
http://luasforecasts.rpa.ie/xml/get.ashx?action=forecast&stop=mar&encrypt=false

Stillorgan
http://luasforecasts.rpa.ie/xml/get.ashx?action=forecast&stop=sti&encrypt=false

Feel free to choose any libraries you want to create your solution. 

You can describe any thoughts / decisions you made that you consider relevant on the README file.

Please upload your code to github and send us the link, so we can take a look on it easily.

The code must be yours, of course ;)
