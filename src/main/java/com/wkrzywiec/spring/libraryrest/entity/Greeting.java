package com.wkrzywiec.spring.libraryrest.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Greeting {

	private long id;
    private String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }
}
