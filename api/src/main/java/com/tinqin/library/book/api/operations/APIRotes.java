package com.tinqin.library.book.api.operations;

public class APIRotes {

    public static final String API = "/api/v1";
    public static final String API_BOOK = API + "/books";
    public static final String API_AUTHOR = API + "/author";
    public static final String API_USER = API + "/user";
    public static final String GET_BOOK = API_BOOK + "/{bookId}";
    public static final String DELETE_BOOK = API_BOOK + "/delete/{bookId}";
    public static final String BLOCK_USER = API_USER + "/block/{userId}";
    public static final String UNBLOCK_USER = API_USER + "/unblock/{userId}";
}
