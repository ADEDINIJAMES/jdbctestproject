package com.tumech.jdbcclient.controller;

import com.tumech.jdbcclient.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private JdbcClient jdbcClient;
//@PostMapping
//    public  String addNewBook (@RequestBody Book book){
//        jdbcClient.sql("INSERT INTO book(id,name,title) values (?,?,?)")
//                .param(List.of(book.getId(),book.getName(),book.getTitle()));
//        return "book added to the system";
//    }

    @PostMapping
    public String addNewBook(@RequestBody Book book) {
        jdbcClient.sql("INSERT INTO Book (id, name, title) VALUES (?, ?, ?)")
                .param(book.getId())
                .param(book.getName())
                .param(book.getTitle())
                .update();
        return "Book added to the system";
    }
@GetMapping
    public List<Book> getAllBooks (){
        return jdbcClient.sql("SELECT id, name, title FROM book")
                .query(Book.class)
                .list();

    }
    @GetMapping("/{id}")
    public Optional<Book> getBookById (@PathVariable int id ){
        return jdbcClient.sql("SELECT id, name, title FROM book where id=:id")
                .param("id",id)
                .query(Book.class)
                .optional();
    }
    @PutMapping("/{id}")
    public String updateBook(@PathVariable int id, @RequestBody Book book) {
        jdbcClient.sql( "UPDATE book SET title = ?, name = ? WHERE id = ?")
                .param(book.getTitle())
                .param(book.getName())
                .param(id)
                .update();
        return "Book modified successfully";
    }
    @DeleteMapping("/{id}")
    public String deleteBook (@PathVariable int id){
        jdbcClient.sql("delete from book where id=:id")
                .param("id",id)
                .update();
        return "book deleted successfully";
    }
}
