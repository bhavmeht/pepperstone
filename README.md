# Pepperstone: Scrambled words search analysis in lengthy string
*This application counts number of words in a dictionary appeared as substrings in a long string of characters either in 
their original form or in their scrambled form. The scrambled form of the dictionary word must adhere to the 
following rule: 
the first and last letter must be maintained while the middle characters can be reorganised.*

The scrambled or original form of the dictionary word may appear multiple times but it should be count once.
It need to know whether scrambled word shows up at least once.

###Inputs to application:
- Dictionary (Dictionary words)
- InputFile (Having scrambled words as sub-array in long search string)


#### Limits in dictionary file

- No two words in the dictionary are the same.
- Each word in the dictionary is between 2 and 105 letters long, inclusive.
- The sum of lengths of all words in the dictionary does not exceed 105.


###Output:
Treating each line of the input file as one search string, program should output a line by

```Case #x: y```

where *x* is the line number (starting from 1) and *y* is the number of words from the dictionary 
that appear (in their original or scrambled form) as substrings of the given string.

Eg:
```Case #1: 4```

## Assumptions:
- Input data is well formed and valid.

## Running from command prompt

#### Prerequisite for running this application.

* install sbt on machine, for macOS use [SBT] link to install and make sure that sbt directory is in PATH. 
* install scala2 on machine. I have used scala 2.12 version for this project. Use [Scala] link to install scala.
* make sure sbt and scala runs from command prompt.


###From root folder *pepperStone* run following command
#### Build and Compile application

    sbt clean compile

#### Run Test cases

    sbt clean test

#### Create Jar for running locally

    sbt clean assembly

assembly will create fat jar file *pepperstone-assembly.jar* in target folder.

#### Run with command

      ./scrambled-strings.sh "DictionaryFilePath" "InputFilePath"
Eg:

      ./scrambled-strings.sh "./src/test/resources/Dictionary.txt" "./src/test/resources/InputFile.txt"


### Running in IntelliJ IDEA
Add a Run Configuration with the following:

**Main Class:**
Add "com.pepperstone.challenge.ScrambledWordAnalysis" as Main class

**Module:**
Select "pepperstone" as module

**Program Arguments:**

    "DictionaryFile.txt" "InputFile.txt"


[SBT]: https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Mac.html
[Scala]: https://www.scala-lang.org/download/
