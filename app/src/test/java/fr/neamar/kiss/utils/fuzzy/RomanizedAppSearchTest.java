package fr.neamar.kiss.utils.fuzzy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import fr.neamar.kiss.normalizer.StringNormalizer;

class RomanizedAppSearchTest {
    private static Stream<Arguments> supportedQueries() {
        return Stream.of(
                Arguments.of("tb", "tao bao"),
                Arguments.of("taobao", "tao bao"),
                Arguments.of("tao bao", "tao bao"),
                Arguments.of("淘宝", "淘宝"),
                Arguments.of("wxzf", "wei xin zhi fu"),
                Arguments.of("weixinzhifu", "wei xin zhi fu")
        );
    }

    @ParameterizedTest
    @MethodSource("supportedQueries")
    void legacyFuzzySearchMatchesRomanizedAliases(String query, String searchableName) {
        assertMatches(new FuzzyScoreV1(normalize(query), false), searchableName);
    }

    @ParameterizedTest
    @MethodSource("supportedQueries")
    void fuzzySearchMatchesRomanizedAliases(String query, String searchableName) {
        assertMatches(new FuzzyScoreV2(normalize(query), false), searchableName);
    }

    private static void assertMatches(FuzzyScore fuzzyScore, String searchableName) {
        assertThat(fuzzyScore.match(normalize(searchableName)).match, equalTo(true));
    }

    private static int[] normalize(String value) {
        return StringNormalizer.normalizeWithResult(value, false).codePoints;
    }
}
