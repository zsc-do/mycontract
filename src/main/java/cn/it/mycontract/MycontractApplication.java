package cn.it.mycontract;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.it.mycontract.mapper")
public class MycontractApplication {

	public static void main(String[] args) {
		SpringApplication.run(MycontractApplication.class, args);
	}

}
