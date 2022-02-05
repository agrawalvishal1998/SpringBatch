package com.techprimers.springbatchexample.batch;

import com.techprimers.springbatchexample.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Processor implements ItemProcessor<User, User> {
    private static final Map<String,String> DEPT_NAMES=new HashMap<>();

    public Processor() {
        DEPT_NAMES.put("001","Electrical Engineering");
        DEPT_NAMES.put("002","Computer Science");
        DEPT_NAMES.put("003","Electronics Engineering");
    }

    @Override
    public User process(User user) throws Exception {
        String deptCode=user.getDept();
        String dept=DEPT_NAMES.get(deptCode);
        user.setDept(dept);
        System.out.printf("Dept changed from %s to %s\n",deptCode,dept);
        return user;
    }
}
