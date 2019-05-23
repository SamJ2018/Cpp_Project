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
		int x; //数据域
		int y;

		Point* next;//指针域
	};

	//初始化蛇
	void initSnake();


	//销毁节点
	void destroyPoint();

	//添加节点
	void addPoint(int x, int y);

	//删除节点
	void delPoint();

	//移动蛇操作,返回值代表移动是否成功,key用户输入的字符
	bool move(char key);


	//设定难度  获取刷屏时间
	int getSleepTime();
	//获取蛇身段
	int countList();
	//获取分数
	int getSorce();

	Point* pHead; //头结点
	Wall& wall;
	Food& food;
	bool isLoop; //循环标志
};