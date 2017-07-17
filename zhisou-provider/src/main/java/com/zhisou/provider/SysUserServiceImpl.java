package com.zhisou.provider;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhisou.api.model.SysUser;
import com.zhisou.api.service.SysUserService;
import com.zhisou.mapper.SysUserMapper;

/**
 * @author cuixiang
 * @since 2017-07-07
 *
 */
@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	SysUserMapper sysUserMapper;
	
	@Override
	public SysUser selectByPrimaryKey(Long id) {
		
		return sysUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SysUser> selectList(Map<String, Object> map) {
		return sysUserMapper.selectList(map);
	}

	@Override
	public int selectListCount(Map<String, Object> map) {
		return sysUserMapper.selectListCount(map);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return sysUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SysUser entity) {
		return sysUserMapper.insert(entity);
	}
	
	@Override
	public int update(SysUser entity) {
		return sysUserMapper.update(entity);
	}

	@Transactional
	@Override
	public int insertTwo(SysUser user) {
		sysUserMapper.insert(user);
		SysUser user2 = new SysUser();
    	
    	user2.setLoginName("哈哈哈");
    	user2.setRealName(user.getId()+"");
    	user2.setPassword("123456");
    	sysUserMapper.insert(user2);
    	
		return 0;
	}
}
