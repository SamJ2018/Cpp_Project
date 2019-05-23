#pragma once
#include<iostream>
#include"Wall.h"
#include"food.h"
using namespace std;

class Snake
{
public:
	Snake(Wall& tempWall,Food &food);

	enum
	{
		UP = 'w',
		DOWN = 's',
		LEFT = 'a',
		RIGHT = 'd'
	};

	struct Point
	{
		int x; //������
		int y;

		Point* next;//ָ����
	};

	//��ʼ����
	void initSnake();


	//���ٽڵ�
	void destroyPoint();

	//��ӽڵ�
	void addPoint(int x, int y);

	//ɾ���ڵ�
	void delPoint();

	//�ƶ��߲���,����ֵ�����ƶ��Ƿ�ɹ�,key�û�������ַ�
	bool move(char key);


	//�趨�Ѷ�  ��ȡˢ��ʱ��
	int getSleepTime();
	//��ȡ�����
	int countList();
	//��ȡ����
	int getSorce();

	Point* pHead; //ͷ���
	Wall& wall;
	Food& food;
	bool isLoop; //ѭ����־
};