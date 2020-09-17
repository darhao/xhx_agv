package com.jimi.xhx_agv.db;

import javax.sql.DataSource;

import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jimi.xhx_agv.constant.ModelGenerator;
import com.jimi.xhx_agv.constant.SystemProperties;
import com.jimi.xhx_agv.util.PropUtil;

public class GeneratorStarter {

	public static void main(String[] args) {
		String url = PropUtil.getString(SystemProperties.FILE_NAME, SystemProperties.D_MYSQL_URL);
		String user = PropUtil.getString(SystemProperties.FILE_NAME, SystemProperties.D_MYSQL_USER);
		String password = PropUtil.getString(SystemProperties.FILE_NAME, SystemProperties.D_MYSQL_PASSWORD);
		// model 所使用的包名
		String modelPkg = ModelGenerator.MODEL_PACKAGE;
		// model 文件保存路径
		String modelDir = ModelGenerator.MODEL_PATH;
		// base model 所使用的包名
		String baseModelPkg = ModelGenerator.BASE_MODEL_PACKAGE;
		// base model 文件保存路径
		String baseModelDir = ModelGenerator.BASE_MODEL_PATH;
		DruidPlugin druidPlugin = new DruidPlugin(url, user, password);
		druidPlugin.start();
		DataSource dataSource = druidPlugin.getDataSource();
		
		// 创建生成器
		Generator generator = new Generator(dataSource, baseModelPkg, baseModelDir, modelPkg, modelDir);
		// 设置是否生成链式 setter 方法
		generator.setGenerateChainSetter(true);
		//设置方言
		generator.setDialect(new MysqlDialect());
		//设置Mapping名字
		generator.setMappingKitClassName("MappingKit");
		// 设置是否在 Model 中生成 dao 对象
		generator.setGenerateDaoInModel(true);
		// 生成
		generator.generate();
	}
}
