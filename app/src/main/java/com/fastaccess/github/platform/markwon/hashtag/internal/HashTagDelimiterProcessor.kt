package com.fastaccess.github.platform.markwon.hashtag.internal


import com.fastaccess.github.platform.markwon.hashtag.HashTag
import org.commonmark.node.Node
import org.commonmark.node.Text
import org.commonmark.parser.delimiter.DelimiterProcessor
import org.commonmark.parser.delimiter.DelimiterRun

class HashTagDelimiterProcessor : DelimiterProcessor {

    override fun getOpeningCharacter(): Char = '#'
    override fun getClosingCharacter(): Char = ' '
    override fun getMinLength(): Int = 1

    override fun getDelimiterUse(
        opener: DelimiterRun,
        closer: DelimiterRun
    ): Int = if (opener.length() >= 1 && closer.length() >= 1) {
        1
    } else {
        0
    }

    override fun process(
        opener: Text,
        closer: Text,
        delimiterCount: Int
    ) {
        val hashTag = HashTag()
        var tmp: Node? = opener.next
        if (tmp != null) {
            hashTag.url = (tmp as Text).literal
        }
        while (tmp != null && tmp !== closer) {
            val next = tmp.next
            hashTag.appendChild(tmp)
            tmp = next
        }
        opener.insertAfter(hashTag)
    }
}