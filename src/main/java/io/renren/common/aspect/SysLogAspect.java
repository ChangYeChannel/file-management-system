package io.renren.common.aspect;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.lang.reflect.Field;

import io.renren.common.annotation.SysLog;
import io.renren.modules.file.entity.FileBaseEntity;
import io.renren.modules.file.service.FileBaseService;
import io.renren.modules.images.entity.FileImagesEntity;
import io.renren.modules.images.service.FileImagesService;
import io.renren.modules.sys.entity.SysLogEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysLogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class SysLogAspect {
	@Autowired
	private SysLogService sysLogService;
	@Resource
	private FileBaseService fileBaseService;
	@Resource
	private FileImagesService fileImagesService;
	
	@Pointcut("@annotation(io.renren.common.annotation.SysLog)")
	public void logPointCut() {}

	//环绕切点方法
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		//记录方法开始前参数
		MethodSignature signature = (MethodSignature) point.getSignature();
		//拿到请求方法
		Method method = signature.getMethod();
		//拿到方法参数列表
		Object[] args = point.getArgs();
		//业务名称（方法名）
		String methodName = signature.getName();
		//操作人
		String username = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();

		//获取@SysLog("")注解内容
		SysLog syslog = method.getAnnotation(SysLog.class);
		if (syslog != null) {
			//拿到注解内容
			String code = syslog.code();
			String msg = syslog.msg();

			//判断操作类型是否为修改数据
			switch (code) {
				case "07":
				case "02": {//内容修改或补录数据
					FileBaseEntity fileBaseParam = (FileBaseEntity) args[0];
					//获取修改前数据库中档案详情
					FileBaseEntity oldFileBase = fileBaseService.getById(fileBaseParam.getId());
					List<FileImagesEntity> oldFileImages = fileImagesService.list(new LambdaQueryWrapper<FileImagesEntity>().eq(FileImagesEntity::getBaseId, fileBaseParam.getId()));
					//执行方法
					Object result = point.proceed();
					//获取修改后数据库中档案详情
					FileBaseEntity newFileBase = fileBaseService.getById(fileBaseParam.getId());
					List<FileImagesEntity> newFileImages = fileImagesService.list(new LambdaQueryWrapper<FileImagesEntity>().eq(FileImagesEntity::getBaseId, fileBaseParam.getId()));
					//新老资料对比
					int DIF = newFileImages.size() - oldFileImages.size();
					//新老档案比对
					String operation = code.equals("07") ? "补录了" : "修改了" + comparatorObject(newFileBase, oldFileBase);
					if (DIF > 0) {
						operation = operation + "[材料]";
					}
					String logNum = newFileBase.getIdCard() + code;

					saveSysLog(username, operation, methodName, logNum);

					return result;

				}
				case "03":
				case "0X": {//删除档案或状态修改
					Long baseId = (Long) args[0];
					if (code.equals("0X")) {
						Integer status = (Integer) args[1];
						code = "0" + status.toString();
					}
					//执行方法
					Object result = point.proceed();
					String logNum = fileBaseService.getById(baseId).getIdCard() + code;

					saveSysLog(username, msg, methodName, logNum);

					return result;

				}
				case "01": {//新增档案
					FileBaseEntity fileBaseParam = (FileBaseEntity) args[0];
					//执行方法
					Object result = point.proceed();
					String logNum = fileBaseService.getById(fileBaseParam.getId()).getIdCard() + code;

					saveSysLog(username, msg, methodName, logNum);

					return result;
				}
			}
		}
		//只标记注解但未输入注解内容不会添加日志
		return null;
	}

	//保存日志
	private void saveSysLog(String username, String operation, String methodName, String logNum) {
		SysLogEntity sysLogEntity = new SysLogEntity();
		sysLogEntity.setUsername(username);
		sysLogEntity.setOperation(operation);
		sysLogEntity.setMethod(methodName);
		sysLogEntity.setLogNum(logNum);
		sysLogEntity.setCreateDate(new Date());
		sysLogService.save(sysLogEntity);
	}


	//对比数据
	private String comparatorObject(FileBaseEntity newFileBase, FileBaseEntity oldFileBase) throws IllegalAccessException {
		StringBuilder matter = new StringBuilder();

		if (oldFileBase != null) {
			Map<String, Object> oldMap = changeValueToMap(oldFileBase);
			Map<String, Object> newMap = changeValueToMap(newFileBase);
			if (oldMap != null && !oldMap.isEmpty()) {
				for (Map.Entry<String, Object> entry : oldMap.entrySet()) {
					Object oldValue = entry.getValue();
					Object newValue = newMap.get(entry.getKey());
					if (!newValue.equals(oldValue)) {
						matter.append("[").append(entry.getKey()).append("]");
					}
				}
			}
		}
		return matter.toString();
	}

	//将对象转化为map
	private Map<String, Object> changeValueToMap(FileBaseEntity fileBase) throws IllegalAccessException {
		Map<String, Object> resultMap = new HashMap<>();
		Field[] fields = fileBase.getClass().getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			//获取private对象字段值
			field.setAccessible(true);
			resultMap.put(name, field.get(fileBase));
		}
		return resultMap;
	}
}
