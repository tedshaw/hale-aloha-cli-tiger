**Table of Contents**


# 1.0 Download #

Please download the latest distribution of hale-aloha-cli-tiger from the [Downloads](http://code.google.com/p/hale-aloha-cli-tiger/downloads/list) page.

Then, unzip the distribution.

# 2.0 Install #

To install the prepackaged hale-aloha-cli-tiger application, find the file named hale-aloha-cli-tiger.jar in the distribution.

Open a new terminal and move into the directory where the hale-aloha-cli-tiger.jar file is located.

Type "java -jar hale-aloha-cli-tiger.jar" to run the CLI.

Type "help" for a list of commands.

Here is some sample input/output:

```

Successfully connected to the Hale Aloha Wattdepot Server
> help
Here are the available commands for this system.
current-power [tower | lounge]
Returns the current power in kW for the associated tower or lounge.
daily-energy [tower | lounge] [date]
Returns the energy in kWh used by the tower or lounge for the specified date (yyyy-mm-dd).
energy-since [tower | lounge] [date]
Returns the energy used since the date (yyyy-mm-dd) to now.
rank-towers [start] [end]
Returns a list in sorted order from least to most energy consumed between the [start] and [end] date (yyyy-mm-dd)
set-baseline [tower | lounge] [date]
Set the data from the date (yyyy-mm-dd) as the baseline power for goal monitoring.
monitor-power [tower | lounge] [interval]
Prints out a timestamp and the current power for [tower | lounge] every [interval] seconds.
monitor-goal [tower | lounge] goal [interval]
Prints out a timestamp, the current power for [tower | lounge], and whether the percentage of power reduction from the baseline in the convervation goal is being met
quit
Terminates execution
Note: towers are:  Mokihana, Ilima, Lehua, Lokelani
Lounges are the tower names followed by a "-" followed by one of A, B, C, D, E. For example, Mokihana-A.
> set-baseline Ilima
Successfully stored baseline data for Ilima on 2011-12-12 in file Ilima.xml.
> monitor-power Ilima 5 
Retrieving current power for Ilima every 5 seconds.
[Press Enter to stop.]

Ilima's power consumption at 2011-12-13 22:39:15 is: 37.30 kW.
Ilima's power consumption at 2011-12-13 22:39:15 is: 37.30 kW.

> monitor-goal Ilima 2 4
Retrieving current power for Ilima every 4 seconds.
[Press Enter to stop.]

Ilima's current power at 2011-12-13 22:39:45 is: 37.23 kW. Base power is: 36.95 kW. Goal not met.
Ilima's current power at 2011-12-13 22:40:23 is: 37.77 kW. Base power is: 36.95 kW. Goal not met.
```