package com.smartg.tokenizer;

import java.util.regex.Pattern;

class TokenInfo<T> {
    public final Pattern regex;
    public final T token;

    public TokenInfo(Pattern regex, T token) {
        this.regex = regex;
        this.token = token;
    }
}