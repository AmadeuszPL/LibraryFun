package com.amadeusz.library.application;

import com.amadeusz.library.application.service.AccountsService;
import com.amadeusz.library.application.service.BookService;
import com.amadeusz.library.application.service.IssueService;
import com.amadeusz.library.application.service.BookItemService;

//@Service("libraryConfiguration")
public class LibraryConfiguration {

    private final BookService bookService;
    private final BookItemService bookItemService;
    private final IssueService issueService;
    private final AccountsService accountsService;

//    private final BookByYearProvider byYearProvider;

    public LibraryConfiguration(BookService bookService, BookItemService bookItemService,
                                IssueService issueService, AccountsService accountsService) {

        this.bookService = bookService;
        this.bookItemService = bookItemService;
        this.issueService = issueService;
        this.accountsService = accountsService;

//        DefaultBookService defaultBookService = new DefaultBookService(bookRepository);
//        this.bookService = defaultBookService;
////        this.byYearProvider = defaultBookService;
//        this.bookItemService = new DefaultBookItemService(bookItemRepository,
//                bookRepository);
//        this.issueService = new DefaultIssueService(bookIssueRepository,
//                bookItemRepository, libraryMembersRepository);
//        this.accountsService =
//                new DefaultAccountsService(this.libraryMembersRepository);

    }

    public BookService getBookService() {
        return bookService;
    }

    public BookItemService getBookItemService() {
        return bookItemService;
    }

    public IssueService getIssueService() {
        return issueService;
    }

    public AccountsService getLibraryMemberService() {
        return accountsService;
    }
}
