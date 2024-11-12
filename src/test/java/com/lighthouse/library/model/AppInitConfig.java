package com.lighthouse.library.model;

import com.lighthouse.library.model.model.Author;
import com.lighthouse.library.model.model.Book;
import com.lighthouse.library.model.model.Genre;
import com.lighthouse.library.model.model.Lender;
import com.lighthouse.library.model.model.Library;
import com.lighthouse.library.model.request.AppUserCreate;
import com.lighthouse.library.model.request.AuthorCreate;
import com.lighthouse.library.model.request.BookCreate;
import com.lighthouse.library.model.request.GenreCreate;
import com.lighthouse.library.model.request.LenderCreate;
import com.lighthouse.library.model.request.LibraryCreate;
import com.lighthouse.library.model.security.UserSecurityContext;
import com.lighthouse.library.model.service.AppUserService;
import com.lighthouse.library.model.service.AuthorService;
import com.lighthouse.library.model.service.BookService;
import com.lighthouse.library.model.service.GenreService;
import com.lighthouse.library.model.service.LenderService;
import com.lighthouse.library.model.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppInitConfig {

  @Autowired private LenderService lenderService;
  @Autowired private BookService bookService;
  @Autowired private LibraryService libraryService;
  @Autowired private AuthorService authorService;
  @Autowired private GenreService genreService;

  @Autowired
  @Qualifier("adminSecurityContext")
  private UserSecurityContext securityContext;

  @Bean
  public Lender lender() {
    LenderCreate lenderCreate = new LenderCreate();
    return lenderService.createLender(lenderCreate, securityContext);
  }

  @Bean
  public Book book() {
    BookCreate bookCreate = new BookCreate();
    return bookService.createBook(bookCreate, securityContext);
  }

  @Bean
  public Library library() {
    LibraryCreate libraryCreate = new LibraryCreate();
    return libraryService.createLibrary(libraryCreate, securityContext);
  }

  @Bean
  public Author author() {
    AuthorCreate authorCreate = new AuthorCreate();
    return authorService.createAuthor(authorCreate, securityContext);
  }

  @Bean
  public Genre genre() {
    GenreCreate genreCreate = new GenreCreate();
    return genreService.createGenre(genreCreate, securityContext);
  }

  @Configuration
  public static class UserConfig {
    @Bean
    @Qualifier("adminSecurityContext")
    public UserSecurityContext adminSecurityContext(AppUserService appUserService) {
      com.lighthouse.library.model.model.AppUser admin =
          appUserService.createAppUser(
              new AppUserCreate().setUsername("admin@flexicore.com").setPassword("admin"), null);
      return new UserSecurityContext(admin);
    }
  }
}
