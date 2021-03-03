package de.todo42.bookdemo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookJpaRepository extends CrudRepository<Book, Long> {

}
