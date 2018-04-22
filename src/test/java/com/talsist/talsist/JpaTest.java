package com.talsist.talsist;

import com.talsist.domain.comment.CommentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaTest {

    @Autowired
    private CommentRepository commentRepo;

    @Test
    public void testComments() {

    }

}
