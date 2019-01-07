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
Add the following to build.gradle where VERSION is the release tag eg 2019.0.1
```
repositories {
    maven { url 'https://jitpack.io' }
}
dependencies {
    implementation 'com.github.Beachbot330:BeachbotLib:VERSION'
}
```

#Releasing
* create a release in github. Jitpack does the rest
