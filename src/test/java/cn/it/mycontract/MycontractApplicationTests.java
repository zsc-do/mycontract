package cn.it.mycontract;

import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.HtglProcessRecord;
import cn.it.mycontract.mapper.HtglContractMapper;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class MycontractApplicationTests {

	@Autowired
	DataSource dataSource;


	@Autowired
	HtglContractMapper htglContractMapper;

	@Test
	public void test02() throws SQLException {

//		List<HtglContract> htglContractList = htglContractMapper.selectHtqcRecode(1, 2);
//
//		System.out.println(htglContractList);


	}




	/*
	* 逆向生成代码
	* */
	@Test
	public void test01() throws SQLException {
		//全局配置
//		GlobalConfig config = new GlobalConfig();
//		config.setActiveRecord(false) //是否支持AR模式
//				.setAuthor("zhengsc") //作者
//				.setOutputDir("D:\\SVNRep\\mycontract\\src\\main\\java")//生成路径
//				.setFileOverride(true)//文件覆盖
//				.setServiceName("%sService") //设置生成的service接口名
//				.setIdType(IdType.AUTO); //主键策略
//
//		//数据源配置
//		DataSourceConfig dsConfig = new DataSourceConfig();
//		dsConfig.setDbType(DbType.MYSQL)
//				.setUrl("jdbc:mysql://localhost:3306/my_contract?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC")
//				.setDriverName("com.mysql.cj.jdbc.Driver")
//				.setUsername("root")
//				.setPassword("root");
//		//策略配置
//		StrategyConfig stConfig = new StrategyConfig();
//		stConfig.setCapitalMode(true) // 全局大写命名
//				.setDbColumnUnderline(true) //表名字段名是否使用下滑线命名
//				.setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
//				.setInclude("htgl_file") //生成的表
//				.setTablePrefix(""); // 表前缀
//		// 包名策略
//		PackageConfig pkConfig = new PackageConfig();
//		pkConfig.setParent("cn.it.mycontract")
//				.setController("controller")
//				.setEntity("entity")
//				.setService("service")
//				.setMapper("mapper");
//		AutoGenerator ag = new
//				AutoGenerator().setGlobalConfig(config)
//				.setDataSource(dsConfig)
//				.setStrategy(stConfig)
//				.setPackageInfo(pkConfig)
//				.setTemplateEngine(new FreemarkerTemplateEngine());
//		ag.execute();
	}

}
