import hudson.model.*

/* 
 * TestFlight constants 
*/
// the following are mandatory (get them from your TestFlight account on www.testflightapp.com)
def apiToken = "<your_testflight_api_token>"
def teamToken = "<your_testflight_team_token>"  
// Distribution list: to send emails about the build to people in your distribution list (set to "" if not interested) 
def testFlightDistList = "<testflight_distribution_list_name>"   
// Build notes: absolute path to a text file containing notes to be prepended to every email sent  
def testFlightNotes = "/path/to/file.text"
// Build change log: a temporary file used by the script to collect commit messages and append them to the above notes
def changeLog = "/temp/changelog.txt" 


// get changes since last build 

def buildChanges = build.getChangeSet()

// prepare TestFlight release notes 

String fileContents = new File(testFlightNotes).text
File cl = new File(changeLog)
cl.write(fileContents)

if(!buildChanges.isEmptySet()){
    // append commit messages and author
    def entryNumber = 1
    buildChanges.each() { entry ->
        cl.append("${entryNumber}. " + entry.getMsg() + " - " + entry.getAuthor() + "\n")
        entryNumber++
    }
}
else{
    cl.append("No changes")
} 

testFlightNotes = changeLog

// find the .ipa or .apk build output file in the workspace

def getBuildFile(fileOrDir){
        def filePattern = ~/.*\.(ipa|apk)$/
        def found = ""
        if(fileOrDir.isDirectory()){
            fileOrDir.eachFileRecurse{
                if(!it.isDirectory() && it.getName() =~ filePattern)
                    found = it.getAbsolutePath()
            }
        }else{
            if(fileOrDir.getName() =~ filePattern){
                found = fileOrDir.getAbsolutePath()
            }
        }
        return found
}

def buildFile = getBuildFile(new File(build.getWorkspace().getRemote()))
     
def proc = """\
    curl http://testflightapp.com/api/builds.json \
    -F file=@${buildFile} \
    -F api_token=${apiToken} \
    -F team_token=${teamToken} \
    -F notes=<${testFlightNotes} \
    -F notify=True \
    -F distribution_lists=${testFlightDistList}
"""

println(proc.execute().text)

return 0