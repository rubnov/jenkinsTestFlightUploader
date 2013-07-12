Jenkins TestFlight Uploader
=========================

Groovy script for uploading CI builds to TestFlight

I wrote this script after [TestFlight Jenkins plugin](https://wiki.jenkins-ci.org/display/JENKINS/Testflight+Plugin) (versions 1.3.x) continuously crashed my Jenkins and was simply unusable.  

This simple script will upload your .ipa or .apk file to your TestFlight account (uses [TestFlight upload API](https://testflightapp.com/api/doc/)).

How to use: 
-----------

1. Assuming you have Jenkins installed on your server ()
2. Install [Groovy Plugin for Jenkins](https://wiki.jenkins-ci.org/display/JENKINS/Groovy+plugin)  
3. Add an "Execute Groovy Script" build step to the end of your build job
4. Select "Groovy Command" option and copy/paste this script into the edit box.
5. Configure the script by replacing the constant placeholders at the top of the script.
6. Create a notes.txt and place it on your build machine. This file needs to be accessible to the script. Set _testFlightNotes_ constant to point to it (absolute path).


**This script was tested with Jenkins 1.522 using Git SCM and Groovy 2.1.6.**

Feedback
--------
<rotem@100grams.nl>

