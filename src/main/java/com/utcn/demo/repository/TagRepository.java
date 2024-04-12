package com.utcn.demo.repository;
import com.utcn.demo.model.Question;
import com.utcn.demo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag,Long> {

}
