package com.pepperstone.challenge

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ScrambledWordAnalysisTest extends AnyFlatSpec with Matchers {

  val wordAnalysis = new ScrambledWordAnalysis()
  val dictionary: List[String] = List("axpaj", "apxaj", "dnrbt", "pjxdn", "abd")

  behavior of "countNumberOfScrambledWordsInInput"
  it should "count number of scrambled words in input search string available in dictionary" in {
    val result = wordAnalysis.countNumberOfScrambledWordsInInput(dictionary,
      "aapxjdnrbtvldptfzbbdbbzxtndrvjblnzjfpvhdhhpxjdnrbt")
    result shouldBe 4
  }

  it should "have 1 hit, if word available in dictionary is part of input search string" in {
    val result = wordAnalysis.countNumberOfScrambledWordsInInput(List("and"), "benandjrery")
    result shouldBe 1
  }

  it should "not have any hits, if word in input search string is not available in dictionary" in {
    val result = wordAnalysis.countNumberOfScrambledWordsInInput(List("and"), "tsetsaerchsrtnig")
    result shouldBe 0
  }

  it should "return 0, when input dictionary is empty" in {
    val result = wordAnalysis.countNumberOfScrambledWordsInInput(
      Nil, "aapxjdnrbtvldptfzbbdbbzxtndrvjblnzjfpvhdhhpxjdnrbt"
    )
    result shouldBe 0
  }

  it should "return 0, when search string is empty" in {
    val result = wordAnalysis.countNumberOfScrambledWordsInInput(dictionary, "")
    result shouldBe 0
  }

  behavior of "createStatsForScrambledWordsInSearchStrings"
  it should "correctly match words in dictionary file to list of input search string in input file" in {
    val dictionaryFilePath = getClass.getClassLoader.getResource("Dictionary.txt").getPath
    val inputFilePath = getClass.getClassLoader.getResource("InputFile.txt").getPath
    val result = wordAnalysis.createStatsForScrambledWordsInSearchStrings(
      dictionaryFilePath, inputFilePath
    )
    result.size shouldBe 1
    result.head shouldBe "Case #1: 4"
  }

  it should "raise exception, if path to dictionary is invalid" in {
    val dictionaryFilePath = ""
    val inputFilePath = getClass.getClassLoader.getResource("InputFile.txt").getPath
    intercept[IllegalArgumentException]{
      wordAnalysis.createStatsForScrambledWordsInSearchStrings(
        dictionaryFilePath, inputFilePath
      )
    }
  }

  it should "raise exception, if path to input file is invalid" in {
    val dictionaryFilePath = getClass.getClassLoader.getResource("Dictionary.txt").getPath
    val inputFilePath = ""
    intercept[IllegalArgumentException]{
      wordAnalysis.createStatsForScrambledWordsInSearchStrings(
        dictionaryFilePath, inputFilePath
      )
    }
  }

  behavior of "validateDictionary"
  it should "throw IllegalArgumentException when dictionary has duplicate words" in {
    intercept[IllegalArgumentException] {
      wordAnalysis.validateDictionary(List("nadal", "nadal"))
    }
  }

  it should "throw IllegalArgumentException when dictionary has 1 letter word" in {
    intercept[IllegalArgumentException] {
      wordAnalysis.validateDictionary(List("a"))
    }
  }

  it should "throw IllegalArgumentException when dictionary has sum of word length > 105 letters" in {
    intercept[IllegalArgumentException] {
      wordAnalysis.validateDictionary(List("asdf", "wdjfwbfms", "shhbsbcfja", "dwhdjdsjs", "cnfjsdjfn", "wdhfurfnxva",
        "csnsfnxvsff", "dnjdhfgzbdjfk", "jttsbfkfhsxdjsdbfjcx", "abVacacsfhbhjsj", "nxzvzxgdhfbcjfkzdfggg", "bxbzffjfbvsj"))
    }
  }
}
