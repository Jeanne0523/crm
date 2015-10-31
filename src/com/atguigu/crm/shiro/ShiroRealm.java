package com.atguigu.crm.shiro;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.dao.UserMapper;
import com.atuigu.crm.entity.User;

@Component
public class ShiroRealm extends AuthorizingRealm{

	@Autowired
	private UserMapper userMapper;
	
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		Object principal = principalCollection.getPrimaryPrincipal();
		User user = (User) principal;
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(user.getRoleList());
		return info;
	}
	
	//认证
	@Transactional(readOnly=true)
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		User user = userMapper.getByName(token.getUsername());
		
		if(user == null){
			throw new UnknownAccountException();
		}
		if(user.getEnabled() != 1){
			throw new LockedAccountException();
		}
		
		Object principal = user;
		String hashedCredentials = user.getPassword();
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		String realmName = getName();
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, 
				hashedCredentials, credentialsSalt, realmName);
		return info;
	}
	
	@PostConstruct
	public void initCredentialsMatcher(){
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(1024);
		setCredentialsMatcher(credentialsMatcher);
	}

	public static void main(String[] args) {
		//MD5 盐值加密的算法
		String hashAlgorithmName = "MD5";
		String credentials = "123456";
		String saltSource = "wwww.atguigu.com";
		ByteSource salt = ByteSource.Util.bytes("wwww.atguigu.com".getBytes());
		int hashIterations = 1024;
		
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
		                                           //65d851f5ff31b38d12eb7a00b6e644c5
		System.out.println(result.toString().equals("65d851f5ff31b38d12eb7a00b6e644c5"));
	}
}
