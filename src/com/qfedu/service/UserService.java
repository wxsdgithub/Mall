package com.qfedu.service;
import java.util.List;

/**
 * �û�Service
 */
import com.qfedu.domain.User;
public interface UserService {
	    //�������ӿڵķ���/����һ��Ҫ��Daoһ��
		boolean save(User user);
		//�������� �û�����������
		User getUserByName(String name);
		//��ѯȫ��
		List<User> selectAll();
		//У���û����Ƿ����  ע��ҳ��
		boolean checkName(String name);
		//У�������Ƿ����    ע��ҳ��
		boolean checkEmail(String email);
		//����¼�û��Ƿ����  ���������� Ҳ�������û���
		boolean checkLogin(String name);
		//����
		boolean activateUser(String email,String code);
		//ɾ���û�
		int deleteByid(int id);
		//�û�����
		List<User> usersearch(String username,String gender);
}
