package fr.neamar.kiss.utils.fuzzy;

import fr.neamar.kiss.pojo.Pojo;

public interface FuzzyScore {

    FuzzyScore setFullWordBonus(int full_word_bonus);

    FuzzyScore setAdjacencyBonus(int adjacency_bonus);

    FuzzyScore setSeparatorBonus(int separator_bonus);

    FuzzyScore setCamelBonus(int camel_bonus);

    FuzzyScore setLeadingLetterPenalty(int leading_letter_penalty);

    FuzzyScore setMaxLeadingLetterPenalty(int max_leading_letter_penalty);

    FuzzyScore setUnmatchedLetterPenalty(int unmatched_letter_penalty);

    FuzzyScore setFirstLetterBonus(int first_letter_bonus);

    MatchInfo match(CharSequence text);

    MatchInfo match(int[] text);
    
    /**
     * Match pinyin against a Pojo's name, pinyin, and pinyin short forms.
     * Default implementation just matches against the normalized name.
     *
     * @param pojo the Pojo to match against
     * @return {@link MatchInfo} with the best match found
     */
    default MatchInfo matchPinyin(Pojo pojo) {
        return match(pojo.normalizedName.codePoints);
    }
}
