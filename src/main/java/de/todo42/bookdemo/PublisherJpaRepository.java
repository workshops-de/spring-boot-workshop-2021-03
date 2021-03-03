package de.todo42.bookdemo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherJpaRepository extends CrudRepository<Publisher, Long> {

}
