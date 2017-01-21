# BeachbotLib
Reusable library for team 330

Features:
* Logging in text and CSV
* BBCommands - adds logging and better sequencing
* BBIterativeRobot - Runs while disconnected
* Additional Buttons - POVButton, POVAnyButton, TwoJoystickButton
* Buzzer arbitrator
* PID controllers with changeable gains
* Multiple Speed Controllers

#Installing
* Close Eclipse
* Unzip to C:\Users\\\<user>\wpilib\user (so that files go in unzip to C:\Users\\\<user>\wpilib\user\java\lib
* Reopen Eclipse

See http://wpilib.screenstepslive.com/s/4485/m/13503/l/682619-3rd-party-libraries for more information for how libraries are implemented for FRC.


#Releasing
* gradlew build
* create release in github using release/BeachbotLib-usershared.zip