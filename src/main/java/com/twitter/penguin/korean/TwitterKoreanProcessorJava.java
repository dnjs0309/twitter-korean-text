/*
 * Twitter Korean Text - Scala library to process Korean text
 *
 * Copyright 2014 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.twitter.penguin.korean;

import java.util.List;

import scala.collection.Iterator;
import scala.collection.JavaConversions;
import scala.collection.Seq;

import com.google.common.collect.Lists;

import com.twitter.penguin.korean.phrase_extractor.KoreanPhraseExtractor;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer.KoreanToken;
import com.twitter.penguin.korean.util.KoreanPos;

/**
 * Java wrapper for TwitterKoreanProcessor using Builder pattern
 */
public final class TwitterKoreanProcessorJava {

  /**
   * Normalize Korean text
   * 그랰ㅋㅋㅋㅋㅋㅋ -> 그래ㅋㅋ
   *
   * @param text Input text.
   * @return Normalized text.
   */
  public static CharSequence normalize(CharSequence text) {
    return TwitterKoreanProcessor.normalize(text);
  }

  /**
   * Tokenize with the builder options.
   *
   * @param text Input text.
   * @return A list of Korean Tokens (run tokensToJavaList to transform to Java List)
   */
  public static Seq<KoreanToken> tokenize(CharSequence text) {
    return TwitterKoreanProcessor.tokenize(
        text
    );
  }


  /**
   * Transforms the tokenization output to List<KoreanTokenJava>
   *
   * @param tokens Korean tokens (output of tokenize(CharSequence text)).
   * @return List of KoreanTokenJava.
   */
  public static List<KoreanTokenJava> tokensToJavaKoreanTokenList(Seq<KoreanToken> tokens) {
    Iterator<KoreanToken> tokenized = tokens.iterator();
    List<KoreanTokenJava> output = Lists.newLinkedList();
    while (tokenized.hasNext()) {
      KoreanToken token = tokenized.next();
      output.add(new KoreanTokenJava(
          token.text(),
          KoreanPosJava.valueOf(token.pos().toString()),
          token.offset(),
          token.length(),
          token.unknown()
      ));
    }
    return output;
  }

  /**
   * Tokenize with the builder options into a String Iterable.
   *
   * @param tokens Korean tokens (output of tokenize(CharSequence text)).
   * @return List of token strings.
   */
  public static List<String> tokensToJavaStringList(Seq<KoreanToken> tokens) {
    Iterator<KoreanToken> tokenized = tokens.iterator();
    List<String> output = Lists.newLinkedList();
    while (tokenized.hasNext()) {
      final KoreanToken token = tokenized.next();

      if (token.pos() != KoreanPos.Space()) {
        output.add(token.text());
      }
    }
    return output;
  }


  /**
   * Stem Korean Verbs and Adjectives
   *
   * @param tokens Korean tokens (output of tokenize(CharSequence text)).
   * @return StemmedTextWithTokens(text, tokens)
   */
  public static Seq<KoreanToken> stem(Seq<KoreanToken> tokens) {

    return TwitterKoreanProcessor.stem(tokens);
  }

  /**
   * Extract phrases from Korean input text
   *
   * @param tokens Korean tokens (output of tokenize(CharSequence text)).
   * @return List of phrase CharSequences.
   */
  public static List<KoreanPhraseExtractor.KoreanPhrase> extractPhrases(Seq<KoreanToken> tokens, boolean filterSpam, boolean includeHashtags) {
    return JavaConversions.seqAsJavaList(
        TwitterKoreanProcessor.extractPhrases(tokens, filterSpam, includeHashtags)
    );
  }

}
