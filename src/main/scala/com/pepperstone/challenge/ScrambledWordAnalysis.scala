package com.pepperstone.challenge

import com.typesafe.scalalogging.LazyLogging

import scala.io.Source
import resource.managed

import java.io.File


class ScrambledWordAnalysis extends LazyLogging {

  /** Checks number of scrambled words in input search string to words available in dictionary
   * @param dictionary  : List of words from dictionary file
   * @param inputString : Input search string containing scrambled words from words available in dictionary
   * @return Int: Number of matched words in input search string with words in dictionary
   */
  def countNumberOfScrambledWordsInInput(dictionary: List[String], inputString: String): Int = {
    logger.debug(s"Processing search string: $inputString")
    val numberOfMatches = dictionary.map { dictWord =>
      // Start and End char of dictionary word to be compared with input string sub-arrays.
      val dictWordStartChar = dictWord.head.toString
      val dictWordEndChar = dictWord.last.toString
      // Middle characters of dictionary word in sorted format, to compare with
      // middle characters of valid input sub-array string.
      val sortedDictMiddleChars = dictWord.substring(1, dictWord.length).sorted
      // It will create sub-arrays from input string by sliding window of dictionary word size.
      val subArraysOfInput = inputString.sliding(dictWord.length)
      // Process and match each sub-arrays from input string for scrumbled word hit criteria.
      // 1. First and last character of dictionary word should match with first and last
      //    character of sub-array from input string.
      // 2. Middle characters of dictionary word has to match with any or exact sequence
      //    of middle characters in input sub-array string.
      subArraysOfInput.filter(word =>
        word.startsWith(dictWordStartChar) && word.endsWith(dictWordEndChar)
      ).exists(inputWord =>
        inputWord.substring(1, inputWord.length).sorted == sortedDictMiddleChars
      )
    }.count(_ == true) // Total number of words from dictionary appears in substring of input long string.
    logger.debug(s"Number of words matched with dictionary: $numberOfMatches for input search string: $inputString")
    numberOfMatches
  }

  /** Process and creates stats about number of scrambled words in each inputs string
   * to words available in dictionary. It takes input path to dictionary and input search string
   * files, validates dictionary with requirements and prints stats to console.
   * @param pathToDictFile  : Path to dictionary file
   * @param pathToInputFile : Path to file with input search strings having scrambled words from dictionary.
   */
  def createStatsForScrambledWordsInSearchStrings(pathToDictFile: String, pathToInputFile: String): List[String] = {
    // Read dictionary and scrambled words input.
    val dictWords = getLinesFromFile(pathToDictFile)
    val inputSearchStrings = getLinesFromFile(pathToInputFile)
    // Check dictionary limits.
    validateDictionary(dictWords)
    logger.debug("Dictionary elements:")
    dictWords.foreach(word => logger.debug(word))
    // Process scrambled word inputs for number of matches with dictionary words.
    val listOfDictWordsInSearchStrings = inputSearchStrings.map(countNumberOfScrambledWordsInInput(dictWords, _))
    // Print results to console.
    logger.debug("Printing search results to console.")
    listOfDictWordsInSearchStrings.zipWithIndex.map { case (result, index) =>
      val formattedString = s"Case #${index + 1}: $result"
      println(formattedString)
      formattedString
    }
  }

  /** Read file content and returns list of strings by line.
   * @param path : Path to input file
   * @return List[String]: Line contents in file
   */
  private[pepperstone] def getLinesFromFile(path: String): List[String] = {
    logger.debug(s"Reading input file content from path: $path")
    require(new File(path).isFile, s"Invalid input file path: $path")
    managed(Source.fromFile(path)("UTF-8")).acquireAndGet(_.getLines().map(_.toLowerCase).toList)
  }

  /** Validate requirements of dictionary file content.
   * @param dictWords : List of words in dictionary file
   */
  private[pepperstone] def validateDictionary(dictWords: List[String]): Unit = {
    require(dictWords.distinct.size == dictWords.length, "Dictionary has duplicate words")
    require(dictWords.exists(dictWord => dictWord.length >= 2 && dictWord.length <= 105),
      "Invalid length of character requirement for dictionary words. " +
        "Dictionary words length has to between 2 and 105 long, inclusive")
    require(dictWords.map(_.length).sum <= 105,
      "The sum of lengths of all words in the dictionary should not exceed 105")
  }
}

object ScrambledWordAnalysis extends App with LazyLogging {

  logger.info("Initializing scrambled word match in input search strings to words available in dictionary")
  new ScrambledWordAnalysis()
    .createStatsForScrambledWordsInSearchStrings(args(0), args(1))
  logger.info("Done analysing scrambled words in search strings")

}
