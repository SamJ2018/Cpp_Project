#ifndef WALL_HEAD
#define WALL_HEAD

#include <iostream>
using namespace std;

class Wall
{
public:
	enum 
	{
		ROW=26,
		COL=26
	};

	//��ʼ��ǽ��
	void initWall();

	//����ǽ��
	void drawWall();

	//������������ ��ά�����������
	void setWall(int x, int y, char c);

	//����������ȡ��ǰλ�õķ���
	char getWall(int x, int y);

private:
	char gameArray[ROW][COL];
};

#endif // !WALL_HEAD

